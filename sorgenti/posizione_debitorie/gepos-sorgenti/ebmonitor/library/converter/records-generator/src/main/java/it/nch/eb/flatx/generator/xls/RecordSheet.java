/**
 * Created on 20/mar/08
 */
package it.nch.eb.flatx.generator.xls;

import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathUtils;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import jxl.Cell;
import jxl.Sheet;


/**
 * @author gdefacci
 */
public class RecordSheet {

//	public static final DefaultColumnMappings	DEFAULT_COLUMN_MAPPINGS	= new DefaultColumnMappings();
	public static final SheetColumnsMapping	DEFAULT_COLUMN_MAPPINGS	= new SheetColumnsMapping.Base( 0, 1 ,2, 3);
	private final RowInfo[] rows;
	private final String name;
	private final String basePath;
	
	private final Set/*<String>*/ names = new TreeSet();
	private final GenTypesMap definedTypes;

	private final boolean ignoreNameColumn;
	
	private final Map/*<String, XlsCustomsFactory>*/ customs;
	
	private final SheetColumnsMapping columnIdxs;
	private final Map toRenameFields;
	private final boolean optionality;
	
	/**
	 * when using this constuctor type existance will non be checked
	 */
	public RecordSheet(Sheet sheet) {
		this(sheet, null);
	}
	
	public RecordSheet(Sheet sheet, GenTypesMap types) {
		this(sheet, types, null);
	}
	
	private RecordSheet(Sheet sheet, GenTypesMap types, String basePathPrefix) {
		this(sheet.getName(), sheet, types, basePathPrefix);
	}
	
	private RecordSheet(String name, Sheet sheet, GenTypesMap types, String basePathPrefix) {
		this(name, sheet, types, basePathPrefix, false);
	}
	
	private RecordSheet(String name, Sheet sheet, GenTypesMap types, String basePathPrefix, boolean ignoreNamesColumn) {
		this(name, sheet, types, basePathPrefix, ignoreNamesColumn, null);
	}
	
	private RecordSheet(String name, Sheet sheet, GenTypesMap types, String basePathPrefix, boolean ignoreNamesColumn, 
			Map customGenerationsMap) {
		this(name, sheet, types, basePathPrefix, ignoreNamesColumn, null, DEFAULT_COLUMN_MAPPINGS, null, false);
	}
	
	public RecordSheet(String name, 
			Sheet sheet, 
			GenTypesMap types, 
			String basePathPrefix, 
			boolean ignoreNamesColumn, 
			Map customGenerationsMap, 
			SheetColumnsMapping columnsIdxMappings,
			Map toRenameFields,
			boolean optionality) {
		this.name = name;
		this.ignoreNameColumn = ignoreNamesColumn;
		this.customs = customGenerationsMap;
		this.columnIdxs = columnsIdxMappings;
		this.toRenameFields = toRenameFields;
		this.optionality = optionality;
		
		if (types!=null && !types.isEmpty()) definedTypes = types;
		else definedTypes = null;
		RowInfo[] aux = createRows(sheet);
		if (columnIdxs.getValueColumnIdx() > -1) {
			String basePath2 = XlsUtil.findCommonXPathPrefix(aux);
			
			if (basePath2==null) {
				basePath = basePath2;
			} else {
				if (basePathPrefix!=null && basePathPrefix.length()>0) 
	//				basePath = StringUtils.concatPaths(basePathPrefix, basePath2);
					basePath = basePathPrefix;
				else
					basePath = basePath2;
			}
			
			if (basePath2==null) rows = aux;
			else rows = stripXPathPrefix(aux, basePath);
		} else {
			basePath = null;
			rows = aux;
		}
		
		
	}
	
	public static String removingLeadingSlash(String str) {
		if (str.startsWith("/")) return removingLeadingSlash(str.substring(1));
		else return str;
	}
	
	public static RowInfo[] stripXPathPrefix(RowInfo[] aux, String basePathStr) {
		RowInfo[] res = new RowInfo[aux.length];
		XPathPosition basePath = XPathsParser.instance.parseXPathPosition(basePathStr);
		for (int i = 0; i < aux.length; i++) {
			RowInfo rowInfo = aux[i];
			try {
				if (rowInfo.isXPath()) {
					BaseXPathPosition val = XPathsParser.instance.parseXPath( rowInfo.getExpression().getValue() );
					BaseXPathPosition xp = XPathUtils.sharedInstance.relative(basePath, val);
					String xpath = removingLeadingSlash(xp.toString());
					
					res[i] = new RowInfo( new XlsXpath(xpath), rowInfo.getName(), rowInfo.getType(), rowInfo.isOptional() );
				} else if (rowInfo.isCustom()) {
					XlsExpression newCustomExpr = ((XlsCustom) rowInfo.getExpression()).copy(basePathStr);
					res[i] = new RowInfo( newCustomExpr, rowInfo.getName(), rowInfo.getType() );
				} else {
					res[i] = rowInfo.copy();
				}
			} catch (Exception e) {
				throw new RuntimeException("error processing " + rowInfo , e);
			}
		}
		return res;
	}
	
	private RowInfo[] createRows(Sheet sheet2) {
		int rowsN = sheet2.getRows();
		
		RowInfo[] res = new RowInfo[rowsN-1]; // first row is the columns header
		boolean finished = false;
		for (int i=1; i<rowsN && !finished; i++) {
			Cell[] row = sheet2.getRow(i);
			if (XlsUtil.areEmptyCells(row)) {
				RowInfo[] aux = new RowInfo[i-1];
				System.arraycopy(res, 0, aux, 0, i-1);
				res = aux;
				finished = true;
			} 
			else try {
				GenTypeModel type = getType(row);
				String name = getName(row);
				XlsExpression expr = getExpression(row, type);
				boolean isOptional = getIsOptional(row);
				res[i-1] = new RowInfo(expr, name, type, isOptional );	
				names.add(name);
			} catch (Exception e) {
				throw new RuntimeException(sheet2.getName() + ": error parsing row n." + i + " row content " + XlsUtil.getStringCellsContent(row), e);
			}
		}
		return res;
	}
	
	private boolean getIsOptional(Cell[] row) {
		int idx = columnIdxs.getOptionalityColumnIdx();
		if (idx < 0) return true;	// when the isOptional column is missing all xpath are optional
		Cell cell = idx < row.length ? row[idx] : null;
		String str = cell!=null ? cell.getContents() : null;
		if (str == null || str.trim().equals("")) {
			return !optionality;
		} else {
			return optionality;
		}
	}

	private GenTypeModel getType(Cell[] cells) {
		int idx = columnIdxs.getTypeColumnIdx();
		if (idx < 1) throw new IllegalStateException("missing type column");
		else if (idx >= cells.length) throw new IllegalStateException("missing type column : " + cells);
		Cell cell = cells[idx];
		String content = cell.getContents();
		GenTypeModel res = decodeTypeName(content);
		if (res==null) {
			throw new IllegalStateException("cant find the type " + cell.getContents()); 
		}
		return res;
//		if (res instanceof String) return new SimpleType((String) res);
//		else return (GenTypeModel) res;
	}
	
	private GenTypeModel decodeTypeName(String alias) {
//		if (definedTypes==null) return alias; // no types map => alias is the type name
		return definedTypes.get(alias);
	}
	
	private boolean lessThan(int what, int to) {
		if (what>-1) return what < to;
		else return false;
	}

	private String getName(Cell[] cells) {
		Cell cell = (ignoreNameColumn || columnIdxs.getDescriptionColumnIdx() >= cells.length) ? null: cells[columnIdxs.getDescriptionColumnIdx()]; 
		Cell exprsCell = lessThan(columnIdxs.getValueColumnIdx(),cells.length) ? cells[columnIdxs.getValueColumnIdx()] : null;
		String specifiedName = cell!=null ? cell.getContents().trim(): null;
		String name = null;
		if (specifiedName==null || "".equals(specifiedName)) {
			String firstCol = exprsCell.getContents().trim();
			if (firstCol.indexOf("\n")>-1) {
				firstCol = firstCol.split("\n")[0];
			}
			
			if (firstCol==null || firstCol.length()==0 || XlsUtil.isQuotedExpr(firstCol)) {
				name = "filler";
			} else if (XlsUtil.isXPath(firstCol)) {
				name = XlsUtil.extractNameFromXPath(firstCol).trim();
			} else if (XlsUtil.isGetSymbol(firstCol)) {
				name = firstCol.toLowerCase().replaceAll("\\s", "");
			} else {
				throw new IllegalStateException("unmanaged: cant deduce name from cells [" + cell.getContents() 
						+ "] and [" + exprsCell.getContents()  + "]");
			}
		} else {
			name = specifiedName.trim();
		}
		if (toRenameFields!=null && toRenameFields.containsKey(name)) {
			name = (String) toRenameFields.get(name);
		}
		
		if (names.contains(name)) {
			int i=1;
			while (true) {
				String nname = name + i;
				if (!names.contains(nname)) return nname; 
				i++;
			}
		}
		return name;
	}
	
	private int guessLength(GenTypeModel typ) {
		String str = typ.toString();
		int idx = str.length() - 1;
		char ch = str.charAt(idx);
		StringBuffer sb = new StringBuffer();
		while (idx > -1 && Character.isDigit(ch)) {
			sb.insert(0, ch);
			idx--;
			ch = str.charAt(idx);
		}
		String nstr = sb.toString();
		if (nstr.length() < 1) {
			throw new IllegalStateException("cant guess length from " + typ.toString());
		}
		return Integer.valueOf(nstr).intValue();
	}

	/**
	 * forse e' meglio usare antlr, anziche this shit
	 * @param cell
	 * @return
	 */
	private XlsExpression getExpression(Cell[] cells, GenTypeModel type) {
		if (columnIdxs.getValueColumnIdx() > -1) { 
			Cell cell = columnIdxs.getValueColumnIdx() < cells.length ? cells[columnIdxs.getValueColumnIdx()] : null;
			String content = cell.getContents().trim();
			if (content==null || "".equals(content)) {
				return new XlsVoid(guessLength(type));
//				return XlsQuoted.createRaw(" ");
			} else {
				boolean multiline = content.indexOf("\n") > -1;
				if (!multiline) {
					if (XlsUtil.isXPath(content)) {
						return new XlsXpath(content);
					} else if (XlsUtil.isGetSymbol(content)) {
						return new XlsSymbol(content);
					} else if (XlsUtil.match(XlsUtil.singleQuatedPattern, content) 
							|| XlsUtil.match(XlsUtil.doubleQuatedPattern, content)) {
						return new XlsQuoted(content);
					}
				} else {
					String[] parts = content.split("\n");
					String op = parts[0];  // first line is the operator, remaining lines are the arguments
					
					XlsCustomsFactory factory = getGeneratorFactory(op);
					if (factory == null) {
						throw new IllegalArgumentException("expecting a custom generator for op " + op + " cell content " + content);
					}
					String[] args = new String[parts.length-1];
					System.arraycopy(parts, 1, args, 0, parts.length-1);	
					
					return factory.create(type, args, null);	// initially the basepath is null
				}
			}
		} else {
			return null;
		}
		throw new IllegalArgumentException("cant recognize " + cells);
	}

	private XlsCustomsFactory getGeneratorFactory(String op) {
		return (XlsCustomsFactory) this.customs.get(op.toUpperCase());
	}
	
	public RowInfo[] getRows() {
		return rows;
	}
	
	public String getName() {
		return name;
	}
	
	public String getBasePath() {
		return basePath;
	}
	
	public Set getNames() {
		return names;
	}
		
}


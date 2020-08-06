/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.flatx.generator.xls.SheetColumnsMapping;
import it.nch.eb.flatx.generator.xls.XlsUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;

/**
 * @author gdefacci
 *
 */
public class XRowInfoUtils {
	
	public static final XRowInfoUtils instance = new XRowInfoUtils();
	
	public String findBaseXpath(List/*<XRowInfo>*/ xpRows) {
		List/*<String>*/ xpths = new ArrayList();
		
		int idx = 0;
		for (Iterator it = xpRows.iterator(); it.hasNext(); idx++) {
			XRowInfo ri = (XRowInfo) it.next();
			if (ri.getXPath()!=null) {
				xpths.add( ri.getXPath() );
			}
		}
	    return XlsUtil.findCommonXPathPrefix((String[]) xpths.toArray(new String[0]));
	}
	
	public List/*<XRowInfo>*/ getRecordCellsInfos(Sheet sheet, int startrow, SheetColumnsMapping columnsMapping) {
		Cell[] dCels = getAvaiableColumnCells(sheet, startrow, columnsMapping.getDescriptionColumnIdx());
		List/*<XRowInfo>*/ xpathCells = new ArrayList();
		for (int rowIdx = 0; rowIdx < dCels.length; rowIdx++) {
			int ridx = rowIdx + 1; 
			String valueCellContent = getContent(sheet, columnsMapping.getValueColumnIdx(), ridx);
			if (valueCellContent!=null) {
				String name = getContent(sheet, columnsMapping.getDescriptionColumnIdx(), ridx) ;
				String typeName = getContent(sheet, columnsMapping.getTypeColumnIdx(), ridx);
				boolean optional = getBooleanContent(sheet, columnsMapping.getOptionalityColumnIdx(), ridx);
				XRowInfo xri = createXRowInfo(name, typeName, optional, valueCellContent);
				xpathCells.add(xri);
			}
		}
		return xpathCells;
	}

	XRowInfo createXRowInfo(String name, String typeName,
			boolean optional, String valueCellContent) {
		XRowInfo xri = null;
		if (valueCellContent == null || !XlsUtil.isQuotedExpr(valueCellContent)) {
			xri = XRowInfo.xpath(name, typeName, valueCellContent, optional );
		} else {
			xri = XRowInfo.constValue(name, typeName, XlsUtil.stripBorders( valueCellContent ));
		}
		return xri;
	}
	
	public List/*<XRowInfo>*/ getAllCellsInfos(Sheet sheet, int startrow, SheetColumnsMapping columnsMapping) {
		DefaultCellFilter dfltCellFltr = new DefaultCellFilter(columnsMapping);
		return getFilteredCellsInfos(sheet, startrow, columnsMapping, dfltCellFltr);
	}
	
	public XRowInfo[] getAllCellsInfosArray(Sheet sheet, int startrow, SheetColumnsMapping columnsMapping) {
		return (XRowInfo[]) getAllCellsInfos(sheet, startrow, columnsMapping).toArray(new XRowInfo[0]);
	}
	
	private boolean getBooleanContent(Sheet sheet, int colIdx,
			int ridx) {
		return XlsUtil.getBooleanContent(sheet, colIdx, ridx);
	}

	private String getContent(Sheet sheet, int colIdx, int ridx) {
		return XlsUtil.getContent(sheet, colIdx, ridx);
	}

	public XRowInfo[] getFilteredCellsInfosArray(Sheet sheet, int startrow, SheetColumnsMapping columnsMapping, CellFilter filter) {
		return (XRowInfo[]) getFilteredCellsInfos(sheet, startrow, columnsMapping, filter).toArray(new XRowInfo[0]);
	}
	
	public List/*<XRowInfo>*/ getFilteredCellsInfos(Sheet sheet, int startrow, SheetColumnsMapping columnsMapping, CellFilter filter) {
		int colIdx = columnsMapping.getDescriptionColumnIdx();
		return getFilteredCellsInfos(sheet, startrow, colIdx, filter);
	}

	public XRowInfo[] getFilteredCellsInfosArray(Sheet sheet, int startrow, int colIdx, CellFilter filter) {
		return (XRowInfo[]) getFilteredCellsInfos(sheet, startrow, colIdx, filter).toArray(new XRowInfo[0]);
	}
	
	public List getFilteredCellsInfos(Sheet sheet, int startrow, int colIdx, CellFilter filter) {
		Cell[] dCels = getAvaiableColumnCells(sheet, startrow, colIdx);
		List/*<XRowInfo>*/ xpathCells = new ArrayList();
		for (int rowIdx = 0; rowIdx < dCels.length; rowIdx++) {
			int ridx = rowIdx + 1; 
			if (filter.match(sheet, ridx)) {
				XRowInfo xri = filter.create(sheet, ridx);
				xpathCells.add(xri);
			}
		}
		return xpathCells;
	}
	
	public Cell[] getAvaiableColumnCells(Sheet sheet, int startRow,
			int valueColumnIdx) {
				Cell[] cells = sheet.getColumn(valueColumnIdx);
				List/*Cell*/ res = new ArrayList();
				boolean stop = false;
				for (int i = startRow; i < cells.length && !stop; i++) {
					Cell cell = cells[i];
					String cont = cell.getContents();
					if ((cont == null) || (cont.trim().length()==0)) stop = true;
					else res.add(cell);
				}
				return (Cell[]) res.toArray(new Cell[0]);
			}

	private final static String[] splitChars = new String[] {"\\s", "_", "\\&" };
	public String[] splitWord(String word) {
		String[] parts = StringUtils.splitWord(word.trim(), splitChars);
		return StringUtils.camelSplitWords(parts);
	}
	
	public String toJavaName(String aword) {
		StringBuffer sb = new StringBuffer();
		String[] parts = splitWord(aword);
		for (int i = 0; i < parts.length; i++) {
			if (i>0) {
				sb.append(StringUtils.capitalized( parts[i] ));
			} else {
				sb.append(StringUtils.decapitalized( parts[i] ));
			}
		}
		return sb.toString();
	}
	
	public String toHypenedLowercaseName(String recordName) {
		String[] parts = recordName.toLowerCase().split("_");
		StringBuffer sb = null;
		for (int i = 0; i < parts.length; i++) {
			String part = parts[i].trim();
			if (part.length()>0) {
				if (sb == null) {
					sb = new StringBuffer(part);
				} else {
					sb.append("-");
					sb.append(part);
				}
			}
		}
		return sb.toString();
	}

	public String toJavaClassName(String recordName) {
		return StringUtils.capitalized(toJavaName(recordName));
	}
}

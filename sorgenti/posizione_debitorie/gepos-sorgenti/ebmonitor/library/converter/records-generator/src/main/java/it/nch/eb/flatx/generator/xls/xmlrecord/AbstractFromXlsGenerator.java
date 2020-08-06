/**
 * Created on 20/mar/08
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import it.nch.eb.flatx.generator.BaseConversionGenerationModel;
import it.nch.eb.flatx.generator.SheetFilter;
import it.nch.eb.flatx.generator.xls.SheetColumnsMapping;
import it.nch.eb.flatx.generator.xls.XlsUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


/**
 * @author gdefacci
 */
public abstract class AbstractFromXlsGenerator extends BaseConversionGenerationModel implements IXlsSheetGenerator {
	
	private final Set/*<String>*/ sheetsToSkip = new TreeSet();
	
	private final List/*<SheetFilter>*/ filters = new ArrayList(); 
	
	public AbstractFromXlsGenerator(java.io.File baseFolder, SheetColumnsMapping columnMappings, 
			String packageName) {
		super(baseFolder, columnMappings, packageName);
	}

	public AbstractFromXlsGenerator(File baseFolder,
			SheetColumnsMapping columnsMappings,
			String packageName, 
			String modelsPackageName,
			String recordPackageName) {
		super(baseFolder, columnsMappings,
				packageName, 
				modelsPackageName, 
				recordPackageName);
	}

	public AbstractFromXlsGenerator skipSheet(final String recordName) {
		this.filters.add(new SheetFilter() {

			public boolean apply(Sheet sheet) {
				String rn = getFixedSheetName(sheet);
				String nm = sheet.getName();
				return (!recordName.equals(rn)) && (!recordName.equals(nm));
			}
			
		});
//		sheetsToSkip.add(XlsUtil.getFixedSheetName(recordName));
		return this;
	}
	
	public AbstractFromXlsGenerator skipSheets(String[] recordsName) {
		for (int i = 0; i < recordsName.length; i++) {
			skipSheet(recordsName[i]);
		}
		return this;
	}
	
	public AbstractFromXlsGenerator skipAllBut(String[] recsName) {
		final Set/*<String>*/ set = new HashSet();
		for (int i = 0; i < recsName.length; i++) {
			set.add( recsName[i] );
		}
		this.filters.add(new SheetFilter() {

			public boolean apply(Sheet sheet) {
				String rn = getFixedSheetName(sheet);
				String nm = sheet.getName();
				return set.contains(rn) || set.contains(nm);
			}
			
		});
		return this;
	}
	
	public AbstractFromXlsGenerator withModelsPackageName(String name) {
		setModelsPackageName(name);
		return this;
	}
	
	public Sheet[] filter(Sheet[] sheets) {
		List/*<Sheet>*/ res = new ArrayList();
		for (int shi = 0; shi < sheets.length; shi++) {
			Sheet sheet = sheets[shi];
			boolean includeSheet = true;
			for (Iterator it = this.filters.iterator(); includeSheet && it.hasNext();) {
				SheetFilter fltr = (SheetFilter) it.next();
				if (!fltr.apply(sheet)) {
					includeSheet = false;
				}
			}
			if (includeSheet) {
				res.add(sheet);
			}
		}
		return (Sheet[]) res.toArray(new Sheet[0]);
	}
	
	public IXlsGenerator[] xlsPostGenerators() {
		return new IXlsGenerator[0];
	}
	public abstract IXlsSheetGenerator[] xlsGenerators();

	public void generate(Workbook workbook) {
		String recordName = null;
		try {
			Sheet[] sheets = filter(workbook.getSheets());
			List/*<String>*/ recs = new ArrayList();
			for (int i = 0; i < sheets.length; i++) {
				Sheet sheet = sheets[i];
				recordName = sheet.getName();
				generate(sheet);
				recs.add(recordName);
			}
			IXlsGenerator[] gens = xlsPostGenerators();
			if (recs.size()>0 && gens.length>0) {
				String[] processedSheets = (String[]) recs.toArray(new String[0]);
				for (int i = 0; i < gens.length; i++) {
					IXlsGenerator xlsPostGenerator = gens[i];
					xlsPostGenerator.generate(workbook, processedSheets);
				}
			}
			
		} catch (Exception e) {
			throw new RuntimeException(recordName==null? "" :"error generating " + recordName, e);
		}
	}

	public final void generate(String recordName, Sheet sheet) {
		IXlsSheetGenerator[] gens = xlsGenerators();
		for (int i = 0; i < gens.length; i++) {
			IXlsSheetGenerator xlsGenerator = gens[i];
			xlsGenerator.generate(recordName, sheet);
		}
		
	}
	
	public void generate(Sheet sheet) throws IOException {
		String recordName = getFixedSheetName(sheet);
		generate(recordName, sheet);
	}

	public static String getFixedSheetName(Sheet sheet) {
		String name = sheet.getName();
		return XlsUtil.getFixedSheetName(name);
	}

	public Set getSheetsToSkip() {
		return sheetsToSkip;
	}
	
	protected String getContent(Sheet sheet, int col, int row) {
		Cell cl = sheet.getCell(col, row);
		return cl.getContents();
	}

	protected boolean getBooleanContent(Sheet sheet, int col, int row) {
		Cell cl = sheet.getCell(col, row);
		String str = cl.getContents().trim();
		return str.length() > 0;
	}

}

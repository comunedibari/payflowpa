/**
 * 
 */
package it.nch.eb.flatx.generator.xls.recordimpl;

import it.nch.eb.common.utils.NamingStrategy;
import it.nch.eb.flatx.generator.ConversionGenerationModel;
import it.nch.eb.flatx.generator.JavaGenerator;
import it.nch.eb.flatx.generator.OutputStreamGenerator;
import it.nch.eb.flatx.generator.xls.SheetColumnsMapping;
import it.nch.eb.flatx.generator.xls.XlsUtil;
import it.nch.eb.flatx.generator.xls.xmlrecord.IXlsGenerator;
import it.nch.eb.flatx.generator.xls.xmlrecord.XRowInfoUtils;

import java.io.File;
import java.io.OutputStream;

import jxl.Workbook;

/**
 * @author gdefacci
 *
 */
public class ModelsGeneratorGenerator implements IXlsGenerator, ConversionGenerationModel {
	
	private final ConversionGenerationModel mainGenerator;
	
	public ModelsGeneratorGenerator(ConversionGenerationModel mainGenerator) {
		this.mainGenerator = mainGenerator;
	}

	public void generate(Workbook workbook, String[] processedSheets) {
		
		OutputStreamGenerator modelsGenerator = createRenderer(workbook, processedSheets);
		
		File pkgFolder = JavaGenerator.createPackageFolder(getBaseFolder(), getPackageName());
		File trgtFile = new File(pkgFolder, getGeneratedClassName() + ".java");
		OutputStream fos = XlsUtil.fileStream(trgtFile);
		
		modelsGenerator.generate(fos);
		
		System.out.println("created " + trgtFile);
	}
	
	public String getGeneratedClassName() { return "ModelsGenerator"; }

	protected OutputStreamGenerator createRenderer(Workbook workbook, String[] processedSheets) {
		String[] recs = new String[processedSheets.length];
		for (int i = 0; i < processedSheets.length; i++) {
			recs[i] = getRercordClassFullName(processedSheets[i]);
		}
		return new ParserRecord2GenerateModelsRenderer(this, getGeneratedClassName(), recs);
	}
	
	public String getRercordClassFullName(String sheetName) {
		return getRecordPackageName() + "." + getRecordName(sheetName);
	}

	public String getRecordName(String sheetName) {
		return getRecordNamingStrategy().apply( XRowInfoUtils.instance.toJavaClassName( XlsUtil.getFixedSheetName( sheetName ) ) );
	}

	public SheetColumnsMapping getColumnMappings() {
		return mainGenerator.getColumnMappings();
	}

	public int getStartRow() {
		return mainGenerator.getStartRow();
	}
	
	public String[] getModelsInterfaces() {
		return mainGenerator.getModelsInterfaces();
	}

	public String getModelsPackageName() {
		return mainGenerator.getModelsPackageName();
	}

	public File getBaseFolder() {
		return mainGenerator.getBaseFolder();
	}

	public String getPackageName() {
		return mainGenerator.getPackageName();
	}

	public String getRecordPackageName() {
		return mainGenerator.getRecordPackageName();
	}

	public NamingStrategy getRecordNamingStrategy() {
		return mainGenerator.getRecordNamingStrategy();
	}

	public String[] getRecordInterfaces() {
		return mainGenerator.getRecordInterfaces();
	}

}

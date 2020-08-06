/**
 * 30/nov/2009
 */
package it.nch.eb.flatx.generator.xls.recordimpl;

import java.io.File;
import java.io.OutputStream;

import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.generator.JavaGenerator;
import it.nch.eb.flatx.generator.xls.RecordSheet;
import it.nch.eb.flatx.generator.xls.SheetColumnsMapping;
import it.nch.eb.flatx.generator.xls.XlsUtil;
import it.nch.eb.flatx.generator.xls.xmlrecord.IXlsSheetGenerator;
import jxl.Sheet;

/**
 * @author gdefacci
 */
public class RecordImplXlsSheetGenerator implements IXlsSheetGenerator{
	
	private RecordImplFromXlsGenarator mainGen;
	

	public RecordImplXlsSheetGenerator(
			RecordImplFromXlsGenarator recordImplFromXlsGenarator) {
		this.mainGen = recordImplFromXlsGenarator;
	}


	public void generate(String recName, Sheet sheet) {
		try {
		String tipoRecord = mainGen.getTipoRecord(recName);
		File pkgFolder = JavaGenerator.createPackageFolder(mainGen.getBaseFolder(), mainGen.getRecordPackageName());
		RecordSheet recordSheet; 
		String recordName = Character.isUpperCase( recName.charAt(0) ) ? recName : StringUtils.capitalized(recName);
		SheetColumnsMapping colMappings = mainGen.getColumnMappings() == null ? RecordSheet.DEFAULT_COLUMN_MAPPINGS : mainGen.getColumnMappings();
		recordSheet = new RecordSheet(recordName, sheet, 
				mainGen.getDefineTypes(), 
				mainGen.getBasePathPrefix(recName), 
				mainGen.isIgnoreNamesColumn(), 
				mainGen.getGeneratorsMap(), 
				colMappings, 
				mainGen.getFieldsToRename(), 
				mainGen.getOptionalitySemantic());
		IRecordSheetXmlRecordImplRenderer recGenerator = mainGen.createRecordsGenerator(tipoRecord);
		File f = new File(pkgFolder, recordName + ".java");
		OutputStream fos = null;
		try {
			fos = XlsUtil.fileStream(f);
			recGenerator.generate(fos, recordSheet);
			System.out.println("created file " + f.getAbsolutePath());
		} finally {
			ResourcesUtil.close(fos);
		}
		} catch (Exception e) {
			throw new RuntimeException("error generating sheet " + recName, e);
		}
		
	}

}

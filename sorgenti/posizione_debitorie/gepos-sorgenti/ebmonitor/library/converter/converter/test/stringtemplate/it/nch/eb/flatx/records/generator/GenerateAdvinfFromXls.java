/**
 * Created on 20/mar/08
 */
package it.nch.eb.flatx.records.generator;

import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.generator.xls.SheetColumnsMapping;
import it.nch.eb.flatx.generator.xls.XlsOrFactory;
import it.nch.eb.flatx.generator.xls.recordimpl.RecordImplFromXlsGenarator;

import java.io.File;

import jxl.Sheet;
import jxl.Workbook;


/**
 * @author gdefacci
 */
public class GenerateAdvinfFromXls {

	static final File srcFolder = new File("src/java");
//	static final File srcFolder = new File("D:/java/projects/flattener/conversions-project/test/stringtemplate");
	static final String pkgName = "it.nch.sample.eb.common.converter.pmtreq.advinf.records";
	static final File workBookFile = new File("docs/advinf.xls");
	static final SheetColumnsMapping.Base columnMappings = new SheetColumnsMapping.Base(5, 2, 1, 3);
	
	public static void main(String[] args) throws Exception {
		Workbook workBoook = Workbook.getWorkbook(workBookFile);
		RecordImplFromXlsGenarator generator = new RecordImplFromXlsGenarator(srcFolder, columnMappings, pkgName);
		generator
			.addTypesContainer(BaseConverters.class)
			.addTipoRecord("RecordTestaBody", "SB")
			.addTipoRecord("RecorddiTesta", "SH")
			.addTipoRecord("RecordCodaBody", "EB")
			.addTipoRecord("RecordCoda", "EH")
			.setIgnoreNamesColumn(true);
		generator.generate(workBoook);
	}
	
	public static void main_(String[] args) throws Exception {
		try {
			generate10();
			System.out.println("flk");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void generate40() throws Exception {
		Sheet sheet40 = Workbook.getWorkbook(workBookFile).getSheet("Record 40");
		RecordImplFromXlsGenarator generator = new RecordImplFromXlsGenarator(srcFolder, columnMappings, pkgName)
			.setIgnoreNamesColumn(true).addCustomGenerator("or", new XlsOrFactory());
		generator.generate(sheet40);
	}
	

	public static void generate10() throws Exception {
		Sheet sheet10 = Workbook.getWorkbook(workBookFile).getSheet("Record 10");
		RecordImplFromXlsGenarator generator = new RecordImplFromXlsGenarator(srcFolder, columnMappings, pkgName)
			.addType("datetime19", "dateTime19")
			.addType("number13_2", "fd_number13_2")
			.addTypesContainer(BaseConverters.class)
			.setIgnoreNamesColumn(true).addCustomGenerator("or", new XlsOrFactory());
		generator.generate(sheet10);
	}
}

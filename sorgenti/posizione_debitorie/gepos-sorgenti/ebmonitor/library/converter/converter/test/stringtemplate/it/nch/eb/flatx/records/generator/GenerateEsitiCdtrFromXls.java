/**
 * Created on 20/mar/08
 */
package it.nch.eb.flatx.records.generator;

import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.generator.InProjectGenerator;
import it.nch.eb.flatx.generator.xls.recordimpl.RecordImplFromXlsGenarator;

import java.io.File;

import jxl.Workbook;


/**
 * @author gdefacci
 */
public class GenerateEsitiCdtrFromXls {

	private static final File	PROJ_FOLDER	= InProjectGenerator.instance.location();
	static final File srcFolder = new File(PROJ_FOLDER, "src/java");
	static final String pkgName = "it.nch.eb.common.converter.pmtreq.cdtr.records.gen";
	static final File workBookFile = new File(PROJ_FOLDER , "docs/esiti-cdtr.xls");
	
	public static void main(String[] args) throws Exception {
		Workbook workBoook = Workbook.getWorkbook(workBookFile);
		RecordImplFromXlsGenarator generator = new RecordImplFromXlsGenarator(srcFolder, pkgName);
		generator
			.addTipoRecord("RecordTestaBody", "SB")
			.addTipoRecord("RecordCodaBody", "EB")
			.addTypesContainer(BaseConverters.class)
			.setDefaultBasePathPrefix("/MSG:CBICdtrPmtStatusReportMsg/MSG:CBIBdyCdtrPmtStatusReport/BODY:CBIEnvelCdtrPmtStatusReport")
			.skipSheet("Record65");
			
			
		generator.generate(workBoook);
	}
}

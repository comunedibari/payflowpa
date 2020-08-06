package it.tasgroup.report.exporter.dynamic;

import it.tasgroup.services.util.enumeration.EnumDynaReportFormat;
import it.tasgroup.services.util.enumeration.EnumExportSTDFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
//import ar.com.fdvs.dj.test.TestRepositoryProducts;
public class DynaReportExporter {
  
    public static OutputStream esporta(EnumDynaReportFormat dynaReportFormat, EnumExportSTDFormat enumExportSTDFormat, List righe, List<String[]> intestazione, boolean withIntestazione, String separatore, OutputStream out, Map<String, String> mapEtichetteIntestazione, Locale locale) {

		DynamicReportBuilder drb = new DynamicReportBuilder();
		
		IDynaReportColumnFormatter columnFormatter = DynaReportColumnFormatterFactory.createColumnFormatter(dynaReportFormat, enumExportSTDFormat);
		
		columnFormatter.formatColumns(drb, intestazione, withIntestazione, mapEtichetteIntestazione);
		
		drb.setIgnorePagination(true);
        drb.setMargins(0, 0, 0, 0);
        drb.setUseFullPageWidth(true);  //we tell the report to use the full width of the page. this rezises
        drb.setWhenNoDataAllSectionNoDetail();
        drb.setWhenResourceMissingLeaveEmptySpace();
        drb.setReportLocale(locale);
		//the columns width proportionally to meat the page width.

		DynamicReport dr = drb.build(); //Finally build the report!
		
		JRDataSource ds = new JRBeanCollectionDataSource(righe);//.getDummyCollection());    //Create a JRDataSource, the Collection used
		//here contains dummy hardcoded objects...
		JasperPrint jp;
		
		try {
			
			jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);

//			exportXls(fileName+".xls", jp, drb);
			return exportCsv(jp, drb,separatore, out);
//			exportPdf(fileName+".pdf", jp, drb);
//			JasperViewer.viewReport(jp);		//finally display the report report
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    //Creates the JasperPrint object, we pass as a Parameter
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//the DynamicReport, a new ClassicLayoutManager instance (this
		//one does the magic) and the JRDataSource
		return null;
	}

	

	private static void exportPdf(String path, JasperPrint jp, DynamicReportBuilder drb) throws Exception{
		Style titleStyle = new Style();
		Style subtitleStyle = new Style();
		Style headerStyle = new Style();
		Style detailStyle = new Style();

		drb.setTitle("November 2006 sales report")		//defines the title of the report
		.setSubtitle("The items in this report correspond "
				+"to the main products: DVDs, Books, Foods and Magazines")
				.setDetailHeight(15)		//defines the height for each record of the report
				.setMargins(30, 20, 30, 15)		//define the margin space for each side (top, bottom, left and right)
				.setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
				.setColumnsPerPage(1);		//defines columns per page (like in the telephone guide)

		FileOutputStream out = new FileOutputStream(path);

		JasperExportManager.exportReportToPdfStream(jp, out);

		//Close the output stream
		out.close();
	}

	private static OutputStream exportCsv(JasperPrint jp, DynamicReportBuilder drb, String path) throws Exception{
		
		OutputStream out = null;
		
		return exportCsv(jp, drb,"\t",out);
	
	}
	
	private static OutputStream exportCsv(JasperPrint jp, DynamicReportBuilder drb, String separatore, OutputStream out) throws Exception{
		
		drb.setIgnorePagination(true);
		
		drb.setWhenResourceMissingLeaveEmptySpace();
		
		JRCsvExporter txtExporter = new JRCsvExporter();

		txtExporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		txtExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);

//		txtExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF8");
		txtExporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, separatore);

		txtExporter.exportReport();

		return null;
	}
	private static void exportXls(String path, JasperPrint jp, DynamicReportBuilder drb) throws Exception{
		drb.setIgnorePagination(true);
		drb.setReportName("My Report");

		JRXlsExporter exporter = new JRXlsExporter();

		File outputFile = new File(path);
		FileOutputStream fos = new FileOutputStream(outputFile);

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fos); //and output stream

		//Excel specific parameter
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);



		String[] sheetNames = {"Sheet1"};
		exporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, sheetNames );
		exporter.exportReport();

	}
}

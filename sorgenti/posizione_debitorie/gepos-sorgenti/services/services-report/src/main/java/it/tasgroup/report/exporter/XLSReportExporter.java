/*
 * Created on 4-ott-09
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.tasgroup.report.exporter;

import java.io.ByteArrayOutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 * @author pazzik
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class XLSReportExporter extends ReportExporter {

	/* (non-Javadoc)
	 * @see it.nch.erbweb.web.common.report.exporter.AbstractReportExporter#exportReport()
	 */
	public byte[] exportReport(JasperPrint jasperPrint) throws JRException {
		JExcelApiExporter exporterXLS = new JExcelApiExporter();
	
		ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();

		exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
	
		exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
	
		exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
	
		exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
	
		exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	
		exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	
		System.out.println("PRIMA");
	
		exporterXLS.exportReport();
		
		byte[] flusso = outputByteArray.toByteArray();
	
		System.out.println("DOPO"); 
		
		return flusso;

	}/*private void generateXLSOutput(String reportname,
	JasperPrint jasperPrint,
	HttpServletResponse resp)
	throws IOException, JRException {
	String reportfilename = tagreport(reportname) + ".xls";
	JExcelApiExporter exporterXLS = new JExcelApiExporter();

	exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
	exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
	exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
	resp.setHeader("Content-Disposition", "inline;filename=" + reportfilename);
	resp.setContentType("application/vnd.ms-excel");

	exporterXLS.exportReport();
	}*/

}

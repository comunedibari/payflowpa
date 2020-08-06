/*
 * Created on 4-ott-09
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.tasgroup.report.exporter;

/**
 * @author pazzik
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ReportExporterFactory {

	public static final String PDF_EXT = "pdf";
	public static final String XLS_EXT = "xls";
	public static final String RTF_EXT = "rtf";
	public static final String HTML_EXT = "html";
	public static final String CSV_EXT = "csv";

	public static ReportExporter getReportExporter(String reportExtension){
		if (reportExtension.equals(PDF_EXT))
			return new PDFReportExporter();
		else if (reportExtension.equals(XLS_EXT))
			return new XLSReportExporter();
		else if (reportExtension.equals(RTF_EXT))
			return new RTFReportExporter();
		else if (reportExtension.equals(HTML_EXT))
			return new HTMLReportExporter();
		return null;
	}

}

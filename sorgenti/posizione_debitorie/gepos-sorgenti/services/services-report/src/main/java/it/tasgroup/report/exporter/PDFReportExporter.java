/*
 * Created on 4-ott-09
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.tasgroup.report.exporter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * @author pazzik
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PDFReportExporter extends ReportExporter {

	/* (non-Javadoc)
	 * @see it.nch.erbweb.web.common.report.exporter.AbstractReportExporter#exportReport()
	 */
	public byte[] exportReport(JasperPrint jasperPrint) throws JRException{
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

}

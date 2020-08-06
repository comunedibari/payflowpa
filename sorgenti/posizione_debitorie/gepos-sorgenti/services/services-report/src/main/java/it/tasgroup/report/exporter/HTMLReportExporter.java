/*
 * Created on 4-ott-09
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.tasgroup.report.exporter;

import java.io.ByteArrayOutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;

/**
 * @author pazzik
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HTMLReportExporter extends ReportExporter {

	/* (non-Javadoc)
	 * @see it.nch.erbweb.web.common.report.exporter.AbstractReportExporter#exportReport()
	 */
	public byte[] exportReport(JasperPrint jasperPrint) throws JRException{

		JRHtmlExporter exporter = new JRHtmlExporter();
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,os);

		exporter.exportReport();

		return os.toByteArray();
	}

}

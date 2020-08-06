/*
 * Created on 25-set-09
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.tasgroup.report;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.report.exporter.ReportExporter;
import it.tasgroup.report.exporter.ReportExporterFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRXmlUtils;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

/**
 * @author PazziK
 *
 * TODO implementare metodi per export HTML e RTF
 * TODO verificare se si mantiene l'hot deploy a caldo
 * TODO come passare la timezone dell'applicazione e non della VM al generatore di Report?
 * TODO predisporre per l'invio di e-mail con EMailManager?
 * TODO passare un nome consono ai fogli EXCEL
 * TODO pensare a modifiche per evitare OutOfMemoryError
 * TODO formattare date e importi sui VO?
 * TODO parametri di ricerca sul foglio EXCEL
 * TODO colori su EXCEL
 * TODO migliorare l'uso delle cornici sui report (in particolare righe e colonne di ListaDisposizioni)
 * TODO strategy su DataSource
 * TODO documentare
 */

public class ReportManager {

	private static ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("JasperReports/config.properties");

	private static String compiledDir = cpl.getProperty("configuration.compiled.location");

	private static Logger logger = Logger.getLogger(ReportManager.class.getName());

	protected Map<String, Object> reportParams = new HashMap<String, Object>();

	protected String reportFileName = null;

	protected String reportExtension = null;

	protected String compiledReportFileName = null;

	protected String moduleName = null;

	private ReportExporter reportExporter = null;

	public ReportManager(String reportFileName, String reportExtension, Map<String, Object> reportParams, String moduleName) {
		this.reportFileName = reportFileName;
		this.reportExtension = reportExtension;
		this.reportParams = reportParams;
		this.compiledReportFileName = reportFileName + reportExtension.toUpperCase() + ".jasper";
		this.moduleName = moduleName;
		this.reportExporter = ReportExporterFactory.getReportExporter(reportExtension);
		logger.debug("ReportManager - costruttore:" + toString());
	}

	public String toString() {

		StringBuffer stringBuf = new StringBuffer("\nReportManager:\n");
		stringBuf.append("\tcompiledDir: " + compiledDir + "\n");
		stringBuf.append("\treportFileName: " + reportFileName + "\n");
		stringBuf.append("\treportExtension: " + reportExtension + "\n");
		stringBuf.append("\treportParams: " + reportParams + "\n");
		stringBuf.append("\tcompiledReportFileName: " + compiledReportFileName + "\n");
		stringBuf.append("\tserviceName: " + moduleName + "\n");
		stringBuf.append("\treportExporter: " + reportExporter + "\n");
		return stringBuf.toString();
	}
	
	private JasperPrint generateAndFillJasperPrint() throws JRException, FileNotFoundException {

		logger.debug("generateAndFillJasperPrint[0]:");

		String moduleSubstring = (moduleName != null && moduleName.length() > 0) ? moduleName + "/" : "";

		InputStream fi = getClass().getResourceAsStream("/"+compiledDir + moduleSubstring + compiledReportFileName);

		// File f = new File(compiledDir + moduleSubstring + compiledReportFileName);

		//logger.debug("generateAndFillJasperPrint[1]: File=" + f);

		//FileInputStream fi = new FileInputStream(f);

		if (reportParams == null) 
			reportParams = new HashMap<String, Object>();	

		logger.debug("generateAndFillJasperPrint[2]: FileInputStream=" + fi + "\nreportParams " + reportParams);

		JasperPrint jasperPrint = JasperFillManager.fillReport(fi, reportParams);

		logger.debug("generateAndFillJasperPrint[3]: jasperPrint=" + jasperPrint);

		return jasperPrint;
	}

	private JasperPrint generateAndFillJasperPrint(JRBeanCollectionDataSource jrDataSource) throws JRException, FileNotFoundException {

		logger.debug("generateAndFillJasperPrint[0]: jrDataSource=" + jrDataSource);

		String moduleSubstring = (moduleName != null && moduleName.length() > 0) ? moduleName + "/" : "";

		InputStream fi = getClass().getResourceAsStream("/"+compiledDir + moduleSubstring + compiledReportFileName);
		//File f = new File("D:/Projects/IRIS5/services/services-report-artifact/src/main/resources/"   + compiledDir + moduleSubstring + compiledReportFileName);

		//logger.debug("generateAndFillJasperPrint[1]: File=" + f);

		//FileInputStream fi = new FileInputStream(f);

		if (reportParams == null) 
			reportParams = new HashMap<String, Object>();
		

		logger.debug("generateAndFillJasperPrint[2]: FileInputStream=" + fi + "\nreportParams " + reportParams + "\njrDataSource=" + jrDataSource);

		JasperPrint jasperPrint = JasperFillManager.fillReport(fi, reportParams, jrDataSource);

		logger.debug("generateAndFillJasperPrint[3]: jasperPrint=" + jasperPrint);

		return jasperPrint;
	}
	
	public byte[] generateReport(byte[] decodedXMLFlow, Locale xmlLocale, String datePattern, String numberPattern) {

		byte[] reportFlow = null;
		
		try {

			logger.debug("generateReport[0]: xmlFilePath = " + decodedXMLFlow);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(decodedXMLFlow);
			
			Document document = JRXmlUtils.parse(bais);
			
			reportParams.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, document);
			reportParams.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, datePattern);
			reportParams.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, numberPattern);
			//reportParams.put(JRXPathQueryExecuterFactory.XML_LOCALE, xmlLocale);
			
			logger.debug("generateReport[1]: jrDataSource = " + document);

			JasperPrint jasperPrint = generateAndFillJasperPrint();

			logger.debug("generateReport[2]: jasperPrint = " + jasperPrint);

			reportFlow = reportExporter.exportReport(jasperPrint);

		} catch (JRException e) {

			logger.error("generateReport[3]:", e);

		} catch (FileNotFoundException e1) {

			logger.error("generateReport[4]:", e1);

		}

		logger.debug("generateReport[5]: FINE");

		return reportFlow;

	}

	public byte[] generateReport(Collection dataCollection) {

		byte[] flusso = null;

		try {

			logger.debug("generateReport[0]: dataCollection = " + dataCollection);

			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(dataCollection);

			logger.debug("generateReport[1]: jrDataSource = " + jrDataSource);

			JasperPrint jasperPrint = generateAndFillJasperPrint(jrDataSource);

			logger.debug("generateReport[2]: jasperPrint = " + jasperPrint);

			flusso = reportExporter.exportReport(jasperPrint);

		} catch (JRException e) {

			logger.error("generateReport[3]:", e);

		} catch (FileNotFoundException e1) {

			logger.error("generateReport[4]:", e1);

		}

		logger.debug("generateReport[5]: FINE");

		return flusso;

	}

	public Map<String, Object> getReportParams() {
		return reportParams;
	}

	public void setReportParams(Map<String, Object> map) {
		reportParams = map;
	}

}

package it.tasgroup.report.servlet;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.report.ReportManager;
import it.tasgroup.report.exporter.ReportExporterFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * @author pazzik
 *
 * TODO: aggiustare content subType in base all'estensione
 *
 */
public class ReportServlet extends HttpServlet{

	private static Logger logger = Logger.getLogger(ReportServlet.class.getName());

//	private static ResourceBundle configurationBundle = ResourceBundle.getBundle("config");
//	private static String configFileName = configurationBundle.getString("configuration.log4j.file");

	public static final String PDF_EXT = ReportExporterFactory.PDF_EXT;
	public static final String XLS_EXT = ReportExporterFactory.XLS_EXT;
	public static final String RTF_EXT = ReportExporterFactory.RTF_EXT;
	public static final String ZIP_EXT = "zip";

	public static final String REPORT_DATA_ATTR_NAME = "reportData";
	public static final String NOME_REPORT_ATTR_NAME = "nomeReport";
	public static final String NOME_FILE_DOWNLOAD_ATTR_NAME = "nomeFileDownload";
	public static final String EXT_ATTR_NAME = "ext";
	public static final String NOME_MODULO_ATTR_NAME = "nomeModulo";
	public static final String REPORT_PARAMETERS_ATTR_NAME = "reportParameters";

	public void init() {
		
		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("JasperReports/config.properties");
		String configFileName = cpl.getProperty("configuration.log4j.file");
		
		PropertyConfigurator.configure(configFileName);
		logger.info("Log4j inizializzato per il servizio di generazione dei report");
	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		{
			logger.debug("doGet[0]");
			generateReport(req,resp);
			logger.debug("doGet[1]");
		}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		{
			logger.debug("doPost[0]");
			generateReport(req,resp);
			logger.debug("doPost[1]");
  		}

  private void generateReport(HttpServletRequest req, HttpServletResponse resp) {
		logger.debug("generateReport[0] BEGIN REQUEST FOR URI "+req.getRequestURI()+" FROM "+req.getRemoteAddr());

		try{

			String file_name = (String) req.getAttribute(NOME_REPORT_ATTR_NAME);

			String ext = ((String)req.getAttribute(EXT_ATTR_NAME));

			String moduleName = ((String)req.getAttribute(NOME_MODULO_ATTR_NAME));

			logger.debug("generateReport[1]: reportFullName="+file_name+"."+ext);

			HashMap reportParametersMap = (HashMap) req.getAttribute(REPORT_PARAMETERS_ATTR_NAME);

			Collection reportData = (Collection) req.getAttribute(REPORT_DATA_ATTR_NAME);

			logger.debug("generateReport[3]: reportParametersMap="+reportParametersMap);

			ReportManager reportGenerator = new ReportManager(file_name,ext,reportParametersMap,moduleName);

			logger.debug("generateReport[4]: ReportManager="+reportGenerator);

			byte[] reportFlow = reportGenerator.generateReport(reportData);

			logger.debug("generateReport[5]: reportData="+reportData);

			req.setAttribute(DownloadServlet.DOWNLOAD_DATA,reportFlow);

			logger.debug("generateReport[6]");
			
			req.getRequestDispatcher("ReportDecorator").forward(req, resp);

		}catch(IOException e){

			logger.error("generateReport[7]",e);

		}catch(ServletException e){

			logger.error("generateReport[8]",e);
		}

		logger.debug("generateReport[9] END REQUEST FOR URI "+req.getRequestURI()+" FROM "+req.getRemoteAddr());
  }

}

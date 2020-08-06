package it.tasgroup.report.servlet;

import it.tasgroup.report.PrintableDocument;
import it.tasgroup.report.utility.PdfUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

//import com.lowagie.text.DocumentException;

/**
 * @author pazzik
 *
 */
public class ReportDecoratorServlet extends HttpServlet {
	
	private static Logger LOGGER = Logger.getLogger(ReportDecoratorServlet.class.getName());

	public void init() throws ServletException {
		LOGGER.debug("init[0]");
	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
			LOGGER.debug("doGet[0]");
			parseReport(req,resp);
			LOGGER.debug("doGet[1]");
	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
			LOGGER.debug("doPost[0]");
			parseReport(req,resp);
			LOGGER.debug("doPost[1]");
	}
	
	private void parseReport(HttpServletRequest req, HttpServletResponse resp) {
		
//		byte[] pdf = (byte[])req.getAttribute(DownloadServlet.DOWNLOAD_DATA);
//		
//		byte[] markedPdf = null;
		
		try {
// L'apposizione della filigrana è stata spostata nel servizio
// (vedi: DDPFacadeBean + DDPDTOBuilder) 			
// TODO:MINO ripulire ....
			
//			Collection<PrintableDocument> reportData = (Collection<PrintableDocument>) req.getAttribute(ReportServlet.REPORT_DATA_ATTR_NAME);
//			
//			PrintableDocument printableDoc = (PrintableDocument)reportData.iterator().next();
//			
//			if (printableDoc.needWatermark()){
//			
//				ResourceBundle bundle = ResourceBundle.getBundle("resources.PDC");
//				
//				String localizedStatus = printableDoc.getWatermarkText(bundle);
//			
//				markedPdf = PdfUtils.watermark(pdf, localizedStatus, 120, 0.1f, null, null, (float)Math.PI/4);
//				
//				req.setAttribute(DownloadServlet.DOWNLOAD_DATA,markedPdf);			
//			}
				
			
			req.getRequestDispatcher("ReportDownload").forward(req, resp);
			
//		} catch (DocumentException e) {		
//			LOGGER.error("Error on parseReport", e);
		} catch (IOException e) {
			LOGGER.error("Error on parseReport", e);
		} catch (ServletException e) {
			LOGGER.error("Error on parseReport", e);
		}
		
		
	}

}

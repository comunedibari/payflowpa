package it.tasgroup.report.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


/**
 * @author pazzik
 *
 * TODO: aggiustare contentType in base all'estensione?
 * TODO: generalizzare meccanismo di log?
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DownloadServlet extends HttpServlet{

	final static public String DOWNLOAD_DATA="downloadData";

	private static Logger logger = Logger.getLogger(DownloadServlet.class.getName());

	public void init() throws ServletException {
		logger.debug("init[0]");
	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
			logger.debug("doGet[0]");
			download(req,resp);
			logger.debug("doGet[1]");
	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
			logger.debug("doPost[0]");
			download(req,resp);
			logger.debug("doPost[1]");
  }

  private void download(HttpServletRequest req, HttpServletResponse resp) throws IOException{
	logger.debug("download[0]: BEGIN REQUEST FOR URI "+req.getRequestURI()+" FROM "+req.getRemoteAddr());

			int length = 0;
			byte[] buf = null;

			ServletOutputStream out = null;

			String file_name = (String)req.getAttribute(ReportServlet.NOME_FILE_DOWNLOAD_ATTR_NAME);
			String ext = (String)req.getAttribute(ReportServlet.EXT_ATTR_NAME);
			String fullDownloadFileName = file_name+"."+ext;

			String mimeType = MimeTypesMapper.getMimeType(ext);
			String contentDisposition = req.getParameter("download") != null && req.getParameter("download").equals("false") ? "inline" : "attachment";

			logger.debug("download[1]: mime_type="+mimeType);
			logger.debug("download[2]: file_name="+fullDownloadFileName);

			try{

				out = resp.getOutputStream();

				resp.setContentType(contentDisposition.equals("inline") ? "text/html" : mimeType);
				resp.addHeader("Content-Disposition", contentDisposition + "; filename=\""+fullDownloadFileName+"\"");
				resp.setHeader("Cache-Control", "public");

				// copy data

				buf = (byte[])req.getAttribute(DOWNLOAD_DATA);

//				logger.debug("download[3]: data="+buf);

				length = buf.length;
				
				resp.setContentLength(length);

				out.write(buf);

//				logger.debug("download[4]copy data["+length+"]:" + new String(buf));

				out.flush();

			}catch(Exception e){

				logger.error("download[5]",e);
			}
			finally{
				if (out != null)
					out.close();
			}

		logger.debug("download[6]: END REQUEST FOR URI "+req.getRequestURI()+" FROM "+req.getRemoteAddr());
  }

}

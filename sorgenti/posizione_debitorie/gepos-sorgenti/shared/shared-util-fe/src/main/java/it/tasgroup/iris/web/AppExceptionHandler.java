package it.tasgroup.iris.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class AppExceptionHandler
 */

public class AppExceptionHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String ERROR_CODE_KEY = "ERROR_CODE";
	public static final String ESITO_KEY      = "ESITO";
	public static final String DEFAULT_ESITO      = "ERROR";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processError(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processError(request, response);
	}
	
	private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String url_ko = (String) request.getSession().getAttribute("URL_KO");
		String charToAppend = url_ko.contains("?") ? "&" : "?";
		String errorCode = (String) request.getSession().getAttribute("ERROR_CODE");
		request.getSession().removeAttribute("ERROR_CODE");
		errorCode = (errorCode == null? "errore_generico" : errorCode);
		url_ko += charToAppend + "esito=ERROR";
		url_ko += "&error_code=" + errorCode;
		response.sendRedirect(url_ko);
	}

}

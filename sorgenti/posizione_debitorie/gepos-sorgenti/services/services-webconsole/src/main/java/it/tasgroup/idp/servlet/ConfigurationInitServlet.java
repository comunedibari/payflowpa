package it.tasgroup.idp.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import it.nch.fwk.fo.util.Tracer;

public class ConfigurationInitServlet extends HttpServlet {

	private static final long serialVersionUID = 2733488073500407729L;

	public void init() throws ServletException {
		try {
			Tracer.init();
		
		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

}



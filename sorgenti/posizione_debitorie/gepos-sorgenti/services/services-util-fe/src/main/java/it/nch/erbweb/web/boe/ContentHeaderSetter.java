package it.nch.erbweb.web.boe;

import it.nch.is.fo.CommonConstant;
import org.apache.struts.Globals;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ContentHeaderSetter {
		
		private static final String CONTENT_HEADER_RESOURCES = "messages.services.ContentHeaderBOEResources";
		
		public static void setHeaderProperties(HttpServletRequest request, String propertiesPrefix, String logoPrefix, boolean resetBreadcrumb){
		
		Locale locale = (Locale) request.getSession().getAttribute(Globals.LOCALE_KEY);
		
		ResourceBundle rb = ResourceBundle.getBundle(CONTENT_HEADER_RESOURCES, locale);

		String menuname = null;
		try {
			menuname = rb.getString(propertiesPrefix + ".menuname");
		} catch (MissingResourceException e) {
			// TODO: handle exception
		}
		
		String logoname = "";
		try {
			if (logoPrefix != null)
				logoname = rb.getString(logoPrefix + ".logoname");
			else
				logoname = rb.getString(propertiesPrefix + ".logoname");
		} catch (MissingResourceException e) {
			// TODO: handle exception
		}

		String pagename = null;
		try {
			pagename = rb.getString(propertiesPrefix + ".pagename");
		} catch (MissingResourceException e) {
			// TODO: handle exception
		}

		
		
		String breadcrumb = "";
		
		if (request.getParameter(CommonConstant.BREADCRUMB) != null) {
			
			if (pagename != null && !pagename.equals("")){
				//breadcrumb = request.getParameter(CommonConstant.BREADCRUMB) + " > " + pagename;
				breadcrumb = pagename;
			}else
				breadcrumb = request.getParameter(CommonConstant.BREADCRUMB);
			
		} else
			breadcrumb = pagename;		
		
		saveParameterOnRequest(request, resetBreadcrumb, menuname, logoname, pagename, breadcrumb);			
		
	}

	private static void saveParameterOnRequest(HttpServletRequest request, boolean resetBreadcrumb, String menuname, String logoname,
			String pagename, String breadcrumb) {
		request.setAttribute(CommonConstant.MENUNAME, menuname);
		request.setAttribute(CommonConstant.LOGONAME, logoname);
		request.setAttribute(CommonConstant.PAGENAME, pagename);

		if (!resetBreadcrumb)
			request.setAttribute(CommonConstant.BREADCRUMB, breadcrumb);
		else
			request.setAttribute(CommonConstant.BREADCRUMB, pagename);
	}
	
	private static void saveParameterOnSession(HttpServletRequest request, boolean resetBreadcrumb, String menuname, String logoname,
			String pagename, String breadcrumb) {
		request.getSession().setAttribute(CommonConstant.MENUNAME, menuname);
		request.getSession().setAttribute(CommonConstant.LOGONAME, logoname);
		request.getSession().setAttribute(CommonConstant.PAGENAME, pagename);

		if (!resetBreadcrumb)
			request.getSession().setAttribute(CommonConstant.BREADCRUMB, breadcrumb);
		else
			request.getSession().setAttribute(CommonConstant.BREADCRUMB, pagename);
	}
	
	
	public static void setHeaderProperties(HttpServletRequest request, String propertiesPrefix){
		setHeaderProperties(request,propertiesPrefix,null,false);
	}
	
	public static void setHeaderProperties(HttpServletRequest request, String propertiesPrefix, String logoPrefix){
		setHeaderProperties(request,propertiesPrefix,logoPrefix,false);
	}
	
	public static void setHeaderPropertiesSession(HttpServletRequest request, String propertiesPrefix, String logoPrefix) {
		setHeaderPropertiesSession(request,propertiesPrefix,logoPrefix,false);
	}
	
	public static void removeHeaderPropertiesSession(HttpServletRequest request) {
		removeParameterOnSession(request);
	}
	

	
 //SONO COSTRETTO A COPIARE IL METODO PERCHE' VIENE RICHIAMATO IL METODO PIU' INTERNO E NON QUELLI ESTERNI DICHIARATI SOPRA	
 public static void setHeaderPropertiesSession(HttpServletRequest request, String propertiesPrefix, String logoPrefix, boolean resetBreadcrumb){
		
		Locale locale = (Locale) request.getSession().getAttribute(Globals.LOCALE_KEY);
		
		ResourceBundle rb = ResourceBundle.getBundle(CONTENT_HEADER_RESOURCES, locale);

		String menuname = null;
		try {
			menuname = rb.getString(propertiesPrefix + ".menuname");
		} catch (MissingResourceException e) {
			// TODO: handle exception
		}
		
		String logoname = "";
		try {
			if (logoPrefix != null)
				logoname = rb.getString(logoPrefix + ".logoname");
			else
				logoname = rb.getString(propertiesPrefix + ".logoname");
		} catch (MissingResourceException e) {
			// TODO: handle exception
		}

		String pagename = null;
		try {
			pagename = rb.getString(propertiesPrefix + ".pagename");
		} catch (MissingResourceException e) {
			// TODO: handle exception
		}

		
		
		String breadcrumb = "";
		
		if (request.getParameter(CommonConstant.BREADCRUMB) != null) {
			
			if (pagename != null && !pagename.equals("")){
				//breadcrumb = request.getParameter(CommonConstant.BREADCRUMB) + " > " + pagename;
				breadcrumb = pagename;
			}else
				breadcrumb = request.getParameter(CommonConstant.BREADCRUMB);
			
		} else
			breadcrumb = pagename;		
		
		saveParameterOnSession(request, resetBreadcrumb, menuname, logoname, pagename, breadcrumb);			
		
	}
 
 private static void removeParameterOnSession(HttpServletRequest request) {
	 request.getSession().removeAttribute(CommonConstant.MENUNAME);
	 request.getSession().removeAttribute(CommonConstant.LOGONAME);
	 request.getSession().removeAttribute(CommonConstant.PAGENAME);
	 request.getSession().removeAttribute(CommonConstant.BREADCRUMB);
 }

}

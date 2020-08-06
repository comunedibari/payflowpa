/*
 * Created on 28-mar-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.fwk.fo.web.util;

import it.nch.fwk.fo.action.FrameworkAction;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.util.Tracer;
import it.nch.fwk.fo.web.menu.MenuCreator;
import it.nch.fwk.fo.web.menu.PathRepository;
import it.nch.fwk.fo.web.session.WebSession;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author EE10057
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WebUtil {
	
	/**
	 * Traccia la request
	 * @param request
	 */
	public static void traceRequest(HttpServletRequest request){
		Tracer.debug(WebUtil.class.getName(),"traceRequest","BEGIN", null);
		String name = null;
		Object obj = null;
		Enumeration en =  request.getAttributeNames();
		while (en.hasMoreElements()){
			name = (String)en.nextElement();
			obj = request.getAttribute(name);
			Tracer.debug(WebUtil.class.getClass().getName(),"traceRequest",name + "= " + obj, null);
		}
		Tracer.debug(WebUtil.class.getName(),"traceRequest","END", null);
	}
	
	/**
	 * Traccia la session
	 * @param request
	 */
	public static void traceSession(HttpServletRequest request){
		Tracer.debug(WebUtil.class.getName(),"traceSession","BEGIN", null);
		HttpSession session= request.getSession(false);
		if(session != null){
			String name = null;
			Object obj = null;
			Enumeration en =  session.getAttributeNames();
			while (en.hasMoreElements()){
				name = (String)en.nextElement();
				obj = request.getSession().getAttribute(name);
				Tracer.debug(WebUtil.class.getName(),"traceSession",name + "= " + obj, null);
			}
		}
		Tracer.debug(WebUtil.class.getName(),"traceSession","END", null);
	}
	
	/**
	 * traccia un oggetto generico
	 * @param obj
	 */
	public static void traceObject(Object obj){
		Tracer.debug(WebUtil.class.getName(),"traceObject","BEGIN", null);
		if(obj == null){
			Tracer.debug(WebUtil.class.getName(),"traceObject","CURRENT OBJECT IS NULL", null);
			return;
		}
		Tracer.debug(WebUtil.class.getName(),"traceObject",obj + " not null field list", null);
		String prefix = "get";
		Class c = obj.getClass();
		Method[] publicMethods = c.getMethods();
		Class[] paramsType = null;
		String methodName = null;
		for (int i = 0; i < publicMethods.length; i++) {
			methodName = publicMethods[i].getName();
			if(methodName.startsWith(prefix) && !methodName.equalsIgnoreCase("getClass")){
				try{
					Method method = obj.getClass().getMethod(methodName, paramsType);
					paramsType = publicMethods[i].getParameterTypes();
					Object result = method.invoke(obj, paramsType);
					if(result != null)
						Tracer.debug(WebUtil.class.getName(), "traceObject", methodName + "= " + result.toString());
				}
				catch(NoSuchMethodException nsmEx){}
				catch(Exception ex){
					Tracer.debug(WebUtil.class.getName(), "traceObject", "Exception: " + ex);
				}
			}	
		}
		Tracer.debug(WebUtil.class.getName(),"traceObject","END", null);
	}
	
	
	public Object getObjectFromList(int index, ArrayList list){
		Tracer.debug(getClass().getName(),"getObjectFromList","BEGIN",null);
		Object obj = null;
		Tracer.debug(getClass().getName(),"getObjectFromList","list= " + list,null);
		if(list.get(index) != null)
			obj = list.get(index);
		Tracer.debug(getClass().getName(),"getObjectFromList","obj["+ index + "]= " + obj, null);
		Tracer.debug(getClass().getName(),"getObjectFromList","END",null);
		return obj;
	}

	public static FrontEndContext getLocatedFrontEndContext(
			HttpServletRequest httpServletRequest) {
		
		FrontEndContext fec = locateFrontEndContext(httpServletRequest, getWebSession(httpServletRequest).getContext());
		if(fec!=null && httpServletRequest!=null){
			String remoteAddress = getRemoteAddr(httpServletRequest);
			if(!"-".equals(remoteAddress))
				fec.setIpAddress(remoteAddress);
		}
		return fec;
	}

	public static FrontEndContext locateFrontEndContext(HttpServletRequest httpServletRequest, FrontEndContext fec){
		
		PathRepository path = null;
		if (getWebSession(httpServletRequest).getSessionElement("menuCreator") != null){
			path =  ((MenuCreator) getWebSession(httpServletRequest).getSessionElement("menuCreator")).getPath();
			fec.setPathMenuCorrente(path.getPathCode());
		}	
		return fec;
	}
	
	public static String getRemoteAddr(HttpServletRequest httpServletRequest) {
        String remoteAddr = "-";
        
        if (httpServletRequest.getHeader("X-Forwarded-For") != null)
        	remoteAddr = httpServletRequest.getHeader("X-Forwarded-For");
        
        else if (httpServletRequest.getHeader("$WSRH") != null)
        	remoteAddr = httpServletRequest.getHeader("$WSRH");
        
        else if (httpServletRequest.getHeader("$WSRA") != null)
        	remoteAddr = httpServletRequest.getHeader("$WSRA");
        
        else remoteAddr = httpServletRequest.getRemoteAddr();
        
        return remoteAddr;
	}
	
	public static WebSession getWebSession(HttpServletRequest httpServletRequest) {

		HttpSession httpSession = httpServletRequest.getSession();
		WebSession webSession = (WebSession) httpSession
				.getAttribute(FrameworkAction.WEB_SESSION_PROPERTY);

		if (webSession == null) {
			webSession = new WebSession();
			httpSession.setAttribute(FrameworkAction.WEB_SESSION_PROPERTY, webSession);
		}

		return webSession;
	}	
	/*
	public static void traceObject1(Object obj){
		Tracer.debug(WebUtil.class.getName(),"traceObject","BEGIN", null);
		Tracer.debug(WebUtil.class.getName(),"traceObject",obj + " not null field list", null);
		String prefix = "get";
		Class c = obj.getClass();
		Method[] publicMethods = c.getMethods();
		Class[] paramsType = null;
		String methodName = null;
		for (int i = 0; i < publicMethods.length; i++) {
			methodName = publicMethods[i].getName();
			if(methodName.startsWith(prefix) && !methodName.equalsIgnoreCase("getClass")){
				try{
					Method method = obj.getClass().getMethod(methodName, paramsType);
					paramsType = publicMethods[i].getParameterTypes();
					Object result = method.invoke(obj, paramsType);
					if(result != null)
						Tracer.debug(WebUtil.class.getName(), "traceObject", methodName + "= " + result.toString());
				}
				catch(NoSuchMethodException nsmEx){}
				catch(Exception ex){
					Tracer.debug(WebUtil.class.getName(), "traceObject", "Exception: " + ex);
				}
			}	
		}
		Tracer.debug(WebUtil.class.getName(),"traceObject","END", null);
	}
	*/
}

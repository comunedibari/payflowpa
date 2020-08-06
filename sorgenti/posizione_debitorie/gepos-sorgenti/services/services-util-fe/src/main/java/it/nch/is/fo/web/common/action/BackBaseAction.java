package it.nch.is.fo.web.common.action;

import it.nch.is.fo.web.FrontEndConstant;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.util.DefaultRequestHelper;
import org.displaytag.util.Href;

/**
 * Base Action for struts based back problematic. Evry action, who wants to join
 * the back must be inherited from this site.
 * 
 * For detail please show
 * {@link "http://www.manfred-wolff.de/struts/articles/back.html"}.
 * 
 * @author Manfred Wolff
 */
public abstract class BackBaseAction extends Action {

	// Pathes which been saved on the stack. You have at maximum
	// MAXBUF backs, you can do. There is one stack per session, so
	// the stack may not be synchronized
	private static final String BACKBUFFER = "BACKBUFFER";

	// Maximal buffer entries of the stack.
	private static int MAXBUF = 10;
	
	/**
	 * Pushs a value onto the stack. If there is an overflow (> MAXBUF) the
	 * first entry will be deleted.
	 * 
	 * @param entry
	 *            The entry that will push onto the stack
	 * @param request
	 *            The currently request
	 */
	public static synchronized void push(HttpServletRequest request, String entry) {

		List<String> buffer = (List<String>) request.getSession().getAttribute(BACKBUFFER);

		// Create a buffer for this session
		if (buffer == null) {
			buffer = new ArrayList<String>();
			request.getSession().setAttribute(BACKBUFFER, buffer);
		}

		// Removes the first entry if necessary
		if (buffer.size() == MAXBUF) {
			buffer.remove(0);
		}

		//check browser back button and page reload (F5)
		if (buffer.size() > 1){
			//first retrieve last page
			String reload = buffer.remove(buffer.size() - 1);
			//second retrieve back button page
			String backButton = buffer.remove(buffer.size() - 1);
			
			//if back button page not equal current entry,NO BACK BUTTON submitted, re-add the not back button to stack
			//if the current request is equal to back button, only the current request is saved
			if (!backButton.equals(entry)){
				buffer.add(backButton);
			
				//if previous page not equal current entry, NO RELOAD submitted, re-add the not reload page
				if (!reload.equals(entry)){
					buffer.add(reload);
				}
			}
		}
		
		
		buffer.add(entry);
	}
	
	public static synchronized String getLastPageURL(HttpServletRequest request) {
		
		List<String> buffer = (List<String>) request.getSession().getAttribute(BACKBUFFER);
		
		return buffer.get(buffer.size() - 1);
		
	}

	/**
	 * Pops a value from the stack. Normally the last entry is the entry of the
	 * action, that call the back functionality. So you have to throw the last
	 * entry away and go two positions back in the stack. Maybe there are
	 * situation, you will get the last entry. In this case you have to set
	 * twice to false.
	 * 
	 * @param request
	 *            The currently request
	 * @param twice
	 *            If twice is true, the last entry will be deleted.
	 * @return The last String pushed onto the stack
	 * @throws IllegalStateException
	 *             if the stack is empty
	 * @todo I don't know, if thats a good behavior
	 */
	public static synchronized String pop(HttpServletRequest request, boolean twice) {

		List<String> buffer = (List<String>) request.getSession().getAttribute(BACKBUFFER);

		if (buffer == null)
			throw new IllegalStateException("Pop is not possible (no buffer available).");

		if ((buffer.size() < 2) && (twice == true))
			throw new IllegalStateException("Pop is not possible (stack is empty)");

		if (buffer.size() == 0)
			throw new IllegalStateException("Pop is not possible (stack is empty)");

		if (twice == false) {
			//BackBaseAction.appendToFile((String) buffer.get(buffer.size() - 1),"D:/pop.txt",cntPop++);
			return buffer.remove(buffer.size() - 1);
		} else {
			buffer.remove(buffer.size() - 1);
			//BackBaseAction.appendToFile((String) buffer.get(buffer.size() - 1),"D:/pop.txt",cntPop++);
			return buffer.remove(buffer.size() - 1);

		}

	}

	/**
	 * Returns true, if back is possible, false otherwise.
	 * 
	 * @param request
	 * @return
	 */
	public static synchronized boolean isBackPossible(HttpServletRequest request) {

		List<String> buffer = (List<String>) request.getSession().getAttribute(BACKBUFFER);

		if (buffer == null)
			return false;

		if (buffer.size() < 2)
			return false;

		return true;

	}

	/**
	 * Abstract method. Inherited classes may only call <code>doExecute</code>
	 * and not <code>execute()</code>!
	 * 
	 * @param mapping
	 *            The ActionMapping class of this call.
	 * @param form
	 *            The associated ActionForm class
	 * @param request
	 *            The Request
	 * @param response
	 *            The Response
	 * @return Must return an ActionForward
	 * @throws Exception
	 * @since 1.0
	 */
	public abstract ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * Original method from the struts-framework. I was made final, so inherited
	 * classes are not able to overwrite this original method.
	 * 
	 * @param mapping
	 *            The ActionMapping class of this call.
	 * @param form
	 *            The associated ActionForm class
	 * @param request
	 *            The Request
	 * @param response
	 *            The Response
	 * @return Must return an ActionForward
	 * @throws Exception
	 * @since 1.0
	 */
	public final ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		BackBaseAction.register(request);
		return doExecute(mapping, form, request, response);
	}

	
	/**
	 * Registers a request to take care of the back mechanism
	 * 
	 * @param request
	 *            The Request
	 */
	public static synchronized void register(HttpServletRequest request) {
		String query = calculateQueryString(request);
		//saveRequestQueryToSession(query, request);
		//removeProviousRequest(request);
		int numToRemove = getBackPageNumber(request);
		if (numToRemove > 0){
			if (numToRemove >1){
				for(int i=0; i < numToRemove - 1; i++)
					BackBaseAction.pop(request, false);
			}
		}
		else{
			BackBaseAction.push(request, query);
		}
	}

	public static synchronized void register(HttpServletRequest request, HttpServletResponse response) {
		String query = calculateQueryString(request, response);
		//saveRequestQueryToSession(query, request);
		//removeProviousRequest(request);
		int numToRemove = getBackPageNumber(request);
		if (numToRemove > 0){
			if (numToRemove >1){
				for(int i=0; i < numToRemove - 1; i++)
					BackBaseAction.pop(request, false);
			}
		}
		else{
			BackBaseAction.push(request, query);
		}
	}

//	private static void saveRequestQueryToSession(String query,HttpServletRequest request){
//		int backTo = getBackPageNumber(request);
//		if (backTo = 0)
//		if (backTo != null)
//			request.getSession().setAttribute(backTo,query);
//		
//	}
	
	private static void removeProviousRequest(HttpServletRequest request){
		int numToRemove = getBackPageNumber(request);
		
		for(int i=0; i < numToRemove; i++)
			BackBaseAction.pop(request, false);
	}
	
	private static int getBackPageNumber(HttpServletRequest request){
		int retNum = 0;
		String backTo= (String)request.getAttribute(FrontEndConstant.BACK_TO_PAGE_ATTRIBUTE);
		try{
			retNum = Integer.parseInt(backTo);
		}
		catch(Exception e){
			retNum = 0;
		}
		return retNum;
	}
	/**
	 * Calculate the query String out of the Reqeust.
	 * 
	 * @param request
	 *            The actually request
	 * @return The calculated string.
	 */
	private static String calculateQueryString(HttpServletRequest request) {

		String sourceURL = null;

		// The alternative Methode request.getRequestURL() has the side-effect
		// to do an endless loop, so we must stay by this deprecated method.
		StringBuffer URL = javax.servlet.http.HttpUtils.getRequestURL(request);

		String queryString = request.getQueryString();
		if ((queryString != null) && (queryString.length() > 0)) {
			URL.append("?");
			URL.append(queryString);
		}
		sourceURL = URL.toString();
		return sourceURL;
	}

	private static String calculateQueryString(HttpServletRequest request, HttpServletResponse response) {

//		String sourceURL = null;
//		DefaultRequestHelper objDRH = new DefaultRequestHelper(request, response);
//		Href href = objDRH.getHref();
//		sourceURL = getHostPartOfURL(request) + href.toString();
//		sourceURL = replace(sourceURL, "amp;", "");
		
//		String queryString = request.getQueryString();
//		String sourceURL2 = request.getRequestURL()+(queryString!=null ? "?"+queryString : "");	
		
		String addOnParameter  = "";
		//POST DATA
	    try {
	        java.util.HashMap hashMap = (java.util.HashMap)request.getParameterMap();
	        java.util.Iterator iterator = hashMap.keySet().iterator();
	        int i = 1;
	        while( iterator.hasNext() ){
                String key = (String)iterator.next();                
                addOnParameter +=  key + "=" + URLEncoder.encode(request.getParameter(key),"UTF-8") + "&";
                i++;
	        }        
	        if (i>1) {
				addOnParameter = addOnParameter.substring(0, addOnParameter.length()-1);
			}
	     } catch (Exception e) { 
	    	 e.printStackTrace(); 
	     }
	    
	    String sourceURL3 = request.getRequestURL()+( !"".equals(addOnParameter) ? "?"+addOnParameter : "");
	    
	    
		return sourceURL3;
	}

	@Deprecated
	private static String getHostPartOfURL(HttpServletRequest request) {

		String strContextPart = request.getContextPath();
		String strURL = javax.servlet.http.HttpUtils.getRequestURL(request).toString();		
		String strHostPart = strURL.substring(0, strURL.indexOf(strContextPart));
		
		String uri = request.getScheme() + "://" +
	             request.getServerName() + 
	             (
	            	"http".equals(request.getScheme()) && request.getServerPort() == 80 
	             || "https".equals(request.getScheme()) && request.getServerPort() == 443 ? "" : ":" + request.getServerPort() ) +
	             request.getRequestURI();
		
		return strHostPart;
	}

	protected static String replace(String str, String pattern, String replace) {
		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer();

		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}
	
	public static void ignoreNextURLInNavigationHistory(HttpServletRequest request){
		request.setAttribute(FrontEndConstant.BACK_TO_PAGE_ATTRIBUTE, FrontEndConstant.BACK_FROM_SAME_PAGE);
	}


}

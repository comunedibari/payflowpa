package it.nch.fwk.fo.web;

import it.nch.fwk.fo.util.HostIdentifier;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;


/**
* <br>
* $Author: CattaniA $ 
* <br>
* $Revision: 1.1.1.1 $
* <br>
* @author liacono
* <br>
* Estensione del Controller di Struts
*
**/
public class ApplicationBaseServlet extends ActionServlet {
	
	/* Logger object */
	private static final Logger logger = Logger.getLogger(ApplicationBaseServlet.class);
	
	/* Nome attributo ove viene memorizzato il flag di esecuzione programmi portal */
	
	/* Nome attributo ove viene memorizzato il flag di on/off del meccanismo di controllo accessi */
	private static final String URL_ACL_FLAG = "urlaclflag";
	
	/* Nome attributo di memorizzazione hostname corrente */
	private static final String CTRL_HOSTNAME = "controllerhostname";
	
	/* Nome attributo di memorizzazione prefisso istanza internet/intranet */
	private static final String CTRL_INSTANCE_PREFIX = "controllerinstanceprefix";
	
    /* Nome della pagina di attesa */
    private static final String WAITING_PAGE = "/wait/wait.jsp";
    	
	/* Contesto servlet controller */
	private ServletContext controllerCtx;
	
	/**
	 * @see javax.servlet.GenericServlet#init()
	 */
	public final void init() throws ServletException {
		
		/* recupero contesto del controller */		
		controllerCtx = getServletContext();
		
		/* nome istanza */
		//String istName = null;
		String istName = "DEMO_eB.WEB";

		initializeFrameworkServlet();

		synchronized(controllerCtx) {
 			
			/* lettura flag on/off meccanismo di accesso controllo */
			if (controllerCtx.getAttribute(URL_ACL_FLAG) == null) {
			  // controllerCtx.setAttribute(URL_ACL_FLAG,  Boolean.valueOf(Configurations.getBooleanProperty( BaseConfigSources.WEB, "url.acl")));
				controllerCtx.setAttribute(URL_ACL_FLAG, new Boolean(false));
			}
				     			
			/* nome istanza */	
			//istName = Configurations.getStringProperty(BaseConfigSources.WEB,"instancename","intranet");     				     				     				     				     				     			
								     					     			
		     			
			/* memorizzo hostname corrente */
			controllerCtx.setAttribute(CTRL_HOSTNAME,HostIdentifier.getHostname());	     	
			
			/* memorizzo nome istanza */
			controllerCtx.setAttribute(CTRL_INSTANCE_PREFIX,
								       istName.trim().toUpperCase());
		}
		
		super.init();                
	}

     protected void initializeFrameworkServlet() {
         //NOP
     }
	 
	/**
	 *  recupero servlet mapping 
	 */
	public final String getServletMapping() {
		return servletMapping;
	}		
	
	/**
	 * Recupero nome dell'host corrente di esecuzione controller
	 * @return String
	 */
	public final String getControllerHostname() {
		return (String)controllerCtx.getAttribute(CTRL_HOSTNAME);
	}
	
	/**
	 * Recupero nome istanza
	 * @return String
	 */
	public final String getInstanceName() {
		return (String)controllerCtx.getAttribute(CTRL_INSTANCE_PREFIX);
	}
    
	/**
	 * Method forward.
	 * @param request
	 * @param response
	 * @param path
	 * @throws ServletException
	 * @throws IOException
	 */
    protected void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(WAITING_PAGE);
        dispatcher.forward(request, response);
    }
    
    /**
	 *  Metodo per recupero flag di on/off meccanismo ACL
	*/
	public final boolean aclEnabled() {
		return ((Boolean)controllerCtx.getAttribute(URL_ACL_FLAG)).booleanValue();
	}
	
}

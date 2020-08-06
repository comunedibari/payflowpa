/*
 *
 */
package it.nch.fwk.fo.web.action;

import it.nch.fwk.fo.action.FrameworkAction;
import it.nch.fwk.fo.base.handler.profile.UserContextInterface;
import it.nch.fwk.fo.common.constants.CommonConstants;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOInfo;
import it.nch.fwk.fo.exceptions.FrontEndException;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.profilo.menu.MenuNode;
import it.nch.fwk.fo.util.Tracer;
import it.nch.fwk.fo.web.action.cm.CMActionAdapter;
import it.nch.fwk.fo.web.login.LoginProcessor;
import it.nch.fwk.fo.web.menu.MenuCreator;
import it.nch.fwk.fo.web.menu.PathRepository;
import it.nch.fwk.fo.web.resources.util.CommonContainerForm;
import it.nch.fwk.fo.web.session.WebSession;
import it.nch.fwk.fo.web.util.WebUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.PropertyMessageResources;

/**
 * Estensione astratta di org.apache.struts.actions.DispatchAction da cui discendono
 * tutte le altre Action dell'applicazione.
 *
 * @author GardiniG
 *
 */
public abstract class CorporateAction extends FrameworkAction {

	// --------------------------------------------------------- Instance
	// Variables

	/**
	 * Logger for this class
	 */
	//private static final Logger logger = Logger.getLogger(CorporateAction.class);

	// --------------------------------------------------------- Methods

	/**
	 * Metodo execute è stato reso simile all'execute della NIBAction in quanto
	 * alcune operazioni dovranno essere eseguite anche lato IOLI2
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	// public final ActionForward execute(ActionMapping actionMapping,
	// ActionForm actionForm, HttpServletRequest httpServletRequest,
	// HttpServletResponse httpServletResponse) throws Exception {
	//
	// // TODO Controllare, magari con una persona del team NIB, quali di
	// // queste operazioni per IOLI2 è superflua, tenendo presente che la
	// // chiamata al metodo 'innerExecute' è stata sostituita con la scrittura
	// // 'pulita' dello stesso. Verificare se è il caso di scrivere, nella
	// // FrameworkAction, un metodo astratto 'innerExecute' e portare le parti
	// // comuni in un altro metodo non astratto che a quel punto sarà
	// // richiamato nei metodi 'execute' della CorporateAction e NIBAction
	//
	// ActionForward actionForward = innerExecute(actionMapping, actionForm,
	// httpServletRequest, httpServletResponse);
	//
	// return actionForward;
	// }
	/**
	 * Method retrieveFields
	 *
	 * @param object
	 * @param actionForm
	 */
	protected void retrieveFields(Object object, ActionForm actionForm) {
		try {
			BeanUtils.copyProperties(object, actionForm);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method releaseFields
	 *
	 * @param actionForm
	 * @param object
	 */
	protected void releaseFields(ActionForm actionForm, Object object) {
		try {
			BeanUtils.copyProperties(actionForm, object);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method getWebSession
	 *
	 * @param httpServletRequest
	 * @return WebSession
	 */
	protected WebSession getWebSession(HttpServletRequest httpServletRequest) {

//		String WEB_SESSION = InterfaceUtil.WEB_SESSION;
//		HttpSession httpSession = httpServletRequest.getSession();
//		WebSession webSession = (WebSession) httpSession
//				.getAttribute(FrameworkAction.WEB_SESSION_PROPERTY);
//
//		if (webSession == null) {
//			webSession = new WebSession();
//			httpSession.setAttribute(FrameworkAction.WEB_SESSION_PROPERTY, webSession);
//		}
//
//		return webSession;
		return WebUtil.getWebSession(httpServletRequest);
	}

	/**
	 * Method getFrontEndContext
	 *
	 * @param httpServletRequest
	 * @return FrontEndContext
	 */
	protected FrontEndContext getFrontEndContext(
			HttpServletRequest httpServletRequest) {

		FrontEndContext fec = getWebSession(httpServletRequest).getContext();
		if(fec!=null && httpServletRequest!=null){
			String remoteAddress = this.getRemoteAddr(httpServletRequest);
			if(!"-".equals(remoteAddress))
				fec.setIpAddress(remoteAddress);
		}
		return fec;
	}

	/**
	 * Crea una nuova WebSession, su cui imposta il frontEndContext in ingresso.
	 * Salva nella sessione HTTP questo oggetto WebSession.
	 *
	 * @param httpServletRequest la HttpServletRequest
	 * @param frontEndContext il FrontEndContext
	 */
	protected void setFrontEndContext(HttpServletRequest httpServletRequest, FrontEndContext frontEndContext) {

		WebSession webSession = new WebSession();
		webSession.setContext(frontEndContext);
		httpServletRequest.getSession().setAttribute(FrameworkAction.WEB_SESSION_PROPERTY, webSession);
	}

	/*
	 * Method locateFrontEndContext
	 *
	 * @param httpServletRequest
	 * @param frontEndContext
	 *
	 * Setta il path di navigazione nel FrontendContext
	 */
	protected FrontEndContext locateFrontEndContext(HttpServletRequest httpServletRequest, FrontEndContext fec){

		return WebUtil.locateFrontEndContext(httpServletRequest, fec);
	}

	protected FrontEndContext getLocatedFrontEndContext(HttpServletRequest httpServletRequest) {
		return WebUtil.getLocatedFrontEndContext(httpServletRequest);
	}

	/**
	 * Method getLoginProcessor
	 *
	 * @return LoginProcessor
	 */
	protected LoginProcessor getLoginProcessor() {
		return null;
	}

	protected ActionForward innerExecute(ActionMapping actionMapping,
			ActionForm actionForm, ActionMessages actionMessages,
			ActionErrors actionErrors, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			UserContextInterface userContextInterface) throws Exception {

		ActionForward actionForward = null;
		CMActionAdapter actionAdapter = initializeContentManagement(
				httpServletRequest, actionMapping);
		UserContextInterface userActionContext = null;

		if (userContextInterface != null) {
			userActionContext = (UserContextInterface) (userContextInterface
					.clone());
		} else {
			//logger.error("lo user context e' 'null' Eseguo action "
					//+ actionMapping.getPath());
		}

		try {
			ActionForward blockForward = checkBlockedAction(userActionContext,
					actionMapping, actionForm, httpServletRequest);

			if (blockForward == null) {
				httpServletRequest.setAttribute("actionErrors", actionErrors);
				httpServletRequest.setAttribute("userActionContext",
						userActionContext);

				FrontEndContext fec = getLocatedFrontEndContext(httpServletRequest);
				if (fec !=null){
					String currentFunction = httpServletRequest.getParameter("FUNCTIONCODESELECTED");

					if (currentFunction!=null){
						fec.setPathMenuCorrente(currentFunction);
						httpServletRequest.getSession().setAttribute("FUNCTIONCODESELECTED",currentFunction);

					}
				}
				actionForward = upperExecute(actionMapping, actionForm,
						httpServletRequest, httpServletResponse);

				DTOInfo dto=(DTOInfo)httpServletRequest.getAttribute("DTO");
				if(dto!=null && dto.getSeverity()==6){
					Tracer.error(getClass().getName(),"inneExecute","return globalError",null);
					return actionMapping.findForward("globalError");
				}//return (new ActionForward(actionMapping.getInput()));
				if(dto!=null && dto.getSeverity()==3){
					Tracer.error(getClass().getName(),"innerExecute","return input error",null);

					if(actionMessages==null)
						actionMessages= new ActionMessages();
					//default value (must be set in ApplicationResources.properties)
					String key="error.global.runtime";
					if(dto.getCode()!=null && !dto.getCode().equals(""))
						key = dto.getCode();

					ActionMessage msg = new ActionMessage(key);
					actionMessages.add("error", msg);
					saveErrors(httpServletRequest, actionMessages);
					return (new ActionForward(actionMapping.getInput(),true));
				}
			} else {
				actionForward = blockForward;
			}

			userContextInterface = mergeContext(userContextInterface,
					userActionContext);
		} catch (FrontEndException e) {
			httpServletRequest.setAttribute("exception", e);
			return actionMapping.findForward("error");
		} catch (Throwable e) {
			e.printStackTrace();
			return actionMapping.findForward("globalError");
		}

		actionAdapter.setActionForward(httpServletRequest, actionMapping,
				actionForward);

		return actionForward;
	}

	/*
	 * Metodo che controlla che applicazione e area passati siano accessibili all'utente, in caso affermativo
	 * viene costruito di conseguenza il pathRepositary
	 */
	protected boolean isValidPath(MenuCreator menuCreator, PathRepository path){

		Tracer.info(getClass().toString(),"isValidPath","inizio",null);
		boolean valid = false;

		MenuNode menuNode;
		Iterator iteratorAppl = menuCreator.getMenuApplicazioni().iterator();
		Iterator iteratorAree;
		Iterator iteratorServ;
		Iterator iteratorFunz;

		while (iteratorAppl.hasNext() && !valid){
			menuNode = (MenuNode) iteratorAppl.next();
			if(path.getApplicationCode() == null)
				path.setApplicationCode(menuNode.getCode());
			if(menuNode.getCode().equals(path.getApplicationCode())){
				if (Tracer.isDebugEnabled(getClass().getName())){
					Tracer.debug(getClass().toString(),"isValidPath","Applicazione trovata: " + menuNode.getCode(),null);
					Tracer.debug(getClass().toString(),"isValidPath","Url: " + menuNode.getUrl(),null);
				}
				iteratorAree = menuNode.getChildren().iterator();
				while(iteratorAree.hasNext()&& !valid){
					menuNode = (MenuNode) iteratorAree.next();
					if (path.getAreaCode()== null)
						path.setAreaCode(menuNode.getCode());
					if (menuNode.getCode().equals(path.getAreaCode())){
						if (Tracer.isDebugEnabled(getClass().getName())){
							Tracer.debug(getClass().toString(),"isValidPath","Area trovata: " + menuNode.getCode(),null);
							Tracer.debug(getClass().toString(),"isValidPath","Url: " + menuNode.getUrl(),null);
						}
						if(path.getServiceCode() == null){
							valid = true;
						}
						else {
							iteratorServ = menuNode.getChildren().iterator();
							while(iteratorServ.hasNext()&& !valid){
								menuNode = (MenuNode) iteratorServ.next();
								if (path.getServiceCode().equals(menuNode.getCode())){
									if (Tracer.isDebugEnabled(getClass().getName())){
										Tracer.debug(getClass().toString(),"isValidPath","Servizio trovato: " + menuNode.getCode(),null);
										Tracer.debug(getClass().toString(),"isValidPath","Url: " + menuNode.getUrl(),null);
									}
									iteratorFunz = menuNode.getChildren().iterator();
									while(iteratorFunz.hasNext()&& !valid){
										menuNode = (MenuNode) iteratorFunz.next();
										if (path.getFunctionCode().equals(menuNode.getCode())){
											if (Tracer.isDebugEnabled(getClass().getName())){
												Tracer.debug(getClass().toString(),"isValidPath","Funzione trovata: " + menuNode.getCode(),null);
												Tracer.debug(getClass().toString(),"isValidPath","Url: " + menuNode.getUrl(),null);
											}
											valid = true;

										}
									}
								}
							}
						}
					}
				}
			}
		}
		if (Tracer.isDebugEnabled(getClass().getName()))
			Tracer.debug(getClass().toString(),"isValidPath","fine. Esito: " + valid,null);
		return valid;
	}

	protected void changePath(CommonContainerForm container, HttpServletRequest httpServletRequest)
	{
		WebSession webSession=getWebSession(httpServletRequest);
		MenuCreator menuCreator = (MenuCreator)webSession.getSessionElement("menuCreator");
		PathRepository path = menuCreator.getPath();

		String service=container.getService();
		container.setService("");
		String funct=container.getFunct();
		container.setFunct("");

		if(service!= null && !service.equals(""))
			path.setServiceCode(service);

		if(funct!= null && !funct.equals(""))
			path.setFunctionCode(funct);

		isValidPath(menuCreator,path);
	}


	protected String getMessagesResource(HttpServletRequest httpServletRequest, String key){

		Tracer.debug(this.getClass().getName(),"getResourcesMessage","BEGIN",null);
		String s = "";

		PropertyMessageResources resources = (PropertyMessageResources) httpServletRequest.getAttribute(CommonConstants.MESSAGES_RESOURCES);
		try{
			s = resources.getMessage(key);
		}catch(Exception e){

		}

		if(s == null || s.length() == 0)
			s = key;
	    Tracer.debug(this.getClass().getName(),"getResourcesMessage","END",null);
	    return s;
	}

	protected String getMessagesResource(HttpServletRequest httpServletRequest, String key, String emergenzyLabel){

		Tracer.debug(this.getClass().getName(),"getResourcesMessage","BEGIN",null);
		String s = "";

		PropertyMessageResources resources = (PropertyMessageResources) httpServletRequest.getAttribute("org.apache.struts.action.MESSAGE");
		try{
			s = resources.getMessage(key);
		}catch(Exception e){}

		if(s == null || s.length() == 0)
			s = emergenzyLabel;
	    Tracer.debug(this.getClass().getName(),"getResourcesMessage","END",null);
	    return s;
	}

	/**
	 * Controlla che dal BusinessDelegate
	 * non siano arrivate informazioni
	 * su errori o warnin, se ciò avviene le possibili risposte sono solo 2
	 * True  nel caso non siano errori bloccanti e quindi Non si pù proseguire il flusso del chiamante,
	 * False nel caso di info o warning e quindi Si può proseguire il flusso delò chiamante
	 *
	 * @param objDTOCollection   -- Collezione di DTO del quale si deve analizzare la InfoList
	 * @param httpServletRequest -- Request al quale si deve restituire il valore dello Info
	 *
	 * @return
	 * 	True non proseguo il flusso per errori bloccanti,
	 *  False proseguo il flusso, le info non sono errori
	 *
	 * NB: Tale metododo non solleva una eccezione poichè lascia al chiamante
	 * la libertà di gestire il flusso senza dover gestire forzatamente una throw di una Exception
	 */
	protected boolean chkInfoList( DTOCollection objDTOCollection, HttpServletRequest httpServletRequest )
	{
		//
		if( objDTOCollection.getInfoList() == null )
		{
			//Caso in cui il Back-End non ha nulla da dire
			//proseguo nel flusso
			return false;
		}
		//
		httpServletRequest.setAttribute(CommonConstants.DTO,(DTOInfo)objDTOCollection.getInfoList().getInfoArray().get(0));
        //
        DTOInfo objDTOInfo =(DTOInfo)objDTOCollection.getInfoList().getInfoArray().get(0);
		//
		this.tracerSeverity(objDTOInfo);
		//
		if (
				((DTOInfo)objDTOCollection.getInfoList().getInfoArray().get(0)).getSeverity() == DTOInfo.INFO
				||
				((DTOInfo)objDTOCollection.getInfoList().getInfoArray().get(0)).getSeverity() == DTOInfo.WARNING
		   )
		{
			// Caso Info o Warning: proseguo fusso, tale tipo di info non provengono da eccezioni bloccanti
			return false;
		}
		// Se il processo è arrivato qui significa che le informazioni contenute provengono da errori bloccanti,
		// sono di tipo DTOInfo.ERROR, pertanto l'unica soluzione possibile è il blocco del flusso chiamnte
		//tale metodo.
		//
		return true;
		//
	}//end chkInfoList
	//
	/**
	 * Controlla che dal BusinessDelegate
	 * non siano arrivate informazioni
	 * su errori o warnin, se ciò avviene le possibili risposte sono solo 2
	 * True  nel caso non siano errori bloccanti e quindi Non si pù proseguire il flusso del chiamante,
	 * False nel caso di info o warning e quindi Si può proseguire il flusso delò chiamante
	 *
	 * @param objDTOCollection   -- DTO dal quale si deve analizzare la InfoList
	 * @param httpServletRequest -- Request al quale si deve restituire il valore dello Info
	 *
	 * @return
	 * 	True non proseguo il flusso per errori bloccanti,
	 *  False proseguo il flusso, le info non sono errori
	 *
	 * NB: Tale metododo non solleva una eccezione poichè lascia al chiamante
	 * la libertà di gestire il flusso senza dover gestire forzatamente una throw di una Exception
	 */
	protected boolean chkInfoList( DTO objDTO, HttpServletRequest httpServletRequest )
	{
		Tracer.debug(getClass().getName(),"chkInfoList","Inizio");
		//
		if( objDTO.getInfoList() == null )
		{
			//Caso in cui il Back-End non ha nulla da dire
			//proseguo nel flusso
			//
			Tracer.debug(getClass().getName(),"chkInfoList","Fine");
			//
			return false;
		}
		//
        httpServletRequest.setAttribute(CommonConstants.DTO,(DTOInfo)objDTO.getInfoList().getInfoArray().get(0));
        //
        DTOInfo objDTOInfo =(DTOInfo)objDTO.getInfoList().getInfoArray().get(0);
		//
		this.tracerSeverity(objDTOInfo);
		//
		if (
				((DTOInfo)objDTO.getInfoList().getInfoArray().get(0)).getSeverity() == DTOInfo.INFO
				||
				((DTOInfo)objDTO.getInfoList().getInfoArray().get(0)).getSeverity() == DTOInfo.WARNING
		   )
		{
			Tracer.debug(getClass().getName(),"chkInfoList","Fine");
			// Caso Info o Warning: proseguo fusso, tale tipo di info non provengono da eccezioni bloccanti
			return false;
		}
		// Se il processo è arrivato qui significa che le informazioni contenute provengono da errori bloccanti,
		// sono di tipo DTOInfo.ERROR, pertanto l'unica soluzione possibile è il blocco del flusso chiamnte
		//tale metodo.
		//
		Tracer.debug(getClass().getName(),"chkInfoList","Fine");
		return true;
		//
	}//end chkInfoList
	//
	private void tracerSeverity(DTOInfo objDTOInfo)
	{
		//
		Tracer.debug(getClass().getName(),"tracerSeverity"," DTOInfo - Message  " + objDTOInfo.getMessage()  );
		Tracer.debug(getClass().getName(),"tracerSeverity"," DTOInfo - Code 	" + objDTOInfo.getCode() 	 );
		Tracer.debug(getClass().getName(),"tracerSeverity"," DTOInfo - Severity " + objDTOInfo.getSeverity() );
		//
	}//end tracerSeverity
	//
	/**
	 *
	 * Metodo che sostituisce il chkInfoList, su un singolo DTO
	 * rilanciando una eccezione al posto della restituzione di un booleano
	 *
	 * @param objDTO
	 * @param httpServletRequest
	 * @throws Exception
	 */
	protected void chkInfoListWithError( DTO objDTO, HttpServletRequest httpServletRequest ) throws Exception
	{
		if(this.chkInfoList(objDTO, httpServletRequest))
		{
			throw new FrontEndException( ((DTOInfo)httpServletRequest.getAttribute(CommonConstants.DTO)).getMessage() ) ;
		}
	}//end chkInfoListWithError
	//
	/**
	 * Metodo che sostituisce il chkInfoList, su una collesione di DTO
	 * rilanciando una eccezione al posto della restituzione di un booleano
	 * @param objDTOCollection
	 * @param httpServletRequest
	 * @throws Exception
	 */
	protected void chkInfoListWithError( DTOCollection objDTOCollection, HttpServletRequest httpServletRequest ) throws Exception
	{
		if(this.chkInfoList(objDTOCollection, httpServletRequest))
		{
			throw new FrontEndException( ((DTOInfo)httpServletRequest.getAttribute(CommonConstants.DTO)).getMessage() ) ;
		}
	}//end chkInfoListWithError
	//


	public ActionForward dirigi(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {

		Tracer.debug(this.getClass().getName(),"dirigi","BEGIN", null);
		String directing= null;

		try
		{

			CommonContainerForm c=(CommonContainerForm)actionForm;
			directing=c.getDirecting();

		}
		catch (Exception e)
		{
			throw new FrontEndException(e);
		}
		if (Tracer.isDebugEnabled(getClass().getName()))
			Tracer.debug(this.getClass().getName(),"**************************************************************************************dirigi","END", null);
		return actionMapping.findForward(directing);
	}


	protected String testAbiAccentratore(String abiAss, Collection abiAcc) throws Exception
	{
		String sonoAcc="";

		if(abiAss != null)
		{
			Iterator iterAbi = abiAcc.iterator();
			sonoAcc="false";

			while(iterAbi.hasNext())
			{
				if(abiAss.equalsIgnoreCase((String)iterAbi.next()))
				{
					sonoAcc="true";
					break;
				}
			}
		}

		return sonoAcc;
	}

	protected ActionForward checkBlockedAction(
			UserContextInterface userContextInterface,
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest) {
		FrontEndContext fec = getFrontEndContext(httpServletRequest);
		if (fec !=null){
			String operatore = fec.getUsername();
			if (operatore ==null){
				//return actionMapping.findForward("globalError");

				ActionMessages actionMessages= new ActionMessages();
				//default value (must be set in ApplicationResources.properties)
				String key="error.global.runtime";


				ActionMessage msg = new ActionMessage(key);
				actionMessages.add("error", msg);
				saveErrors(httpServletRequest, actionMessages);
				return (new ActionForward(actionMapping.getInput(),true));
			}
		}

		return null;
	}


	/**
	 * Retrieve the real ip address of client.
	 *
	 * @param httpServletRequest
	 * @return
	 */
	private String getRemoteAddr(HttpServletRequest httpServletRequest) {
		return WebUtil.getRemoteAddr(httpServletRequest);
	}

}
/**
 *
 */
package it.nch.fwk.fo.action;

import it.nch.fwk.fo.base.handler.profile.UserContextInterface;
import it.nch.fwk.fo.exceptions.FrontEndException;
import it.nch.fwk.fo.util.StopWatchLogger;
import it.nch.fwk.fo.web.FrameworkWebInterface;
import it.nch.fwk.fo.web.action.cm.CMActionAdapter;
import it.nch.fwk.fo.web.action.cm.CMNullAdapter;
import it.nch.fwk.fo.web.action.log.ApplicativeLogManager;
import it.nch.fwk.fo.web.action.log.HttpDebugger;
import it.nch.fwk.fo.web.action.log.Log4jApplicativeLogManager;
import it.nch.fwk.fo.web.action.sso.SSOAdapter;
import it.nch.fwk.fo.web.action.sso.SiteMinderSSOAdapter;
import it.nch.fwk.fo.web.login.LoginProcessor;
import it.nch.fwk.fo.web.mapping.FrameworkActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

/**
 * Estensione astratta di org.apache.struts.actions.DispatchAction da cui discendono
 * tutte le altre Action dell'applicazione.
 *
 * @author GardiniG
 *
 */
public abstract class FrameworkAction extends DispatchAction implements
		FrameworkWebInterface {

	// --------------------------------------------------------- Instance
	// Variables

	/**
	 * Logger for this class
	 */
	//private static final Logger logger = Logger.getLogger(FrameworkAction.class);
	private static final Logger logger = Logger.getLogger("it.nch.is.fwk.request.tracer");


	private static SSOAdapter adapter = null;
	//private static final Logger log = Logger.getLogger("it.nch.is.fo.audit");
	// --------------------------------------------------------- Methods

	/**
	 * Metodo execute scritto spostato nella FrameworkAction in quanto non
	 * necessario più nelle singole classi NIB e Corporate, grazie ai metodi
	 * 'innerExecute' e upperExecute (quest'ultimo necessario solo per la
	 * 'CorporateAction'
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public final ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		String methodInvokedName = httpServletRequest.getParameter("method");
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),methodInvokedName,"");
		//inizio log tempo di esecuzione
		long startTime = System.currentTimeMillis();
		st.start();
		ActionForward actionForward = null;

		// Recupero eventuali errori/messaggi struts precedentemente impostati
		ActionErrors actionErrors = (ActionErrors) httpServletRequest
				.getAttribute(org.apache.struts.Globals.ERROR_KEY);

		if (actionErrors == null) {
			actionErrors = new ActionErrors();
		}

		ActionMessages actionMessages = (ActionMessages) httpServletRequest
				.getAttribute(Globals.MESSAGE_KEY);

		if (actionMessages == null) {
			actionMessages = new ActionMessages();
		}

		// Recupero l'adattatore per il sistema di SSO utilizzato
		SSOAdapter retrievedSSOAdapter = getSSOAdapter();

		// recupero il gestore per le funzionalita' avanzate di logging
		// applicativo
		ApplicativeLogManager logMgr = getApplicativeLogManager();

		// Per prima cosa provvedo ad attivare il logging per avere il maggior
		// numero di informazioni per identificare l'utente e la sessione
		// dell'utente che ha chiamato l'applicazione
		logMgr.setupLogging(httpServletRequest, retrievedSSOAdapter);

		// Provvedo poi ad utilizzare opportuni meccanismi di gestione della
		// cache del browser e del proxy
		setCacheParameters(httpServletResponse);

		// Verifico se ci sono le condizioni per dover gestire un "clone switch"
		// (spegnimento o caduta di uno degli application server in assenza di
		// sessioni persistenti)
		boolean isCloneSwitch = isCloneSwitch(actionMapping, httpServletRequest
				.getSession());

		// Procedo ad attivare i meccanismi di gestione dell'autologin. Nella
		// maggior parte dei casi si limitera' a recuperare lo UserContext dalla
		// sessione, ma potra' anche dover fare una vera e propria login (nei
		// casi di login iniziale o "clone switch")
		UserContextInterface userContextInterface = manageAutoLogin(httpServletRequest);

		// Dopo la login posso permettermi di verificare se devo fare una
		// qualche tipo di gestione per un eventuale "clone switch"
		if (!(isCloneSwitch && ((actionForward = manageCloneSwitch())) != null)) {
			// Se non sto reagendo ad un "clone switch" posso procedere con gli
			// altri compiti
			actionForward = innerExecute(actionMapping, actionForm,
					actionMessages, actionErrors, httpServletRequest,
					httpServletResponse, userContextInterface);
		}

		// Per ultima cosa provvedo a rilasciare qualsiasi risorsa che avesse
		// mantenuto un contesto nella esecuzione della chiamata
		getApplicativeLogManager().tearDownLogging(httpServletRequest);


		long endTime = System.currentTimeMillis();
	    long gapTime = endTime - startTime;
	    st.stop();

		if (logger.isInfoEnabled()) {

		    //logger.info("\nLOGGER-> FRAMEWORK ACTION: method invoked: "+methodInvokedName+" invocation time ms: " + gapTime);
		    logger.info("["+getClass().getName()+"::"+methodInvokedName+"]" +" invocation time ms: " + gapTime);
		    logger.info("["+getClass().getName()+"::"+methodInvokedName+"]" +" httpRequest: " + HttpDebugger.traceHttpRequest(httpServletRequest));
		}



		return actionForward;
	}

	/**
	 * Method initializeContentManagement
	 *
	 * @param httpServletRequest
	 * @param actionMapping
	 * @return CMActionAdapter
	 */
	protected CMActionAdapter initializeContentManagement(
			HttpServletRequest httpServletRequest, ActionMapping actionMapping) {
		CMActionAdapter actionAdapter = getCMAdapter();
		actionAdapter.createCMContext(httpServletRequest, actionMapping);
		return actionAdapter;
	}

	/**
	 * Method getCMAdapter
	 *
	 * @return CMActionAdapter
	 */
	protected CMActionAdapter getCMAdapter() {
		return new CMNullAdapter();
	}

	/**
	 * Method checkBlockedAction
	 *
	 * @param userContextInterface
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @return ActionForward
	 */
	protected ActionForward checkBlockedAction(
			UserContextInterface userContextInterface,
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest) {
		return null;
	}

	/**
	 * Method mergeContext
	 *
	 * @param userContext
	 * @param userActionContext
	 * @return UserContextInterface
	 */
	protected UserContextInterface mergeContext(
			UserContextInterface userContext,
			UserContextInterface userActionContext) {
		return userContext;
	}

	/**
	 * Metodo manageCloneSwitch deve essere riscritto se si intende provvedere
	 * ad una gestione particolare dell'evento di "clone switch" come ad esempio
	 * la redirezione su una particolare pagina. Se invece non si vuole gestire
	 * in alcun modo particolare, basta utilizzare questa implementazione di
	 * default, che restituisce 'null'
	 *
	 * @return ActionForward
	 */
	protected ActionForward manageCloneSwitch() {
		return null;
	}

	/**
	 * Metodo manageAutoLogin gestisce la logica di login
	 *
	 * @param httpServletRequest
	 * @return UserContextInterface
	 * @throws FrontEndException
	 *
	 * <br>
	 * =========================================================================
	 * <br>
	 * Modified by Richard Gennaro on 13-dic-2005 16.18.39 <br>
	 * REASON: Questo metodo deve essere personalizzato dalle classi
	 * specializzate di IOLI2, per poterlo fare ne cambiamo la firma da private
	 * a protected <br>
	 * =========================================================================
	 * <br>
	 * ORIGINAL CODE: private UserContextInterface
	 * manageAutoLogin(HttpServletRequest request) <br>
	 * =========================================================================
	 */
	protected UserContextInterface manageAutoLogin(
			HttpServletRequest httpServletRequest) throws FrontEndException {

		// Al momento questo parametro determina, per un dato ambiente, se si
		// usa l'autenticazione di SSO o se si usa un'autenticazione simulata.
		boolean isSSOAuth = getSSOAdapter().isSSOActive();

		// Recupero la session
		HttpSession session = httpServletRequest.getSession();

		// Recupero lo UserContextInterface dalla sessione
		UserContextInterface userContext = (UserContextInterface) session
				.getAttribute(USER_CONTEXT_PROPERTY);

		String userId = null;
		String bankId = null;

		if (userContext != null) {
			if (isSSOAuth) {
				// Incrocio i dati restituiti con quelli di SSO
				try {
					userId = getSSOAdapter().getUserId(httpServletRequest);
				} catch (NoSuchFieldException e) {
					throw new FrontEndException(
							"Non e' stata possibile recuperare la UserId da SSO [chiamata con UserContext]");
				}

				// Check dello UserId del Contesto con quello letto dalla
				// request.
				if (userId != null
						&& !userId.equals(userContext.getLoginName())) {
					logger
							.error("Login Name letto da request diverso da quello del contesto corrente dell'utente.");
					throw new FrontEndException(
							"Login Name ("
									+ userId
									+ ") letto da request diverso da quello del contesto corrente dell'utente:"
									+ userContext.getLoginName());
				}
			} else if (isUserContextMandatory()) {
				// Se lo UserContext e' obbligatorio per l'azione
				if (isSSOAuth) {
					// Se siamo in un ambiente SSO recupero l'id utente
					// dall'header
					try {
						userId = getSSOAdapter().getUserId(httpServletRequest);
					} catch (NoSuchFieldException e) {
						throw new FrontEndException(
								"Non e' stata possibile recuperare la UserId da SSO [chiamata senza UserContext]");
					}

					// Recupero poi il codice della banca
					try {
						bankId = getSSOAdapter().getBankId(httpServletRequest);
					} catch (NoSuchFieldException e) {
						throw new FrontEndException(
								"Non e' stata possibile recuperare il BankId da SSO [chiamata senza UserContext]");
					}

				} else {
					// Se siamo in un ambiente NON SSO (sviluppo) recupero il
					// valore di un campo dalla request ed uso quello come
					// userid
					userId = httpServletRequest
							.getParameter(MANUAL_LOGIN_PROPERTY);

					if (userId == null) {
						throw new FrontEndException(
								"Non e' stata possibile recuperare la UserId da SSO [chiamata senza UserContext]");
					}

					bankId = httpServletRequest
							.getParameter(MANUAL_BANK_PROPERTY);

					if (bankId == null) {
						throw new FrontEndException(
								"Non e' stata possibile recuperare il BankId da SSO [chiamata senza UserContext]");
					}
				}

				// Infine parto col processo di login
				userContext = getLoginProcessor().doLoginProcess(userId,
						bankId, session, httpServletRequest);

			} else {
				// Se lo UserContext non e' obbligatorio per l'azione non faccio
				// nulla e sara' null
				userContext = null;

				// Questo pero' dovrebbe succedere solo in ambiente di sviluppo,
				// quindi ad ogni buon conto provvedo a loggarlo debitamente
				logger
						.warn("Una sottoclasse ha sovrascritto il metodo isUserContextMandatory(). Tipicamente la LoginAction.");

			}
		}

		if (userContext != null) {
			session.setAttribute(USER_CONTEXT_PROPERTY, userContext);
		}

		return userContext;
	}

	/**
	 * Method getSSOAdapter
	 *
	 * @return SSOAdapter
	 */
	protected SSOAdapter getSSOAdapter() {
		return (adapter == null) ? new SiteMinderSSOAdapter() : adapter;
	}

	/**
	 * Metodo isUserContextMandatory serve per determinare se l'assenza di uno
	 * UserContext comporta un errore (redirezione alla pagina di errore).
	 * L'implementazione di default restituisce semplicemente "true" Alcune
	 * classi particolari possono "ignorare" l'assenza della UserContext
	 * effettuando l'override del metodo con uno che restituisca "false". Un
	 * esempio di questo comportamento si può vedere nella LoginAction
	 *
	 * @return boolean
	 */
	protected boolean isUserContextMandatory() {
		return true;
	}

	/**
	 * Metodo getLoginProcessor recupera il LoginProcessor per creare uno
	 * UserContext in seguito alla login
	 *
	 * @return LoginProcessor
	 */
	protected abstract LoginProcessor getLoginProcessor();

	/**
	 * Metodo setCacheParameters per la gestione dei parametetri della risposta
	 * HTTP che permettono una gestione "personalizzata" della cache dei browser
	 * e dei proxy. L'implementazione di default è la più restrittiva possibile
	 * (per motivi di sicurezza). E' possibile cambiare l'implementazione della
	 * cache facendo l'ovverride di questo metodo.
	 *
	 * @param httpServletResponse
	 *            E' l'oggetto HttpServletResponse che viene modificato per la
	 *            gestione della cache (in pratica si agirà sull'header della
	 *            risposta HTTP)
	 */
	protected void setCacheParameters(HttpServletResponse httpServletResponse) {

		// Per il problema della cache
		httpServletResponse.setHeader("Pragma", "no-cache");

		// Per protocollo HTTP 1.0 serve a non salvare la pagina nella cache
		// per un server Proxy : il browser salva ugualmente la pagina nella
		// cache locale
		httpServletResponse.setDateHeader("Expires", 0);

		// Per protocollo HTTP 1.0 & HTTP 1.1 rende invalita subito la pagina
		httpServletResponse.setHeader("Cache-Control",
				"no-cache, must-revalidate, max_age=0, no-store");

		// Per protocollo HTTP 1.1 serve a non salvare la pagina nella cache
		// per un server Proxy e nella cache locale
	}

	/**
	 * Method isCloneSwitch
	 *
	 * @param actionMapping
	 * @param httpSession
	 * @return boolean
	 */
	protected boolean isCloneSwitch(ActionMapping actionMapping,
			HttpSession httpSession) {

		boolean isCloneSwitched = false;

		/*
		 * Controllo se l'utente non ha ancora in sessione lo UserContext. In
		 * questo caso, ci sono 2 possibilita': 1 - e' alla login 2 - ha appena
		 * subito un "clone switch"
		 *
		 * Come faccio a distinguere i 2 casi? Nello StrutsConfig devo aver
		 * impostato delle proprieta' che mi dicono quali Action siano in punti
		 * base navigazionali (home page, pagine di partenze di un wizard, etc).
		 * In questo caso, come anche nel caso della login non devo fare alcuna
		 * gestione particolare per il "clone switch" e quindi mi limito a
		 * restituire che non e' accaduto nulla di strano.
		 *
		 * Solo se la Action non e' un "punto base navigazionale" (e quindi non
		 * puo' essere una pagina di atterraggio della login) restituiro' che
		 * c'e' stato un "clone switch".
		 *
		 * Sara' poi la "manageCloneSwitch" a decidere come gestire il caso
		 */
		if (httpSession.getAttribute(USER_CONTEXT_PROPERTY) == null) {
			if (!(actionMapping instanceof FrameworkActionMapping && ((FrameworkActionMapping) actionMapping)
					.isNavigationalBase())) {
				logger.debug("Action non di login");
				isCloneSwitched = true;
			}
		}

		return isCloneSwitched;
	}

	/**
	 * Method getApplicativeLogManager
	 *
	 * @return ApplicativeLogManager
	 */
	protected ApplicativeLogManager getApplicativeLogManager() {
		return new Log4jApplicativeLogManager();
	}

	/**
	 * Metodo innerExecute reso astratto per poi essere riscritto all'interno
	 * delle classi NIBAction (dove dovrà solo essere reso 'protected' e
	 * CorporateAction. In questo modo potrà essere richiamato all'interno del
	 * metodo 'innerExecute' scritto sopra
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param actionMessages
	 * @param actionErrors
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param userContextInterface
	 * @return ActionForward
	 * @throws Exception
	 */
	protected abstract ActionForward innerExecute(ActionMapping actionMapping,
			ActionForm actionForm, ActionMessages actionMessages,
			ActionErrors actionErrors, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			UserContextInterface userContextInterface) throws Exception;

	/**
	 * Metodo upperExecute necessario in quanto permette il 'rimbalzo' alla
	 * 'DispatchAction' dalla 'CorporateAction', senza creare un loop
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	protected final ActionForward upperExecute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		return super.execute(actionMapping, actionForm, httpServletRequest,
				httpServletResponse);
	}
}
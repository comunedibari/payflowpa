package it.tasgroup.iris.facade.ejb.gateway.authentication;

import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.locator.ServiceLocatorException;
import it.nch.is.fo.profilo.Applicazioni;
import it.nch.is.fo.profilo.IntestatariCommon;
import it.tasgroup.iris.business.ejb.client.authentication.AuthenticationBusinessLocal;
import it.tasgroup.iris.business.ejb.client.gateway.GatewayAuthenticationBusinessLocal;
import it.tasgroup.iris.domain.SessioneGateway;
import it.tasgroup.iris.dto.ApplicazioniDTO;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.FunzioniPropDTO;
import it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO;
import it.tasgroup.iris.dto.SessionIdDTO;
import it.tasgroup.iris.dto.autentication.SessionDTO;
import it.tasgroup.iris.dto.exception.GatewayAuthenticationException;
import it.tasgroup.iris.dto.gateway.PaymentAuthenticationDTO;
import it.tasgroup.iris.dto.gateway.SessioneGatewayDTO;
import it.tasgroup.iris.dto.menu.ApplicazioniMenu;
import it.tasgroup.iris.facade.ejb.gateway.dto.builder.GatewayAuthenticationBEDTOBuilder;
import it.tasgroup.iris.gateway.facade.ejb.client.authentication.AuthenticationFacadeLocal;
import it.tasgroup.iris.gateway.facade.ejb.client.authentication.AuthenticationFacadeRemote;
import it.tasgroup.iris.shared.util.enumeration.EnumOutcomeStatus;
import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name = "AuthenticationFacade")
public class AuthenticationFacadeBean implements AuthenticationFacadeLocal, AuthenticationFacadeRemote {
	
//	private static final Logger LOGGER = LogManager.getLogger(AuthenticationFacadeBean.class);	
	
	@EJB(name = "GatewayAuthenticationBusiness")
	private GatewayAuthenticationBusinessLocal autorizzazioneBusinessBean;
	
	@EJB(name = "AuthenticationBusiness")
	private AuthenticationBusinessLocal authenticationBusinessBean;

	/**
	 * @throws GatewayAuthenticationException 
	 * 
	 */
	@Override
	public EsitoOperazionePagamentoDTO askForNewToken(RichiestaAutorizzazioneDTO richAutDTO) throws GatewayAuthenticationException{
		
		EsitoOperazionePagamentoDTO esitoDTO = autorizzazioneBusinessBean.checkPaymentGatewayShoppingCart(richAutDTO);		
		
		if (EnumBusinessReturnCodes.getByKey(esitoDTO.getCodice()).getSeverityLevel().getStatus().equals(EnumOutcomeStatus.OK)){
			
						
			SessioneGateway session = GatewayAuthenticationBEDTOBuilder.populateSessioneGateway(richAutDTO);
			
			SessioneGateway savedSession = autorizzazioneBusinessBean.saveGatewaySession(session);
			
			SessionIdDTO sessionIdDTO = GatewayAuthenticationBEDTOBuilder.populateSessionIdDTO(savedSession);
			
			
			richAutDTO.getTestata().setSession(sessionIdDTO);
			
		}
				
		return esitoDTO;
			
	}
	
	@Override
	public SessioneGatewayDTO authenticateForPayment(PaymentAuthenticationDTO dto) throws GatewayAuthenticationException{
		
		SessioneGateway sessioneGateway = autorizzazioneBusinessBean.authenticateForPayment(dto);
		
		SessioneGatewayDTO sessioneGatewayDTO = GatewayAuthenticationBEDTOBuilder.populateSessioneGatewayDTO(sessioneGateway);
		return sessioneGatewayDTO;
	}
	
	@Override
	public SessioneGatewayDTO getSessioneGatewayDTOLight(String token) throws GatewayAuthenticationException{
		SessioneGateway sessioneGateway = autorizzazioneBusinessBean.getSessioneGateway(token);
		SessioneGatewayDTO sessioneGatewayDTO = GatewayAuthenticationBEDTOBuilder.populateSessioneGatewayDTOLight(sessioneGateway);
		return sessioneGatewayDTO;
	}

	@Override
	public FrontEndContext populateFEC(SessioneGatewayDTO sessioneGatewayDTO) throws GatewayAuthenticationException, ServiceLocatorException, RemoteException {
		
		SessioneGateway sessioneGateway = GatewayAuthenticationBEDTOBuilder.populateSessioneGateway(sessioneGatewayDTO);
		
		FrontEndContext fec = autorizzazioneBusinessBean.populateFEC(sessioneGateway);
		
		return fec;
	}

	@Override
	public void disconnectSession(String sessionId) {
		authenticationBusinessBean.disconnectSession(sessionId);		
	}

	@Override
	public void insertOrUpdateSession(SessionDTO sessionDto) {
		authenticationBusinessBean.insertOrUpdateSession(sessionDto);
	}

	@Override
	public void updateCorporateInSession(String sessionId, String corporate) {
		authenticationBusinessBean.updateCorporateInSession(sessionId, corporate);
	}

	@Override
	public String getDbInfo() {
		return authenticationBusinessBean.getDbInfo();
	}
	
	@Override
	public ApplicazioniMenu estraiMenuPerCache(String codApplicazione) {
		return authenticationBusinessBean.estraiMenuPerCache(codApplicazione);
	}
	
	@Override
	public FrontEndContext getAziendaCorrente(FrontEndContext fec, String corporate, String operator) {
		return authenticationBusinessBean.getCurrentCorporateForLogin(fec, corporate, operator);
	}

	@Override
	public List<String> listEnabledFunctions(String corporate, String operator) {
		return authenticationBusinessBean.listEnabledFunctions(corporate, operator);
	}

	@Override
	public FrontEndContext login(FrontEndContext fec, DTO dto) {
		return authenticationBusinessBean.login(fec, dto);
	}

	@Override
	public FrontEndContext loginRT(FrontEndContext fec, DTO dto) {
		return authenticationBusinessBean.loginRT(fec, dto);
	}

	@Override
	public String getCodApplicazioneByCategoria(String categoria) {
		return authenticationBusinessBean.getCodApplicazioneByCategoria(categoria);
	}

	@Override
	public IntestatariCommon getAziendaByCode(String corporate) {
		return authenticationBusinessBean.getAziendaByCode(corporate);
	}
	
	@Override
	public void synchTemplate(String operatore, String corporate, String codApplicazione) {
		authenticationBusinessBean.synchTemplate(operatore, corporate, codApplicazione);
	}

	@Override
	public List<ApplicazioniDTO> getListaApplicazioni() {
		
		List<ApplicazioniDTO> returnList = new ArrayList<ApplicazioniDTO>();
		List<Applicazioni> listaAppl = authenticationBusinessBean.getListaApplicazioni();
		if (listaAppl != null) {
			for (Applicazioni applicazione : listaAppl) {
				ApplicazioniDTO appl = new ApplicazioniDTO();
				appl.setCodiceApplicazione(applicazione.getApplicationCode());
				appl.setDescrizione(applicazione.getDescription());
				returnList.add(appl);
			}
		}
		return returnList;
	}

	@Override
	public List<String> getListaClassiByApplicazione(String applicazione) {
		return authenticationBusinessBean.getListaClassiByApplicazione(applicazione);
	}

	@Override
	public List<FunzioniPropDTO> getListaFunzioniByApplicazioneClasse(String applicazione, String classe) {
		return authenticationBusinessBean.getListaFunzioniByApplicazioneClasse(applicazione, classe);
	}


	@Override
	public void abilitaClasseByApplicazioneFunzione(String classe, String applicazione, String funzione, String user) {
		authenticationBusinessBean.abilitaClasseByApplicazioneFunzione(classe, applicazione, funzione, user);
		
	}

	@Override
	public void disabilitaClasseByApplicazioneFunzione(String classe, String applicazione, String funzione) {
		authenticationBusinessBean.disabilitaClasseByApplicazioneFunzione(classe, applicazione, funzione);
	}	
}


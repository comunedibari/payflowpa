/**
 * 
 */
package it.tasgroup.iris.gateway.facade.ejb.client.authentication;

import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.locator.ServiceLocatorException;
import it.nch.is.fo.profilo.IntestatariCommon;
import it.tasgroup.iris.dto.ApplicazioniDTO;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.FunzioniPropDTO;
import it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO;
import it.tasgroup.iris.dto.autentication.SessionDTO;
import it.tasgroup.iris.dto.exception.GatewayAuthenticationException;
import it.tasgroup.iris.dto.gateway.PaymentAuthenticationDTO;
import it.tasgroup.iris.dto.gateway.SessioneGatewayDTO;
import it.tasgroup.iris.dto.menu.ApplicazioniMenu;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author pazzik
 * 
 */
public interface AuthenticationFacade {

	public SessioneGatewayDTO authenticateForPayment(PaymentAuthenticationDTO dto) throws GatewayAuthenticationException;
	
	public SessioneGatewayDTO getSessioneGatewayDTOLight(String token) throws GatewayAuthenticationException;

	public EsitoOperazionePagamentoDTO askForNewToken(RichiestaAutorizzazioneDTO richAutDTO) throws GatewayAuthenticationException;

	public FrontEndContext populateFEC(SessioneGatewayDTO sessioneGatewayDTO) throws GatewayAuthenticationException, ServiceLocatorException, RemoteException;

	//
	//
	// metodi portati dal SessioncontrolserviceBusinessDelegate
	//
	//
	public void disconnectSession(String sessionId);

	public void insertOrUpdateSession(SessionDTO sessionDto);

	public void updateCorporateInSession(String sessionId, String corporate);

	public String getDbInfo();

	//
	//
	//  metodi portati dal LoginBusinessDelegate
	//
	//
	
	// TODO: eliminare i DTO!!!
	public FrontEndContext login(FrontEndContext fec, DTO dto);
	public FrontEndContext loginRT(FrontEndContext fec, DTO dto);
	public FrontEndContext getAziendaCorrente(FrontEndContext fec, String corporate, String operator) throws Exception;	
	public ApplicazioniMenu estraiMenuPerCache(String codApplicazione);	
	
	
	//
	//
	//  metodi portati dal OperatorserviceBusinessDelegate
	//
	//
	public List<String> listEnabledFunctions(String corporate, String operator);
	
	//
	//
	//  metodi portati dal ProfiloserviceBusinessDelegate
	//
	//
	public String getCodApplicazioneByCategoria(String categoria);
	public IntestatariCommon getAziendaByCode(String corporate);	// rinominare, usare dto diverso da IntestatariCommon
	void synchTemplate(String operatore, String corporate, String codApplicazione);
	
	public List<ApplicazioniDTO> getListaApplicazioni();
	public List<String> getListaClassiByApplicazione(String applicazione);
	public List<FunzioniPropDTO> getListaFunzioniByApplicazioneClasse(String applicazione, String classe);
	void abilitaClasseByApplicazioneFunzione(String classe, String applicazione, String funzione, String user);
	void disabilitaClasseByApplicazioneFunzione(String classe, String applicazione, String funzione);
	

}

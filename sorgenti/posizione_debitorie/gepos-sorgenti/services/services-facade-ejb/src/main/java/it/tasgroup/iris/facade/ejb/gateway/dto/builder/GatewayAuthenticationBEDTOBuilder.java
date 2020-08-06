/**
 * 
 */
package it.tasgroup.iris.facade.ejb.gateway.dto.builder;

import it.nch.utility.GeneratoreIdUnivoci;
import it.tasgroup.idp.rs.model.CondizionePagamentoReference;
import it.tasgroup.idp.rs.model.RichiestaAperturaSessioneGateway;
import it.tasgroup.iris.domain.CarrelloGateway;
import it.tasgroup.iris.domain.CarrelloGatewayPK;
import it.tasgroup.iris.domain.CfgIrisGatewayClient;
import it.tasgroup.iris.domain.SessioneGateway;
import it.tasgroup.iris.dto.PendenzaDTO;
import it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO;
import it.tasgroup.iris.dto.SessionIdDTO;
import it.tasgroup.iris.dto.TestataMessaggioDTO;
import it.tasgroup.iris.dto.gateway.CartItemDTO;
import it.tasgroup.iris.dto.gateway.IrisGatewayClientDTO;
import it.tasgroup.iris.dto.gateway.SessioneGatewayDTO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author pazzik
 *
 */
public class GatewayAuthenticationBEDTOBuilder {
	
	public static SessionIdDTO populateSessionIdDTO(SessioneGateway savedSession){
		
		SessionIdDTO sessionIdDTO = new SessionIdDTO();
		
		sessionIdDTO.setDataOraAccesso(savedSession.getTsInserimento().toString());
		
		sessionIdDTO.setIdSistema(savedSession.getCfgIrisGatewayClient().getSystemId());
		
		sessionIdDTO.setToken(savedSession.getToken());
		
		sessionIdDTO.setIdTerminale(savedSession.getCfgIrisGatewayClient().getApplicationId());
		
		return sessionIdDTO;
		
	}
	
/*	Versione di populateSessioneGateway per chiamate REST API:
	chiamata su oggetto RichiestaAperturaSessioneGateway
	Rispetto all'altra funzione utilizza una lista di CondizionePagamentoReference 
	E fa un controllo su ogni flag opposizione 730
*/
public static SessioneGateway populateSessioneGateway(RichiestaAperturaSessioneGateway richAutDTO){
		
		SessioneGateway sessioneGateway = new SessioneGateway();
		CfgIrisGatewayClient gtwClient = new CfgIrisGatewayClient();
		gtwClient.setApplicationId(richAutDTO.getSenderSil());
		gtwClient.setSystemId(richAutDTO.getSenderSys());
		//sessioneGateway.setSessionId(testata.getSession().getToken());
		sessioneGateway.setCfgIrisGatewayClient(gtwClient);
		sessioneGateway.setOpInserimento("GATEWAY_REST");
		
		sessioneGateway.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		String newToken = GeneratoreIdUnivoci.GetCurrent().generaCodiceTransazione();
		sessioneGateway.setToken(newToken);
		sessioneGateway.setSessionId(newToken);
		sessioneGateway.setOfflinePaymentMethods(0);
		sessioneGateway.setRedirectOnly(richAutDTO.getRedirectOnly());
		sessioneGateway.setAnagraficaVers(richAutDTO.getAnagraficaVersante());
		sessioneGateway.setEmailVers(richAutDTO.getEmail());
		sessioneGateway.setUrlBack(richAutDTO.getUrlBack());
		sessioneGateway.setUrlCancel(richAutDTO.getUrlCancel());
		sessioneGateway.setCodFiscaleVers(richAutDTO.getCodiceFiscaleVersante());
		
		Set<CarrelloGateway> carrello = new HashSet<CarrelloGateway>();
		//loop su condizioniReference
		BigDecimal importoTotale = BigDecimal.ZERO;
		int flagOpposizioneSessione = 0;
		String flagOpposizioneFinale = "0";
		List<BigDecimal> importi = new ArrayList<BigDecimal>();
		List<Integer> listaFlagOpposizione = new ArrayList<Integer>();
		
		for (CondizionePagamentoReference condizione : richAutDTO.getCondizioniList()) {
			importi.add(condizione.getImporto()); //calcolo totale
			listaFlagOpposizione.add(condizione.getFlagOpposizione730()); //se c'è almeno un true allora true
			String idCondizione = condizione.getIdCondizione();
			BigDecimal importo  = condizione.getImporto();
			//int flag_730 = condizione.getFlag_opposizione_730();
			CarrelloGateway condizioneGateway = new CarrelloGateway();
				
			CarrelloGatewayPK pk = new CarrelloGatewayPK();
			pk.setToken(newToken);
			pk.setIdCondizione(idCondizione);
			condizioneGateway.setId(pk);
			condizioneGateway.setOpInserimento("GATEWAY_REST");
			condizioneGateway.setTsInserimento(new Timestamp(System.currentTimeMillis()));
			condizioneGateway.setSessioneGateway(sessioneGateway);
			//condizioneGateway.setItemFlagOpposizione730(flag_730); DA COMMITTARE
			condizioneGateway.setImTotale(importo);
			carrello.add(condizioneGateway);				
				
		}
		
		for (BigDecimal importo : importi) {
			importoTotale = importoTotale.add(importo);
		}
		
		for (int flagOpposizione : listaFlagOpposizione) {
			flagOpposizioneSessione += flagOpposizione;
		}
		
		if(flagOpposizioneSessione > 0){
			flagOpposizioneFinale = "1";
		}
		
		sessioneGateway.setImTotale(importoTotale); //Calcolarlo dal totale delle condizioni
		sessioneGateway.setFlagOpposizione730(flagOpposizioneFinale);
		sessioneGateway.setCarrelloGateways(carrello);
		
		return sessioneGateway;
		
	}

	
	public static SessioneGateway populateSessioneGateway(RichiestaAutorizzazioneDTO richAutDTO){
		
		TestataMessaggioDTO testata = richAutDTO.getTestata();
		
		SessioneGateway sessioneGateway = new SessioneGateway();
		
		CfgIrisGatewayClient gtwClient = new CfgIrisGatewayClient();
		
		gtwClient.setApplicationId(testata.getSenderSil());
		
		gtwClient.setSystemId(testata.getSenderSys());
		
		sessioneGateway.setSessionId(testata.getSession().getToken());
		
		sessioneGateway.setCfgIrisGatewayClient(gtwClient);
		
		// TODO PAZZIK NON CABLARE
		
		sessioneGateway.setOpInserimento("GATEWAY_WIP");
		
		// TODO PAZZIK 31082012 FORMATTARE
		sessioneGateway.setTsInserimento(new Timestamp(System.currentTimeMillis()));
				
		String newToken = GeneratoreIdUnivoci.GetCurrent().generaCodiceTransazione();
		
		sessioneGateway.setToken(newToken);
		
		sessioneGateway.setImTotale(richAutDTO.getTotalAmount());
		
		sessioneGateway.setFlagOpposizione730(richAutDTO.getFlagOpposizione730() != null ? richAutDTO.getFlagOpposizione730() : "0");
		
		Set<CarrelloGateway> carrello = new HashSet<CarrelloGateway>();
		
		for (PendenzaDTO pend : richAutDTO.getPendenze()){
			
			List<String> condizioni = pend.getCondizioni();
			
			List<BigDecimal> importi = pend.getImporti();
			
			List<String> listaFlagOpposizione = pend.getListaItemFlagOpposizione();
			
			for (int i= 0; i<condizioni.size(); i++){
				
				String idCondizione = condizioni.get(i);
				
				BigDecimal importo  = importi.get(i);
										
				CarrelloGateway condizioneGateway = new CarrelloGateway();
				
				CarrelloGatewayPK pk = new CarrelloGatewayPK();
				
				pk.setToken(newToken);
				
				pk.setIdCondizione(idCondizione);
				
				condizioneGateway.setId(pk);
				
				// TODO PAZZIK NON CABLARE
				
				condizioneGateway.setOpInserimento("GATEWAY_WIP");
				
				condizioneGateway.setTsInserimento(new Timestamp(System.currentTimeMillis()));
				
				condizioneGateway.setSessioneGateway(sessioneGateway);
				
//				condizioneGateway.setItemFlagOpposizione730(flagOpposizione);
				
				condizioneGateway.setImTotale(importo);
							
				carrello.add(condizioneGateway);				
				
			}
		}
		
		sessioneGateway.setCarrelloGateways(carrello);
		
		return sessioneGateway;
		
	}
	
	public static SessioneGatewayDTO populateSessioneGatewayDTOLight (SessioneGateway sessioneGateway) {
		SessioneGatewayDTO sessioneGatewayDTO = new SessioneGatewayDTO();
		sessioneGatewayDTO.setRedirectOnly(sessioneGateway.getRedirectOnly());
		sessioneGatewayDTO.setUrlBack(sessioneGateway.getUrlBack());
		return sessioneGatewayDTO;
	}

	public static SessioneGatewayDTO populateSessioneGatewayDTO(SessioneGateway sessioneGateway) {
		
		SessioneGatewayDTO sessioneGatewayDTO = new SessioneGatewayDTO();
		
		IrisGatewayClientDTO clientDTO = new IrisGatewayClientDTO();
		
		CfgIrisGatewayClient client = sessioneGateway.getCfgIrisGatewayClient();
		
		clientDTO.setApplicationId(client.getApplicationId());
		
		clientDTO.setSystemId(client.getSystemId());
		
		clientDTO.setAuthenticated(client.getAuthenticated()>0);
		
		sessioneGatewayDTO.setUsata(sessioneGateway.getUsata());
				
		String newToken = GeneratoreIdUnivoci.GetCurrent().generaCodiceTransazione();
		
		sessioneGatewayDTO.setToken(newToken);
		
		sessioneGatewayDTO.setSessionId(sessioneGateway.getSessionId());
		
		sessioneGatewayDTO.setIntestatario(sessioneGateway.getIntestatario());
		
		sessioneGatewayDTO.setOperatore(sessioneGateway.getOperatore());
		
		sessioneGatewayDTO.setImTotale(sessioneGateway.getImTotale());
		
		sessioneGatewayDTO.setFlagOpposizione730(sessioneGateway.getFlagOpposizione730());
		
		sessioneGatewayDTO.setRedirectOnly(sessioneGateway.getRedirectOnly());
		
		List<CartItemDTO> carrello = new ArrayList<CartItemDTO>();
		
			
		for (CarrelloGateway condizioneGateway: sessioneGateway.getCarrelloGateways()){
									
				CartItemDTO condizioneGatewayDTO = new CartItemDTO();
				
				condizioneGatewayDTO.setToken(condizioneGateway.getId().getToken());
				
				condizioneGatewayDTO.setIdCondizione(condizioneGateway.getId().getIdCondizione());
				
				condizioneGatewayDTO.setImTotale(condizioneGateway.getImTotale());
				
				//TODO PAZZIK CONFRONTARE I SEGUENTI MAPPING CON QUELLI DI IRIS PRECEDENTI
				condizioneGatewayDTO.setEnte(condizioneGateway.getCondizionePagamento().getEnte().getDenominazione());
				condizioneGatewayDTO.setTributo(condizioneGateway.getCondizionePagamento().getCdTrbEnte());
				condizioneGatewayDTO.setScadenza(condizioneGateway.getCondizionePagamento().getDtScadenza());
				condizioneGatewayDTO.setCausale(condizioneGateway.getCondizionePagamento().getPendenza().getDeCausale());
				condizioneGatewayDTO.setIdTributoEnte(condizioneGateway.getCondizionePagamento().getCdTrbEnte());
				condizioneGatewayDTO.setOpposizioneInvio730(condizioneGateway.getFlagOpposizione730());
				carrello.add(condizioneGatewayDTO);				
				
		}
		
		sessioneGatewayDTO.setCartItemDTOs(carrello);
		
		sessioneGatewayDTO.setUrlBack(sessioneGateway.getUrlBack());
		sessioneGatewayDTO.setUrlCancel(sessioneGateway.getUrlCancel());
		sessioneGatewayDTO.setOfflinePaymentMethods(sessioneGateway.getOfflinePaymentMethods());
		
		sessioneGatewayDTO.setTipoSoggettoVers(sessioneGateway.getTipoSoggettoVers()); 
		sessioneGatewayDTO.setCodFiscaleVers(sessioneGateway.getCodFiscaleVers()); 
		sessioneGatewayDTO.setAnagraficaVers(sessioneGateway.getAnagraficaVers());
		sessioneGatewayDTO.setIndirizzoVers(sessioneGateway.getIndirizzoVers()); 
		sessioneGatewayDTO.setNumeroCivicoVers(sessioneGateway.getNumeroCivicoVers()); 
		sessioneGatewayDTO.setCapVers(sessioneGateway.getCapVers()); 
		sessioneGatewayDTO.setLocalitaVers(sessioneGateway.getLocalitaVers()); 
		sessioneGatewayDTO.setProvinciaVers(sessioneGateway.getProvinciaVers()); 
		sessioneGatewayDTO.setNazioneVers(sessioneGateway.getNazioneVers()); 
		sessioneGatewayDTO.setEmailVers(sessioneGateway.getEmailVers());
		return sessioneGatewayDTO;
	}

	public static SessioneGateway populateSessioneGateway(SessioneGatewayDTO sessioneDTO) {
		
		SessioneGateway sessione = new SessioneGateway();
		
		sessione.setIntestatario(sessioneDTO.getIntestatario());
		sessione.setOperatore(sessioneDTO.getOperatore());
		sessione.setToken(sessioneDTO.getToken());
		sessione.setImTotale(sessioneDTO.getImTotale());
		sessione.setSessionId(sessioneDTO.getSessionId());
		sessione.setUsata(sessioneDTO.getUsata());
		
		return sessione;
	}

}

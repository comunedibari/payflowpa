package it.tasgroup.iris.payment.helper;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;

import gov.telematici.pagamenti.ws.FaultBean;
import gov.telematici.pagamenti.ws.NodoInviaCarrelloRPT;
import gov.telematici.pagamenti.ws.NodoInviaCarrelloRPTRisposta;
import gov.telematici.pagamenti.ws.ppthead.IntestazioneCarrelloPPT;
import it.nch.erbweb.client.ServiceSessionConstants;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.util.Tracer;
import it.nch.fwk.fo.web.util.WebUtil;
import it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaDettaglioVO;
import it.nch.is.fo.profilo.OperatoriPojo;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.pagamenti.creditcard.DisposizioneCartaCreditoVO;
import it.nch.pagamenti.creditcard.DistintaCartaCreditoVO;
import it.nch.profile.GatewayProfileManager;
import it.tasgroup.iris.dto.CondizionePagamentoDTO;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;
import it.tasgroup.iris.dto.anagrafica.OperatoreDTO;
import it.tasgroup.iris.dto.anagrafica.TributoEnteDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgGatewayPagamentoDTO;
import it.tasgroup.iris.facade.ejb.client.anagrafica.AnagraficaEntiFacade;
import it.tasgroup.iris.facade.ejb.client.confpagamenti.ConfPagamentiFacade;
import it.tasgroup.iris.facade.ejb.client.flussi.DistintePagamentoFacade;
import it.tasgroup.iris.nodopagamentispc.util.RPTUtil;
import it.tasgroup.iris.sessioncart.ShoppingCartSessionHelper;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;
import it.tasgroup.iris.util.WrapperRPTInvoker;
import it.tasgroup.services.util.enumeration.EnumTipoModalitaPagamento;
import it.tasgroup.services.util.idgenerator.IDGenerator;

public class Wisp20Adapter {
	
	protected static final String BUSINESS_SERVICE = "POSIZIONEDEBITORIA";

	
	public String gotoWisp(HttpServletRequest request, 
			             List<SessionShoppingCartItemDTO> pagamentiWIP,
			             GatewayProfileManager profile) throws Exception {
		
		final String FAULT_NODO_SPC = "FAULT_NODO_SPC";
		
		
        ConfPagamentiFacade cfggatewayPagamentoBean = (ConfPagamentiFacade) ServiceLocator.getSLSBProxy("confPagamentiFacadeBean");
		
	    CfgGatewayPagamentoDTO cfgPagamento = cfggatewayPagamentoBean.readCfgGatewayPagamentoDettaglioBySystemId("AGID_01");
	    
		String forward = null;
				

    	// genero id_gruppo per individuare il carrello
    	String idGruppo = IDGenerator.Generate_TRANSACTION_ID();
    	Iterator<SessionShoppingCartItemDTO> ssCIter =pagamentiWIP.iterator();
    	
    	List<DistintaCartaCreditoVO> dccVoList = new ArrayList<DistintaCartaCreditoVO>();
    	List<String> rptList                   = new ArrayList<String>();
    	List<String> iuvList                   = new ArrayList<String>();
    	List<String> idFiscCreditoreList       = new ArrayList<String>();
    	List<String> codContestoPagList        = new ArrayList<String>();
    	
    	
    	while (ssCIter.hasNext()) {
    		
    		SessionShoppingCartItemDTO sscI= ssCIter.next();

    		String codTransazione = null;
    		String codTransazionePSP = null;
    		
    		codTransazione = sscI.getIdPagamentoEnte();
    		codTransazionePSP = IDGenerator.Generate_TRANSACTION_ID();
    		
            // creo distinta
    		Locale locale = (Locale)request.getSession().getAttribute(Globals.LOCALE_KEY);
    		DistintaCartaCreditoVO dccVo= creaDistintaCartaCreditoCarrello(codTransazione, idGruppo, sscI, codTransazionePSP, locale,profile,cfgPagamento);
    		
    		
    	    // creo rpt
    		String currentRpt =  creaRichiestaPagamentoTelematicoCartaCreditoCarrello(request, dccVo, sscI,codTransazione,cfgPagamento,profile);
    	    
    		idFiscCreditoreList.add(sscI.getIdFiscaleEnte());
    		rptList.add(currentRpt);
    		dccVoList.add(dccVo);
    		iuvList.add(codTransazione);
    		codContestoPagList.add(codTransazionePSP);
    	
    	}
    	NodoInviaCarrelloRPT bodyRichiestaCarrello = RPTUtil.buildBodyNodoInviaCarrelloRPT(cfgPagamento.getSystemId(),
    			                                                                           cfgPagamento.getSubsystemId(),
    			                                                                           cfgPagamento.getApplicationId(),
    			                                                                           codContestoPagList,
                                                                                           idFiscCreditoreList, // idFiscaleCreditore del primo pagamento
                                                                                           iuvList,
                                                                                           rptList);
                                                                                           
    	IntestazioneCarrelloPPT headerCarrello = RPTUtil.buildIntestazioneRichiestaCarrello(idGruppo);
    	
    	StatiPagamentiIris statoPaytas;
    	try {
			Tracer.debug(Wisp20Adapter.class.getName(), "preparaRichiesta", "calling nodoInviaCarrelloRPT con RT:");
			
			NodoInviaCarrelloRPTRisposta risposta = new WrapperRPTInvoker().nodoInviaCarrelloRPT(bodyRichiestaCarrello, headerCarrello);
			
			//
			// elaborazione risposta del nodo
			//
			if (risposta.getFault() != null || risposta.getListaErroriRPT()!=null) {

				Tracer.debug(Wisp20Adapter.class.getName(), "preparaRichiesta","FAULT nella risposta");
				statoPaytas = StatiPagamentiIris.IN_ERRORE;
						if (risposta.getFault() != null) {
								request.setAttribute(FAULT_NODO_SPC, getFaultDescription(risposta.getFault()));
						} else {
								if (risposta.getListaErroriRPT() != null) {
										StringBuilder descrizione = new StringBuilder();
										int l = risposta.getListaErroriRPT().getFault().length;
										for (int i = 0; i < l; i++) {
												FaultBean f = risposta.getListaErroriRPT().getFault(i);
												if (f != null) {
														descrizione.append(" | ").append(getFaultDescription(f));
												}
										}
										request.setAttribute(FAULT_NODO_SPC, descrizione.toString());
								}
						}
				  request.setAttribute("MSG_NODO_SPC", "flusso.esitoPagamento.nodopagamenti.msg.esitoKo");
				//-------
				
				//------
				forward = "esitoRichiestaNodoSPC";

			} else {
                // qui risposta OK.... 
				
				// faccio le insert su database delle operazioni con stato in corso
				// effettuo il redirect sulla url fornita come parametro
				
				if (risposta.getUrl() != null && !risposta.getUrl().equals("")) { // TEST PAGAMENTO DIFFERITO....
					Tracer.debug(Wisp20Adapter.class.getName(), "preparaRichiesta","pagamento IMMEDIATO");
					// pagamento immediato - redirect alla pagina del nodo
					statoPaytas = StatiPagamentiIris.IN_CORSO;
					try {
						URL url = new URL(risposta.getUrl());
						Map<String, String> queryMap = getQueryMap(url.getQuery());
						String idSessioneNDP = queryMap.get("idSession");
						request.getSession().setAttribute("idSessioneNDP",idSessioneNDP);

					} catch (MalformedURLException e) {
						
						request.getSession().removeAttribute("idSessioneNDP");

						Tracer.error(Wisp20Adapter.class.getName(), "preparaRichiesta","ERRORE ho ricevuto un URL non ben formato", e);
					}
					Tracer.debug(Wisp20Adapter.class.getName(), "preparaRichiesta",
							"redirezione a: " + risposta.getUrl());
					request.setAttribute("URL_TO_REDIRECT", risposta.getUrl());
					forward = "redirectionNodoSPC";

				} else {
					Tracer.debug(Wisp20Adapter.class.getName(), "preparaRichiesta",
							"Pagamento DIFFERITO terminato");
					// pagamento differito - richiesta ok
					statoPaytas = StatiPagamentiIris.ESEGUITO_SBF;
					request.setAttribute("MSG_NODO_SPC","flusso.esitoPagamento.nodopagamenti.msg.esitoOkSbf");
					ShoppingCartSessionHelper.removeCartFromSession(request);
					forward = "esitoRichiestaNodoSPC";
				}
			}

		} catch (RemoteException e) {
			statoPaytas = StatiPagamentiIris.IN_ERRORE;
			Tracer.error(Wisp20Adapter.class.getName(),"preparaRichiesta","Non riesco a determinare l'esito della richiesta pagamento a causa di un errore.",e);
			request.setAttribute("MSG_NODO_SPC","flusso.esitoPagamento.nodopagamenti.msg.esitoKo");
			request.setAttribute("MSG_ESITO_PAGAMENTO_TYPE", "KO");
			forward = "esitoRichiestaNodoSPC";
		}
    	
    	//-------------------------
    	// AGGIORNA STATO distinte
    	try {
			// aggiorno l'oggetto in sessione (mi serve per portare le info sulla pagina)
    		// TAROCCONE non ho una sola distinta ma N
    		// ne creo una fittizia per la visualizzazione dell'esito nella pagina esitoRichiesta.jsp
    		//************************************************
    		DistintaCartaCreditoVO distinta = new DistintaCartaCreditoVO();

    		distinta.setMezzoPagamento(EnumTipoModalitaPagamento.PAGO_PA.getChiave());
    		distinta.setCodTransazione(idGruppo);
    		//distinta.setIdFlusso(Long.parseLong(dccVo.getFlusso()));
    		//distinta.setDataTransazione(dataOraTransazione); 
  		
    		distinta.setDataOrdine(new Date());
			distinta.setStatoPagamento(statoPaytas);
			request.getSession().setAttribute(ServiceSessionConstants.distintaPagamento, distinta);
            //************************************************
			//
			// aggiorno lo stato di tutte le distinte contenute nel gruppo
			//
			Iterator<DistintaCartaCreditoVO> iterDccVo = dccVoList.iterator();
			while (iterDccVo.hasNext()) {
				
				DistintaCartaCreditoVO dccVo = iterDccVo.next();
				
				DistintaPosizioneDebitoriaDettaglioVO dettDistinta = new DistintaPosizioneDebitoriaDettaglioVO();
    			dettDistinta.setIdFlusso(Long.parseLong(dccVo.getFlusso()));
    			dettDistinta.setStato(statoPaytas.getFludMapping());
    			dettDistinta.setStPagamento(statoPaytas.getPagaMapping());
    			dettDistinta.setDeOperazione("NDP_RICHIESTA_RPT: richiesta inviata - update stato distinta a: "	+ dettDistinta.getStato());
    			
    			aggiornaEsito(dettDistinta);
			}

		} catch (Throwable e) {
			Tracer.error(Wisp20Adapter.class.getName(), "preparaRichiesta", "ERRORE", e);
			throw new RuntimeException("ERRORE GRAVE", e);
		}

    	
    	//-------------------------
    	return forward;
    
	}
	static protected void aggiornaEsito(DistintaPosizioneDebitoriaDettaglioVO dettDistinta) throws it.tasgroup.iris.shared.util.locator.ServiceLocatorException {
		DistintePagamentoFacade dpBean = (DistintePagamentoFacade) ServiceLocator.getSLSBProxy("distintePagamentoFacadeBean");
		dpBean.aggiornaEsito(dettDistinta.getIdFlusso().longValue(), StatiPagamentiIris.getStatiPagamentiIrisFromFlud(dettDistinta.getStato()), dettDistinta.getTranId(), dettDistinta.getDeOperazione(),dettDistinta.getTipoIdentificativoAttestante(),dettDistinta.getIdentificativoAttestante(),dettDistinta.getDescrizioneAttestante());
	}
	/**
    *
    * @param codTransazione
    * @param idGruppo
    * @param sscI
    * @return
    */
	private DistintaCartaCreditoVO creaDistintaCartaCreditoCarrello(String codTransazione, String idGruppo, SessionShoppingCartItemDTO sscI, String codTransazionePSP, Locale locale, GatewayProfileManager profile, CfgGatewayPagamentoDTO cfgPagamento) {

       ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("nodopagamenti.properties");


		
		DistintaCartaCreditoVO distintaCC = new DistintaCartaCreditoVO();
		
		//***** creo la disposizione*****
		
		DisposizioneCartaCreditoVO disposizioneCC = new DisposizioneCartaCreditoVO();
		disposizioneCC.setBusinessService(BUSINESS_SERVICE);
		disposizioneCC.setPagamentoVO(sscI);
		disposizioneCC.setIdPagamento(sscI.getIdPagamento());
		disposizioneCC.setImportoPagato(sscI.getImporto());
		disposizioneCC.setDescrizione(sscI.getEnte() + ", " + sscI.getCausale() + ", ID=" + sscI.getIdPagamento());
		disposizioneCC.setOperatore(profile.getUsername()!=null?profile.getUsername():"ANONIMO");
		//***** inserisco la disposizione  nella distinta*****
		distintaCC.addDisposizione(disposizioneCC);
	
		distintaCC.setCodTransazione(codTransazionePSP);
		distintaCC.setIUV(codTransazione);
		
		//distintaCC.setCodPagamento(codTransazione);
		distintaCC.setCodPagamento(idGruppo);
		distintaCC.setIdGruppo(idGruppo);
		distintaCC.setIdFiscaleCreditore(sscI.getIdFiscaleEnte());
		distintaCC.setCausaleDistinta("PAGONLINERTIRIS");
		//distintaCC.setDataOrdine(dataOrdine);
		distintaCC.setDataTransazione(distintaCC.getDataOrdine());
		
		//distintaCC.setImportoCommissioni(importoCommissioni);     //TODO ERRATO... da verificare
		distintaCC.setImportoCommissioni(new BigDecimal(0.0));
		//distintaCC.setTotImportiPositivi(importoTotalePagamenti); //TODO ERRATO... da verificare
		distintaCC.setTotImportiPositivi(sscI.getImporto());
		//distintaCC.setTotale(importoConCommissioni);              //TODO ERRATO... da verificare
		distintaCC.setTotale(sscI.getImporto());
		
		distintaCC.setMezzoPagamento(cfgPagamento.getCfgModalitaPagamento().getId());
		distintaCC.setIdCfgGateway(Long.toString(cfgPagamento.getId()));
		distintaCC.setCodTransazionePSP(codTransazionePSP);

		distintaCC.setTipoSoggettoVers(sscI.getTipoSoggettoVers());
		distintaCC.setCodFiscaleVers(sscI.getCodFiscaleVers());
		distintaCC.setAnagraficaVers(sscI.getAnagraficaVers());
		distintaCC.setIndirizzoVers(sscI.getIndirizzoVers());
		distintaCC.setNumeroCivicoVers(sscI.getNumeroCivicoVers());
		distintaCC.setCapVers(sscI.getCapVers());
		distintaCC.setLocalitaVers(sscI.getLocalitaVers());
		distintaCC.setProvinciaVers(sscI.getProvinciaVers());
		distintaCC.setNazioneVers(sscI.getNazioneVers());
		
		if (locale != null)
			distintaCC.setLocale(locale.getLanguage() + "_" + locale.getCountry());
		
		try {
		
			DistintePagamentoFacade dpBean = (DistintePagamentoFacade) ServiceLocator
					.getSLSBProxy("distintePagamentoFacadeBean");
			if (profile.getCodFiscalePagante()==null){
				profile.setCodFiscalePagante("ANONYMOUS");
			}
			if (profile.getEmailPagante()==null){
				profile.setEmailPagante("nobody@nomail.it");
			}
			distintaCC = dpBean.preparaPagamento(profile, distintaCC);

		} catch (Throwable e) {
			Tracer.error(Wisp20Adapter.class.getName(), "preparaRichiesta",
					"ERRORE nella creazione distinta", e);
			throw new RuntimeException("ERRORE", e);
		}

		return distintaCC;
	}
	/*
    *
    */
	private String creaRichiestaPagamentoTelematicoCartaCreditoCarrello(
			                                                   HttpServletRequest request,
			                                                   DistintaCartaCreditoVO distinta,
			                                                   SessionShoppingCartItemDTO sscI,
			                                                   String iuv, CfgGatewayPagamentoDTO cfgPagamento,GatewayProfileManager profile) {

		
		
		String ibanAddebito = request.getParameter("ibanAddebito");
		if (ibanAddebito != null) {
			ibanAddebito = ibanAddebito.toUpperCase();
		}
		// sovrascrivo l'iban beneficiario contenuto nell'elemento del carrello
       sscI.setIbanBeneficiario(calcolaIbanBeneficiario(sscI.getIdEnte(),   sscI.getIdTributoEnte() ,  sscI.getIdCondizione(),cfgPagamento));
       sscI.setIbanAppoggio(calcolaIbanAppoggio(sscI.getIdEnte(),sscI.getIdTributoEnte()));
   	
       FrontEndContext fec = WebUtil.getLocatedFrontEndContext(request);
		@SuppressWarnings("deprecation")
		OperatoriPojo operatorePojo = (OperatoriPojo) fec.getOperatore()
				.getPojo();

		OperatoreDTO operatore = new OperatoreDTO();
		operatore.setNome(operatorePojo.getName());
		operatore.setCognome(operatorePojo.getSurname());

		IntestatarioDTO profilo = new IntestatarioDTO();
		profilo.setIdFiscale(profile.getCodFiscalePagante());
		profilo.setEmail(profile.getEmailPagante());
 
		sscI.setIdPagamentoEnte(iuv);
		List<SessionShoppingCartItemDTO> lsscI= new ArrayList<SessionShoppingCartItemDTO>();
		lsscI.add(sscI);
		
		//
		String richiestaPagamentoTelematico = RPTBuilder.buildRPTXmlString(/*tribEnte.getEnte(),*/
				         profilo,
				         profile.isAnonymous(),
				         operatore,
				         lsscI,
				         cfgPagamento.getTipoVersamento(),
						 sscI.getImporto(),
						 iuv,
						 distinta.getCodTransazione(),
						 distinta.getCodTransazionePSP(),
				         distinta.getDataOrdine(),
				         ibanAddebito);

		return richiestaPagamentoTelematico;
	}

	protected static Map<String, String> getQueryMap(String query) {
		String[] params = query.split("&");
		Map<String, String> map = new HashMap<String, String>();
		for (String param : params) {
			String[] nameAndValue = param.split("=");
			map.put(nameAndValue[0], nameAndValue[1]);
		}
		return map;
	}
	
	/*
    *
    */
	protected String calcolaIbanBeneficiario(String ente, String tributo, String idCondizione, CfgGatewayPagamentoDTO cfgPagamento) {
		
		try {
			
			AnagraficaEntiFacade anagBean = (AnagraficaEntiFacade) ServiceLocator
					.getSLSBProxy("anagraficaEntiFacadeBean");

			TributoEnteDTO tribEnte = anagBean.getTributoEnteByKey(ente,
					tributo);

			
			if (tribEnte.getIBANContoTecnico() != null && !tribEnte.getIBANContoTecnico().trim().equals(""))
				return tribEnte.getIBANContoTecnico();

			return tribEnte.getIBAN();
			
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return null;
	}
	
	protected String calcolaIbanAppoggio(String ente, String tributo){
		
		try {
			
			AnagraficaEntiFacade anagBean = (AnagraficaEntiFacade) ServiceLocator
					.getSLSBProxy("anagraficaEntiFacadeBean");
	
			TributoEnteDTO tribEnte = anagBean.getTributoEnteByKey(ente,
					tributo);
	
			
			if (tribEnte.getIBAN_CCP()!=null && !"".equals(tribEnte.getIBAN_CCP())) {
				return tribEnte.getIBAN_CCP();
			}
		
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return null;
	
	}
	
	private String getFaultDescription(FaultBean f) {
		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("nodopagamenti.properties");
		final boolean showLiteFault = cpl.getBooleanProperty("nodopagamentispc.esito.showLiteFault");
		if (!showLiteFault) {
				return f.getFaultCode() + " - " + f.getFaultString() + " [descrizione: " + f.getDescription() + "]";
		} else {
				return f.getDescription();
		}
}


}

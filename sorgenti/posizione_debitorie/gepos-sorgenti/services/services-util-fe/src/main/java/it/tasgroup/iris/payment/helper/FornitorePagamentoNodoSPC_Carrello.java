/**
 * 
 */
package it.tasgroup.iris.payment.helper;

import gov.telematici.pagamenti.ws.FaultBean;
import gov.telematici.pagamenti.ws.NodoChiediCopiaRT;
import gov.telematici.pagamenti.ws.NodoChiediCopiaRTRisposta;
import gov.telematici.pagamenti.ws.NodoChiediStatoRPT;
import gov.telematici.pagamenti.ws.NodoChiediStatoRPTRisposta;
import gov.telematici.pagamenti.ws.NodoInviaCarrelloRPT;
import gov.telematici.pagamenti.ws.NodoInviaCarrelloRPTRisposta;
import gov.telematici.pagamenti.ws.NodoInviaRPT;
import gov.telematici.pagamenti.ws.NodoInviaRPTRisposta;
import gov.telematici.pagamenti.ws.ppthead.IntestazioneCarrelloPPT;
import gov.telematici.pagamenti.ws.ppthead.IntestazionePPT;
import it.nch.erbweb.client.ServiceSessionConstants;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.util.Tracer;
import it.nch.fwk.fo.web.util.WebUtil;
import it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaDettaglioVO;
import it.nch.is.fo.profilo.OperatoriPojo;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.pagamenti.creditcard.DisposizioneCartaCreditoVO;
import it.nch.pagamenti.creditcard.DistintaCartaCreditoVO;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;
import it.tasgroup.iris.dto.anagrafica.OperatoreDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgGatewayPagamentoDTO;
import it.tasgroup.iris.dto.exception.IncompleteRegistrationException;
import it.tasgroup.iris.facade.ejb.client.flussi.DistintePagamentoFacade;
import it.tasgroup.iris.nodopagamentispc.util.RPTUtil;
import it.tasgroup.iris.sessioncart.ShoppingCartSessionHelper;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;
import it.tasgroup.iris.util.WrapperRPTInvoker;
import it.tasgroup.services.util.enumeration.EnumStatoRPT;
import it.tasgroup.services.util.enumeration.EnumTipoModalitaPagamento;
import it.tasgroup.services.util.idgenerator.IDGenerator;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;

/**
 * @author pazzik
 *
 */
public class FornitorePagamentoNodoSPC_Carrello extends FornitorePagamentoNodoSPC {
	
	FornitorePagamentoNodoSPC_Carrello(){

	}

	@Override 
	protected String preparaRichiesta(HttpServletRequest request,
			HttpServletResponse response) throws PagamentoException,
			IncompleteRegistrationException {

		Properties beProperties = ConfigurationPropertyLoader.getProperties("iris-fe.properties");
        
    	boolean nodoPagamenti1_7 =beProperties.getProperty("iris.pagamenti.nodoPagamenti1_7").equals("true");
		
		Tracer.debug(className, "inner preparaRichiesta", "START");

		String forward;

		inizializzaAnagraficaVersante(request); 

		verificaSoggettoVersante();

		// TODO PAZZIK: VERIFICARE SE SERVE VERIFICA ANAGRAFICA PAGATORE
		//
		// per pagamento con RID devo scegliere l'IBAN di Addebito
		//
		forward = gestisciIbanAddebito(request); 

		if (forward != null)

			return forward;

		//
		// creazione distinta - inserimento su DB di DISTINTA, PAGAMENTO e
		// PAGAMENTO_ONLINE (SEMPRE in stato IN_CORSO)
		//
		
		// VERIFICO SE IL CARRELLO CONTIENE UN SOLO PAGAMENTO O PIU DI UNO
		// siamo nel caso di Carta di Credito... pagamento immediato
        if (pagamentiWIP.size() > 1) {
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
        		//
        		
        		String codTransazione = null; 
        		String codTransazionePSP = null;
        		
        		if (!nodoPagamenti1_7) {
        			codTransazione = IDGenerator.Generate_TRANSACTION_ID();
        			codTransazionePSP = "n/a";
        		} else {
        			codTransazione = sscI.getIdPagamentoEnte();
        			codTransazionePSP = IDGenerator.Generate_TRANSACTION_ID();
        		}
                // creo distinta
        		Locale locale = (Locale)request.getSession().getAttribute(Globals.LOCALE_KEY);
        		DistintaCartaCreditoVO dccVo= creaDistintaCartaCreditoCarrello(codTransazione, idGruppo, sscI, codTransazionePSP, locale);
        		
        		
        	    // creo rpt 
        		String currentRpt =  creaRichiestaPagamentoTelematicoCartaCreditoCarrello(request, dccVo, sscI,codTransazione,cfgPagamento);
        	    //
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
        	
        	StatiPagamentiIris statoIris;
        	try {
    			Tracer.debug(className, "preparaRichiesta", "calling nodoInviaCarrelloRPT con RT:");
    			
    			NodoInviaCarrelloRPTRisposta risposta = new WrapperRPTInvoker().nodoInviaCarrelloRPT(bodyRichiestaCarrello, headerCarrello);
    			
    			//
    			// elaborazione risposta del nodo
    			//
    			if (risposta.getFault() != null || risposta.getListaErroriRPT()!=null) {

    				Tracer.debug(className, "preparaRichiesta","FAULT nella risposta");
    				// TODO: gestire il mapping dei codici di fault??
    				statoIris = StatiPagamentiIris.IN_ERRORE;
    				if (risposta.getFault()!=null) {
    				  request.setAttribute("FAULT_NODO_SPC", risposta.getFault().getFaultCode() + " - " + risposta.getFault().getFaultString() + " [descrizione: " + risposta.getFault().getDescription() + "]");
    				} else {
    					if (risposta.getListaErroriRPT()!=null) {
    						String descriz = new String("");
    						int l = risposta.getListaErroriRPT().getFault().length;
    						for (int i=0; i< l;i++){
    							FaultBean f = risposta.getListaErroriRPT().getFault(i);
    							if (f!=null){
    								descriz=descriz+ f.getFaultCode() + " - " + f.getFaultString() + " [descrizione: " + f.getDescription() + "]"; 
    							}
    						}
    						request.setAttribute("FAULT_NODO_SPC", descriz);
    					}
    				}
    				  request.setAttribute("MSG_NODO_SPC", "flusso.esitoPagamento.nodopagamenti.msg.esitoKo");
    				//-------
    				if ("RegioneToscana".equals(irisCustomer)) {
    				   ShoppingCartSessionHelper.removeCartFromSession(request);
    				}
    				//------
    				forward = "esitoRichiestaNodoSPC";

    			} else {

    				if (risposta.getUrl() != null && !risposta.getUrl().equals("")) { // TEST PAGAMENTO DIFFERITO....
    					Tracer.debug(className, "preparaRichiesta","pagamento IMMEDIATO");
    					// pagamento immediato - redirect alla pagina del nodo
    					statoIris = StatiPagamentiIris.IN_CORSO;
    					try {
    						URL url = new URL(risposta.getUrl());
    						Map<String, String> queryMap = getQueryMap(url.getQuery());
    						String idSessioneNDP = queryMap.get("idSession");
    						request.getSession().setAttribute("idSessioneNDP",idSessioneNDP);

    					} catch (MalformedURLException e) {
    						
    						request.getSession().removeAttribute("idSessioneNDP");
    						// TODO: cosa faccio se mi arriva un URL non ben
    						// formato?
    						Tracer.error(className, "preparaRichiesta","ERRORE ho ricevuto un URL non ben formato", e);
    					}
    					Tracer.debug(className, "preparaRichiesta",
    							"redirezione a: " + risposta.getUrl());
    					request.setAttribute("URL_TO_REDIRECT", risposta.getUrl());    					
    					forward = "redirectionNodoSPC";

    				} else {
    					Tracer.debug(className, "preparaRichiesta",
    							"Pagamento DIFFERITO terminato");
    					// pagamento differito - richiesta ok
    					statoIris = StatiPagamentiIris.ESEGUITO_SBF;
    					request.setAttribute("MSG_NODO_SPC","flusso.esitoPagamento.nodopagamenti.msg.esitoOkSbf");
    					ShoppingCartSessionHelper.removeCartFromSession(request);
    					forward = "esitoRichiestaNodoSPC";
    				}
    			}

    		} catch (RemoteException e) {
    			// lascio la distinta IN CORSO
    			statoIris = StatiPagamentiIris.IN_CORSO;
    			Tracer.error(className,"preparaRichiesta","Non riesco a determinare l'esito della richiesta pagamento a causa di un errore.",e);
    			request.setAttribute("MSG_NODO_SPC","flusso.esitoPagamento.nodopagamenti.msg.esitoNonDeterminato");
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
        		distinta.setMezzoPagamento(cfgPagamento.getCfgModalitaPagamento().getId());
        		distinta.setCodTransazione(idGruppo);
        		//distinta.setDataTransazione(dataOraTransazione); TODO ?????
    			distinta.setStatoPagamento(statoIris);
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
        			dettDistinta.setStato(statoIris.getFludMapping());
        			dettDistinta.setStPagamento(statoIris.getPagaMapping());
        			dettDistinta.setDeOperazione("NDP_RICHIESTA_RPT: richiesta inviata - update stato distinta a: "	+ dettDistinta.getStato());
        			
        			aggiornaEsito(dettDistinta);
    			}    			

    		} catch (Throwable e) {
    			Tracer.error(className, "preparaRichiesta", "ERRORE", e);
    			throw new RuntimeException("ERRORE GRAVE", e);
    		}

        	
        	//-------------------------
        } else {
		
		
        	String codTransazione = null;
            String codTransazionePsp = null;
            
        	if (!nodoPagamenti1_7) {
        		codTransazione = IDGenerator.Generate_TRANSACTION_ID();
        		codTransazionePsp = "n/a";
    		} else {
    			codTransazione = pagamentiWIP.get(0).getIdPagamentoEnte();
    			codTransazionePsp = IDGenerator.Generate_TRANSACTION_ID();
    		}
        	DistintaCartaCreditoVO distinta = creaDistinta(request, codTransazione,codTransazionePsp); 

        	//
        	// creazione richiesta (solo xml RPT) - la creo prima di salvare la
        	// distinta in modo da poter ritentare il pagamento in caso di errori
        	//
        	String rptString = creaRichiestaPagamentoTelematicoCartaCreditoCarrello(request, 
        	                		                                                distinta,
        	                		                                                pagamentiWIP.get(0),
        	                		                                                codTransazione,
        	                		                                                cfgPagamento);
        	                		                                                
        	Tracer.debug(className, "preparaRichiesta", "RPT da inviare");
        	Tracer.debug(className, "preparaRichiesta", rptString);

        	//
        	// invio richiesta al Nodo
        	//

        	NodoInviaRPT bodyrichiesta = RPTUtil.buildBodyInviaRPT(
        			cfgPagamento.getSystemId(), cfgPagamento.getSubsystemId(),
        			cfgPagamento.getApplicationId(), rptString.getBytes());

        	String idDominio = distinta.getIdFiscaleCreditore();
        	                     
//        	if (conf.getBooleanProperty("nodopagamentispc.modalitaRT")==true) {
//        		idDominio = FornitorePagamentoNodoSPC.ID_FISCALE_RT;
//        		distinta.setIdFiscaleCreditore(FornitorePagamentoNodoSPC.ID_FISCALE_RT);
//        	}

        	IntestazionePPT header = RPTUtil.buildIntestazioneRichiesta(
        			codTransazione,
        			codTransazionePsp,
        			idDominio);

        	StatiPagamentiIris statoIris;

        	try {
        		Tracer.debug(className, "preparaRichiesta",
        				"calling nodoInviaRPT con RT:");
        		Tracer.debug(className, "preparaRichiesta", rptString);
        		
        		NodoInviaRPTRisposta risposta = new WrapperRPTInvoker().nodoInviaRPT(bodyrichiesta, header);

        		Tracer.debug(className, "preparaRichiesta", "esito risposta: "
        				+ risposta.getEsito());
        		//
        		// elaborazione risposta del nodo
        		//
        		if (risposta.getFault() != null) {

        			Tracer.debug(className, "preparaRichiesta","FAULT nella risposta");
        			// TODO: gestire il mapping dei codici di fault??
        			statoIris = StatiPagamentiIris.IN_ERRORE;
        			request.setAttribute("FAULT_NODO_SPC", risposta.getFault().getFaultCode()
        					+ " - "
        					+ risposta.getFault().getFaultString()
        					+ " [descrizione: "
        					+ risposta.getFault().getDescription() + "]");
        			request.setAttribute("MSG_NODO_SPC",
        					"flusso.esitoPagamento.nodopagamenti.msg.esitoKo");
        			//-------
        			if ("RegioneToscana".equals(irisCustomer)) {
        				ShoppingCartSessionHelper.removeCartFromSession(request);
        			}
        			//------
        			forward = "esitoRichiestaNodoSPC";

        		} else {

        			if (risposta.getRedirect().intValue() == 1) {
        				Tracer.debug(className, "preparaRichiesta",
        						"pagamento IMMEDIATO");
        				// pagamento immediato - redirect alla pagina del nodo
        				statoIris = StatiPagamentiIris.IN_CORSO;
        				try {
        					URL url = new URL(risposta.getUrl());
        					Map<String, String> queryMap = getQueryMap(url
        							.getQuery());
        					String idSessioneNDP = queryMap.get("idSession");
        					request.getSession().setAttribute("idSessioneNDP",
        							idSessioneNDP);

        				} catch (MalformedURLException e) {

        					request.getSession().removeAttribute("idSessioneNDP");
        					Tracer.error(className, "preparaRichiesta",
        							"ERRORE ho ricevuto un URL non ben formato", e);
        				}
        				Tracer.debug(className, "preparaRichiesta",
        						"redirezione a: " + risposta.getUrl());
        				request.setAttribute("URL_TO_REDIRECT", risposta.getUrl());
        				forward = "redirectionNodoSPC";

        			} else {
        				Tracer.debug(className, "preparaRichiesta","Pagamento DIFFERITO terminato");
        				// pagamento differito - richiesta ok
        				statoIris = StatiPagamentiIris.ESEGUITO_SBF;
        				request.setAttribute("MSG_NODO_SPC","flusso.esitoPagamento.nodopagamenti.msg.esitoOkSbf");
        				ShoppingCartSessionHelper.removeCartFromSession(request);
        				forward = "esitoRichiestaNodoSPC";
        			}
        		}

        	} catch (RemoteException e) {
        		// lascio la distinta IN CORSO
        		statoIris = StatiPagamentiIris.IN_CORSO;
        		Tracer.error(className,"preparaRichiesta","Non riesco a determinare l'esito della richiesta pagamento a causa di un errore.",e);
        		request.setAttribute("MSG_NODO_SPC", "flusso.esitoPagamento.nodopagamenti.msg.esitoNonDeterminato");
        		forward = "esitoRichiestaNodoSPC";

        	}

        	//
        	// update stato dalla risposta del servizio
        	//
        	try {
        		// aggiorno l'oggetto in sessione (mi serve per portare le info
        		// sulla pagina)
        		distinta.setStatoPagamento(statoIris);
        		request.getSession().setAttribute(
        				ServiceSessionConstants.distintaPagamento, distinta);

        		
        		DistintaPosizioneDebitoriaDettaglioVO dettDistinta = new DistintaPosizioneDebitoriaDettaglioVO();
        		dettDistinta.setIdFlusso(Long.parseLong(distinta.getFlusso()));
        		dettDistinta.setStato(statoIris.getFludMapping());
        		dettDistinta.setStPagamento(statoIris.getPagaMapping());
        		dettDistinta.setDeOperazione("NDP_RICHIESTA_RPT: richiesta inviata - update stato distinta a: " + dettDistinta.getStato());
        		
        		aggiornaEsito(dettDistinta);

        	} catch (Throwable e) {
        		Tracer.error(className, "preparaRichiesta", "ERRORE", e);
        		throw new RuntimeException("ERRORE GRAVE", e);
        	}
        }
		Tracer.debug(className, "preparaRichiesta", "END - forwarding to: "
				+ forward);

		return forward;
	}



	/**
	 * Nel caso dei pagamenti da PSP l'aggiornamento delle distinte IN CORSO deve inviare la RPT
	 * relativa alla distinta creata dal "paaAttivaRPT"
	 * @see it.tasgroup.iris.payment.helper.FornitorePagamento#aggiornaStatoDistinta(it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaDettaglioVO)
	 */
	@Override
	protected boolean aggiornaStatoDistintaInCorso(DistintaPosizioneDebitoriaDettaglioVO dettDistinta) {

		Tracer.debug(getClass().getName(), "aggiornaStatoDistinta", " - BEGIN");
		return aggiornaStatoDistintaDaStatoRPToDaCopiaRT(dettDistinta);
		
	}
	
	/**
     * 
     * @param codTransazione
     * @param idGruppo
     * @param sscI
     * @return
     */	
	private DistintaCartaCreditoVO creaDistintaCartaCreditoCarrello(String codTransazione, String idGruppo, SessionShoppingCartItemDTO sscI, String codTransazionePSP, Locale locale) {

		Properties beProperties = ConfigurationPropertyLoader.getProperties("iris-fe.properties");
        
    	boolean nodoPagamenti1_7 =beProperties.getProperty("iris.pagamenti.nodoPagamenti1_7").equals("true");

		
		DistintaCartaCreditoVO distintaCC = new DistintaCartaCreditoVO();
		
		//***** creo la disposizione*****
		     
		DisposizioneCartaCreditoVO disposizioneCC = new DisposizioneCartaCreditoVO();
		disposizioneCC.setBusinessService(BUSINESS_SERVICE);
		disposizioneCC.setPagamentoVO(sscI);
		disposizioneCC.setIdPagamento(sscI.getIdPagamento());
		disposizioneCC.setImportoPagato(sscI.getImporto());
		disposizioneCC.setDescrizione(sscI.getEnte() + ", " + sscI.getCausale() + ", ID=" + sscI.getIdPagamento());
		disposizioneCC.setOperatore(profileManager.getUsername());
		//***** inserisco la disposizione  nella distinta*****  
		distintaCC.addDisposizione(disposizioneCC);
		if (!nodoPagamenti1_7) {
		   distintaCC.setCodTransazione(codTransazione);
		} else {
			distintaCC.setCodTransazione(codTransazionePSP);
			distintaCC.setIUV(codTransazione);
		}
		//distintaCC.setCodPagamento(codTransazione);
		distintaCC.setCodPagamento(idGruppo);
		distintaCC.setIdGruppo(idGruppo);
		distintaCC.setIdFiscaleCreditore(sscI.getIdFiscaleEnte());
		distintaCC.setCausaleDistinta("PAGONLINERTIRIS");
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
			
			distintaCC = dpBean.preparaPagamento(profileManager, distintaCC);

		} catch (Throwable e) {
			Tracer.error(className, "preparaRichiesta",
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
			                                                   String iuv, CfgGatewayPagamentoDTO cfgPagamento) {

		
		
		String ibanAddebito = request.getParameter("ibanAddebito");
		if (ibanAddebito != null) {
			ibanAddebito = ibanAddebito.toUpperCase();
		}
		// sovrascrivo l'iban beneficiario contenuto nell'elemento del carrello 	
        sscI.setIbanBeneficiario(calcolaIbanBeneficiario(sscI.getIdEnte(),   sscI.getIdTributoEnte() ,  sscI.getIdCondizione(),cfgPagamento));
		
        FrontEndContext fec = WebUtil.getLocatedFrontEndContext(request);
		@SuppressWarnings("deprecation")
		OperatoriPojo operatorePojo = (OperatoriPojo) fec.getOperatore()
				.getPojo();

		OperatoreDTO operatore = new OperatoreDTO();
		operatore.setNome(operatorePojo.getName());
		operatore.setCognome(operatorePojo.getSurname());

		IntestatarioDTO profilo = new IntestatarioDTO();
		profilo.setIdFiscale(profileManager.getCodFiscalePagante());
		profilo.setEmail(profileManager.getEmailPagante());
        
		sscI.setIdPagamentoEnte(iuv);// taroccone per settaggio iuv... 
		List<SessionShoppingCartItemDTO> lsscI= new ArrayList<SessionShoppingCartItemDTO>();
		lsscI.add(sscI);
		
		String richiestaPagamentoTelematico = RPTBuilder.buildRPTXmlString(
				         profilo, 
				         isAnonymous(), 
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

	protected boolean aggiornaStatoDistintaDaStatoRPToDaCopiaRT(DistintaPosizioneDebitoriaDettaglioVO dettDistinta) {

		//
		// calcolo se il pagamento � diretto o differito in base alla modalit�
		// di pagamento
		// TODO: in futuro questa informazione sar� sulla tabella di
		// configurazione CFG_GATEWAY_PAGAMENTO (modello versamento)
		//
		EnumTipoModalitaPagamento enumModalitaPagamento = EnumTipoModalitaPagamento.getByDescrizione(dettDistinta.getModalitaPagamento());
		boolean isPagamentoDiretto =  ("0".equals(dettDistinta.getModelloVersamento()) ||   "1".equals(dettDistinta.getModelloVersamento())); 

		Tracer.debug(getClass().getName(),
				"aggiornaStatoDistintaDaStatoRPToDaCopiaRT",
				" aggiorno lo stato di un pagamento "
						+ (isPagamentoDiretto ? "DIRETTO" : "DIFFERITO"));

		NodoChiediStatoRPT richiestaStatoRPT = new NodoChiediStatoRPT();
		richiestaStatoRPT.setIdentificativoIntermediarioPA(conf.getProperty("nodopagamentispc.identificativoIntermediarioPA"));
		richiestaStatoRPT.setIdentificativoStazioneIntermediarioPA(conf.getProperty("nodopagamentispc.identificativoStazioneIntermediarioPA"));
		richiestaStatoRPT.setPassword(conf.getProperty("nodopagamentispc.password"));

		String idDominio = dettDistinta.getIdFiscaleEnte();
		
		richiestaStatoRPT.setIdentificativoDominio(idDominio);
		richiestaStatoRPT.setIdentificativoUnivocoVersamento(dettDistinta.getIuv());
		richiestaStatoRPT.setCodiceContestoPagamento(dettDistinta.getCodiceContestoPagamento());

		NodoChiediStatoRPTRisposta rispostaStatoRPT;

		try {
			rispostaStatoRPT = new WrapperRPTInvoker().nodoChiediStatoRPT(richiestaStatoRPT);

		} catch (RemoteException e) {
			// non riesco a contattare il servizio
			Tracer.error(className,
					"aggiornaStatoDistintaDaStatoRPToDaCopiaRT",
					"non riesco a contattare il servizio", e);
			return false;
		}

		String stato = null;
		StatiPagamentiIris statoIris = null;
		if (rispostaStatoRPT.getFault() != null) {
			if ("PPT_RPT_SCONOSCIUTA".equals(rispostaStatoRPT.getFault().getFaultCode()) ||
				"PPT_DOMINIO_SCONOSCIUTO".equals(rispostaStatoRPT.getFault().getFaultCode())) {
				stato = rispostaStatoRPT.getFault().getFaultCode();
				statoIris = StatiPagamentiIris.ANNULLATO;
			} else {
				// problema gestito dal nodo
				Tracer.error(className,
						"aggiornaStatoDistintaDaStatoRPToDaCopiaRT",
						"fault nella risposta al nodoChiediStatoRPT: "
								+ rispostaStatoRPT.getFault().getFaultString());
				return false;
			}
		}

		if (stato == null) {
			stato = rispostaStatoRPT.getEsito().getStato();
			Tracer.debug(className,
					"aggiornaStatoDistintaDaStatoRPToDaCopiaRT",
					"nodoChiediStatoRPT ha restituito un esito: " + stato);

			EnumStatoRPT enumStato = EnumStatoRPT.valueOf(stato);
			Tracer.debug(className,
					"aggiornaStatoDistintaDaStatoRPToDaCopiaRT",
					"descrizione esito: " + enumStato.getDescrizione());

			statoIris = isPagamentoDiretto ? enumStato
					.getStatoPagamentoDiretto() : enumStato
					.getStatoPagamentoDifferito();
			Tracer.debug(className,
					"aggiornaStatoDistintaDaStatoRPToDaCopiaRT", "statoIris: "
							+ statoIris);

			if (enumStato.equals(EnumStatoRPT.RT_ACCETTATA_PA) || 
				enumStato.equals(EnumStatoRPT.RT_ACCETTATA_NODO) || 
				enumStato.equals(EnumStatoRPT.RT_ESITO_SCONOSCIUTO_PA)) {
				// esiste sicuramente una ricevuta
				return aggiornaStatoDistintaDaCopiaRT(dettDistinta);
			}

		}

		String descOp = null;
		//
		// se la distinta � in corso da un tempo superiore al timeout allora
		// forzo l'annullamento
		// processo disabilitabile by properties
		// "nodopagamentispc.abilita.annullamento.incorso"
		//
		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("nodopagamenti.properties");
		if (cpl.getBooleanProperty("nodopagamentispc.abilita.annullamento.incorso")) {
			int minutiTimeout = cpl.getIntProperty("nodopagamentispc.timeout.annullamento.incorso");
			if (StatiPagamentiIris.IN_CORSO.equals(statoIris)
					&& new Date().getTime()	- dettDistinta.getDataTransazione().getTime() > minutiTimeout * 60 * 1000) {
				Tracer.info(
						className,
						"aggiornaStatoDistintaDaStatoRPToDaCopiaRT",
						"TIMEOUT IN_CORSO della distinta ID: "
								+ dettDistinta.getIdFlusso());
				statoIris = StatiPagamentiIris.ANNULLATO;
				descOp = "NDP_STATO_RPT [TIMEOUT] - stato esito nodo: " + stato
						+ " --> update stato distinta a: "
						+ statoIris.getFludMapping();
			}
		}

		boolean continuaPagamento = StatiPagamentiIris.IN_ERRORE
				.equals(statoIris)
				|| StatiPagamentiIris.ANNULLATO.equals(statoIris)
				|| StatiPagamentiIris.ANNULLATO_DA_OPERATORE.equals(statoIris)
				|| StatiPagamentiIris.NON_ESEGUITO.equals(statoIris);

		Tracer.debug(className, "aggiornaStatoDistintaDaStatoRPToDaCopiaRT",
				"continuaPagamento: " + continuaPagamento);

		return salvaDistinta(dettDistinta, statoIris, stato, continuaPagamento,
				descOp);

	}

	/**
	 * aggiorna lo stato della distinta + pagamenti + tracciamento su
	 * pagamenti_online facendo la richiesta al nodo di una copia della RT
	 * (ricevuta telematica)
	 * 
	 * @param dettDistinta
	 * @return true o false a seconda che sia stato possibile fare
	 *         l'aggiornamento o no
	 */
	private boolean aggiornaStatoDistintaDaCopiaRT(
			DistintaPosizioneDebitoriaDettaglioVO dettDistinta) {

		try {

			NodoChiediCopiaRT richiestaCopiaRT = new NodoChiediCopiaRT();
			richiestaCopiaRT.setIdentificativoIntermediarioPA(conf.getProperty("nodopagamentispc.identificativoIntermediarioPA"));
			richiestaCopiaRT.setIdentificativoStazioneIntermediarioPA(conf.getProperty("nodopagamentispc.identificativoStazioneIntermediarioPA"));
			richiestaCopiaRT.setPassword(conf.getProperty("nodopagamentispc.password"));

			String idDominio = dettDistinta.getIdFiscaleEnte();
		
			richiestaCopiaRT.setIdentificativoDominio(idDominio);
			
			richiestaCopiaRT.setIdentificativoUnivocoVersamento(dettDistinta.getIuv());

			// TODO:MINO da eliminare. Solo per evitare problemi di
			// compatibilit� con le prime distinte create senza codTransazione
			// PSP
			String ccp = dettDistinta.getCodiceContestoPagamento() == null
					|| dettDistinta.getCodiceContestoPagamento().isEmpty() ? conf.getProperty("nodopagamentispc.codiceContestoPagamento")
					: dettDistinta.getCodiceContestoPagamento();
			
			richiestaCopiaRT.setCodiceContestoPagamento(ccp);

			NodoChiediCopiaRTRisposta rispostaCopiaRT = new WrapperRPTInvoker().nodoChiediCopiaRT(richiestaCopiaRT);
			
			FaultBean faultRDP = rispostaCopiaRT.getFault();

			if (faultRDP != null) {
				Tracer.debug(className, "aggiornaStatoDistintaDaCopiaRT",
						"Fault nella risposta al NodoChiediCopiaRPT: "
								+ rispostaCopiaRT.getFault().getFaultCode()
								+ " - "
								+ rispostaCopiaRT.getFault().getFaultString());
				Tracer.debug(className, "aggiornaStatoDistintaDaCopiaRT",
						"Descrizione: "
								+ rispostaCopiaRT.getFault().getDescription());
				// cos� traccio l'operazione sulla PAGAMENTI_ONLINE mantenendo
				// lo stesso stato
				return salvaDistinta(dettDistinta,
						StatiPagamentiIris
								.getStatiPagamentiIrisFromFlud(dettDistinta
										.getStato()), "Fault", false);

			} else {
				Tracer.debug(className, "aggiornaStatoDistintaDaCopiaRT",
						"Successo nella risposta al NodoChiediCopiaRPT: "
								+ rispostaCopiaRT.getRt());

				String xmlString = new String(rispostaCopiaRT.getRt());
				// unmarshall di RT e aggiornamento dei pagamenti dall'esito
				// ricevuto
				String nuovoStato = FornitorePagamentoNodoSPC
						.aggiornaPagamentiDallaRT(xmlString);
				Tracer.debug(className, "aggiornaStatoDistintaDaCopiaRT",
						"Aggiornamento dati DISTINTA terminato con successo - nuovo stato: "
								+ nuovoStato);
				if (nuovoStato == null) {
					Tracer.error(
							className,
							"aggiornaStatoDistintaDaCopiaRT",
							"NON RIESCO AD AGGIORNARE DALLA RT PERCHE' POTREBBE ESSERE RELATIVA AD UNA DISTINTA CHE NON CONOSCO");
					return false;
					// throw new
					// Exception("NON RIESCO AD AGGIORNARE DALLA RT PERCHE' POTREBBE ESSERE RELATIVA AD UNA DISTINTA CHE NON CONOSCO ...");
				}
				return true;

			}

		} catch (Exception e) {
			Tracer.error(className, "aggiornaStatoDistintaDaCopiaRT", "ERRORE",
					e);
			return false;
		}

	}

}

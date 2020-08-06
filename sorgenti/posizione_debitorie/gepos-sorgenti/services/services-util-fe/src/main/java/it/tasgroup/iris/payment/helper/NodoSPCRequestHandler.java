package it.tasgroup.iris.payment.helper;

import gov.telematici.pagamenti.ws.*;
import gov.telematici.pagamenti.ws.ppthead.IntestazioneCarrelloPPT;
import gov.telematici.pagamenti.ws.ppthead.IntestazionePPT;
import it.nch.fwk.fo.util.Tracer;
import it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaDettaglioVO;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.pagamenti.creditcard.DisposizioneCartaCreditoVO;
import it.nch.pagamenti.creditcard.DistintaCartaCreditoVO;
import it.nch.profile.IProfileManager;
import it.tasgroup.idp.rs.enums.EnumModelloPagamento;
import it.tasgroup.iris.dto.CondizionePagamentoDTO;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;
import it.tasgroup.iris.dto.anagrafica.OperatoreDTO;
import it.tasgroup.iris.dto.anagrafica.TributoEnteDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgGatewayPagamentoDTO;
import it.tasgroup.iris.dto.exception.IncompleteRegistrationException;
import it.tasgroup.iris.facade.ejb.client.anagrafica.AnagraficaEntiFacade;
import it.tasgroup.iris.facade.ejb.client.flussi.DistintePagamentoFacade;
import it.tasgroup.iris.nodopagamentispc.util.RPTUtil;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;
import it.tasgroup.iris.shared.util.locator.ServiceLocatorException;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;
import it.tasgroup.iris.util.WrapperRPTInvoker;
import it.tasgroup.services.util.enumeration.EnumStatoRPT;
import it.tasgroup.services.util.enumeration.EnumTipoModalitaPagamento;
import it.tasgroup.services.util.idgenerator.IDGenerator;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;

import static it.tasgroup.iris.payment.helper.FornitorePagamento.aggiornaEsito;
import static it.tasgroup.iris.payment.helper.FornitorePagamentoNodoSPC.getQueryMap;
import static it.tasgroup.iris.payment.helper.FornitorePagamentoNodoSPC.salvaDistinta;


public class NodoSPCRequestHandler {
    private static final String BUSINESS_SERVICE = "POSIZIONEDEBITORIA";

    private final String className = this.getClass().getName();

    private CfgGatewayPagamentoDTO cfgPagamento;

    private IProfileManager profileManager;

    private OperatoreDTO operatore;

    private BigDecimal importoTotalePagamenti;

    private BigDecimal importoCommissioni;

    private BigDecimal importoConCommissioni;

    private static final ConfigurationPropertyLoader conf = ConfigurationPropertyLoader
            .getInstance("nodopagamenti.properties");


    private List<SessionShoppingCartItemDTO> pagamentiWIP;

    public NodoSPCRequestHandler(List<SessionShoppingCartItemDTO> pagamentiWIP,
                                 CfgGatewayPagamentoDTO cfgDto,
                                 IProfileManager profileManager, OperatoreDTO operatore) {

        this.cfgPagamento = cfgDto;
        this.profileManager = profileManager;
        this.pagamentiWIP = pagamentiWIP;
        this.operatore = operatore;
        inizializzaImporti();
    }

    private void inizializzaImporti() {

        importoTotalePagamenti = BigDecimal.ZERO;

        for (SessionShoppingCartItemDTO cartItem : pagamentiWIP) {
            importoTotalePagamenti = importoTotalePagamenti.add(cartItem.getImporto());
        }

        importoTotalePagamenti = importoTotalePagamenti.setScale(2, RoundingMode.HALF_UP);

//      BigDecimal importoTotale = cfgPagamento.getCfgModalitaPagamento().getImportoTotale();
        importoConCommissioni = importoTotalePagamenti.add(BigDecimal.ZERO);

        importoConCommissioni = importoConCommissioni.setScale(2, RoundingMode.HALF_UP);

        importoCommissioni = importoConCommissioni.subtract(importoTotalePagamenti);  

        importoCommissioni = importoCommissioni.setScale(2, RoundingMode.HALF_UP);

    }


    public PaymentOutput preparaRichiesta(PaymentInput paymentInput) throws PagamentoException, IncompleteRegistrationException, ServiceLocatorException {
        Tracer.debug(className, "inner preparaRichiesta", "START");

        
		Properties beProperties = ConfigurationPropertyLoader.getProperties("iris-fe.properties");
        
    	boolean nodoPagamenti1_7 =beProperties.getProperty("iris.pagamenti.nodoPagamenti1_7").equals("true");
		
        PaymentOutput paymentOutput = new PaymentOutput();

        String modelloVersamento = cfgPagamento.getModelloVersamento();

        if (! ((EnumModelloPagamento.IMMEDIATO.getChiave().equals(modelloVersamento)) ||
                (EnumModelloPagamento.IMMEDIATO_CARRELLO.getChiave().equals(modelloVersamento)) )) {
            paymentOutput.setForward(null);
            return paymentOutput;
        }

        // creazione distinta - inserimento su DB di DISTINTA, PAGAMENTO e
        // PAGAMENTO_ONLINE (SEMPRE in stato IN_CORSO)
        //
        // VERIFICO SE IL CARRELLO CONTIENE UN SOLO PAGAMENTO O PIU DI UNO
        // siamo nel caso di Carta di Credito... pagamento immediato
        if (pagamentiWIP.size() > 1) {
            // genero id_gruppo per individuare il carrello
            String idGruppo = IDGenerator.Generate_TRANSACTION_ID();
            Iterator<SessionShoppingCartItemDTO> ssCIter = pagamentiWIP.iterator();

            List<DistintaCartaCreditoVO> dccVoList = new ArrayList<DistintaCartaCreditoVO>();
            List<String> rptList = new ArrayList<String>();
            List<String> iuvList = new ArrayList<String>();
            List<String> idFiscCreditoreList = new ArrayList<String>();
            List<String> codContestoPagList        = new ArrayList<String>();
        	

            while (ssCIter.hasNext()) {

                SessionShoppingCartItemDTO sscI = ssCIter.next();
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
                DistintaCartaCreditoVO dccVo = creaDistintaCartaCreditoCarrello(codTransazione, idGruppo, sscI);
                // creo rpt
                String currentRpt = creaRichiestaPagamentoTelematicoCartaCreditoCarrello(paymentInput, dccVo, sscI, codTransazione, cfgPagamento);
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
                if (risposta.getFault() != null) {

                    Tracer.debug(className, "preparaRichiesta", "FAULT nella risposta");
                    // TODO: gestire il mapping dei codici di fault??
                    statoIris = StatiPagamentiIris.IN_ERRORE;
                    // request.setAttribute("FAULT_NODO_SPC", risposta.getFault().getFaultCode() + " - " + risposta.getFault().getFaultString() + " [descrizione: " + risposta.getFault().getDescription() + "]");
                    paymentOutput.setFaultNodoSPC(risposta.getFault().getFaultCode() + " - " + risposta.getFault().getFaultString() + " [descrizione: " + risposta.getFault().getDescription() + "]");
//                    request.setAttribute("MSG_NODO_SPC", "flusso.esitoPagamento.nodopagamenti.msg.esitoKo");
                    paymentOutput.setMsgNodoSpc("flusso.esitoPagamento.nodopagamenti.msg.esitoKo");

                    //-------
/*
                    if (!"RegioneToscana".equals(irisCustomer)) {
    				   ShoppingCartSessionHelper.removeCartFromSession(request);
    				}
*/
                    //------
//    				forward = "esitoRichiestaNodoSPC";
                    paymentOutput.setForward("esitoRichiestaNodoSPC");

                } else {

                    if (risposta.getUrl() != null && !risposta.getUrl().equals("")) { // TEST PAGAMENTO DIFFERITO....
                        Tracer.debug(className, "preparaRichiesta", "pagamento IMMEDIATO");
                        // pagamento immediato - redirect alla pagina del nodo
                        statoIris = StatiPagamentiIris.IN_CORSO;
                        try {
                            URL url = new URL(risposta.getUrl());
                            Map<String, String> queryMap = getQueryMap(url.getQuery());
                            String idSessioneNDP = queryMap.get("idSession");
                            // request.getSession().setAttribute("idSessioneNDP",idSessioneNDP);
                            paymentOutput.setIdSessioneNDP(idSessioneNDP);

                        } catch (MalformedURLException e) {
//    						request.getSession().removeAttribute("idSessioneNDP");
                            paymentOutput.setIdSessioneNDP(null);
                            // TODO: cosa faccio se mi arriva un URL non ben formato?
                            Tracer.error(className, "preparaRichiesta", "ERRORE ho ricevuto un URL non ben formato", e);
                        }
                        Tracer.debug(className, "preparaRichiesta", "redirezione a: " + risposta.getUrl());
                        // request.setAttribute("URL_TO_REDIRECT", risposta.getUrl());
                        paymentOutput.setUrlToRedirect(risposta.getUrl());
                        // forward = "redirectionNodoSPC";
                        paymentOutput.setForward("redirectionNodoSPC");

                    } else {
                        Tracer.debug(className, "preparaRichiesta",
                                "Pagamento DIFFERITO terminato");
                        // pagamento differito - richiesta ok
                        statoIris = StatiPagamentiIris.ESEGUITO_SBF;
                        // request.setAttribute("MSG_NODO_SPC","flusso.esitoPagamento.nodopagamenti.msg.esitoOkSbf");
                        paymentOutput.setMsgNodoSpc("flusso.esitoPagamento.nodopagamenti.msg.esitoOkSbf");
//    					ShoppingCartSessionHelper.removeCartFromSession(request);
//    					forward = "esitoRichiestaNodoSPC";
                        paymentOutput.setForward("esitoRichiestaNodoSPC");
                    }
                }

            } catch (RemoteException e) {
                // lascio la distinta IN CORSO
                statoIris = StatiPagamentiIris.IN_CORSO;
                Tracer.error(className, "preparaRichiesta", "Non riesco a determinare l'esito della richiesta pagamento a causa di un errore.", e);
//    			request.setAttribute("MSG_NODO_SPC","flusso.esitoPagamento.nodopagamenti.msg.esitoNonDeterminato");
                paymentOutput.setMsgNodoSpc("flusso.esitoPagamento.nodopagamenti.msg.esitoNonDeterminato");
//    			forward = "esitoRichiestaNodoSPC";
                paymentOutput.setForward("esitoRichiestaNodoSPC");

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
//    			request.getSession().setAttribute(ServiceSessionConstants.distintaPagamento, distinta);
                paymentOutput.setDistintaPagamento(distinta);
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
                    dettDistinta.setDeOperazione("NDP_RICHIESTA_RPT: richiesta inviata - update stato distinta a: " + dettDistinta.getStato());

                    aggiornaEsito(dettDistinta);
                }

                paymentOutput.setCodPagamento(idGruppo); // TODO da verificare

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
        	
            DistintaCartaCreditoVO distinta = creaDistinta(null, codTransazione,codTransazionePsp);

            // AGGIUNTO
            paymentOutput.setCodPagamento(distinta.getCodPagamento());

            //
            // creazione richiesta (solo xml RPT) - la creo prima di salvare la
            // distinta in modo da poter ritentare il pagamento in caso di errori
            //
            String rptString = creaRichiestaPagamentoTelematicoCartaCreditoCarrello(paymentInput,
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

                    Tracer.debug(className, "preparaRichiesta", "FAULT nella risposta");
                    // TODO: gestire il mapping dei codici di fault??
                    statoIris = StatiPagamentiIris.IN_ERRORE;


                    paymentOutput.setFaultNodoSPC(risposta.getFault().getFaultCode()
                            + " - "
                            + risposta.getFault().getFaultString()
                            + " [descrizione: "
                            + risposta.getFault().getDescription() + "]");

                    paymentOutput.setMsgNodoSpc("flusso.esitoPagamento.nodopagamenti.msg.esitoKo");
                    paymentOutput.setForward("esitoRichiestaNodoSPC");

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
                            paymentOutput.setIdSessioneNDP(idSessioneNDP);

                        } catch (MalformedURLException e) {

//        					request.getSession().removeAttribute("idSessioneNDP");
                            paymentOutput.setIdSessioneNDP(null);
                            Tracer.error(className, "preparaRichiesta",
                                    "ERRORE ho ricevuto un URL non ben formato", e);
                        }
                        Tracer.debug(className, "preparaRichiesta",
                                "redirezione a: " + risposta.getUrl());
//        				request.setAttribute("URL_TO_REDIRECT", risposta.getUrl());
                        paymentOutput.setUrlToRedirect(risposta.getUrl());
//        				forward = "redirectionNodoSPC";
                        paymentOutput.setForward("redirectionNodoSPC");

                    } else {
                        Tracer.debug(className, "preparaRichiesta", "Pagamento DIFFERITO terminato");
                        // pagamento differito - richiesta ok
                        statoIris = StatiPagamentiIris.ESEGUITO_SBF;
//        				request.setAttribute("MSG_NODO_SPC","flusso.esitoPagamento.nodopagamenti.msg.esitoOkSbf");
                        paymentOutput.setMsgNodoSpc("flusso.esitoPagamento.nodopagamenti.msg.esitoOkSbf");
//        				ShoppingCartSessionHelper.removeCartFromSession(request);
//        				forward = "esitoRichiestaNodoSPC";
                        paymentOutput.setForward("esitoRichiestaNodoSPC");
                    }
                }

            } catch (RemoteException e) {
                // lascio la distinta IN CORSO
                statoIris = StatiPagamentiIris.IN_CORSO;
                Tracer.error(className, "preparaRichiesta", "Non riesco a determinare l'esito della richiesta pagamento a causa di un errore.", e);
//        		request.setAttribute("MSG_NODO_SPC", "flusso.esitoPagamento.nodopagamenti.msg.esitoNonDeterminato");
                paymentOutput.setMsgNodoSpc("flusso.esitoPagamento.nodopagamenti.msg.esitoNonDeterminato");
//        		forward = "esitoRichiestaNodoSPC";
                paymentOutput.setForward("esitoRichiestaNodoSPC");

            }

            //
            // update stato dalla risposta del servizio
            //
            try {
                // aggiorno l'oggetto in sessione (mi serve per portare le info
                // sulla pagina)
                distinta.setStatoPagamento(statoIris);

                paymentOutput.setDistintaPagamento(distinta);

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
        Tracer.debug(className, "preparaRichiesta", "END - forwarding to: " + paymentOutput.getForward());

        return paymentOutput;
    }

    /**
     * @param codTransazione
     * @param idGruppo
     * @param sscI
     * @return
     */
    private DistintaCartaCreditoVO creaDistintaCartaCreditoCarrello(String codTransazione, String idGruppo, SessionShoppingCartItemDTO sscI) {

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

        distintaCC.setCodTransazione(codTransazione);
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
        distintaCC.setCodTransazionePSP("n/a");

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
            PaymentInput paymentInput,
            DistintaCartaCreditoVO distinta,
            SessionShoppingCartItemDTO sscI,
            String iuv, CfgGatewayPagamentoDTO cfgPagamento) {


        String ibanAddebito = paymentInput.getIbanAddebito();
        if (ibanAddebito != null) {
            ibanAddebito = ibanAddebito.toUpperCase();
        }
        // sovrascrivo l'iban beneficiario contenuto nell'elemento del carrello
        sscI.setIbanBeneficiario(calcolaIbanBeneficiario(sscI.getIdEnte(), sscI.getIdTributoEnte(), sscI.getIdCondizione(), cfgPagamento));
 
        IntestatarioDTO profilo = new IntestatarioDTO();
        profilo.setIdFiscale(profileManager.getCodFiscalePagante());
        profilo.setEmail(profileManager.getEmailPagante());

        sscI.setIdPagamentoEnte(iuv);// taroccone per settaggio iuv...
        List<SessionShoppingCartItemDTO> lsscI = new ArrayList<SessionShoppingCartItemDTO>();
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

    private boolean isAnonymous() {
        return true;
    }


    /*
     *
     */
  protected String calcolaIbanBeneficiario(String ente, String tributo, String idCondizione, CfgGatewayPagamentoDTO cfgPagamento) {
		
		try {
			DistintePagamentoFacade dpBean = (DistintePagamentoFacade) ServiceLocator
					.getSLSBProxy("distintePagamentoFacadeBean");

			AnagraficaEntiFacade anagBean = (AnagraficaEntiFacade) ServiceLocator
					.getSLSBProxy("anagraficaEntiFacadeBean");

			TributoEnteDTO tribEnte = anagBean.getTributoEnteByKey(ente,
					tributo);

			if (cfgPagamento.getSystemId().startsWith("BPPIITRR")) {
				if (tribEnte.getIBAN_CCP() == null || "".equals(tribEnte.getIBAN_CCP())) {
					// throws
				} else {
					return tribEnte.getIBAN_CCP();
				}
			}
			
			if ("NDP".equals(cfgPagamento.getCfgFornitoreGateway().getBundleKey()) && "OBEP".equals(cfgPagamento.getCfgModalitaPagamento().getBundleKey())) {
				if (tribEnte.getIBAN_MYBANK() == null || "".equals(tribEnte.getIBAN_MYBANK())) {
					// throws
				} else {
					return tribEnte.getIBAN_MYBANK();
				}
			}
			//
			if (tribEnte.getIBANContoTecnico() != null && !tribEnte.getIBANContoTecnico().trim().equals(""))
				return tribEnte.getIBANContoTecnico();

			CondizionePagamentoDTO condPag = dpBean
					.readCondizionePagamento(idCondizione);
			if (condPag.getIbanBeneficiario() != null)
				return condPag.getIbanBeneficiario();

			return tribEnte.getIBAN();
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return null;
	}
  
    private DistintaCartaCreditoVO creaDistinta(String codTransazione) {

        DistintaCartaCreditoVO distintaCC = new DistintaCartaCreditoVO();
        distintaCC.setCodTransazione(codTransazione);
        distintaCC.setCodPagamento(codTransazione);
        // recupero id fiscale ente
        String idFiscaleEnte = null;
        for (SessionShoppingCartItemDTO pagamentoWIP : pagamentiWIP) {

            DisposizioneCartaCreditoVO disposizioneCC = new DisposizioneCartaCreditoVO();
            disposizioneCC.setBusinessService(BUSINESS_SERVICE);
            disposizioneCC.setPagamentoVO(pagamentoWIP);
            disposizioneCC.setIdPagamento(pagamentoWIP.getIdPagamento());
            disposizioneCC.setImportoPagato(pagamentoWIP.getImporto());
            disposizioneCC.setDescrizione(pagamentoWIP.getEnte() + ", " + pagamentoWIP.getCausale() + ", ID=" + pagamentoWIP.getIdPagamento());
            disposizioneCC.setOperatore(profileManager.getUsername());
            idFiscaleEnte = pagamentoWIP.getIdFiscaleEnte();

            distintaCC.addDisposizione(disposizioneCC);

        }

        distintaCC.setIdFiscaleCreditore(idFiscaleEnte);
        distintaCC.setCausaleDistinta("PAGONLINERTIRIS");

        distintaCC.setImportoCommissioni(importoCommissioni);
        distintaCC.setTotImportiPositivi(importoConCommissioni);
        distintaCC.setMezzoPagamento(cfgPagamento.getCfgModalitaPagamento().getId());
        distintaCC.setIdCfgGateway(Long.toString(cfgPagamento.getId()));

        return distintaCC;

    }

    private DistintaCartaCreditoVO creaDistinta(HttpServletRequest request, String codTransazione,String codTransazionePsp) throws ServiceLocatorException {


		Properties beProperties = ConfigurationPropertyLoader.getProperties("iris-fe.properties");
        
    	boolean nodoPagamenti1_7 =beProperties.getProperty("iris.pagamenti.nodoPagamenti1_7").equals("true");

		
        DistintaCartaCreditoVO distinta = creaDistinta(codTransazione);
        distinta.setCodTransazionePSP(codTransazionePsp);


		if (nodoPagamenti1_7) {
			distinta.setCodTransazione(codTransazionePsp);
			distinta.setIUV(codTransazione);
			distinta.setCodPagamento(codTransazionePsp);
		}

        DistintePagamentoFacade dpBean = (DistintePagamentoFacade) ServiceLocator.getSLSBProxy("distintePagamentoFacadeBean");

        distinta.setDataTransazione(distinta.getDataOrdine());
        distinta.setTotale(importoConCommissioni);
        distinta.setImportoCommissioni(importoCommissioni);
        distinta.setTotImportiPositivi(importoTotalePagamenti);

        distinta = dpBean.preparaPagamento(profileManager, distinta);


        return distinta;
    }

}

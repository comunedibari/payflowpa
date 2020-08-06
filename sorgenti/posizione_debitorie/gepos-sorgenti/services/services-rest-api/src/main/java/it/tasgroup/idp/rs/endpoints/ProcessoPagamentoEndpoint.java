package it.tasgroup.idp.rs.endpoints;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import it.tasgroup.idp.rs.enums.EnumModelloPagamento;
import it.tasgroup.idp.rs.enums.EnumTipoVersamento;
import it.tasgroup.idp.rs.model.InfoAperturaPagamento;

//import it.nch.is.fo.profilo.Operatori;
//import it.nch.is.fo.profilo.OperatoriPojo;

import it.tasgroup.idp.rs.model.OpzionePagamentoCondizione;
import it.tasgroup.idp.rs.model.ProcessoPagamento;
import it.tasgroup.idp.rs.model.RichiestaAperturaSessioneGateway;
import it.tasgroup.idp.rs.model.RispostaAperturaSessioneGateway;
import it.tasgroup.idp.rs.util.GWRestProfile;
import it.tasgroup.iris.dto.anagrafica.OperatoreDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgGatewayPagamentoDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.iris.dto.exception.GatewayAuthenticationException;
import it.tasgroup.iris.dto.exception.InvalidInputException;
import it.tasgroup.iris.facade.ejb.client.confpagamenti.ConfPagamentiFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.shoppingcart.ShoppingCartFacadeLocal;
import it.tasgroup.iris.facade.ejb.restapi.PaymentAPIFacadeBeanLocal;
import it.tasgroup.iris.payment.helper.NodoSPCRequestHandler;
import it.tasgroup.iris.payment.helper.PaymentInput;
import it.tasgroup.iris.payment.helper.PaymentOutput;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;
import it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;
import it.tasgroup.rest.utils.exceptions.NotFoundException;
import it.tasgroup.services.util.enumeration.EnumStatoDistintePagamento;
import it.tasgroup.services.util.enumeration.EnumUtils;

/**
 * Processo di pagamento
 */
@Path("/processo_pagamento")
public class ProcessoPagamentoEndpoint {

    @EJB(name = "ShoppingCartFacade")
    private ShoppingCartFacadeLocal shoppingCartFacade;

    @EJB(name = "confPagamentiFacadeBean")
    private ConfPagamentiFacadeLocal confPagamentiFacade;

    @EJB(name = "PaymentAPIFacade")
    private PaymentAPIFacadeBeanLocal paymentAPIFacade;
    
    
    /**
     * Apertura sessione gateway.
     *
     * @param RichiestaAperturaSessioneGateway, contiene le informazioni necessarie per aprire la sessione gateway.
     * Campi necessari: String codiceFiscaleVersante, String anagraficaVersante, String email, String urlBack, String urlCancel,
     * String senderSil, String senderSys, int flagOpposizione730, String redirectOnly,
     * List di <a href="el_ns0_condizionePagamentoReference">CondizionePagamentoReference</a>;
     * 
     * @return <a href="el_ns0_rispostaAperturaSessioneGateway.html">RispostaAperturaSessioneGateway</a> con campi String token, String urlGateway.
     * Il chiamante riceve l'url del gateway su cui fare redirect
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/sessione_gw")
    public RispostaAperturaSessioneGateway RichiestaSessioneGateway(RichiestaAperturaSessioneGateway request) throws GatewayAuthenticationException {
		  
    	RispostaAperturaSessioneGateway response = paymentAPIFacade.aperturaSessioneGateway(request);
    	String token = response.getToken();
    	Properties gwProperties = ConfigurationPropertyLoader.getProperties("gateway-ws-client.properties");
		String gwBaseUrl= gwProperties.getProperty("iris.gateway.webapp.baseUrl");
		
		String gwFinalUrl = gwBaseUrl + "/PaymentAuthentication?token=" + token;
    	response.setUrlGateway(gwFinalUrl);
    	
    	return response;
    }
    /**
     * Avvia un pagamento, creando un corrispondente Processo di pagamento.<br>
     * E' utilizzabile solo con modelloPagamento immediato. L'API e' predisposta anche per modello pagamento differito, ma questa modalita' non e' ancora supportata.
     *
     * @param infoAperturaPagamento, contiene le informazioni necessarie per aprire il processo di pagamento.
     * @return ProcessoPagamento appena aperto.
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/")
    public ProcessoPagamento paga(final InfoAperturaPagamento infoAperturaPagamento, @Context UriInfo uriInfo) throws Exception {
        String codPrestatoreServizio = infoAperturaPagamento.getCodPrestatoreServizio();

        Long id = Long.valueOf(codPrestatoreServizio);
        CfgGatewayPagamentoDTO gateway = confPagamentiFacade.readCfgGatewayPagamentoDettaglio(id);
        if (gateway == null) {
            throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_NOGTW, new String[] {"codPrestatoreServizio"}, "Nessun Gateway trovato " + infoAperturaPagamento.getCodPrestatoreServizio() );
        }
        
        String modelloVersamento = gateway.getModelloVersamento();
        if (! (EnumModelloPagamento.IMMEDIATO.getChiave().equals(modelloVersamento) || EnumModelloPagamento.IMMEDIATO_CARRELLO.getChiave().equals(modelloVersamento))) {
            throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_NOGTW, new String[] {"codPrestatoreServizio"}, "Gateway con valido, modalitÃ  pagamento : " + modelloVersamento );
        }
        
        List<String> idCondizioni = infoAperturaPagamento.getIdCondizioni();
        //  ids.size > 1 ==> GW modello pagamento = 1 (IMMEDIATO - CARRELLO)
        if (idCondizioni.size()>1 && !EnumModelloPagamento.IMMEDIATO_CARRELLO.getChiave().equals(modelloVersamento)) {
            throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_NOGTW, new String[] {"codPrestatoreServizio"}, "Gateway con valido, modalitÃ  pagamento : " + modelloVersamento + " se il numero delle condizioni Ã¨ maggiore di 1" );
        }

        List<SessionShoppingCartItemDTO> cartItemList = shoppingCartFacade.readFullCartItemList(idCondizioni);
        GWRestProfile profile = new GWRestProfile();
        profile.setUserName("ANONYMOUS");
        profile.setEmailPagante(infoAperturaPagamento.getEmailVersante());
        //profile.setCodFiscalePagante(infoAperturaPagamento.getCodFiscaleVersante());
        //lo imposto ad anonimo per uniformit� come da indicazioni di Marco B, visto che non c'� autenticazione
        profile.setCodFiscalePagante("ANONYMOUS");
        profile.setLapl("ANONYMOUS");

        PaymentInput paymentInput = new PaymentInput();
        paymentInput.setIbanAddebito(infoAperturaPagamento.getIbanAddebito());

        OperatoreDTO operatore = new OperatoreDTO();
        operatore.setNome("ANONYMOUS");
        operatore.setCognome("ANONYMOUS");

        String[] condizioniArray = new String[idCondizioni.size()];
        condizioniArray = idCondizioni.toArray(condizioniArray);
        String condizioniAsString = Arrays.toString(condizioniArray);

        if (cartItemList.isEmpty()) {
            throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_NOBILL, new String[] {"condizioni"}, "Nessuna condizione trovata :" + condizioniAsString);
        } else if (cartItemList.size() !=  idCondizioni.size()){
            throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_NOBILL, new String[] {"condizioni"}, "Non tutte le condizioni specificate sono state trovate : " + condizioniAsString);
        }
        
        List<EnumStatoPagamentoCondizione> statiCondizioni = paymentAPIFacade.getStatiCondizioni(idCondizioni);
        boolean tuttePagabili = true, inCorso = false;
        for (EnumStatoPagamentoCondizione statoCondizione : statiCondizioni) {
            if (statoCondizione == EnumStatoPagamentoCondizione.IN_CORSO) {
                inCorso = true;
            } else if (statoCondizione != EnumStatoPagamentoCondizione.DA_PAGARE) {
                tuttePagabili = false;
            }
        }

        if (!tuttePagabili) {
            throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_NOBILL, new String[] {"condizioni"}, "Non tutte le condizioni sono pagabili " + condizioniAsString );
        }

        if (inCorso) {
            throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_IN_PROGRESS, new String[] {"condizioni"}, "Sono state specificate condizioni con stato IN CORSO : " + condizioniAsString);
        }

        Map<String, SessionShoppingCartItemDTO> cartItemsMap = new HashMap<String, SessionShoppingCartItemDTO>();
        for (SessionShoppingCartItemDTO cartItem : cartItemList) {
            cartItemsMap.put(cartItem.getIdCondizione(), cartItem);
        }
        
        List<OpzionePagamentoCondizione> opzioniPagamento = infoAperturaPagamento.getOpzioniPagamento();
        if (opzioniPagamento != null) {
            for (OpzionePagamentoCondizione opzione : opzioniPagamento) {
                String idCondizione = opzione.getIdCondizione();
                SessionShoppingCartItemDTO cartItem = cartItemsMap.get(idCondizione);
                if (cartItem == null) {
                    // se nelle opzioni c'è un  ID Condizione che non è sulla lista ..dobbiamo tornare errore parlante.
                    throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_IN_PROGRESS, new String[] {"condizioni"}, "Sono state specificate opzioni per un condizione non presente, id : " + idCondizione);
                }
                cartItem.setItemOpposizione730(opzione.isFlagOpposizione730() ? "1" : "0");
            }
        }

        NodoSPCRequestHandler nodoSPCRequestHandler = new NodoSPCRequestHandler(
                cartItemList,
                gateway,
                profile,
                operatore
        );

        PaymentOutput paymentOutput = nodoSPCRequestHandler.preparaRichiesta(paymentInput);
        if (paymentOutput.getFaultNodoSPC() != null) {
            throw new BusinessConstraintException(EnumBusinessErrorCodes.APPEXC_NODE_GENERIC_ERROR,new String[]{}, paymentOutput.getFaultNodoSPC());
        }
        String codPagamento = paymentOutput.getCodPagamento();
        String modelloVersamentoStr = gateway.getModelloVersamento();

        EnumModelloPagamento modelloPagamento = null;
        if (modelloVersamentoStr != null) {
            modelloPagamento = EnumUtils.findByChiave(modelloVersamentoStr,EnumModelloPagamento.class);
        }

        String tipoVersamentoStr = gateway.getTipoVersamento();
        EnumTipoVersamento tipoVersamento = null;
        if (tipoVersamentoStr != null) {
             tipoVersamento = EnumUtils.findByChiave(tipoVersamentoStr,EnumTipoVersamento.class);
        }

        ProcessoPagamento processoPagamento = new ProcessoPagamento(
                codPagamento,
                new Date(System.currentTimeMillis()),
                tipoVersamento,
                modelloPagamento,
                infoAperturaPagamento.getCodPrestatoreServizio(),
                gateway.getSystemId(),
                gateway.getSystemName(),
                EnumStatoDistintePagamento.INCORSO,
                paymentOutput.getDistintaPagamento().getTotale(),
                infoAperturaPagamento.getCodFiscaleVersante(),
                infoAperturaPagamento.getEmailVersante(),
                infoAperturaPagamento.getDescrizioneVersante(),
                paymentOutput.getUrlToRedirect(),
                gateway.getCfgModalitaPagamento().getDescrizione());

        return processoPagamento;
    }

    /**
     * Recupera il processo di pagamento in base al codice identificativo (<em>codPagamento</em>).  Questo codice e' restituito all'atto dell'apertura del pagamento.
     * Il codice e' anche recapitato all'utente nella mail di conferma del pagamento avvenuto. <br>
     * Per questione di privacy si accede ai dati del pagamento indicando anche il codice fiscale del debitore o del versante.
     *
     * @param codPagamento  [Obbligatorio]: codice identificativo del processo di pagamento.
     * @param codiceFiscale [Obbligatorio]:  codice fiscale o di chi ha effettuato il pagamento, oppure dell'intestatario di una delle condizioni contenute nel carrello pagato.
     * @return ProcessoPagamento
     */
    @GET
    @Produces("application/json")
    @Path("/")
    public ProcessoPagamento getProcessoPagamento(@QueryParam("codPagamento") String codPagamento, @QueryParam("codiceFiscale") String codiceFiscale) throws Exception {
        if (codPagamento == null || codiceFiscale == null) {
            throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_WRONG_PARAMETERS, new String[] {"codPagamento", "codiceFiscale"}, "Entrambi i parametri sono obbligatori");
        }

        ProcessoPagamento processoPagamento = paymentAPIFacade.getProcessoPagamento(codPagamento, codiceFiscale);
        if (processoPagamento == null) {
            throw new NotFoundException();
        }
        return processoPagamento;

    }

    @OPTIONS
    @Path("/")
    public Response pagaPrefligth (final InfoAperturaPagamento infoAperturaPagamento, @Context UriInfo uriInfo) {
        return Response.ok().build();
    }

}

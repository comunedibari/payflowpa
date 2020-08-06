package it.tasgroup.iris.facade.ejb.anonymous;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import it.tasgroup.idp.rs.enums.EnumModelloPagamento;
import it.tasgroup.idp.rs.enums.EnumStatoPagamento;
import it.tasgroup.idp.rs.enums.EnumTipoVersamento;
import it.tasgroup.idp.rs.model.InfoAperturaPagamento;
import it.tasgroup.idp.rs.model.PrestatoreServizio;
import it.tasgroup.idp.rs.model.ProcessoPagamento;
import it.tasgroup.idp.rs.model.ProcessoPagamentoCondizione;
import it.tasgroup.idp.rs.model.RichiestaAperturaSessioneGateway;
import it.tasgroup.idp.rs.model.RispostaAperturaSessioneGateway;
import it.tasgroup.iris.business.ejb.client.anonymous.AnonymousPaymentBusinessLocal;
import it.tasgroup.iris.business.ejb.client.flussi.DistintePagamentoBusinessLocal;
import it.tasgroup.iris.business.ejb.client.gateway.GatewayAuthenticationBusinessLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.AutorizzazioneLocal;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.SessioneGateway;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO;
import it.tasgroup.iris.dto.SessionIdDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgCommissionePagamentoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgGatewayPagamentoDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.iris.dto.exception.GatewayAuthenticationException;
import it.tasgroup.iris.dto.exception.InvalidInputException;
import it.tasgroup.iris.facade.ejb.client.confpagamenti.ConfPagamentiFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.posizionedebitoria.DDPFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.shoppingcart.ShoppingCartFacadeLocal;
import it.tasgroup.iris.facade.ejb.gateway.dto.builder.GatewayAuthenticationBEDTOBuilder;
import it.tasgroup.iris.facade.ejb.restapi.PaymentAPIFacadeBeanLocal;
import it.tasgroup.iris.facade.ejb.restapi.PaymentAPIFacadeRemote;
import it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;
import it.tasgroup.services.util.enumeration.EnumStatoDistintePagamento;
import it.tasgroup.services.util.enumeration.EnumUtils;


/**
 *
 */
@Stateless(name = "PaymentAPIFacade")
public class PaymentAPIFacadeBean implements PaymentAPIFacadeBeanLocal, PaymentAPIFacadeRemote {

    @EJB(name = "AnonimousPaymentBusiness")
    private AnonymousPaymentBusinessLocal anonymousPaymentBusinessBean;

    @EJB(name = "confPagamentiFacadeBean")
    private ConfPagamentiFacadeLocal confPagamentiFacade;

    @EJB(name = "ShoppingCartFacade")
    private ShoppingCartFacadeLocal shoppingCartFacade;

    @EJB(name = "DistintePagamentoBusiness")
    private DistintePagamentoBusinessLocal distintePagamentoBusiness;

    @EJB(name = "AutorizzazioneBusiness")
    private AutorizzazioneLocal autorizzazioneBusiness;

    @EJB(name = "DDPFacade")
    private DDPFacadeLocal ddpFacadeLocal;
    
    @EJB(name = "GatewayAuthenticationBusiness")
	private GatewayAuthenticationBusinessLocal autorizzazioneBusinessBean;
    
    
/* Funzione per apertura sessione gateway da REST API (come asfForNewToken)
 * Restituisce alla chiamata REST solo il token che verrà poi concatenato nella url
 * direttamente nella chiamata REST
 * */
    @Override 
    public RispostaAperturaSessioneGateway aperturaSessioneGateway(RichiestaAperturaSessioneGateway richiestaAperturaDTO) throws GatewayAuthenticationException {
    	
    	
    	SessioneGateway session = GatewayAuthenticationBEDTOBuilder.populateSessioneGateway(richiestaAperturaDTO);
    	SessioneGateway savedSession = autorizzazioneBusinessBean.saveGatewaySession(session);
		SessionIdDTO sessionIdDTO = GatewayAuthenticationBEDTOBuilder.populateSessionIdDTO(savedSession);
		
		RispostaAperturaSessioneGateway response = new RispostaAperturaSessioneGateway();
		response.setToken(sessionIdDTO.getToken());
		//qui l'url del gw non è ancora settato nella response
		return response;
    }
    

    @Override
    public List<it.tasgroup.idp.rs.model.CondizionePagamento> getCondizioni(String iuv, String codEnte, String codCategoriaTributo, String codiceFiscale) throws BusinessConstraintException {
        List<CondizionePagamento> condizioniByIUV = new ArrayList<CondizionePagamento>();


        if (codEnte != null) { // risultato univoco (ENTE + IUV)
            CondizionePagamento condizioneByEnteIUV = anonymousPaymentBusinessBean.getCondizioneByEnteIUV(iuv, codEnte, codiceFiscale);

            if (condizioneByEnteIUV != null) {
                condizioniByIUV.add(condizioneByEnteIUV);
            }
        } else if (codCategoriaTributo != null) { // possibile risultato non univoco
            condizioniByIUV = anonymousPaymentBusinessBean.getCondizioniByIUV(iuv, codCategoriaTributo, codiceFiscale);
        }

        for (CondizionePagamento condizionePagamento : condizioniByIUV) {
            condizionePagamento.updateStatoPagamentoCalcolato();
        }

        return AnonymousPaymentFacadeDTOBuilder.populateCondizioneRestApi(condizioniByIUV);

    }

    @Override
    public List<PrestatoreServizio> getListaPSP(final EnumTipoVersamento tipoVersamento, final List<EnumModelloPagamento> modelloPagamentoList, List<String> idCondizionePagamentoList) throws Exception {
        List<CfgGatewayPagamentoDTO> gatewayPagamentoList = confPagamentiFacade.readCfgGatewayPagamentoList();

        List<CfgGatewayPagamentoDTO> gatewayPagamentoStatoAttivo = new ArrayList<CfgGatewayPagamentoDTO>();
        for (CfgGatewayPagamentoDTO cfgGatewayPagamentoDTO : gatewayPagamentoList) {
            if ("ATTIVO".equals(cfgGatewayPagamentoDTO.getStato())) {
                gatewayPagamentoStatoAttivo.add(cfgGatewayPagamentoDTO);
            }
        }

        List<SessionShoppingCartItemDTO> shoppinCartItems = new ArrayList<SessionShoppingCartItemDTO>();
        Collection<CfgGatewayPagamentoDTO> decoratedGateways;
        if (!idCondizionePagamentoList.isEmpty()) {
            shoppinCartItems = shoppingCartFacade.readFullCartItemList(idCondizionePagamentoList);
            decoratedGateways = confPagamentiFacade.filtraConfigurazioniVisualizzate(gatewayPagamentoStatoAttivo, shoppinCartItems, true, false);
        } else {
            decoratedGateways = confPagamentiFacade.readCfgGatewayPagamentoList();
        }


        List<PrestatoreServizio> pspList = new ArrayList<PrestatoreServizio>();

        for (CfgGatewayPagamentoDTO decoratedGateway : decoratedGateways) {
            String tipoVers = decoratedGateway.getTipoVersamento();
            EnumTipoVersamento enumTipoVersamento = EnumUtils.findByChiave(tipoVers, EnumTipoVersamento.class);
            String modelloVersamento = decoratedGateway.getModelloVersamento();

            EnumModelloPagamento enumModelloPagamento = null;
            if (modelloVersamento != null) {
                enumModelloPagamento = EnumUtils.findByChiave(String.valueOf(modelloVersamento), EnumModelloPagamento.class);
            }


            List<CfgCommissionePagamentoDTO> cfgCommissionePagamenti = decoratedGateway.getCfgCommissionePagamenti();
            String descrizioneVideo = cfgCommissionePagamenti.size() > 0 ? cfgCommissionePagamenti.get(0).getDescrizioneVideo() : "";

            PrestatoreServizio prestatoreServizio = new PrestatoreServizio(String.valueOf(decoratedGateway.getId()),
                    decoratedGateway.getApplicationId(),
                    decoratedGateway.getDescrizione(),
                    decoratedGateway.getSystemId(),
                    decoratedGateway.getSystemName(),
                    descrizioneVideo,
                    decoratedGateway.getUrlInfoPsp(),
                    decoratedGateway.getUrlInfoCanale(),
                    decoratedGateway.getDisponibilitaServizio(),
                    enumTipoVersamento,
                    enumModelloPagamento,
                    "1", // TODO prioritÃ  ?
                    decoratedGateway.isDisponibilePerPagamentoCorrente(),
                    decoratedGateway.getDescrizioneIndisponibilitaPerPagamentoCorrente()
            );


            pspList.add(prestatoreServizio);
        }

        // filtro i risultati per tipoVersamento / modelloPagamentoList
        Predicate matchRequestCriteria = new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                final PrestatoreServizio prestatoreServizio = (PrestatoreServizio) object;
                Predicate isInEnumModelloPagamentoList = new Predicate() {
                    @Override
                    public boolean evaluate(Object object) {
                        EnumModelloPagamento enumModelloPagamento = (EnumModelloPagamento) object;
                        return prestatoreServizio.getModelloVersamento() == enumModelloPagamento;
                    }
                };
                int matches = CollectionUtils.countMatches(modelloPagamentoList, isInEnumModelloPagamentoList);
                return (modelloPagamentoList.size() == 0 || matches > 0) && (tipoVersamento == null || prestatoreServizio.getTipoVersamento() == tipoVersamento);
            }
        };

        // filtro in base ai criteri specificati
        CollectionUtils.filter(pspList, matchRequestCriteria);

        return pspList;
    }

    @Override
    public ProcessoPagamento getProcessoPagamento(String codPagamento, String codiceFiscale) {
        ProcessoPagamento processoPagamento = new ProcessoPagamento();

        List<GestioneFlussi> gestioneFlussiList = distintePagamentoBusiness.getByCodPagamentoCodiceFiscale(codPagamento, codiceFiscale);

        if (gestioneFlussiList.isEmpty()) {
            return null;
        }

        GestioneFlussi distinta = gestioneFlussiList.get(0);
        processoPagamento.setCodPagamento(distinta.getCodPagamento());
        Timestamp dataSpedizione = distinta.getDataSpedizione();

        if (dataSpedizione != null) {
            Date d = new Date(dataSpedizione.getTime());
            processoPagamento.setDataEsecuzione(dataSpedizione);
        }

        CfgGatewayPagamento cfgGatewayPagamento = distinta.getCfgGatewayPagamento();
        String tipoVersamento = cfgGatewayPagamento.getTipoVersamento();
        if (tipoVersamento != null) {
            processoPagamento.setTipoVersamento(EnumTipoVersamento.valueOf(tipoVersamento));
        }

        String modelloVersamento = cfgGatewayPagamento.getModelloVersamento();
        if (modelloVersamento != null) {
            processoPagamento.setModelloPagamento(EnumUtils.findByChiave(modelloVersamento, EnumModelloPagamento.class));
        }

        processoPagamento.setCodPrestatoreServizio(cfgGatewayPagamento.getBundleKey());
        processoPagamento.setSystemId(cfgGatewayPagamento.getSystemId());
        processoPagamento.setSystemName(cfgGatewayPagamento.getSystemName());

        if (distinta.getStato() != null) {
            processoPagamento.setStato(EnumUtils.findByChiave(distinta.getStato(), EnumStatoDistintePagamento.class));
        }

        processoPagamento.setCodFiscaleVersante(distinta.getUtentecreatore());
        processoPagamento.setEmailVersante(distinta.getEmailVersante());
        processoPagamento.setDescrizioneVersante(""); // query sul destinatario...?
        processoPagamento.setUrlRedirect(""); // non presente qui

        // calcolo importo totale...
        List<ProcessoPagamentoCondizione> processoPagamentoCondizioneList = new ArrayList<ProcessoPagamentoCondizione>();
        BigDecimal importoTotaleCalcolato = BigDecimal.ZERO;

        for (GestioneFlussi flusso : gestioneFlussiList) {
            BigDecimal totimportipositivi = flusso.getTotimportipositivi();
            importoTotaleCalcolato = importoTotaleCalcolato.add(totimportipositivi);
            Set<Pagamenti> pagamenti = flusso.getPagamenti();
            for (Pagamenti pagamento : pagamenti) {
                CondizionePagamento condPagamento = pagamento.getCondPagamento();
                ProcessoPagamentoCondizione processoPagamentoCondizione = new ProcessoPagamentoCondizione(
                        flusso.getIdentificativoFiscaleCreditore(),
                        flusso.getIuv(),
                        "n/a",
                        pagamento.getIdRiscossionePSP(),
                        distintePagamentoBusiness.getUrlRicevuta(pagamento, codPagamento, flusso.getUtentecreatore(), String.valueOf(flusso.getId())),
                        getCondizione(condPagamento)
                );
                processoPagamentoCondizioneList.add(processoPagamentoCondizione);
            }
        }
        processoPagamento.setImportoTotale(importoTotaleCalcolato);

        processoPagamento.setProcessoPagamentoCondizioneList(processoPagamentoCondizioneList);
        
        processoPagamento.setModalitaPagamento(cfgGatewayPagamento.getCfgModalitaPagamento().getDescrizione());

        return processoPagamento;
    }

    @Override
    public ProcessoPagamento paga(InfoAperturaPagamento infoAperturaPagamento) throws InvalidInputException {
        throw new UnsupportedOperationException();
    }

    private it.tasgroup.idp.rs.model.CondizionePagamento getCondizione(CondizionePagamento condPagamento) {
        return AnonymousPaymentFacadeDTOBuilder.populateCondizioneRestApi(condPagamento);
    }

    public List<EnumStatoPagamentoCondizione> getStatiCondizioni(List<String> ids) {
        List<CondizionePagamento> condizioniByIdsWithStatoCalcolato = anonymousPaymentBusinessBean.getCondizioniByIdsWithStatoCalcolato(ids);
        List<EnumStatoPagamentoCondizione> statiCalcolati = new ArrayList<EnumStatoPagamentoCondizione>();

        for (CondizionePagamento condizionePagamento : condizioniByIdsWithStatoCalcolato) {
            statiCalcolati.add(condizionePagamento.getStatoPagamentoCalcolato());
        }
        return statiCalcolati;
    }

    @Override
    public it.tasgroup.idp.rs.model.CondizionePagamento getCondizioneQRCode(String codFiscaleCreditore, String idPagamentoEnte, BigDecimal importo) {
        CondizionePagamento condizionePagamento = anonymousPaymentBusinessBean.getCondizioneQRCode(codFiscaleCreditore,idPagamentoEnte,importo);

        it.tasgroup.idp.rs.model.CondizionePagamento condPagDTO = null;
        if (condizionePagamento != null) {
            condizionePagamento.updateStatoPagamentoCalcolato();
            condPagDTO = AnonymousPaymentFacadeDTOBuilder.populateCondizioneRestApi(condizionePagamento);
        }

        return condPagDTO;
    }

    @Override
    public List<it.tasgroup.idp.rs.model.CondizionePagamento> getCondizioniFull(String idCreditore, String enumTipoDebito, String idDebito, String idPagamento, String identificativoUnivocoVersamento, String codiceFiscaleDebitore, EnumStatoPagamento enumStatoPagamento, String flagIncasso, String dataOraPagamento) {
        return null;
    }

}

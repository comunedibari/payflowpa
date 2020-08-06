/**
 *
 */
package it.tasgroup.iris.facade.ejb.anonymous;

import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.addon.api.manager.helper.AddOnManager;
import it.tasgroup.addon.api.manager.helper.AddOnViewHelper;
import it.tasgroup.addon.internal.AddOnManagerFactory;
import it.tasgroup.idp.rs.model.*;
import it.tasgroup.idp.rs.model.CondizionePagamento.VoceImporto;
import it.tasgroup.iris.domain.*;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.demo.VocePagamento;
import it.tasgroup.iris.domain.helper.BillInspector;
import it.tasgroup.iris.domain.helper.BillItemInspector;
import it.tasgroup.iris.dto.anonymous.ddp.DDPAnonymousDTOLight;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousPendenzaDTO;
import it.tasgroup.iris.dto.anonymous.payment.CondizioneDTO;
import it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder.EntiDTOBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static it.tasgroup.idp.rs.model.CondizionePagamento.*;


public class AnonymousPaymentFacadeDTOBuilder {

    public static AnonymousPendenzaDTO populatePendenzaDTO(Pendenza pend, String idCondizione) {
        AnonymousPendenzaDTO dto = new AnonymousPendenzaDTO();
        dto.setAnno(pend.getAnnoRiferimento());
        dto.setCausaleDebito(pend.getDeCausale() != null ? pend.getDeCausale().toUpperCase() : null);
        dto.setId(pend.getIdPendenza());
        dto.setIdDebitoEnte(pend.getIdPendenzaente());
        dto.setIdente(pend.getTributoEnte().getIdEnte());
        dto.setNote(pend.getNote());
        dto.setTipoDebito(pend.getTributoEnte().getCdTrbEnte());
        dto.setTsEmissioneEnte(pend.getTsEmissioneente());
        dto.setTsPrescrizione(pend.getTsPrescrizione());

        List<String[]> destinatari = new ArrayList<String[]>();
        for (DestinatariPendenza dest : pend.getDestinatari()) {
            String[] mma = {dest.getCoDestinatario(), dest.getTiDestinatario(), dest.getDeDestinatario()};
            destinatari.add(mma);
        }
        dto.setDestinatari(destinatari);

        dto.setCodEnte(pend.getTributoEnte().getEntiobj().getCodiceEnte());
        dto.setSil(pend.getTributoEnte().getSIL());

        String idPagamentoEnte = null;
        for (CondizionePagamento condizionePagamento : pend.getCondizioni()) {
            if (condizionePagamento.getIdCondizione().equals(idCondizione)) {
                idPagamentoEnte = condizionePagamento.getIdPagamento();
                break;
            }
        }

        dto.setIdPagamento(idPagamentoEnte);

        Double totParz = 0D;
        for (CondizionePagamento cond : pend.getCondizioni()) {
//                E -> pagamento irregolare
            if (cond.getStPagamento().equals("E")) {
                totParz = totParz + cond.getImPagamento().doubleValue();
            }
        }

        dto.setTotalePagamentiParziali(totParz);

        return dto;

    }

    public static CondizioneDTO populateCondizioneDTO(CondizionePagamento condizionePagamento) {

        CondizioneDTO dto = new CondizioneDTO();

        dto.setScadenza(condizionePagamento.getDtScadenza());

        TributoEnte trbEnte = condizionePagamento.getPendenza().getTributoEnte();

        dto.setTributo(trbEnte.getCdTrbEnte());

        dto.setDescrTributo(trbEnte.getDeTrb());

        dto.setIdPagamento(condizionePagamento.getIdPagamento());

        dto.setImporto(condizionePagamento.getImTotale());
        
        dto.setImportoPendenza(condizionePagamento.getPendenza().getImTotale());

        dto.setFineValidita(condizionePagamento.getDtFinevalidita());

        dto.setId(condizionePagamento.getIdCondizione());

        dto.setIdPendenza(condizionePagamento.getPendenza().getIdPendenza());

        dto.setCausalePagamento(condizionePagamento.getCausalePagamento());

        dto.setCausalePendenza(condizionePagamento.getPendenza().getDeCausale());

        Enti ente = condizionePagamento.getPendenza().getTributoEnte().getEntiobj();

        dto.setEnte(ente.getDenominazione());
        
        dto.setIdEnte(ente.getIdEnte());

        Map<String, List<DocumentoDiPagamento>> map = BillItemInspector.getDDPValidoEAnnullati(condizionePagamento);

        List<DocumentoDiPagamento> nullDDPList = map.get("NULL_DDP");

        List<DocumentoDiPagamento> validDDPList = map.get("VALID_DDP");

        if (!validDDPList.isEmpty()) {

            DocumentoDiPagamento validDDP = validDDPList.get(0);

            DDPAnonymousDTOLight ddpDTO = populateDDPAnonymousDTOLight(validDDP);

            dto.setValidDDP(ddpDTO);

        }

        for (DocumentoDiPagamento ddp : nullDDPList) {

            DDPAnonymousDTOLight ddpDTO = populateDDPAnonymousDTOLight(ddp);

            dto.getDdpAnnullati().add(ddpDTO);

        }

        Pagamenti pagamento = BillItemInspector.getUniqueValidPayment(condizionePagamento);

        if (pagamento != null) {
            dto.setCodPagamento(pagamento.getFlussoDistinta().getCodPagamento());
        }

        dto.setStatoPagamentoCalcolato(condizionePagamento.getStatoPagamentoCalcolato());

        dto.setIdDistintaStatoPagamentoCalcolato(condizionePagamento.getIdDistintaInCorso());


        dto.setDebitori(BillInspector.getDebitori(condizionePagamento.getPendenza(), ", "));
        
		dto.setAllegatiPendenza(condizionePagamento.getAllegatiPendenza().iterator().hasNext());

        return dto;

    }

    private static DDPAnonymousDTOLight populateDDPAnonymousDTOLight(DocumentoDiPagamento ddp) {

        DDPAnonymousDTOLight ddpDTO = new DDPAnonymousDTOLight();

        ddpDTO.setDataEmissione(ddp.getTsEmissione());

        ddpDTO.setIntestatario(ddp.getIntestatario());

        ddpDTO.setOpInserimento(ddp.getOpInserimento());

        ddpDTO.setOpAnnullamento(ddp.getOpAnnullamento());

        ddpDTO.setDataAnnullamento(ddp.getTsAnnullamento());

        ddpDTO.setId(ddp.getId());

        return ddpDTO;


    }

    public static List<CondizioneDTO> populateCondizioneDTOList(Pendenza pend) {

        ArrayList<CondizioneDTO> cp = new ArrayList<CondizioneDTO>();

        Set<CondizionePagamento> condizioni = pend.getCondizioni();

        for (CondizionePagamento condizionePagamento : condizioni) {

            condizionePagamento.updateStatoPagamentoCalcolato();


            CondizioneDTO condDTO = populateCondizioneDTO(condizionePagamento);

            cp.add(condDTO);

        }

        return cp;
    }

    public static it.tasgroup.idp.rs.model.CondizionePagamento populateCondizioneRestApi(CondizionePagamento condizionePagamento) {
        it.tasgroup.idp.rs.model.CondizionePagamento condPagDTO = new it.tasgroup.idp.rs.model.CondizionePagamento();

        condPagDTO.setScadenza(condizionePagamento.getDtScadenza());

        TributoEnte trbEnte = condizionePagamento.getPendenza().getTributoEnte();

        // eventualmente sostituire con query ottimizzata
        Set<DestinatariPendenza> destinatari = condizionePagamento.getPendenza().getDestinatari();
        List<InfoDebitori> debitori = new ArrayList<InfoDebitori>();
        for (DestinatariPendenza destinatariPendenza : destinatari) {
            String coDestinatario = destinatariPendenza.getCoDestinatario();
            
            String email = "";
            if (destinatariPendenza.getEmailDestinatario() != null) {
                email = destinatariPendenza.getEmailDestinatario();
            }

            String descDebitore = destinatariPendenza.getDeDestinatario();

            InfoDebitori infoDebitori = new InfoDebitori(coDestinatario, descDebitore, email);
            debitori = new ArrayList<InfoDebitori>();
            debitori.add(infoDebitori);
        }

        condPagDTO.setDebitori(debitori);

        condPagDTO.setNote(condizionePagamento.getDeNotePagamento());

        condPagDTO.setCodEnte(condizionePagamento.getEnte().getCodiceEnte());

        condPagDTO.setDescEnte(condizionePagamento.getEnte().getDenominazione());

        condPagDTO.setCodFiscaleEnte(condizionePagamento.getEnte().getIntestatarioobj().getLaplIForm());


        condPagDTO.setCodCategoriaTributo(trbEnte.getIdTributo());
        condPagDTO.setCodTributo(condizionePagamento.getCdTrbEnte());

        condPagDTO.setDescrTributo(trbEnte.getDeTrb());

        condPagDTO.setIdPendenzaEnte(condizionePagamento.getPendenza().getIdPendenzaente());

        condPagDTO.setIdPagamentoEnte(condizionePagamento.getIdPagamento());

        condPagDTO.setImporto(condizionePagamento.getImTotale());

        condPagDTO.setInizioValidita(condizionePagamento.getDtIniziovalidita());
        condPagDTO.setFineValidita(condizionePagamento.getDtFinevalidita());

        condPagDTO.setId(condizionePagamento.getIdCondizione());

        condPagDTO.setCausalePagamento(condizionePagamento.getCausalePagamento());

        condPagDTO.setCausalePendenza(condizionePagamento.getPendenza().getDeCausale());

        TributoStrutturato tributoStrutturato = condizionePagamento.getPendenza().getTributoStrutturato();
		if (tributoStrutturato != null) {
			String causaleDescr=getDescrCausaleByAddonManager(condizionePagamento.getPendenza());
			condPagDTO.setCausalePendenza(causaleDescr);
		}
        
        Pagamenti pagamento = BillItemInspector.getUniqueValidPayment(condizionePagamento);
        if (pagamento != null) {
            condPagDTO.setCodPagamento(pagamento.getFlussoDistinta().getCodPagamento());
        }
        
        condizionePagamento.updateStatoPagamentoCalcolato();

        condPagDTO.setStatoPagamentoCalcolato(condizionePagamento.getStatoPagamentoCalcolato());

        
        List<VocePagamento> vociPagamento = condizionePagamento.getVociPagamento();
        if (vociPagamento != null && !vociPagamento.isEmpty()) {
            List<VoceImporto> vociImporto = new ArrayList<VoceImporto>();
        	for (VocePagamento vocePagamento : vociPagamento) {
                vociImporto.add(new VoceImporto(vocePagamento.getDeVoce(), vocePagamento.getImVoce()));
			}
            condPagDTO.setVociImporto(vociImporto);
        }

        // aggiunte per attualizzatore
        condPagDTO.setIdEnte(condizionePagamento.getEnte().getIdEnte());
        condPagDTO.setAnnoRiferimento("" + condizionePagamento.getPendenza().getAnnoRiferimento());
        condPagDTO.setDataPrescrizione(condizionePagamento.getPendenza().getTsPrescrizione());
        condPagDTO.setDataEmissione(condizionePagamento.getPendenza().getTsEmissioneente());
        condPagDTO.setCausalePendenzaGrezza(condizionePagamento.getPendenza().getDeCausale());
        
        return condPagDTO;
    }
    
    private static String getDescrCausaleByAddonManager(Pendenza pendenza) {
		TributoEnte tributo = pendenza.getTributoEnte();
		String cdPlugin = tributo.getCfgTributoEntePlugin() != null ? tributo.getCfgTributoEntePlugin().getCdPlugin() : "";
		return getDescrCausaleByAddonManager(tributo.getIdEnte(), tributo.getCdTrbEnte(), cdPlugin, pendenza.getDeCausale());
	} 
    
    private static String getDescrCausaleByAddonManager(String idEnte, String cdTrbEnte, String cdPlugin, String deCausale) {

		String causaleDescr;
		if (AddOnManagerFactory.exists(cdPlugin)) {
			AddOnManager<TributoStrutturato> manager = AddOnManagerFactory.getAddOnManager(idEnte, cdTrbEnte, cdPlugin); // per es CONFERIMENTO_DISCARICA
			AddOnViewHelper<TributoStrutturato> viewHelper = manager.getViewHelper();
			causaleDescr = viewHelper.getCausale(deCausale);

		} else {
			causaleDescr = deCausale;
		}
		return causaleDescr;

	}

    public static List<it.tasgroup.idp.rs.model.CondizionePagamento> populateCondizioneRestApi(List<CondizionePagamento> condizionePagamentoList) {
        List<it.tasgroup.idp.rs.model.CondizionePagamento> condPagDtoList = new ArrayList<it.tasgroup.idp.rs.model.CondizionePagamento>();
        for (CondizionePagamento condizionePagamento : condizionePagamentoList) {
            it.tasgroup.idp.rs.model.CondizionePagamento condPagDTO = populateCondizioneRestApi(condizionePagamento);
            condPagDtoList.add(condPagDTO);
        }

        return condPagDtoList;

    }
}

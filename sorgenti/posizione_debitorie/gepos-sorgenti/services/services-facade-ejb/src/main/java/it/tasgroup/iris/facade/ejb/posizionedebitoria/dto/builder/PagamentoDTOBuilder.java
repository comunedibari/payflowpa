package it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder;


import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.iris.domain.CfgFornitoreGateway;
import it.tasgroup.iris.domain.CfgModalitaPagamento;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.IUVPosizEnteMap;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.domain.demo.VocePagamento;
import it.tasgroup.iris.domain.RevochePagamento;
import it.tasgroup.iris.dto.flussi.DistintePagamentoDTO;
import it.tasgroup.iris.dto.flussi.DistintePagamentoDTOLight;
import it.tasgroup.iris.shared.util.CausaleFormatter;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

public class PagamentoDTOBuilder {    

	/**
	 * 
	 * @param listaPag
	 * @return
	 */
	public static List<DistintePagamentoDTO> populateListPagamentoDTO(List<Pagamenti> listaPag) {
		List<DistintePagamentoDTO> dtos = new ArrayList<DistintePagamentoDTO>();

		for (Pagamenti p : listaPag) {
			DistintePagamentoDTO dto = DistintePagamentoDTOBuilder.populateDistintePagamentoDTO(p.getFlussoDistinta());
			dtos.add(dto);
		}

		return dtos;
	}

	public static DistintePagamentoDTOLight populatePagamentoDTOLight(Pagamenti pagamento){
		return populatePagamentoDTOLight(pagamento, null, null);
	}

	/**
	 * 
	 * @param gestioneFlussi
	 * @return
	 */
	public static DistintePagamentoDTOLight populatePagamentoDTOLight(Pagamenti pagamenti, Map<String, String> mapEnti, HashMap<String, HashMap<String, String>> mapTipiTributo) {
		String sep = "<BR>";
		DistintePagamentoDTOLight dto = new DistintePagamentoDTOLight();
		GestioneFlussi gestioneFlussi = pagamenti.getFlussoDistinta();
		dto.setId(pagamenti.getId().intValue());
		dto.setIdDistintaPagamento(gestioneFlussi.getId());
		dto.setDataCreazione(gestioneFlussi.getTmbcreazione());
		dto.setStato(gestioneFlussi.getStato());
		dto.setImporto(gestioneFlussi.getTotimportipositivi());
		dto.setCodTransazione(gestioneFlussi.getCodTransazione());
		dto.setImportoCommissione(gestioneFlussi.getImportoCommissioni());
		dto.setUtenteCreatore(gestioneFlussi.getUtentecreatore());
		
		dto.setCodTransazionePSP(gestioneFlussi.getCodTransazionePSP());
		dto.setNomePSP(gestioneFlussi.getCfgGatewayPagamento().getSystemName());
		dto.setEmailVersante(gestioneFlussi.getEmailVersante());
		dto.setDescrIstitutoAttestante(gestioneFlussi.getDescrizioneAttestante()!=null?gestioneFlussi.getDescrizioneAttestante():"");
		dto.setIdentificativoAttestante(gestioneFlussi.getIdentificativoAttestante() != null ? gestioneFlussi.getIdentificativoAttestante() : "");
		dto.setTipoIdentificativoAttestante(gestioneFlussi.getTipoIdentificativoAttestante() != null ? gestioneFlussi.getTipoIdentificativoAttestante() : "");
		
		CfgFornitoreGateway fornitore = gestioneFlussi.getCfgGatewayPagamento().getCfgFornitoreGateway();
		String circuito = fornitore != null ? fornitore.getDescrizione() : "";
		dto.setCircuito(circuito);
		
		CfgModalitaPagamento modalitaPagamento = gestioneFlussi.getCfgGatewayPagamento().getCfgModalitaPagamento();
		String descrizioneModalitaPagamento = modalitaPagamento!=null?modalitaPagamento.getDescrizione():"";
		dto.setModalitaPagamento(descrizioneModalitaPagamento);

		try{
			StatiPagamentiIris st = StatiPagamentiIris.getStatiPagamentiIrisFromPaga(pagamenti.getStPagamento());
			dto.setStatoPagamento(st == null ? "NP" : st.getFludMapping());
			dto.setFlagIncasso(pagamenti.getFlagIncasso());
			dto.setIdQuietanza(pagamenti.getId());
			dto.setCoPagante(pagamenti.getCoPagante());
			dto.setOpInserimento(pagamenti.getOpInserimento());
			dto.setTipoSpontaneo(pagamenti.getCondPagamento().getPendenza().getTributoEnte().getFlPredeterm());
			dto.setNotePagamento(pagamenti.getNotePagamento());
			if (pagamenti.getRevochePagamento() != null) {
			   for(RevochePagamento revoca : pagamenti.getRevochePagamento()) {
				   dto.addRevocaPagamentoDTO(RevocaPagamentoDTOBuilder.populateRevocaPagamentoDTO(revoca));
			   }
			}
			
			dto.setNumeroRevoche(pagamenti.getNumeroRevoche());
			dto.setNumeroRevocheDaValutare(pagamenti.getNumeroRevocheDaValutare());
			
			if(mapEnti != null && !mapEnti.isEmpty()){
				dto.setDenomEnte(mapEnti.get(pagamenti.getIdEnte()));
			} else {
				dto.setDenomEnte(pagamenti.getIdEnte());
			}
			
			dto.setDesTributo(pagamenti.getCdTrbEnte());
			if(mapTipiTributo != null && !mapTipiTributo.isEmpty() && mapTipiTributo.get(pagamenti.getIdEnte()) != null){
				HashMap<String, String> trib = mapTipiTributo.get(pagamenti.getIdEnte());
				if (!trib.isEmpty() && trib.get(pagamenti.getCdTrbEnte()) != null)
					dto.setDesTributo(trib.get(pagamenti.getCdTrbEnte()));
			} 

		}catch (Exception e) {
			e.printStackTrace();
		}
        dto.setIdPagamento(pagamenti.getCondPagamento().getIdPagamento());
        dto.setIuv(gestioneFlussi.getIuv());
		dto.setIdPendenza(pagamenti.getIdPendenzaente());
		dto.setCausalePagamento(pagamenti.getCondPagamento().getPendenza().getDeCausale());
		
		dto.setCausalePagamentoFormattata(CausaleFormatter.format(dto.getCausalePagamento(), pagamenti.getCdTrbEnte()));
		
		dto.setImPagato(pagamenti.getImPagato());
		dto.setIdDocumentoRepository(gestioneFlussi.getIdDocumentoRepository());

		dto.setCodPagamento(gestioneFlussi.getCodPagamento());

		if (pagamenti.getCondPagamento() != null && pagamenti.getCondPagamento().getPendenza() != null) {
			Pendenza pendenza = pagamenti.getCondPagamento().getPendenza();
			String dest = "";
			Set<DestinatariPendenza> destinatari = pendenza.getDestinatari();
			for (DestinatariPendenza destinatariPendenza : destinatari) {
				if (!DestinatariPendenza.TIPO_DEST_DELEGATO.equals(destinatariPendenza.getTiDestinatario())) {
					dest += dest != "" ? sep : "";
					dest += destinatariPendenza.getCoDestinatario();
				}
			}
			dto.setCodFiscDebitore(dest);
			
			dto.setAnnoRif(pendenza.getAnnoRiferimento());
			dto.setNote(pagamenti.getCondPagamento().getPendenza().getNote());
			
			TributoStrutturato tributoStrutturato = pendenza.getTributoStrutturato();
			if (tributoStrutturato != null) {
				dto.setNoteVersante(tributoStrutturato.getNoteVersante());
			}
			Set<DestinatariPendenza> destinatariPendenza = pendenza.getDestinatari();
			if (destinatariPendenza != null && !destinatariPendenza.isEmpty()) {
				DestinatariPendenza destinatarioPendenza =destinatariPendenza.iterator().next();
				dto.setDenominazioneDebitore(destinatarioPendenza.getDeDestinatario());
			}
			dto.setImportoDovuto(pagamenti.getCondPagamento().getImTotale());
			dto.setCausaleVersamento(pagamenti.getCondPagamento().getCausalePagamento());
			List<VocePagamento> vociPagamento = pagamenti.getCondPagamento().getVociPagamento();
			if (vociPagamento != null && !vociPagamento.isEmpty()) {
				String vociStr ="";
				for(VocePagamento vp : vociPagamento) {
					vociStr += vp.getDeVoce() + "=" + vp.getImVoce() + ";";
				}
				dto.setVociImporto(vociStr.endsWith(";") ? vociStr.substring(0, vociStr.length()-1) : vociStr);
				
			}
		}

		dto.setAssociatedDocAvailable(pagamenti.isAssociatedDocumentAvailable());
		dto.setIdRiscossionePSP(pagamenti.getIdRiscossionePSP());
		
		return dto;
	}
  
	public static List<DistintePagamentoDTOLight> populateListPagamentoDTOLight(List<Pagamenti> listagf){
		return populateListPagamentoDTOLight(listagf, null, null);
	}

	/**
	 * 
	 * @param gestioneFlussi
	 * @return
	 */
	public static List<DistintePagamentoDTOLight> populateListPagamentoDTOLight(List<Pagamenti> listagf, Map<String, String> mapEnti, HashMap<String, HashMap<String, String>> mapTipiTributo) {

		List<DistintePagamentoDTOLight> returnDTOList = new ArrayList<DistintePagamentoDTOLight>();

		for (Pagamenti gf : listagf) {
			DistintePagamentoDTOLight dto = populatePagamentoDTOLight(gf, mapEnti, mapTipiTributo);
			returnDTOList.add(dto);
		}

		return returnDTOList;
	}
	
	
}

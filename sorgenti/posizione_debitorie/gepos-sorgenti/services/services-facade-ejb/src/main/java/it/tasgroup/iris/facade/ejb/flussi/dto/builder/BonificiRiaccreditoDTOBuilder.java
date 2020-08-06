package it.tasgroup.iris.facade.ejb.flussi.dto.builder;

import it.tasgroup.iris.domain.AllineamentiElettroniciArchivi;
import it.tasgroup.iris.domain.BaseEntity;
import it.tasgroup.iris.domain.BonificiRiaccredito;
import it.tasgroup.iris.domain.BozzeBonificiRiaccredito;
import it.tasgroup.iris.domain.EsitiBb;
import it.tasgroup.iris.domain.EsitiRct;
import it.tasgroup.iris.domain.IncassiBonificiRh;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.Rid;
import it.tasgroup.iris.domain.helper.BillItemInspector;
import it.tasgroup.iris.dto.flussi.BonificiRiaccreditoDTO;
import it.tasgroup.iris.dto.flussi.BozzeBonificiRiaccreditoDTO;
import it.tasgroup.services.util.enumeration.EnumCodRendicontazione;

import java.util.ArrayList;
import java.util.List;

public class BonificiRiaccreditoDTOBuilder {

	
	/**
	 * 
	 * @param listabr
	 * @return
	 */
	
	public static List<BonificiRiaccreditoDTO> populateListBonificiRiaccreditoDTO(List<BonificiRiaccredito> listabr, Integer idBonSelected) {
		List<BonificiRiaccreditoDTO> dtos = new ArrayList<BonificiRiaccreditoDTO>();
		
		for (BonificiRiaccredito gf : listabr) {
			BonificiRiaccreditoDTO dto = populateBonificiRiaccreditoDTO(gf, idBonSelected);
			dtos.add(dto);
		}
		
		return dtos;
	}

	/**
	 * 
	 * @param BonificiRiaccredito
	 * @return
	 */
	public static BonificiRiaccreditoDTO populateBonificiRiaccreditoDTO(BonificiRiaccredito bonificiRiaccredito,  Integer idBonSelected) {
		BonificiRiaccreditoDTO dto = new BonificiRiaccreditoDTO();

		dto.setId(bonificiRiaccredito.getId());
		dto.setCodiceUnivoco(bonificiRiaccredito.getCodiceUnivoco());
		dto.setStato(bonificiRiaccredito.getStato());
		dto.setDataEsecuzione(bonificiRiaccredito.getDataEsecuzione());
		dto.setImporto(bonificiRiaccredito.getImporto());
		dto.setIbanBeneficiario(bonificiRiaccredito.getIbanBeneficiario());
		dto.setRagioneSocialeBeneficiario(bonificiRiaccredito.getRagioneSocialeBeneficiario());
		dto.setNumBozze(bonificiRiaccredito.getNumBozze());
		if (idBonSelected != null && idBonSelected.longValue() == dto.getId().longValue()){
			
			List<BozzeBonificiRiaccreditoDTO> listaBozzeDTO = new ArrayList<BozzeBonificiRiaccreditoDTO>();
			
			for (BozzeBonificiRiaccredito bozza : bonificiRiaccredito.getBozzeBonificiRiaccredito()){
				BozzeBonificiRiaccreditoDTO bozzaDTO = new BozzeBonificiRiaccreditoDTO();
				bozzaDTO.setImporto(bozza.getImporto());
				
				Pagamenti pagamentoValido = BillItemInspector.getUniqueValidPayment(bozza.getCondizionePagamento());
				
				bozzaDTO.setStato(pagamentoValido.getStPagamento());
				bozzaDTO.setDebitore(pagamentoValido.getCoPagante());
				bozzaDTO.setEnte(bozza.getCondizionePagamento().getEnte().getDenominazione());
				bozzaDTO.setTipodebito(bozza.getCondizionePagamento().getCdTrbEnte());
				bozzaDTO.setTipologiapagamento(pagamentoValido.getTiPagamento());
				bozzaDTO.setDataPagamento(pagamentoValido.getTsOrdine());
				bozzaDTO.setIdPagamento(bozza.getCondizionePagamento().getIdPagamento());
				
				listaBozzeDTO.add(bozzaDTO);
			}
			
			dto.setBozzeBonificiRiaccredito(listaBozzeDTO);
		}
		return dto;
	}
	
	/**
	 * 
	 * @param BonificiRiaccredito
	 * @return
	 */
	public static BonificiRiaccreditoDTO populateBonificiRiaccreditoDTONoBozze(BonificiRiaccredito bonificiRiaccredito) {
		BonificiRiaccreditoDTO dto = new BonificiRiaccreditoDTO();

		dto.setId(bonificiRiaccredito.getId());
		dto.setCodiceUnivoco(bonificiRiaccredito.getCodiceUnivoco());
		dto.setStato(bonificiRiaccredito.getStato());
		dto.setDataEsecuzione(bonificiRiaccredito.getDataEsecuzione());
		dto.setImporto(bonificiRiaccredito.getImporto());
		dto.setIbanBeneficiario(bonificiRiaccredito.getIbanBeneficiario());
		dto.setRagioneSocialeBeneficiario(bonificiRiaccredito.getRagioneSocialeBeneficiario());
		dto.setNumBozze(bonificiRiaccredito.getNumBozze());
		
		return dto;
	}
	
	/**
	 * 
	 * @param BonificiRiaccredito
	 * @return
	 */
	public static BozzeBonificiRiaccreditoDTO populateBozzaBonificiRiaccreditoDTO(BozzeBonificiRiaccredito bozza, BaseEntity esito) {

		BozzeBonificiRiaccreditoDTO bozzaDTO = new BozzeBonificiRiaccreditoDTO();
		
		Pagamenti pagamentoValido = BillItemInspector.getUniqueValidPayment(bozza.getCondizionePagamento());
		
		bozzaDTO.setImporto(bozza.getImporto());		
		bozzaDTO.setStato(pagamentoValido.getStPagamento());
		bozzaDTO.setDebitore(pagamentoValido.getCoPagante());
		bozzaDTO.setEnte(bozza.getCondizionePagamento().getEnte().getDenominazione());
		bozzaDTO.setTipodebito(bozza.getCondizionePagamento().getCdTrbEnte());
		bozzaDTO.setTipologiapagamento(pagamentoValido.getTiPagamento());
		bozzaDTO.setDataPagamento(pagamentoValido.getTsOrdine());
		bozzaDTO.setIdPagamento(bozza.getCondizionePagamento().getIdPagamento());

		if (bozza.getTipoEsito().equals(EnumCodRendicontazione.BB.getChiave())) 
			bozzaDTO.setIdRiconciliazione(bozza.getTipoEsito() + ":" + bozza.getIdEsitoOrigine() + ":" + ((esito != null) ? ((EsitiBb)esito).getIdRiconciliazione() : "NOT FOUND!"));
		else if (bozza.getTipoEsito().equals(EnumCodRendicontazione.SL.getChiave())) 
			bozzaDTO.setIdRiconciliazione(bozza.getTipoEsito() + ":" + bozza.getIdEsitoOrigine() + ":" + ((esito != null) ? ((EsitiRct)esito).getIdRiconciliazione() : "NOT FOUND!"));
		else if (bozza.getTipoEsito().equals(EnumCodRendicontazione.RH.getChiave())) 
			bozzaDTO.setIdRiconciliazione(bozza.getTipoEsito() + ":" + bozza.getIdEsitoOrigine() + ":" + ((esito != null) ? ((IncassiBonificiRh)esito).getIdRiconciliazione() : "NOT FOUND!"));
		else if (bozza.getTipoEsito().equals(EnumCodRendicontazione.IR.getChiave())) 
			bozzaDTO.setIdRiconciliazione(bozza.getTipoEsito() + ":" + bozza.getIdEsitoOrigine() + ":" + ((esito != null) ? ((Rid)esito).getIdDisposizioneOrig() : "NOT FOUND!"));
		else if (bozza.getTipoEsito().equals(EnumCodRendicontazione.AL.getChiave())) 
			bozzaDTO.setIdRiconciliazione(bozza.getTipoEsito() + ":" + bozza.getIdEsitoOrigine() + ":" + ((esito != null) ? ((AllineamentiElettroniciArchivi)esito).getIdDisposizioneOrig() : "NOT FOUND!"));
		

		return bozzaDTO;
	}
}

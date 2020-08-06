package it.tasgroup.iris.facade.ejb.flussi.dto.builder;


import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.iris.domain.BonificiRiaccredito;
import it.tasgroup.iris.domain.BozzeBonificiRiaccredito;
import it.tasgroup.iris.domain.CasellarioDispo;
import it.tasgroup.iris.domain.DistinteRiaccredito;
import it.tasgroup.iris.domain.EsitiBonificiRiaccredito;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.dto.flussi.CasellarioDispoDTO;
import it.tasgroup.iris.dto.flussi.DistinteRiaccreditoDTO;
import it.tasgroup.iris.dto.flussi.DistinteRiaccreditoDTOLight;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ColumnResult;

public class DistinteRiaccreditoDTOBuilder {

		
		/**
		 * 
		 * @param listadr
		 * @return
		 */
		
		public static List<DistinteRiaccreditoDTO> populateListDistinteRiaccreditoDTO(List<DistinteRiaccredito> listadr) {
			List<DistinteRiaccreditoDTO> dtos = new ArrayList<DistinteRiaccreditoDTO>();
			
			for (DistinteRiaccredito gf : listadr) {
				DistinteRiaccreditoDTO dto = populateDistinteRiaccreditoDTO(gf);
				dtos.add(dto);
			}
			
			return dtos;
		}

		/**
		 * 
		 * @param distinteRiaccredito
		 * @return
		 */
		public static DistinteRiaccreditoDTO populateDistinteRiaccreditoDTO(DistinteRiaccredito distinteRiaccredito) {
			DistinteRiaccreditoDTO dto = new DistinteRiaccreditoDTO();

			dto.setId(distinteRiaccredito.getId());
			dto.setCodTransazione(distinteRiaccredito.getCodTransazione());
			dto.setStato(distinteRiaccredito.getStato());
			dto.setDataCreazione(distinteRiaccredito.getDataCreazione());
			dto.setImporto(distinteRiaccredito.getImporto().abs());
			dto.setNumeroDisposizioni(distinteRiaccredito.getNumeroDisposizioni());
			dto.setCasellarioDispo(populateCasellarioDispoDTO(distinteRiaccredito.getCasellarioDispo(),false));
			
			return dto;
		}
		
		
		/**
		 * 
		 * @param distinteRiaccredito
		 * @return
		 */
		public static DistinteRiaccreditoDTOLight populateDistinteRiaccreditoDTOLight(DistinteRiaccredito distinteRiaccredito) {
			DistinteRiaccreditoDTOLight dto = new DistinteRiaccreditoDTOLight();
			
			dto.setId(distinteRiaccredito.getId());
			dto.setStato(distinteRiaccredito.getStato());
			dto.setDataCreazione(distinteRiaccredito.getDataCreazione());
			dto.setImporto(distinteRiaccredito.getImporto().doubleValue());
			
			return dto;
		}
		
		/**
		 * 
		 * @param listadr
		 * @return
		 */
	public static List<DistinteRiaccreditoDTOLight> populateListDistinteRiaccreditoDTOLight(List<DistinteRiaccredito> listadr) {
			
			List<DistinteRiaccreditoDTOLight> returnDTOList = new ArrayList<DistinteRiaccreditoDTOLight>();

			for (DistinteRiaccredito dr : listadr) {
				DistinteRiaccreditoDTOLight dto = populateDistinteRiaccreditoDTOLight(dr);
				returnDTOList.add(dto);
			}
			
			return returnDTOList;
		}
	
	
	/**
     * 
     * @param row
     * @return
     */
	public static DistinteRiaccreditoDTOLight populateRicercaDistinteRiaccreditoDTOLight(Object[] row) {
	    DistinteRiaccreditoDTOLight dto = new DistinteRiaccreditoDTOLight();
	    
	    Timestamp data_spedizione = (Timestamp) row[0];
        Timestamp ts_inserimento = (Timestamp) row[1];
	    String codice_riferimento = (String) row[2];
	    String rag_sociale_beneficiario = (String) row[3];
	    String iban_beneficiario = (String) row[4];
	    BigDecimal importo = (BigDecimal) row [5];
	    String id_pag_cond_ente = (String) row[6];
	    String stato = (String) row[7];
	    String de_trb = (String) row[8];
	    Timestamp ts_decorrenza = (Timestamp) row[9];
	    String cod_pagamento = (String) row[10];
	    String causale = (String) row[11];


	    dto.setDataSpedizione(data_spedizione);
	    dto.setTsInserimento(ts_inserimento);
	    dto.setCodiceRiferimento(codice_riferimento);
	    dto.setRagSocBenefic(rag_sociale_beneficiario);
	    dto.setIbanBenefic(iban_beneficiario);
	    dto.setImporto(importo != null? importo.doubleValue() : 0L);
	    dto.setIdPagCondEnte(id_pag_cond_ente);
	    dto.setStato(stato);
	    dto.setDeTrb(de_trb);
	    dto.setTsDecorrenza(ts_decorrenza);
	    dto.setCodPagamento(cod_pagamento);
	    dto.setCausale(causale);

	    return dto;
	}

	/**
	 * 
	 * @param listadr
	 * @return
	 */
	public static List<DistinteRiaccreditoDTOLight> populateListRicercaDistinteRiaccreditoDTOLight(List<Object[]> listadr) {

	    List<DistinteRiaccreditoDTOLight> returnDTOList = new ArrayList<DistinteRiaccreditoDTOLight>();

	    for (Object[] dr : listadr) {
	        DistinteRiaccreditoDTOLight dto = populateRicercaDistinteRiaccreditoDTOLight(dr);
	        returnDTOList.add(dto);
	    }

	    return returnDTOList;
	}
	
	/**
	 * 
	 * @param listaCasellarioDispo
	 * @return
	 */
	public static List<CasellarioDispoDTO> populateListCasellarioDispoDTO(List<CasellarioDispo> listaCasellarioDispo) {

		List<CasellarioDispoDTO> dtos = new ArrayList<CasellarioDispoDTO>();

		for (CasellarioDispo cd : listaCasellarioDispo) {
			CasellarioDispoDTO dto = populateCasellarioDispoDTO(cd,false);
			dtos.add(dto);
		}

		return dtos;
	}
	
	/**
	 * 
	 * @param cd
	 * @return
	 */
	public static CasellarioDispoDTO populateCasellarioDispoDTO(CasellarioDispo cd, boolean isLoadFlussoCBI) {
		if (cd == null)
			return null;
		
		CasellarioDispoDTO dto = new CasellarioDispoDTO();
		dto.setId(cd.getId());
		dto.setDescErrore(cd.getDescErrore());
		dto.setDimensione(cd.getDimensione());
		dto.setNomeSupporto(cd.getNomeSupporto());
		dto.setNumeroRecord(cd.getNumeroRecord());
		dto.setTipoErrore(cd.getTipoErrore());
		dto.setDescErrore(cd.getDescErrore()); 
		
		if (isLoadFlussoCBI)
			dto.setFlussoCBI(cd.getFlussoCbi());
		
		return dto;
	}
	
	
}



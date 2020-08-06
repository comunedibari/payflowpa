package it.tasgroup.iris.facade.ejb.flussi.dto.builder;

import it.tasgroup.iris.domain.CasellarioInfo;
import it.tasgroup.iris.domain.EsitiBonificiRiaccredito;
import it.tasgroup.iris.domain.Rendicontazioni;
import it.tasgroup.iris.dto.flussi.CasellarioInfoDTO;
import it.tasgroup.iris.dto.flussi.EsitiBonificiRiaccreditoDTO;
import it.tasgroup.iris.dto.flussi.RendicontazioniDTO;

import java.util.ArrayList;
import java.util.List;

public class EsitiBonificiRiaccreditoDTOBuilder {

	
	/**
	 * 
	 * @param listaeb
	 * @return
	 */
	
	public static List<EsitiBonificiRiaccreditoDTO> populateListEsitiBonificiRiaccreditoDTO(List<EsitiBonificiRiaccredito> listaeb) {
		List<EsitiBonificiRiaccreditoDTO> dtos = new ArrayList<EsitiBonificiRiaccreditoDTO>();
		
		for (EsitiBonificiRiaccredito eb : listaeb) {
			EsitiBonificiRiaccreditoDTO dto = populateEsitiBonificiRiaccreditoDTO(eb);
			dtos.add(dto);
		}
		
		return dtos;
	}

	/**
	 * 
	 * @param esitibonificiRiaccredito
	 * @return
	 */
	public static EsitiBonificiRiaccreditoDTO populateEsitiBonificiRiaccreditoDTO(EsitiBonificiRiaccredito esitiBonificiRiaccredito) {
		
		if (esitiBonificiRiaccredito == null)
			return null;
		
		EsitiBonificiRiaccreditoDTO dto = new EsitiBonificiRiaccreditoDTO();
		if(esitiBonificiRiaccredito != null) {
			dto.setCausale(esitiBonificiRiaccredito.getCausale());
			dto.setCodiceRiferimento(esitiBonificiRiaccredito.getCodiceRiferimento());
			dto.setDataContabileAddebito(esitiBonificiRiaccredito.getDataContabileAddebito());
			dto.setDataEsecuzione(esitiBonificiRiaccredito.getDataEsecuzione());
			dto.setDataOrdine(esitiBonificiRiaccredito.getDataOrdine());
			dto.setDataValutaBeneficiario(esitiBonificiRiaccredito.getDataValutaBeneficiario());
			dto.setDataValutaOrdinante(esitiBonificiRiaccredito.getDataValutaOrdinante());
			dto.setImporto(esitiBonificiRiaccredito.getImporto());
			dto.setTipoAnomalia(esitiBonificiRiaccredito.getTipoAnomalia());
			dto.setTipoCodiceRiferimento(esitiBonificiRiaccredito.getTipoCodiceRiferimento());
			
			if(esitiBonificiRiaccredito.getRendicontazioni() != null) {
				dto.setRendicontazioni(populateRendicontazioniDTO(esitiBonificiRiaccredito.getRendicontazioni()));
			}
		}
		
		return dto;
	}
	
	/**
	 * 
	 * @param listaRendicontazioni
	 * @return
	 */
	public static List<RendicontazioniDTO> populateListRendicontazioniDTO(List<Rendicontazioni> listaRendicontazioni) {

		List<RendicontazioniDTO> dtos = new ArrayList<RendicontazioniDTO>();

		for (Rendicontazioni rend : listaRendicontazioni) {
			RendicontazioniDTO dto = populateRendicontazioniDTO(rend);
			dtos.add(dto);
		}

		return dtos;
	}
	
	/**
	 * 
	 * @param rend
	 * @return
	 */
	public static RendicontazioniDTO populateRendicontazioniDTO(Rendicontazioni rend) {
		if (rend == null)
			return null;
		
		RendicontazioniDTO dto = new RendicontazioniDTO();

		dto.setId(rend.getId());
		dto.setDataCreazione(rend.getDataCreazione());
		dto.setImporto(rend.getImporto());
		dto.setNumeroEsiti(rend.getNumeroEsiti());
		dto.setNumEsitiInsoluto(rend.getNumEsitiInsoluto());
		dto.setStato(rend.getStato());
		dto.setIdFlusso(rend.getIdFlusso());
		dto.setIdPsp(rend.getCasellarioInfo().getMittente());
		dto.setDataRegolamento(rend.getDataRegolamento());
		dto.setTransRefNumber(rend.getIdRegolamento());
		
		dto.setCasellarioInfo(populateCasellarioInfoDTO(rend.getCasellarioInfo(),false));
	
		return dto;
	}
	
	/**
	 * 
	 * @param listaCasellarioInfo
	 * @return
	 */
	public static List<CasellarioInfoDTO> populateListCasellarioInfoDTO(List<CasellarioInfo> listaCasellarioInfo) {

		List<CasellarioInfoDTO> dtos = new ArrayList<CasellarioInfoDTO>();

		for (CasellarioInfo cd : listaCasellarioInfo) {
			CasellarioInfoDTO dto = populateCasellarioInfoDTO(cd,false);
			dtos.add(dto);
		}

		return dtos;
	}
	
	/**
	 * 
	 * @param cd
	 * @return
	 */
	public static CasellarioInfoDTO populateCasellarioInfoDTO(CasellarioInfo ci, boolean isLoadFlussoCBI) {
		if (ci == null)
			return null;
	
		CasellarioInfoDTO dto = new CasellarioInfoDTO();

		dto.setId(ci.getId());
		dto.setDescErrore(ci.getDescErrore());
		dto.setDimensione(ci.getDimensione());
		dto.setNomeSupporto(ci.getNomeSupporto());
		dto.setNumeroRecord(ci.getNumeroRecord());
		dto.setTipoErrore(ci.getTipoErrore());
		dto.setDescErrore(ci.getDescErrore()); 

		if (isLoadFlussoCBI)
			dto.setFlussoCBI(ci.getFlussoCbi());
		
		return dto;
	}
}

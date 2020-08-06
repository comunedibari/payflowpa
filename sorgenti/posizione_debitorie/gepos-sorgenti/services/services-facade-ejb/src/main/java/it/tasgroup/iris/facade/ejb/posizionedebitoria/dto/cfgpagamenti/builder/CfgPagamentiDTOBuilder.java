/**
 * 
 */
package it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.cfgpagamenti.builder;


import it.tasgroup.iris.domain.CfgCanalePagamento;
import it.tasgroup.iris.domain.CfgCommissionePagamento;
import it.tasgroup.iris.domain.CfgDocumentoPagamento;
import it.tasgroup.iris.domain.CfgFornitoreGateway;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CfgModalitaPagamento;
import it.tasgroup.iris.domain.CfgStrumentoPagamento;
import it.tasgroup.iris.domain.CfgTipoCommissione;
import it.tasgroup.iris.domain.CfgUtenteModalitaPagamento;
import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.dto.anagrafica.ContoTecnicoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgCanalePagamentoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgCommissionePagamentoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgDocumentoPagamentoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgFornitoreGatewayDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgGatewayPagamentoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgModalitaPagamentoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgStrumentoPagamentoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgTipoCommissioneDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgUtenteModalitaPagamentoDTO;
import it.tasgroup.services.util.enumeration.EnumBoolean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author pazzik
 * 
 */
public class CfgPagamentiDTOBuilder {

	/**
	 * 
	 * @param listaModalitaPagamento
	 * @return
	 */
	public static List<CfgModalitaPagamentoDTO> populateListModalitaPagamentoDTO(List<CfgModalitaPagamento> listaModalitaPagamento) {

		List<CfgModalitaPagamentoDTO> dtos = new ArrayList<CfgModalitaPagamentoDTO>();

		for (CfgModalitaPagamento cfg : listaModalitaPagamento) {
			CfgModalitaPagamentoDTO dto = populateModalitaPagamentoDTO(cfg);
			dtos.add(dto);
		}

		return dtos;
	}
	

	/**
	 * 
	 * @param cfg
	 * @return
	 */
	public static CfgModalitaPagamentoDTO populateModalitaPagamentoDTO(CfgModalitaPagamento cfg) {
		if (cfg == null)
			return null;
		
		CfgModalitaPagamentoDTO dto = new CfgModalitaPagamentoDTO();
		dto.setId(Long.toString(cfg.getId()));
		dto.setBundleKey(cfg.getBundleKey()); 
		dto.setDescrizione(cfg.getDescrizione());
		dto.setPaymentMethod(cfg.getPaymentMethod());
		dto.setEnabled(cfg.getEnabled());
		return dto;
	}
	

	/**
	 * 
	 * @param listaCfg
	 * @return
	 */
	public static List<CfgGatewayPagamentoDTO> populateListCfgGatewayPagamentoDTO(List<CfgGatewayPagamento> listaCfg) {
		
		List<CfgGatewayPagamentoDTO> dtos = new ArrayList<CfgGatewayPagamentoDTO>();
		
		for (CfgGatewayPagamento cpg : listaCfg) {
			CfgGatewayPagamentoDTO dto = populateCfgGatewayPagamentoDTO(cpg);
			dtos.add(dto);
		}
		
		return dtos;
	}

	/**
	 * 
	 * @param cfgGatewayPagamento
	 * @return
	 */
	public static CfgGatewayPagamentoDTO populateCfgGatewayPagamentoDTO(CfgGatewayPagamento cfgGatewayPagamento) {
		CfgGatewayPagamentoDTO dto = new CfgGatewayPagamentoDTO();

		dto.setId(cfgGatewayPagamento.getId());
		dto.setApplicationId(cfgGatewayPagamento.getApplicationId());
		dto.setApplicationIp(cfgGatewayPagamento.getApplicationIp());
		dto.setBundleKey(cfgGatewayPagamento.getBundleKey());
		dto.setCfgCanalePagamento(populateCanalePagamentoDTO(cfgGatewayPagamento.getCfgCanalePagamento()));
		dto.setCfgCommissionePagamenti(populateListCommissionePagamentoDTO(cfgGatewayPagamento.getCfgCommissionePagamenti()));
		dto.setCfgDocumentoPagamento(populateDocumentoPagamentoDTO(cfgGatewayPagamento.getCfgDocumentoPagamento()));
		dto.setCfgFornitoreGateway(populateFornitoreGatewayDTO(cfgGatewayPagamento.getCfgFornitoreGateway()));
		dto.setCfgModalitaPagamento(populateModalitaPagamentoDTO(cfgGatewayPagamento.getCfgModalitaPagamento()));
		dto.setCfgStrumentoPagamento(populateStrumentoPagamentoDTO(cfgGatewayPagamento.getCfgStrumentoPagamento()));
		dto.setDataFineValidita(cfgGatewayPagamento.getDataFineValidita());
		dto.setDataInizioValidita(cfgGatewayPagamento.getDataInizioValidita());
		dto.setDescGateway(cfgGatewayPagamento.getDescGateway());
		dto.setMaxImporto(cfgGatewayPagamento.getMaxImporto());
		dto.setMolteplicita(cfgGatewayPagamento.getMolteplicita());
		dto.setStato(cfgGatewayPagamento.getStato());
		dto.setSystemId(cfgGatewayPagamento.getSystemId());
		dto.setSubsystemId(cfgGatewayPagamento.getSubsystemId());
		dto.setTimeout(cfgGatewayPagamento.getTimeout());
		dto.setTimeoutAup(cfgGatewayPagamento.getTimeoutAup());
		dto.setTimeoutNp(cfgGatewayPagamento.getTimeoutNp());
		dto.setDescrizione(cfgGatewayPagamento.getDescrizione());
		dto.setSystemName(cfgGatewayPagamento.getSystemName());
		dto.setDisponibilitaServizio(cfgGatewayPagamento.getDisponibilitaServizio());
		dto.setModelloVersamento(cfgGatewayPagamento.getModelloVersamento());
		
		dto.setFlPagabileIris(cfgGatewayPagamento.getFlPagabileIris());
		dto.setUrlInfoCanale(cfgGatewayPagamento.getUrlInfoCanale());
		dto.setUrlInfoPsp(cfgGatewayPagamento.getUrlInfoPsp());
		dto.setModelloVersamento(cfgGatewayPagamento.getModelloVersamento());
		dto.setTipoVersamento(cfgGatewayPagamento.getTipoVersamento());
		                 
		dto.setFlModRiversamento(cfgGatewayPagamento.getEnumModRiversamento());
		dto.setFlRendRiversamento(EnumBoolean.getByKey(cfgGatewayPagamento.getFlagRendRiversamento()).getChiaveBundle());
		
		dto.setContoTecnico(fillContoTecnicoDTO(cfgGatewayPagamento.getContoTecnico()));
		dto.setAcl(cfgGatewayPagamento.getAcl());
		dto.setFlMdbGestito(cfgGatewayPagamento.getFlMdbGestito());
		
		return dto;
	}
	
	private static ContoTecnicoDTO fillContoTecnicoDTO(ContoTecnico contoTecnico) {
		
		ContoTecnicoDTO contoDTO = new ContoTecnicoDTO();
		
		if (contoTecnico!=null){
			
			contoDTO.setDescrizione(contoTecnico.getDescrizione());
			contoDTO.setIntestazione(contoTecnico.getIntestazione());
			contoDTO.setIban(contoTecnico.getIban());
		}
		
		return contoDTO;
	}


	/**
	 * 
	 * @param cfg
	 * @return
	 */
	public static CfgCanalePagamentoDTO populateCanalePagamentoDTO(CfgCanalePagamento cfg) {
		CfgCanalePagamentoDTO dto = new CfgCanalePagamentoDTO();
		dto.setBundleKey(cfg.getBundleKey());
		dto.setDescrizione(cfg.getDescrizione().toLowerCase());
		return dto;
	}

	/**
	 * 
	 * @param listaCommissioni
	 * @return
	 */
	public static List<CfgCommissionePagamentoDTO> populateListCommissionePagamentoDTO(Set<CfgCommissionePagamento> listaCommissioni) {

		if (listaCommissioni == null)
			return null;
		
		List<CfgCommissionePagamentoDTO> dtos = new ArrayList<CfgCommissionePagamentoDTO>();

		for (CfgCommissionePagamento cfg : listaCommissioni) {
			
			CfgCommissionePagamentoDTO dto = populateCommissionePagamentoDTO(cfg);
			
			dtos.add(dto);
			
		}

		return dtos;
	}
	
	/**
	 * 
	 * @param cfg
	 * @return
	 */
	public static CfgCommissionePagamentoDTO populateCommissionePagamentoDTO(CfgCommissionePagamento cfg) {
		
		if (cfg == null)
			return null;
		
		CfgCommissionePagamentoDTO dto = new CfgCommissionePagamentoDTO();

		dto.setCfgTipoCommissione(populateTipoCommissionePagamentoDTO(cfg.getCfgTipoCommissione()));
		dto.setImportoA(cfg.getImportoA());
		dto.setImportoDa(cfg.getImportoDa());
		dto.setDivisa(cfg.getDivisa());
		dto.setValore(cfg.getValore());
		dto.setDescrizione(cfg.getDescrizione());
		
		return dto;
	}

	/**
	 * 
	 * @param cfg
	 * @return
	 */
	public static CfgTipoCommissioneDTO populateTipoCommissionePagamentoDTO(CfgTipoCommissione cfg) {
		
		if (cfg == null)
			return null;
		
		CfgTipoCommissioneDTO dto = new CfgTipoCommissioneDTO();
		dto.setId(Long.toString(cfg.getId()));
		dto.setBundleKey(cfg.getBundleKey());
		dto.setDescrizione(cfg.getDescrizione());
		dto.setFlStato(cfg.getFlStato());
		
		return dto;
	}

	/**
	 * 
	 * @param cfg
	 * @return
	 */
	public static CfgDocumentoPagamentoDTO populateDocumentoPagamentoDTO(CfgDocumentoPagamento cfg) {
		
		if (cfg == null)
			return null;
		
		CfgDocumentoPagamentoDTO dto = new CfgDocumentoPagamentoDTO();
		dto.setId(Long.toString(cfg.getId()));
		dto.setBundleKey(cfg.getBundleKey());
		dto.setDescrizione(cfg.getDescrizione());
		
		return dto;
	}

	/**
	 * 
	 * @param cfg
	 * @return
	 */
	public static CfgFornitoreGatewayDTO populateFornitoreGatewayDTO(CfgFornitoreGateway cfg) {
		if (cfg == null)
			return null;
		
		CfgFornitoreGatewayDTO dto = new CfgFornitoreGatewayDTO();
		dto.setId(cfg.getId());
		dto.setBundleKey(cfg.getBundleKey());
		dto.setDescrizione(cfg.getDescrizione());
		
		return dto;
	}

	/**
	 * 
	 * @param cfg
	 * @return
	 */
	public static CfgStrumentoPagamentoDTO populateStrumentoPagamentoDTO(CfgStrumentoPagamento cfg) {
		
		if (cfg == null)
			return null;
		
		CfgStrumentoPagamentoDTO dto = new CfgStrumentoPagamentoDTO();
		
		dto.setId(Long.toString(cfg.getId()));
		dto.setBundleKey(cfg.getBundleKey());
		dto.setDescrizione(cfg.getDescrizione());
		
		return dto;
	}
	
	/**
	 * 
	 * @param listaCfg
	 * @return
	 */
	public static List<CfgStrumentoPagamentoDTO> populateListCfgStrumentoPagamentoDTO(List<CfgStrumentoPagamento> listaCfg) {
		List<CfgStrumentoPagamentoDTO> dtos = new ArrayList<CfgStrumentoPagamentoDTO>();
		
		for (CfgStrumentoPagamento cps : listaCfg) {
			CfgStrumentoPagamentoDTO dto = populateStrumentoPagamentoDTO(cps);
			dtos.add(dto);
		}
		
		return dtos;
	}
	
	/**
	 * 
	 * @param listaCfgUtenteModalitaPagamento
	 * @return
	 */
	public static List<CfgUtenteModalitaPagamentoDTO> populateListUtenteModalitaPagamentoDTO(List<CfgUtenteModalitaPagamento> listaCfgUtenteModalitaPagamento) {

		List<CfgUtenteModalitaPagamentoDTO> dtos = new ArrayList<CfgUtenteModalitaPagamentoDTO>();

		for (CfgUtenteModalitaPagamento cfg : listaCfgUtenteModalitaPagamento) {
			CfgUtenteModalitaPagamentoDTO dto = populateUtenteModalitaPagamentoDTO(cfg);
			dtos.add(dto);
		}

		return dtos;
	}


	private static CfgUtenteModalitaPagamentoDTO populateUtenteModalitaPagamentoDTO(
			CfgUtenteModalitaPagamento cfg) {
		if (cfg == null)
			return null;
		
		CfgUtenteModalitaPagamentoDTO dto = new CfgUtenteModalitaPagamentoDTO();
		
		dto.setApplicationId(cfg.getCfgUtenteModalitaPagamentoId().getApplicationId());
		dto.setCodiceFiscale(cfg.getCfgUtenteModalitaPagamentoId().getCodiceFiscale());
		dto.setSystemId(cfg.getCfgUtenteModalitaPagamentoId().getSystemId());
		
		
		return dto;
	}


	public static List<CfgFornitoreGatewayDTO> populateListCfgFornitoreGatewayDTO(
			List<CfgFornitoreGateway> lstaCfgFornitoreGateway) {
		List<CfgFornitoreGatewayDTO> dtos = new ArrayList<CfgFornitoreGatewayDTO>();

		for (CfgFornitoreGateway cfg : lstaCfgFornitoreGateway) {
			CfgFornitoreGatewayDTO dto = populateFornitoreGatewayDTO(cfg);
			dtos.add(dto);
		}

		return dtos;
	}


	




}

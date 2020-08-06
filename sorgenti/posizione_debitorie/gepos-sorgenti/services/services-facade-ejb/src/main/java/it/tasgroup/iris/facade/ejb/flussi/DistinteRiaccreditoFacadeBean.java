package it.tasgroup.iris.facade.ejb.flussi;

import it.nch.is.fo.common.CodiceDescrizioneVO;
import it.tasgroup.iris.business.ejb.client.flussi.DistinteRiaccreditoBusinessLocal;
import it.tasgroup.iris.business.ejb.client.flussi.MonitoraggioFlussiBusinessLocal;
import it.tasgroup.iris.domain.BaseEntity;
import it.tasgroup.iris.domain.BonificiRiaccredito;
import it.tasgroup.iris.domain.BozzeBonificiRiaccredito;
import it.tasgroup.iris.domain.DistinteRiaccredito;
import it.tasgroup.iris.domain.EsitiBonificiRiaccredito;
import it.tasgroup.iris.domain.Rendicontazioni;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.BonificiRiaccreditoDTO;
import it.tasgroup.iris.dto.flussi.BozzeBonificiRiaccreditoDTO;
import it.tasgroup.iris.dto.flussi.CasellarioDispoDTO;
import it.tasgroup.iris.dto.flussi.CasellarioInfoDTO;
import it.tasgroup.iris.dto.flussi.DistinteRiaccreditoDTO;
import it.tasgroup.iris.dto.flussi.DistinteRiaccreditoDTOLight;
import it.tasgroup.iris.dto.flussi.EsitiBonificiRiaccreditoDTO;
import it.tasgroup.iris.facade.ejb.client.flussi.DistinteRiaccreditoFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.flussi.DistinteRiaccreditoFacadeRemote;
import it.tasgroup.iris.facade.ejb.flussi.dto.builder.BonificiRiaccreditoDTOBuilder;
import it.tasgroup.iris.facade.ejb.flussi.dto.builder.DistinteRiaccreditoDTOBuilder;
import it.tasgroup.iris.facade.ejb.flussi.dto.builder.EsitiBonificiRiaccreditoDTOBuilder;
import it.tasgroup.services.util.enumeration.EnumCodRendicontazione;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "DistinteRiaccreditoFacade")
public class DistinteRiaccreditoFacadeBean implements DistinteRiaccreditoFacadeLocal, DistinteRiaccreditoFacadeRemote {
	
	private static final Logger LOGGER = LogManager.getLogger(DistinteRiaccreditoFacadeBean.class);	
	
	@EJB(name = "DistinteRiaccreditoBusiness")
	private DistinteRiaccreditoBusinessLocal distinteRiaccreditoBusinessBean;
	
	@EJB(name = "MonitoraggioFlussiBusiness")
	private MonitoraggioFlussiBusinessLocal monitoraggioFlussiBusinessBean;
	
	/**
	 * 
	 */
	@Override
	public ContainerDTO readDistinteRiaccreditoListLight(ContainerDTO containerDTO){
		
	    List<Object[]> listaDistinteRiaccredito = distinteRiaccreditoBusinessBean.getListDistinteRiaccreditoByFilterParameters(containerDTO);
		
		List<DistinteRiaccreditoDTOLight> dtoCollection = DistinteRiaccreditoDTOBuilder.populateListRicercaDistinteRiaccreditoDTOLight(listaDistinteRiaccredito);
		
		containerDTO.addAllOutputDTO(dtoCollection);
		
		return containerDTO;		
	}
	@Override
	public ContainerDTO readDistinteRiaccreditoListFull(ContainerDTO containerDTO){
		
		List<Object[]> listaDistinteRiaccredito = distinteRiaccreditoBusinessBean.getListDistinteRiaccreditoByFilterParametersFull(containerDTO);
		
		List<DistinteRiaccreditoDTOLight> dtoCollection = DistinteRiaccreditoDTOBuilder.populateListRicercaDistinteRiaccreditoDTOLight(listaDistinteRiaccredito);
		
		ContainerDTO outputDTO = new ContainerDTO();
		
		outputDTO.addAllOutputDTO(dtoCollection);
		
		return outputDTO;		
	}
	
	
	@Override
    public List<CodiceDescrizioneVO> listaBeneficiari(){
        return (List<CodiceDescrizioneVO>) distinteRiaccreditoBusinessBean.listaBeneficiari();
    }
    
    @Override
    public Map<String, Map<String, String>> listaDebiti(){
        return (Map<String, Map<String, String>>) distinteRiaccreditoBusinessBean.listaDebiti();
    }

	@Override
	public ContainerDTO readDistinteRiaccreditoListLightByIdBozzeBonificiRiaccredito(ContainerDTO containerDTO) {
	
		List<DistinteRiaccredito> listaDistinteRiaccredito = distinteRiaccreditoBusinessBean.getDistinteRiaccreditoByIdBozzeBonificiRiaccredito(containerDTO);
		
		List<DistinteRiaccreditoDTOLight> dtos = DistinteRiaccreditoDTOBuilder.populateListDistinteRiaccreditoDTOLight(listaDistinteRiaccredito);
		
		containerDTO.addAllOutputDTO(dtos);
		
		return containerDTO;		
	}
	
	@Override
	public ContainerDTO readDistinteRiaccreditoListLightByIdBonifico(ContainerDTO containerDTO) {
	
		List<DistinteRiaccredito> listaDistinteRiaccredito = distinteRiaccreditoBusinessBean.getDistinteRiaccreditoByIdBonificiRiaccredito(containerDTO);
		
		List<DistinteRiaccreditoDTOLight> dtos = DistinteRiaccreditoDTOBuilder.populateListDistinteRiaccreditoDTOLight(listaDistinteRiaccredito);
		
		containerDTO.addAllOutputDTO(dtos);
		
		return containerDTO;		
	}
	
	@Override
	public DistinteRiaccreditoDTO readDistintaRiaccreditoById(Long id){
		
		DistinteRiaccredito distintaRiaccredito = distinteRiaccreditoBusinessBean.getDistinteRiaccreditoById(id);
		
		DistinteRiaccreditoDTO dto = DistinteRiaccreditoDTOBuilder.populateDistinteRiaccreditoDTO(distintaRiaccredito);
		
		return dto;		
	}
	
	@Override
	public CasellarioDispoDTO readFlussoDistintaRiaccreditoById(Long id){
		
		DistinteRiaccredito distintaRiaccredito = distinteRiaccreditoBusinessBean.getDistinteRiaccreditoById(id);
		
		CasellarioDispoDTO dto = DistinteRiaccreditoDTOBuilder.populateCasellarioDispoDTO(distintaRiaccredito.getCasellarioDispo(), true);
		
		return dto;		
	}
	
	@Override
	public CasellarioInfoDTO readFlussoRendicontazioneById(Long id){
		
		Rendicontazioni rendicontazioni = distinteRiaccreditoBusinessBean.getRendicontazioniById(id);
		
		CasellarioInfoDTO dto = EsitiBonificiRiaccreditoDTOBuilder.populateCasellarioInfoDTO(rendicontazioni.getCasellarioInfo(), true);
		
		return dto;		
	}
	
	
	@Override
	public ContainerDTO getListBonificiRiaccreditoByIdDistinta(ContainerDTO inputDTO) {
		
		Integer idBonificoInteger = (Integer) inputDTO.getInputDTOList().get(0);
		
		List<BonificiRiaccredito> bonificiList = distinteRiaccreditoBusinessBean.getListBonificiRiaccreditoByIdDistinta(inputDTO);
		
		List<BonificiRiaccreditoDTO> dtoList = new ArrayList<BonificiRiaccreditoDTO>();
		
		for (BonificiRiaccredito gf : bonificiList) {
			BonificiRiaccreditoDTO dto = BonificiRiaccreditoDTOBuilder.populateBonificiRiaccreditoDTONoBozze(gf);
			
			if (idBonificoInteger != null && idBonificoInteger.longValue() == dto.getId().longValue()){
				
				List<BozzeBonificiRiaccreditoDTO> listaBozzeDTO = new ArrayList<BozzeBonificiRiaccreditoDTO>();
				
				for (BozzeBonificiRiaccredito bozza : gf.getBozzeBonificiRiaccredito()){
					
					BaseEntity esito = null;
					
					BozzeBonificiRiaccreditoDTO bozzaDTO = new BozzeBonificiRiaccreditoDTO();
					
					if(bozza.getTipoEsito().equals(EnumCodRendicontazione.BB.getChiave())){
						esito = monitoraggioFlussiBusinessBean.getEsitiBbById(bozza.getIdEsitoOrigine().longValue());
					} else if(bozza.getTipoEsito().equals(EnumCodRendicontazione.SL.getChiave())){
						esito = monitoraggioFlussiBusinessBean.getEsitiRctById(bozza.getIdEsitoOrigine().longValue());
					} else if(bozza.getTipoEsito().equals(EnumCodRendicontazione.RH.getChiave())){
						esito = monitoraggioFlussiBusinessBean.getIncassiBonificiRhById(bozza.getIdEsitoOrigine().longValue());
					} else if(bozza.getTipoEsito().equals(EnumCodRendicontazione.IR.getChiave())){
						esito = monitoraggioFlussiBusinessBean.getRidById(bozza.getIdEsitoOrigine().longValue());
					} else if(bozza.getTipoEsito().equals(EnumCodRendicontazione.AL.getChiave())){
						esito = monitoraggioFlussiBusinessBean.getAEAById(bozza.getIdEsitoOrigine().longValue());
						
					}

					bozzaDTO = BonificiRiaccreditoDTOBuilder.populateBozzaBonificiRiaccreditoDTO(bozza, esito);
					listaBozzeDTO.add(bozzaDTO);
				}
				
				dto.setBozzeBonificiRiaccredito(listaBozzeDTO);
			}
			
			
			dtoList.add(dto);
		}
		
//		List<BonificiRiaccreditoDTO> dtoList =  BonificiRiaccreditoDTOBuilder.populateListBonificiRiaccreditoDTO(bonificiList,idBonificoInteger);
		 
		inputDTO.setOutputDTOList(dtoList);
		
		return inputDTO;
	}
	
	
	@Override
	public EsitiBonificiRiaccreditoDTO readEsitiBonificiRiaccreditoByIdBonificiRiaccredito(Long id){
		
		EsitiBonificiRiaccredito esitiBonificiRiaccredito = distinteRiaccreditoBusinessBean.getEsitiBonificiRiaccreditoByIdBonifico(id);
		
		EsitiBonificiRiaccreditoDTO dto = EsitiBonificiRiaccreditoDTOBuilder.populateEsitiBonificiRiaccreditoDTO(esitiBonificiRiaccredito);
		
		return dto;		
	}
	
	
}


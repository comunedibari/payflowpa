package it.tasgroup.iris.facade.ejb.client.flussi;

import it.nch.is.fo.common.CodiceDescrizioneVO;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.CasellarioDispoDTO;
import it.tasgroup.iris.dto.flussi.CasellarioInfoDTO;
import it.tasgroup.iris.dto.flussi.DistinteRiaccreditoDTO;
import it.tasgroup.iris.dto.flussi.DistinteRiaccreditoDTOLight;
import it.tasgroup.iris.dto.flussi.EsitiBonificiRiaccreditoDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DistinteRiaccreditoFacade {

	public ContainerDTO readDistinteRiaccreditoListLight(ContainerDTO dto);
	
	public DistinteRiaccreditoDTO readDistintaRiaccreditoById(Long id);
	
	public CasellarioDispoDTO readFlussoDistintaRiaccreditoById(Long id);	
	
	public CasellarioInfoDTO readFlussoRendicontazioneById(Long id);
	
	public ContainerDTO getListBonificiRiaccreditoByIdDistinta(ContainerDTO dto);

	public EsitiBonificiRiaccreditoDTO readEsitiBonificiRiaccreditoByIdBonificiRiaccredito(Long idBonifico);
	
	public ContainerDTO readDistinteRiaccreditoListLightByIdBozzeBonificiRiaccredito(ContainerDTO containerDTO);

	public ContainerDTO readDistinteRiaccreditoListLightByIdBonifico(ContainerDTO containerDTO);

	public List<CodiceDescrizioneVO> listaBeneficiari();

	public Map<String, Map<String, String>> listaDebiti();

	public ContainerDTO readDistinteRiaccreditoListFull(ContainerDTO containerDTO);

}

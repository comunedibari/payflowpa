package it.tasgroup.iris.business.ejb.client.flussi;

import it.nch.is.fo.common.CodiceDescrizioneVO;
import it.tasgroup.iris.domain.BonificiRiaccredito;
import it.tasgroup.iris.domain.BozzeBonificiRiaccredito;
import it.tasgroup.iris.domain.DistinteRiaccredito;
import it.tasgroup.iris.domain.EsitiBonificiRiaccredito;
import it.tasgroup.iris.domain.Rendicontazioni;
import it.tasgroup.iris.dto.ContainerDTO;

import java.util.List;
import java.util.Map;


public interface DistinteRiaccreditoBusiness {	

	public List<Object[]> getListDistinteRiaccreditoByFilterParameters(ContainerDTO containerDTO);
	
	public DistinteRiaccredito getDistinteRiaccreditoById(Long id);
	
	public List<BonificiRiaccredito> getListBonificiRiaccreditoByIdDistinta(ContainerDTO containerDTO);
	
	public List<BozzeBonificiRiaccredito> getListBozzeBonificiRiaccreditoByIdBonifico(Integer idBonifico);
	
	public EsitiBonificiRiaccredito getEsitiBonificiRiaccreditoByIdBonifico(Long id);
	
	public Rendicontazioni getRendicontazioniById(Long id);
	
	public List<DistinteRiaccredito> getDistinteRiaccreditoByIdBozzeBonificiRiaccredito(ContainerDTO containerDTO);

	public List<DistinteRiaccredito> getDistinteRiaccreditoByIdBonificiRiaccredito(ContainerDTO containerDTO);

    public List<CodiceDescrizioneVO> listaBeneficiari();

    public Map<String, Map<String, String>> listaDebiti();

    public List<Object[]> getListDistinteRiaccreditoByFilterParametersFull(
			ContainerDTO dto);
	
}


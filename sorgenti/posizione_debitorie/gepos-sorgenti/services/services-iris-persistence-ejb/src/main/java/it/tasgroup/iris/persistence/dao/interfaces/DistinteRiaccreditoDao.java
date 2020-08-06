package it.tasgroup.iris.persistence.dao.interfaces;

import it.nch.is.fo.common.CodiceDescrizioneVO;
import it.tasgroup.iris.domain.DistinteRiaccredito;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

@Local
public interface DistinteRiaccreditoDao extends Dao<DistinteRiaccredito>{
	
	public List<Object[]> listDistinteRiaccreditoByFilterParameters(ContainerDTO input);
	
	public DistinteRiaccredito retrieveDistintaRiaccreditoById(Long id);

	public List<DistinteRiaccredito> getDistinteRiaccreditoByIdBozzeBonificiRiaccredito(ContainerDTO containerDTO);

	public List<DistinteRiaccredito> getDistinteRiaccreditoByIdBonificiRiaccredito(ContainerDTO containerDTO);

	public List<CodiceDescrizioneVO> listaBeneficiari();

	public Map<String, Map<String, String>> listaDebiti();

	public List<Object[]> listDistinteRiaccreditoByFilterParametersFull(
			ContainerDTO containerDTO);
}

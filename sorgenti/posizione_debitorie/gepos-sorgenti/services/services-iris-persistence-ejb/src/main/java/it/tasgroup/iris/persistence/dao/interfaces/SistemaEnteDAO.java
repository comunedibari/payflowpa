package it.tasgroup.iris.persistence.dao.interfaces;

import it.nch.is.fo.sistemienti.SistemaEnte;
import it.nch.is.fo.sistemienti.SistemaEnteId;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.anagrafica.SistemaEnteDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface SistemaEnteDAO extends Dao<SistemaEnte>{

	public List<SistemaEnte> listSistemaEnteByFilterParams(ContainerDTO dtoIn);
	
	public SistemaEnte createSistemaEnte(SistemaEnteDTO silDTO);
	
	public void deleteSistemaEnteByKey(SistemaEnteId key);

	public SistemaEnte updateSistemaEnte(SistemaEnteDTO silDTO);

	public List<SistemaEnte> selectActiveAndSemplSistemaEnte();
	
	public SistemaEnte getSistemaEnteByCdEnteAndIdSystem(String cdEnte, String idSystem);
	
}

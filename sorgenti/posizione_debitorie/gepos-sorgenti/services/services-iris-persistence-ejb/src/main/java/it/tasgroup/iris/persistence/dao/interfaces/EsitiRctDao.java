package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.EsitiRct;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface EsitiRctDao extends Dao<EsitiRct>{

	public List<EsitiRct> listEsitiRctByIdRendicontazione(ContainerDTO containerDTO);
	
	public List<EsitiRct> listEsitiRctByIdRendicontazioneAndTipoEsito(ContainerDTO containerDTO);
	
	public EsitiRct getEsitiRctById(Long id);
}

package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import it.tasgroup.iris.domain.Rendicontazioni;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;

@Local
public interface RendicontazioniDao extends Dao<Rendicontazioni>{

	public Rendicontazioni retrieveRendicontazioniById(Long id);
	
	public List<Rendicontazioni> retrieveRendicontazioniRiversamento(ContainerDTO containerDTO);
}

package it.tasgroup.iris.persistence.dao.interfaces;


import java.util.List;

import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.gde.GiornaleEventi;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;

@Local
public interface GiornaleEventiDao extends Dao<GiornaleEventi>{

	public List<GiornaleEventi> listEventiNDPByFilterParams(ContainerDTO dto);
	
}
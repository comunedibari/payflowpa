package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.CasellarioDispo;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

public interface CasellarioDispoDao extends Dao<CasellarioDispo>{
	
	public List<CasellarioDispo> listCasellarioDispoByFilterParameters(ContainerDTO input);
	
	public CasellarioDispo retrieveCasellarioDispoById(Long id);

	
}

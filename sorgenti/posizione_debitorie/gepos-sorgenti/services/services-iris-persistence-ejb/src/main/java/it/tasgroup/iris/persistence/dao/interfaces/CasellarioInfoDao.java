package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.CasellarioInfo;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

public interface CasellarioInfoDao extends Dao<CasellarioInfo>{
	
	public List<CasellarioInfo> listCasellarioInfoByFilterParameters(ContainerDTO input);
	
	public CasellarioInfo retrieveCasellarioInfoById(Long id);

	public List<Object[]> getListMittenti();
	
	public List<Object[]> getListMittenti(String ricevente);

	public List<Object[]> getListRiceventi();
}

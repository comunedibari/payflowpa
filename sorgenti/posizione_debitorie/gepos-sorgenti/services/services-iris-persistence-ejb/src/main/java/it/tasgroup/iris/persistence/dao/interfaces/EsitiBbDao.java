package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.EsitiBb;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface EsitiBbDao extends Dao<EsitiBb>{

	public List<EsitiBb> listEsitiBbByIdRendicontazione(ContainerDTO containerDTO);
	
	public EsitiBb getEsitiBbById(Long id);
}

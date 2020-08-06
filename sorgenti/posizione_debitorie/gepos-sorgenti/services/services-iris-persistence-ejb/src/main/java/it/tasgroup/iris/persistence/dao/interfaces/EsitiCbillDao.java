package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.EsitiCbill;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface EsitiCbillDao extends Dao<EsitiCbill>{

	public List<EsitiCbill> listEsitiCbillByIdRendicontazione(ContainerDTO containerDTO);
	
	public EsitiCbill getEsitiCbillById(Long id);
}

package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;
import java.util.List;

@Local
public interface StoricoDao extends Dao<Pagamenti> {
	
	List<CondizionePagamento> getCondizioniCreditore(ContainerDTO containerDTO);
	
	
}

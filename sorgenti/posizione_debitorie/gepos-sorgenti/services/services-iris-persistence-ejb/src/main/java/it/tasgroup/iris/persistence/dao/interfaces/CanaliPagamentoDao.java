package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.CanaliPagamento;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;
@Local
public interface CanaliPagamentoDao extends Dao<CanaliPagamento>{

	public CanaliPagamento getByTipoCanale(String tipoCanale);
	
}

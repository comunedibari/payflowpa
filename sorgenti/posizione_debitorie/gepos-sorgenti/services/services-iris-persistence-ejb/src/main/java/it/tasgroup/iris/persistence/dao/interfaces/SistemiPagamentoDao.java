package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import it.tasgroup.iris.domain.SistemiPagamento;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;
@Local
public interface SistemiPagamentoDao extends Dao<SistemiPagamento>{
	public List<SistemiPagamento> getSystemsById(String systemId);
	public List<SistemiPagamento> getSilsById(String systemSil);
}

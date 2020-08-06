package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.CfgCanalePagamento;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;
@Local
public interface CfgCanalePagamentoDao extends Dao<CfgCanalePagamento>{

	public CfgCanalePagamento getCfgByBundleKey(String key);
	
}

package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.CfgStrumentoPagamento;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;
@Local
public interface CfgStrumentoPagamentoDao extends Dao<CfgStrumentoPagamento>{
	public List<CfgStrumentoPagamento> getCfgStrumentoPagamentoAll();
	public CfgStrumentoPagamento getCfgStrumentoPagamentoById(Long id);
	
	
}

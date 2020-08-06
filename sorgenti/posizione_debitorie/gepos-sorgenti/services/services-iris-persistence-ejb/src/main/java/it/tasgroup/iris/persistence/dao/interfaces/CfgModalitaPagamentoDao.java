package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.CfgModalitaPagamento;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface CfgModalitaPagamentoDao extends Dao<CfgModalitaPagamento>{
	
	public List<CfgModalitaPagamento> getListaModalitaPagamentoAttive();
	
	public List<CfgModalitaPagamento> getListaAllModalitaPagamento();
	
	public List<CfgModalitaPagamento> getListaCfgModalitaInActiveCfgFornitore(String circuito);
	
}

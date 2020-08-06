package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.CfgNotificaPagamento;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;

@Local
public interface CfgNotificaPagamentoDao extends Dao<CfgNotificaPagamento>{

	public CfgNotificaPagamento updateCfgNotificaPagamento(CfgNotificaPagamento cfg);
	
	public CfgNotificaPagamento createCfgNotificaPagamento(CfgNotificaPagamento cfg);
	
}

package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import it.tasgroup.iris.domain.CfgUtenteModalitaPagamento;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;

@Local
public interface CfgUtenteModalitaPagamentoDao extends Dao<CfgUtenteModalitaPagamento>{

	public CfgUtenteModalitaPagamento getCfgUtenteModalitaPagamentoByKey(String codiceFiscale, String applicationId, String systemId);
	public List<CfgUtenteModalitaPagamento> getLstCfgUtenteModalitaPagamentoByCodiceFiscale(String codiceFiscale);
}

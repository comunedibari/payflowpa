package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.gev.CfgEventi;
import it.tasgroup.services.dao.ejb.Dao;

public interface CfgEventiDao extends Dao<CfgEventi> {

	CfgEventi getCfgEventiDao(String codiceEvento);
	
}

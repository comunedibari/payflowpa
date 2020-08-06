package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.CfgEntiLogo;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;

@Local
public interface CfgEntiLogoDao extends Dao<CfgEntiLogo> {

	public CfgEntiLogo retrieveById(String id);

	public CfgEntiLogo updateCfgEntiLogo(CfgEntiLogo entiLogo);

	public CfgEntiLogo createCfgEntiLogo(CfgEntiLogo entiLogo);
	
}

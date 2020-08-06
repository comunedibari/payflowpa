package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.CfgEntiTema;

import javax.ejb.Local;
import java.util.Date;

@Local
public interface CfgEntiTemaDao {
    CfgEntiTema retrieveById(String id);
    
    Date retrieveLastModified(String cdEnte);

    CfgEntiTema updateCfgEntiTema(CfgEntiTema entiTema);

    CfgEntiTema createCfgEntiTema(CfgEntiTema entiTema);

    CfgEntiTema retrieveLiteById(String cdEnte);
}

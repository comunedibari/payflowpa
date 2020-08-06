package it.tasgroup.iris.persistence.dao;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.gev.CfgEventi;
import it.tasgroup.iris.persistence.dao.interfaces.CfgEventiDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

@Stateless(name="CfgEventiDaoService")
public  class CfgEventiDaoImpl extends DaoImplJpaCmtJta<CfgEventi> implements CfgEventiDao {

	private static final Logger LOGGER = LogManager.getLogger(CfgEventiDaoImpl.class);
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}

    @Override
    public CfgEventi getCfgEventiDao(String codiceEvento) {

    	CfgEventi cfgEvento = null;

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("codiceEvento", codiceEvento);

        try {

        	cfgEvento = (CfgEventi) this.uniqueResultByQuery("CfgEventiByCodiceEvento", parameters);

        } catch (Exception e) {

            LOGGER.error("error on getCfgEventiDao " + parameters, e);
            throw new DAORuntimeException(e);
        }

        return cfgEvento;
    }
	
	
}

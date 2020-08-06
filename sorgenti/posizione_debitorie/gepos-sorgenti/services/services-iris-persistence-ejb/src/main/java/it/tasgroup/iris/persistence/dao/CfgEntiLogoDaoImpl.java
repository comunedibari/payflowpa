package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.CfgEntiLogo;
import it.tasgroup.iris.persistence.dao.interfaces.CfgEntiLogoDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.sql.Timestamp;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "CfgEntiLogoDao")
public class CfgEntiLogoDaoImpl extends DaoImplJpaCmtJta<CfgEntiLogo> implements CfgEntiLogoDao {

	private static final Logger LOGGER = LogManager.getLogger(CfgEntiLogoDaoImpl.class);

	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public CfgEntiLogo retrieveById(String id) {
		CfgEntiLogo retrieved = null;
		try {
			retrieved = loadById(CfgEntiLogo.class, id);
		} catch (Exception e) {
			LOGGER.error("error on  retrieveById, ID = " + id, e);
			throw new DAORuntimeException(e);
		}
		return retrieved;
	}
	
	@Override
	public CfgEntiLogo updateCfgEntiLogo(CfgEntiLogo entiLogo) {
		
		try {  
									
			CfgEntiLogo entiLogoOld = loadById(CfgEntiLogo.class, entiLogo.getIdEnte());
			
			entiLogoOld.setLogoBlob(entiLogo.getLogoBlob());
			
			entiLogoOld.setNomeFileLogo(entiLogo.getNomeFileLogo());
			
			entiLogoOld.setOpAggiornamento(entiLogo.getOpAggiornamento());
			
			entiLogoOld.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
			
			CfgEntiLogo updated = update(entiLogoOld);
			
			return updated;
			
		} catch (Exception e) {
			LOGGER.error("error on  updateCfgEntiLogo, " + entiLogo, e);
			throw new DAORuntimeException(e);
		}
	}
	
	@Override
	public CfgEntiLogo createCfgEntiLogo(CfgEntiLogo entiLogo) {
		try {  
						
			CfgEntiLogo created = create(entiLogo);
			
			return created;
			
		} catch (Exception e) {
			LOGGER.error("error on  createCfgEntiLogo, " + entiLogo, e);
			throw new DAORuntimeException(e);
		}
	}	

}

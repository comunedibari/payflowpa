
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.BozzeBonificiRiaccredito;
import it.tasgroup.iris.persistence.dao.interfaces.BozzeBonificiRiaccreditoDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="BozzeBonificiRiaccreditoDaoService")
public class BozzeBonificiRiaccreditoDaoImpl extends DaoImplJpaCmtJta<BozzeBonificiRiaccredito> implements BozzeBonificiRiaccreditoDao {
	
	private static final Logger LOGGER = LogManager.getLogger(BonificiRiaccreditoDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public List<BozzeBonificiRiaccredito> getListBozzeBonificiRiaccreditoByIdBonifico(Integer idBonifico) {
		
		List<BozzeBonificiRiaccredito> retList = null;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idBonificoRiaccredito", idBonifico);
		
		try {			
			retList = (List<BozzeBonificiRiaccredito>)listByQuery("listBozzeBonificiRiaccreditoByIdBonifico",params,em);
		} catch (Exception e) {
			LOGGER.error("error on listBozzeBonificiRiaccreditoByIdBonifico "+params, e);		
			throw new DAORuntimeException(e);
		}
		return retList;
	}
	
	
	@Override
	public BozzeBonificiRiaccredito retrieveBozzeBonificiRiaccreditoById(Long id) {
		BozzeBonificiRiaccredito retrieved = null;
		try {
			retrieved = loadById(BozzeBonificiRiaccredito.class,id);
		} catch (Exception e) {
			LOGGER.error("error on  retrieveBozzeBonificiRiaccreditoById, ID = " + id, e);
			throw new DAORuntimeException(e);
		}
		return retrieved;
	}

	
	@Override
	public BozzeBonificiRiaccredito getBozzeBonificiRiaccreditoById(Long id) {
		
		BozzeBonificiRiaccredito bozze = null;
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("id", id);
			
			bozze = (BozzeBonificiRiaccredito)uniqueResultByQuery("getBozzeBonificiRiaccreditoById",params,em);

		} catch (Exception e) {
			LOGGER.error("error on getBozzeBonificiRiaccreditoById ", e);
			throw new DAORuntimeException(e);
		}
		return bozze;
	}
}

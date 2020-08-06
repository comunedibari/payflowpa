package it.tasgroup.iris.persistence.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.Canali;
import it.tasgroup.iris.persistence.dao.interfaces.CanaliDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

@Stateless(name = "CanaliDaoService")
public class CanaliDaoImpl extends DaoImplJpaCmtJta<Canali> implements CanaliDao {
	Logger logger = Logger.getLogger(CanaliDaoImpl.class);

	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<Canali> findCanaliByDenominazione(String denominazione) {
		logger.debug("findCanaliByDenominazione - denominazione [" + denominazione + "]");

		String query = "findCanaliByDenominazione";
		Map<String, String> params = new HashMap<String, String>();
		params.put("denominazione", denominazione);

		try {
			return (List<Canali>) listByQuery(query, params);
		} catch (Exception e) {
			logger.error("Error on findCanaliByDenominazione", e);
			throw new DAORuntimeException(e);
		}
	}

}

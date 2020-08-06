package it.tasgroup.iris.persistence.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.UtentiCanali;
import it.tasgroup.iris.persistence.dao.interfaces.UtentiCanaliDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

@Stateless(name = "UtentiCanaliDaoService")
public class UtentiCanaliDaoImpl extends DaoImplJpaCmtJta<UtentiCanali> implements UtentiCanaliDao {
	Logger logger = Logger.getLogger(UtentiCanaliDaoImpl.class);

	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public int deleteUtentiCanaliByUtente(String idUtente) {
		List<UtentiCanali> listaUtentiCanali = findUtentiCanaliByUtente(idUtente);
		for (UtentiCanali utentiCanali : listaUtentiCanali) {
			try {
				delete(utentiCanali);
			} catch (Exception e) {
				logger.error("Error on deleteUtentiCanaliByUtente", e);
				throw new DAORuntimeException(e);
			}
		}
		return listaUtentiCanali.size();
	}

	@Override
	public List<UtentiCanali> findUtentiCanaliByUtente(String idUtente) {

		logger.debug("findUtentiCanaliByUtente - start - idUtente [" + idUtente + "]");

		String query = "findUtentiCanaliByUtente";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idUtente", idUtente);

		try {
			List<UtentiCanali> utentiCanali = (List<UtentiCanali>) listByQuery(query, params);
			return utentiCanali;
		} catch (Exception e) {
			logger.error("Error on findUtentiCanaliByUtente", e);
			throw new DAORuntimeException(e);
		}
	}

	@Override
	public UtentiCanali saveUtentiCanali(UtentiCanali uc) {
		logger.debug("saveUtentiCanali - start [" + uc + "]");
		UtentiCanali c = null;
		try {
			c = save(uc);
		} catch (Exception e) {
			logger.error("Error on saveUtentiCanali", e);
			throw new DAORuntimeException(e);
		}
		return c;
	}

}

package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.Sessione;
import it.tasgroup.iris.persistence.dao.interfaces.SessioneDAO;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author PazziK
 * 
 */
@Stateless(name = "SessioneDAO")
public class SessioneDaoImpl extends DaoImplJpaCmtJta<Sessione> implements SessioneDAO {

	private static final Logger LOGGER = LogManager.getLogger(SessioneDaoImpl.class);

	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public Sessione retrieveSessionById(String sessionId) {
		try {
			Sessione readSession = loadById(Sessione.class, sessionId);
			return readSession;

		} catch (Exception e) {
			LOGGER.error("error on  retrieveSessionById, " + sessionId, e);
			throw new DAORuntimeException(e);
		}
	}

	@Override
	public Sessione insertSession(Sessione session) {
		try {
			em.persist(session);

		} catch (Exception e) {
			LOGGER.error("error on updateSession ", e);
			throw new DAORuntimeException(e);
		}
		return session;
	}

	@Override
	public Sessione updateSession(Sessione session) {
		try {
			session = this.update(session);

		} catch (Exception e) {
			
			LOGGER.error("error on updateSession ", e);
			throw new DAORuntimeException(e);
		}
		return session;
	}

	@Override
	public void deleteSessionById(String sessionId) {
		try {
			Sessione session = loadById(Sessione.class, sessionId);
			if(session != null) {
				this.delete(session);
			}
		} catch (Exception e) {
			LOGGER.error("error on updateSession ", e);
			throw new DAORuntimeException(e);
		}
	}

}

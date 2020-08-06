/**
 * 
 */
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.SessioneGateway;
import it.tasgroup.iris.persistence.dao.interfaces.SessioneGatewayDAO;
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
@Stateless(name="SessioneGatewayDAO")
public class SessioneGatewayDaoImpl extends DaoImplJpaCmtJta<SessioneGateway> implements SessioneGatewayDAO {

	private static final Logger LOGGER = LogManager.getLogger(SessioneGatewayDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public SessioneGateway saveGatewaySession(SessioneGateway sessione) {
		try {
			
			SessioneGateway created = create(sessione);
			
			return created;
			
		} catch (Exception e) {
			LOGGER.error("error on  saveGatewaySession, " + sessione, e);
			throw new DAORuntimeException(e);
		}
		
	}
	
	@Override
	public SessioneGateway retrieveGatewaySession(String token) {
		try {
			
			SessioneGateway readSession = loadById(SessioneGateway.class, token);
			
			return readSession;
			
		} catch (Exception e) {
			
			LOGGER.error("error on  retrieveGatewaySession, " + token, e);
			
			throw new DAORuntimeException(e);
		}
		
	}
	
	@Override
	public SessioneGateway updateGatewaySession(SessioneGateway sessione){
		
		try {
			
			SessioneGateway updatedSession = update(sessione);
			
			return updatedSession;
			
		} catch (Exception e) {
			
			LOGGER.error("error on  updateGatewaySession, " + sessione, e);
			
			throw new DAORuntimeException(e);
		}
	}

}

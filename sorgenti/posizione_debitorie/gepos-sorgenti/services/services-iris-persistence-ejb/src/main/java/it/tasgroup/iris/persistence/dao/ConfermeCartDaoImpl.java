package it.tasgroup.iris.persistence.dao;

import it.tasgroup.idp.domain.messaggi.ConfermeCart;
import it.tasgroup.iris.persistence.dao.interfaces.ConfermeCartDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "ConfermeCartDaoService")
public class ConfermeCartDaoImpl extends DaoImplJpaCmtJta<ConfermeCart> implements ConfermeCartDao {

	private static final Logger LOGGER = LogManager.getLogger(ConfermeCartDaoImpl.class);
	
	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
}
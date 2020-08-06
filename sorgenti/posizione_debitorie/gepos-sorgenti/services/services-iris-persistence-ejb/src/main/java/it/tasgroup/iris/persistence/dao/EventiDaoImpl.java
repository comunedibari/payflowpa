package it.tasgroup.iris.persistence.dao;

import it.tasgroup.iris.gev.Eventi;
import it.tasgroup.iris.persistence.dao.interfaces.EventiDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="EventiDaoService")
public  class EventiDaoImpl extends DaoImplJpaCmtJta<Eventi> implements EventiDao {

	private static final Logger LOGGER = LogManager.getLogger(EventiDaoImpl.class);
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}


}

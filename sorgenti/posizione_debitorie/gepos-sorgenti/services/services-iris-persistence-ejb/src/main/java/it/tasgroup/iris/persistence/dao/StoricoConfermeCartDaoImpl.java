package it.tasgroup.iris.persistence.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.tasgroup.iris.persistence.dao.interfaces.StoricoConfermeCartDao;

@Stateless(name = "StoricoConfermeCartDaoService")
public class StoricoConfermeCartDaoImpl extends ConfermeCartDaoImpl implements StoricoConfermeCartDao {

	@PersistenceContext(unitName = "StoricoPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

}
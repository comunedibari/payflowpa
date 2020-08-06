package it.tasgroup.iris.persistence.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.tasgroup.iris.persistence.dao.interfaces.StoricoNotificheCartDao;


@Stateless(name = "StoricoNotificheCartDaoService")
public class StoricoNotificheCartDaoImpl extends NotificheCartDaoImpl implements StoricoNotificheCartDao {
	
	@Override
	@PersistenceContext(unitName = "StoricoPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

}

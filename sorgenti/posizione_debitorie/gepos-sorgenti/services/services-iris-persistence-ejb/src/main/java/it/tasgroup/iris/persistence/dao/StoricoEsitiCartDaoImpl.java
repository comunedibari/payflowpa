package it.tasgroup.iris.persistence.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.tasgroup.iris.persistence.dao.interfaces.StoricoEsitiCartDao;


@Stateless(name = "StoricoEsitiCartDaoService")
public class StoricoEsitiCartDaoImpl extends EsitiCartDaoImpl implements StoricoEsitiCartDao {
	
	@Override
	@PersistenceContext(unitName = "StoricoPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

}

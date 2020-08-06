package it.tasgroup.iris.persistence.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.tasgroup.iris.persistence.dao.interfaces.StoricoGestioneFlussiDao;


@Stateless(name = "StoricoGestioneFlussiDaoService")
public class StoricoGestioneFlussiDaoImpl extends GestioneFlussiDaoImpl implements StoricoGestioneFlussiDao {
	
	@Override
	@PersistenceContext(unitName = "StoricoPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

}

package it.tasgroup.iris.persistence.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.tasgroup.iris.persistence.dao.interfaces.StoricoPendenzaDao;


@Stateless(name = "StoricoPendenzaDaoService")
public class StoricoPendenzaDaoImpl extends PendenzaDaoImpl implements StoricoPendenzaDao {
	
	@Override
	@PersistenceContext(unitName = "StoricoPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

}

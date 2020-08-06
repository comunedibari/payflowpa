package it.tasgroup.iris.persistence.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.tasgroup.iris.persistence.dao.interfaces.StoricoPendenzeCartDao;


@Stateless(name = "StoricoPendenzeCartDaoService")
public class StoricoPendenzeCartDaoImpl extends PendenzeCartDaoImpl implements StoricoPendenzeCartDao {
	
	@Override
	@PersistenceContext(unitName = "StoricoPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

}

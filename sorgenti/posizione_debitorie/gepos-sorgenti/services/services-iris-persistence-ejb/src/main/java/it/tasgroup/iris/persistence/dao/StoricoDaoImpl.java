package it.tasgroup.iris.persistence.dao;

import it.tasgroup.iris.persistence.dao.interfaces.StoricoDao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless(name = "StoricoDaoService")
public class StoricoDaoImpl extends PagamentiDaoImpl implements StoricoDao {
	
	@Override
	@PersistenceContext(unitName = "StoricoPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

}

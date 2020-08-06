package it.tasgroup.iris.persistence.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.tasgroup.iris.persistence.dao.interfaces.StoricoErroriIdpDao;


@Stateless(name = "StoricoErroriIdpDaoService")
public class StoricoErroriIdpDaoImpl extends ErroriIdpDaoImpl implements StoricoErroriIdpDao {
	
	@Override
	@PersistenceContext(unitName = "StoricoPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

}

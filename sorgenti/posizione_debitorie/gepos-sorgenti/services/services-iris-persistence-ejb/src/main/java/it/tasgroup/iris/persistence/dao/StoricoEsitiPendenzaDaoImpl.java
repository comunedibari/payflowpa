package it.tasgroup.iris.persistence.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.tasgroup.iris.persistence.dao.interfaces.StoricoEsitiPendenzaDao;


@Stateless(name = "StoricoEsitiPendenzaDaoService")
public class StoricoEsitiPendenzaDaoImpl extends EsitiPendenzaDaoImpl implements StoricoEsitiPendenzaDao {
	
	@Override
	@PersistenceContext(unitName = "StoricoPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

}

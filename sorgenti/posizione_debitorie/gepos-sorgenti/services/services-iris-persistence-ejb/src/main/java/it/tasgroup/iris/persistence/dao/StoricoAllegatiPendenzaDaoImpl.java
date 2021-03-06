package it.tasgroup.iris.persistence.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.tasgroup.iris.persistence.dao.interfaces.StoricoAllegatiPendenzaDao;


@Stateless(name = "StoricoAllegatiPendenzaDaoService")
public class StoricoAllegatiPendenzaDaoImpl extends AllegatiPendenzaDaoImpl implements StoricoAllegatiPendenzaDao {
	
	@Override
	@PersistenceContext(unitName = "StoricoPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

}

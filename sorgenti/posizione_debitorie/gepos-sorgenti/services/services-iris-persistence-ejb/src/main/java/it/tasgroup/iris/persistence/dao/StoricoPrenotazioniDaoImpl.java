package it.tasgroup.iris.persistence.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.tasgroup.iris.persistence.dao.interfaces.StoricoPrenotazioniDao;


@Stateless(name = "StoricoPrenotazioniDaoService")
public class StoricoPrenotazioniDaoImpl extends PrenotazioniDaoImpl implements StoricoPrenotazioniDao {
	
	@Override
	@PersistenceContext(unitName = "StoricoPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

}

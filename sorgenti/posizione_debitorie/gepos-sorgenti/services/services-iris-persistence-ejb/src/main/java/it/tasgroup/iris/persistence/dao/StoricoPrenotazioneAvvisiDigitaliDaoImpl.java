package it.tasgroup.iris.persistence.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.tasgroup.iris.persistence.dao.interfaces.StoricoPrenotazioneAvvisiDigitaliDao;


@Stateless(name = "StoricoPrenotazioneAvvisiDigitaliDao")
public class StoricoPrenotazioneAvvisiDigitaliDaoImpl extends PrenotazioneAvvisiDigitaliDaoImpl implements StoricoPrenotazioneAvvisiDigitaliDao {
	
	@Override
	@PersistenceContext(unitName = "StoricoPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

}

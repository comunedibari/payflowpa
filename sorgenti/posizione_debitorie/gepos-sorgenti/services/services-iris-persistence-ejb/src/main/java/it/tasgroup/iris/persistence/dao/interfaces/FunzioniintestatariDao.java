package it.tasgroup.iris.persistence.dao.interfaces;

import it.nch.is.fo.profilo.Funzioniintestatari;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;

@Local
public interface FunzioniintestatariDao extends Dao<Funzioniintestatari> {

	void cleanFunzioniintestatari(String corporate);

	void synchFunzioniintestatari(String corporate, String codApplicazione);

}

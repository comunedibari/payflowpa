package it.tasgroup.iris.persistence.dao.interfaces;

import it.nch.is.fo.profilo.Funzionioperatori;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface FunzionioperatoriDao extends Dao<Funzionioperatori> {

	List<String> listEnabledFunctions(String corporate, String operatore);

	void cleanFunzionioperatori(String corporate, String operatore);

	void synchFunzionioperatori(String corporate, String operatore, String codApplicazione);

}

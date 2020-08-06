package it.tasgroup.iris.persistence.dao.interfaces;

import it.nch.is.fo.profilo.ClassiAbilitazioni;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface ClassiAbilitazioniDao extends Dao<ClassiAbilitazioni> {

	List<String> getListaClassiByApplicazione(String applicazione);

	void abilitaClasseByApplicazioneFunzione(String classe, String applicazione, String funzione, String user);

	void disabilitaClasseByApplicazioneFunzione(String classe, String applicazione, String funzione);

}

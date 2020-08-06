package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import it.nch.is.fo.profilo.Applicazioni;
import it.tasgroup.iris.dto.FunzioniPropDTO;
import it.tasgroup.services.dao.ejb.Dao;

@Local
public interface ApplicazioniDao extends Dao<Applicazioni> {

	Applicazioni estraiDatiMenu(String applicazione);
	
	String getCodApplicazioneByCategoria(String categoria);

	List<Applicazioni> getListaApplicazioni();

	List<FunzioniPropDTO> getFunzioniByApplicazioneClasse(String applicazione, String classe);

}

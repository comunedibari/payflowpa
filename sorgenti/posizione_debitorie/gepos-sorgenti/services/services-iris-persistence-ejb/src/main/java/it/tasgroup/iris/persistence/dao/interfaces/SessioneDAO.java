package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.Sessione;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;

@Local
public interface SessioneDAO extends Dao<Sessione>{
	
	public Sessione retrieveSessionById(String sessionId);

	public Sessione updateSession(Sessione session);

	public Sessione insertSession(Sessione session);
	
	public void deleteSessionById(String sessionId);
	
}

package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.SessioneGateway;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;

@Local
public interface SessioneGatewayDAO extends Dao<SessioneGateway>{
	
	public SessioneGateway saveGatewaySession(SessioneGateway sessione);
	
	public SessioneGateway retrieveGatewaySession(String token);
	
	public SessioneGateway updateGatewaySession(SessioneGateway sessione);
	
}

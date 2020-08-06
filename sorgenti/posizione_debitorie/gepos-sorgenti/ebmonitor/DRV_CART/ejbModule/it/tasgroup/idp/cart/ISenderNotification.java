package it.tasgroup.idp.cart;

import javax.persistence.EntityManager;

import it.tasgroup.idp.domain.messaggi.NotificheCart;

public interface ISenderNotification {
	
	public Object delivery(NotificheCart nc,EntityManager manager) throws Exception;

	public String getFinalState();
	
}

package it.tasgroup.idp.cart;

import it.tasgroup.idp.domain.messaggi.NotificheCart;

import javax.ejb.Local;

@Local
public interface INotificaItemSenderBeanTx {
	
	public int updateStatoNotificheCart(NotificheCart notificheCart, String oldState, String newState);
	
	public Object delivery(NotificheCart notifica, ISenderNotification sender) throws Exception;
	
}

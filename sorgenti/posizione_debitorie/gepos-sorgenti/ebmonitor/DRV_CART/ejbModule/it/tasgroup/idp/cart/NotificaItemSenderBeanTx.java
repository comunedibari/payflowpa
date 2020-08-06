package it.tasgroup.idp.cart;

import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

import java.sql.Timestamp;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class NotificaItemSenderBeanTx implements INotificaItemSenderBeanTx {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJtaXA) 
	private EntityManager manager;	
	
	private final Log logger = LogFactory.getLog(this.getClass());

	@Override
	public int updateStatoNotificheCart(NotificheCart notificheCart, String oldState, String newState) {
		String query = "Update NotificheCart notificheCart " +
			" set notificheCart.stato = :stato  " +
			" , notificheCart.opAggiornamento = :op_aggiornamento  " +
			" , notificheCart.tsAggiornamento = :ts_aggiornamento  " +
			" WHERE notificheCart.id.e2emsgid = :e2emsgid" +
			" AND notificheCart.id.receiverid= :receiverId " +
			" AND notificheCart.id.receiversys = :receiverSys " +
			" AND notificheCart.stato = :vecchioStato";
		logger.info(this.getClass().getSimpleName() + " query update notificheCart = " + query);
		
		Query queryUpdNotificaCart = manager.createQuery (query);
		queryUpdNotificaCart.setParameter("stato", newState);			
		queryUpdNotificaCart.setParameter("e2emsgid", notificheCart.getId().getE2emsgid());
		queryUpdNotificaCart.setParameter("receiverId", notificheCart.getId().getReceiverid());
		queryUpdNotificaCart.setParameter("receiverSys", notificheCart.getId().getReceiversys());
		queryUpdNotificaCart.setParameter("op_aggiornamento", "NOTIFICA SENDER");
		queryUpdNotificaCart.setParameter("ts_aggiornamento", new Timestamp(System.currentTimeMillis()));
		queryUpdNotificaCart.setParameter("vecchioStato", oldState);
		int notificheUpd = queryUpdNotificaCart.executeUpdate();
							
		// se questo processo non gira gli ESITI_CART rimangono in stato DA_SPEDIRE
		// mentre le pendenze cart rimangono in stato RISPOSTA_INVIATA
		logger.info(this.getClass().getSimpleName() + " ho cambiato (notifica="+notificheUpd+") lo stato della notificacart " 
			+ notificheCart.getId().getE2emsgid() + " - " + notificheCart.getId().getReceiverid() + " -  " + notificheCart.getId().getReceiversys() + " , num. esiti = " + notificheUpd );
		
		return notificheUpd;		
	}

	@Override
	public Object delivery(NotificheCart notifica, ISenderNotification sender) throws Exception {
		
		Object result = sender.delivery(notifica,manager);
		
		// Se è previsto uno stato finale non nullo 
		if (sender.getFinalState()!=null) {
			updateStatoNotificheCart(notifica, StatoEnum.IN_SPEDIZIONE, sender.getFinalState());
		}
		
		return result;
	}
	
	
	
	

}

/*******************************************************************************
 * Copyright (c) 2009 TasGroup.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     TasGroup - initial API and implementation
 ******************************************************************************/
package it.tasgroup.idp.cart;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.bean.ObjectMessageContainer;
import it.tasgroup.idp.bean.ResultKOException;
import it.tasgroup.idp.domain.enti.ConfigNotifiche;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.NotificheCartPK;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

/**
 * Message-Driven Bean implementation class for: InformativaPagamentoListenerBean
 *
 */

@MessageDriven(activationConfig =
		{@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		 @ActivationConfigProperty(propertyName = "destination", propertyValue=ServiceLocalMapper.InformativaPagamentoPendenzeOutputInternal)},
		mappedName = ServiceLocalMapper.InformativaPagamentoPendenzeOutputInternal)	

public class InformativaPagamentoListenerBean implements MessageListener {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJtaXA)
	public EntityManager manager;
	
	@Resource 
	private EJBContext ejbCtx;		
	
	@EJB(beanName = "CartSenderBeanTx")
	private ICartSenderBeanTx cartSenderBeanTx;
	
	private final Log logger = LogFactory.getLog(this.getClass());	
	    
	
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void onMessage(Message message) {
    	logger.info("InformativaPagamentoListenerBean, messaggio in arrivo");
		 
		ObjectMessageContainer omc = null;
		try {
			omc = (ObjectMessageContainer) ((ObjectMessage)message).getObject();
			logger.info("[" + this.getClass().getSimpleName() + "] received message = " + omc.toString());
			
			String n = getConsegnaNotifica(omc.getTipoNotifica(),omc.getIdEnte(), omc.getCdTrbEnte());		
			ISenderNotificationQueueDemux sender = SenderNotificationFactory.createSenderNotificationQueueDemux(n);
			Object deliveryResult = cartSenderBeanTx.doDeliveryInTransaction(sender, toNotificheCart(omc));

			cartSenderBeanTx.updateDb(omc, StatoEnum.ELABORATO_OK, deliveryResult);
			logger.info(this.getClass().getSimpleName() + " cambiato stato su notifiche_cart ad ELABORATO_OK");
		} catch (ResultKOException e) {
 			logger.error(this.getClass().getSimpleName() + " ERROR = " + e.getMessage() + " operazione conclusa negativamente", e);
		    try {
			   cartSenderBeanTx.updateDb(omc, StatoEnum.ELABORATO_KO, e.getSerializedResult());
			   logger.info(this.getClass().getSimpleName() + " cambiato stato su notifiche_cart ad ELABORATO_KO");
		    } catch (Exception e1) {
		      logger.error(this.getClass().getSimpleName() + " errore in update su tabella notifiche_cart", e1);
		      // L'eccezione non e rilanciata perche' si vuole evitare che riparta il delivery del messaggio e che il messaggio finisca nella DLQ
		      // Si prova a incrementare il numero di tentativi su NOTIFICHE_CART
		      incrementTentativiAndManageStopReprocessing(omc);
		    }
		} catch (Exception e) {					
			logger.error(this.getClass().getSimpleName() + " ERROR = " + e.getMessage()+ " errore transitorio... rollback", e);
			// L'eccezione non e rilanciata perche' si vuole evitare che riparta il delivery del messaggio e che il messaggio finisca nella DLQ
			// Si prova a incrementare il numero di tentativi su NOTIFICHE_CART
			incrementTentativiAndManageStopReprocessing(omc);	
		} finally{
			logger.info(this.getClass().getSimpleName() + " , elaborazione messaggio terminata. ");
		}
    }

	private void incrementTentativiAndManageStopReprocessing(ObjectMessageContainer omc) {
		try {
			cartSenderBeanTx.incrementTentativiAndManageStopReprocessing(toNotificheCartPK(omc));
		} catch (Exception e) {
			// In caso di errore, si segnala nel log
			logger.error("Errore nel tentativo di aggiornare numero tentativi di NOTIFICHE_CART", e);
		}
	}
    
    private String getConsegnaNotifica (String tipoNotifica, String idEnte, String  cdTrbEnte)  {
		Query queryListCfgNot = manager.createNamedQuery("getNotifByIdEnteCdTrbEnteAndType");				
			queryListCfgNot.setParameter("tipoNotifica", tipoNotifica);
			queryListCfgNot.setParameter("idEnte", idEnte);
			queryListCfgNot.setParameter("cdTrbEnte", cdTrbEnte);
			queryListCfgNot.setMaxResults(1);
			queryListCfgNot.setFirstResult(0);
		try {
			ConfigNotifiche n = (ConfigNotifiche) queryListCfgNot.getSingleResult();
			return n.getConsegnaNotifica();			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	private NotificheCart toNotificheCart (ObjectMessageContainer omc) {
    	NotificheCart nc = new NotificheCart();
    		nc.setNotificaXml(omc.getMsgContent().getBytes());
    		nc.setId(toNotificheCartPK(omc));
    		nc.setSenderid(omc.getSenderId());
    		nc.setSendersys(omc.getSenderSys());
    	return nc;
    }
	
	private NotificheCartPK toNotificheCartPK (ObjectMessageContainer omc) {
		NotificheCartPK pk = new NotificheCartPK();
			pk.setE2emsgid(omc.getE2eMsgId());
			pk.setReceiverid(omc.getReceiverId());
			pk.setReceiversys(omc.getReceiverSys());
		return pk;
	}
}

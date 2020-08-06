/*******************************************************************************
 * Copyright (c) 2016 TasGroup.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     TasGroup - initial API and implementation
 ******************************************************************************/

package it.tasgroup.idp.cart;

import java.nio.charset.Charset;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.cart.core.IdpCartSpeditoreEJB;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.NotificheCartPK;
import it.tasgroup.idp.util.ServiceLocalMapper;

/**
 * Questo MDB insiste sulla coda interna InformativaPagamentoPendenzeOutputSSil
 * ed orchestra le operazioni di gestione della notifica pagamento in presenza di Smart SIL
 * 
 * @author tasgroup
 *
 */

@MessageDriven(activationConfig =
	{@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
	 @ActivationConfigProperty(propertyName = "destination", propertyValue=ServiceLocalMapper.InformativaPagamentoPendenzeOutputInternalSSil)},
mappedName = ServiceLocalMapper.InformativaPagamentoPendenzeOutputInternalSSil)	

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CartInformativaPagamentoInternalListenerBean implements MessageListener {
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpAppJpa)
	private EntityManager em;
	
	@EJB(beanName = "IdpCartSpeditoreEJBImpl")
	private IdpCartSpeditoreEJB idpCartSpeditore;
	
	@EJB(beanName = "CartSenderBeanTx")
	private ICartSenderBeanTx cartSenderBeanTx;
	
	private final Log logger = LogFactory.getLog(this.getClass());	

	private enum ProcessingStatus {LETTURA_MESSAGGIO, ACQUISIZIONE_ESITO_DA_DB, INVOCAZIONE_SPEDITORE, AGGIORNAMENTO_DB_STATO_NOTIFICA};
	
    public void onMessage(Message message) {
    	NotificheCartPK notificheCartPK = null;
    	ProcessingStatus processingStatus = null;
    	try {
    		// Fase 1: lettura messaggio da coda InformativaPagamentoPendenzeOutputInternalSSil
    		processingStatus = ProcessingStatus.LETTURA_MESSAGGIO;
    		logger.info("Lettura messaggio su coda InformativaPagamentoPendenzeOutputInternalSSil");
    		notificheCartPK = (NotificheCartPK) ((ObjectMessage)message).getObject();
			
			// Fase 2: acquisizione esito cart da DB
			processingStatus = ProcessingStatus.ACQUISIZIONE_ESITO_DA_DB;
			logger.info("Acquisizione da DB di notifica cart con pk " + notificheCartPK.toString()) ;
			NotificheCart notificheCart = em.find(NotificheCart.class, notificheCartPK);
			String notificaXml = new String (notificheCart.getNotificaXml(), Charset.forName("UTF-8"));
			
			// Fase 3: invocazione speditore
			processingStatus = ProcessingStatus.INVOCAZIONE_SPEDITORE;
			logger.info("Invocazione speditore");
			idpCartSpeditore.processIP(notificheCartPK.getReceiverid(), notificheCartPK.getReceiversys(), notificheCart.getTrtReceiverId(), notificheCart.getTrtReceiverSys(), notificheCartPK.getE2emsgid(), notificaXml);
			
			// Fase 4: Aggiornamento stato tabella NOTIFICHE_CART
			processingStatus = ProcessingStatus.AGGIORNAMENTO_DB_STATO_NOTIFICA;
			logger.info("Aggiornamento stato tabella NOTIFICHE_CART");
			cartSenderBeanTx.updateDb(notificheCart);
		} catch (Exception e) {
			logger.error("Errore in " + processingStatus.name(),  e);
			// L'eccezione non e rilanciata perche' si vuole evitare che riparta il delivery del messaggio e che il messaggio finisca nella DLQ
			// Si prova a incrementare il numero di tentativi su NOTIFICHE_CART
			try {
				cartSenderBeanTx.incrementTentativiAndManageStopReprocessing(notificheCartPK);
			} catch (Exception e1) {
				// In caso di errore, si segnala nel log
				logger.error("Errore nel tentativo di aggiornare numero tentativi di NOTIFICHE_CART", e);
			}
		}
    }
}

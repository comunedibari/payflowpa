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

import it.tasgroup.idp.bean.ErrorTao;
import it.tasgroup.idp.util.ServiceLocalMapper;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Message-Driven Bean implementation class for: CartListenerBean
 *
 */
@MessageDriven(activationConfig =
		{@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		 @ActivationConfigProperty(propertyName = "destination", propertyValue=ServiceLocalMapper.InformativaPagamentoPendenzeErrore)},
		mappedName = ServiceLocalMapper.InformativaPagamentoPendenzeErrore)		
public class CartErroreNotificheListenerBean implements MessageListener {

	@EJB(beanName = "ErrorIdpManager")
	private ErrorTao ErrorIdpManagerProxy;
	
	
	private final Log logger = LogFactory.getLog(this.getClass());	
	
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void onMessage(Message message) {
    	logger.info(this.getClass().getSimpleName() + " messaggio in arrivo da coda di errore NOTIFICHE PAGAMENTO ");
		
		//leggo il messaggio dal JMS
		String contenutoErrore;
		try {
			contenutoErrore = ((TextMessage)message).getText();
			String stato = message.getStringProperty("stato");
			String actor = message.getStringProperty("actor");
			logger.debug("messaggio Ricevuto da PROXY \n" + contenutoErrore + "\n + stato = " 
					+ stato + " actor = " + actor);									
	    	
			//calling datastorage as EJB 3
			logger.info(this.getClass().getSimpleName() + " calling ErrorIdpManagerProxy");
			ErrorIdpManagerProxy.executeApplicationTransaction(contenutoErrore,stato,actor);  
			
		} catch (JMSException e) {
			logger.error("ERRORE IDP LISTENER ON MESSAGE " + e.getMessage());			
		}
		      
    }

}

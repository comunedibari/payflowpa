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

import it.tasgroup.idp.bean.CommandExecutorLocal;
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
		 @ActivationConfigProperty(propertyName = "destination", propertyValue=ServiceLocalMapper.InformativaPagamentoPendenzeEsito)},
		mappedName = ServiceLocalMapper.InformativaPagamentoPendenzeEsito)	

public class CartEsitoNotificheListenerBean implements MessageListener {

	@EJB(beanName = "EsitoNotificaPagamentoManager")
	private CommandExecutorLocal EsitoNotificaPagamentoManagerProxy;
	
	@EJB(beanName = "ErroriCartManager")
	private CommandExecutorLocal ErroriCartManagerProxy;	
	
	private final Log logger = LogFactory.getLog(this.getClass());	
	
    /**
     * Default constructor. 
     */
    public CartEsitoNotificheListenerBean() {
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void onMessage(Message message) {
    	logger.info("CartRicezioneNotificheListenerBean , messaggio in arrivo ");
		
		//leggo il messaggio dal JMS
		String contenutoXml = null;
		try {
			contenutoXml = ((TextMessage)message).getText();
			logger.debug(this.getClass().getSimpleName() + "messaggio di esito notifica Ricevuto da CART \n" + contenutoXml + "\n");
	    	
			//calling datastorage as EJB 3
			logger.info(this.getClass().getSimpleName() + " calling EsitoNotificaPagamentoManagerProxy");
			EsitoNotificaPagamentoManagerProxy.executeApplicationTransaction(contenutoXml);  
			
		} catch (JMSException e) {
			logger.error(this.getClass().getSimpleName() + "ERRORE JMS ON MESSAGE " + e.getMessage());			
		} catch (RuntimeException e) {
			logger.error(this.getClass().getSimpleName() + "Error FATAL, Adesso salvo il messaggio su ERRORI_CART " + e.getMessage() );
			//salvo il messaggio nella tabella ERRORI_CART
			ErroriCartManagerProxy.executeApplicationTransaction(contenutoXml);		
		} finally{
			logger.info("CartRicezioneNotificheListenerBean , elaborazione messaggio terminata. ");
		}
		
		      
    }

}

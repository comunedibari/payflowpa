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

import it.tasgroup.dse.view.DataInput;
import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.bean.IDataStorageManager;
import it.tasgroup.idp.util.ServiceLocalMapper;

/**
 * Message-Driven Bean implementation class for: CartListenerBean
 *
 */
@MessageDriven(activationConfig =
		{@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		 @ActivationConfigProperty(propertyName = "destination", propertyValue=ServiceLocalMapper.PosizioneDebitoriaInput)},
		mappedName = ServiceLocalMapper.PosizioneDebitoriaInput)	

public class CartListenerBean implements MessageListener {

	@EJB(beanName = "DataStorageManager")
	private IDataStorageManager DataStorageManagerProxy;

	@EJB(beanName = "ErroriCartManager")
	private CommandExecutorLocal ErroriCartManagerProxy;	
	
//	@EJB(beanName="timerBean", mappedName="ejb/timedBean")
//	private ITimedBean timerBean;
	
	private final Log logger = LogFactory.getLog(this.getClass());	
	
    /**
     * Default constructor. 
     */
    public CartListenerBean() {
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void onMessage(Message message) {
    	logger.info("CartListenerBean , messaggio in arrivo ");
		
		//leggo il messaggio dal JMS
		String contenutoXml = null;
		try {
			contenutoXml = ((TextMessage)message).getText();
//			logger.debug("CartListenerBean , messaggio Ricevuto da CART \n" + contenutoXml + "\n");
	    	
			if ("test".equals(contenutoXml)) {
				//e' un messaggio di controllo... non faccio niente
				//lo scodo e basta
				logger.info("CartListenerBean , Ricevuto messaggio di test!");
			} else {
				//e' un messaggio vero... allora procedo!
				
				//calling datastorage as EJB 3
				logger.info("CartListenerBean , calling DataStorageManagerProxy");
				
				DataInput input = DataStorageManagerProxy.executeApplicationTransactionTX1(contenutoXml);
				DataStorageManagerProxy.executeApplicationTransactionTX2(input);

			}
			
		} catch (JMSException e) {
			logger.error("ERRORE JMS CARTLISTENER ON MESSAGE " + e.getMessage());			
		} catch (RuntimeException e) {
			logger.error("Error FATAL, Adesso salvo il messaggio su ERRORI_CART " + e.getMessage() );
			//salvo il messaggio nella tabella ERRORI_CART
			ErroriCartManagerProxy.executeApplicationTransaction(contenutoXml);		
		} finally{
			logger.info("CartListenerBean , elaborazione messaggio terminata. ");
		}
		
		      
    }


}

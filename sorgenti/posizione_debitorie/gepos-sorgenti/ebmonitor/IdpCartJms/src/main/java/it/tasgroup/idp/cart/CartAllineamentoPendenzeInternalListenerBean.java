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

import java.nio.charset.Charset;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.dse.view.DataInput;
import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.bean.CommandExecutorSemplificationLocal;
import it.tasgroup.idp.bean.MessageInnerLight;
import it.tasgroup.idp.bean.ObjectCommandExecutorLocal;
import it.tasgroup.idp.esiti.EsitiModel;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;

/**
 * Message-Driven Bean implementation class for: CartListenerBean
 *
 */
@MessageDriven(activationConfig =
		{@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		 @ActivationConfigProperty(propertyName = "destination", propertyValue=ServiceLocalMapper.JmsAllineamentoPendenzeInternalInput)},
		mappedName = ServiceLocalMapper.JmsAllineamentoPendenzeInternalInput)	

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CartAllineamentoPendenzeInternalListenerBean implements MessageListener {
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpAppJpa)	
	private EntityManager em;

	@EJB(beanName = "DataStorageManagerSemplificationImpl")
	private CommandExecutorSemplificationLocal dataStorageManager;

	@EJB(beanName = "ErroriCartManager")
	private CommandExecutorLocal ErroriCartManagerProxy;	
	
	@EJB(beanName = "SpedizioneEsiti")
	private ObjectCommandExecutorLocal SpedizioneEsitiProxy; 

	@EJB(beanName = "CartSenderBean")
	private ICartSenderBean CartSenderProxy;
	
	private final Log logger = LogFactory.getLog(this.getClass());	
	
    /**
     * Default constructor. 
     */
    public CartAllineamentoPendenzeInternalListenerBean() {
    }
	
    
    public void onMessage(Message message) {
    	logger.info("CartSmartSilInternalListenerBean , messaggio in arrivo ");
    	String xml = null;
    	MessageInnerLight mil = null;
		try {
			mil = (MessageInnerLight)((ObjectMessage) message).getObject();
			logger.info(this.getClass().getName() + " : Message is MessageInnerLight");
			byte[] xmlAsByteArray = 
				(byte[]) 
				em.createNamedQuery("findBlobByPk")
					.setParameter("e2emsgid", mil.getPrevData().getE2EMsgId())
					.setParameter("ente", mil.getPrevData().getIdMittente())
					.setParameter("sil", mil.getPrevData().getSilMittente())
						.getSingleResult();
			xml = new String(xmlAsByteArray, Charset.forName("UTF-8"));
			logger.info(this.getClass().getName() + " : Got blob from database");
			mil.setXml(xml);
			
			DataInput input = dataStorageManager.executeApplicationTransaction(mil);
			dataStorageManager.executeApplicationTransactionTX2(input);
			
			// creazione esiti
			EsitiModel esitoModel = new EsitiModel();
			esitoModel.setE2emsgid(mil.getPrevData().getE2EMsgId());
			esitoModel.setSenderId(mil.getPrevData().getIdMittente());
			esitoModel.setSenderSys(mil.getPrevData().getSilMittente());
			esitoModel.setOTF(false);
			MonitoringData esitoContainer = (MonitoringData)SpedizioneEsitiProxy.executeApplicationTransaction(esitoModel);
			String esitoXml = esitoContainer.getEsito(); // TODO gestione dell'esito ?
			// spedizione esiti
			CartSenderProxy.executeApplicationTransaction(esitoModel);
			
			logger.info(this.getClass().getName() + " : executed dataStorageManager");
		} catch (JMSException e) {
			logger.error(this.getClass().getName() + " : ERRORE JMS CARTLISTENER ON MESSAGE " + e.getMessage());
		} catch (ClassCastException cce) {
			// se arriva qui vuol dire che qualcuno, non da applicazione, sta puttando sulla coda un oggetto non consono
			logger.error(this.getClass().getName() +  ": ERRORE CARTLISTENER NON MESSAGEINNERLIGHT");
		} catch (Exception e) {
			// se arriva qui vuol dire che il database e' in lock su quel record, quindi per avere un riferimento alla pendenze_cart,
			// al posto dell'xml ci metto l'id della pendenze_cart
			// mil a questo punto non puo' essere null
			logger.error(this.getClass().getName() + " : Error FATAL, Adesso salvo il messaggio su ERRORI_CART " + e.getMessage() );
			if (xml == null) {
				xml = "e2emsgid:[" + mil.getPrevData().getE2EMsgId() + "];ente:[" + mil.getPrevData().getIdMittente() + "];sil:[" + mil.getPrevData().getSilMittente() + "];";
			}
			ErroriCartManagerProxy.executeApplicationTransaction(xml);
		} finally{
			logger.info(this.getClass().getName() + " : elaborazione messaggio terminata. ");
		}
		
		      
    }


}

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
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.EsitiCartPK;
import it.tasgroup.idp.util.ServiceLocalMapper;

/**
 * Questo MDB insiste sulla coda interna PosizioneDebitoriaOutputInternalSSil
 * ed orchestra le operazioni di gestione dell'esito cart in presenza di Smart SIL
 * 
 * @author tasgroup
 *
 */

@MessageDriven(activationConfig =
	{@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
	 @ActivationConfigProperty(propertyName = "destination", propertyValue=ServiceLocalMapper.PosizioneDebitoriaOutputInternalSSil)},
mappedName = ServiceLocalMapper.PosizioneDebitoriaOutputInternalSSil)	

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CartEsitoInternalListenerBean implements MessageListener {
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpAppJpa)
	private EntityManager em;
	
	@EJB(beanName = "IdpCartSpeditoreEJBImpl")
	private IdpCartSpeditoreEJB idpCartSpeditore;
	
	@EJB(beanName = "CartSenderBeanTx")
	private ICartSenderBeanTx cartSenderBeanTx;
	
	private final Log logger = LogFactory.getLog(this.getClass());	
	
	private enum ProcessingStatus {LETTURA_MESSAGGIO, ACQUISIZIONE_ESITO_DA_DB, INVOCAZIONE_SPEDITORE, AGGIORNAMENTO_DB_STATO_PENDENZA_E_ESITO};
	
    public void onMessage(Message message) {
    	EsitiCartPK esitiCartPk = null;
    	ProcessingStatus processingStatus = null;
    	try {
    		// Fase 1: lettura messaggio da coda PosizioneDebitoriaOutputInternalSSil
    		processingStatus = ProcessingStatus.LETTURA_MESSAGGIO;
    		logger.info("Lettura messaggio su coda PosizioneDebitoriaOutputInternalSSil");
			esitiCartPk = (EsitiCartPK) ((ObjectMessage)message).getObject();
			
			// Fase 2: acquisizione esito cart da DB
			processingStatus = ProcessingStatus.ACQUISIZIONE_ESITO_DA_DB;
			logger.info("Acquisizione da DB di esito cart con pk " + esitiCartPk.toString()) ;
			EsitiCart esitiCart = em.find(EsitiCart.class, esitiCartPk);
			String esitoXml = new String (esitiCart.getEsitoXml(), Charset.forName("UTF-8"));
			
			// Fase 3: invocazione speditore
			processingStatus = ProcessingStatus.INVOCAZIONE_SPEDITORE;
			logger.info("Invocazione speditore");
			idpCartSpeditore.processAPE(esitiCartPk.getSenderid(), esitiCartPk.getSendersys(), esitiCart.getTrtSenderId(), esitiCart.getTrtSenderSys(), esitiCartPk.getE2emsgid(), esitoXml);
			
			// Fase 4: Aggiornamento stato tabelle ESITI_CARI, PENDENZE_CART, ESITI_PENDENZA
			processingStatus = ProcessingStatus.AGGIORNAMENTO_DB_STATO_PENDENZA_E_ESITO;
			logger.info("Aggiornamento stato tabelle ESITI_CART, PENDENZE_CART, ESITI_PENDENZA");
			cartSenderBeanTx.updateDb(esitiCart);
		} catch (Exception e) {
			logger.error("Errore in " + processingStatus.name(),  e);
			// L'eccezione non e rilanciata perche' si vuole evitare che riparta il delivery del messaggio e che il messaggio finisca nella DLQ
			// Si prova a incrementare il numero di tentativi su ESITI_CART
			try {
				cartSenderBeanTx.incrementTentativiAndManageStopReprocessing(esitiCartPk);
			} catch (Exception e1) {
				// In caso di errore, si segnala nel log
				logger.error("Errore nel tentativo di aggiornare numero tentativi di ESITI_CART", e);
			}
		}
    }
}

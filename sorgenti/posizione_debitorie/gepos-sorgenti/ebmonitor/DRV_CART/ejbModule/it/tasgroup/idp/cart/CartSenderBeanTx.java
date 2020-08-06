package it.tasgroup.idp.cart;

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

import java.io.Serializable;
import java.nio.charset.Charset;
import java.sql.Timestamp;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.bean.ObjectMessageContainer;
import it.tasgroup.idp.domain.messaggi.ConfermeCart;
import it.tasgroup.idp.domain.messaggi.ConfermeCartPK;
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.EsitiCartPK;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.NotificheCartPK;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartPK;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.JmsUtils;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

/**
 * Questo componente complementa il bean {@link CartSenderBean} esponendo metodi che lavorano 
 * in transazione di DB
 * 
 * @author tasgroup
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CartSenderBeanTx implements ICartSenderBeanTx {
	
	private static final String MAX_TENTATIVI_PROCESSING_PROP_NAME = "maxnum.tentativi";
	private static final int DEFAULT_MAX_TENTATIVI_PROCESSING = 5;
	private static final int nMaxTentativiProcessing;
	static {
		int value = DEFAULT_MAX_TENTATIVI_PROCESSING;
		try {
			value = Integer.parseInt(IrisProperties.getProperty(MAX_TENTATIVI_PROCESSING_PROP_NAME, String.valueOf(DEFAULT_MAX_TENTATIVI_PROCESSING)));
		} catch (NumberFormatException e) {}
		nMaxTentativiProcessing = value;
	}
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager em;
	
	private final Log logger = LogFactory.getLog(this.getClass());	
	
	
	/* Utilita' - parte pubblica */
	
	/*
	 * Nella stessa transazione serializza un oggetto in una coda JMS
	 */
	@Override
	public void sendJmsObjectMessage(String cfString, String jmsDestinationString, Serializable object) {
		sendJmsObjectMessage(cfString, null, jmsDestinationString, object);
	}
	
	/*
	 * Nella stessa transazione serializza un oggetto in una coda JMS utilizzando,se configurato, un InitialContext con visibilita' sul cluster 
	 */
	@Override
	public void sendJmsObjectMessage(String cfString, String cfStringClusterAware, String jmsDestinationString, Serializable object) {
		logger.info("Spedisco object message " + object.toString() + " verso " + jmsDestinationString);
		try {
			if (cfStringClusterAware == null) {
				JmsUtils.sendJmsMessage(cfString, jmsDestinationString, object);
			} else {
				JmsUtils.sendJmsMessage(cfString, cfStringClusterAware, jmsDestinationString, object);
			}
					
			logger.info("Object message " + object.toString() + " spedito su " + jmsDestinationString);
		} catch (Exception e) {
			logger.error(e);
			if (e instanceof RuntimeException) throw (RuntimeException) e;
			throw new RuntimeException(e);
		}
	}
	
	
	/* Gestione esiti - parte pubblica */
	
	/**
	 * Nella stessa transazione:
	 *     - mette sulla coda PosizioneDebitoriaOutput l'xml di esito. (Qui andra' a leggere Proxy Cart)
	 *     - aggiorna il DB (ESITI_CART, PENDENZE_CART, ESITI_PENDENZA)
	 *     
	 * Note:
	 *     - se la successiva elaborazione del messaggio di esito da parte di Proxy Cart dovesse dare errore
	 *       comunque il DB viene aggiornato come se tutto fosse andato bene
	 */
	@Override
	public void sendJmsAndUpdateEsito(EsitiCart esitiCart) {
		logger.info("Spedisco esito " + esitiCart.getPendenzeCart().getPk().getE2emsgid());
		String esitoXml = new String(esitiCart.getEsitoXml(),Charset.forName("UTF8"));
		
		try {			
			JmsUtils.sendJmsMessage("JmsXA","JmsPosizioneDebitoriaOutput", esitoXml);
	        
			logger.info("Esito spedito verso Cart, idBustaEgov = " + esitiCart.getPk().getE2emsgid() + " - " + esitiCart.getPk().getSenderid() + " - " + esitiCart.getPk().getSendersys());
			if (logger.isDebugEnabled()) logger.debug("Messaggio di esito verso Cart = \n" + esitoXml);
			
			updateDb (esitiCart);
		
		} catch (Exception e) {
			logger.error(e);
			if (e instanceof RuntimeException) throw (RuntimeException) e;
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * Nella stessa transazione aggiorna queste tabelle di DB:
	 *     - ESITI_CART      -> stato INVIATO
	 *     - PENDENZE_CART   -> stato RISPOSTA INVIATA
	 *     - ESITI_PENDENZA  -> stato INVIATO
	 */
	@Override
	public void updateDb (EsitiCart esitiCart) {
		try {
			updateDbInternal(esitiCart);
		} catch (Exception e) {
			logger.error(e);
			if (e instanceof RuntimeException) throw (RuntimeException) e;
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * Nella stessa transazione incrementa il numero di tentativi di processing e gestisce l'aggiornamento
	 * dello stato dell'esito (IN_ERRORE) quando il numero massimo di tentativi di processing e' raggiunto 
	 */
	@Override
	public void incrementTentativiAndManageStopReprocessing(EsitiCartPK esitiCartPk) {
		try {
			updateTentativiEsitiCart(esitiCartPk);
			if (updateStatoMaxTentativiRaggiuntoEsitiCart(esitiCartPk, nMaxTentativiProcessing, StatoEnum.NON_INVIATO) > 0) {
				// E' stato aggiornato almeno un record di ESITI_CART, impostando lo stato a NON_INVIATO
				logger.info("Numero massimo di tentativi di processing raggiunto per esito Cart con pk " + esitiCartPk.toString());
				// Aggiorniamo di conseguenza PENDENZE_CART e ESITI_PENDENZA
				updateStatoPendenza(esitiCartPk.getE2emsgid(), esitiCartPk.getSenderid(), esitiCartPk.getSendersys(), StatoEnum.RISPOSTA_NON_INVIATA);
				updateStatoEsitiPendenza(esitiCartPk.getE2emsgid(), esitiCartPk.getSenderid(), esitiCartPk.getSendersys(), StatoEnum.NON_INVIATO);
			}
		} catch (Exception e) {
			logger.error(e);
			if (e instanceof RuntimeException) throw (RuntimeException) e;
			throw new RuntimeException(e);
		}
	}
	
	
	/* Gestione esiti - parte privata */
		
	private void updateDbInternal (EsitiCart esitiCart) {
		EsitiCartPK esitiCartPk = esitiCart.getPk();

		// Aggiornamento stato ESITI_CART: INVIATO
		int esitiUpd = updateStatoEsitiCart(esitiCartPk, StatoEnum.INVIATO);
		logger.info("Cambiato (esito="+esitiUpd+") lo stato dell'esito cart " + esitiCartPk.getE2emsgid() + " - " + esitiCartPk.getSenderid() + " -  " + esitiCartPk.getSendersys() + " , num. esiti = " + esitiUpd );
		
		// Aggiornamento stato PENDENZE_CART: RISPOSTA INVIATA
		logger.info("Query update pendenze_cart (E2eMsgId = " + esitiCartPk.getE2emsgid() +  ") = " + StatoEnum.RISPOSTA_INVIATA);
		updateStatoPendenza(esitiCartPk.getE2emsgid(), esitiCartPk.getSenderid(), esitiCartPk.getSendersys(), StatoEnum.RISPOSTA_INVIATA);
		
		// Aggiornamento stato ESITI_PENDENZA: INVIATO
		logger.info("Query update esiti_pendenza " + StatoEnum.INVIATO);				
		updateStatoEsitiPendenza(esitiCartPk.getE2emsgid(), esitiCartPk.getSenderid(), esitiCartPk.getSendersys(), StatoEnum.INVIATO);
	}

	@Override
	public int updateStatoEsitiCart(EsitiCartPK esitiCartPk, String stato) {
		String query = 
				"UPDATE EsitiCart esitiCart " +
		        " SET esitiCart.stato = :stato  " +
		        "   , esitiCart.opAggiornamento = :op_aggiornamento" +
		        "   , esitiCart.tsAggiornamento = :ts_aggiornamento  " +
		        " WHERE esitiCart.pk.e2emsgid = :e2emsgid" +
		        " AND   esitiCart.pk.senderid= :senderId " +
		        " AND   esitiCart.pk.sendersys = :senderSys ";
		logger.info(this.getClass().getSimpleName() + " query update EsitiCart = " + query);
		
		Query queryUpdateEsitiCart = em.createQuery (query);
			queryUpdateEsitiCart.setParameter("stato", stato);			
			queryUpdateEsitiCart.setParameter("e2emsgid", esitiCartPk.getE2emsgid());
			queryUpdateEsitiCart.setParameter("senderId", esitiCartPk.getSenderid());
			queryUpdateEsitiCart.setParameter("senderSys", esitiCartPk.getSendersys());
			queryUpdateEsitiCart.setParameter("op_aggiornamento", "CART SENDER");
			queryUpdateEsitiCart.setParameter("ts_aggiornamento", new Timestamp(System.currentTimeMillis()));
		int esitiUpd = queryUpdateEsitiCart.executeUpdate();
		return esitiUpd;
	}

	private int updateStatoPendenza(String E2EMsgId, String idMittente, String silMittente, String stato) {
		String query = 
				"UPDATE PendenzeCart pend " +
			    " SET pend.stato = :stato  " +
			    " WHERE pend.pk.e2emsgid = :e2emsgid" +
			    " AND   pend.pk.senderid= :senderId " +
			    " AND   pend.pk.sendersys = :senderSys ";
		logger.info(this.getClass().getSimpleName() + " query update PendenzeCart = " + query);
		
		Query queryUpdatePend = em.createQuery (query);
			queryUpdatePend.setParameter("stato", stato);			
			queryUpdatePend.setParameter("e2emsgid", E2EMsgId);
			queryUpdatePend.setParameter("senderId", idMittente);
			queryUpdatePend.setParameter("senderSys", silMittente);
		
		int esitiPend = queryUpdatePend.executeUpdate();
		return esitiPend;
	}

	private int updateStatoEsitiPendenza(String E2EMsgId, String idMittente, String silMittente, String stato) {
		String query = 
				"UPDATE EsitiPendenza esiti " +
				" SET esiti.stato = :stato  " +
				" WHERE esiti.pendenzeCart = :pendenzeCart";
		logger.info(this.getClass().getSimpleName() + " query update EsitiPendenza = " + query);

		PendenzeCart pendC = new PendenzeCart();
			PendenzeCartPK pendPK = new PendenzeCartPK();
			pendPK.setE2emsgid(E2EMsgId);
			pendPK.setSenderid(idMittente);
			pendPK.setSendersys(silMittente);
		pendC.setPk(pendPK);		
		
		Query queryUpdateEsiti = em.createQuery (query);
			queryUpdateEsiti.setParameter("stato", stato);
			queryUpdateEsiti.setParameter("pendenzeCart", pendC);
		int esitiUpd = queryUpdateEsiti.executeUpdate();
		logger.info(this.getClass().getSimpleName() + " imposto lo stato " + StatoEnum.INVIATO + " di tutti gli esiti associati alla pendenza , num. esiti = " + esitiUpd );
		
		return esitiUpd;
	}
	
	private int updateTentativiEsitiCart(EsitiCartPK esitiCartPk) {
		String query = 
				"UPDATE EsitiCart esitiCart " +
		        " SET esitiCart.tentativi = esitiCart.tentativi + 1 " +
		        "   , esitiCart.opAggiornamento = :op_aggiornamento" +
		        "   , esitiCart.tsAggiornamento = :ts_aggiornamento  " +
		        ", esitiCart.stato= :stato"+
		        " WHERE esitiCart.pk.e2emsgid = :e2emsgid" +
		        " AND   esitiCart.pk.senderid= :senderId " +
		        " AND   esitiCart.pk.sendersys = :senderSys ";
		logger.info(this.getClass().getSimpleName() + " query update EsitiCart = " + query);
		
		Query queryUpdateEsitiCart = em.createQuery (query);
			queryUpdateEsitiCart.setParameter("e2emsgid", esitiCartPk.getE2emsgid());
			queryUpdateEsitiCart.setParameter("senderId", esitiCartPk.getSenderid());
			queryUpdateEsitiCart.setParameter("senderSys", esitiCartPk.getSendersys());
			queryUpdateEsitiCart.setParameter("op_aggiornamento", "CART SENDER");
			queryUpdateEsitiCart.setParameter("ts_aggiornamento", new Timestamp(System.currentTimeMillis()));
			queryUpdateEsitiCart.setParameter("stato", StatoEnum.DA_SPEDIRE);
		int esitiUpd = queryUpdateEsitiCart.executeUpdate();
		return esitiUpd;
	}
	
	private int updateStatoMaxTentativiRaggiuntoEsitiCart(EsitiCartPK esitiCartPk, int maxTentativi, String stato) {
		String query = 
				"UPDATE EsitiCart esitiCart " +
		        " SET stato = :stato " +
		        " WHERE esitiCart.pk.e2emsgid = :e2emsgid" +
		        " AND   esitiCart.pk.senderid= :senderId " +
		        " AND   esitiCart.pk.sendersys = :senderSys " +
				" AND   esitiCart.tentativi >= :maxTentativi ";
		logger.info(this.getClass().getSimpleName() + " query update EsitiCart = " + query);
		
		Query queryUpdateEsitiCart = em.createQuery (query);
			queryUpdateEsitiCart.setParameter("stato", stato);
			queryUpdateEsitiCart.setParameter("e2emsgid", esitiCartPk.getE2emsgid());
			queryUpdateEsitiCart.setParameter("senderId", esitiCartPk.getSenderid());
			queryUpdateEsitiCart.setParameter("senderSys", esitiCartPk.getSendersys());
			queryUpdateEsitiCart.setParameter("maxTentativi", maxTentativi);
		int esitiUpd = queryUpdateEsitiCart.executeUpdate();
		return esitiUpd;
	}


	/* Gestione notifiche - parte pubblica */
	
	/*
	 * Esegue l'operazione di delivery in transazione
	 */
	public Object doDeliveryInTransaction(ISenderNotificationQueueDemux sender, NotificheCart notificheCart) throws Exception {
		return sender.delivery(notificheCart, em);
	}
	
	@Override
	public void updateDb (NotificheCart notificheCart) {
		try {
			updateStatoNotificheCart(notificheCart, StatoEnum.INVIATO, StatoEnum.IN_SPEDIZIONE);
		} catch (Exception e) {
			logger.error(e);
			if (e instanceof RuntimeException) throw (RuntimeException) e;
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void updateDb (ObjectMessageContainer objectMessageContainer, String stato, Object deliveryResult) {
		try {
			updateStatoNotificheCart(objectMessageContainer, stato, StatoEnum.IN_SPEDIZIONE);
			insertConfermeCart(objectMessageContainer, stato, deliveryResult);
		} catch (Exception e) {
			logger.error(e);
			if (e instanceof RuntimeException) throw (RuntimeException) e;
			throw new RuntimeException(e);
		}
	}

	@Override
	public void incrementTentativiAndManageStopReprocessing(NotificheCartPK notificheCartPk) {
		try {
			updateTentativiNotificheCart(notificheCartPk);
			if (updateStatoMaxTentativiRaggiuntoNotificheCart(notificheCartPk, nMaxTentativiProcessing, StatoEnum.NON_INVIATO) > 0) {
				// E' stato aggiornato almeno un record di NOTIFICHE_CART, impostando lo stato a NON_INVIATO
				logger.info("Numero massimo di tentativi di processing raggiunto per notifica Cart con pk " + notificheCartPk.toString());
			}
		} catch (Exception e) {
			logger.error(e);
			if (e instanceof RuntimeException) throw (RuntimeException) e;
			throw new RuntimeException(e);
		}
	}
	
	
	/* Gestione notifiche - parte privata */
	
	private int updateStatoNotificheCart(NotificheCart notificheCart, String stato, String statoPrec) {
		return updateStatoNotificheCart(
			  notificheCart.getId().getE2emsgid()
			, notificheCart.getId().getReceiverid()
			, notificheCart.getId().getReceiversys() 
			, stato
			, "NOTIFICA SENDER"
			, new Timestamp(System.currentTimeMillis())
			, statoPrec
		);
	}
	
	private int updateStatoNotificheCart(ObjectMessageContainer objectMessageContainer, String stato, String statoPrec) {
		return updateStatoNotificheCart(
			  objectMessageContainer.getE2eMsgId()
			, objectMessageContainer.getReceiverId()
			, objectMessageContainer.getReceiverSys() 
			, stato
			, "NOTIFICA SENDER"
			, new Timestamp(System.currentTimeMillis())
			, statoPrec
		);
	}
	
	private int updateStatoNotificheCart(String e2eMsgId, String receiverId, String receiverSys, String stato, String opAggiornamento, Timestamp tsAggiornamento, String statoPrec) {
		String query = 
				"UPDATE NotificheCart notificheCart " +
                " SET notificheCart.stato = :stato " +
                "   , notificheCart.opAggiornamento = :op_aggiornamento " +
                "   , notificheCart.tsAggiornamento = :ts_aggiornamento " +
                " WHERE notificheCart.id.e2emsgid = :e2emsgid " +
                " AND   notificheCart.id.receiverid= :receiverId " +
                " AND   notificheCart.id.receiversys = :receiverSys " +
                " AND   notificheCart.stato = :vecchioStato ";
		logger.info(this.getClass().getSimpleName() + " query update NotificheCart = " + query);
		
		Query queryUpdateNotificaCart = em.createQuery (query);
			queryUpdateNotificaCart.setParameter("stato", stato);			
			queryUpdateNotificaCart.setParameter("e2emsgid", e2eMsgId);
			queryUpdateNotificaCart.setParameter("receiverId", receiverId);
			queryUpdateNotificaCart.setParameter("receiverSys", receiverSys);
			queryUpdateNotificaCart.setParameter("op_aggiornamento", opAggiornamento);
			queryUpdateNotificaCart.setParameter("ts_aggiornamento", tsAggiornamento);
			queryUpdateNotificaCart.setParameter("vecchioStato", statoPrec);
		int notificheUpd = queryUpdateNotificaCart.executeUpdate();
		return notificheUpd;
	}
	
	private int updateTentativiNotificheCart(NotificheCartPK notificheCartPk) {
			
		String query = 
				"UPDATE NotificheCart notificheCart " +
		        " SET notificheCart.tentativi = notificheCart.tentativi + 1 " +
		        "   , notificheCart.opAggiornamento = :op_aggiornamento " +
		        "   , notificheCart.tsAggiornamento = :ts_aggiornamento " +
		        "   , notificheCart.stato = :stato " +
		        " WHERE notificheCart.id.e2emsgid = :e2emsgid " +
		        " AND   notificheCart.id.receiverid= :receiverId " +
		        " AND   notificheCart.id.receiversys = :receiverSys " +
				" AND   notificheCart.stato  = :statoOld ";
		
		logger.info(this.getClass().getSimpleName() + " query update NotificheCart = " + query);
		
		Query queryUpdateNotificheCart = em.createQuery (query);
			queryUpdateNotificheCart.setParameter("e2emsgid", notificheCartPk.getE2emsgid());
			queryUpdateNotificheCart.setParameter("receiverId", notificheCartPk.getReceiverid());
			queryUpdateNotificheCart.setParameter("receiverSys", notificheCartPk.getReceiversys());
			queryUpdateNotificheCart.setParameter("op_aggiornamento", "CART SENDER");
			queryUpdateNotificheCart.setParameter("ts_aggiornamento", new Timestamp(System.currentTimeMillis()));
			queryUpdateNotificheCart.setParameter("stato", StatoEnum.DA_SPEDIRE);
			queryUpdateNotificheCart.setParameter("statoOld", StatoEnum.IN_SPEDIZIONE);
			
		int notificheUpd = queryUpdateNotificheCart.executeUpdate();
		return notificheUpd;
	}
	
	private int updateStatoMaxTentativiRaggiuntoNotificheCart(NotificheCartPK notificheCartPk, int maxTentativi, String stato) {
		String query = 
				"UPDATE NotificheCart notificheCart " +
		        " SET stato = :stato " +
		        " WHERE notificheCart.id.e2emsgid = :e2emsgid " +
		        " AND   notificheCart.id.receiverid= :receiverId " +
		        " AND   notificheCart.id.receiversys = :receiverSys " +
				" AND   notificheCart.tentativi >= :maxTentativi ";
		logger.info(this.getClass().getSimpleName() + " query update NotificheCart = " + query);
		
		Query queryUpdateNotificheCart = em.createQuery (query);
			queryUpdateNotificheCart.setParameter("stato", stato);
			queryUpdateNotificheCart.setParameter("e2emsgid", notificheCartPk.getE2emsgid());
			queryUpdateNotificheCart.setParameter("receiverId", notificheCartPk.getReceiverid());
			queryUpdateNotificheCart.setParameter("receiverSys", notificheCartPk.getReceiversys());
			queryUpdateNotificheCart.setParameter("maxTentativi", maxTentativi);
		int notificheUpd = queryUpdateNotificheCart.executeUpdate();
		return notificheUpd;
	}
	
	private void insertConfermeCart(ObjectMessageContainer objectMessageContainer, String stato, Object deliveryResult){
    	ConfermeCartPK cartPK = new ConfermeCartPK();
    		cartPK.setE2emsgid(objectMessageContainer.getE2eMsgId());
    		cartPK.setSenderid(objectMessageContainer.getReceiverId());
    		cartPK.setSendersys(objectMessageContainer.getReceiverSys());
    	ConfermeCart cart = new ConfermeCart();
			cart.setMessaggioXml(deliveryResult.toString().getBytes(Charset.forName("UTF-8")));			
			cart.setStato(stato);
			cart.setId(cartPK);  
			cart.setReceiverid(objectMessageContainer.getSenderId()); 
			cart.setReceiversys(objectMessageContainer.getSenderSys()); 
			cart.setOpInserimento("IDP");
			cart.setTimestampRicezione(new Timestamp(System.currentTimeMillis()));
			cart.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		em.persist(cart);
    }
}

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

import java.io.Serializable;

import javax.ejb.Local;

import it.tasgroup.idp.bean.ObjectMessageContainer;
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.EsitiCartPK;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.NotificheCartPK;

/**
 * Descrive i metodi realizzati da un bean locale che lavora in transazione per conto di {@link CartSenderBean}
 *  
 * @author tasgroup
 *
 */

@Local
public interface ICartSenderBeanTx {

	/* Gestione esiti */
	
	// Nella stessa transazione esegue la put del messaggio di esito nella coda PosizioneDebitoriaOutput e aggiorna il DB
	public void sendJmsAndUpdateEsito (EsitiCart esitiCart);
	// Nella stessa transazione aggiorna il DB
	public void updateDb (EsitiCart esitiCart);
	// Nella stessa transazione serializza un oggetto in una coda JMS
	public void sendJmsObjectMessage(String cfString, String jmsDestinationString, Serializable object);
	// Nella stessa transazione serializza un oggetto in una coda JMS utilizzando,se configurato, un InitialContext con visibilita' sul cluster
	public void sendJmsObjectMessage(String cfString, String cfStringClusterAware, String jmsDestinationString, Serializable object);
	// Nella stessa transazione aumente il numero di tentativi di elaborazione di esiti cart 
	public void incrementTentativiAndManageStopReprocessing(EsitiCartPK esitiCartPk);
	// Cambio lo stato ad EsitiCart (solo lui) e ritorno il numero di elementi aggiornati (o 1 o 0 in caso di fallimento).
	public int updateStatoEsitiCart(EsitiCartPK esitiCartPk, String stato);
	
	/* Gestione notifiche */
	
	// Esegue il delivery in transazione utilizzando il sender specificato
	public Object doDeliveryInTransaction(ISenderNotificationQueueDemux sender, NotificheCart notificheCart) throws Exception;
	// Nella stessa transazione aggiorna il DB
	public void updateDb (NotificheCart notificheCart);
	// Nella stessa transazione aggiorna il DB
	public void updateDb (ObjectMessageContainer objectMessageContainer, String stato, Object deliveryResult);
	// Nella stessa transazione aumente il numero di tentativi di elaborazione di esiti cart 
	public void incrementTentativiAndManageStopReprocessing(NotificheCartPK notificheCartPk);
}

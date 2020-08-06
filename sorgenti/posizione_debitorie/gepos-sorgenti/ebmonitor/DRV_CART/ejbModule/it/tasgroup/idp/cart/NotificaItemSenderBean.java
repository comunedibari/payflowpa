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

import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.bean.ObjectCommandExecutor;
import it.tasgroup.idp.bean.ResultKOException;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

@Stateless

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Remote(ObjectCommandExecutor.class)
public class NotificaItemSenderBean implements ObjectCommandExecutor {
	

	private final Log logger = LogFactory.getLog(this.getClass());

	@Resource 
	private EJBContext ctx;
		
	@EJB(beanName = "NotificaItemSenderBeanTx")
	private INotificaItemSenderBeanTx notificaItemSenderBeanTx;	
		
	/**
	 * I parametri di chiamata sono due:
	 *    1) record di NotificheCart da consegnare
	 *    2) tipo di consegna prevista
	 * 
	 *  Sulla base del tipo di consegna prevista, e' acquisito un SenderNotification a cui viene chiesto il delivery 
	 *  del record di NotificheCart e viene aggiornato lo stato di questo record ad uno stato finale.
	 *  L'aggiornamento di stato puo' essere eseguito da questa classe o in una delle classi richiamate in azione
	 *  dal SenderNotification. Per distinguere tra queste due evenienze si analizza l'output fornito dall'operazione
	 *  di delivery. Normalmente e' null, ad indicare che l'operazione di update dello stato di NotificheCart deve
	 *  essere eseguito da questa classe. Quando invece e' Boolean.FALSE l'update dello stato e' eseguito altrove.
	 *  
	 *  In caso in fase di delivery sia sollevata una ResultKOException, si effettua rollback e si prova ad impostare
	 *  lo stato del record di NotificheCart a ELABORATO_KO.
	 *  
	 *  In caso di altre accezioni
	 */
	@Override
	public MonitoringData executeApplicationTransaction(Object data) {
		logger.info(this.getClass().getSimpleName() + " notifica pagamento, BEGIN ! ");
		
		Object[] obj = (Object[])data;
			NotificheCart notifica = (NotificheCart)obj[0];
			String tipoConsegnaNotifica = (String)obj[1];
		ISenderNotification sender = SenderNotificationFactory.createSenderNotification(tipoConsegnaNotifica);
		Object deliveryResult = null;
		try {			
			logger.info("Cambio stato IN_SPEDIZIONE per la notifica: "+notifica.getId().toString());
			int	updated = notificaItemSenderBeanTx.updateStatoNotificheCart(notifica, StatoEnum.DA_SPEDIRE, StatoEnum.IN_SPEDIZIONE);
			logger.info("Cambiato stato IN_SPEDIZIONE per la notifica: "+notifica.getId().toString()+" (updated="+updated+")");
			try {
			    if (updated!=0) {
			       logger.info("Inizio Delivery per la notifica: "+notifica.getId().toString()+ " utilizzando il sender: "+sender.getClass().getName());
				   deliveryResult = notificaItemSenderBeanTx.delivery(notifica, sender); // puo' sollevare ResultKOException ad indicare un KO di elaborazione
				   logger.info("Delivery effettuato per la notifica: "+notifica.getId().toString()+ " utilizzando il sender: "+sender.getClass().getName());
			    }
			} catch (Exception e) {
				logger.error(this.getClass().getSimpleName() + " ERROR = " + e.getMessage()+ " errore transitorio... rollback");
				updated=notificaItemSenderBeanTx.updateStatoNotificheCart(notifica, StatoEnum.IN_SPEDIZIONE, StatoEnum.DA_SPEDIRE);
				logger.error(this.getClass().getSimpleName() + " Rimessa notifica "+notifica.getId().toString()+" da spedire (updated="+updated+")");				
			}
		} catch (Exception e) {
			logger.error("Errore inatteso",e);			
		} finally {
			logger.info(this.getClass().getSimpleName() + " notifica pagamento, END ! ");
		} 
		return null;
	}
	
	@Override
	public String executeHtml(String msg, String sendId, String sendSys) {
		throw new UnsupportedOperationException();
	}
}

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
package it.tasgroup.idp.bean;

import it.tasgroup.idp.domain.enti.ConfigNotifiche;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.timer.AbstractNotificationTimer;
import it.tasgroup.idp.timer.AbstractNotificationTimer.NotificationStatus;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Interceptors(MonitoringInterceptor.class)
@Stateless
@Remote(CommandExecutor.class)
public class NotificaPagamento implements NotificationCommandExecutor {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;

	private final Log logger = LogFactory.getLog(this.getClass());

	@Resource
	private SessionContext ctx;
	@Resource
	private EJBContext ejbCtx;	
	
	@EJB(beanName = "DataManager")
	private DataStorageInterface dataTx;	
	
	@Override
	public MonitoringData executeApplicationTransaction() {
		throw new UnsupportedOperationException();
	}
	/**
	 * 
	 * @param statoNotificaPagamento
	 * @param aIdEnte
	 * @param aCdTrbEnte
	 * @param formatoNotifica
	 * @return
	 */
	private MonitoringData executeApplicationTransactionInternal(String statoNotificaPagamento,String aIdEnte, String aCdTrbEnte, String formatoNotifica) {
		
		logger.info(" =========== BEGIN executeApplicationTransactionInternal "
				+ "(idEnte = "+ aIdEnte +", cdTrbEnte= "+aCdTrbEnte+", statoNotificaPagamento= "+statoNotificaPagamento 
				+ " formatoNotifica= " + formatoNotifica + ")");
		MonitoringData monData = new MonitoringData();
		try {
			//calcolo la lista dei pagamenti da spedire, relativi alle pendenze in stato pagato
			List<NotifichePagamenti> listaNotifichePagamenti = listaMessaggiNotifichePagamentiCreati(manager,statoNotificaPagamento,aIdEnte, aCdTrbEnte);

			logger.info(" trovati notifiche temporanee con messaggio xml da creare , numtotale = " + listaNotifichePagamenti.size()  );
			//monitoring data
			monData.setNumRecord(listaNotifichePagamenti.size());

			//se ho trovato qualche notifica temporanea... allora procedo... altrimenti esco
			if (listaNotifichePagamenti.size()>0) {
				IBuildNotification builder = BuildNotificationFactory.createNotificationBuilder(formatoNotifica);
				builder.build(listaNotifichePagamenti, statoNotificaPagamento,dataTx,manager);
			}   

		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName() + " ERROR SPEDIZIONE NOTIFICA PAGAMENTO = " + e.getMessage(), e);
		}
		finally {
			logger.info(" =========== END executeApplicationTransactionInternal ========");
		}
		return monData;
	}

	/**
	 * 
	 * @param em
	 * @return
	 */
	private List<NotificheCart> listaMessaggiNotificheCreati(EntityManager em) {
		//recupero lista esiti da spedire
		Query notificheDaSpedire = em.createNativeQuery("select * "
				+ " from NOTIFICHE_CART "
				+ " where NOTIFICHE_CART.stato = :stato "
				+ " order by NOTIFICHE_CART.id_ente, NOTIFICHE_CART.cd_trb_ente, "
				+ " NOTIFICHE_CART.tipo_notifica ",NotificheCart.class);
	   notificheDaSpedire.setParameter("stato", StatoEnum.DA_ELABORARE);
	   
	   @SuppressWarnings(value = "unchecked")
	   List<NotificheCart> listaNotificheCart = (List<NotificheCart>) notificheDaSpedire.getResultList();

		logger.info("trovati notifiche_pag con mex xml già creato, solo da spedire, numtotale = " + listaNotificheCart.size());

		return listaNotificheCart;
	}

	/**
	 *
	 * @param em
	 * @return
	 */
	private List<NotifichePagamenti> listaMessaggiNotifichePagamentiCreati(EntityManager em, String statoNotificaPagamento, String idEnte, String cdTrbEnte) {
		//recupero lista esiti da spedire
		String query = " select * "
				+ " from NOTIFICHE_PAGAMENTI "
				+ " where NOTIFICHE_PAGAMENTI.stato_notifica=:stato_notifica "
				+ "  and  NOTIFICHE_PAGAMENTI.stato_pagamento=:stato_pagamento ";
		
		if (idEnte!=null){
			query += " and NOTIFICHE_PAGAMENTI.id_ente=:idEnte ";
		}
        if (cdTrbEnte!=null){
        	query += " and NOTIFICHE_PAGAMENTI.cd_trb_ente=:cdTrbEnte ";
		}
		Query notificheDaCreare = em.createNativeQuery(query ,NotifichePagamenti.class);

		notificheDaCreare.setParameter("stato_pagamento", statoNotificaPagamento);
		notificheDaCreare.setParameter("stato_notifica", StatoEnum.NOTIFICHE_DA_CREARE); // CHE SCHIFO!!!! Da mettere in ordine....secondo me 
		                                                                                 // non serve utilizzare uno stato differente NEW ESEGUITO, NEW REGOLATO o NEW INCASSATO
		                                                                                 // ma semplicemente NEW... la conseguente modifica deve essere effettuata nella 
		                                                                                 // InserimentoPagamentiNotifica
		if (idEnte!=null){
			notificheDaCreare.setParameter("idEnte",idEnte);
		}
        if (cdTrbEnte!=null){
        	notificheDaCreare.setParameter("cdTrbEnte",cdTrbEnte);
		}
	   @SuppressWarnings(value = "unchecked") 	
	   List<NotifichePagamenti> listaNotifichePagamenti = (List<NotifichePagamenti>) notificheDaCreare.getResultList();

		return listaNotifichePagamenti;
	}



	/**
	 * TODO FIXARE QUESTO METODO !!!
	 * @return
	 */
	public EntityManager getManager() {
			return manager;
	}




	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction(String statoNotificaPagamento) {
			
		return this.executeApplicationTransactionInternal(statoNotificaPagamento,null,null,null);
	}
    /**
     *  
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)	
	public String executeHtml() throws Exception {

		String table = "";
		// connessione db

		logger.info(", executeHtml() BEGIN ");

		// connessione db
		EntityManager em = null;
		try {
			em = getManager();

			Iterator<NotificationStatus> iterNotifStatus = AbstractNotificationTimer.getListStatusToNotify().iterator();
			// ***********************
			// ciclo tutti i tipi di notifica
			while (iterNotifStatus.hasNext()) {

				NotificationStatus currNotifStatus = iterNotifStatus.next();
				logger.info("executeHtml() START handling  " + currNotifStatus.getNotifStatus());
				// recupero la lista delle notifiche da effettuare
				List<ConfigNotifiche> listcfgNot = AbstractNotificationTimer.getListCfgNotifTest(em, currNotifStatus);
				Iterator<ConfigNotifiche> iterListCfgNot = listcfgNot.iterator();
				while (iterListCfgNot.hasNext()) {
					ConfigNotifiche cfgNot = iterListCfgNot.next();
					// effettuo il caricamento della tabella NOTIFICHE_PAGAMENTI
					this.executeApplicationTransaction(cfgNot);

				}
				logger.info("executeHtml() END handling  "
						+ currNotifStatus.getNotifStatus());
			}
			// ***********************
			List<NotificheCart> lins = listaMessaggiNotificheCreati(manager);

			logger.info("executeHtml, found "
					+ lins.size());
			table = "<br><br>";
			table += "<b>Lista Messaggi di Notifica AGGREGATI e CREATI con messaggio Xml pronto per essere spedito</b>";
			table += "<br>";
			table += "<TABLE border=\"\1\">";
			table += "<TR>" + "<TD>E2eMsgid</TD>" + "<TD>receiverId</TD>"
					+ "<TD>receiverSys</TD>" + "<TD>Stato</TD>"
					+ "<TD>TS Inserimento</TD>" + "<TD>Notifica Xml</TD>"
					+ "</TR>";

			int i = 0;
			for (NotificheCart object : lins) {
				table += "<TR>";
				table += "<TD>";
				table += (object.getId().getE2emsgid());
				table += "</TD>";
				table += "<TD>";
				table += (object.getId().getReceiverid());
				table += "</TD>";
				table += "<TD>";
				table += (object.getId().getReceiversys());
				table += "</TD>";
				table += "<TD>";
				table += (object.getStato());
				table += "</TD>";
				table += "<TD>";
				table += (object.getTsInserimento());
				table += "</TD>";
				table += "<TD>";
				table += "<span title=\"Click here to display XML\" onclick=\"openCloseDiv( 'DIVNAME"
						+ i + "' )\">";
				table += "Open/Close</span>";
				table += "<div id=\"DIVNAME" + i
						+ "\"  style=\"display: none;\">";
				i++;
				table += (StringEscapeUtils.escapeHtml(new String(object
						.getNotificaXml()).toString()));
				table += "</div>";
				table += "</TD>";
				table += "</TR>";
			}
			table += "</TABLE>";

		} catch (Exception e) {
			logger.info(" ERROR EXECUTEHTML "
					+ e.getMessage());
		}
		// table+="</PRE>";
		return table;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction(Object data) {
		if (data instanceof String) 
			return executeApplicationTransactionInternal((String)data,null,null,null);			
		else {
			ConfigNotifiche cfg = (ConfigNotifiche)data;			
			
			String tipoNotifica    = cfg.getTipoNotifica();
			String formatoNotifica = cfg.getFormatoNotifica();
			String idEnte       = cfg.getJltentr().getId().getIdEnte();
			String cdTrbEnte    = cfg.getJltentr().getId().getCdTrbEnte();
			return executeApplicationTransactionInternal(tipoNotifica,idEnte,cdTrbEnte,formatoNotifica);
			
		}
	}
	
}

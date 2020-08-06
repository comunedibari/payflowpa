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
package it.tasgroup.idp.timer;

import it.tasgroup.idp.bean.NotificationCommandExecutor;
import it.tasgroup.idp.domain.enti.ConfigNotifiche;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;
import it.tasgroup.idp.monitoring.TimerActivationControllerLocal;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class AbstractNotificationTimer extends AbstractTimer implements ITimerLocal, ITimerRemote {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;
	
	@EJB(beanName = "NotificaPagamento")
	private NotificationCommandExecutor NotificaPagamentoProxy;

	@EJB(beanName = "NotificaSenderBean")
	private NotificationCommandExecutor NotificaSenderProxy;
	
	@EJB(beanName = "EstrazionePagamentiNotifica")
	private NotificationCommandExecutor EstrazioneNotificheProxy;	

	private final Log logger = LogFactory.getLog(this.getClass());
 
	public enum NotificationFrequence {
		MAI("MAI"), 
		AD_EVENTO("AD_EVENTO"), 
		GIORNALIERO("GIORNALIERO"),
		OGNI_6_ORE("OGNI_6_ORE"),
		OGNI_12_ORE("OGNI_12_ORE");
		
		private String notifFreq;
		private NotificationFrequence(String aNotifFreq) {
			notifFreq=aNotifFreq;
		}
		public String getFrequence() { return notifFreq;}
	}
    
	public enum NotificationFormat {
		RFC_145_V010300("RFC_145_V010300"),
	    RFC_145_V010302("RFC_145_V010302"),
		RFC_145_V010303("RFC_145_V010303"),
		GTART("GTART"),
		GTART_DIRECT("GTART_DIRECT"),
		CSV_BASIC_V2("CSV_BASIC_V2");
				
		private String notifFormat;
		private NotificationFormat(String aNotifFormat){
			notifFormat=aNotifFormat;
		}
		public String getNotifFormat() { return notifFormat;}
		
		public boolean equals(NotificationFormat n) {
			return this.getNotifFormat().equals(n.getNotifFormat());			
		}
	}
	
	public enum NotificationMethod {
		PUSH_CART("PUSH_CART"),
		PULL_WS("PULL_WS"),		
		PUSH_WS_GTART("PUSH_WS_GTART"),
		PUSH_WS_RFC145("PUSH_WS_RFC145");
		
		private String notifMethod;
		private NotificationMethod(String aNotifMethod) {
			notifMethod=aNotifMethod;
		}
		public String getNotifMethod() { return notifMethod;}
		
		public boolean equals(NotificationMethod n) {
			return this.getNotifMethod().equals(n.getNotifMethod());			
		}
		
	}
	
	public enum NotificationStatus {
		ESEGUITO("ESEGUITO"), 
		REGOLATO("REGOLATO"),
		INCASSO("INCASSO");
		
		private String notifStatus;
		private NotificationStatus(String aNotifStatus) {
			notifStatus=aNotifStatus; 
		}
		public String getNotifStatus() { return notifStatus;}
		
	}
	// settato da 
	protected NotificationFrequence notifFrequence;
	
	private static ArrayList<NotificationStatus> listStatusToNotify = new ArrayList<NotificationStatus>();
	
	static {
		listStatusToNotify = new ArrayList<NotificationStatus>();
		listStatusToNotify.add(NotificationStatus.ESEGUITO);
		listStatusToNotify.add(NotificationStatus.REGOLATO); 
		listStatusToNotify.add(NotificationStatus.INCASSO);
	}

	@EJB(beanName = "TimerActivationController")
	private TimerActivationControllerLocal activationController;

	@Timeout
	// @Interceptors(TimerInterceptor.class)
	public void handleTimeout(Timer timer) {
		if(!activationController.isTimerActive(this.getClass().getSimpleName()))
			return;
		
		logger.info(" handleTimeout() BEGIN ");
		
		Iterator<NotificationStatus> iterNotifStatus= getListStatusToNotify().iterator();
		
		//***********************
		// ciclo tutti i tipi di notifica
		while (iterNotifStatus.hasNext()) {
			
			NotificationStatus currNotifStatus = iterNotifStatus.next();
			
			logger.info("START handling  "+ currNotifStatus.getNotifStatus());
			
			// effettuo il caricamento della tabella NOTIFICHE_PAGAMENTI
			logger.info(" Extract Pagamenti and build NotPagamenti ");
			EstrazioneNotificheProxy.executeApplicationTransaction(currNotifStatus.getNotifStatus());
			// recupero la lista delle notifiche 
			List<ConfigNotifiche> listcfgNot= getListCfgNotif(getNotifFrequence(),currNotifStatus);
			Iterator<ConfigNotifiche> iterListCfgNot = listcfgNot.iterator();
			while (iterListCfgNot.hasNext()) {
				ConfigNotifiche cfgNot=iterListCfgNot.next();			
				logger.info(" Managing CfgNotifica = " + cfgNot.getConsegnaNotifica() + " - " + cfgNot.getFormatoNotifica() 
						+ " - " + cfgNot.getFreqNotifica() + " - " + cfgNot.getTipoNotifica() );
				// effettuo il popolamento di NOTIFICHE_CART
				NotificaPagamentoProxy.executeApplicationTransaction(cfgNot);
			}
			logger.info("END handling  "+ currNotifStatus.getNotifStatus());
		}
		
		// Invio Notifiche
		logger.info("START spedizione");
		List<NotificheCart> notificheCartDaSpedire = getNotificheCartDaSpedire();
		for (NotificheCart notificaCartDaSpedire : notificheCartDaSpedire) {
			NotificaSenderProxy.executeApplicationTransaction(notificaCartDaSpedire);
		}
		logger.info("END spedizione");
		
		//***********************
		logger.info(", handleTimeout() END ");
	}

	public NotificationFrequence getNotifFrequence() {
		return notifFrequence;
	}

	public void setNotifFrequence(NotificationFrequence notifFrequence) {
		this.notifFrequence = notifFrequence;
	}

	/**
	 * 
	 * @param notifFreq
	 * @param notifStatus
	 * @return
	 */
	private List<ConfigNotifiche> getListCfgNotif(NotificationFrequence notifFreq, NotificationStatus notifStatus) {
	
		Query queryListCfgNot = manager.createNamedQuery("getNotifByFreqAndTypeCheckNotifPagamenti"); // "getNotifByFreqAndType");	
		                                                 
		queryListCfgNot.setParameter("freqNotifica", notifFreq.getFrequence());
		queryListCfgNot.setParameter("tipoNotifica", notifStatus.getNotifStatus());
					
		List<ConfigNotifiche> listaConfigNotif = (List<ConfigNotifiche>) queryListCfgNot.getResultList();
	
		return listaConfigNotif;
	}	
	/**
	 * 
	 *
	 * @param notifStatus
	 * @return
	 * 
	 * Utilizzata solamente a scopo di test per la esecuzione manuale dei timer
	 * recupera tutte le notifiche con periodicita configurata diversa da MAI e relative al 
	 * NotificationStatus fornito come parametro 
	 */
	public static List<ConfigNotifiche> getListCfgNotifTest(EntityManager em,NotificationStatus notifStatus) {
	
		Query queryListCfgNot = em.createNamedQuery("getNotifByTypeCheckNotifPagamenti");				
		queryListCfgNot.setParameter("freqNotifica", NotificationFrequence.MAI.getFrequence());
		queryListCfgNot.setParameter("tipoNotifica", notifStatus.getNotifStatus());
			
		
		List<ConfigNotifiche> listaConfigNotif = (List<ConfigNotifiche>) queryListCfgNot.getResultList();
	
		return listaConfigNotif;
	}
    /**
     * DA CONTROLLARE... verificare eventuale corsa critica in inizializzazione
     * @return
     */
	public static ArrayList<NotificationStatus> getListStatusToNotify() {
		return listStatusToNotify;
	}	
    /**
     * 
     */
	public void cleanUpNotifichePagamenti() {
		
		logger.info(this.getClass().getSimpleName() + "cleanUpNotifichePagamenti BEGIN  ");		
		// update dei record sulla notifica pagamenti  con stato 'DA_CREARE' che hanno associato sulla tabella cfg_notifica_pagamento frequenza = 'MAI'
		String sqlSelect = "Select ID_NOTIFICA FROM NOTIFICHE_PAGAMENTI  LEFT OUTER JOIN CFG_NOTIFICA_PAGAMENTO  " +
                           " ON (NOTIFICHE_PAGAMENTI.CD_TRB_ENTE = CFG_NOTIFICA_PAGAMENTO.CD_TRB_ENTE AND " +
                           "     NOTIFICHE_PAGAMENTI.ID_ENTE = CFG_NOTIFICA_PAGAMENTO.ID_ENTE AND " +
                           "     NOTIFICHE_PAGAMENTI.STATO_PAGAMENTO = CFG_NOTIFICA_PAGAMENTO.TIPO_NOTIFICA) " + 
                           " WHERE NOTIFICHE_PAGAMENTI.STATO_NOTIFICA='DA_CREARE' AND CFG_NOTIFICA_PAGAMENTO.FREQ_NOTIFICA='"+NotificationFrequence.MAI.getFrequence()+"'";
		
		cleanUpNotifichePagamenti(sqlSelect);
		logger.info(this.getClass().getSimpleName() + "cleanUpNotifichePagamenti updated records with related frequences = MAI on cfg_notifica_pagamento ");
		// update dei record sulla notifica pagamenti con stato 'DA_CREARE' che non hanno corrispettiva configurazione su cfg_notifica_pagamento
		sqlSelect = "select ID_NOTIFICA FROM NOTIFICHE_PAGAMENTI " +
                    " WHERE " +
                    " NOT EXISTS ( " +
                    " SELECT CFG_NOTIFICA_PAGAMENTO.ID " +
                    " FROM CFG_NOTIFICA_PAGAMENTO " +
                    " WHERE NOTIFICHE_PAGAMENTI.CD_TRB_ENTE = CFG_NOTIFICA_PAGAMENTO.CD_TRB_ENTE AND " +
                    "      NOTIFICHE_PAGAMENTI.ID_ENTE = CFG_NOTIFICA_PAGAMENTO.ID_ENTE AND " +
                    "      NOTIFICHE_PAGAMENTI.STATO_PAGAMENTO = CFG_NOTIFICA_PAGAMENTO.TIPO_NOTIFICA " +
                    ") " +
                    " AND NOTIFICHE_PAGAMENTI.STATO_NOTIFICA='DA_CREARE'";
		
		cleanUpNotifichePagamenti(sqlSelect);
		logger.info(this.getClass().getSimpleName() + "cleanUpNotifichePagamenti updated records no related configuration on cfg_notifica_pagamento  ");
		logger.info(this.getClass().getSimpleName() + "cleanUpNotifichePagamenti END  ");	
	}
	/**
	 * 
	 * @param sqlSelect
	 */
	private void cleanUpNotifichePagamenti(String sqlSelect) {
		 Query listOfNotifPagamenti = manager.createNativeQuery(sqlSelect);
		 
			List listaNotif =listOfNotifPagamenti.getResultList();
			Iterator i = listaNotif.iterator();
			
			logger.info(" Executed cleanUp NotifichePagamenti on " + listaNotif.size() + " records ");
			while (i.hasNext()) {
				String key = (String)i.next();
				NotifichePagamenti n = manager.find(NotifichePagamenti.class, key);
				if (n!=null) {
					n.setStatoNotifica("NON_PREVISTO");
				}
			}
		 
	 }

	private List<NotificheCart> getNotificheCartDaSpedire() {
		//recupero lista notifiche da spedire
		String SQL = "SELECT * "
					+ "FROM NOTIFICHE_CART "
					+ "WHERE STATO = :stato "
					+ "ORDER BY ID_ENTE, CD_TRB_ENTE, TIPO_NOTIFICA ";
		Query notificheDaSpedire = manager.createNativeQuery(SQL, NotificheCart.class);
		notificheDaSpedire.setParameter("stato", StatoEnum.DA_SPEDIRE);
		
		List<NotificheCart> listaNotificheCart = (List<NotificheCart>) notificheDaSpedire.getResultList();
		logger.info("Notifiche da spedire = " + listaNotificheCart.size());
		return listaNotificheCart;
	}
}

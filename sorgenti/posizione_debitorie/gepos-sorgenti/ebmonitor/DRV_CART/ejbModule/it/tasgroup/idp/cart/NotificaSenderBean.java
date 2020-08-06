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
/*

 * Copyright (c) 1996-2002 Sun Microsystems, Inc. All Rights Reserved.

 *

 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,

 * modify and redistribute this software in source and binary code form,

 * provided that i) this copyright notice and license appear on all copies of

 * the software; and ii) Licensee does not utilize the software in a manner

 * which is disparaging to Sun.

 *

 * This software is provided "AS IS," without a warranty of any kind. ALL

 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY

 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR

 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE

 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING

 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS

 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,

 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER

 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF

 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE

 * POSSIBILITY OF SUCH DAMAGES.

 *

 * This software is not designed or intended for use in on-line control of

 * aircraft, air traffic, aircraft navigation or aircraft communications; or in

 * the design, construction, operation or maintenance of any nuclear

 * facility. Licensee represents and warrants that it will not use or

 * redistribute the Software for such purposes.

 */
package it.tasgroup.idp.cart;

import it.tasgroup.idp.bean.CommandExecutor;
import it.tasgroup.idp.bean.NotificationCommandExecutor;
import it.tasgroup.idp.bean.ObjectCommandExecutor;
import it.tasgroup.idp.domain.enti.ConfigNotifiche;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.timer.AbstractNotificationTimer;
import it.tasgroup.idp.timer.AbstractNotificationTimer.NotificationStatus;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Remote;
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
public class NotificaSenderBean implements NotificationCommandExecutor {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;
	@Resource 
	private EJBContext ejbCtx;	

	private final Log logger = LogFactory.getLog(this.getClass());	
	
	@EJB(beanName = "NotificaItemSenderBean")
	private ObjectCommandExecutor NotificaItemSenderProxy;
	
	
	/**
	 * Invocato solamente da pagina web per test notifiche 
	 */	
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public MonitoringData executeApplicationTransaction() {
    	logger.info(" BEGIN ");
    	String table = "";
    	//connessione db
    	EntityManager em = null;
    	try {
    		em = getManager();
    		Iterator<NotificationStatus> iterNotifStatus= AbstractNotificationTimer.getListStatusToNotify().iterator();
    		//***********************
    		// ciclo tutti i tipi di notifica
    		while (iterNotifStatus.hasNext()) {

    			NotificationStatus currNotifStatus = iterNotifStatus.next();
    			logger.info(" START handling  "+ currNotifStatus.getNotifStatus());
    			// recupero la lista delle notifiche da effettuare 
    			List<ConfigNotifiche> listcfgNot= AbstractNotificationTimer.getListCfgNotifTest(em, currNotifStatus);
    			Iterator<ConfigNotifiche> iterListCfgNot = listcfgNot.iterator();
    			while (iterListCfgNot.hasNext()) {
    				ConfigNotifiche cfgNot=iterListCfgNot.next();
    				// effettuo il caricamento della tabella NOTIFICHE_PAGAMENTI
    				this.executeApplicationTransaction(cfgNot);				

    			}
    			logger.info(" END handling  "+ currNotifStatus.getNotifStatus());
    		}
    		//***********************    		
    	} catch (Exception e) {
    		ejbCtx.setRollbackOnly();				
    		logger.error(" ERROR = " + e.getMessage());
    	} finally {
    		logger.info(" END ");
    	}					
    	return null;
    }

    /**
     * 
     * @param em
     * @return
     */
	private List<NotificheCart> listaNotificheProntiDaSpedire(EntityManager em, ConfigNotifiche cfgNot) {
		
		
		String strQuery = "SELECT notificheCart FROM NotificheCart notificheCart " +
				" WHERE (notificheCart.stato=:stato) ";
				
		if (cfgNot!=null) {
			strQuery=strQuery +  " AND notificheCart.tipoNotifica=:tipoNotifica ";
			strQuery=strQuery +  " AND notificheCart.idEnte=:idEnte ";
			strQuery=strQuery +  " AND notificheCart.cdTrbEnte=:cdTrbEnte ";
		} else {
			strQuery=strQuery + " order by notificheCart.idEnte, ";
			strQuery=strQuery + " notificheCart.cdTrbEnte, ";
			strQuery=strQuery + " notificheCart.tipoNotifica ";
		}
		
		Query queryEsiti = em.createQuery (strQuery);
		
		queryEsiti.setParameter("stato", StatoEnum.DA_SPEDIRE);

		if (cfgNot!=null) {
			queryEsiti.setParameter("tipoNotifica",cfgNot.getTipoNotifica());
			queryEsiti.setParameter("idEnte",cfgNot.getJltentr().getId().getIdEnte());
			queryEsiti.setParameter("cdTrbEnte",cfgNot.getJltentr().getId().getCdTrbEnte());
		}
		List<NotificheCart> lins = (List) queryEsiti.getResultList();
		return lins;
	}
    
    
	@Override
	public MonitoringData executeApplicationTransaction(String data) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 */
	public String executeHtml() throws Exception  {			
		
	String table = "";
	//connessione db
	EntityManager em = null;		
	try {				
		em = getManager();
		//calcolo la lista delle pendenze per cui gli esiti sono pronti
		//per essere creati e spediti
		List<NotificheCart> lins = listaNotificheProntiDaSpedire(em,null);	
			 
			logger.info("executeHtml, found " + lins.size());	
			table = "<br><br>";
			table+="<TABLE border=\"\1\">";
			int i = 0;
			for (NotificheCart object : lins) {
				table+="<TR>";
					table+="<TD>";
						table+=(object.getId().getE2emsgid());
					table+="</TD>";
					table+="<TD>";
						table+=(object.getId().getReceiverid());
					table+="</TD>";
					table+="<TD>";
						table+=(object.getId().getReceiversys());
					table+="</TD>";						
					table+="<TD>";
						table+=(object.getStato());
					table+="</TD>";
					table+="<TD>";
						table+=(object.getTsInserimento());
					table+="</TD>";										
					table+="<TD>";
						table+="<span title=\"Click here to display XML\" onclick=\"openCloseDiv( 'DIVNAME"+i+"' )\">";
						table+="Open/Close</span>";
						table+="<div id=\"DIVNAME"+i+"\"  style=\"display: none;\">";
						i++;							
						table+=( StringEscapeUtils.escapeHtml(  new String(object.getNotificaXml()).toString() ));
						table+="</div>";						
					table+="</TD>";										
				table+="</TR>";				
			}		
			
			table+="</TABLE>";
		} catch (Exception e) {
			logger.info(this.getClass().getSimpleName() + e.getMessage());
		}
//		table+="</PRE>";
		return table;
	}


	/**
	 * 
	 * @return
	 */
	public EntityManager getManager() {
			return manager;
	}

	
	/**
	 * Il metodo prevede un argomento di tipo NotificheCart o ConfigNotifiche.
	 *  
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction(Object data) {
		if (data instanceof NotificheCart) {
			return executeApplicationTransaction((NotificheCart) data);
		}
		if (data instanceof ConfigNotifiche) {
			return executeApplicationTransaction((ConfigNotifiche) data);
		}
		return null;
	}
	
	/*
	 * Data la configurazione di un tributo, si ricava la lista delle notifiche in stato 'DA SPEDIRE' e per ciascuna di 
	 * queste si chiama NotificaItemSenderProxy
	 */
	private MonitoringData executeApplicationTransaction(ConfigNotifiche cfgNot) {
		MonitoringData monData = new MonitoringData();    	
		try {	
			//------
			//recupero la lista degli xml da inviare
			List<NotificheCart> lins = listaNotificheProntiDaSpedire(manager,cfgNot);

			String cfgNotDetail = (cfgNot!=null ? cfgNot.getTipoNotifica()+"-"+cfgNot.getJltentr().getId().getIdEnte()+"-"+cfgNot.getJltentr().getId().getCdTrbEnte() : "NOT CONFIG");
			logger.info("["+cfgNotDetail+"] ho trovato n. notifiche da spedire " + lins.size());	
			monData.setNumRecord(lins.size());

			for (int i = 0; i < lins.size(); i++) {
				NotificheCart notificheCart = lins.get(i);
		
				Object[] obj = new Object[2];
				obj[0] = notificheCart;
				obj[1] = cfgNot.getConsegnaNotifica();
				NotificaItemSenderProxy.executeApplicationTransaction(obj);
			}
		} catch (Exception e) {
			ejbCtx.setRollbackOnly();				
			logger.error(this.getClass().getSimpleName() + " ERROR = " + e.getMessage());
		}		
		return monData;
	}
	
	/*
	 * Data una notifica cart (in stato 'DA SPEDIRE') si ricava la corrispondente configurazione tributo e si chiama  
	 * NotificaItemSenderProxy
	 */
	private MonitoringData executeApplicationTransaction(NotificheCart notificaCart) {
		MonitoringData monData = new MonitoringData();    
		try {
			ConfigNotifiche cfgNotifica =
				(ConfigNotifiche)
					manager.createNamedQuery("getNotifByIdEnteCdTrbEnteAndType")
						.setParameter("tipoNotifica", notificaCart.getTipoNotifica())
						.setParameter("idEnte", notificaCart.getIdEnte())
						.setParameter("cdTrbEnte", notificaCart.getCdTrbEnte())
							.getSingleResult();
			monData.setNumRecord(1);

			Object[] obj = new Object[2];
			obj[0] = notificaCart;
			obj[1] = cfgNotifica.getConsegnaNotifica();
			NotificaItemSenderProxy.executeApplicationTransaction(obj);
		} catch (Exception e) {
			ejbCtx.setRollbackOnly();				
			logger.error(this.getClass().getSimpleName() + " ERROR = " + e.getMessage());
		}			
		return monData;
	}

	private void updateNotificheCart(String newState, NotificheCart notificheCart) {
		String query = "Update NotificheCart notificheCart " +
				" set notificheCart.stato = :stato  " +
				" , notificheCart.opAggiornamento = :op_aggiornamento  " +
				" , notificheCart.tsAggiornamento = :ts_aggiornamento  " +
				" WHERE notificheCart.id.e2emsgid = :e2emsgid" +
				" AND notificheCart.id.receiverid= :receiverId " +
				" AND notificheCart.id.receiversys = :receiverSys ";
				logger.info(this.getClass().getSimpleName() + " query update notificheCart = " + query);
				//aggiorno
				Query queryUpdNotificaCart = manager.createQuery (query);
				queryUpdNotificaCart.setParameter("stato", newState);			
				queryUpdNotificaCart.setParameter("e2emsgid", notificheCart.getId().getE2emsgid());
				queryUpdNotificaCart.setParameter("receiverId", notificheCart.getId().getReceiverid());
				queryUpdNotificaCart.setParameter("receiverSys", notificheCart.getId().getReceiversys());
				queryUpdNotificaCart.setParameter("op_aggiornamento", "NOTIFICA SENDER");
				queryUpdNotificaCart.setParameter("ts_aggiornamento", new Timestamp(System.currentTimeMillis()));
				int notificheUpd = queryUpdNotificaCart.executeUpdate();
								
				// se questo processo non gira gli ESITI_CART rimangono in stato DA_SPEDIRE
				// mentre le pendenze cart rimangono in stato RISPOSTA_INVIATA
				
				logger.info(this.getClass().getSimpleName() + " ho cambiato (notifica="+notificheUpd+") lo stato della notificacart " 
							+ notificheCart.getId().getE2emsgid() + " - " + notificheCart.getId().getReceiverid() + " -  " + notificheCart.getId().getReceiversys() + " , num. esiti = " + notificheUpd );							
	}

}

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

import it.tasgroup.idp.domain.messaggi.ErroriIdp;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.util.List;

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
@Remote(ObjectCommandExecutor.class)
public class MonitoraggioErrori implements ObjectCommandExecutor {
   
	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;	

	private final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction() {
		//monitoring data
		return null;
	}


	/**
	 * 
	 * @param em
	 * @return
	 */
	private List<ErroriIdp> listaErroriIdp(EntityManager em, String msg, String sendId, String sendSys) {
		Query queryErroriIdp = em.createQuery ("SELECT erroriIdp FROM ErroriIdp erroriIdp " +
				" WHERE erroriIdp.pendenzeCart.pk.e2emsgid = :e2emsgid" +
				" AND erroriIdp.pendenzeCart.pk.senderid = :senderId " + 
				" AND erroriIdp.pendenzeCart.pk.sendersys = :senderSys");		
		queryErroriIdp.setParameter("e2emsgid", msg);
		queryErroriIdp.setParameter("senderId", sendId);
		queryErroriIdp.setParameter("senderSys", sendSys);
		List<ErroriIdp> lins = null; 
		lins = (List) queryErroriIdp.getResultList();
		return lins;
	}


	/**
	 * TODO FIXARE QUESTO METODO !!!
	 * @return
	 */
	public EntityManager getManager() {
		//quando siamo su JBOSS ci vuole questa riga
//		return emf.createEntityManager();
		//quando siamo su TEST JUNIT ci vuole questa riga
//			return manager;
		//JTA
		return manager;
	}



	@Override
	public MonitoringData executeApplicationTransaction(Object data) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String executeHtml(String msg, String sendId, String sendSys)  {			
		
	String table = "";
	//connessione db
	EntityManager em = null;		
	try {				
		em = getManager();
		//esito cart
		List<ErroriIdp> lins = listaErroriIdp(em, msg, sendId, sendSys);	
			 
		table = "<br><br>";
		table += "<b>Errore ricevuto a seguito dell'invio di un esito a Cart </b>";
		table += "<br>";			
		table+="<TABLE border=\"\1\">";
		table+="<TR>";
			table+="<TD>ID</TD>";							
			table+="<TD>SENDERID</TD>";
			table+="<TD>SENDERSYS</TD>";		
			table+="<TD>STATO</TD>";
			table+="<TD>DEX STATO</TD>";
			table+="<TD>XML</TD>";	
			table+="<TD>TS INSERIMENTO</TD>";
		table+="</TR>";		
	
		int i = 0;
		for (ErroriIdp object : lins) {
			table+="<TR>";
				table+="<TD>";
				table+=	(object.getPk().getE2emsgid());
				table+="</TD>";							
				table+="<TD>";
					table+=(object.getPk().getSenderid());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getPk().getSendersys());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getPk().getStato());
				table+="</TD>";				
				table+="<TD>";
					table+=(object.getDescrizioneStato());
				table+="</TD>";
				table+="<TD>";
					table+="<span title=\"Click here to display XML\" onclick=\"openCloseDiv( 'DIVNAME"+i+"' )\">";
					table+="Open/Close</span>";
					table+="<div id=\"DIVNAME"+i+"\"  style=\"display: none;\">";
					i++;							
					table+=( StringEscapeUtils.escapeHtml( new String(object.getEsitoXml()).toString()));
					table+="</div>";	
				table+="</TD>";					
				table+="<TD>";
					table+=(object.getTsInserimento());
				table+="</TD>";											
			table+="</TR>";				
		}				
		table+="</TABLE>";
		
		} catch (Exception e) {
			logger.info(this.getClass().getSimpleName() + " ERROR EXECUTEHTML " +  e.getMessage());
		}
		return table;
	}

	
}

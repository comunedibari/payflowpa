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

import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

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
//@TransactionManagement(TransactionManagementType.BEAN)
@Remote(ObjectCommandExecutor.class)
public class MonitoraggioEsiti implements ObjectCommandExecutor {
   
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
	private List<EsitiCart> listaEsitiCart(EntityManager em, String msg, String sendId, String sendSys) {
		Query queryEsitiCart = em.createQuery ("SELECT esitiC FROM EsitiCart esitiC " +
				" WHERE esitiC.pendenzeCart.pk.e2emsgid = :e2emsgid" +
				" AND esitiC.pendenzeCart.pk.senderid = :senderId " + 
				" AND esitiC.pendenzeCart.pk.sendersys = :senderSys");		
		queryEsitiCart.setParameter("e2emsgid", msg);
		queryEsitiCart.setParameter("senderId", sendId);
		queryEsitiCart.setParameter("senderSys", sendSys);
		List<EsitiCart> lins = null; 
		lins = (List) queryEsitiCart.getResultList();
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
		//PU JTA
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
		List<EsitiCart> lins = listaEsitiCart(em, msg, sendId, sendSys);	
			 
		table = "<br><br>";
		table += "<b>Esito inviato a Cart </b>";
		table += "<br>";			
		table+="<TABLE border=\"\1\">";
		table+="<TR>";
			table+="<TD>ID</TD>";							
			table+="<TD>SENDERID</TD>";
			table+="<TD>SENDERSYS</TD>";		
			table+="<TD>STATO</TD>";		
			table+="<TD>XML</TD>";
			table+="<TD>ESITO</TD>";
			table+="<TD>TS INVIO</TD>";
		table+="</TR>";				
		
		int i = 0;
		for (EsitiCart object : lins) {
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
				if (
//						StatoEnum.RISPOSTA_INVIATA.equals(object.getStato().trim())
						StatoEnum.DA_REINVIARE.equals(object.getStato().trim())
						|| StatoEnum.RIFIUTATO.equals(object.getStato().trim())
						) {
						table+="<a href=\"invoke?operation=dettaglioErroreIdp&objectname=it.tasgroup.idp.jmx%3Atype%3DMXBeanCommandExecutor&" +
						"value0=" + object.getPk().getE2emsgid().trim() +
						"&type0=java.lang.String" +
						"&value1=" + object.getPk().getSenderid().trim() +
						"&type1=java.lang.String" +
						"&value2=" + object.getPk().getSendersys().trim() +
						"&type2=java.lang.String\">"+object.getStato()+"</a>";										
					} else {
						table+=(object.getStato());	
					}		
				table+="</TD>";
				table+="<TD>";
					table+="<span title=\"Click here to display XML\" onclick=\"openCloseDiv( 'DIVNAME"+i+"' )\">";
					table+="Open/Close</span>";
					table+="<div id=\"DIVNAME"+i+"\"  style=\"display: none;\">";
					i++;							
					table+=( StringEscapeUtils.escapeHtml(  new String(object.getEsitoXml()).toString() ));
					table+="</div>";									
				table+="</TD>";					
				table+="<TD>";
					table+=(object.getTimestampInvio());
				table+="</TD>";											
			table+="</TR>";							
			
		}				
		table+="</TABLE>";
		
		} catch (Exception e) {
			logger.info(this.getClass().getSimpleName() + " ERROR EXECUTEHTML " +  e.getMessage());
		}
//		table+="</PRE>";
		return table;
	}


//	@Override
//	public MonitoringData executeApplicationTransaction(Object data) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
}

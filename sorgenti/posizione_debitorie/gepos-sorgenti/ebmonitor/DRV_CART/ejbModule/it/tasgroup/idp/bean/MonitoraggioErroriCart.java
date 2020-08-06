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

import it.tasgroup.idp.domain.messaggi.ErroriCart;
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

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

@Interceptors(MonitoringInterceptor.class)
@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
@Remote(CommandExecutor.class)
public class MonitoraggioErroriCart implements CommandExecutor, CommandExecutorLocal {
   
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
	private List<ErroriCart> listaErroriCart(EntityManager em) {
		List<ErroriCart> pend;
		org.hibernate.Session session = (org.hibernate.Session) em.getDelegate();		
		Criteria critPend = session.createCriteria(ErroriCart.class);	
		critPend.addOrder(Order.desc("tsInserimento"));
		pend = critPend.setMaxResults(25).list();
		return pend;
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
	public MonitoringData executeApplicationTransaction(String data) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String executeHtml() throws Exception  {			
		
	String table = "";
	//connessione db
	EntityManager em = null;		
	try {				
		em = getManager();
		//calcolo la lista delle pendenze per cui gli esiti sono pronti
		//per essere creati e spediti
		List<ErroriCart> lins = listaErroriCart(em);	
			 
		System.out.println("executeHtml, found " + lins.size());	
		table = "<br><br>";
		table += "<b>Lista errori cart , ordinate in base al tempo di arrivo</b>";
		table += "<br>";			
		table+="<TABLE border=\"\1\">";
		table+="<TR>";
			table+="<TD>TIPO MEX</TD>";							
			table+="<TD>STATO ERRORE</TD>";
			table+="<TD>TS INSERT</TD>";		
			table+="<TD>XML</TD>";		
		table+="</TR>";		
	
		int i = 0;
		for (ErroriCart object : lins) {
			table+="<TR>";
				table+="<TD>";
					table+=object.getTipoMessaggio();	
				table+="</TD>";							
				table+="<TD>";
					table+=(object.getStatoErrore());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getTsInserimento());
				table+="</TD>";						
//				table+="<TD>";
//					table+=(new String(object.getMessaggioXml()).toString());			
//				table+="</TD>";
				table+="<TD>";
				table+="<span title=\"Click here to display XML\" onclick=\"openCloseDiv( 'DIVNAME"+i+"' )\">";
				table+="Open/Close</span>";
				table+="<div id=\"DIVNAME"+i+"\"  style=\"display: none;\">";
				i++;
//						""display: block;"							
					table+=( StringEscapeUtils.escapeHtml(  new String(object.getMessaggioXml()).toString()));
					table+="</div>";
				table+="</TD>";				

//				table+="<TD>";
//					table+=(object.getTimestampRicezione());
//				table+="</TD>";										
//				table+="<TD>";
//					table+=(object.getNumPendenze());
//				table+="</TD>";										
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

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

import it.tasgroup.idp.domain.messaggi.ErroriEsitiPendenza;
import it.tasgroup.idp.domain.messaggi.EsitiPendenza;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Interceptors(MonitoringInterceptor.class)
@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
@Remote(ObjectCommandExecutor.class)
public class MonitoraggioEsitiPendenza implements ObjectCommandExecutor {
   
	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;	

	private final Log logger = LogFactory.getLog(this.getClass());
	


	/**
	 * 
	 * @param em
	 * @return
	 */
	private List<EsitiPendenza> listaEsitiPendenza(EntityManager em, String msg, String sendId, String sendSys) {
		List<EsitiPendenza> lins = null; 			
		//recupero lista esiti da spedire
		//cerco le pendenze i cui esiti sono tutti in stato COMPLETO
		//questa sarebbe di molto meglio scriverla come QUERY JPA
		//scriverla come NativeQuery comporta l'output di tutte le colonne in output (vedi sotto)
		Query queryEsiti = em.createQuery("SELECT esiti " +
				" FROM EsitiPendenza AS esiti " +
				" WHERE esiti.pendenzeCart.pk.e2emsgid = :e2emsgid" +
				" AND esiti.pendenzeCart.pk.senderid = :senderId " + 
				" AND esiti.pendenzeCart.pk.sendersys = :senderSys");		
		queryEsiti.setParameter("e2emsgid", msg);
		queryEsiti.setParameter("senderId", sendId);
		queryEsiti.setParameter("senderSys", sendSys);
		lins = (List) queryEsiti.getResultList();
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
	public String executeHtml(String msg, String sendId, String sendSys) {			
		
	String table = "";
	//connessione db
	EntityManager em = null;		
	try {				
		em = getManager();
		//calcolo la lista delle pendenze per cui gli esiti sono pronti
		//per essere creati e spediti
		List<EsitiPendenza> lins = listaEsitiPendenza(em, msg, sendId, sendSys);	
			 
		logger.info(this.getClass().getSimpleName() + "executeHtml, found " + lins.size());	
		table = "<br><br>";
		table += "<b>Lista esiti pendenza</b>";
		table += "<br>";			
		table+="<TABLE border=\"\1\">";
		table+="<TR>";
			table+="<TD>ID</TD>";							
			table+="<TD>SENDERID</TD>";
			table+="<TD>SENDERSYS</TD>";		
			table+="<TD>STATO</TD>";		
			table+="<TD>TS INSERIMENTO</TD>";
			table+="<TD>ESITO</TD>";
			table+="<TD>ID PENDENZA</TD>";
			table+="<TD>ID PENDENZA ENTE</TD>";
		table+="</TR>";				
		
		for (EsitiPendenza object : lins) {
			table+="<TR>";
				table+="<TD>";
					table+=(msg);
				table+="</TD>";
				table+="<TD>";
					table+=(sendId);
				table+="</TD>";
				table+="<TD>";
					table+=(sendSys);
				table+="</TD>";						
				table+="<TD>";
					table+=(object.getStato());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getTsInserimento());
				table+="</TD>";										
				table+="<TD>";
					table+=(object.getEsitoPendenza());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getIdPendenza());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getIdPendenzaEnte());
				table+="</TD>";										
			table+="</TR>";
			
			//stampo gli errori esiti
			Set<ErroriEsitiPendenza> erroriEsitiP = object.getErroriEsitiPendenzaCollection();
			Iterator iter1 = erroriEsitiP.iterator();
			while (iter1.hasNext()) {
				ErroriEsitiPendenza type = (ErroriEsitiPendenza) iter1.next();
				logger.info(" errore, codice = " + type.getCodice() + " / idErr = " + type.getIdErrore().trim() 
						+ " / dexErrore = " + type.getDescrizioneErrore() );	
				table+="<TR>";
					table+="<TD colspan=\"3\">&nbsp;";
						table+=(msg);
					table+="</TD>";
					table+="<TD>";
						table+=(type.getCodice());
					table+="</TD>";								
					table+="<TD>";
						table+=(type.getDescrizioneErrore());
					table+="</TD>";
					table+="<TD>";
						table+=(type.getTsInserimento());
					table+="</TD>";
					table+="<TD>";
						table+=(type.getIdPendenzaEnte());
					table+="</TD>";										
				table+="</TR>";				
			}
			
			
		}				
		table+="</TABLE>";
		
		} catch (Exception e) {
			logger.info(this.getClass().getSimpleName() + " ERROR EXECUTEHTML " +  e.getMessage());
		}
//		table+="</PRE>";
		return table;
	}


	@Override
	public MonitoringData executeApplicationTransaction(Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

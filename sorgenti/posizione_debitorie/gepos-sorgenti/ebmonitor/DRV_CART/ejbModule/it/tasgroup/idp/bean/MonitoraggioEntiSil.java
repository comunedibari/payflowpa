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

import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Interceptors(MonitoringInterceptor.class)
@Stateless
@Remote(StressTestCommandExecutor.class)
public class MonitoraggioEntiSil implements StressTestCommandExecutor {
   
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
	 * @param em
	 * @return
	 */
	private List<Object> listaEntiTributiConfigurati() {	
		 
		Query queryPendenze = manager.createNativeQuery(
				"SELECT JLTENTI.CD_ENTE, JLTENTR.ID_SYSTEM, JLTENTR.CD_TRB_ENTE,"+
					"JLTENTR.DE_TRB,JLTENTR.FL_INIZIATIVA,JLTENTR.FL_PREDETERM,"+
					"JLTENTR.STATO,FL_NOTIFICA_PAGAMENTO,FL_RICEVUTA_ANONIMO,FL_NASCOSTO_FE"+	
					" FROM JLTENTR"+
					" LEFT OUTER JOIN JLTENTI ON JLTENTR.ID_ENTE = JLTENTI.ID_ENTE "
					+ " ORDER BY JLTENTI.CD_ENTE, JLTENTR.ID_SYSTEM, JLTENTR.CD_TRB_ENTE");
			
		List lins = (List) queryPendenze.getResultList();
		return lins;
	}		
	
	/**
	 * 
	 * @param sil 
	 * @param em 
	 * @param em
	 * @return
	 */
	private List<Object> listaConfigurazioniNotifiche(String sil) {
		
		String addOn = "";
		if (!"".equals(sil) && sil.length()>1  ) {
			addOn = " where JLTENTR.ID_SYSTEM = '"+sil+"'";
		}
		
		Query queryNotifiche = manager.createNativeQuery(
				"select FREQ_NOTIFICA,FORMATO_NOTIFICA,CONSEGNA_NOTIFICA,TIPO_NOTIFICA,CFG_NOTIFICA_PAGAMENTO.CD_TRB_ENTE,ID_SYSTEM, CFG_NOTIFICA_PAGAMENTO.TS_INSERIMENTO, CFG_NOTIFICA_PAGAMENTO.TS_AGGIORNAMENTO, FL_NOTIFICA_PAGAMENTO " +
					" from CFG_NOTIFICA_PAGAMENTO " + 
					" left outer join JLTENTR ON CFG_NOTIFICA_PAGAMENTO.ID_ENTE = JLTENTR.ID_ENTE AND CFG_NOTIFICA_PAGAMENTO.CD_TRB_ENTE = JLTENTR.CD_TRB_ENTE " 
					+ addOn
					+ " order by ID_SYSTEM, CD_TRB_ENTE, TIPO_NOTIFICA");
		//fetch first 25 rows only
		List<Object> lins = null;
		lins = (List) queryNotifiche.getResultList();	
		logger.info(" FOUND LAST 25 NOTIFICHE = " + lins.size());
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
			return manager;
	}




	@Override
	public MonitoringData executeApplicationTransaction(String data) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String executeHtml() throws Exception  {			
		
	String table = "";
	
	try {				

		//calcolo la lista delle pendenze per cui gli esiti sono pronti
		//per essere creati e spediti
		List<Object> lins = listaEntiTributiConfigurati();	
			 
		logger.info(this.getClass().getSimpleName() + "executeHtml, found " + lins.size());	
		table = "<br><br>";
		table += "<b>Lista Configurazioni ente, ordinate in base al tempo di invio</b>";
		table += "<br>";			
		table+="<TABLE border=\"\1\">";
		table+="<TR>";
			table+="<TD>CDENTE</TD>";
			table+="<TD>ID_SYSTEM</TD>";
			table+="<TD>CD_TRB_ENTE</TD>";
			table+="<TD>DE_TRB</TD>";
			table+="<TD>FL_INIZIATIVA</TD>";
			table+="<TD>FL_PREDETERM</TD>";
			table+="<TD>STATO</TD>";
			table+="<TD>FL_NOTIFICA_PAGAMENTO</TD>";
			table+="<TD>FL_RICEVUTA_ANONIMO</TD>";
			table+="<TD>FL_NASCOSTO_FE</TD>";
		table+="</TR>\n";			
		
		int i = 0;
		Iterator iter = lins.iterator();
		while (iter.hasNext()) {
			Object[] object = (Object[]) iter.next();				
											
				table+="<TR>";
					table+="<TD>";table+=((String) object[0]);table+="</TD>";
					table+="<TD>";table+=((String) object[1]);table+="</TD>";
					table+="<TD>";table+=((String) object[2]);table+="</TD>";
					table+="<TD>";table+=((String) object[3]);table+="</TD>";
					table+="<TD>";table+=((String) object[4]);table+="</TD>";
					table+="<TD>";table+=((String) object[5]);table+="</TD>";
					table+="<TD>";table+=((String) object[6]);table+="</TD>";
					table+="<TD>";table+=((String) object[7]);table+="</TD>";
					table+="<TD>";table+=((String) object[8]);table+="</TD>";
					table+="<TD>";table+=((String) object[9]);table+="</TD>";
					table+="<TD>";
				table+="</TR>\n";
			
		}
		table += "</TABLE>";
		
		} catch (Exception e) {
			logger.info(this.getClass().getSimpleName() + " ERROR EXECUTEHTML " +  e.getMessage());
		}
//		table+="</PRE>";
		return table;
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String executeHtml(String Sil) throws Exception {
		String table = "";
		//connessione db
		EntityManager em = null;
		try {
			em = getManager();
			//calcolo la lista dei pagamenti eseguiti
			List lins = listaConfigurazioniNotifiche(Sil);

			logger.info(this.getClass().getSimpleName() + "executeHtml, found " + lins.size());
			table = "<br><br>";
			table += "<b>Configurazione Notifiche per Ente/Sil</b>";
			table += "<br>";
			table+="<TABLE border=\"\1\"><TR>";
				table +="<TD>FREQ_NOTIFICA,             </TD>";
				table +="<TD>FORMATO_NOTIFICA,             </TD>";
				table +="<TD>CONSEGNA_NOTIFICA,             </TD>";
				table +="<TD>TIPO_NOTIFICA			             </TD>";
				table +="<TD>CD_TRB_ENTE             </TD>";
				table +="<TD>ID_SYSTEM             </TD>";
				table +="<TD>TS_INSERT             </TD>";
				table +="<TD>TS_UPDATE              </TD>";
				table +="<TD>FL_NOTIFICA_PAGAMENTO             </TD>";
			table+="<TR>";
				
			Iterator iter = lins.iterator();
			int i = 0;
			while (iter.hasNext()) {
					Object[] object = (Object[]) iter.next();
				table+="<TR>";
									
					table+="<TD>";table+=((String) object[0]);table+="</TD>";
					table+="<TD>";table+=((String) object[1]);table+="</TD>";
					table+="<TD>";table+=((String) object[2]);table+="</TD>";
					table+="<TD>";table+=((String) object[3]);table+="</TD>";
					table+="<TD>";table+=((String) object[4]);table+="</TD>";
					table+="<TD>";table+=((String) object[5]);table+="</TD>";
					
					table+="<TD>";table+=((Timestamp) object[6]);table+="</TD>";
					table+="<TD>";table+=((Timestamp) object[7]);table+="</TD>";
					table+="<TD>";table+=((String) object[8]);table+="</TD>";					

				table+="</TR>";
			}
			table+="</TABLE>";

			} catch (Exception e) {
				e.printStackTrace();
				logger.info(this.getClass().getSimpleName() + " ERROR EXECUTEHTML " +  e.getMessage());
			}
//			table+="</PRE>";
			return table;
	}	


//	@Override
//	public MonitoringData executeApplicationTransaction(Object data) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
}

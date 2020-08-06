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

import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@Remote(ObjectCommandExecutor.class)
public class MonitoringDataStorage implements ObjectCommandExecutor {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;		

	private final Log logger = LogFactory.getLog(this.getClass());
	 
	/**
	 * String e2emsgid,
			String senderId, String senderSys, List<EsitoPendenza> esitoDaSpedire)
	 */
	public synchronized MonitoringData executeApplicationTransaction(Object data) {		
		return null;
	}

	@Override
	public String executeHtml(String msg, String sendId, String sendSys) {
		String table = "";
		//connessione db	
		try {				
			//calcolo la lista dei tempi di esecuzione dei processi di inserimento delle pendenze 
			List lins = listaProcessi(manager);	
				 
			logger.info(this.getClass().getSimpleName() + "executeHtml, found " + lins.size());	
			table = "<br><br>";
			table += "<b>Lista Processi eseguiti , ordinate in base al tempo di esecuzione</b>";
			table += "<br>";			
			table+="<TABLE border=\"\1\">";
			table+="<TR>";
			table+="<TD>Processo</TD>";
			
			table+="<TD>E2EMSGID</TD>";			
			table+="<TD>SENDERID</TD>";
			table+="<TD>SENDERSYS</TD>";
			table+="<TD>NUM PENDENZE</TD>";
			
			table+="<TD>DURATA_MONITOR</TD>";			
			table+="<TD>TEMPO ARRIVO</TD>";			
			
			table+="<TD>TS MIN ESITO PEND.</TD>";
			table+="<TD>TS MAX ESITO PEND.</TD>";
			table+="<TD>TS TOTAL INSERT</TD>";
							
			table+="</TR>";		
			
			Iterator iter = lins.iterator();
			int i = 0;
			while (iter.hasNext()) {					
					Object[] object = (Object[]) iter.next();								
//			for (Monitoring object : lins) {
				table+="<TR>";
					table+="<TD>";
						table+=((String) object[0]);
					table+="</TD>";														
					table+="<TD>";
					table+="<a href=\"invoke?operation=listaEsitiPendenza&objectname=it.tasgroup.idp.jmx%3Atype%3DMXBeanCommandExecutor&" +
							"value0=" + (String) object[1] +
							"&type0=java.lang.String" +
							"&value1=" + (String) object[2] +
							"&type1=java.lang.String" +
							"&value2=" + (String) object[3] +
							"&type2=java.lang.String\">"+(String) object[1]+"</a>";	
					table+="</TD>";							
					table+="<TD>";
						table+=((String) object[2]);
					table+="</TD>";
					table+="<TD>";
						table+=((String) object[3]);
					table+="</TD>";
					table+="<TD>";
						table+=((Integer) object[4]);
					table+="</TD>";
					table+="<TD>";
						table+=((Integer) object[5]);
					table+="</TD>";					
					table+="<TD>";
						table+=((Timestamp) object[6]);
					table+="</TD>";					
					table+="<TD>";
						table+=((Timestamp) object[7]);
					table+="</TD>";					
					table+="<TD>";
						table+=((Timestamp) object[8]);
					table+="</TD>";
					table+="<TD>";
						table+=((Integer) object[9]);
					table+="</TD>";							
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

	/**
	 * 
	 * @param em
	 * @return
	 */
	private List<MonitoringData> listaProcessi(EntityManager em) {
		Query queryPendenze = em.createNativeQuery(
			"SELECT  "
			+ "    varchar(PROCESSO),varchar(monitor.E2EMSGID),varchar(monitor.SENDERID),varchar(monitor.SENDERSYS), "
			 + "    max(NUM_RECORD) as num_pendenze, " 
			 + "    max(TEMPO_ESECUZIONE) AS DURATA_FLUSSO, "
			 + "    max(monitor.TIMESTAMP_esecuzione) as TS_INIZIO,"
			 + "    MIN(TS_INSERIMENTO) as MIN_TS_INSERT_ESITO,"
			 + "    MAX(TS_INSERIMENTO) as MAX_TS_INSERT_ESITO, "
			 + "    timestampdiff (2, char("
			 + "      timestamp(MAX(TS_INSERIMENTO))-"
			 + "      timestamp(max(monitor.TIMESTAMP_esecuzione)))) as TOTAL"
			
			 + "	FROM MONITORING as monitor  "
			
			 + "	left outer join  ESITI_PENDENZA as Esiti" 
			 + "    on (monitor.E2EMSGID=Esiti.E2EMSGID "
			 + "        and monitor.SENDERID=Esiti.SENDERID" 
			 + "        and monitor.SENDERSYS=Esiti.SENDERSYS)"
			
			 + " 	where monitor.E2EMSGID is not null and processo = 'it.tasgroup.idp.bean.DataStorageManager'"
			 + " group by "
			 + "    PROCESSO,monitor.E2EMSGID,monitor.SENDERID,monitor.SENDERSYS"
			 + " order by "
			 + "    max(monitor.TIMESTAMP_esecuzione) desc" 
			
			 + " fetch first 25 rows only");
		//fetch first 25 rows only
		List lins = (List) queryPendenze.getResultList();				   
		return lins;
	}

	/**
		 * TODO FIXARE QUESTO METODO !!!
		 * @return
		 */
		public EntityManager getManager() {
			//quando siamo su JBOSS ci vuole questa riga
//			return emf.createEntityManager();
			//quando siamo su TEST JUNIT ci vuole questa riga
	//			return manager;
			//PU JTA
			return manager;
		}


//	@Override
//	public MonitoringData executeApplicationTransaction(String data) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public MonitoringData executeApplicationTransaction() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
}

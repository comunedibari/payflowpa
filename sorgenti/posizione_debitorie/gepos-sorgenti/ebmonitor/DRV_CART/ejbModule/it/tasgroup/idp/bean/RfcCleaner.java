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

import it.tasgroup.idp.domain.messaggi.EsitiPendenza;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.FilterData;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Interceptors(MonitoringInterceptor.class)
@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
@Remote(ObjectCommandExecutor.class)
public class RfcCleaner implements CommandExecutor, CommandExecutorLocal, ObjectCommandExecutor {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;

	private final Log logger = LogFactory.getLog(this.getClass());


	/**
	 *
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction() {
		//monitoring data
		MonitoringData monData = new MonitoringData();

		try {


			logger.info(this.getClass().getSimpleName() );

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getSimpleName() + e.getMessage());
		}
		finally {
//			if (em!=null && em.isOpen()) em.close();
		}
		return monData;
	}


	@Override
	public MonitoringData executeApplicationTransaction(Object data) {
		//monitoring data
		MonitoringData monData = new MonitoringData();
		
		try {

			FilterData filter = (FilterData)data;
			
			Timestamp tsStart = filter.getTsStart();
			Timestamp tsEnd = filter.getTsEnd();			
						
			Query queryDelPendVero = manager.createNamedQuery("DeleteByDate");
			queryDelPendVero.setParameter("start", tsStart);
			queryDelPendVero.setParameter("end", tsEnd);					
			int num = queryDelPendVero.executeUpdate();
			logger.info(" Pend Cart Delete ? = " + num);	
		
			
			logger.info(this.getClass().getSimpleName() );

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getSimpleName() + e.getMessage());
		}
		finally {
//			if (em!=null && em.isOpen()) em.close();
		}
		return monData;			
		
	}	

	/**
	 * TODO FIXARE QUESTO METODO !!!
	 * @return
	 */
	public EntityManager getManager() {
		//quando siamo su JBOSS ci vuole questa riga
//			return emf.createEntityManager();
		//quando siamo su TEST JUNIT ci vuole questa riga
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

		return table;
	}

	@Override
	public String executeHtml(String dataStart, String dataEnd, String tipoOperazione) {
		
		String table = "";
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatSql = new SimpleDateFormat("yyyy-MM-dd");	
		Timestamp tsStart = new Timestamp(2012,1,1,0,0,0,0);
		Timestamp tsEnd = new Timestamp(2012,1,1,0,0,0,0);	
		String dateSqlStart = "";
		String dateSqlEnd = "";		
		try {
			Date dateStart = format.parse(dataStart);
			Date dateEnd = format.parse(dataEnd);
			tsStart = new Timestamp(dateStart.getTime());
			tsEnd = new Timestamp(dateEnd.getTime());
			
			 dateSqlStart = formatSql.format(dateStart);
			 dateSqlEnd = formatSql.format(dateEnd);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		if (dimBlocco == null || "".equalsIgnoreCase(dimBlocco))  {
//			dimBlocco = "50";
//		}
		
		logger.info(this.getClass().getSimpleName() + "executeHtml, eseguo operazione da " + 
				dataStart + " a " + dataEnd + " , dimBlocco/operazione = " + tipoOperazione);		
		
		if ("VIEW".equals(tipoOperazione)) {
			table = executeView("50", table, dateSqlStart, dateSqlEnd);
		} else if ("DELETE".equals(tipoOperazione)) {
			table = executeDelete("50", table, tsStart, tsEnd);
		}
		
		return table;
	}


	/**
	 * 
	 * @param dimBlocco
	 * @param table
	 * @param dateSqlStart
	 * @param dateSqlEnd
	 * @return
	 */
	private String executeView(String dimBlocco, String table,
			String dateSqlStart, String dateSqlEnd) {
		//connessione db
		EntityManager em = null;
		try {
			em = getManager();
			//calcolo la lista delle pendenze per cui gli esiti sono pronti
			//per essere creati e spediti
			List<EsitiPendenza> lins = listaPendAnnullate(dateSqlStart,dateSqlEnd,dimBlocco);

			logger.info(this.getClass().getSimpleName() + "executeHtml, found " + lins.size());
			table = "<br><br>";
			table += "<b>Lista Rfc127 </b>";
			table += "<br>";
			table+="<TABLE border=\"\1\">";
			table+="<TR>";
				table+="<TD>E2EMSGID</TD><TD> SENDERID</TD><TD> SENDERSYS</TD><TD> NUM_PENDENZE</TD><TD> STATOPEND</TD><TD> TIMESTAMP_RICEZIONE</TD><TD> TIMESTAMP_INVIO</TD><TD> STATOESITO</TD><TD> ESITO_PENDENZA</TD><TD> DESCRIZIONE_ESITO</TD><TD> DESCRIZIONE_ERRORE</TD><TD> STATOIDP</TD>";
			table+="</TR>";
			
			Iterator iter = lins.iterator();
			logger.info(" PEND ANNULLATE = " + lins.size());
			while (iter.hasNext()) {

				Object[] object = (Object[]) iter.next();

				table+="<TR>";
					table+="<TD>";table+=(object[0]);table+="</TD>";
					table+="<TD>";table+=(object[1]);table+="</TD>";
					table+="<TD>";table+=(object[2]);table+="</TD>";
					table+="<TD>";table+=(object[3]);table+="</TD>";
					table+="<TD>";table+=(object[4]);table+="</TD>";
					table+="<TD>";table+=(object[5]);table+="</TD>";
					table+="<TD>";table+=(object[6]);table+="</TD>";
					table+="<TD>";table+=(object[7]);table+="</TD>";
					table+="<TD>";table+=(object[8]);table+="</TD>";
					table+="<TD>";table+=(object[9]);table+="</TD>";
					table+="<TD>";table+=(object[10]);table+="</TD>";
					table+="<TD>";table+=(object[11]);table+="</TD>";						
				table+="</TR>";
			}
			table+="</TABLE>";

			} catch (Exception e) {
				logger.info(this.getClass().getSimpleName() + " ERROR EXECUTEHTML " +  e.getMessage());
				e.printStackTrace();
			}
		return table;
	}		
	
	/**
	 * 
	 * @param dimBlocco
	 * @param table
	 * @param tsStart
	 * @param tsEnd
	 * @return
	 */
	private String executeDelete(String dimBlocco, String table,
			Timestamp tsStart, Timestamp tsEnd) {
		//connessione db
		EntityManager em = null;
		try {			
			
			
			FilterData filter = new FilterData();
			filter.setTsStart(tsStart);
			filter.setTsEnd(tsEnd);
			this.executeApplicationTransaction(filter);			
			
//			em = getManager();
//			EntityTransaction tx = em.getTransaction();
//			tx.begin();
						
//			Query queryDelPendVero = em.createNamedQuery("DeleteByDate");
//			queryDelPendVero.setParameter("start", tsStart);
//			queryDelPendVero.setParameter("end", tsEnd);					
//			int num = queryDelPendVero.executeUpdate();
//			logger.info(" Pend Cart Delete ? = " + num);	
//			
//			tx.commit();

			table = "<br><br>";
			table += "<b>Lista Rfc127 </b>";
			table += "<br>";
			table+="<TABLE border=\"\1\">";
			table+="<TR>";
				table+="<TD>RFC127 CANCELLATE</TD>";
			table+="</TR>";
			
//			logger.info(" RFC127 CANCELLATE = " + num);

				table+="<TR>";
//					table+="<TD>";table+=num;table+="</TD>";							
				table+="</TR>";
			table+="</TABLE>";

			} catch (Exception e) {
				logger.info(this.getClass().getSimpleName() + " ERROR EXECUTEHTML " +  e.getMessage());
				e.printStackTrace();
			}
		return table;
	}			
	
	/**
	 * 
	 * @param dimBlocco 
	 * @param dimBlocco2 
	 * @param dateSqlEnd 
	 * @return
	 */
	private List listaPendAnnullate(String dateSqlStart, String dateSqlEnd, String dimBlocco) {
		logger.info(" TEST PEND.CART CON NATIVE SQL LEFT JOIN ");
		String sql = 
				"select                                                                                                                                                                   "+
						"  pcart.e2emsgid,pcart.senderid,ecart.sendersys,                                                                                                                         "+
						"  pcart.num_pendenze, pcart.stato as statoPend, pcart.timestamp_ricezione, ecart.timestamp_invio, ecart.stato as statoEsito,                                             "+
						"  esiti.esito_pendenza,esiti.descrizione_esito,errori.descrizione_errore, errori2.stato as statoIdp                                                                      "+
						"from PENDENZE_CART as pcart                                                                                                                                              "+
						"left outer join ESITI_CART as ecart on (pcart.e2emsgid = ecart.e2emsgid and pcart.senderid = ecart.senderid and pcart.sendersys = ecart.sendersys)                       "+
						"left outer join ESITI_PENDENZA as esiti on (esiti.e2emsgid = ecart.e2emsgid and esiti.senderid = ecart.senderid and esiti.sendersys = ecart.sendersys)                   "+
						"left outer join ERRORI_ESITI_PENDENZA as errori on (esiti.id_esito_pendenza = errori.id_esito_pendenza)                                                                  "+
						"left outer join ERRORI_IDP as errori2 on (pcart.e2emsgid = errori2.e2emsgid and pcart.senderid = errori2.senderid and pcart.sendersys = errori2.sendersys)               "+
						"where ecart.stato = 'INVIATO' and esiti.esito_pendenza = 'OK'                                                                                                            "+
						" and pcart.timestamp_ricezione >= '"+dateSqlStart+"-00.00.00' and pcart.timestamp_ricezione <= '"+dateSqlEnd+"-00.00.00' 														  "+
						"order by pcart.timestamp_ricezione asc                                                                                                                                   "+
						" fetch first " + dimBlocco + " rows only ";
		Query query = getManager().createNativeQuery(sql);
		List resultList = (List) query.getResultList();
		return resultList;
	}

}

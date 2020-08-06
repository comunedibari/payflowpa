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
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Interceptors(MonitoringInterceptor.class)
@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
@Remote(ObjectCommandExecutor.class)
public class PendenzeCleaner implements CommandExecutor, CommandExecutorLocal, ObjectCommandExecutor {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;

	private final Log logger = LogFactory.getLog(this.getClass());


	String filtroListaEsiti = null;
	String e2emsgid = null;
	String senderId = null;
	String senderSys = null;

	/**
	 *
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction() {
		//monitoring data
		MonitoringData monData = new MonitoringData();

		//connessione db
		EntityManager em = null;
		try {

			em = getManager();

			logger.info(this.getClass().getSimpleName()  );

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getSimpleName() + " ERROR  = " + e.getMessage());
		}
		finally {
			if (em!=null && em.isOpen()) em.close();
		}
		return monData;
	}


	@Override
	public MonitoringData executeApplicationTransaction(Object data) {
		// TODO Auto-generated method stub

		return null;
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
	public String executeHtml(String dataStart, String dataEnd, String dimBlocco) {
		String table = "";
		if (dimBlocco == null || "".equalsIgnoreCase(dimBlocco))  {
			dimBlocco = "50";
		}
		
		String dateSqlStartExt = formatDate(dataStart);
		String dateSqlEndExt = formatDate(dataEnd);
		
		//connessione db
		EntityManager em = null;
		try {
			em = getManager();
			//calcolo la lista delle pendenze per cui gli esiti sono pronti
			//per essere creati e spediti
			List<EsitiPendenza> lins = listaPendAnnullate(dimBlocco, dateSqlStartExt, dateSqlEndExt);

			logger.info(this.getClass().getSimpleName() + "executeHtml, found " + lins.size());
			table = "<br><br>";
			table += "<b>Lista pendenze annullate</b>";
			table += "<br>";
			table+="<TABLE border=\"\1\">";
			table+="<TR>";
				table+="<TD>IBANBOZZE</TD><TD> IMPORTOBOZZE</TD><TD> DATAPAGAMENTO</TD><TD> DATACREAZDOC</TD><TD> DATAANNULLDOCS</TD><TD> ID_PENDENZA</TD><TD> ID_PENDENZAENTE</TD><TD> ID_ENTE</TD><TD> CD_TRB_ENTE</TD><TD> ID_CONDIZIONE</TD><TD> TS_INSERIMENTO</TD><TD> TS_AGGIORNAMENTO</TD><TD> TS_ANNULLAMENTO</TD><TD> ID_PAGAMENTO</TD><TD> CO_DESTINATARIO</TD><TD> ID_VOCE</TD><TD> STATO_RIGA_PEND</TD><TD> TARGA_MULTA</TD><TD> TARGA_BOLLO</TD><TD> STATO_RIGA_COND</TD><TD> STATO_RIGA_DESTINAT</TD><TD> STATO_RIGA_ALLEGATI</TD><TD> STATO_RIGA_VOCI</TD><TD> TIPO_TRIBUTO<TD>";
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
					table+="<TD>";table+=(object[12]);table+="</TD>";
					table+="<TD>";table+=(object[13]);table+="</TD>";
					table+="<TD>";table+=(object[14]);table+="</TD>";
					table+="<TD>";table+=(object[15]);table+="</TD>";				
					table+="<TD>";table+=(object[16]);table+="</TD>";
					table+="<TD>";table+=(object[17]);table+="</TD>";
					table+="<TD>";table+=(object[18]);table+="</TD>";
					table+="<TD>";table+=(object[19]);table+="</TD>";
					table+="<TD>";table+=(object[20]);table+="</TD>";
					table+="<TD>";table+=(object[21]);table+="</TD>";
					table+="<TD>";table+=(object[22]);table+="</TD>";
					table+="<TD>";table+=(object[23]);table+="</TD>";								
				table+="</TR>";
			}
			table+="</TABLE>";

			} catch (Exception e) {
				logger.info(this.getClass().getSimpleName() + " ERROR EXECUTEHTML " +  e.getMessage());
				e.printStackTrace();
			}
//			table+="</PRE>";
			return table;
	}


	/**
	 * 
	 * @param dataStart
	 * @return
	 */
	private String formatDate(String dataStart) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatSql = new SimpleDateFormat("yyyy-MM-dd");	
		String dateSqlStart = "";
		try {
			Date dateStart = format.parse(dataStart);
			 dateSqlStart = formatSql.format(dateStart);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return dateSqlStart;
	}		
	
	/**
	 * 
	 * @param dimBlocco 
	 * @return
	 */
	private List listaPendAnnullate(String dimBlocco, String minData, String maxData) {
		logger.info(" TEST PEND.ANNULLATE CON NATIVE SQL LEFT JOIN ");
		String sql = 
				"SELECT                                                                                                                                                          "+
						"  BOZZE.IBAN_BENEFICIARIO AS IBANBOZZE,BOZZE.IMPORTO AS IMPORTOBOZZE,                                                                                           "+
						"  PAGA.TS_INSERIMENTO AS DATAPAGAMENTO,                                                                                                                         "+
						"  DOCS.TS_INSERIMENTO AS DATACREAZDOC,DOCS.TS_ANNULLAMENTO AS DATAANNULLDOCS,                                                                                   "+			
						"  PENDENZE.ID_PENDENZA,PENDENZE.ID_PENDENZAENTE,PENDENZE.ID_ENTE,PENDENZE.CD_TRB_ENTE,JLTCOPD.ID_CONDIZIONE,                                                             "+
						"  PENDENZE.TS_INSERIMENTO, PENDENZE.TS_AGGIORNAMENTO,PENDENZE.TS_ANNULLAMENTO,                                                                                  "+
						"  JLTCOPD.ID_PAGAMENTO, JLTDEPD.CO_DESTINATARIO,                                                                                                                "+
						"  JLTVOPD.ID_VOCE,                                                                                                                                              "+
						"  PENDENZE.ST_RIGA AS STATO_RIGA_PEND, MULTA.TARGA AS TARGA_MULTA, BOLLO.TARGA AS TARGA_BOLLO,                                                                  "+
						"  JLTCOPD.ST_RIGA AS STATO_RIGA_COND, JLTDEPD.ST_RIGA AS STATO_RIGA_DESTINAT, JLTALPE.ST_RIGA AS STATO_RIGA_ALLEGATI, JLTVOPD.ST_RIGA AS STATO_RIGA_VOCI,       "+
						"  TRIBUTO_STRUTTURATO.TIPO_TRIBUTO                                                                                                                              "+
						"FROM PENDENZE                                                                                                                                                   "+
						"LEFT OUTER JOIN TRIBUTO_STRUTTURATO ON (PENDENZE.ID_PENDENZA = TRIBUTO_STRUTTURATO.ID_PENDENZA)                                                                 "+
						"LEFT OUTER JOIN T_BOLLO_AUTO AS BOLLO ON (BOLLO.ID = TRIBUTO_STRUTTURATO.ID)                                                                                    "+
						"LEFT OUTER JOIN T_MULTA AS MULTA ON (MULTA.ID = TRIBUTO_STRUTTURATO.ID)                                                                                         "+
						"LEFT OUTER JOIN JLTCOPD ON (PENDENZE.ID_PENDENZA = JLTCOPD.ID_PENDENZA)                                                                                         "+
						"LEFT OUTER JOIN JLTVOPD ON (JLTVOPD.ID_CONDIZIONE = JLTCOPD.ID_CONDIZIONE)                                                                                      "+
						"LEFT OUTER JOIN JLTDEPD ON (JLTDEPD.ID_PENDENZA = PENDENZE.ID_PENDENZA)                                                                                         "+
						"LEFT OUTER JOIN JLTALPE ON (JLTALPE.ID_PENDENZA = PENDENZE.ID_PENDENZA)                                                                                         "+
						"LEFT OUTER JOIN BOZZE_BONIFICI_RIACCREDITO AS BOZZE ON (BOZZE.ID_CONDIZIONE = JLTCOPD.ID_CONDIZIONE)                                                            "+
						"LEFT OUTER JOIN PAGAMENTI AS PAGA ON (PAGA.ID_CONDIZIONE = JLTCOPD.ID_CONDIZIONE)                                                                               "+
						"LEFT OUTER JOIN CONDIZIONI_DOCUMENTO AS DOCS ON (DOCS.ID_CONDIZIONE = JLTCOPD.ID_CONDIZIONE)                                                                    "+
						"WHERE PENDENZE.ST_RIGA = 'A' AND PENDENZE.TS_ANNULLAMENTO IS NOT NULL                                                                                           "+
						" AND PENDENZE.TS_INSERIMENTO >= '"+minData+"-00.00.00' "+
						" AND PENDENZE.TS_INSERIMENTO <= '"+maxData+"-00.00.00' "+						
//						"and id_tributo_strutturato is null 																															 "+
//						"and docs.TS_ANNULLAMENTO is not null and  paga.TS_INSERIMENTO is null                                                                                           "+							
						"ORDER BY PENDENZE.TS_ANNULLAMENTO DESC                                                                                                                          "+
						" FETCH FIRST " + dimBlocco + " ROWS ONLY ";
		Query query = getManager().createNativeQuery(sql);
		List resultList = (List) query.getResultList();
		return resultList;
	}

}

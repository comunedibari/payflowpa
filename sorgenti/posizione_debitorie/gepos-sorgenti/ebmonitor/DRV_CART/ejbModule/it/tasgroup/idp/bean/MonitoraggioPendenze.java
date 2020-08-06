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

import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartMessage;
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
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

@Interceptors(MonitoringInterceptor.class)
@Stateless
//@TransactionManagement(TransactionManagementType.BEAN) 
@Remote(StressTestCommandExecutor.class)
public class MonitoraggioPendenze implements StressTestCommandExecutor {

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
	private List<PendenzeCart> listaPendenze(EntityManager em, String e2emsgid) {
		String filtro = " WHERE pendenze.e2emsgid = '" + e2emsgid +"'";
		Query queryPendenze = em.createNativeQuery(
				"SELECT * " +
				" FROM PENDENZE_CART as pendenze " 
				+ (e2emsgid!=null ? filtro  : "") 
				+ " order by pendenze.TIMESTAMP_RICEZIONE desc "
				+ " fetch first 25 rows only",PendenzeCart.class);
		//fetch first 25 rows only
		List<PendenzeCart> lins = null;
		lins = (List) queryPendenze.getResultList();
		
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
		
		//test CriteriaBuilder
		List<PendenzeCartMessage> lins = listaPendenzeAdvanced(em,null);							
		
		//calcolo la lista delle pendenze per cui gli esiti sono pronti
		//per essere creati e spediti
//		List<PendenzeCart> lins = listaPendenze(em,null);

		logger.info(this.getClass().getSimpleName() + "executeHtml, found " + lins.size());
		table = "<br><br>";
		table += "<b>Lista pendenze (Cart) , ordinate in base al tempo di arrivo</b>";
		table += "<br>";
		table+="<TABLE border=\"\1\">";
		table+="<TR>";
			table+="<TD>ID</TD>";
			table+="<TD>SENDERID</TD>";
			table+="<TD>SENDERSYS</TD>";
			table+="<TD>STATO</TD>";
			table+="<TD>TS RICEZIONE</TD>";
			table+="<TD>NUM PEND.</TD>";
			table+="<TD>TIPO MEX</TD>";
			table+="<TD>TIPO OPERAZ</TD>";
			table+="<TD>TIPO TRIB</TD>";
			table+="<TD>XML</TD>";
		table+="</TR>";

		int i = 0;
		for (PendenzeCartMessage object : lins) {
			table+="<TR>";
				table+="<TD>";
				table+="<a href=\"invoke?operation=listaEsitiPendenza&objectname=it.tasgroup.idp.jmx%3Atype%3DMXBeanCommandExecutor&" +
						"value0=" + object.getPk().getE2emsgid() +
						"&type0=java.lang.String" +
						"&value1=" + object.getPk().getSenderid() +
						"&type1=java.lang.String" +
						"&value2=" + object.getPk().getSendersys() +
						"&type2=java.lang.String\">"+object.getPk().getE2emsgid()+"</a>";
				table+="</TD>";
				table+="<TD>";
					table+=(object.getPk().getSenderid());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getPk().getSendersys());
				table+="</TD>";
				table+="<TD>";
					if (StatoEnum.RISPOSTA_INVIATA.equals(object.getStato().trim())
//						|| StatoEnum.DA_REINVIARE.equals(object.getStato().trim())
//						|| StatoEnum.RIFIUTATO.equals(object.getStato().trim())
						|| StatoEnum.RISPOSTA_INVIATA_WS.equals(object.getStato().trim()) ) {
						table+="<a href=\"invoke?operation=dettaglioEsitoCart&objectname=it.tasgroup.idp.jmx%3Atype%3DMXBeanCommandExecutor&" +
						"value0=" + object.getPk().getE2emsgid() +
						"&type0=java.lang.String" +
						"&value1=" + object.getPk().getSenderid() +
						"&type1=java.lang.String" +
						"&value2=" + object.getPk().getSendersys() +
						"&type2=java.lang.String\">"+object.getStato()+"</a>";
					} else {
						table+=(object.getStato());
					}
				table+="</TD>";
				table+="<TD>";
					table+=(object.getTimestampRicezione());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getNumPendenze());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getTipoMessaggio());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getTipoOperazione());
				table+="</TD>";				
				table+="<TD>";
					table+=(object.getTipoTributo());
				table+="</TD>";
				table+="<TD>";
					table+="<span title=\"Click here to display XML\" onclick=\"openCloseDiv( 'DIVNAME"+i+"' )\">";
					table+="Open/Close</span>";
					table+="<div id=\"DIVNAME"+i+"\"  style=\"display: none;\">";
					i++;
					table+=(StringEscapeUtils.escapeHtml( new String(object.getMessaggioXml()).toString()) );
				table+="</div>";
			table+="</TD>";
			table+="</TR>";
		}
		table+="</TABLE>";
		
		
		//lista pendenze (pro-pagamenti spontanei)
//		logger.info(" leggo ultime 25 pendenze (table pendenze)");
//		Query queryPendenze = em.createNativeQuery(
//				"SELECT * " +
//				" FROM pendenze as pendenze "  
//				+ " order by pendenze.id_pendenza desc "
//				+ " fetch first 25 rows only",Pendenze.class);
//		List<Pendenze> linsP = null; 
//		linsP = (List) queryPendenze.getResultList();			
//
//		logger.info("trovati pendenze , numtotale = " + linsP.size());	
//		
//		table += "<br><br><b>Lista pendenze (dettaglio) , ordinate in base al tempo di arrivo</b>";
//		table += "<br>";		
//		
//		String table2="<table border=\"\1\">";
//		table2+="<TR>";
//		table2+="<TD>ID</TD>";
//		table2+="<TD>ID_ENTE</TD>";
//		table2+="<TD>CD_TRB_ENTE</TD>";
//		table2+="<TD>ID PEND. ENTE</TD>";
//		table2+="<TD>ID SYSTEM</TD>";
//		table2+="<TD>IMP TOTALE</TD>";
//		table2+="<TD>CAUSALE</TD>";
//		table2+="<TD>ST_RIGA</TD>";
//		table2+="<TD>TS_INSERT</TD>";
//		table2+="<TD>STRUTTURATO</TD>";
//		table2+="</TR>";	
//		for (Pendenze object : linsP) {
//			table2+="<TR>";
//				table2+="<TD>";
//				table2+=object.getIdPendenza()+"</a>";
//				table2+="</TD>";
//				table2+="<TD>";
//					table2+=(object.getIdEnte());
//				table2+="</TD>";
//				table2+="<TD>";
//					table2+=(object.getCdTrbEnte());
//				table2+="</TD>";
//				table2+="<TD>";
//					table2+=(object.getIdPendenzaente());
//				table2+="</TD>";
//				table2+="<TD>";
//					table2+=(object.getIdSystem());
//				table2+="</TD>";
//				table2+="<TD>";
//					table2+=(object.getImTotale());
//				table2+="</TD>";
//				table2+="<TD>";
//					table2+=(object.getDeCausale());
//				table2+="</TD>";
//				table2+="<TD>";
//					table2+=(object.getStRiga());
//				table2+="</TD>";				
//				table2+="<TD>";
//					table2+=(object.getTsInserimento());
//				table2+="</TD>";
//				table2+="<TD>";
//					table2+=(object.getIdTributoStrutturato());
//				table2+="</div>";
//			table2+="</TD>";
//			table2+="</TR>";
//		}
//		table2+="</table>";		
//		
//		table += table2;
		

		} catch (Exception e) {
			logger.info(this.getClass().getSimpleName() + " ERROR EXECUTEHTML " +  e.getMessage());
		}
//		table+="</PRE>";
		return table;
	}


	/**
	 * 
	 * @param em
	 * @param object
	 * @return 
	 */
	private List<PendenzeCartMessage> listaPendenzeAdvanced(EntityManager em, String filtro) {
		List<PendenzeCartMessage> pend;
		org.hibernate.Session session = (org.hibernate.Session) em.getDelegate();		
		Criteria critPend = session.createCriteria(PendenzeCartMessage.class);
		if (filtro!=null) {
			critPend.add(Restrictions.eq("pk.e2emsgid", filtro));	
		}		
		critPend.addOrder(Order.desc("timestampRicezione"));
		pend = critPend.setMaxResults(25).list();
		logger.info(" FOUND CRITERIA = " + pend.size());
		
		//notifiche
//		List<NotificheCart> not;
//		Criteria critNot = session.createCriteria(NotificheCart.class);	
//		critNot.addOrder(Order.desc("timestampInvio"));
//		not = critNot.setMaxResults(25).list();	
//		logger.info(" FOUND CRITERIA NOTIFICHE = " + not.size());		
		
		return pend;
	}


	@Override
	public String executeHtml(String e2emsgid) throws Exception {
		String table = "";
		//connessione db
		EntityManager em = null;
		try {
			em = getManager();
			
			//new method
			List<PendenzeCartMessage> lins = listaPendenzeAdvanced(em,e2emsgid);
			//calcolo la lista delle pendenze per cui gli esiti sono pronti
			//per essere creati e spediti
//			List<PendenzeCart> lins = listaPendenze(em,e2emsgid);

			logger.info(this.getClass().getSimpleName() + "executeHtml, found " + lins.size());
			table = "<br><br>";
			table += "<b>Dettaglio Allineamento Pendenze , E2EMSGID = </b>" + e2emsgid;
			table += "<br>";
			table+="<TABLE border=\"\1\">";
			table+="<TR>";
				table+="<TD>ID</TD>";
				table+="<TD>SENDERID</TD>";
				table+="<TD>SENDERSYS</TD>";
				table+="<TD>STATO</TD>";
				table+="<TD>TS RICEZIONE</TD>";
				table+="<TD>NUM PEND.</TD>";
				table+="<TD>TIPO MEX</TD>";
				table+="<TD>TIPO TRIB</TD>";
				table+="<TD>XML</TD>";
			table+="</TR>";

			int i = 0;
			for (PendenzeCartMessage object : lins) {
				table+="<TR>";
					table+="<TD>";
					table+="<a href=\"invoke?operation=listaEsitiPendenza&objectname=it.tasgroup.idp.jmx%3Atype%3DMXBeanCommandExecutor&" +
							"value0=" + object.getPk().getE2emsgid() +
							"&type0=java.lang.String" +
							"&value1=" + object.getPk().getSenderid() +
							"&type1=java.lang.String" +
							"&value2=" + object.getPk().getSendersys() +
							"&type2=java.lang.String\">"+object.getPk().getE2emsgid()+"</a>";
					table+="</TD>";
					table+="<TD>";
						table+=(object.getPk().getSenderid());
					table+="</TD>";
					table+="<TD>";
						table+=(object.getPk().getSendersys());
					table+="</TD>";
					table+="<TD>";
						if (StatoEnum.RISPOSTA_INVIATA.equals(object.getStato().trim())
//							|| StatoEnum.DA_REINVIARE.equals(object.getStato().trim())
//							|| StatoEnum.RIFIUTATO.equals(object.getStato().trim())
							) {
							table+="<a href=\"invoke?operation=dettaglioEsitoCart&objectname=it.tasgroup.idp.jmx%3Atype%3DMXBeanCommandExecutor&" +
							"value0=" + object.getPk().getE2emsgid() +
							"&type0=java.lang.String" +
							"&value1=" + object.getPk().getSenderid() +
							"&type1=java.lang.String" +
							"&value2=" + object.getPk().getSendersys() +
							"&type2=java.lang.String\">"+object.getStato()+"</a>";
						} else {
							table+=(object.getStato());
						}
					table+="</TD>";
					table+="<TD>";
						table+=(object.getTimestampRicezione());
					table+="</TD>";
					table+="<TD>";
						table+=(object.getNumPendenze());
					table+="</TD>";
					table+="<TD>";
						table+=(object.getTipoMessaggio());
					table+="</TD>";
					table+="<TD>";
						table+=(object.getTipoTributo());
					table+="</TD>";
					table+="<TD>";
						table+="<span title=\"Click here to display XML\" onclick=\"openCloseDiv( 'DIVNAME"+i+"' )\">";
						table+="Open/Close</span>";
						table+="<div id=\"DIVNAME"+i+"\"  style=\"display: none;\">";
						i++;
						table+=(StringEscapeUtils.escapeHtml( new String(object.getMessaggioXml()).toString()) );
					table+="</div>";
				table+="</TD>";
				table+="</TR>";
			}
			table+="</TABLE>";

			} catch (Exception e) {
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

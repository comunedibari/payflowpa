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

import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.math.BigDecimal;
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

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

@Interceptors(MonitoringInterceptor.class)
@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
@Remote(StressTestCommandExecutor.class)
public class MonitoraggioNotifiche implements StressTestCommandExecutor {
   
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
	private List<NotificheCart> listaNotifiche() {
		
		List<NotificheCart> pend;
		org.hibernate.Session session = (org.hibernate.Session) manager.getDelegate();		
		Criteria critPend = session.createCriteria(NotificheCart.class);	
		critPend.addOrder(Order.desc("timestampInvio"));
		pend = critPend.setMaxResults(25).list();	
		logger.info(" FOUND CRITERIA = " + pend.size());		
		return pend;
		
//		Query queryNotifiche = manager.createNativeQuery(
//				"SELECT * " +
//				" FROM notifiche_cart  notifiche " 
//				+ " order by notifiche.TIMESTAMP_INVIO desc "
//				+ " fetch first 25 rows only",NotificheCart.class);
//		//fetch first 25 rows only
//		List<NotificheCart> lins = null;
//		lins = (List) queryNotifiche.getResultList();		
//		return lins;
	}		
	
	/**
	 * 
	 * @param em 
	 * @param em
	 * @return
	 */
	private List<NotificheCart> listaUltime25Notifiche() {
		Query queryNotifiche = manager.createNativeQuery(
				"SELECT * " +
				" FROM NOTIFICHE_CART  notifiche " 
				+ " order by notifiche.TIMESTAMP_INVIO desc "
				+ " fetch first 25 rows only",NotificheCart.class);
		//fetch first 25 rows only
		List<NotificheCart> lins = null;
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
		List<NotificheCart> lins = listaNotifiche();	
			 
		logger.info("executeHtml, found " + lins.size());	
		table = "<br><br>";
		table += "<b>Lista notifiche , ordinate in base al tempo di invio</b>";
		table += "<br>";			
		table+="<TABLE border=\"\1\">";
		table+="<TR>";
			table+="<TD>ID</TD>";							
			table+="<TD>SENDERID</TD>";
			table+="<TD>SENDERSYS</TD>";		
			table+="<TD>STATO</TD>";	
			table+="<TD>TIPO. NOTIFICA</TD>";
			table+="<TD>CD TRB.ENTE</TD>";	
			table+="<TD>TS INVIO</TD>";
			table+="<TD>XML</TD>";				
		table+="</TR>";				
		
		int i = 0;
		for (NotificheCart object : lins) {
			table+="<TR>";
				table+="<TD>";
				table+=object.getId().getE2emsgid() + "-" +
						 object.getId().getReceiverid() + "-"
						+ object.getId().getReceiversys();	
				table+="</TD>";							
				table+="<TD>";
					table+=(object.getId().getReceiverid());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getId().getReceiversys());
				table+="</TD>";						
				table+="<TD>";
					table+=object.getStato();
				table+="</TD>";
				
				String mex = new String(object.getNotificaXml()).toString();
//				String[] spl = mex.split("Esito>");
				table+="<TD>";
						table+=object.getTipoNotifica();
				table+="</TD>";
				table+="<TD>";
					table+=object.getCdTrbEnte();
				table+="</TD>";				
				
				table+="<TD>";
					table+=(object.getTimestampInvio());
				table+="</TD>";									
				
						
				table+="<TD>";
					table+="<span title=\"Click here to display XML\" onclick=\"openCloseDiv( 'DIVNAME"+i+"' )\">";
					table+="Open/Close</span>";
					table+="<div id=\"DIVNAME"+i+"\"  style=\"display: none;\">";
					i++;							
					table+=( StringEscapeUtils.escapeHtml( mex  ));
				table+="</div>";	
			table+="</TD>";					
			table+="</TR>";				
		}				
		table+="</TABLE>";
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(" ERROR EXECUTEHTML " +  e.getMessage());
		}
//		table+="</PRE>";
		return table;
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String executeHtml(String idTx) throws Exception {
		String table = "";
		//connessione db
		EntityManager em = null;
		try {
			em = getManager();
			//calcolo la lista dei pagamenti eseguiti
			List lins = listaPagamenti(idTx);

			logger.info("executeHtml, found " + lins.size());
			table = "<br><br>";
			table += "<b>Tracking Pagamenti, da Inserimento Pendenza fino a Riaccredito Ente</b>";
			table += "<br>";
			table+="<TABLE border=\"\1\">";
			table+="<TR>";
				table+="<TD>COND - PAGA</TD>";
				table+="<TD>PAGA - DIST</TD>";
				table+="<TD>DIST - NOT1</TD>";
				table+="<TD>NOT1 - BOZZ</TD>";
				table+="<TD>BOZZ - NOT2</TD>";
				table+="<TD>NOT2 - BONI</TD>";			
				table+="<TD>BONI - FLUSSO EP</TD>";			
				table+="<TD>FLUSSO EP - EP</TD>";
				table+="<TD>EP - NOT3</TD>";	
				
				table +="<TD>CD_TRB_ENTE,               </TD>";
				table +="<TD>STATO_NOTIFICA 			</TD>";
				table +="<TD>IM_PAGATO,					</TD>";
				table +="<TD>COD_TRANSAZ	            </TD>";
				table +="<TD>COD 				        </TD>";
				table +="<TD>STRUMENTO	                </TD>";
				
				table +="<TD>TSINSERTCOND,              </TD>";
				table +="<TD>INSERTPAGA,                </TD>";
				table +="<TD>INSERTDIST,                </TD>";
				table +="<TD>NOTIFICA_ESEGUITO,         </TD>";
				table +="<TD>TSINSERTBOZZ,              </TD>";
				table +="<TD>NOTIFICA_REGOLATO,         </TD>";
				table +="<TD>TSINSERTBONI,              </TD>";
				table +="<TD>INSERTRCT,                 </TD>";
				table +="<TD>TSINSERTESITOEP,           </TD>";
				table +="<TD>NOTIFICA_INCASSO,          </TD>";
				
				table +="<TD>BONI.IMPORTO,              </TD>";
				table +="<TD>TIPO_ESITO                 </TD>";
				table +="<TD>ID_PAG_COND_ENTE           </TD>";
				table +="<TD>CODICE_RIFERIMENTO         </TD>";
			
			table+="</TR>";

			Iterator iter = lins.iterator();
			int i = 0;
			while (iter.hasNext()) {
					Object[] object = (Object[]) iter.next();
//			for (Monitoring object : lins) {
				table+="<TR>";
					table+="<TD "+ (((String) object[0]).equals("ERR") ? "bgcolor=\"red\" " : "")+" >";table+=((String) object[0]);table+="</TD>";
					table+="<TD>";table+=((String) object[1]);table+="</TD>";
					table+="<TD "+ (((String) object[2]).equals("ERR") ? "bgcolor=\"red\" " : "") +" >";table+=((String) object[2]);table+="</TD>";
					table+="<TD "+ (((String) object[4]).equals("ERR") ? "bgcolor=\"red\" " : "") +" >";table+=((String) object[4]);table+="</TD>";
					table+="<TD "+ (((String) object[5]).equals("ERR") ? "bgcolor=\"red\" " : "") +" >";table+=((String) object[5]);table+="</TD>";
					table+="<TD>";table+=((String) object[6]);table+="</TD>";
					table+="<TD "+ (((String) object[7]).equals("ERR") ? "bgcolor=\"red\" " : "") +" >";table+=((String) object[7]);table+="</TD>";
					table+="<TD "+ (((String) object[8]).equals("ERR") ? "bgcolor=\"red\" " : "") +" >";table+=((String) object[8]);table+="</TD>";
					table+="<TD "+ (((String) object[29]).equals("ERR") ? "bgcolor=\"red\" " : "") +" >";table+=((String) object[29]);table+="</TD>";					
					
					table+="<TD>";table+=((String) object[9]);table+="</TD>";
					table+="<TD>";table+=((String) object[10]);table+="</TD>";
													
					table+="<TD>";table+=((BigDecimal) object[11]);table+="</TD>";
					table+="<TD>";table+=((String) object[12]);table+="</TD>";					
					table+="<TD>";table+=((String) object[13]);table+="</TD>";
					
					table+="<TD>";table+=((String) object[28]);table+="</TD>";	
					
					table+="<TD>";table+=((Timestamp) object[14]);table+="</TD>";
					table+="<TD>";table+=((Timestamp) object[15]);table+="</TD>";
					table+="<TD>";table+=((Timestamp) object[16]);table+="</TD>";
					table+="<TD>";table+=((Timestamp) object[17]);table+="</TD>";
					table+="<TD>";table+=((Timestamp) object[18]);table+="</TD>";
					table+="<TD>";table+=((Timestamp) object[19]);table+="</TD>";
					table+="<TD>";table+=((Timestamp) object[20]);table+="</TD>";
					table+="<TD>";table+=((Timestamp) object[21]);table+="</TD>";
					table+="<TD>";table+=((Timestamp) object[22]);table+="</TD>";
					table+="<TD>";table+=((Timestamp) object[23]);table+="</TD>";
					
					table+="<TD>";table+=((BigDecimal) object[24]);table+="</TD>";
					
					table+="<TD>";table+=((String) object[25]);table+="</TD>";
					table+="<TD>";table+=((String) object[26]);table+="</TD>";
					table+="<TD>";table+=((String) object[27]);table+="</TD>";					
					
					table+="<TD>";
				table+="</TR>";
			}
			table+="</TABLE>";

			} catch (Exception e) {
				e.printStackTrace();
				logger.info(" ERROR EXECUTEHTML " +  e.getMessage());
			}
//			table+="</PRE>";
			return table;
	}
	
	/**
	 *
	 * @param em
	 * @param msgId
	 * @return
	 */
	private List<MonitoringData> listaPagamenti(String msgId) {
		
		String condMsgId = (msgId!=null && !("".equals(msgId.trim())) ? " and flussi.msgid = '"+msgId+"'" : "");
		
		Query queryPendenze = manager.createNativeQuery(
				"select                                                            " +
						"  CASE  WHEN C.TS_INSERIMENTO>P.TS_INSERIMENTO                    " +
						"                      THEN 'ERR'                                  " +
						"                      ELSE 'OK PAGA'                              " +
						"                      END AS FASE1ERR,                            " +
						"  CASE  WHEN P.TS_INSERIMENTO>D.TS_INSERIMENTO                    " +
						"                      THEN 'ERR'                                  " +
						"                      ELSE 'OK DIST'                              " +
						"                      END AS FASE2WARN,                           " +
						"  CASE  WHEN D.TS_INSERIMENTO>NOTIFICA_ESEGUITO                   " +
						"                      THEN 'ERR'                                  " +
						"                      ELSE 'OK NOT1'                              " +
						"                      END AS FASE3ERR,                            " +                                     
						"  CASE  WHEN D.TS_INSERIMENTO>NOTIFICA_ESEGUITO                   " +
						"                      THEN 'ERR'                                  " +
						"                      ELSE 'OK NOT1'                              " +
						"                      END AS FASE4ERR,                            " +
						"  CASE  WHEN NOTIFICA_ESEGUITO>BOZZ.TS_INSERIMENTO                " +
						"                      THEN 'ERR'                                  " +
						"                      ELSE 'OK BOZZ'                              " +
						"                      END AS FASE5ERR,                            " + 
						"  CASE  WHEN BOZZ.TS_INSERIMENTO> NOTIFICA_REGOLATO               " +
						"                      THEN 'ERR'                                  " +
						"                      ELSE 'OK NOT2'                              " +
						"                      END AS FASE6ERR,                            " +
						"  CASE  WHEN NOTIFICA_REGOLATO>BONI.TS_INSERIMENTO                " +
						"                      THEN 'ERR'                                  " +
						"                      ELSE 'OK BONI'                              " +
						"                      END AS FASE7WARN,                           " +
						"  CASE  WHEN BONI.TS_INSERIMENTO>RENDI.TS_INSERIMENTO             " +
						"                      THEN 'ERR'                                  " +
						"                      ELSE 'OK EP'                                " +
						"                      END AS FASE8ERR,                            " +
						"  CASE  WHEN RENDI.TS_INSERIMENTO> ESITI.TS_INSERIMENTO           " +
						"                      THEN 'ERR'                                  " +
						"                      ELSE 'OK ESITOBON'                          " +
						"                      END AS FASE9ERR,                            " +
						"                                                                  " +
						"  CD_TRB_ENTE, STATO_NOTIFICA, IM_PAGATO,                         " +
						"  D.COD_TRANSAZIONE,                                              " +
						"  RENDI.COD_RENDICONTAZIONE,                                      " +
						
						"  C.TS_INSERIMENTO AS TSINSERTCOND,                               " +
						"  P.TS_INSERIMENTO AS INSERTPAGA,                                 " +
						"  D.TS_INSERIMENTO AS INSERTDIST,                                 " +
						"  NOTIFICA_ESEGUITO,                                              " +
						"  BOZZ.TS_INSERIMENTO AS TSINSERTBOZZ,                            " +
						"  NOTIFICA_REGOLATO,                                              " +
						"  BONI.TS_INSERIMENTO AS TSINSERTBONI,                            " +
						"  RENDI.TS_INSERIMENTO AS INSERTRCT,                              " +
						"  ESITI.TS_INSERIMENTO AS TSINSERTESITOEP,                        " +
						"  NOTIFICA_INCASSO,                                               " +						
						"  BONI.IMPORTO, 												   " +
						"  TIPO_ESITO, 													   " +
						"  ID_PAG_COND_ENTE,                							   " +
						"  CODICE_RIFERIMENTO,                                             " +
						"  CFG.APPLICATION_ID,											   " +
						
						"  CASE  WHEN ESITI.TS_INSERIMENTO > NOTIFICA_INCASSO			   " +
	                    "  	THEN 'ERR'			   										   " +
	                    "  	ELSE 'OK NOT3'			   									   " +
	                    "  	END AS FASE10ERR   							   				   " +		                    
	                    
						"from PAGAMENTI as P,                                              " +
						"  CONDIZIONI_DOCUMENTO as C,                                      " +
						"  DISTINTE_PAGAMENTO AS D,                                        " +
//						"  --CASELLARIO_INFO AS INFO,                                      " +
						"  BOZZE_BONIFICI_RIACCREDITO AS BOZZ,                             " +
						"  BONIFICI_RIACCREDITO AS BONI,                                   " +
						"  ESITI_BONIFICI_RIACCREDITO AS ESITI,                            " +
						"  RENDICONTAZIONI AS RENDI,                                       " +
						"  CFG_GATEWAY_PAGAMENTO AS CFG                                    " +						
						"where                                                             " +
						"  P.ID_CONDIZIONE = C.ID_CONDIZIONE AND                           " +
						"  C.ID_CONDIZIONE = BOZZ.ID_CONDIZIONE AND                        " +
						"  P.ID_DISTINTE_PAGAMENTO = D.ID AND                              " +
//						"  --D.COD_TRANSAZIONE = RENDI.COD_RENDICONTAZIONE AND             " +
						"  BOZZ.ID_BONIFICI_RIACCREDITO = BONI.ID AND                      " +
						"  BONI.ID = ESITI.ID_BONIFICI_RIACCREDITO AND                     " +
						"  ESITI.ID_RENDICONTAZIONE = RENDI.ID AND                         " +
						"  CFG.ID = D.ID_CFG_GATEWAY_PAGAMENTO AND                         " +												
						"  ST_PAGAMENTO = 'ES'  AND                                        " +
						"  (C.TS_INSERIMENTO > P.TS_INSERIMENTO OR                         " +
						"   P.TS_INSERIMENTO > D.TS_INSERIMENTO OR                         " +
						"   D.TS_INSERIMENTO > NOTIFICA_ESEGUITO OR                        " +
						"   NOTIFICA_ESEGUITO > NOTIFICA_REGOLATO OR                       " +
						"   NOTIFICA_REGOLATO > BONI.TS_INSERIMENTO OR                     " +
						"   BONI.TS_INSERIMENTO > RENDI.TS_INSERIMENTO OR                  " +
						"   RENDI.TS_INSERIMENTO > ESITI.TS_INSERIMENTO OR                 " +
						"   ESITI.TS_INSERIMENTO > NOTIFICA_INCASSO)                       " +
						"                                                                  " +
						"order by P.TS_INSERIMENTO DESC                                    " +
						"fetch first 50 rows only                                          ");
		List lins = (List) queryPendenze.getResultList();
		return lins;
	}	


//	@Override
//	public MonitoringData executeApplicationTransaction(Object data) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
}

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

import java.math.BigDecimal;
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
//@TransactionManagement(TransactionManagementType.BEAN)
@Remote(StressTestCommandExecutor.class)
public class MonitoraggioPagamenti implements StressTestCommandExecutor {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;

	private final Log logger = LogFactory.getLog(this.getClass());

	/**
	 *
	 */
	public synchronized MonitoringData executeApplicationTransaction(Object data) {
		return null;
	}

	@Override
	public String executeHtml(String msgId) {
		if ("STATS".equals(msgId)) {
			return listaStatistichePagamenti(msgId);
		} else {
			return lista25Pagamenti(msgId);
		}		
	}

	/**
	 * 
	 * @param msgId
	 * @return
	 */
	private String lista25Pagamenti(String msgId) {
		String table = "";
		//connessione db
		EntityManager em = null;
		try {
			em = getManager();
			//calcolo la lista dei pagamenti eseguiti
			List lins = listaPagamenti(em,msgId);

			logger.info(this.getClass().getSimpleName() + "executeHtml, found " + lins.size());
			table = "<br><br>";
			table += "<b>Lista pagamenti eseguiti , ordinate in base al tempo di esecuzione</b>";
			table += "<br>";
			table+="<TABLE border=\"\1\">";
			table+="<TR>";
			table+="<TD>Ut. Creatore</TD>";//0
			table+="<TD>Op. Inserim.</TD>";//3
			table+="<TD>Stato Pag</TD>";//1
			table+="<TD>Stato Dist</TD>";//19
			table+="<TD>stato notifica</TD>";			//11
			table+="<TD>tipo notifica</TD>";			//11
			
			table+="<TD>Cod. Transaz.</TD>";//2
			
			table+="<TD>Importo pagato</TD>";//?
			table+="<TD>Flag Incasso</TD>";//20
			
			table+="<TD>Id. Distinta</TD>";//6
			table+="<TD>Cod. Tributo</TD>";//7					
			
			table+="<TD>Ts. Paga</TD>";			//10
			table+="<TD>Not. Eseguito</TD>";			//22
			table+="<TD>Not. Regolato</TD>";			//23
			table+="<TD>Not. Incasso</TD>";			//24
			           
			table+="<TD>CONSEGNA</TD>";			//25
			table+="<TD>FORMATO</TD>";			//26
			table+="<TD>FREQ</TD>";			//27
			table+="<TD>TS INSERT CFG</TD>";			//28
			table+="<TD>TS UPD CFG</TD>";			//29			
			
			table+="<TD>Id. Pend.Ente</TD>";			//12
			
			table+="<TD>E2EMsgId</TD>";					//13
			table+="<TD>SenderId</TD>";					//14
			table+="<TD>SenderSys</TD>";				//15
			
			table+="<TD>Esiti Pendenze</TD>";				//13+14+15
			
			table+="<TD>Notifica Id - ReceiverId - ReceiverSys</TD>";
			table+="</TR>";

			Iterator iter = lins.iterator();
			int i = 0;
			while (iter.hasNext()) {
					Object[] object = (Object[]) iter.next();
//			for (Monitoring object : lins) {
				table+="<TR>";
					System.out.println("TS OUT=" + (Timestamp) object[10] );
					table+="<TD>";table+=((String) object[0]);table+="</TD>";
					table+="<TD>";table+=((String) object[3]);table+="</TD>";
					table+="<TD>";table+=((String) object[1]);table+="</TD>";
					table+="<TD>";table+=((Character) object[19]);table+="</TD>";
					table+="<TD>";table+=((String) object[11]);table+="</TD>";
					table+="<TD>";table+=((String) object[21]);table+="</TD>";
					
					table+="<TD>";table+=((String) object[2]);table+="</TD>";					
					table+="<TD>";table+=((BigDecimal) object[4]);table+="</TD>";
//					table+="<TD>";table+=((BigDecimal) object[8]);table+="</TD>";
					
					table+="<TD>";table+=((String) object[20]);table+="</TD>";	
					
					table+="<TD>";table+=((Number) object[6]);table+="</TD>";
					table+="<TD>";table+=((String) object[7]);table+="</TD>";					
															
					table+="<TD>";table+=((Timestamp) object[10]);table+="</TD>";
					table+="<TD>";table+=((Timestamp) object[22]);table+="</TD>";
					table+="<TD>";table+=((Timestamp) object[23]);table+="</TD>";
					table+="<TD>";table+=((Timestamp) object[24]);table+="</TD>";
					
					table+="<TD>";table+=((String) object[25]);table+="</TD>";
					table+="<TD>";table+=((String) object[26]);table+="</TD>";
					table+="<TD>";table+=((String) object[27]);table+="</TD>";					
					table+="<TD>";table+=((Timestamp) object[28]);table+="</TD>";
					table+="<TD>";table+=((Timestamp) object[29]);table+="</TD>";
					
					
					table+="<TD>";table+=((String) object[12]);table+="</TD>";
					
					table+="<TD>";table+=((String) object[13]);table+="</TD>";
					table+="<TD>";table+=((String) object[14]);table+="</TD>";
					table+="<TD>";table+=((String) object[15]);table+="</TD>";
					
					
					table+="<TD>";
					table+="<a href=\"invoke?operation=listaEsitiPendenza&objectname=it.tasgroup.idp.jmx%3Atype%3DMXBeanCommandExecutor&" +
						"value0=" + ((String) object[13])+
						"&type0=java.lang.String" +
						"&value1=" + ((String) object[14])+
						"&type1=java.lang.String" +
						"&value2=" + ((String) object[15]) +
						"&type2=java.lang.String\">"+((String) object[13])+"</a>";
					table+="</TD>";
					
					table+="<TD>";table+=
							((String) object[16])+ " "+
							((String) object[17])+ " "+
							((String) object[18]);
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
	 * @param msgId
	 * @return
	 */
	private List<MonitoringData> listaPagamenti(EntityManager em, String msgId) {
		
		String condMsgId = (msgId!=null && !("".equals(msgId.trim())) ? " and flussi.msgid = '"+msgId+"'" : "");
		
		Query queryPendenze = em.createNativeQuery(
				" SELECT        " +
		   "              flussi.utente_creatore," +   //0
		   "              flussi.stato,   " +//1
		   "              flussi.cod_transazione,   " +//2
		   "              flussi.op_inserimento,    " +//3
		   "              flussi.importo,    " +//4
		   "              flussi.cod_transazione, " +//5
		   "              flussi.id as id_distinta,    " +
		   "              paga.cd_trb_ente," +//7
		   "			  paga.im_pagato,    " +//8
		   "             paga.distinta,   " +
		   "             paga.TS_INSERIMENTO," +//10
		   "			 paga.stato_notifica,    " +//11
		   "             notifiche.id_pendenzaente,    " +//12
		   "             esitipend.e2emsgid,    " +//13
		   "             esitipend.senderid,    " +//14
		   "             esitipend.sendersys,    " +//15
		   "             notifiche_cart.e2emsgid,   " +//16 
		   "             notifiche_cart.receiverid,    " +//17
		   "             notifiche_cart.receiversys ,   " +//18
		   "             paga.ST_PAGAMENTO, " +//19
		   "             paga.FLAG_INCASSO, " +//20
			"           notifiche.STATO_pagamento, " +//21
			"           paga.NOTIFICA_ESEGUITO, 		  " +//22 			
			"           paga.NOTIFICA_REGOLATO, 		   		" +//23	
			"           paga.NOTIFICA_INCASSO, 	" +//24
			
            "      cfg.CONSEGNA_NOTIFICA, " + //25
            "      cfg.FORMATO_NOTIFICA, " + //26
            "      cfg.FREQ_NOTIFICA, " + //27
            "      cfg.TS_INSERIMENTO, " + //28
            "      cfg.TS_AGGIORNAMENTO " + //29			
		   
		   "        from  DISTINTE_PAGAMENTO  flussi        " +
		   "        left outer join  PAGAMENTI  paga       " +
		   "             on (paga.id_distinte_pagamento = flussi.id) " +                                     
		   "         left outer join  NOTIFICHE_PAGAMENTI  notifiche" +    
		   "             on (flussi.cod_transazione = notifiche.msgid)        " +                         
		   "         left outer join  NOTIFICHE_CART  notifiche_cart    " +
		   "             on (notifiche.e2emsgid = notifiche_cart.e2emsgid   " +
		   "                   and notifiche.receiverid  = notifiche_cart.senderid " +   
		   "                   and notifiche.receiversys = notifiche_cart.sendersys) " +		   
		   "          left outer join  CFG_NOTIFICA_PAGAMENTO cfg   " +  
	       "          on (notifiche.id_ente    = cfg.id_ente     " +
	       "                and notifiche.cd_trb_ente  = cfg.cd_trb_ente" +
	       " 				and notifiche.STATO_NOTIFICA = cfg.tipo_notifica) " +            
		   "        left outer join  ESITI_PENDENZA  esitipend     " +
		   "             on (paga.id_pendenza = esitipend.id_pendenza)       " +
		   " 		where  paga.ts_inserimento is not null " 
		   +		   condMsgId +
		   "         order by paga.ts_inserimento desc    " +
            "          fetch first 25 rows only ");
		List lins = (List) queryPendenze.getResultList();
		queryPendenze.setMaxResults(25);
		return lins;
	}
	
	/**
	 * 
	 * @param em
	 * @param msgId
	 * @return
	 */
	private List<MonitoringData> listaPagamentiJpa(EntityManager em, String msgId) {
		
		String condMsgId = (msgId!=null && !("".equals(msgId.trim())) ? " and flussi.msgid = '"+msgId+"'" : "");
		
		Query queryPendenze = em.createQuery(
				" SELECT        " +
		   "              flussi.");
//            "          fetch first 25 rows only ");
		List lins = (List) queryPendenze.getResultList();
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
		return manager;
	}


	@Override
	public MonitoringData executeApplicationTransaction(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonitoringData executeApplicationTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeHtml() throws Exception {
		String table = "";
		//connessione db
		EntityManager em = null;
		try {
			em = getManager();
			//calcolo la lista dei pagamenti eseguiti
			List lins = cruscotto(em);

			logger.info(this.getClass().getSimpleName() + "executeHtml, found " + lins.size());
			table = "<br><br>";
			table += "<b>Cruscotto</b>";
			table += "<br>";
			table+="<TABLE border=\"\1\">";
			table+="<TR>";
				table+="<TD>Data Creaz</TD>";
				table+="<TD>ID RICON</TD>";
				table+="<TD>TOT. DIST.</TD>";
				table+="<TD>IMP. COMMISS</TD>";
				table+="<TD>UT.CREAT</TD>";
				table+="<TD>ID CFG</TD>";
				table+="<TD>ID PAGAM</TD>";
				table+="<TD>IMP. PAGAM</TD>";
				table+="<TD>ID ENTE</TD>";
				table+="<TD>BB</TD>";
				table+="<TD>RCT</TD>";
				table+="<TD>RH</TD>";
				table+="<TD>BOZZA</TD>";
				table+="<TD>BONIFICO</TD>";
				table+="<TD>NOME SUPP</TD>";
				table+="<TD>IMP. DIST</TD>";
				table+="<TD>EP</TD>";
			table+="</TR>";

			Iterator iter = lins.iterator();
			int i = 0;
			while (iter.hasNext()) {
				Object[] object = (Object[]) iter.next();
				table+="<TR>";
					table+="<TD>";
						table+=((Timestamp) object[0]);
					table+="</TD>";
					table+="<TD>";
						table+=((String) object[1]);
					table+="</TD>";
					table+="<TD>";
						table+=((BigDecimal) object[2]);
					table+="</TD>";					
					table+="<TD>";
						table+=((BigDecimal) object[3]);
					table+="</TD>";
					table+="<TD>";table+=((String) object[4]);table+="</TD>";
					table+="<TD>";table+=((Integer)object[5]);table+="</TD>";
					table+="<TD>";table+=((Integer)object[6]);table+="</TD>";
					table+="<TD>";table+=((BigDecimal) object[7]);table+="</TD>";
					table+="<TD>";table+=((String) object[8]);table+="</TD>";
					table+="<TD>";table+=((String) object[9]);table+="</TD>";
					table+="<TD>";table+=((Integer) object[10]);table+="</TD>";
					table+="<TD>";table+=((Integer) object[11]);table+="</TD>";
					table+="<TD>";table+=((Integer) object[12]);table+="</TD>";
					table+="<TD>";table+=((Integer) object[13]);table+="</TD>";
					table+="<TD>";table+=((Integer) object[14]);table+="</TD>";
					
					table+="<TD>";table+=((String) object[15]);table+="</TD>";
					table+="<TD>";table+=((BigDecimal) object[16]);table+="</TD>";
					table+="<TD>";table+=((Integer) object[17]);table+="</TD>";

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
	 * @param msgId
	 * @return
	 */
	private List<MonitoringData> cruscotto(EntityManager em) {
		Query queryPendenze = em.createNativeQuery(
			"select                                                              " +
			"dist.data_creazione as data_creazione,                              " +
			"dist.cod_transazione as id_riconciliazione,                         " +
			"dist.importo as totale_distinta,                                    " +
			"dist.importo_commissioni as importo_commissioni,                    " +
			"dist.utente_creatore as utente_creatore,                            " +
			"dist.id_cfg_gateway_pagamento,pags.id as id_pagamento,              " +
			"pags.im_pagato as importo_pagamento,                                " +
			"pags.id_ente as id_ente,                                            " +
			"pags.cd_trb_ente as cd_trb_ente,                                    " +
			"ebb.id as esitobb ,                                                 " +
			"erct.id as esitorct,                                                " +
			"erh.id as esitorh,                                                  " +
			"bozzeriaccredito.id as bozza_bonifico_riaccredito,                  " +
			"riaccredito.id as bonifico_riaccredito,                             " +
			"casellario.nome_supporto as nomesupporto_bonifico_riaccredito,      " +
			"distinteriaccredito.importo  as importodistintariaccredito,         " +
			"esitiriaccredito.id as esitoEP                                      " +
			"                                                                    " +
            "                                                                    " +
			"from DISTINTE_PAGAMENTO dist                                        " +
			"                                                                    " +
			"left outer join pagamenti pags                                      " +
			"on dist.id=pags.id_distinte_pagamento                               " +
			"                                                                    " +
			"left outer join esiti_bb ebb                                        " +
			"on dist.cod_transazione = ebb.id_riconciliazione                    " +
			"                                                                    " +
			"left outer join esiti_rct erct                                      " +
			"on dist.cod_transazione = erct.id_riconciliazione                   " +
			"                                                                    " +
			"left outer join incassi_bonifici_rh erh                             " +
			"on dist.cod_transazione = erh.id_riconciliazione                    " +
			"                                                                    " +
			"left outer join bozze_bonifici_riaccredito bozzeriaccredito         " +
			"on pags.id_condizione=bozzeriaccredito.id_condizione                " +
			"                                                                    " +
			"left outer join bonifici_riaccredito riaccredito                    " +
			"on riaccredito.id=bozzeriaccredito.id_bonifici_riaccredito          " +
			"                                                                    " +
			"left outer join distinte_riaccredito distinteriaccredito            " +
			"on riaccredito.id_distinte_riaccredito = distinteriaccredito.id     " +
			"                                                                    " +
			"left outer join casellario_dispo casellario                         " +
			"on casellario.id_distinte_riaccredito = distinteriaccredito.id      " +
			"                                                                    " +
			"left outer join esiti_bonifici_riaccredito esitiriaccredito         " +
			"on riaccredito.id=esitiriaccredito.id_bonifici_riaccredito          " +
			"                                                                    " +
			"where dist.data_creazione >= '2012-05-03-20.00.00.000000'           " +
			"                                                                    " +
			"UNION ALL                                                           " +
			"                                                                    " +
			"select                                                              " +
			"docs.ts_inserimento as data_creazione,                              " +
			"docs.id as id_riconciliazione,                                      " +
			"0.0 as totale_distinta,                                             " +
			"0.0 as importo_commissioni,                                         " +
			"docs.op_inserimento as utente_creatore,                             " +
			"docs.id_cfg_gateway_pagamento as id_cfg_gateway_pagamento,          " +
			"0 as id_pagamento,                                                  " +
			"0.0 as importo_pagamento,                                           " +
			"'' as id_ente,                                                      " +
			"'' as cd_trb_ente,                                                  " +
			"0 as esitobb ,                                                      " +
			"0 as esitorct,                                                      " +
			"0 as esitorh,                                                       " +
			"0 as bozza_bonifico_riaccredito,                                    " +
			"0 as bonifico_riaccredito,                                          " +
			"''  as nomesupporto_bonifico_riaccredito,                           " +
			"0.0 as importodistintariaccredito,                                  " +
			"0 as esitoEP                                                        " +
			"                                                                    " +
			"from DOCUMENTI_PAGAMENTO as docs                                    " +
			"where docs.id_distinta_pagamento is null                            " +
			" order by data_creazione desc " +
			"  fetch first 25 rows only" +
			" with ur ");
		List lins = (List) queryPendenze.getResultList();
		return lins;
	}

	/**
	 * 
	 * @param msgId
	 * @return
	 */
	private String listaStatistichePagamenti(String msgId) {
		String table = "";
		//connessione db
		EntityManager em = null;
		try {
			em = getManager();
			//calcolo la lista dei pagamenti eseguiti
			List lins = listaStatistichePagamenti(em);

			logger.info(this.getClass().getSimpleName() + "executeHtml, found " + lins.size());
			table = "<br><br>";
			table += "<b>Statistiche pagamenti, ultimi 25 valori</b>";
			table += "<br>";
			table+="<TABLE border=\"\1\">";
			table+="<TR>";
			table+="<TD>ANNO</TD>";//0
			table+="<TD>MESE</TD>";//3
			table+="<TD>TOTALE</TD>";//1
			table+="<TD>NUM</TD>";//19
			table+="<TD>STATO PAGAMENTO</TD>";			//11

			table+="</TR>";

			Iterator iter = lins.iterator();
			int i = 0;
			while (iter.hasNext()) {
					Object[] object = (Object[]) iter.next();
//			for (Monitoring object : lins) {
				table+="<TR>";
					table+="<TD>";table+=((Integer)object[0]);table+="</TD>"; 
					table+="<TD>";table+=((Integer)object[1]);table+="</TD>";
					table+="<TD>";table+=((BigDecimal)object[2]);table+="</TD>";
					table+="<TD>";table+=((Integer)object[3]);table+="</TD>";
					table+="<TD>";table+=((String)object[4]);table+="</TD>";
//					table+="<TD>";table+=((String) object[1]);table+="</TD>";
//					table+="<TD>";table+=((Character) object[19]);table+="</TD>";			
//					table+="<TD>";table+=((BigDecimal) object[4]);table+="</TD>";
//					table+="<TD>";table+=((Integer) object[6]);table+="</TD>";
//					table+="<TD>";table+=((Timestamp) object[10]);table+="</TD>";
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
	 * @param msgId
	 * @return
	 */
	private List<MonitoringData> listaStatistichePagamenti(EntityManager em) {
			
		Query queryPendenze = em.createNativeQuery(
				" select year(ts_inserimento) as anno, month(ts_inserimento) as mese, sum(im_pagato) as Totale,count(*) as num, vargraphic(st_pagamento)  from PAGAMENTI group by st_pagamento,month(ts_inserimento),year(ts_inserimento) "
				+ " order by year(ts_inserimento) desc,month(ts_inserimento) desc "
				+ " fetch first 25 rows only with UR");
		List lins = (List) queryPendenze.getResultList();
		return lins;
	}	

}

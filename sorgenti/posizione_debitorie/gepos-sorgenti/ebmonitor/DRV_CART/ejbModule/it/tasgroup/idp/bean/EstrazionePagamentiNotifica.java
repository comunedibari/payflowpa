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

import it.tasgroup.idp.domain.enti.ConfigNotifiche;
import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.timer.AbstractNotificationTimer;
import it.tasgroup.idp.timer.AbstractNotificationTimer.NotificationStatus;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
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
@Remote(CommandExecutor.class)
public class EstrazionePagamentiNotifica implements NotificationCommandExecutor {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;

	private final Log logger = LogFactory.getLog(this.getClass());

	@EJB(beanName = "InserimentoPagamentoNotifica")
	private ObjectCommandExecutor InserimentoNotificheProxy;
	
	@Override
	public MonitoringData executeApplicationTransaction() {
		throw new UnsupportedOperationException();
	}
	/**
	 *
	 */	
	private  MonitoringData executeApplicationTransactionInternal(String idEnte, String cdTrbEnte, String tipoNotifica) {
		//monitoring data
		MonitoringData monData = new MonitoringData();
		logger.info(" =========== BEGIN Internal (idEnte = "+ idEnte +", cdTrbEnte= "+cdTrbEnte+", tipoNotifica= "+tipoNotifica);
		try { 
//			//calcolo la lista dei pagamenti da spedire, relativi alle pendenze in stato pagato
			String queryMini = "select " +
			" pagamenti.ID," +	//object[0] 
			" pagamenti.TS_DECORRENZA," + //object[1] 
			" condizioni.DT_SCADENZA," + //object[2] 
			" pagamenti.IM_PAGATO, " +//object[3] 
			" rtrim(pendenze.RIFERIMENTO), " +//object[4] 
			" pagamenti.stato_notifica, " +//object[5]  //DESCRIZIONESTATO NON ESISTE PIU, SOSTITUISCO CON STATO
			" pagamenti.CO_PAGANTE, " +//object[6]  
			" entitributi.FL_PREDETERM, " + //object[7]  
			
			" flussiDispo.COD_TRANSAZIONE, " + //object[8]  //MSGID NON ESISTE PIU, SOSTITUISCO CON COD_TRANSAZIONE
			//non uso più la data-spedizione, ma la data creazione
			//questo campo finisce nel campo 'data spedizione'
			//" flussiDispo.DATA_SPEDIZIONE, " +	//object[..]//TMBSPEDIZIONE NON ESISTE PIU, SOSTITUISCO CON DATA_SPEDIZIONE			
			" flussiDispo.DATA_CREAZIONE, " +	//object[9]  //TMBSPEDIZIONE NON ESISTE PIU, SOSTITUISCO CON DATA_SPEDIZIONE
			//non uso più il ts_inserimento, ma la data spedizione
			//questo campo finisce nel campo 'data autorizzazione'			
			" flussiDispo.data_spedizione, " +//object[10]			
			" flussiDispo.IMPORTO, " +	//object[11]  TOTIMPORTIPOSITIVI NON ESISTE PIU, SOSTITUISCO CON importo
			
			" rtrim(pagamenti.ID_PENDENZA), " +//object[12] 
			" entitributi.ID_ENTE, " +//object[13] 
			" entitributi.CD_TRB_ENTE, " + //object[14] 
			" entitributi.ID_SYSTEM, " +//object[15] 
			" enti.CD_ENTE, " +//object[16]
			" intestatari.ragionesociale as ragionesociale, " +//object[17] 
			" pendenze.ID_PENDENZAENTE,		" +//object[18]	
			
			" rtrim(pagamenti.tipospontaneo),		" +//object[19]
			" rtrim(condizioni.ID_PAGAMENTO), " +	//object[20] 
			" flussiDispo.ID_CFG_GATEWAY_PAGAMENTO, " +	//object[21] 
			" pagamenti.TS_ORDINE, " +//object[22]
			" pendenze.DE_CAUSALE,		" +//object[23]
			" pagamenti.DATA_ACCREDITO_CONTOTECNICO, " +//object[24]	
			" pagamenti.DATA_ACCREDITO_ENTE, " +//object[25]		
			" pagamenti.FLAG_INCASSO, " +  	//object[26]
			" gateways.application_id, " +  	//object[27]
			" modalita.id as idmodalita, " +				//object[28]
			" modalita.DESCRIZIONE,  " + 	//object[29]
			" modalita.bundle_key, "+  //object[30]
			" sil.trt_id, "+  //object[31]
			" sil.trt_system "+  //object[32]
			" from  PAGAMENTI pagamenti " +
			" left outer join  DISTINTE_PAGAMENTO flussiDispo on (flussiDispo.id = pagamenti.id_distinte_pagamento)  " +
			" left outer join  JLTCOPD condizioni on (condizioni.id_condizione= pagamenti.id_condizione) " +
			" left outer join  JLTPEND pendenze on (pagamenti.id_pendenza= pendenze.id_pendenza) " +
			" inner join  JLTENTR entitributi " +
			" on (entitributi.id_ente= pagamenti.id_ente and entitributi.cd_trb_ente= pagamenti.cd_trb_ente) " +
			" left outer join  JLTENTI enti on (entitributi.id_ente= enti.id_ente)  " +
			" left outer join  INTESTATARI intestatari on (intestatari.intestatario= enti.intestatario) " +
			" left outer join  CFG_GATEWAY_PAGAMENTO gateways  on  (gateways.id = flussiDispo.id_cfg_gateway_pagamento) "+
			" left outer join  "
//			eliminata inner query 1776, errore processo su nodo2 produzione			
//			+ "	(select ID as id_modalita, BUNDLE_KEY as modalita_bundle_key, "
//			+ "			DESCRIZIONE from cfg_modalita_pagamento) "
//			+ "	modalita on  "
			+ " CFG_MODALITA_PAGAMENTO modalita on "
			+ "	(gateways.id_cfg_modalita_pagamento = modalita.id) "+ 
			" left outer join JLTSIL sil on (entitributi.id_system = sil.id_system and entitributi.id_ente=sil.id_ente) "+
			" where " +	calculateExtraCondition(idEnte, cdTrbEnte, tipoNotifica) + 
			" AND entitributi.fl_notifica_pagamento = 'Y' " + 			
			" order by entitributi.ID_ENTE desc, entitributi.ID_SYSTEM desc " ;
			logger.debug("query" + queryMini);

			Query queryNotifiche = manager.createNativeQuery(queryMini);
			List resultList = (List) queryNotifiche.getResultList();
			logger.info("Trovati notifiche da inserire , numtotale = " + resultList.size());

			//monitorizzo il num.di pagamenti estratti
			monData.setNumRecord(resultList.size());

			Iterator iter = resultList.iterator();
			while (iter.hasNext()) {

					Object[] object = (Object[]) iter.next();

					logger.info(" ==== CALL INSERT-NOTIFICHE-PAGAMENTI EJB (IdPagamento = "+ object[0] +"==== ");
					InserimentoNotificheProxy.executeApplicationTransaction(object);
					logger.info(" ==== NOTIFICA INSERT-NOTIFICHE-PAGAMENTI END EJB ==== ");

			}

			logger.info("Estrazione pagamenti per creazione notifiche terminata " );

		} catch (Exception e) {
			logger.error(" ERROR ESTRAZIONE NOTIFICA PAGAMENTO = " + e.getMessage());
			logger.error(e);
		}
		finally {
			logger.info(" =========== END Internal ========");
		}

		return monData;
	}

	/**
	 * 
	 * @return
	 */
	public EntityManager getManager() {
		return manager;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction(String stato) {
		
		MonitoringData mon = executeApplicationTransactionInternal(null, null, stato);		
		//***** se il tipo notifica è regolato dobbiamo fare girare anche i recuperi **************			
		if (stato.equals(NotificationStatus.REGOLATO.getNotifStatus())) {			
			logger.info("Gestione stato  " + stato + ". Recupero eseguiti in corso");
			MonitoringData mon2 = executeApplicationTransactionInternal(null,null,StatoEnum.NOTIFICHE_REGOLATO_FLAG2);
			mon.setNumRecord(mon.getNumRecord() + mon2.getNumRecord());
		}
		return mon;
	}
    /**
     * 
     * NB: quando si effettua un test sulle notifiche
     *     si elaborano tutte le notifiche pending indipendentemente 
     *     dalla frequenza configurata (si esclude solo il caso in cui la frequenza è MAI)
     */    
	@Override
	public String executeHtml() throws Exception  {
		logger.info(", executeHtml() BEGIN ");
		String table = "";
		//connessione db
		EntityManager em = null;
		try {
			em = getManager();

			Iterator<NotificationStatus> iterNotifStatus= AbstractNotificationTimer.getListStatusToNotify().iterator();
			//***********************
			// ciclo tutti i tipi di notifica
			while (iterNotifStatus.hasNext()) {

				NotificationStatus currNotifStatus = iterNotifStatus.next();
				logger.info("executeHtml() START handling  "+ currNotifStatus.getNotifStatus());
				// recupero la lista delle notifiche da effettuare 
				List<ConfigNotifiche> listcfgNot= AbstractNotificationTimer.getListCfgNotifTest(em, currNotifStatus);
				Iterator<ConfigNotifiche> iterListCfgNot = listcfgNot.iterator();
				
				while (iterListCfgNot.hasNext()) {
					ConfigNotifiche cfgNot=iterListCfgNot.next();
					// effettuo il caricamento della tabella NOTIFICHE_PAGAMENTI
					logger.info(" ==== CFG. NOTIFICHE - START ==== " );
					logger.info(" Consegna = " + cfgNot.getConsegnaNotifica() + " Formato = " + cfgNot.getFormatoNotifica() 
								+ " Tipo = " + cfgNot.getTipoNotifica() + " Frequenza = " + cfgNot.getFreqNotifica()
								+ " SIL = " + cfgNot.getJltentr().getIdSystem() + " De.Tributo = " + cfgNot.getJltentr().getDeTrb()
								+ " CD_TRB_ENTE = " + cfgNot.getJltentr().getId().getCdTrbEnte() );
					logger.info(" ==== CFG. NOTIFICHE - END ==== " );
					this.executeApplicationTransaction(cfgNot);				

				}
				logger.info("executeHtml() END handling  "+ currNotifStatus.getNotifStatus());
			}
			//***********************
			//calcolo la lista dei pagamenti estratti
			List<NotifichePagamenti> lins = listaNotificheTemporaneeCreate(em);

			logger.info("executeHtml(), found " + lins.size());
			table = "<br><br>";
			table += "<b>Lista pagamenti AGGREGATI la cui notifica è pronta per essere creato e poi spedita</b>";
			table += "<br>";
			table+="<TABLE border=\"\1\">";
			table+="<TR>" +
					"<TD>Id Pagam</TD>" +
					"<TD>Id Notific</TD>" +
					"<TD>Id Pendenza</TD>" +
					"<TD>Id Pendenza Ente</TD>" +
					"<TD>Stato Pag</TD>" +
					"<TD>Cd Trb</TD>" +
					"<TD>Id Ente</TD>" +
					"<TD>Sil</TD>" +
					"<TD>Im Tot</TD>" +
					"<TD>Tot Importi</TD>" +
					"<TD>Causale</TD>" +
					"<TD>Co Pagante</TD>" +
					"</TR>";

			for (NotifichePagamenti object : lins) {
				table+="<TR>";
				table+="<TD>";
				table+=(object.getIdPagamento());
				table+="</TD>";
				table+="<TD>";
				table+=(object.getIdNotifica());
				table+="</TD>";
				table+="<TD>";
				table+=(object.getIdPendenza());
				table+="</TD>";
				table+="<TD>";
				table+=(object.getIdPendenzaente());
				table+="</TD>";
				table+="<TD>";
				table+=(object.getStatoPagamento());
				table+="</TD>";
				table+="<TD>";
				table+=(object.getCdTrbEnte());
				table+="</TD>";
				table+="<TD>";
				table+=(object.getIdEnte());
				table+="</TD>";
				table+="<TD>";
				table+=(object.getIdSystem());
				table+="</TD>";
				table+="<TD>";
				table+=(object.getImTotale());
				table+="</TD>";
				table+="<TD>";
				table+=(object.getTotimportipositivi());
				table+="</TD>";
				table+="<TD>";
				table+=(object.getDeCausale());
				table+="</TD>";
				table+="<TD>";
				table+=(object.getCoPagante());
				table+="</TD>";
				table+="</TR>";
			}
			table+="</TABLE>";
		} catch (Exception e) {
			logger.info(" ERROR EXECUTEHTML " +  e.getMessage());
		}
		//		table+="</PRE>";
		return table;
	}


	/**
	 * TODO devo modificare il metodo in base alle nuove specifiche
	 * @param em 
	 * @return
	 */    
	private List<NotifichePagamenti> listaNotificheTemporaneeCreate(EntityManager em) {
		//recupero lista esiti da spedire
		Query notificheDaCreare = em.createNativeQuery("select * "
				+ " from NOTIFICHE_PAGAMENTI "
				+ " where NOTIFICHE_PAGAMENTI.stato_notifica =:stato " +
				" order by NOTIFICHE_PAGAMENTI.id_ente, NOTIFICHE_PAGAMENTI.cd_trb_ente, " + 
				" NOTIFICHE_PAGAMENTI.stato_pagamento " 
				,NotifichePagamenti.class);
		
	   notificheDaCreare.setParameter("stato", StatoEnum.NOTIFICHE_DA_CREARE);	   
	   
	   List<NotifichePagamenti> listaNotifichePagamenti = (List) notificheDaCreare.getResultList();

		logger.info("trovati notifiche_pag di cui creare il mex xml, numtotale = " + listaNotifichePagamenti.size());

		return listaNotifichePagamenti;
	}


	/**
	 * 
	 * @param idEnte
	 * @param cdTrbEnte
	 * @param stato
	 * @return
	 */
	private String calculateExtraCondition(String idEnte, String cdTrbEnte, String stato) {
		String condition = "";
		if (StatoEnum.NOTIFICHE_ESEGUITO.equals(stato)) {
			
			condition = ""	+
				//NOTIFICA FASE 1  
				//non c'è bisogno di controllare il flag incasso....
				//la NOTIFICA1 va fatta sempre quando il pagamento è ESEGUITO
				//e ancora non è stata creata....				  
				" (pagamenti.st_pagamento = 'ES') " +
				" AND pagamenti.stato_notifica is null ";
			
		} else if (StatoEnum.NOTIFICHE_REGOLATO_FLAG2.equals(stato)) {
			//da eseguire se FLAG INCASSO = 2
			//e la NOTIFICA REGOLATO non è ancora stata creata
			condition = ""	+
					//NOTIFICA FASE 2   
					" (pagamenti.flag_incasso = '2') " +
					//MA SOLO IN CASO DI PAGAMENTO ESEGUITO E NON ANNULLATO, #665   
					" AND (pagamenti.st_pagamento = 'ES')"+					
					" AND (pagamenti.stato_notifica = 'ESEGUITO' or pagamenti.stato_notifica is null)" +
					" AND pagamenti.notifica_regolato is null";
			
		} else if (StatoEnum.NOTIFICHE_REGOLATO.equals(stato)) {
			
			condition = ""	+
					//NOTIFICA FASE 2   
					" (pagamenti.flag_incasso = '1') " +					 
					" AND (pagamenti.st_pagamento = 'ES')"+					
					" AND (pagamenti.stato_notifica = 'ESEGUITO' or pagamenti.stato_notifica is null)" +
					" AND pagamenti.notifica_regolato is null";
			
		} else if (NotificationStatus.INCASSO.getNotifStatus().equals(stato)) {
				
			condition = ""	+
					//NOTIFICA FASE 3  
					" (pagamenti.flag_incasso = '2') " +					
					" AND (pagamenti.st_pagamento = 'ES' or pagamenti.st_pagamento = 'EF') " +
					" AND pagamenti.stato_notifica = 'REGOLATO'";			
		}
		if (idEnte!=null) {
			condition = condition + " AND entitributi.ID_ENTE = '"+idEnte+"' "; 				
		}
		if (cdTrbEnte!=null) {
			condition = condition + " AND entitributi.CD_TRB_ENTE = '"+cdTrbEnte+"' "; 					
		}
		return condition;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction(Object data) {
		
		if (data instanceof String) {
			MonitoringData mon = executeApplicationTransactionInternal(null,null,(String)data);	
			//***** se il tipo notifica è regolato dobbiamo fare girare anche i recuperi **************			
			if (((String)data).equals(NotificationStatus.REGOLATO.getNotifStatus())) {	
				logger.info("Gestione stato  " + data + ". Recupero eseguiti in corso");
				MonitoringData mon2 = executeApplicationTransactionInternal(null,null,StatoEnum.NOTIFICHE_REGOLATO_FLAG2);
				mon.setNumRecord(mon.getNumRecord() + mon2.getNumRecord());
			}
			return mon;
		}	
		else {
			ConfigNotifiche cfg = (ConfigNotifiche)data;			
			
			String tipoNotifica = cfg.getTipoNotifica();
			String idEnte       = cfg.getJltentr().getId().getIdEnte();
			String cdTrbEnte    = cfg.getJltentr().getId().getCdTrbEnte();
			MonitoringData mon = executeApplicationTransactionInternal(idEnte,cdTrbEnte,tipoNotifica);
			
			//***** se il tipo notifica è regolato dobbiamo fare girare anche i recuperi **************			
			if (tipoNotifica.equals(NotificationStatus.REGOLATO.getNotifStatus())) {		
				logger.info("Gestione stato  " + tipoNotifica + ". Recupero eseguiti in corso");
				MonitoringData mon2 = executeApplicationTransactionInternal(idEnte,cdTrbEnte,StatoEnum.NOTIFICHE_REGOLATO_FLAG2);
				mon.setNumRecord(mon.getNumRecord() + mon2.getNumRecord());
			}
			return mon;
			
		}
		
	}
	
}

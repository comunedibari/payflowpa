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

import it.tasgroup.dse.service.DataStoreEngineService;
import it.tasgroup.dse.view.DBReference;
import it.tasgroup.dse.view.DataInput;
import it.tasgroup.dse.view.DataInputDb;
import it.tasgroup.idp.domain.messaggi.EsitiPendenza;
import it.tasgroup.idp.domain.messaggi.PendenzeCartPK;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ConcreteFactory;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;
import it.tasgroup.idp.util.SystemPropertiesNames;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
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
@Remote(CommandExecutor.class)
public class RipartenzaDataStorage implements CommandExecutor, CommandExecutorLocal {
  
	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)	
	private EntityManager manager;
	
	@Resource
	private SessionContext ctx;
		
	private final Log logger = LogFactory.getLog(this.getClass());
	
	@EJB(beanName = "DataManager")
	private DataStorageInterface dataTx;
	
	/**
	 * 
	 */
	public MonitoringData executeApplicationTransaction() {
		//monitoring data
		MonitoringData monData = new MonitoringData();		
				
		try {
				
		logger.info(" RipartenzaDataStorageManager , leggo buste in sospeso:  ");
		List<Object[]> lins = listaPendenzeSospese(manager);

		logger.info(this.getClass().getSimpleName() + "trovati pendenze sospese, numtotale = " + lins.size());

		//monitoring data		
		monData.setNumRecord(lins.size());
		
		Iterator iter = lins.iterator();
		while (iter.hasNext()) {
			Object[] object = (Object[]) iter.next();
			PendenzeCartPK pendPk = (PendenzeCartPK) object[0];
			Integer numPend = (Integer) object[1];
			
			String e2emsgid = pendPk.getE2emsgid().trim();
			String senderid = pendPk.getSenderid().trim();
			String sendersys = pendPk.getSendersys().trim();
			
			try {
				logger.info("EsitiNecessari=" + numPend + " Verifico quanti esiti pendenza sono ancora mancanti ? ");
				//OLD STYLE QUERY
//				Query queryEsitiList = manager.createQuery(" SELECT esiti FROM EsitiPendenza esiti " +
//						" WHERE esiti.pendenzeCart.pk.e2emsgid = :e2emsgid" +
//						" AND esiti.pendenzeCart.pk.senderid = :senderId " + 
//						" AND esiti.pendenzeCart.pk.sendersys = :senderSys");
				Query queryEsitiList = manager.createNamedQuery("listaEsitiPendenzaRFC127");	
				queryEsitiList.setParameter("e2emsgid", e2emsgid);
				queryEsitiList.setParameter("senderId", senderid);
				queryEsitiList.setParameter("senderSys", sendersys);
				List<EsitiPendenza> linsList = queryEsitiList.getResultList();	
				
				logger.info(this.getClass().getSimpleName() + "trovati esitiP arrivati (busta="+pendPk.getE2emsgid().trim()+"), numtotale = " + linsList.size() + " su di un totale di " + numPend);
				if (linsList.size()<numPend) {
					logger.info("Ripartenza su e2emsgId = " + e2emsgid + ", escludendo n° " + linsList.size() + "pendenze");
					
					List<String> listPendenzaEnte = new ArrayList<String>();
					EsitiPendenza esitoPendSospeso = null;
					for (int i = 0; i < linsList.size(); i++) {
						//recupero esito corrente
						esitoPendSospeso = linsList.get(i);					
						//aggiungo (idPendenzaEnte) alla lista di esiti da NON rielaborare 
						listPendenzaEnte.add(esitoPendSospeso.getIdPendenzaEnte().trim());										
					}
					
					//se la lista non è vuota ne spedisco il contenuto
					if (listPendenzaEnte.size()>0) {
						logger.info(this.getClass().getSimpleName() + " Invio al DS il segnale di ripartenza sulla Pendenza " + esitoPendSospeso.getPendenzeCart().getPk().getE2emsgid().trim()
								+esitoPendSospeso.getPendenzeCart().getPk().getSenderid().trim()
								+esitoPendSospeso.getPendenzeCart().getPk().getSendersys().trim()
								+ " , elementi da NON rielaborare = n° " + listPendenzaEnte.size() );	
						wakeUpDataStorage(esitoPendSospeso.getPendenzeCart().getPk().getE2emsgid().trim(),
								esitoPendSospeso.getPendenzeCart().getPk().getSenderid().trim(),
								esitoPendSospeso.getPendenzeCart().getPk().getSendersys().trim(),listPendenzaEnte);							
					}		else {
						logger.info(" Il flusso " + pendPk.getE2emsgid().trim() + " deve essere rielaborato per intero ");
						wakeUpDataStorage(e2emsgid,senderid,sendersys, listPendenzaEnte);
					}			
				}
			} catch (Exception e) {
				logger.error(" ERROR RIPARTENZA >>>>>" + e.getMessage() + " ID = ");
			}	
			
		}		
			
			
		} catch (Exception e) {
//			e.printStackTrace();
//			if (tx!=null && tx.isActive()) tx.rollback();						
//			throw new RuntimeException("Errore RipartenzaDataStorage" + e.getMessage());
		} finally {
//			if (em!=null && em.isOpen()) em.close();
		}			
		
		
		return monData;
	}

	/**
	 * 
	 * @param em
	 * @return
	 */
	private List<Object[]> listaPendenzeSospese(EntityManager em) {
		long elapsedMilliPast = IrisProperties.getLongProperty("ripartenza.datastorage.min.elapsedmilli", (long) (8 * 60 * 60 * 1000));
		Timestamp minPastTS = new Timestamp(System.currentTimeMillis() - elapsedMilliPast);
		long elapsedMilliForgetMe = IrisProperties.getLongProperty("ripartenza.datastorage.max.elapsedmilli", (long) (5 * 24 * 60 * 60 * 1000));
		Timestamp maxPastTS = new Timestamp(System.currentTimeMillis() - elapsedMilliForgetMe);
		logger.info(" RipartenzaDataStorageManager , leggo buste in sospeso:  ");
		Query queryPendenze = em.createQuery ("SELECT pendenze.pk,pendenze.numPendenze FROM PendenzeCart pendenze " +
				" WHERE pendenze.stato = :stato " +
				" AND pendenze.timestampRicezione <= :minPastTS " +
				" AND pendenze.timestampRicezione >= :maxPastTS " + 
				" ORDER BY pendenze.pk desc ");
		queryPendenze.setParameter("stato", StatoEnum.DA_ELABORARE);
		queryPendenze.setParameter("minPastTS", minPastTS);
		queryPendenze.setParameter("maxPastTS", maxPastTS);
		List<Object[]> lins = (List) queryPendenze.getResultList();
		return lins;
	}

	/**
	 * 
	 * @param pend
	 * @param listPendenzaEnte
	 */
	private void wakeUpDataStorage(String e2emsgid, String senderId, String senderSys ,
			List<String> listPendenzaEnte) {
		//preparo il dataInput con i dati in memoria
//		DataInput input = new DataInputByte(pend.getMessaggioXml());
		//preparo il dataInput con l'indicazione dei dati su Db
		String[][] keys= new String[][]{{
								  e2emsgid,"E2EMSGID"}
								,{senderId,"SenderId"}
								,{senderSys,"SenderSys"}};
		DBReference reference= new DBReference("PENDENZE_CART",keys,"Messaggio_Xml");
		DataInput inputDb = new DataInputDb(reference);			
		try {
			
			//se la lista pendenze ente da escludere esiste allora la valorizzo
			dataStore(inputDb, e2emsgid
					+senderId
					+senderSys,listPendenzaEnte);
			
		} catch (Exception e) {
			e.printStackTrace();
			//in caso di eccezione gestire STATO per ripartenza
			logger.error(" ERRORE DATASTORAGE >>>>>" + e.getMessage());
		}						
		logger.info(" DataStorage ripartito su busta con Id = :  " + e2emsgid
																	+senderId
																	+senderSys );
	}


	/**
	 * 
	 * @param msgId 
	 * @param listPendenzaEnte 
	 * @throws Exception
	 */
	private void dataStore(DataInput input, String msgId, List<String> listPendenzaEnte) throws Exception {
		//use data storage library here
		logger.info("Calling DatastorageEngineBean Here!");
		//put key in property
//		Hashtable<String, String> hash = new Hashtable<String, String>();
//		hash.put("E2EMsgId", msgId);
//		DataStoreEngineService dataStoreEngineService=  new SpringDataStoreEngineImpl();
		
		ConcreteFactory factory = new ConcreteFactory();
		String DseImpl = System.getProperty(SystemPropertiesNames.DSE_IMPL);
		DataStoreEngineService dataStoreEngineService = factory.createDSE(DseImpl);			
		
		if (listPendenzaEnte!=null) {
			//se la lista pendenze ente da escludere esiste allora la valorizzo
//			dataStoreEngineService.store(input,listPendenzaEnte.toArray(new String[listPendenzaEnte.size()]));
			dataTx.callMeToRestart(dataStoreEngineService, input, listPendenzaEnte.toArray(new String[listPendenzaEnte.size()]));
		} else {
			dataTx.callMe(dataStoreEngineService, input);
//			dataStoreEngineService.store(input);		
		}		
		
	}

	@Override
	public MonitoringData executeApplicationTransaction(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	//	@Override
	public String executeHtml() throws Exception  {			
			
		String table = "";
		//connessione db
		EntityManager em = null;		
		try {				
			em = getManager();
			//calcolo la lista delle pendenze in stato da ELABORARE da oltre 8 ore
			//(non tutte le pendenze sono state elaborate)
			List<Object[]> lins = listaPendenzeSospese(em);
				 
			logger.info(this.getClass().getSimpleName() + "executeHtml, found " + lins.size());	
				table = "<br><br>";
				table += "<b>Lista pendenze in stato da ELABORARE da oltre 8 ore</b>";
				table += "<br>";
				table+="<TABLE border=\"\1\">";
				
				for (Object[] object : lins) {
//					Object[] object2 = (Object[]) iter.next();
					PendenzeCartPK pendPk = (PendenzeCartPK) object[0];
					Integer numPend = (Integer) object[1];
					
					table+="<TR>";
						table+="<TD>";
							table+=(pendPk.getE2emsgid());
						table+="</TD>";
						table+="<TD>";
							table+=(pendPk.getSenderid());
						table+="</TD>";
						table+="<TD>";
							table+=(pendPk.getSendersys());
						table+="</TD>";																
						table+="<TD>";
							table+=(numPend);
						table+="</TD>";										
					table+="</TR>";				
				}		
				
				table+="</TABLE>";
			} catch (Exception e) {
				logger.info(this.getClass().getSimpleName() + " ERROR EXECUTEHTML " +  e.getMessage());
			}
	//		table+="</PRE>";
			return table;
	}

	/**
	 * 
	 * @return
	 */
	public EntityManager getManager() {
		//quando siamo su JBOSS ci vuole questa riga
//		return emf.createEntityManager();
		//quando siamo su TEST JUNIT ci vuole questa riga
			return manager;
	}



}

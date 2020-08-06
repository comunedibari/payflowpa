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
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.esiti.ErroreEsitoPendenza;
import it.tasgroup.idp.esiti.EsitiModel;
import it.tasgroup.idp.esiti.EsitoPendenza;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ErrorDecoder;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
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
@Remote(CommandExecutor.class)
public class Timeout implements CommandExecutor, CommandExecutorLocal {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;

	private final Log logger = LogFactory.getLog(this.getClass());

	@EJB(beanName = "CreazioneEsito")
	private ObjectCommandExecutor CreazioneEsitoProxy;

	/**
	 *
	 */
	public MonitoringData executeApplicationTransaction() {
		//monitoring data
		MonitoringData monData = new MonitoringData();
//		System.out.println("MBEAN INFO" + monData.getMBeanInfo());

		//connessione db
		EntityManager em = null;
		try {

			em = getManager();
			List<PendenzeCart> lins = listaPendenzeTimeout(em);

			logger.info("trovati pendenze in timeout, numtotale = " + lins.size());
			//monitoring data
			monData.setNumRecord(lins.size());

			Iterator iter = lins.iterator();
			while (iter.hasNext()) {
				//recupero flusso pendenza corrente
				PendenzeCart pendCart = (PendenzeCart) iter.next();

				//scandaloso questo ulteriore try/catch, sistemare con nuovo bean
				//se la spedizione di un flusso pendenza in timeout va in errore allora
				//procede comunque con il successivo
				try {

					List<EsitoPendenza> esitoDaSpedire = new ArrayList<EsitoPendenza>();
					String e2emsgid = pendCart.getPk().getE2emsgid().trim();
					String senderId = pendCart.getPk().getSenderid().trim();
					String senderSys = pendCart.getPk().getSendersys().trim();
					String receiverId = pendCart.getReceiverid().trim();
					String receiverSys = pendCart.getReceiversys().trim();
					String trtSenderId = new String(pendCart.getTrtSenderId().trim());
					String trtSenderSys = new String(pendCart.getTrtSenderSys().trim());					

					logger.info("EsitiNecessari=" + pendCart.getNumPendenze() + " Verifico quanti esiti pendenza mancano! ");
					//OLD STYLE QUERY
//					Query queryEsitiList = manager.createQuery(" SELECT esiti FROM EsitiPendenza esiti " +
//							" WHERE esiti.pendenzeCart.pk.e2emsgid = :e2emsgid" +
//							" AND esiti.pendenzeCart.pk.senderid = :senderId " + 
//							" AND esiti.pendenzeCart.pk.sendersys = :senderSys");
					Query queryEsitiList = em.createNamedQuery("listaEsitiPendenzaRFC127AndStato");	
					queryEsitiList.setParameter("e2emsgid", e2emsgid);
					queryEsitiList.setParameter("senderId", senderId);
					queryEsitiList.setParameter("senderSys", senderSys);
					queryEsitiList.setParameter("stato", StatoEnum.COMPLETO);
					//lista esiti pendenza
					List<EsitiPendenza> linsList = queryEsitiList.getResultList();

					logger.info(this.getClass().getSimpleName() + " Costruisco esito del messaggio " +
							e2emsgid + " - " + senderId + " - " + senderSys
							+ " contenente in origine n° "
							+ pendCart.getNumPendenze() + " e sul quale ho ricevuto n° " + linsList.size() + " esitiPendenze ");

					for (int j = 0; j < linsList.size(); j++) {
						//recupero esito corrente
						EsitiPendenza esitoPendenza = (EsitiPendenza)linsList.get(j);

						//aggiungo alla lista di esiti da spedire in una busta singola
						String dexEsito = esitoPendenza.getDescrizioneEsito()!=null ? esitoPendenza.getDescrizioneEsito().trim() : "---";
						String esitoOkKo = esitoPendenza.getEsitoPendenza()!=null ? esitoPendenza.getEsitoPendenza().trim() : "---";
						String idEsitoPendenzaEnte = esitoPendenza.getIdPendenzaEnte()!=null ? esitoPendenza.getIdPendenzaEnte().trim() : "---";

						//recupero il dettaglio errori di ogni singola pendenza
						//select * from idp.ERRORI_ESITI_PENDENZA where id_esito_pendenza = '6376'
//						Query queryErroriEsitiPendenza = em.createQuery ("SELECT erroriEsitiPendenza FROM ErroriEsitiPendenza erroriEsitiPendenza " +
//								" WHERE erroriEsitiPendenza.idPendenzaEnte = :idPendenzaEnte "
//								+ " AND erroriEsitiPendenza.idEsitoPendenza.idEsitoPendenza = :idEsitoPendenza");
//						queryErroriEsitiPendenza.setParameter("idPendenzaEnte", esitoPendenza.getIdPendenzaEnte());
//						queryErroriEsitiPendenza.setParameter("idEsitoPendenza", esitoPendenza.getIdEsitoPendenza());											
//						List<ErroriEsitiPendenza> listaErroriEsitiPendenza = (List) queryErroriEsitiPendenza.getResultList();
//						logger.info(this.getClass().getSimpleName() + " Per questo esitoPendenza (idPendEnte = "
//								+ esitoPendenza.getIdPendenzaEnte() + "; "
//								+ "idEsitoPend = " + esitoPendenza.getIdEsitoPendenza() + ") "
//								+ " ci sono " + listaErroriEsitiPendenza.size() + " errori ! " );
//						Iterator iterator = listaErroriEsitiPendenza.iterator();						

						//trasformo i codici generati applicando il multilanguage
						List<ErroreEsitoPendenza> linsErroriEsitoDecoded = new ArrayList<ErroreEsitoPendenza>();
						ErrorDecoder decoder = new ErrorDecoder();
						
						//recupero la lista degli errori associati a questo esito
						Set<ErroriEsitiPendenza> erroList = esitoPendenza.getErroriEsitiPendenzaCollection();
						logger.info(this.getClass().getSimpleName() + " Per questo esitoPendenza (idPendEnte = "
								+ esitoPendenza.getIdPendenzaEnte() + "; "
								+ "idEsitoPend = " + esitoPendenza.getIdEsitoPendenza() + ") "
								+ " ci sono " + erroList.size() + " errori ! " );
						Iterator iterError = erroList.iterator();
						//sostituisco e leggo usando jpa querying
						while (iterError.hasNext()) {
							ErroriEsitiPendenza erroreEsitoPendenza = (ErroriEsitiPendenza) iterError.next();
							logger.info("Decoding Errore " + erroreEsitoPendenza.getCodice() + " -- " + idEsitoPendenzaEnte );
							ErroreEsitoPendenza erroreEP = decoder.decode(erroreEsitoPendenza);
							linsErroriEsitoDecoded.add(erroreEP);
							logger.info("Decoded Errore " + erroreEP.getCodice() + "/" + erroreEP.getDescrizione());
						}


						//aggiungo alla lista di esiti da spedire in una busta singola
						//compongo l'oggetto da inviare al template writer
						EsitoPendenza esitoP = new EsitoPendenza(StatoEnum.SERVICE_NAME_TYPE_ALL_PENDENZE,e2emsgid,senderId, senderSys, receiverId, receiverSys,
								esitoPendenza.getIdPendenza(),
								idEsitoPendenzaEnte,
								esitoPendenza.getEsitoPendenza().trim(),
								dexEsito,
								linsErroriEsitoDecoded, trtSenderId, trtSenderSys);
						esitoDaSpedire.add(esitoP);
					}

					//creo il messaggio di Esito (corretto o non corretto in funzione degli esiti)
					logger.info(this.getClass().getSimpleName() + " Spedisco la lista di esiti.. dimensione = " + esitoDaSpedire.size() );
					sendEsitoAndUpdateStatus(e2emsgid, senderId, senderSys, esitoDaSpedire, trtSenderId, trtSenderSys);

				} catch (Exception e) {
					logger.error(this.getClass().getSimpleName() + " ERROR TIMEOUT SU FLUSSO = " + pendCart.getPk().getE2emsgid());
				}
			}


		} catch (Exception e) {
//			if (tx!=null && tx.isActive()) tx.rollback();
			logger.error(this.getClass().getSimpleName() + " ERROR TIMEOUT = " + e.getMessage());
//		} catch (org.hibernate.hql.ast.QuerySyntaxException sql)  {
//			if (tx!=null && tx.isActive()) tx.rollback();
//			logger.error(this.getClass().getSimpleName() + " ERROR SQL = " + sql.getMessage());
		}
		finally {
//			if (em!=null && em.isOpen()) em.close();
		}
		return monData;
	}


	/**
	 *
	 * @param em
	 * @return
	 */
	private List<PendenzeCart> listaPendenzeTimeout(EntityManager em) {
		Timestamp currentTs = new Timestamp(System.currentTimeMillis());
		int before = 172800000;
		Timestamp hoursBefore = new Timestamp(System.currentTimeMillis()-before);						
		logger.info(" Timeout , leggo buste in sospeso:  ");
		Query queryPendenze = em.createQuery ("SELECT pendenze FROM PendenzeCart pendenze " +
				" WHERE pendenze.stato = :stato " +
				" AND pendenze.timestampRicezione<= :from " +  
				" ORDER BY pendenze.pk desc ");
		queryPendenze.setParameter("stato", StatoEnum.DA_ELABORARE);
		queryPendenze.setParameter("from", hoursBefore);
		List<PendenzeCart> lins = null;
		lins = (List) queryPendenze.getResultList();
		return lins;
	}


	/**
	 *
	 * @param em
	 * @param tx
	 * @param idEgov
	 * @param senderSys
	 * @param senderId
	 * @param esitoDaSpedire
	 * @param esitoBuilder
	 * @throws Exception
	 */
	private void sendEsitoAndUpdateStatus(String e2emsgid,
			String senderId, String senderSys, List<EsitoPendenza> esitoDaSpedire,
			String trtSenderId, String trtSenderSys)
			throws Exception {

		EsitiModel esitoModel = new EsitiModel();
		esitoModel.setE2emsgid(e2emsgid);
		esitoModel.setSenderId(senderId);
		esitoModel.setSenderSys(senderSys);
		esitoModel.setTrtSenderId(trtSenderId);
		esitoModel.setTrtSenderSys(trtSenderSys);		
		esitoModel.setPendenze(esitoDaSpedire);
		
		//imposto lo stato... in questo modo i processi successivi (SpedizioneEsiti)
		//potranno concludere l'operazione		
		esitoModel.setStato(StatoEnum.DA_SPEDIRE);
		esitoModel.setStatoFlusso(StatoEnum.IN_SPEDIZIONE);		

		CreazioneEsitoProxy.executeApplicationTransaction(esitoModel);
	}


	/**
	 * TODO FIXARE QUESTO METODO !!!
	 * @return
	 */
	public EntityManager getManager() {

//		if (emf==null) {
			//questo non va perchè siamo fuori da contesto Ejb
			//emf = Persistence.createEntityManagerFactory("IdpAppJpa");
			//questo non va perchè siamo fuori da contesto Ejb, idem
			//a causa di un messaggio poco comprensibile
//	        try {
//	            logger.info("Building JPA EntityManager for JMX Ejb");
//	            Map<String, String> properties = new HashMap<String, String>();
//	            properties.put( "openjpa.ConnectionUserName", "IDP" );
//	            properties.put( "openjpa.ConnectionPassword", "IDP" );
//	            properties.put( "openjpa.ConnectionURL","jdbc:db2://10.1.132.92:50000/IDPDB" );
//	            properties.put( "openjpa.ConnectionDriverName","com.ibm.db2.jcc.DB2Driver" );
//	            EntityManagerFactory result = Persistence.createEntityManagerFactory( "IdpAppJpa", properties );
//	            logger.info("Builds JPA EntityManager " + emf);
//	        } catch (Exception ex) {
//	            ex.printStackTrace();
//	        }
//		}

//		quando siamo su MXBEAN (da progetto di test.. cioè quello con l'emulatore EJB3)
//		ci vuole questa riga
//		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("IdpAppJpa");
//		EntityManager em = emFactory.createEntityManager();
//		return em;
//		quando siamo su JBOSS ci vuole questa riga
//		return emf.createEntityManager();
//		quando siamo su TEST JUNIT ci vuole questa riga
		return manager;
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
		//calcolo la lista delle pendenze per cui gli esiti sono pronti
		//per essere creati e spediti
		List<PendenzeCart> lins = listaPendenzeTimeout(em);

		logger.info(this.getClass().getSimpleName() + "executeHtml, found " + lins.size());
		table = "<br><br>";
		table+="<TABLE border=\"\1\">";
		table+="<TR>";
			table+="<TD>ID</TD>";
			table+="<TD>SENDERID</TD>";
			table+="<TD>SENDERSYS</TD>";
			table+="<TD>STATO</TD>";
			table+="<TD>TS RICEZIONE</TD>";
			table+="<TD>NUM PENDENZE</TD>";
		table+="</TR>";

		for (PendenzeCart object : lins) {
			table+="<TR>";
				table+="<TD>";
					table+=(object.getPk().getE2emsgid());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getPk().getSenderid());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getPk().getSendersys());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getStato());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getTimestampRicezione());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getNumPendenze());
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


}

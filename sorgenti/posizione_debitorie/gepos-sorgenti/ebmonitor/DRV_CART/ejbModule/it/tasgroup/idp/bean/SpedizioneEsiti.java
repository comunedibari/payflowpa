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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

import it.tasgroup.idp.domain.messaggi.ErroriEsitiPendenza;
import it.tasgroup.idp.domain.messaggi.EsitiPendenza;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.esiti.ErroreEsitoPendenza;
import it.tasgroup.idp.esiti.EsitiModel;
import it.tasgroup.idp.esiti.EsitoPendenza;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ErrorDecoder;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

@Interceptors(MonitoringInterceptor.class)
@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
@Remote(CommandExecutor.class)
public class SpedizioneEsiti implements CommandExecutor, CommandExecutorLocal, ObjectCommandExecutorLocal {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;

	private final Log logger = LogFactory.getLog(this.getClass());

	@EJB(beanName = "CreazioneEsito")
	private ObjectCommandExecutor CreazioneEsitoProxy;

	
	public final static String FINESTRATURA_SPEDIZIONE_ESITI_QUERY="finestratura.query.spedizione.esiti";
	public final static Integer DEFAULT_FINESTRATURA = 1000;

	/**
	 *  Metodo di attivazione chiamato da SpedizioneEsitiTimer:
	 *  Esegue estrazione di tutti gli esiti "pronti" da creare e spedire
	 *  sulla base di query su PendenzeCart ed EsitiPendenza
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction() {
		return extractAndSaveEsiti(null);
	}

	/**
	 *  Metodo di attivazione chiamato in caso di pagamenti OTF:
	 *  Esegue estrazione di esito da spedire specifico, sul messaggio OTF trattato in quel momento
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction(Object data) {
		EsitiModel refEsitoOTF = (EsitiModel)data;		
		return this.extractAndSaveEsiti(refEsitoOTF);
	}	

	
	/**
	 * Metodo che effettua l'estrazione ed il salvataggio degli esiti da creare.
	 * @param 
	 * @return
	 */
	private MonitoringData extractAndSaveEsiti(EsitiModel refEsitoOTF) {
		//monitoring data
		MonitoringData monData = new MonitoringData();

		try {

			//calcolo la lista delle pendenze per cui gli esiti sono pronti
			//per essere creati e spediti
			logger.info(" ready to extract rfc127 to be replied to " );
			List<PendenzeCart> listaPend = listaPendCartConEsitiDaSpedire(manager,refEsitoOTF);
			logger.info(" extracted " + listaPend.size() + " rfc127 to be replied to " );
			List<PendenzeCart> lins = extractListaPendenzeCartDaEsitare(listaPend);
			logger.info(" extracted " + lins.size() + " esiti rfc127 to be sent " );	

			logger.info(" trovati esiti da spedire a Cart, numtotale = " + lins.size()  );
			//monitoring data
			monData.setNumRecord(lins.size());

			//questo metodo era un p� da sistemare....  Solo un po'?


			for (int i = 0; i < lins.size(); i++) {
				//recupero esito corrente
				PendenzeCart pendenzaDaEsitare = (PendenzeCart)lins.get(i);
				
				String e2emsgid = pendenzaDaEsitare.getPk().getE2emsgid();
				String senderId = pendenzaDaEsitare.getPk().getSenderid();
				String senderSys = pendenzaDaEsitare.getPk().getSendersys();
				String receiverId = pendenzaDaEsitare.getReceiverid() ;
				String receiverSys = pendenzaDaEsitare.getReceiversys();
				String trtSenderId = pendenzaDaEsitare.getTrtSenderId();
				String trtSenderSys = pendenzaDaEsitare.getTrtSenderSys();

				//scandaloso questo ulteriore try/catch, sistemare con nuovo bean
				//se la spedizione di un flusso pendenza va in errore allora
				//procede comunque con il successivo
				try {
					
					boolean pendenze =  pendenzaDaEsitare.getTipoMessaggio().trim().equals(StatoEnum.MSG_TYPE_ALL_PENDENZE) || 
										pendenzaDaEsitare.getTipoMessaggio().trim().equals(StatoEnum.MSG_TYPE_ALL_PENDENZE_OTF) ||
										pendenzaDaEsitare.getTipoMessaggio().trim().equals(StatoEnum.MSG_TYPE_ALL_PENDENZE_SYNC)||
										pendenzaDaEsitare.getTipoMessaggio().trim().equals(StatoEnum.MSG_TYPE_ALL_PENDENZE_SSIL);
					
					String serviceNameType = new String(pendenze ? StatoEnum.SERVICE_NAME_TYPE_ALL_PENDENZE : StatoEnum.SERVICE_NAME_TYPE_ALL_MASSIVO_PENDENZE  );
					String tipoEsito = new String(pendenzaDaEsitare.getStato().trim()  );					
					
					//tipoEsito pu� essere ...
					//in caso di allineamento massivo
					//ESITO_KO_DA_CREARE
					//ESITO_OK_DA_CREARE

					List<EsitoPendenza> esitoDaSpedire = new ArrayList<EsitoPendenza>();
					
					if (StatoEnum.SERVICE_NAME_TYPE_ALL_PENDENZE.equals(serviceNameType)) {

						logger.info(" EsitoCart n� " + i + ", contiene n� " +pendenzaDaEsitare.getNumPendenze());

						//recupero gli esitiPendenza dell'EsitoCart da costruire
						List<EsitiPendenza> listaEsitiPendenza = getEsitiInserimentiPendenze(manager, pendenzaDaEsitare);

						logger.info(" Costruisco esito del messaggio " + pendenzaDaEsitare.getPk().getE2emsgid() + "/" + pendenzaDaEsitare.getPk().getSenderid() + "/" + pendenzaDaEsitare.getPk().getSendersys()
									+ " contenente n� " + pendenzaDaEsitare.getNumPendenze() + " esitiPendenza, " +
											"di cui " + listaEsitiPendenza.size() + " trovate !");
						Iterator<EsitiPendenza> iterEsitiP = listaEsitiPendenza.iterator();
					
						//costruisco la lista di oggetti EsitiPendenza per usarla poi con stringTemplate
						creaListaEsitiPerCreazioneFlussoXml(serviceNameType,
								e2emsgid, senderId, senderSys, receiverId,
								receiverSys, esitoDaSpedire, iterEsitiP, trtSenderId, trtSenderSys);						
												
					} else {
						
						throw new UnsupportedOperationException("Allineamento Massivo Non Supportato");
						
					}

					//creo il messaggio di Esito (corretto o non corretto in funzione degli esiti)
					logger.info(" Spedisco la lista di esiti.. dimensione = " + esitoDaSpedire.size() );
					//spedisco l'esito XML alla rete CART
					boolean isOTF = refEsitoOTF != null && refEsitoOTF.isOTF;
					String esitoXml = sendEsitoAndUpdateStatus(e2emsgid.trim(), senderId.trim(), senderSys.trim(), esitoDaSpedire, trtSenderId, trtSenderSys, isOTF);
					monData.setEsito(esitoXml);

				} catch (Exception e) {
					logger.error(" ERROR SPEDIZIONE SU FLUSSO = " + e2emsgid,e);
				}

			}

			logger.info(" creazione/spedizione esiti terminata " );

		} catch (Exception e) {
			logger.error(" ERROR SPEDIZIONE ESITI = " + e.getMessage(),e);
			e.printStackTrace();
		}
		finally {
//			if (em!=null && em.isOpen()) em.close();
		}
		return monData;
	}
	

	/**
	 * 
	 * @param serviceNameType
	 * @param e2emsgid
	 * @param senderId
	 * @param senderSys
	 * @param receiverId
	 * @param receiverSys
	 * @param esitoDaSpedire
	 * @param iterEsitiP
	 * @param trtReceiverSys 
	 * @param trtReceiverId 
	 */
	private void creaListaEsitiPerCreazioneFlussoXml(String serviceNameType,
			String e2emsgid, String senderId, String senderSys,
			String receiverId, String receiverSys,
			List<EsitoPendenza> esitoDaSpedire,
			Iterator<EsitiPendenza> iterEsitiP, String trtReceiverId, String trtReceiverSys) {
		
		while (iterEsitiP.hasNext()) {
			EsitiPendenza esitoPendenza = (EsitiPendenza) iterEsitiP.next();

			//recupero esito corrente
//						EsitiPendenza esitoPendenza = (EsitiPendenza)listaEsitiPendenza.get(j);
			logger.info(" Elaboro esitoPendenza fase 1, id =  " + esitoPendenza.getIdEsitoPendenza()
					+ " idPend = " + esitoPendenza.getIdPendenza()
					+ " idPendEnte = " + esitoPendenza.getIdPendenzaEnte()
					+ " stato = " + esitoPendenza.getStato()
					+ " esitoPend = " + esitoPendenza.getEsitoPendenza()
					+ " dexEsito = " + esitoPendenza.getDescrizioneEsito()
					+ " tsInsert = " + esitoPendenza.getTsInserimento() );
			//creo var temporanea per bug lettura campo IdPendenzaEnte in oggetto esitoPendenza,
			//il campo campo IdPendenzaEnte andr� a recuperarlo dentro alla lista ErroriEsitiPendenzaCollection
			String idPendEnte = "";

			//recupero la lista degli errori associati a questo esito
			Set<ErroriEsitiPendenza> erroList = esitoPendenza.getErroriEsitiPendenzaCollection();			
			Iterator iterError = erroList.iterator();

			//trasformo i codici ottenuti applicando il multilanguage
			List<ErroreEsitoPendenza> linsErroriEsitoDecoded = new ArrayList<ErroreEsitoPendenza>();
			ErrorDecoder decoder = new ErrorDecoder();

			//decodifico gli errori
			idPendEnte = decodificaErrori(idPendEnte, iterError,
					linsErroriEsitoDecoded, decoder);
			
			//compongo l'oggetto da inviare al template writer
			EsitoPendenza esitoP = new EsitoPendenza(serviceNameType,e2emsgid,senderId, senderSys, receiverId, receiverSys,
					esitoPendenza.getIdPendenza(),
//								(esitoPendenza.getIdPendenzaEnte()!=null ? esitoPendenza.getIdPendenzaEnte().trim() : "---"),
					(idPendEnte!=null ? idPendEnte.trim() : "---"),
					esitoPendenza.getEsitoPendenza().trim(),
					esitoPendenza.getDescrizioneEsito()!=null ? esitoPendenza.getDescrizioneEsito().trim() : "---",
					linsErroriEsitoDecoded, trtReceiverId, trtReceiverSys);
			logger.info(" Esito da Spedire decodificato " + esitoP.toString());
			esitoDaSpedire.add(esitoP);
		}
	}


	/**
	 * 
	 * @param idPendEnte
	 * @param iterError
	 * @param linsErroriEsitoDecoded
	 * @param decoder
	 * @return
	 */
	private String decodificaErrori(String idPendEnte, Iterator iterError,
			List<ErroreEsitoPendenza> linsErroriEsitoDecoded,
			ErrorDecoder decoder) {
		while (iterError.hasNext()) {
			ErroriEsitiPendenza erroreEsito = (ErroriEsitiPendenza) iterError.next();
			logger.info(" Elaboro esitoPendenza, lista Errori associati" +
					" id =  " + erroreEsito.getIdErrore()
					+ " idPend = " + erroreEsito.getIdPendenza()
					+ " idPendEnte = " + erroreEsito.getIdPendenzaEnte()
					+ " stato = " + erroreEsito.getCodice()
//									+ " dexEsito = " + erroreEsito.getDescrizioneErrore()
					+ " idEsito = " + erroreEsito.getIdEsitoPendenza() );
			//recupero campo corretto (bug jps)
			idPendEnte = erroreEsito.getIdPendenzaEnte();

			logger.info("Decoding Errore " + erroreEsito.getCodice() + " -- " + erroreEsito.getIdPendenzaEnte() );
			ErroreEsitoPendenza erroreEP = decoder.decode(erroreEsito);
			linsErroriEsitoDecoded.add(erroreEP);
			logger.info("Decoded Errore " + erroreEP.getCodice() + "/" + erroreEP.getDescrizione());
		}
		return idPendEnte;
	}

	/**
	 * 
	 * @param em
	 * @param cart
	 * @return
	 */
	private List<EsitiPendenza> getEsitiInserimentiPendenze(EntityManager em,
			PendenzeCart cart) {		
		//OLD STYLE QUERY
//		Query queryEsitiPendenze = em.createQuery ("SELECT esitiPendenze FROM EsitiPendenza esitiPendenze " +
//				" WHERE esitiPendenze.stato = :stato " +
//				" AND esitiPendenze.pendenzeCart = :cart "
//				);
		Query queryEsitiPendenze = em.createNamedQuery("listaEsitiPendenzaPerStato");		
		queryEsitiPendenze.setParameter("stato", StatoEnum.COMPLETO);
		queryEsitiPendenze.setParameter("cart", cart);
		//lista esiti pendenza
		List<EsitiPendenza> listaEsitiPendenza = (List) queryEsitiPendenze.getResultList();
		return listaEsitiPendenza;
	}


	/**
	 * Non pi� usata dai processi di backend. Lasciata a vecchio da interfaccia JMX per cross check con nuove gestioni. 
	 *
 	 * @param em
	 * @return
	 */
	private List<EsitiPendenza> listaEsitiDaSpedire(EntityManager em) {
		List<EsitiPendenza> lins = null;
		//recupero lista esiti da spedire
		//cerco le pendenze i cui esiti sono tutti in stato COMPLETO
		//questa sarebbe di molto meglio scriverla come QUERY JPA
		//scriverla come NativeQuery comporta l'output di tutte le colonne in output (vedi sotto)
		Query queryEsiti = em.createNativeQuery("SELECT esiti.E2EMSGID as E2EMSGID, " +
				" esiti.senderId as senderId, esiti.senderSys as senderSys " +
				" ,max(esiti.id_esito_Pendenza) as ID_ESITO_PENDENZA " +
				" ,max(esiti.id_Pendenza) as id_Pendenza " +
				" ,max(esiti.id_Pendenza_ente) as id_Pendenza_ente " +
				" ,max(esiti.esito_Pendenza) as esito_Pendenza" +
				" ,max(esiti.TS_INSERIMENTO) as TS_INSERIMENTO" +
				" ,max(esiti.OP_INSERIMENTO) as OP_INSERIMENTO" +
				" ,max(esiti.pr_versione) as pr_versione" +
				" ,max(esiti.stato) as stato" +
				" ,max(esiti.descrizione_Esito) as descrizione_Esito " +
				" ,max(pend.num_Pendenze) as num_Pendenze " +
				" ,max(pend.trt_senderid) as trt_senderid " +
				" ,max(pend.trt_sendersys) as trt_sendersys " +
				" FROM ESITI_PENDENZA  esiti " +
				" LEFT OUTER JOIN PENDENZE_CART  pend  " +
				" ON (esiti.E2EMSGID=pend.E2EMSGID AND esiti.SenderId = pend.SenderId AND esiti.SenderSys=pend.SenderSys) " +
				" WHERE esiti.stato = :stato " +
				" AND (pend.stato = :statoPend " +
				"	OR pend.stato = :statoPend2" +
				"	OR pend.stato = :statoPend3)" +
//				+ getFiltroListaEsiti() +
				" AND (pend.tipo_Messaggio = :tipoMessaggio1 OR pend.tipo_Messaggio = :tipoMessaggio2)" + 
				" group by esiti.E2EMSGID, esiti.SenderId, esiti.SenderSys  " +
				" having count(esiti.e2emsgid)=max(pend.num_Pendenze) " +
				" ORDER BY esiti.E2EMSGID, esiti.SenderId, esiti.SenderSys desc ",EsitiPendenza.class);

		//ciclo su array di object e crea array di EsitiPendenza
		queryEsiti.setParameter("stato", StatoEnum.COMPLETO);
		
		queryEsiti.setParameter("statoPend", StatoEnum.DA_ELABORARE);
		queryEsiti.setParameter("statoPend2", StatoEnum.ESITO_KO_DA_CREARE);
		queryEsiti.setParameter("statoPend3", StatoEnum.ESITO_OK_DA_CREARE);
		
//		if (!"".equals(getFiltroListaEsiti())) {
//			queryEsiti.setParameter("E2EMSGID", e2emsgid);
//			queryEsiti.setParameter("SenderId", senderId);
//			queryEsiti.setParameter("SenderSys", senderSys);
//			queryEsiti.setParameter("tipoMessaggio1", StatoEnum.MSG_TYPE_ALL_PENDENZE_OTF);
//			queryEsiti.setParameter("tipoMessaggio2", StatoEnum.MSG_TYPE_ALL_PENDENZE_SYNC);
//		} else {
//			queryEsiti.setParameter("tipoMessaggio1", StatoEnum.MSG_TYPE_ALL_PENDENZE);
//			queryEsiti.setParameter("tipoMessaggio2", StatoEnum.MSG_TYPE_ALL_PENDENZE);
//		}
		
		lins = (List) queryEsiti.getResultList();
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
	 * @param trtSenderSys 
	 * @param trtSenderId 
	 * @param esitoBuilder
	 * @throws Exception
	 */
	private String sendEsitoAndUpdateStatus(String e2emsgid,
			String senderId, String senderSys, List<EsitoPendenza> esitoDaSpedire, String trtSenderId, String trtSenderSys, boolean isOTF)
			throws Exception {
	
		EsitiModel esitoModel = new EsitiModel();
		esitoModel.setE2emsgid(e2emsgid);
		esitoModel.setSenderId(senderId);
		esitoModel.setSenderSys(senderSys);
		esitoModel.setTrtSenderId(trtSenderId);
		esitoModel.setTrtSenderSys(trtSenderSys);
		esitoModel.setPendenze(esitoDaSpedire);
		
		if (!isOTF) {
			//se non ho filtrato per msgid,id e sil, allora significa che il messaggio
			//� arrivato su coda jms ... ed imposto lo stato 'DA SPEDIRE'
			//in questo modo sar� rispedito su coda JMS
			esitoModel.setStato(StatoEnum.DA_SPEDIRE);
			esitoModel.setStatoFlusso(StatoEnum.IN_SPEDIZIONE);
		} else {
			//altrimenti significa che � arrivato su WS
			//e setto lo stato ... in modo che si risponda su WS
			esitoModel.setStato(StatoEnum.DA_SPEDIRE_WS);
			esitoModel.setStatoFlusso(StatoEnum.RISPOSTA_INVIATA_WS);
		}				
		

		MonitoringData esitoDaRitornareSuWs = (MonitoringData)CreazioneEsitoProxy.executeApplicationTransaction(esitoModel);
		return esitoDaRitornareSuWs.getEsito();
	}

	
	/**
	 * TODO FIXARE QUESTO METODO !!!
	 * @return
	 */
	public EntityManager getManager() {
//			if (emf==null) {
//				Connection  connection = null;
//		        try {
//		            logger.info("Starting in-memory HSQL database for unit tests");
//		            Class.forName("com.ibm.db2.jcc.DB2Driver");
//		            connection = DriverManager.getConnection("jdbc:db2://10.1.132.92:50000/IDPDB", "IDP", "IDP");
//		        } catch (Exception ex) {
//		            ex.printStackTrace();
//		        }
//		        try {
//		            logger.info("Building JPA EntityManager for unit tests");
//		            emf = Persistence.createEntityManagerFactory("IdpAppJpa");
//		            em = emFactory.createEntityManager();
//		            logger.info("here we are!!!");
//		        } catch (Exception ex) {
//		            ex.printStackTrace();
//		        }
//			}
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
	public String executeHtml() throws Exception  {

	String table = "";
	//connessione db
	EntityManager em = null;
	try {
		em = getManager();
		//calcolo la lista delle pendenze per cui gli esiti sono pronti
		//per essere creati e spediti
		List<EsitiPendenza> lins = listaEsitiDaSpedire(em);

		logger.info("executeHtml, found " + lins.size());
		table = "<br><br>";
		table += "<b>Lista pendenze il cui esito � pronto per essere creato e spedito</b>";
		table += "<br>";
		table+="<TABLE border=\"\1\">";
		table+="<TR>";
			table+="<TD>ID</TD>";
			table+="<TD>SENDERID</TD>";
			table+="<TD>SENDERSYS</TD>";
			table+="<TD>STATO</TD>";
			table+="<TD>TS RICEZIONE</TD>";
			table+="<TD>NUM PENDENZE</TD>";
		table+="</TR>";

		for (EsitiPendenza object : lins) {
			table+="<TR>";
				table+="<TD>";
					table+=(object.getPendenzeCart().getPk().getE2emsgid());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getPendenzeCart().getPk().getSenderid());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getPendenzeCart().getPk().getSendersys());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getPendenzeCart().getStato());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getPendenzeCart().getTimestampRicezione());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getPendenzeCart().getNumPendenze());
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



	@Override
	public String executeHtml(String msg, String sendId, String sendSys) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * Filtra la lista in ingresso. Include in output solo i record di "PendenzeCart" completi. Ovvero che hanno TUTTE 
	 * le pendenze elaborate.
	 * 
	 * @param 
	 * @return
	 */
	private List<PendenzeCart> extractListaPendenzeCartDaEsitare(List<PendenzeCart> lins) {
		
		List<PendenzeCart> listaDaEsitare = new ArrayList<PendenzeCart>();
		
		for (Iterator iterator = lins.iterator(); iterator.hasNext();) {
			PendenzeCart pendenzeCart = (PendenzeCart) iterator.next();			
			logger.info(" cerco esiti del messaggio PendCart " + pendenzeCart.getPk().getE2emsgid() + pendenzeCart.getPk().getSenderid() );

			Query queryListaEsiti =  manager.createNamedQuery("listaCountEsitiPendenza");			
			queryListaEsiti.setParameter("cart", pendenzeCart);
			queryListaEsiti.setParameter("stato", StatoEnum.COMPLETO);
			Long listaEsiti = (Long) queryListaEsiti.getSingleResult();
			logger.info(" trovati n. esiti " + listaEsiti );		
			
			//aggiungo la pendenza..  ma solo se � arrivato tutto
			if (pendenzeCart.getNumPendenze()==listaEsiti.intValue()) {
				listaDaEsitare.add(pendenzeCart);
				logger.debug(" Aggiunto messaggio da creare: ("+pendenzeCart.getPk().getSenderid()+"-"+pendenzeCart.getPk().getSendersys()+"-"+pendenzeCart.getPk().getE2emsgid()+") tot.Pendenze="+ listaEsiti.intValue());
			} else {
				logger.debug(" PendCart NON completo " );
			}			
		}
		return listaDaEsitare;
	}


	/**
	 *
	 * @param em
	 * @return
	 */
	private List<PendenzeCart> listaPendCartConEsitiDaSpedire(EntityManager em, EsitiModel refEsitoOTF) {

		// le tre tipologie chiamanti (mutuamente esclusive)
		boolean fromTimer = refEsitoOTF == null;
		boolean fromOTF   = !fromTimer && refEsitoOTF.isOTF(); 
		boolean fromSSIL  = !fromTimer && !refEsitoOTF.isOTF();
		
		
		List<PendenzeCart> lins = null;
				
			//recupero lista pend. da elaborare
		/*
		 * String queryABitBetter = "SELECT * " + " FROM PENDENZE_CART pend " +
		 * " WHERE 	 " + "  (pend.stato = :statoPend " +
		 * "	OR pend.stato = :statoPend2" +"	OR pend.stato = :statoPend3) "+
		 * " AND (pend.tipo_Messaggio = :tipoMessaggio1 OR pend.tipo_Messaggio = :tipoMessaggio2)"
		 * ; 
		 * Query queryEsiti = em.createNativeQuery(queryABitBetter,PendenzeCart.class);//.setMaxResults(Integer.parseInt(maxResults));
		 * 
		 * Ho dovuto cambiare questa query nativa (commentata sopra) utilizzata per prendere le pendenzeCart, 
		 * in quanto con l'aggiunta del setMaxResult,per finestrare la query, 
		 * incappava in un errore del dialetto di DB2 come descritto in questa pagina:
		 * {@link https://hibernate.atlassian.net/browse/HHH-3344}
		 * 
		 * Per farla funzionare ho cambiato la query in una query jpa, che non incappa in quel problema.
		 * (vedere query non commentata)
		 */
		
			String queryABitBetter = "SELECT pend "  + 					
					" FROM PendenzeCart pend " +  
					" WHERE 	 " + 
					"  (pend.stato = :da_elaborare " +
					"	OR pend.stato = :esito_ko_da_creare" +
					"	OR pend.stato = :esito_ok_da_creare)";
			
			if (fromTimer) {
				queryABitBetter += " AND (pend.tipoMessaggio = :msg_type_all_pendenze OR (pend.tipoMessaggio = :msg_type_all_pendenze_ssil AND pend.timestampRicezione <= :pastTS))";
			} else {
				if (fromOTF) {
					queryABitBetter += " AND (pend.tipoMessaggio = :msg_type_all_pendenze_otf OR pend.tipoMessaggio = :msg_type_all_pendenze_sync)";
				} else if (fromSSIL) {
					queryABitBetter += " AND pend.tipoMessaggio = :msg_type_all_pendenze_ssil";
				}
				queryABitBetter += " AND pend.pk.e2emsgid = :e2emsgid AND pend.pk.senderid = :senderid AND pend.pk.sendersys = :sendersys ";
			}
			
			logger.info(" QUERY FOR EXTRACTING \n " +  queryABitBetter );
			
			String maxResults = IrisProperties.getProperty(FINESTRATURA_SPEDIZIONE_ESITI_QUERY, Integer.toString(DEFAULT_FINESTRATURA));
			Query queryEsiti = em.createQuery(queryABitBetter).setMaxResults(Integer.parseInt(maxResults));

			//queryEsiti.setParameter("stato", StatoEnum.COMPLETO);		
			queryEsiti.setParameter("da_elaborare", StatoEnum.DA_ELABORARE);
			queryEsiti.setParameter("esito_ko_da_creare", StatoEnum.ESITO_KO_DA_CREARE);
			queryEsiti.setParameter("esito_ok_da_creare", StatoEnum.ESITO_OK_DA_CREARE);
			
			if (fromTimer) {
				queryEsiti.setParameter("msg_type_all_pendenze", StatoEnum.MSG_TYPE_ALL_PENDENZE);
				queryEsiti.setParameter("msg_type_all_pendenze_ssil", StatoEnum.MSG_TYPE_ALL_PENDENZE_SSIL);
				long elapsedMilli = IrisProperties.getLongProperty("spedizione.esiti.ssil.min.elapsedmilli", (long) (15 * 60 * 1000));
				Timestamp pastTS = new Timestamp(System.currentTimeMillis() - elapsedMilli);
				queryEsiti.setParameter("pastTS", pastTS);
			} else {
				if (fromOTF) {
					queryEsiti.setParameter("msg_type_all_pendenze_otf", StatoEnum.MSG_TYPE_ALL_PENDENZE_OTF);
					queryEsiti.setParameter("msg_type_all_pendenze_sync", StatoEnum.MSG_TYPE_ALL_PENDENZE_SYNC);
				} else if (fromSSIL) {
					queryEsiti.setParameter("msg_type_all_pendenze_ssil", StatoEnum.MSG_TYPE_ALL_PENDENZE_SSIL);
				}
				queryEsiti.setParameter("e2emsgid", refEsitoOTF.e2emsgid);
				queryEsiti.setParameter("senderid", refEsitoOTF.senderId);
				queryEsiti.setParameter("sendersys", refEsitoOTF.senderSys);
			}
			
			lins = (List) queryEsiti.getResultList();
		return lins;
	}


//	@Override
//	public MonitoringData executeApplicationTransaction(Object data) {
//		// TODO Auto-generated method stub
//		return null;
//	}


}

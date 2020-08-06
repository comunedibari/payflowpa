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
package it.tasgroup.idp.massivi;

import it.tasgroup.dse.service.DataStoreEngineService;
import it.tasgroup.dse.view.DBReference;
import it.tasgroup.dse.view.DataInput;
import it.tasgroup.dse.view.DataInputDb;
import it.tasgroup.idp.bean.MassiveCommandExecutor;
import it.tasgroup.idp.domain.messaggi.EsitiPendenza;
import it.tasgroup.idp.domain.messaggi.PendenzeCartPK;
import it.tasgroup.idp.pojo.PrevisitingData;
import it.tasgroup.idp.pojo.TimerData;
import it.tasgroup.idp.timer.AbstractTimer;
import it.tasgroup.idp.timer.ITimerLocal;
import it.tasgroup.idp.timer.ITimerRemote;
import it.tasgroup.idp.timer.TimerCommandExecutorLocal;
import it.tasgroup.idp.util.ConcreteFactory;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.SystemPropertiesNames;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ControllerUpdateMassivoTimer extends AbstractTimer implements ITimerLocal, ITimerRemote {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;

	@Resource
	private SessionContext ctx;

	@EJB(beanName = "ControllerUpdateMassivo")
	private MassiveCommandExecutor ControllerUpdateMassivoProxy;

	@EJB(beanName = "TimerController")
	TimerCommandExecutorLocal timerController;

	private final Log logger = LogFactory.getLog(this.getClass());

    protected static String DA_ELABORARE = "DA ELABORARE";
    protected static String DA_ELABORARE_MASSIVO = "DA ELABORARE_MASSIVO";

	@Timeout
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void handleTimeout(Timer timer) {

		try {
			logger.info(this.getClass().getSimpleName() + " Ejb per massivo, ControllerUpdateMassivoProxy " + ControllerUpdateMassivoProxy.getClass().getSimpleName() );
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {

			logger.info(" ControllerUpdateMassivoTimer , leggo buste in sospeso:  ");
			Query queryPendenze = manager.createQuery ("SELECT " +
					"	pendenze.pk,pendenze.numPendenze,pendenze.tipoTributo," +
					"	pendenze.timestampRicezione " +
					"	FROM PendenzeCart pendenze " +
					" 	WHERE pendenze.stato = :stato " +
					" 	ORDER BY pendenze.pk desc ");
			queryPendenze.setParameter("stato", this.DA_ELABORARE_MASSIVO);
			List<Object[]> lins = null;
			lins = (List) queryPendenze.getResultList();

			logger.info("trovati all.pendenze.massivi da elaborare, numtotale = " + lins.size());

			Iterator iter = lins.iterator();
			while (iter.hasNext()) {
				Object[] object = (Object[]) iter.next();
				PendenzeCartPK pendPk = (PendenzeCartPK) object[0];
				Integer numPend = (Integer) object[1];
				String cdTrbEnte = (String) object[2];
				Timestamp times = (Timestamp)object[3];
				//600.000 = 5 minuti
				boolean ripartenza = new Timestamp(System.currentTimeMillis()-600000).after(times);

				//preparo il PrevistingData per l'esecuzione dei bean con la biz logic
				PrevisitingData data = new PrevisitingData();
				data.setE2EMsgId(pendPk.getE2emsgid());
				data.setSilMittente(pendPk.getSendersys());
				data.setIdMittente(pendPk.getSenderid());
				data.setTipoPendenza(cdTrbEnte);

				logger.info("ripartenza all. massivo =" + ripartenza + " tsRicezione =  " + times + " now = " + new Timestamp(System.currentTimeMillis()-600000));
				logger.info("EsitiNecessari=" + numPend + " Verifico quanti esiti pendenza sono ancora mancanti ? ");

				Query queryEsitiList = manager.createQuery(" SELECT esiti FROM EsitiPendenza esiti " +
						" WHERE esiti.pendenzeCart.pk.e2emsgid = :e2emsgid" +
						" AND esiti.pendenzeCart.pk.senderid = :senderId " +
						" AND esiti.pendenzeCart.pk.sendersys = :senderSys");
				queryEsitiList.setParameter("e2emsgid", pendPk.getE2emsgid().trim());
				queryEsitiList.setParameter("senderId", pendPk.getSenderid().trim());
				queryEsitiList.setParameter("senderSys", pendPk.getSendersys().trim());
				List<EsitiPendenza> linsList = queryEsitiList.getResultList();

				logger.info("trovati esitiP arrivati (busta="+pendPk.getE2emsgid().trim()+"), numtotale = " + linsList.size() + " su di un totale di " + numPend);
				if (linsList.size()<numPend && ripartenza) { //qui secondo me bisogna andare in else e cancellare tutto ciò che era stato inserito!!!

					//prima della ripartenza è bene aspettare un pò di tempo!!!

					logger.info("Ripartenza Massiva su e2emsgId = " + pendPk.getE2emsgid() + ", escludendo n° " + linsList.size() + "pendenze");

					List<String> listPendenzaEnte = new ArrayList<String>();
					EsitiPendenza esitoPendSospeso = null;
					for (int i = 0; i < linsList.size(); i++) {
						//recupero esito corrente
						esitoPendSospeso = linsList.get(i);
						//aggiungo (idPendenzaEnte) alla lista di esiti da NON rielaborare
						logger.info(" Non rielaborare, idPendenzaEnte = " + esitoPendSospeso.getIdPendenzaEnte() );
						listPendenzaEnte.add(esitoPendSospeso.getIdPendenzaEnte().trim());
					}

					//se la lista non è vuota ne spedisco il contenuto
					if (listPendenzaEnte.size()>0) {
						logger.info(" Invio al DS il segnale di ripartenza sulla Pendenza " + esitoPendSospeso.getPendenzeCart().getPk().getE2emsgid().trim()
								+esitoPendSospeso.getPendenzeCart().getPk().getSenderid().trim()
								+esitoPendSospeso.getPendenzeCart().getPk().getSendersys().trim()
								+ " , elementi da NON rielaborare = n° " + listPendenzaEnte.size() );
						wakeUpDataStorage(esitoPendSospeso.getPendenzeCart().getPk().getE2emsgid().trim(),
								esitoPendSospeso.getPendenzeCart().getPk().getSenderid().trim(),
								esitoPendSospeso.getPendenzeCart().getPk().getSendersys().trim(),listPendenzaEnte);
					} else {
						logger.info(" Il flusso " + pendPk.getE2emsgid().trim() + " deve essere rielaborato per intero ");
					}

				} else if (linsList.size()==numPend) { //qui secondo me bisogna andare in else e cancellare tutto ciò che era stato inserito!!!
					logger.info("Tutti gli esitiPend, sono arrivati ! (n° = " + linsList.size() + ")");

					EsitiPendenza esitoPendSospeso = null;
					boolean esito100 = true;
					for (int i = 0; i < linsList.size(); i++) {
						//recupero esito corrente
						esitoPendSospeso = linsList.get(i);
						logger.info("esito corrente = " + esitoPendSospeso.getEsitoPendenza() + " idPend " + esitoPendSospeso.getIdPendenza() + " idpendente " + esitoPendSospeso.getIdPendenzaEnte() );
						logger.info("esito corrente = " + linsList.get(i).getEsitoPendenza() + " idPend " + linsList.get(i).getIdPendenza() + " idpendente " + linsList.get(i).getIdPendenzaEnte() );
						if ("KO".equals(esitoPendSospeso.getEsitoPendenza().trim())) {
							//APPENA TROVO UN ESITO KO... SIGNIFICA CHE IL MASSIVO NON E' ANDATO BENE
							//DEVO RIALLINEARE TUTTO !!!!
							logger.info(" trovato il primo esito KO !!! Devo procedere con il ripristino");
							esito100 = false;
							break;
						}
					}

					data.setAllMassivoCompletato(esito100);
					ControllerUpdateMassivoProxy.executeApplicationTransaction(data);

					// se ho finito (ripristinando o cancellando non importa
					// come...) adesso devo stoppare il timer
					// cioè me stesso

					//utilizzo il bean per le operazioni sui timer
					TimerData timerData = new TimerData(ServiceLocalMapper.ControllerUpdateMassivoTimer_NAME, TimerData.Action.STOP);
					timerController.executeApplicationTransaction(timerData);

				}


			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" ERRORE Controller Update Massivo : " + e.getMessage(),e);
		} catch (Throwable ez) {
			ez.printStackTrace();
			logger.error(" ERRORE Controller Update Massivo : " + ez.getMessage(),ez);
		}


	}

	private void wakeUpDataStorage(String e2emsgid, String senderId, String senderSys, List<String> listPendenzaEnte) {
		// preparo il dataInput con i dati in memoria
		// DataInput input = new DataInputByte(pend.getMessaggioXml());
		// preparo il dataInput con l'indicazione dei dati su Db
		String[][] keys = new String[][] { { e2emsgid, "E2EMSGID" }, { senderId, "SenderId" },
				{ senderSys, "SenderSys" } };
		DBReference reference = new DBReference("PENDENZE_CART", keys, "Messaggio_Xml");
		DataInput inputDb = new DataInputDb(reference);
		try {

			// se la lista pendenze ente da escludere esiste allora la valorizzo
			dataStore(inputDb, e2emsgid + senderId + senderSys, listPendenzaEnte);

		} catch (Exception e) {
			e.printStackTrace();
			// in caso di eccezione gestire STATO per ripartenza
			logger.info(" ERRORE DATASTORAGE >>>>>" + e.getMessage());
		}
		logger.info(" DataStorage ripartito su busta con Id = :  " + e2emsgid + senderId + senderSys);
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
			dataStoreEngineService.store(input,listPendenzaEnte.toArray(new String[listPendenzaEnte.size()]));
		} else {
			dataStoreEngineService.store(input);
		}

	}




}

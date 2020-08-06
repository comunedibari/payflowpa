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

import it.tasgroup.idp.bean.MassiveCommandExecutor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.pojo.PrevisitingData;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@Deprecated
public class ControllerUpdateMassivo implements MassiveCommandExecutor {


	private final Log logger = LogFactory.getLog(this.getClass());

	@EJB(beanName = "CancellaFisicaPendenzeOriginali")
	private MassiveCommandExecutor CancellaFisicaPendenzeOriginaliProxy;

	@EJB(beanName = "RipristinaPendenzeOriginali")
	private MassiveCommandExecutor RipristinaPendenzeOriginaliProxy;

	@EJB(beanName = "DeleteNuovePendenzeMassive")
	private MassiveCommandExecutor DeleteNuovePendenzeOriginaliProxy;

	/**
	 *
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction(Object data) {
		//monitoring data
		MonitoringData monData = new MonitoringData();

		//recupero i dati di input
		PrevisitingData prevData = (PrevisitingData)data;

		String e2eMsgId = prevData.getE2EMsgId();
		String senderId = prevData.getIdMittente();
		String silMittente = prevData.getSilMittente();
		String cdTrbEnte = prevData.getTipoPendenza();

		//setting dei dati per monitoraggio
		monData.setE2emsgid(e2eMsgId);
		monData.setSenderid(senderId);
		monData.setSendersys(silMittente);

		logger.info(" ControllerUpdateMassivo , e2emsgId / senderId / senderSys / tipoPendenza = " +  e2eMsgId + " / " +  senderId + " / " +  silMittente + " / " + cdTrbEnte);

//		EntityManager em = null;
//		EntityTransaction tx = null;
//		em = getManager();

		try {

			//devo controllare l'esito...
			if (prevData.isAllMassivoCompletato()) {
				logger.info(" Start Delete Fisica " );
				//se tutti sono OK allora
				// - cancella definitivamente le pendenze originali
	//		    deleteFisica(pendPk.getE2emsgid(), pendPk.getSenderid(), pendPk.getSendersys(),cdTrbEnte);
			    CancellaFisicaPendenzeOriginaliProxy.executeApplicationTransaction(data);
	//			updateStatoPendenza(pendPk.getE2emsgid(), pendPk.getSenderid(), pendPk.getSendersys(), ESITO_OK_DA_CREARE);
			    logger.info(" End Delete Fisica " );
			} else {
				logger.info(" Start Ripristino + Delete Nuove " );

				//NOTA LB 19/07/2011
				//devo prima cancellare le nuove pendenze
				//e poi ripristinare le vecchie per problemi con le PK e FK
				//IMPORTANTE !!!

				// - cancello le nuove pendenze inserite
				//			deleteNuovePendenze(pendPk.getSenderid(), pendPk.getSendersys(),cdTrbEnte,pendPk.getE2emsgid().trim());
				//uso il bean anzichè il metod
				DeleteNuovePendenzeOriginaliProxy.executeApplicationTransaction(data);
				logger.info(" End Ripristino + Delete Nuove " );

				//se ALMENO uno è KO allora
				// - ripristino le pendenze originali
	//			ripristinoMassivi(pendPk.getSenderid(), pendPk.getSendersys(),cdTrbEnte);
				RipristinaPendenzeOriginaliProxy.executeApplicationTransaction(data);
				//qui manca il controllo di business .... per decidere se si può cancellare o meno
	//			updateStatoPendenza(pendPk.getE2emsgid(), pendPk.getSenderid(), pendPk.getSendersys(), ESITO_KO_DA_CREARE);
			}
		} catch (Exception e) {
			logger.error(" ERROR CONTROLLER UPDATE MASSIVO >>>>>" + e.getMessage() + " ID = ");
			e.printStackTrace();
		}

//		} catch (Exception e) {
//			e.printStackTrace();
//			if (tx!=null && tx.isActive()) tx.rollback();
//		} catch (Throwable ez) {
//			ez.printStackTrace();
//		}  finally {
//			if (em!=null && em.isOpen()) em.close();
//		}


		return monData;
	}



	//	@Override
	public String executeHtml(String e2eMsgId, String senderId, String silMittente, String cdTrbEnte, String msgType) {

		String table = "";

		try {
			//delete fisica, all_massivo terminato con successo
			//(tutte le pendenze inserite senza errori)
			table += CancellaFisicaPendenzeOriginaliProxy.executeHtml(e2eMsgId,
					senderId, silMittente, cdTrbEnte, msgType);
			//delete fisica, all_massivo terminato con ERRORI
			//(almeno una pendenze NON inserita!!!)
			table += RipristinaPendenzeOriginaliProxy.executeHtml(e2eMsgId,
					senderId, silMittente, cdTrbEnte, msgType);
			table += DeleteNuovePendenzeOriginaliProxy.executeHtml(e2eMsgId,
					senderId, silMittente, cdTrbEnte, msgType);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return table;

	}

}

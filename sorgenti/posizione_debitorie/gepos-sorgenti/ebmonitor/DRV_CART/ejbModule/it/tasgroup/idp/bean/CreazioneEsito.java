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

import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.EsitiCartPK;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartPK;
import it.tasgroup.idp.esiti.EsitiModel;
import it.tasgroup.idp.esiti.EsitoBuilder;
import it.tasgroup.idp.esiti.EsitoPendenza;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
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
public class CreazioneEsito implements ObjectCommandExecutor {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager em;
	@Resource 
	private EJBContext ejbCtx;		

	private final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * String e2emsgid,
			String senderId, String senderSys, List<EsitoPendenza> esitoDaSpedire)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction(Object data) {
		//monitoring data
		MonitoringData monData = new MonitoringData();

		//recupero i dati di input
		EsitiModel esito = (EsitiModel)data;
		String e2emsgid = esito.getE2emsgid();
		String senderId = esito.getSenderId();
		String senderSys = esito.getSenderSys();
		String trtSenderId = esito.getTrtSenderId();
		String trtSenderSys = esito.getTrtSenderSys();
		String stato = esito.getStato();
		String statoFlusso = esito.getStatoFlusso();
		List<EsitoPendenza> esitoDaSpedire = esito.getPendenze();

		//monitoring data
		monData.setE2emsgid(e2emsgid);
		monData.setSenderid(senderId);
		monData.setSendersys(senderSys);
		monData.setNumRecord(1);

		EsitoBuilder esitoBuilder = new EsitoBuilder();

		try {

			//devo spedire con l IDEGOV che corrisponde al contenuto della lista!
			PendenzeCart pendC = new PendenzeCart();
			PendenzeCartPK pendPK = new PendenzeCartPK();
			pendPK.setE2emsgid(e2emsgid);
			pendPK.setSenderid(senderId);
			pendPK.setSendersys(senderSys);
			pendC.setPk(pendPK);

			//creo EsitoBuilder e creo il documento xml
			logger.info( this.getClass().getSimpleName() + " Creo XML di Esito " + " (statoFlusso = " + statoFlusso + ")" );
			String esitoXmlDaSpedire = esitoBuilder.creaEsito(esitoDaSpedire);
			logger.debug( this.getClass().getSimpleName() + " Esito Creato = " + esitoXmlDaSpedire  );

			//salvo l'esito da spedire in tabella
			saveEsitoCart(esito,esitoXmlDaSpedire);
			logger.info(this.getClass().getSimpleName() + " Inserito esitoCart, idBusta = " + e2emsgid + " - " + senderId + " - " + senderSys );

//			tx.commit();
			//da correggere dopo il ripristino della FK su ESITI_CART (LB 15 09)
//			tx.begin();

			//aggiorno lo stato della pendenza cart
			logger.info(this.getClass().getSimpleName() + " cambio stato della pendenza ricevuta, " + " idBusta = " + e2emsgid + " - " + senderId + " - " + senderSys );

			//lo stato che si imposta è RISPOSTA_INVIATA, considerazione:
			//l'esito XML non è stato ancora inviato alla rete (viene inviato da CartSenderBean)
			//l'esito XML è stato già prodotto e verrà inviato dal bean successivo CartSenderBean, in transazione.
			//LB 27/10: Lo stato viene impostato a IN SPEDIZIONE
			//LB 27/10: Lo stato della pendenze diventa RISPOSTA INVIATA con il bean successivo
			int esitiPend = updateStatoPendenzeCart(e2emsgid, senderId, senderSys, statoFlusso,
					em);
			logger.info(this.getClass().getSimpleName() + " ho cambiato lo stato della pendenza , num. pendenze = " + esitiPend );

			int esitiUpd = updateStatoEsitiPendenza(em, statoFlusso, pendC);
			logger.info(this.getClass().getSimpleName() + " ho cambiato lo stato di tutti gli esiti associati alla pendenza , num. esiti = " + esitiUpd );

			//aggiungo l'esito xml per rfc127 sincrono
			monData.setEsito(esitoXmlDaSpedire);
			 

		} catch (Exception e) {
			ejbCtx.setRollbackOnly();	
			logger.error(this.getClass().getSimpleName() + " ERROR CREAZIONE ESITO = " + e.getMessage());
		}
		return monData;
	}

	/**
	 * 
	 * @param em
	 * @param statoFlusso
	 * @param pendC
	 * @return
	 */
	private int updateStatoEsitiPendenza(EntityManager em, String statoFlusso, PendenzeCart pendC) {
		Query queryUpdateEsiti = em.createQuery ("Update EsitiPendenza esiti " +
				" set esiti.stato = :stato  " +
				" WHERE esiti.pendenzeCart = :pendenzeCart");
//		queryUpdateEsiti.setParameter("stato", StatoEnum.IN_SPEDIZIONE);
		queryUpdateEsiti.setParameter("stato", statoFlusso);
		queryUpdateEsiti.setParameter("pendenzeCart", pendC);
		int esitiUpd = queryUpdateEsiti.executeUpdate();
		return esitiUpd;
	}

	/**
	 * 
	 * @param e2emsgid
	 * @param senderId
	 * @param senderSys
	 * @param statoFlusso
	 * @param em
	 * @return
	 */
	private int updateStatoPendenzeCart(String e2emsgid, String senderId,
			String senderSys, String statoFlusso, EntityManager em) {
		Query queryUpdatePend = em.createQuery ("Update PendenzeCart pend " +
				" set pend.stato = :stato  " +
				" , pend.tsAggiornamento = :tsAggiornamento  " +
				" , pend.opAggiornamento = :opAggiornamento  " +
				" WHERE pend.pk.e2emsgid = :e2emsgid" +
				" AND pend.pk.senderid= :senderId " +
				" AND pend.pk.sendersys = :senderSys ");
//		queryUpdatePend.setParameter("stato", StatoEnum.IN_SPEDIZIONE);
		queryUpdatePend.setParameter("tsAggiornamento", new Timestamp(System.currentTimeMillis()));
		queryUpdatePend.setParameter("opAggiornamento", "CreazioneEsito");		
		queryUpdatePend.setParameter("stato", statoFlusso);
		queryUpdatePend.setParameter("e2emsgid", e2emsgid);
		queryUpdatePend.setParameter("senderId", senderId);
		queryUpdatePend.setParameter("senderSys", senderSys);

		int esitiPend = queryUpdatePend.executeUpdate();
		return esitiPend;
	}

	/**
	 * 
	 * @param e2emsgid
	 * @param senderId
	 * @param senderSys
	 * @param stato
	 * @param em
	 * @param esitoXmlDaSpedire
	 */
	private void saveEsitoCart(EsitiModel esitiModel, String esitoXmlDaSpedire) {
		EsitiCart esitiCart = new EsitiCart();
		esitiCart.setEsitoXml(esitoXmlDaSpedire.getBytes(Charset.forName("US-ASCII")));
		EsitiCartPK esitCartPK = new EsitiCartPK();
		esitCartPK.setE2emsgid(esitiModel.getE2emsgid());
		esitCartPK.setSenderid(esitiModel.getSenderId());
		esitCartPK.setSendersys(esitiModel.getSenderSys());
		esitiCart.setPk(esitCartPK);

//			da correggere dopo il ripristino della FK su ESITI_CART (LB 15 09)
//			PendenzeCart cart = new PendenzeCart();
//			PendenzeCartPK cartPK = new PendenzeCartPK();
//			cartPK.setE2emsgid(e2emsgid);
//			cartPK.setSenderid(senderId);
//			cartPK.setSendersys(senderSys);
//			cart.setPk(cartPK);
//			esitCart.setPendenzeCart(cart);

		esitiCart.setStato(esitiModel.getStato());
		esitiCart.setTrtSenderId(esitiModel.getTrtSenderId());
		esitiCart.setTrtSenderSys(esitiModel.getTrtSenderSys());
		esitiCart.setOpInserimento("SPEDIZ-ESITI-TIMER");
		esitiCart.setTimestampInvio(new Timestamp(System.currentTimeMillis()));
		esitiCart.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		em.persist(esitiCart);
	}

	@Override
	public String executeHtml(String msg, String sendId, String sendSys) {
		// TODO Auto-generated method stub
		return null;
	}


}

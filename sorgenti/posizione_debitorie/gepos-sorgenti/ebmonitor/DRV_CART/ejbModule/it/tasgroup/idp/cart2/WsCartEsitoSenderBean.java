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
/*

 * Copyright (c) 1996-2002 Sun Microsystems, Inc. All Rights Reserved.

 *

 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,

 * modify and redistribute this software in source and binary code form,

 * provided that i) this copyright notice and license appear on all copies of

 * the software; and ii) Licensee does not utilize the software in a manner

 * which is disparaging to Sun.

 *

 * This software is provided "AS IS," without a warranty of any kind. ALL

 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY

 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR

 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE

 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING

 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS

 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,

 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER

 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF

 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE

 * POSSIBILITY OF SUCH DAMAGES.

 *

 * This software is not designed or intended for use in on-line control of

 * aircraft, air traffic, aircraft navigation or aircraft communications; or in

 * the design, construction, operation or maintenance of any nuclear

 * facility. Licensee represents and warrants that it will not use or

 * redistribute the Software for such purposes.

 */
package it.tasgroup.idp.cart2;

import it.tasgroup.idp.bean.ObjectCommandExecutor;
import it.tasgroup.idp.bean.ObjectCommandExecutorLocal;
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartPK;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

import java.sql.Timestamp;
import java.util.Random;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.jms.JMSException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Bean implementation class for: WsCartEsitoSenderBean
 *
 */	
@Interceptors(MonitoringInterceptor.class)
@Stateless
public class WsCartEsitoSenderBean implements ObjectCommandExecutor, ObjectCommandExecutorLocal {
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;
	@Resource 
	private EJBContext ejbCtx;			

	private final Log logger = LogFactory.getLog(this.getClass());	
	
 	
	/**
	 * 
	 */    
	public MonitoringData executeApplicationTransaction(Object data) {
		//monitoring data
		MonitoringData monData = new MonitoringData();
			
		//pensavo si dovesse marshallare, invece no
		EsitiCart esitCart = (EsitiCart)data;
		
		//build soap
		//build URL PDD
		//call Webservice on IdpCartServices
		String output = fakeWsCaller();
						
		logger.info(" risultato spedizione " + output);
		
		updateEsito(esitCart);								
		
		return monData;
	}

	/**
	 * 
	 * @param esito
	 * @return
	 */
	private EsitiCart marshallEsito(String esito) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @return
	 */
    private String fakeWsCaller() {
		String output = "OK";
		int max = 5; int min = 1;
		int random = new Random().nextInt(max - min + 1) + min;
		switch (random) {
		case 1:
			output = "OK";
		case 2:
			output = "KO_PDDIRS";
		case 3:
			output = "KO_PDDENTE";
		case 4:
			output = "KO_ENTE";
		case 5:
			output = "KO_INTERNO";	
		default:
			output = "OK";
			break;
		}
		
		return output;
	}

	/**
     * 
     * @param esitCart
     * @throws JMSException
     */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void updateEsito(EsitiCart esitCart)  {
		
		try {		
		
			//creo la chiave
			PendenzeCart pendC = new PendenzeCart();
			PendenzeCartPK pendPK = new PendenzeCartPK();
			pendPK.setE2emsgid(esitCart.getPk().getE2emsgid());
			pendPK.setSenderid(esitCart.getPk().getSenderid());
			pendPK.setSendersys(esitCart.getPk().getSendersys());
			pendC.setPk(pendPK);
			//pendC.setIdMessaggioEgov(esitCart.getIdMessaggioEgov());
			
			String query = "Update EsitiCart esitiCart " +
			" set esitiCart.stato = :stato  " +
			" , esitiCart.opAggiornamento = :op_aggiornamento" +
			" , esitiCart.tsAggiornamento = :ts_aggiornamento  " +
			" WHERE esitiCart.pk.e2emsgid = :e2emsgid" +
			" AND esitiCart.pk.senderid= :senderId " +
			" AND esitiCart.pk.sendersys = :senderSys ";
			logger.info(" query update esitiCart = " + query);
			//aggiorno
			Query queryUpdateEsitiCart = manager.createQuery (query);
			queryUpdateEsitiCart.setParameter("stato", StatoEnum.INVIATO);			
			queryUpdateEsitiCart.setParameter("e2emsgid", esitCart.getPk().getE2emsgid());
			queryUpdateEsitiCart.setParameter("senderId", esitCart.getPk().getSenderid());
			queryUpdateEsitiCart.setParameter("senderSys", esitCart.getPk().getSendersys());
			queryUpdateEsitiCart.setParameter("op_aggiornamento", "CART WS-SENDER");
			queryUpdateEsitiCart.setParameter("ts_aggiornamento", new Timestamp(System.currentTimeMillis()));
			int esitiUpd = queryUpdateEsitiCart.executeUpdate();
			
			//aggiorno lo stato di pendenze_cart
			logger.info(" query update pendenze_cart (E2eMsgId = " + esitCart.getPk().getE2emsgid() +  ") = " + StatoEnum.RISPOSTA_INVIATA);
			updateStatoPendenza(manager, esitCart.getPk().getE2emsgid(), esitCart.getPk().getSenderid(), esitCart.getPk().getSendersys(), 
					StatoEnum.RISPOSTA_INVIATA);
			//test1
			//throw new RuntimeException("Example Exception");
			//verificare se lo stato della PendenzaCart è INVIATO
			
			//aggiorno lo stato di esiti_pendenza
			logger.info(" query update esiti_pendenza " + StatoEnum.INVIATO);				
			updateStatoEsitiPendenza(manager, esitCart.getPk().getE2emsgid(), esitCart.getPk().getSenderid(), esitCart.getPk().getSendersys(), 
					StatoEnum.INVIATO);
		
			logger.info(" ho cambiato (esito="+esitiUpd+") lo stato dell'esito cart " + esitCart.getPk().getE2emsgid() + " - " + esitCart.getPk().getSenderid() + " -  " + esitCart.getPk().getSendersys() + " , num. esiti = " + esitiUpd );
		
		} catch(PersistenceException e) {
			logger.error(e);
			throw e;	
		} catch(RuntimeException e) {
			logger.error(e);
			ejbCtx.setRollbackOnly();
			throw e;
		} 
	}



	
	/**
	 * 
	 * @param em
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param stato
	 */
	private void updateStatoPendenza(EntityManager em, String E2EMsgId,
			String idMittente, String silMittente, String stato ) {
		Query queryUpdatePend = em.createQuery ("Update PendenzeCart pend " +
				" set pend.stato = :stato  " +
				" WHERE pend.pk.e2emsgid = :e2emsgid" +
				" AND pend.pk.senderid= :senderId " +
				" AND pend.pk.sendersys = :senderSys ");
		queryUpdatePend.setParameter("stato", stato);			
		queryUpdatePend.setParameter("e2emsgid", E2EMsgId);
		queryUpdatePend.setParameter("senderId", idMittente);
		queryUpdatePend.setParameter("senderSys", silMittente);
		
		int esitiPend = queryUpdatePend.executeUpdate();
	}

	/**
	 * 
	 * @param em
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param stato
	 */
	private void updateStatoEsitiPendenza(EntityManager em, String E2EMsgId,
			String idMittente, String silMittente, String stato ) {
		//creo pend cart
		PendenzeCart pendC = new PendenzeCart();
		PendenzeCartPK pendPK = new PendenzeCartPK();
		pendPK.setE2emsgid(E2EMsgId);
		pendPK.setSenderid(idMittente);
		pendPK.setSendersys(silMittente);
		pendC.setPk(pendPK);		
		
		Query queryUpdateEsiti = em.createQuery ("Update EsitiPendenza esiti " +
				" set esiti.stato = :stato  " +
				" WHERE esiti.pendenzeCart = :pendenzeCart");
		queryUpdateEsiti.setParameter("stato", stato);
		queryUpdateEsiti.setParameter("pendenzeCart", pendC);
		int esitiUpd = queryUpdateEsiti.executeUpdate();
		
		logger.info(this.getClass().getSimpleName() + " imposto lo stato " + StatoEnum.INVIATO + " di tutti gli esiti associati alla pendenza , num. esiti = " + esitiUpd );
		
	}


	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String executeHtml() throws Exception  {			
		
		String table = "";
		return table;

	}


	/**
	 * 
	 * @return
	 */
	public EntityManager getManager() {
		//PU JTA
		return manager;
	}

	@Override
	public String executeHtml(String msg, String sendId, String sendSys) {
		// TODO Auto-generated method stub
		return null;
	}



}

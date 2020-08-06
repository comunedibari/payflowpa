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
package it.tasgroup.idp.cart3;

import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.bean.ObjectCommandExecutorLocal;
import it.tasgroup.idp.bean.SimplestCommandExecutor;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeEnteOTF;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeEnteOTFEsito;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamento.IdpVerificaStatoPagamentiEsito;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Interceptors(MonitoringInterceptor.class)
@Stateless
public class CartWsListenerBean implements SimplestCommandExecutor  {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager em;
	@Resource 
	private EJBContext ejbCtx;		

	private final Log logger = LogFactory.getLog(this.getClass());
	
//	@EJB(beanName = "CartWsImpl")
//	private ObjectCommandExecutorLocal CartWsImpl;
	
	//ad uso del servizio sincrono
	@EJB(beanName = "DataStorageManager")
	private CommandExecutorLocal DataStorageManagerProxy;
	@EJB(beanName = "ErroriCartManager")
	private CommandExecutorLocal ErroriCartManagerProxy;
	
	private static String ALL_PEND = "ALL_PEND";
	private static String ALL_PEND_OTF = "ALL_PEND_OTF";
	private static String VERIFICA_STATO = "VERIFICA_STATO";
	private static String ESITO_NOTIFICA = "ESITO_NOTIFICA";
	
	
	/**
	 * 
	 * @param message
	 * @param idEgov
	 * @param hash
	 * @param async
	 * @return metodo principale, il return fisso non è corretto 
	 */
	@Override
	public MonitoringData executeApplicationTransaction(
				String message, 
				String idEgov, 
				String hash,
				String method) {
		
		MonitoringData data = new MonitoringData();
		
		//if... pessimo
		if (ALL_PEND.equals(method)) {
			data = this.allineamentoPendenze(message, idEgov, hash);
		} else if (ALL_PEND_OTF.equals(method)) {
			IdpAllineamentoPendenzeEnteOTFEsito esito = this.allineamentoPendenzeOTF(message, idEgov, hash);
			data.setEsitoAllineamentoPendenze(esito);	
			
		} else if (VERIFICA_STATO.equals(method)) {
			IdpVerificaStatoPagamentiEsito esito = this.verificaStatoPagamento(message, idEgov, hash);
			data.setVerificaStatoPagamenti(esito);
			
		} else if (ESITO_NOTIFICA.equals(method)) {
			data = this.informativaPagamentiEsito(message, idEgov, hash);
			
		} else  {
			//be careful
		}   
		
		return data;
	}

	/**
	 * 
	 * @param message
	 * @param idEgov
	 * @param hash
	 * @return non deve necessariamente restituire un oggetto leggibile
	 */
	@Override
	public MonitoringData allineamentoPendenze(
			String contenutoXml, 
			String idEgov, 
			String hash) {
		
    	logger.info("messaggio in arrivo ");
		
		try {
				//e' un messaggio vero... allora procedo!		
				logger.info(" calling DataStorageManagerProxy");
				DataStorageManagerProxy.executeApplicationTransaction(contenutoXml);  							
			
		} catch (RuntimeException e) {
			logger.error("Error FATAL, Adesso salvo il messaggio su ERRORI_CART " + e.getMessage() );
			//salvo il messaggio nella tabella ERRORI_CART
			ErroriCartManagerProxy.executeApplicationTransaction(contenutoXml);		
		} finally{
			logger.info("elaborazione messaggio terminata. ");
		}		
		
		return null;
	}
	
	/**
	 * 
	 * @param message
	 * @param idEgov
	 * @param hash
	 * @return deve restituire un oggetto leggibile
	 */
	@Override
	public IdpAllineamentoPendenzeEnteOTFEsito allineamentoPendenzeOTF(
			String message, 
			String idEgov, 
			String hash) {
		
		//message ???
		//no..... adesso si deve trasformare 'message' 
		//da formato xml a formato jaxb
		//questo è necessario se si vuole riutilizzare in toto il codice as-is
		//proveniente dal webservice
		
		//NOTA LB 19/01/16! 
		//NON UTILIZZATO DAL WEBSERVICE !!
		//IL WEBSERVICE LAVORA DIRETTAMENTE CON CartWsImpl
		//SENZA TRANSITARE DA QUESTO METODO
		//SI DEVE DECIDERE SE E' OPPORTUNO RIPETERE IL MARSHALL OPPURE NO
		//A MIO PARERE PUO' ANDARE BENE COSI' 
		IdpAllineamentoPendenzeEnteOTF data = marshallMe(message);
		CartWsImpl cartWsImpl = new CartWsImpl();
		IdpAllineamentoPendenzeEnteOTFEsito esito = cartWsImpl.idpAllineamentoPendenzeEnteOTF(data);
		
		return esito;
	}
	
	/**
	 * 
	 * @param message
	 * @return
	 */
	private IdpAllineamentoPendenzeEnteOTF marshallMe(String message) {

		return null;
	}

	/**
	 * 
	 * @param message
	 * @param idEgov
	 * @param hash
	 * @return non deve necessariamente restituire un oggetto leggibile
	 */
	@Override
	public MonitoringData informativaPagamentiEsito(
			String message, 
			String idEgov, 
			String hash) {
						
		return null;
	}
	
	/**
	 * 
	 * @param message
	 * @param idEgov
	 * @param hash
	 * @return deve restituire un oggetto leggibile
	 */
	@Override
	public IdpVerificaStatoPagamentiEsito verificaStatoPagamento(
			String message, 
			String idEgov, 
			String hash) {
		
		//fake
		IdpVerificaStatoPagamentiEsito esito = new IdpVerificaStatoPagamentiEsito();
		
		
		return esito;
	}			




}

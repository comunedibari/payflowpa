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

import it.nch.eb.flatx.flatmodel.sax.splitter.FragmentHandler;
import it.nch.eb.flatx.flatmodel.sax.splitter.GetContentTextCollectorEffectFactory;
import it.nch.eb.flatx.flatmodel.sax.splitter.XmlSplitSaxHandlerBuilder;
import it.nch.fwk.core.NamespacesInfos;
import it.tasgroup.idp.domain.messaggi.ConfermeCart;
import it.tasgroup.idp.domain.messaggi.ConfermeCartPK;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.pojo.PrevisitingData;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.stream.FactoryConfigurationError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

@Interceptors(MonitoringInterceptor.class)
@Stateless
public class EsitoNotificaPagamentoManager implements CommandExecutor, CommandExecutorLocal {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta) 	
	private EntityManager manager;
	@Resource 
	private EJBContext ejbCtx;	
		
	private final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * 
	 */
	public MonitoringData executeApplicationTransaction(String entity) {
		//monitoring data
		MonitoringData monData = new MonitoringData();					
				
		//messaggioXml
		String message = (String)entity;
		StringBuilder contents = new StringBuilder(message);
		
		//esito
//		EsitoBuilder esitoB = new EsitoBuilder(); 
		
		//previsita xml 		
		PrevisitingData prevData = new PrevisitingData();				
		
		//conteggio pendenze e duplicati
		prevData = this.previsitingXml("Pendenza", contents);	
		logger.info("EsitoNotificaPagamentoManager , Ricevuta busta :  " + prevData.getE2EMsgId() + "/"+ prevData.getE2EMsgId() + "/" + prevData.getSilMittente() );
		
		//monitoring data
		monData.setE2emsgid(prevData.getE2EMsgId());
		monData.setSenderid(prevData.getIdMittente());
		monData.setSendersys(prevData.getSilMittente());
		monData.setReceiverid(prevData.getIdRicevente());
		monData.setReceiversys(prevData.getSilRicevente());	
		monData.setNumRecord(1);		
		
		try {
			//salvo su ConfermeCart
			savingBlobOnDatabase(message, prevData);
			
			//da decidere se si deve controaggiornare 
			//anche lo stato della notificaPagamento
			updateNotifichePagamento(prevData);		
				
			logger.info("Salvata busta con blob :  " + prevData.getE2EMsgId() );
		
		} catch (Exception e) {
			//qualsiasi eccezione allora set rollback only
			ejbCtx.setRollbackOnly();
			logger.error(this.getClass().getSimpleName() + " : Error Saving BLOB " + e.getMessage() );
			throw new RuntimeException("Error saving Blob");
		} 			

							
				
		return monData;
	}


	/**
	 * 
	 * @param em
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param stato
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)		
	private void updateNotifichePagamento(PrevisitingData prevData) {
		Query queryUpdateNotifiche = manager.createQuery ("Update NotificheCart notifiche " +
				" set notifiche.stato = :stato  " +
				" WHERE notifiche.id.e2emsgid = :e2emsgid" );
				//questo UPDATE adesso lo facciamo SOLO su E2EMSGID
				// 03/08/2012
				// a seguito della problematica dovuto al doppio SIL
				// cioè un SIL per RFC127 ed un altro SIL per RFC145
				// probabilmente andrà RIPRISTINATO l'UPDATE su tutti e tre i campi
				// ma solo QUANDO sarà gestito il DOPPIO SIL
				// sulla coppia ente/tributo !!!!
//				+	" AND notifiche.id.receiverid= :receiverid " 
//				+ 	" AND notifiche.id.receiversys = :receiversys ");			
		
		//un pò brutto questo.. pericoloso
		if ("Elaborato Correttamente".equals(prevData.getStato())) {
			queryUpdateNotifiche.setParameter("stato", StatoEnum.ELABORATO_OK);
		} else if ("Elaborato con Errori".equals(prevData.getStato())) {
			queryUpdateNotifiche.setParameter("stato", StatoEnum.ELABORATO_KO);			
		}				
		
		queryUpdateNotifiche.setParameter("e2emsgid", prevData.getE2EMsgId());
//		queryUpdateNotifiche.setParameter("receiverid", prevData.getIdMittente());
//		queryUpdateNotifiche.setParameter("receiversys", prevData.getSilMittente());
		
		int esitiPend = queryUpdateNotifiche.executeUpdate();
	}


	/**
	 * 
	 * @param em
	 * @param message
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param idRicevente
	 * @param silRicevente
	 * @param prevData
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)		
	private void savingBlobOnDatabase(String message,
			 PrevisitingData prevData) {
		ConfermeCart cart = new ConfermeCart();
		cart.setMessaggioXml(message.getBytes(Charset.forName("US-ASCII")));			
		
		//un pò brutto questo.. pericoloso
		if ("Elaborato Correttamente".equals(prevData.getStato())) {
			cart.setStato(StatoEnum.ELABORATO_OK);	
		} else if ("Elaborato con Errori".equals(prevData.getStato())) {
			cart.setStato(StatoEnum.ELABORATO_KO);
		}		
		
		ConfermeCartPK cartPK = new ConfermeCartPK();
		cartPK.setE2emsgid(prevData.getE2EMsgId());
		cartPK.setSenderid(prevData.getIdMittente());
		cartPK.setSendersys(prevData.getSilMittente());
		cart.setId(cartPK);  
		cart.setReceiverid(prevData.getIdRicevente());
		cart.setReceiversys(prevData.getSilRicevente());
		cart.setOpInserimento("IDP");
		cart.setTimestampRicezione(new Timestamp(System.currentTimeMillis()));
		manager.persist(cart);
	}


	/**
	 * 
	 * @param idTag
	 * @param contents
	 * @param fileName 
	 * @return
	 * @throws IOException
	 */
	public PrevisitingData previsitingXml(String idTag, StringBuilder contents)  {
		//build object
		final PrevisitingData prevData = new PrevisitingData();
		
		try {
			NamespacesInfos nss = new NamespacesInfos( new String[][] { 
//					{ "", "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze" },
						{"","http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito"}, 
						{"h","http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader"},
						{ "xmlns:i", "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude" },									
				});
			
			//versione 1.2
			//xmlns:HE2E="urn:Idp:xsd:IdpHeaderE2E.01.02.00"
			//targetNamespace="urn:Idp:xsd:IdpAllineamentoPendenzeEnte.01.02.00"
			//elementFormDefault="qualified">
			//versione 1.3
			//targetNamespace="http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze"
			//elementFormDefault="unqualified">		
						
			//time per controllo tempo esecuzione
			long timeStart = System.currentTimeMillis();		
			
			XmlSplitSaxHandlerBuilder bldr = new XmlSplitSaxHandlerBuilder().usingNamespaces(nss);
			
			//versione 1.2
			//bldr.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Service/HE2E:E2EMsgId",
			//versione 1.3
			String E2emsgidPath = "/IdpEsito/h:IdpHeader/h:E2E/h:E2EMsgId";
			bldr.add(E2emsgidPath,
					new GetContentTextCollectorEffectFactory(
							new FragmentHandler() {	
						public void onFragment(String xmlFragment) {
							prevData.setE2EMsgId(xmlFragment);
						}					
					}));
			
			//versione 1.2
			//bldr.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Service/HE2E:SenderId",
			//versione 1.3
			String SenderIdPath = "/IdpEsito/h:IdpHeader/h:E2E/h:Sender/h:E2ESndrId";
			bldr.add(SenderIdPath,
					new GetContentTextCollectorEffectFactory(
							new FragmentHandler() {	
						public void onFragment(String xmlFragment) {
							prevData.setIdMittente(xmlFragment);
						}					
					})); 		
	
			//versione 1.2
			//bldr.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Service/HE2E:SenderId",
			//versione 1.3
			String SenderSysPath = "/IdpEsito/h:IdpHeader/h:E2E/h:Sender/h:E2ESndrSys";
			bldr.add(SenderSysPath,
					new GetContentTextCollectorEffectFactory(
							new FragmentHandler() {	
						public void onFragment(String xmlFragment) {
							prevData.setSilMittente(xmlFragment);
						}					
					})); 		
			
			//versione 1.2
			//bldr.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Service/HE2E:SenderId",
			//versione 1.3
			String ReceiverIdPath = "/IdpEsito/h:IdpHeader/h:E2E/h:Receiver/h:E2ERcvrId";
			bldr.add(ReceiverIdPath,
					new GetContentTextCollectorEffectFactory(
							new FragmentHandler() {	
						public void onFragment(String xmlFragment) {
							prevData.setIdRicevente(xmlFragment);
						}					
					})); 			
			
			//versione 1.2
			//bldr.add("/IdpAllineamentoPendenzeEnte/IdpHeaderE2E/HE2E:Service/HE2E:SenderId",
			//versione 1.3
			String ReceiverSysPath = "/IdpEsito/h:IdpHeader/h:E2E/h:Receiver/h:E2ERcvrSys";
			bldr.add(ReceiverSysPath,
					new GetContentTextCollectorEffectFactory(
							new FragmentHandler() {	
						public void onFragment(String xmlFragment) {
							prevData.setSilRicevente(xmlFragment);
						}					
					})); 				
			
			//versione 1.2		
			//bldr.add("/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/IdPendenza",
			//versione 1.3
			String IdPendenzaPath = "/IdpEsito/IdpBody/InfoMessaggio/Stato";
			bldr.add(IdPendenzaPath,
					new GetContentTextCollectorEffectFactory(
							new FragmentHandler() {	
						public void onFragment(String xmlFragment) {
							//aggiungo lo stato, cioè esito della notifica
							prevData.setStato(xmlFragment);
						}					
					})); 		
							
			
			//nuovo file
//			File xmlFile = new File(fileName);

			DefaultHandler handler = bldr.create();
			//parsing
			//saxParse(new FileReader(xmlFile), handler);
			saxParse(new StringReader(contents.toString()), handler);		
			
			long timeEnd  = System.currentTimeMillis();
			long timeAll = timeStart - timeEnd;
			
			logger.info("Time Needed to parse XML"+timeAll);			
			
			logger.info(E2emsgidPath + " >>> "+prevData.getE2EMsgId());
			logger.info(SenderIdPath + " >>> " +prevData.getIdMittente());
			logger.info(SenderSysPath + " >>> "+prevData.getSilMittente());
			logger.info(ReceiverIdPath + " >>> "+prevData.getIdRicevente());
			logger.info(ReceiverSysPath + " >>> "+prevData.getSilRicevente());
			logger.info("count(/IdpEsito/IdpBody/InfoMessaggio/Stato) = "+prevData.getStato());
			
//		} catch (FileNotFoundException e1) {
//			logger.error(this.getClass().getSimpleName() + " : Error Counting Pendenze " + e1.getMessage() );
//			throw new RuntimeException("Error Counting Pendenze " + e1.getMessage());
		} catch (FactoryConfigurationError e) {
			logger.error(this.getClass().getSimpleName() + " : Error Counting Pendenze " + e.getMessage() );
			throw new RuntimeException("Error Counting Pendenze " + e.getMessage());
		} 
		
		return prevData;
	}
	
	/**
	 * 
	 * @param reader
	 * @param dsh
	 */
	protected static void saxParse(Reader reader, DefaultHandler dsh) {
		try {
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			xmlReader.setContentHandler(dsh);
			xmlReader.setErrorHandler(dsh);
			
			xmlReader.parse(new InputSource(reader));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}	
 	
	/**
	 * TODO FIXARE QUESTO METODO !!!
	 * @return
	 */
	public EntityManager getManager() {
		//quando siamo su JBOSS ci vuole questa riga
//		return emf.createEntityManager();
		//quando siamo su TEST JUNIT ci vuole questa riga
//		return manager;
		//PU JTA
		return manager;
	}


	@Override
	public MonitoringData executeApplicationTransaction() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String executeHtml() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


//	@Override
//	public MonitoringData executeApplicationTransaction(Object data) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}

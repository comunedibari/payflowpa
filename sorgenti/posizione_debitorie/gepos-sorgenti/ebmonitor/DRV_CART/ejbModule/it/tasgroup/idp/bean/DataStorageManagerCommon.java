package it.tasgroup.idp.bean;

import it.nch.eb.flatx.flatmodel.sax.splitter.FragmentHandler;
import it.nch.eb.flatx.flatmodel.sax.splitter.GetContentTextCollectorEffectFactory;
import it.nch.eb.flatx.flatmodel.sax.splitter.XmlSplitSaxHandlerBuilder;
import it.nch.eb.flatx.flatmodel.sax.transform.QNameMatch;
import it.nch.fwk.core.NamespacesInfos;
import it.tasgroup.dse.errorInfo.ExtendedErrorInfoImpl;
import it.tasgroup.idp.domain.Iban;
import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.Sil;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.EsitiCartPK;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartMessage;
import it.tasgroup.idp.domain.messaggi.PendenzeCartPK;
import it.tasgroup.idp.esiti.EsitoBuilder;
import it.tasgroup.idp.pojo.PrevisitingData;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.EnterpriseBean;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public abstract class DataStorageManagerCommon {

	static final String OTF_NOT_FOUND = "NOT FOUND";

	@Resource
	private SessionContext ctx;
	@Resource
	private EJBContext ejbCtx;	
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpAppJpa)
	private EntityManager manager;
	
	@EJB(beanName="DataManager")
	private DataStorageInterface dataTx;	
	
	final Log logger = LogFactory.getLog(this.getClass());
	
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
	
	
//	public DataStorageManagerCommon() {
//		super();
//	}


	/**
	 *
	 * @param esitoB
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param idRicevente
	 * @param silRicevente
	 */
	protected String managePendenzeNonUnivoche(EsitoBuilder esitoB, String serviceNameType, String E2EMsgId, String idMittente, String silMittente,
		String idRicevente, String silRicevente, String trtIdMittente,String trtSilMittente) {
			
		String esitoXML = "";
		try {
//				em = getManager();
//				tx = em.getTransaction();
//				tx.begin();
	
			dataTx.updateStatoPendenza(E2EMsgId, idMittente, silMittente, StatoEnum.DA_SPEDIRE);
			//updateStatoPendenza(em, E2EMsgId, idMittente, silMittente, StatoEnum.DA_SPEDIRE);
			logger.info(" ho cambiato lo stato della pendenza_cart =  " + StatoEnum.DA_SPEDIRE );
	
			//spedisco esito globale per dire che XML contiene id pendenze non univoci
			Set setPendenzeUnivoche = new HashSet<ExtendedErrorInfoImpl>();
			String errore = "Id Pendenze non univoci";
			ExtendedErrorInfoImpl typeTrunc = new ExtendedErrorInfoImpl("A0000003",0,null,null,null,null,null,null,errore);
			setPendenzeUnivoche.add(typeTrunc);
			esitoXML = esitoB.creaEsitoXmlNonValido(serviceNameType, E2EMsgId,idMittente,silMittente,idRicevente,silRicevente, "A0000003", setPendenzeUnivoche, trtIdMittente, trtSilMittente);
			logger.info( " Esito Xml Per ricezione File con idPendenza non univoco " );
			logger.debug( esitoXML);
	
			//salvo l'esito da spedire in tabella
			insertEsitoCartBridge(E2EMsgId, idMittente, silMittente, esitoXML, trtIdMittente, trtSilMittente);
			logger.info("Inserito esitoCart XML Per ricezione File con idPendenza non univoco , idBusta = " + E2EMsgId + " - " + idMittente + " - " + silMittente );
	
			dataTx.updateStatoPendenza(E2EMsgId, idMittente, silMittente, StatoEnum.IN_SPEDIZIONE);
			//updateStatoPendenza(em, E2EMsgId, idMittente, silMittente, StatoEnum.IN_SPEDIZIONE);
			logger.info(" ho cambiato lo stato della pendenza_cart =  " + StatoEnum.IN_SPEDIZIONE );		
	
			dataTx.flush();
			
		} catch (Exception ex) {
			ejbCtx.setRollbackOnly();
			//if (tx!=null && tx.isActive()) tx.rollback();
			logger.error("Error UPDATE stato IN SPEDIZIONE / Spedizione Esito Globale " + ex.getMessage() );
		} 
		return esitoXML;
	}
	
	/**
	 * 
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param esitoXML
	 */
	protected abstract void insertEsitoCartBridge(String E2EMsgId, String idMittente,
			String silMittente, String esitoXML,String trtSenderId, String trtSenderSys);	

	/**
	 *
	 * @param esitoB
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param idRicevente
	 * @param silRicevente
	 */
	protected String manageSilNonPresente(EsitoBuilder esitoB, String serviceNameType, String E2EMsgId, String idMittente, String silMittente,
		String idRicevente, String silRicevente, String trtIdMittente, String trtSilMittente) {
			String esitoXML = "";
			try {
				dataTx.updateStatoPendenza(E2EMsgId, idMittente, silMittente, StatoEnum.DA_SPEDIRE);
				//updateStatoPendenza(em, E2EMsgId, idMittente, silMittente, StatoEnum.DA_SPEDIRE);
				logger.info(" ho cambiato lo stato della pendenza_cart =  " + StatoEnum.DA_SPEDIRE );
		
				//spedisco esito globale per dire che XML contiene id Pagamento non univoci
				Set setSilInesistente = new HashSet<ExtendedErrorInfoImpl>();
				String errore = "SIL inesistente";
				ExtendedErrorInfoImpl typeTrunc = new ExtendedErrorInfoImpl("A0000018",0,null,null,null,null,null,null,errore);
				setSilInesistente.add(typeTrunc);
				esitoXML = esitoB.creaEsitoXmlNonValido(serviceNameType, E2EMsgId,idMittente,silMittente,idRicevente,silRicevente, "A0000018", setSilInesistente, trtIdMittente, trtSilMittente);
				logger.info( " Esito Xml Per ricezione File con Sil inesistente " );
				logger.debug( esitoXML);
		
				//salvo l'esito da spedire in tabella
				insertEsitoCartBridge(E2EMsgId, idMittente, silMittente, esitoXML, trtIdMittente, trtSilMittente);
				logger.info("Inserito esitoCart XML Per ricezione File con SIL inesistente , idBusta = " + E2EMsgId + " - " + idMittente + " - " + silMittente );
		
				dataTx.updateStatoPendenza(E2EMsgId, idMittente, silMittente, StatoEnum.IN_SPEDIZIONE);
				//updateStatoPendenza(em, E2EMsgId, idMittente, silMittente, StatoEnum.IN_SPEDIZIONE);
				logger.info(" ho cambiato lo stato della pendenza_cart =  " + StatoEnum.IN_SPEDIZIONE );
		
				dataTx.flush();
				
			} catch (Exception ex) {
//				if (tx!=null && tx.isActive()) tx.rollback();
				logger.error("Error UPDATE stato IN SPEDIZIONE / Spedizione Esito Globale " + ex.getMessage() );
			} 
			return esitoXML;
		}
	
	
	/**
	 * 
	 * @param esitoB
	 * @param serviceNameType
	 * @param e2eMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param idRicevente
	 * @param silRicevente
	 */
	protected String manageIbanNotTrusted(EsitoBuilder esitoB, String serviceNameType,
			String E2EMsgId, String idMittente, String silMittente,
			String idRicevente, String silRicevente,
			String trtIdMittente, String trtSilMittente) {		
		String esitoXML = "";
		try {
//			em = getManager();
//			tx = em.getTransaction();
//			tx.begin();										
	
			dataTx.updateStatoPendenza(E2EMsgId, idMittente, silMittente, StatoEnum.DA_SPEDIRE);
			//updateStatoPendenza(em, E2EMsgId, idMittente, silMittente, StatoEnum.DA_SPEDIRE);
			logger.info(" ho cambiato lo stato della pendenza_cart =  " + StatoEnum.DA_SPEDIRE );
	
			//spedisco esito globale per dire che XML contiene IBAN NOT TRUSTED
			Set ibanNotStrusted = new HashSet<ExtendedErrorInfoImpl>();
			String errore = "La cartella di pagamento contiene Iban not trusted";
			ExtendedErrorInfoImpl typeTrunc = new ExtendedErrorInfoImpl("A0000037",0,null,null,null,null,null,null,errore);
			
			ibanNotStrusted.add(typeTrunc);
			esitoXML = esitoB.creaEsitoXmlNonValido(serviceNameType, E2EMsgId,idMittente,silMittente,idRicevente,silRicevente, "A0000037", ibanNotStrusted, trtIdMittente, trtSilMittente);
			logger.info( " Esito Xml Per ricezione File con IBAN NOT TRUSTED" );
			logger.debug( esitoXML);
	
			//salvo l'esito da spedire in tabella
			insertEsitoCartBridge(E2EMsgId, idMittente, silMittente, esitoXML, trtIdMittente, trtSilMittente);
			logger.info("Inserito esitoCart XML Per ricezione File con IBAN NOT TRUSTED, idBusta = " + E2EMsgId + " - " + idMittente + " - " + silMittente );
	
			dataTx.updateStatoPendenza(E2EMsgId, idMittente, silMittente, StatoEnum.IN_SPEDIZIONE);
			//updateStatoPendenza(em, E2EMsgId, idMittente, silMittente, StatoEnum.IN_SPEDIZIONE);
			logger.info(" ho cambiato lo stato della pendenza_cart =  " + StatoEnum.IN_SPEDIZIONE );
	
			dataTx.flush();
			
		} catch (Exception ex) {
			logger.error("Error UPDATE stato IN SPEDIZIONE / Spedizione Esito Globale " + ex.getMessage() );
		} 
		return esitoXML;			
	}		
	
	
	/**
	 * 
	 * @param esitoB
	 * @param serviceNameType
	 * @param e2eMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param idRicevente
	 * @param silRicevente
	 */
	protected String manageTipoOperazioneNonConsistente(EsitoBuilder esitoB, String serviceNameType,
			String E2EMsgId, String idMittente, String silMittente,
			String idRicevente, String silRicevente, 
			String trtIdMittente, String trtSilMittente) {		
		String esitoXML = "";
		try {
//			em = getManager();
//			tx = em.getTransaction();
//			tx.begin();										
	
			dataTx.updateStatoPendenza(E2EMsgId, idMittente, silMittente, StatoEnum.DA_SPEDIRE);
			//updateStatoPendenza(em, E2EMsgId, idMittente, silMittente, StatoEnum.DA_SPEDIRE);
			logger.info(" ho cambiato lo stato della pendenza_cart =  " + StatoEnum.DA_SPEDIRE );
	
			//spedisco esito globale per dire che XML contiene TIPO OPERAZIONE non omogeneo
			Set setSilInesistente = new HashSet<ExtendedErrorInfoImpl>();
			String errore = "Tipo operazione non omogeneo";
			ExtendedErrorInfoImpl typeTrunc = new ExtendedErrorInfoImpl("A0000014",0,null,null,null,null,null,null,errore);
			setSilInesistente.add(typeTrunc);
			esitoXML = esitoB.creaEsitoXmlNonValido(serviceNameType, E2EMsgId,idMittente,silMittente,idRicevente,silRicevente, "A0000014", setSilInesistente, trtIdMittente, trtSilMittente);
			logger.info( " Esito Xml Per ricezione File con tipo operazione non omogeneo " );
			logger.debug( esitoXML);
	
			//salvo l'esito da spedire in tabella
			insertEsitoCartBridge(E2EMsgId, idMittente, silMittente, esitoXML, trtIdMittente, trtSilMittente);
			logger.info("Inserito esitoCart XML Per ricezione File con tipo operazione non omogeneo , idBusta = " + E2EMsgId + " - " + idMittente + " - " + silMittente );
	
			dataTx.updateStatoPendenza(E2EMsgId, idMittente, silMittente, StatoEnum.IN_SPEDIZIONE);
			//updateStatoPendenza(em, E2EMsgId, idMittente, silMittente, StatoEnum.IN_SPEDIZIONE);
			logger.info(" ho cambiato lo stato della pendenza_cart =  " + StatoEnum.IN_SPEDIZIONE );	
	
			dataTx.flush();
			
		} catch (Exception ex) {
			logger.error("Error UPDATE stato IN SPEDIZIONE / Spedizione Esito Globale " + ex.getMessage() );
		} 
		return esitoXML;			
	}	

	/**
	 *
	 * @param contents
	 * @param senderSys 
	 * @param senderId 
	 * @throws FactoryConfigurationError
	 * @throws FileNotFoundException
	 * @throws XMLStreamException
	 */
	protected String[] readXml(String idMsgTrtTag1, String idMsgTrtTag2, String idMsgTrtTag3,
			String idMsgTrtTag4, String idMsgTrtTag5, String idMsgTrtTag6, String idMsgTrtTag7, String idMsgTrtTag8, String idMsgTrtTag9, String idMsgTrtTag10, String contents)
			throws FactoryConfigurationError, FileNotFoundException,
			XMLStreamException {
			
			String[] tags = new String[10];
			tags[5] = OTF_NOT_FOUND;
	
			//Creo input factory
			XMLInputFactory factory = XMLInputFactory.newInstance();
		    Reader fileReader = new StringReader(contents);
		    XMLEventReader reader = factory.createXMLEventReader(fileReader);
	
		    //lista tag necessari
		    boolean idMsgTrtFound1 = false;
		    boolean idMsgTrtFound2 = false;
		    boolean idMsgTrtFound3 = false;
		    boolean idMsgTrtFound4 = false;
		    boolean idMsgTrtFound5 = false;
		    boolean idMsgTrtFound6 = false;
		    boolean idMsgTrtFound7 = false;
		    boolean idMsgTrtFound8 = false;
		    boolean idMsgTrtFound9 = false;
		    boolean idMsgTrtFound10 = false;
	
		    //bean di uscita
		    String dati = "";
	
		    //leggo dal reader
		    while (reader.hasNext()) {
		      //evento
		      XMLEvent event = reader.nextEvent();
		      //entro se è l'apertura di un elemento
		      if (event.isStartElement()) {
		        StartElement element = (StartElement) event;
	
		        //cerco il tag che mi interessa
		        idMsgTrtFound1 = findTag(idMsgTrtTag1, idMsgTrtFound1, element);
		        idMsgTrtFound2 = findTag(idMsgTrtTag2, idMsgTrtFound2, element);
		        idMsgTrtFound3 = findTag(idMsgTrtTag3, idMsgTrtFound3, element);
		        idMsgTrtFound4 = findTag(idMsgTrtTag4, idMsgTrtFound4, element);
		        idMsgTrtFound5 = findTag(idMsgTrtTag5, idMsgTrtFound5, element);
		        idMsgTrtFound6 = findTag(idMsgTrtTag6, idMsgTrtFound6, element);
		        idMsgTrtFound7 = findTag(idMsgTrtTag7, idMsgTrtFound7, element);
		        idMsgTrtFound8 = findTag(idMsgTrtTag8, idMsgTrtFound8, element);
		        idMsgTrtFound9 = findAttributeTag(idMsgTrtTag9, idMsgTrtFound9, "Pendenza", element);
		        if (idMsgTrtFound9) {
		        	idMsgTrtFound9 = getAttributeTag(tags, 8, idMsgTrtTag9, "Pendenza", element);
		        	idMsgTrtFound10 = getAttributeTag(tags, 9, idMsgTrtTag10, "Pendenza", element);	
				}		        			        
		        //stampo gli attributi del tag
		        Iterator iterator = element.getAttributes();
		        while (iterator.hasNext()) {
		          Attribute attribute = (Attribute) iterator.next();
		          QName name = attribute.getName();
		          String value = attribute.getValue();
		        }
		      }
		      //entro se è la chiusura di un elemento
		      if (event.isEndElement()) {
		        EndElement element = (EndElement) event;
		      }
		      //entro e stampo il contenuto di un tag
		      if (event.isCharacters()) {
		        Characters characters = (Characters) event;
		        idMsgTrtFound1 = getTagValue(tags, 0,idMsgTrtFound1, characters);
		        idMsgTrtFound2 = getTagValue(tags, 1,idMsgTrtFound2, characters);
		        idMsgTrtFound3 = getTagValue(tags, 2,idMsgTrtFound3, characters);
		        idMsgTrtFound4 = getTagValue(tags, 3,idMsgTrtFound4, characters);
		        idMsgTrtFound5 = getTagValue(tags, 4,idMsgTrtFound5, characters);
		        idMsgTrtFound6 = getTagValue(tags, 5,idMsgTrtFound6, characters);
		        idMsgTrtFound7 = getTagValue(tags, 6,idMsgTrtFound7, characters);
		        idMsgTrtFound8 = getTagValue(tags, 7,idMsgTrtFound8, characters);
		      }
		        
		    }
	
		    logger.info(" DataStorageManager, trovati tags = " + tags[0] + " - " + tags[1] + " - " + tags[2] + " - " 
		    			+ tags[3] + " - " + tags[4] + " - " + tags[5]  
		    			+ " - " + tags[6] + " - " + tags[7] + " - " + tags[8] );
		    return tags;
		}

	/**
	 * 
	 * @param idMsgTrtTag9
	 * @param idMsgTrtFound9
	 * @param attributeValue
	 * @param element
	 * @return
	 */
	private boolean findAttributeTag(String idMsgTrtTag9, boolean idMsgTrtFound9, String attributeValue, StartElement element) {
		
        if (attributeValue.equals(element.getName().getLocalPart())) {
	        Iterator iter = element.getAttributes();
	        while (iter.hasNext()) { 
				Object object = (Object) iter.next();
				logger.info("Tag=" + attributeValue + " Name/Value Attributo: " + ((Attribute)object).getName() + " - " + ((Attribute)object).getValue()  );	
				if (idMsgTrtTag9.equals((((Attribute)object).getName()).toString())) {
					String value = ((Attribute)object).getValue();
					idMsgTrtFound9 = true;
				}
			}					
		}		
        return idMsgTrtFound9;
	}
	
	/**
	 * 
	 * @param idMsgTrtTag9
	 * @param idMsgTrtFound9
	 * @param attributeValue
	 * @param element
	 * @return
	 */
	private boolean getAttributeTag(String[] tags, int pos, String idMsgTrtTag9, String attributeValue, StartElement element) {
		
		boolean found = false;
        if (attributeValue.equals(element.getName().getLocalPart())) {
	        Iterator iter = element.getAttributes();
	        while (iter.hasNext()) {
				Object object = (Object) iter.next();
				logger.info("Tag=" + attributeValue + " Name/Value Attributo: " + ((Attribute)object).getName() + " - " + ((Attribute)object).getValue()  );	
				if (idMsgTrtTag9.equals((((Attribute)object).getName()).toString())) {
					tags[pos] = ((Attribute)object).getValue();
				}
			}					
		}		
        return true;
	}	


	/**
	 *
	 * @param tags
	 * @param pos
	 * @param idMsgTrtFound1
	 * @param characters
	 * @return
	 */
	private boolean getTagValue(String[] tags, int pos, boolean idMsgTrtFound,
		Characters characters) {
			if (idMsgTrtFound) {
				tags[pos] = characters.getData().trim();
				idMsgTrtFound = false;
			}
			return idMsgTrtFound;
		}
	
	/**
	 *
	 * @param idMsgTrtTag1
	 * @param idMsgTrtFound1
	 * @param element
	 * @return
	 */
	private boolean findTag(String idMsgTrtTag1, boolean idMsgTrtFound1,
			StartElement element) {
		if (element.getName().getLocalPart().equals(idMsgTrtTag1)) {
			idMsgTrtFound1 = true;
		}
		return idMsgTrtFound1;
	}	

	/**
	 *
	 * @param idMsgTrtTag1
	 * @param idMsgTrtFound1
	 * @param element
	 * @return
	 */
	private boolean e(String idMsgTrtTag1, boolean idMsgTrtFound1, StartElement element) {
		if (element.getName().getLocalPart().equals(idMsgTrtTag1)) {
			idMsgTrtFound1 = true;
		}
		return idMsgTrtFound1;
	}
	
	
	/**
	 *
	 * @param em
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param stato
	 */
	void updateStatoPendenza(EntityManager em, String E2EMsgId,
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
	 * TODO FIXARE QUESTO METODO !!!
	 * @return
	 */
	public EntityManager getManager() {
		//quando siamo su JBOSS ci vuole questa riga
//		return emf.createEntityManager();
		//quando siamo su TEST JUNIT ci vuole questa riga
//		return manager;
		return manager;
	}

	/**
	 *
	 * @param em
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param esitoXML
	 */
	protected void insertEsitoCart(String E2EMsgId, String idMittente,
			String silMittente, String esitoXML, String stato, String trtSenderId, String trtSenderSys) {
		
		EsitiCart esitCart = new EsitiCart();
		esitCart.setEsitoXml(esitoXML.getBytes(Charset.forName("US-ASCII")));
		EsitiCartPK esitCartPK = new EsitiCartPK();
		esitCartPK.setE2emsgid(E2EMsgId);
		esitCartPK.setSenderid(idMittente);
		esitCartPK.setSendersys(silMittente);
		esitCart.setPk(esitCartPK);
		esitCart.setTrtSenderId(trtSenderId);
		esitCart.setTrtSenderSys(trtSenderSys);
		esitCart.setStato(stato);
		esitCart.setOpInserimento("CART-LISTENER");
		esitCart.setTimestampInvio(new Timestamp(System.currentTimeMillis()));
		esitCart.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		dataTx.persistEsitiCart(esitCart);
		//em.persist(esitCart);
		
	}
	

	/**
	 *
	 * @param message
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param trtSenderSys 
	 * @param trtSenderId 
	 * @return  
	 */
	protected String saveBlobOnFile(String message, String E2EMsgId, 
				String idMittente, String silMittente, 
				String trtSenderId, String trtSenderSys) {
			FileOutputStream out;
			File file = null;
			//String pathDirectory  = (String)ServiceLocator.getServiceName("directoryFileXml");
		String pathDirectory  = null; 
		String fileName = null;
		try {
			pathDirectory  = IrisProperties.getProperty("DIRECTORY_FILE_XML"); 
			fileName = pathDirectory+E2EMsgId+idMittente+silMittente+".xml";			
			file = new File(fileName);
			FileWriter fileWriter= new FileWriter(file);
			fileWriter.write(message);
			fileWriter.flush();
		} catch (FileNotFoundException e1) {
			logger.error(this.getClass().getSimpleName() + " : Error writing XML file " + e1.getMessage() );
			throw new RuntimeException("Error Writing File " + e1.getMessage());
		} catch (IOException e) {
			logger.error(this.getClass().getSimpleName() + " : Error writing XML file " + e.getMessage() );
			throw new RuntimeException("Error Writing File " + e.getMessage());
		} catch (RuntimeException e) {
			logger.error(this.getClass().getSimpleName() + " : Error writing XML file " + e.getMessage() );
			throw e;
		}
		logger.info(" fileSaved " + fileName );
		return fileName;
	}
	
	/**
	 * 
	 * @param message
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @return
	 */
	protected void removeBlobFromFS(String fileName) {
		FileOutputStream out;
		File file = null;
		boolean deleted = false;
		//String pathDirectory  = (String)ServiceLocator.getServiceName("directoryFileXml");
//		String pathDirectory  = null; 
		try {
//				pathDirectory  = IrisProperties.getProperty("DIRECTORY_FILE_XML"); 
//				fileName = pathDirectory+E2EMsgId+idMittente+silMittente+".xml";			
			file = new File(fileName);
			
			deleted = file.delete();
			
//		} catch (FileNotFoundException e1) {
//			logger.error(this.getClass().getSimpleName() + " : Error Deleting XML file " + e1.getMessage() );
//			throw new RuntimeException("Error Deleting File " + e1.getMessage());
//		} catch (IOException e) {
//			logger.error(this.getClass().getSimpleName() + " : Error Deleting XML file " + e.getMessage() );
//			throw new RuntimeException("Error Deleting File " + e.getMessage());
		} catch (RuntimeException e) {
			logger.error(this.getClass().getSimpleName() + " : Error Deleting XML file " + e.getMessage() );
			throw e;
		}
		logger.info(" fileDelete " + fileName + " = " + deleted);
	}
	

	/**
	 *
	 * @param esitoB
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param idRicevente
	 * @param silRicevente
	 */
	protected String managePagamentoNonUnivoche(EsitoBuilder esitoB, String serviceNameType, String E2EMsgId, String idMittente, String silMittente,
			String idRicevente, String silRicevente, String trtIdMittente, String trtSilMittente) {
		
		String esitoXML = "";
		try {
//			em = getManager();
//			tx = em.getTransaction();
//			tx.begin();
	
			dataTx.updateStatoPendenza(E2EMsgId, idMittente, silMittente, StatoEnum.DA_SPEDIRE);
			//updateStatoPendenza(em, E2EMsgId, idMittente, silMittente, StatoEnum.DA_SPEDIRE);
			logger.info(" ho cambiato lo stato della pendenza_cart =  " + StatoEnum.DA_SPEDIRE );
	
			//spedisco esito globale per dire che XML contiene id Pagamento non univoci
			Set setPagamentoUnivoche = new HashSet<ExtendedErrorInfoImpl>();
			String errore = "Id Pagamento non univoci";
			ExtendedErrorInfoImpl typeTrunc = new ExtendedErrorInfoImpl("A0000017",0,null,null,null,null,null,null,errore);
			setPagamentoUnivoche.add(typeTrunc);
			esitoXML = esitoB.creaEsitoXmlNonValido(serviceNameType, E2EMsgId,idMittente,silMittente,idRicevente,silRicevente, "A0000017", setPagamentoUnivoche, trtIdMittente, trtSilMittente);
			logger.info( " Esito Xml Per ricezione File con idPagamento non univoco " );
			logger.debug( esitoXML);
	
			//salvo l'esito da spedire in tabella
			insertEsitoCartBridge(E2EMsgId, idMittente, silMittente, esitoXML, trtIdMittente, trtSilMittente);
			logger.info("Inserito esitoCart XML Per ricezione File con idPagamento non univoco , idBusta = " + E2EMsgId + " - " + idMittente + " - " + silMittente );

			dataTx.updateStatoPendenza(E2EMsgId, idMittente, silMittente, StatoEnum.IN_SPEDIZIONE);
			//updateStatoPendenza(em, E2EMsgId, idMittente, silMittente, StatoEnum.IN_SPEDIZIONE);
			logger.info(" ho cambiato lo stato della pendenza_cart =  " + StatoEnum.IN_SPEDIZIONE );
			
			dataTx.flush();
		
		} catch (Exception ex) {
			logger.error("Error UPDATE stato IN SPEDIZIONE / Spedizione Esito Globale " + ex.getMessage() );
		} 
		return esitoXML;
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
	 * @param otf 
	 * @param prevData
	 * @param trtSenderSys 
	 * @param trtSenderId 
	 */
	protected void savingBlobOnDatabase(String message,
			String E2EMsgId, String idMittente, String silMittente, 
			String idRicevente, String silRicevente,
			String otf, PrevisitingData prevData, 
			String trtSenderId, String trtSenderSys) {
		
		PendenzeCartMessage cart = createPendenzeRecord(message, E2EMsgId, idMittente, silMittente, idRicevente, silRicevente, otf,
				prevData, trtSenderId, trtSenderSys);
		
		//persist please
		dataTx.persistPendenzeCart(cart);
		
	}


	private PendenzeCartMessage createPendenzeRecord(String message, String E2EMsgId, String idMittente, String silMittente,
			String idRicevente, String silRicevente, String otf, PrevisitingData prevData, String trtSenderId,
			String trtSenderSys) {
		PendenzeCartMessage cart = new PendenzeCartMessage();
		cart.setMessaggioXml(message.getBytes(Charset.forName("US-ASCII")));
	
		if ("UpdateMassivo".equals(prevData.getTipoOperazione())) {
			cart.setStato(StatoEnum.DA_ELABORARE_MASSIVO); 
		} else {
			cart.setStato(StatoEnum.DA_ELABORARE);
		}	
	
		PendenzeCartPK cartPK = new PendenzeCartPK();
		cartPK.setE2emsgid(E2EMsgId);
		cartPK.setSenderid(idMittente);
		cartPK.setSendersys(silMittente);
		cart.setPk(cartPK);
		cart.setReceiverid(idRicevente);
		cart.setReceiversys(silRicevente);
		cart.setTrtSenderId(trtSenderId);
		cart.setTrtSenderSys(trtSenderSys);
		
		cart.setTipoOperazione(prevData.getTipoOperazione());
		cart.setTipoTributo(prevData.getTipoPendenza());
			
		setTipoMessaggio("UpdateMassivo", prevData, otf, cart);			
	
		cart.setTimestampRicezione(new Timestamp(System.currentTimeMillis()));
		cart.setNumPendenze(prevData.getNumPendXPath());
		return cart;
	}
	
	
	protected void savingSemplificationBlobOnDatabase(String message,
			String E2EMsgId, String idMittente, String silMittente, 
			String idRicevente, String silRicevente,
			String otf, PrevisitingData prevData, 
			String trtSenderId, String trtSenderSys) {
		
		PendenzeCartMessage cart = createPendenzeRecord(message, E2EMsgId, idMittente, silMittente, idRicevente, silRicevente, otf,
				prevData, trtSenderId, trtSenderSys);
		
		dataTx.persistSemplificationPendenzeCart(cart);
	}
	
	/**
	 * 
	 * @param tipoUpd
	 * @param otf 
	 * @param tipoOperazione 
	 */
	protected abstract void setTipoMessaggio(String tipoUpd, PrevisitingData prevData, String otf, PendenzeCartMessage cart);

	/**
	 *
	 * @param message
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @return
	 */
	protected boolean findSilEnte(EntityManager em, 
			String idMittente, String silMittente, 
			String trtIdMittente, String trtSilMittente) {
	
		//OLD STYLE QUERT
//		Query queryFindSIL = em.createQuery ("" +
//				" select SIL from Sil as SIL, Enti as ENTI " +
//				"	where SIL.id.idEnte=ENTI.idEnte " +
//				" and SIL.id.idSystem = :idSystem " + 
//				" and ENTI.cdEnte = :idMittente ");
		Query queryFindSIL = em.createNamedQuery("checkSIL");	
		queryFindSIL.setParameter("idSystem", silMittente);
		queryFindSIL.setParameter("idMittente", idMittente);
	
		List<Sil> silList = queryFindSIL.getResultList();
	
		if (silList!=null && silList.size()==1) {
			//se ho trovato il SIL allora posso procedere
			logger.info(" SIL trovato ");
			//se ho trovato il SIL allora posso procedere
			//... ma devo controllare anche la tratta!!
			
			Sil sil = silList.get(0);
			
			String trtId = sil.getTrtId();
			String trtSystem = sil.getTrtSystem();
			//String trtCheck = sil.getTrtCheck();			
			logger.info(" TRT " + trtId + trtSystem);
			
			// se per il SIL è specificato che 
			// il messaggio viene inviato tramite 
			// un Header di tratta particolare
			// allora lo controllo 
			// (trtCheck = A significa CONTROLLO TRATTA ATTIVO)
			//dopo riunione, controllo tratta sempre attivo
			//if (trtCheck!=null && trtCheck.equals("A")) {
				// controllo che la tratta specificata
				// sia uguale alla tratta inviata
				if (trtId.equals(trtIdMittente)
						&& trtSystem.equals(trtSilMittente)) {
					logger.info(" SIL trovato, TRT trovato ");
					return true;
				} else {
					logger.info(" SIL trovato, TRT non trovato ");
					return false;
				}
			//} else {
			//	logger.info(" SIL trovato, TRT non controllato ");
			//	return true;
			//}						
			
		} else {
			logger.info(" SIL NON trovato ");
			return false;
		}
	}
	
	
	
	/**
	 *
	 * @param message
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @return
	 */
	protected boolean findEnteTributo(EntityManager em, 
			String idMittente, String cdTrbEnte) {
		boolean result = false;
		//recupero il cdEnte
		Query queryFindEnte = em.createNamedQuery("TributiEntiByCdEnte");	
		queryFindEnte.setParameter("cdEnte", idMittente);
		String idEnte = "";
		List<Enti> entiList = queryFindEnte.getResultList();
		if (entiList!=null && entiList.size()==1) {
			Enti ente = entiList.get(0);			
			idEnte = ente.getIdEnte(); 

			//ok ho trovato 
			Query queryFindEnteTributo = em.createNamedQuery("TributiEntiByIdEnteCdTrbEnte");	
			queryFindEnteTributo.setParameter("idEnte", idEnte);
			queryFindEnteTributo.setParameter("cdTrbEnte", cdTrbEnte);
		
			List<TributiEnti> entiTributoList = queryFindEnteTributo.getResultList();
			logger.info(" Certo Ente id = " + idMittente + " Trb " + cdTrbEnte);	
			if (entiTributoList!=null && entiTributoList.size()==1) {
				TributiEnti enteTributo = entiTributoList.get(0);			
				if ("Y".equals(enteTributo.getFlReplaceOTF())) {
					result = true;
				}
			} 		
		} 
//		else {
//			result = false;
//		}
		
		return result;		
	}	
	
	
	/**
	 * 
	 * @param em
	 * @param hashSet
	 * @param silMittente
	 * @return
	 */
	protected boolean checkIbanTrusted(EntityManager em, HashSet hashSet, String silMittente, String tipoTributo) {
	
		Query queryIban = manager.createNamedQuery("listaIban");				
		queryIban.setParameter("listaIban", hashSet);
		queryIban.setParameter("silMittente", silMittente);
		queryIban.setParameter("tipoTributo", tipoTributo);		
		
		List<Iban> listaIbanTrusted = (List<Iban>) queryIban.getResultList();
	
		if (listaIbanTrusted!=null && listaIbanTrusted.size()==hashSet.size()) {
			//se ho trovato lo stesso numero di IBAN allora procedo
			logger.info(" Tutti gli IBAN sono trusted ");		
			return true;
		} else {
			//se non ho trovato l-IBAN allora ritorno errore
			logger.info(" Ci sono IBAN non trusted  ");
			return false;
		}
	}	
	
	/**
	 *
	 * @param em
	 * @param tx
	 * @param esitoB
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param idRicevente
	 * @param silRicevente
	 * @param set
	 */
	protected String manageXsdNonValido(EsitoBuilder esitoB,
		String serviceNameType, String E2EMsgId, String idMittente, String silMittente, String idRicevente,
		String silRicevente, Set set, String trtIdMittente, String trtSilMittente) {
		
		String esitoXML = "";
			try {
//				em = getManager();
//				tx = em.getTransaction();
//				tx.begin();
		
				//update stato pend			
				dataTx.updateStatoPendenza(E2EMsgId, idMittente, silMittente, StatoEnum.NON_VALIDO_XSDSCHEMA);
				//updateStatoPendenza(em, E2EMsgId, idMittente, silMittente, StatoEnum.NON_VALIDO_XSDSCHEMA);
				logger.info(" ho cambiato lo stato della pendenza_cart = " + StatoEnum.NON_VALIDO_XSDSCHEMA);
		
				//spedisco esito globale per dire che XML NON E VALIDO
				esitoXML = esitoB.creaEsitoXmlNonValido(serviceNameType, E2EMsgId,idMittente,silMittente,idRicevente,silRicevente,"V0000001", set, trtIdMittente, trtSilMittente);
				logger.info( " Esito Xml Non valido \n " + esitoXML);
		
				//inserimento esito da spedire
				insertEsitoCartBridge(E2EMsgId, idMittente, silMittente, esitoXML, trtIdMittente, trtSilMittente);
				logger.info("Inserito esitoCart XML NonValido, idBusta = " + E2EMsgId + " - " + idMittente + " - " + silMittente );		
		
			} catch (Exception ex) {
				logger.error("Error UPDATE stato NON_VALIDO_XSDSCHEMA / Spedizione Esito Globale " + ex.getMessage() );
			} 
		return esitoXML;
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
	protected void updateEsitoAndPendenze(String e2emsgid, String senderId, String senderSys, String esitoDaSpedire) {
				
//		CreazioneEsitoProxy.executeApplicationTransaction(esitoModel);
		//eseguo EJB di Creazione Esito
		//connessione db
		//EntityManager em = null;
		//EntityTransaction tx = null;
		EsitoBuilder esitoBuilder = new EsitoBuilder();

		try {
			//em = getManager();
			//tx = em.getTransaction();
			//inizio transazione... in realtà non deve essere transazione DB ma distribuita su più risorse (jms + Db)
			//tx.begin();
			
			//devo spedire con l IDEGOV che corrisponde al contenuto della lista!
			PendenzeCart pendC = new PendenzeCart();
			PendenzeCartPK pendPK = new PendenzeCartPK();
			pendPK.setE2emsgid(e2emsgid);
			pendPK.setSenderid(senderId);
			pendPK.setSendersys(senderSys);
			pendC.setPk(pendPK);			

			//aggiorno lo stato della pendenza cart
			logger.info(this.getClass().getSimpleName() 
					+ " cambio stato della pendenza ricevuta, " + " idBusta = " + e2emsgid + " - " + senderId + " - " + senderSys );

			//lo stato che si imposta è RISPOSTA_INVIATA, considerazione:
			//l'esito XML non è stato ancora inviato alla rete (viene inviato da CartSenderBean)
			//l'esito XML è stato già prodotto e verrà inviato dal bean successivo CartSenderBean, in transazione.
			//LB 27/10: Lo stato viene impostato a IN SPEDIZIONE
			//LB 27/10: Lo stato della pendenze diventa RISPOSTA INVIATA con il bean successivo
			Query queryUpdatePend = manager.createQuery ("Update PendenzeCart pend " +
					" set pend.stato = :stato  " +
					" , pend.opAggiornamento = :opAggiornamento  " +
					" , pend.tsAggiornamento = :tsAggiornamento  " +
					" WHERE pend.pk.e2emsgid = :e2emsgid" +
					" AND pend.pk.senderid= :senderId " +
					" AND pend.pk.sendersys = :senderSys ");
//				queryUpdatePend.setParameter("stato", StatoEnum.IN_SPEDIZIONE);
			queryUpdatePend.setParameter("stato", StatoEnum.RISPOSTA_INVIATA_WS);
			queryUpdatePend.setParameter("opAggiornamento", "DSE SYNC");
			queryUpdatePend.setParameter("tsAggiornamento", new Timestamp(System.currentTimeMillis()));
			queryUpdatePend.setParameter("e2emsgid", e2emsgid);
			queryUpdatePend.setParameter("senderId", senderId);
			queryUpdatePend.setParameter("senderSys", senderSys);

			int esitiPend = queryUpdatePend.executeUpdate();    
			logger.info(this.getClass().getSimpleName() 
					+ " ho cambiato lo stato della pendenza , num. pendenze = " + esitiPend );

			Query queryUpdateEsiti = manager.createQuery ("Update EsitiPendenza esiti " +
					" set esiti.stato = :stato  " +
					" WHERE esiti.pendenzeCart = :pendenzeCart");
//				queryUpdateEsiti.setParameter("stato", StatoEnum.IN_SPEDIZIONE);
			queryUpdateEsiti.setParameter("stato", StatoEnum.RISPOSTA_INVIATA_WS);				
			queryUpdateEsiti.setParameter("pendenzeCart", pendC);
			int esitiUpd = queryUpdateEsiti.executeUpdate();
			logger.info(this.getClass().getSimpleName() 
					+ " ho cambiato lo stato di tutti gli esiti associati alla pendenza , num. esiti = " + esitiUpd );

			//tx.commit();

		} catch (Exception e) {
			ejbCtx.setRollbackOnly();
			logger.error(this.getClass().getSimpleName() + " ERROR CREAZIONE ESITO = " + e.getMessage());
		}
	}

	/**
	 *
	 * @param idTag
	 * @param contents
	 * @param fileName
	 * @return 
	 * @throws IOException
	 */
	public PrevisitingData previsitingXml(String idTag, String fileName) {
		//build object
		final PrevisitingData prevData = new PrevisitingData();

		try {
			NamespacesInfos nss = new NamespacesInfos( new String[][] {
//					{ "", "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze" },
						{"","http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze"},
						{"h","http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader"},
//							{ "xmlns:i", "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude" },
						{ "i", "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude" },
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

			final Set<String> listaIdPendenzaEnte = new HashSet<String>();
			final Set<Boolean> listaIdPendenzaEnteDuplicated = new HashSet<Boolean>();
			final Set<String> listaIdPagamento = new HashSet<String>();
			final Set<Boolean> listaIdPagamentoDuplicated = new HashSet<Boolean>();
			final Set<String> listaTipiPendenza = new HashSet<String>();
			final Set<Boolean> listaTipiPendenzaDuplicated = new HashSet<Boolean>();
			
			final HashSet<String> listaIban = new HashSet<String>();			
			final int[] countHolder = new int[1];
			countHolder[0] = 0;
			final boolean consistencyFlag = true;

			//versione 1.3 - caso replace
			String IdPagamentoReplacePath = "/IdpAllineamentoPendenze/IdpBody/Pendenza/Replace/InfoPagamento/DettaglioPagamento/IdPagamento";
			bldr.add(IdPagamentoReplacePath,
					new GetContentTextCollectorEffectFactory(
							new FragmentHandler() {
						public void onFragment(String xmlFragment) {
							//aggiungo alla lista il valore di IdPagamento
							//ed alla lista che mi consente di sapere se esistono duplicati o meno
							listaIdPagamentoDuplicated.add(new Boolean(listaIdPagamento.add(xmlFragment)));
						}
					}));
			//versione 1.3 - caso insert
			String IdPagamentoInsertPath = "/IdpAllineamentoPendenze/IdpBody/Pendenza/Insert/InfoPagamento/DettaglioPagamento/IdPagamento";
			bldr.add(IdPagamentoInsertPath,
					new GetContentTextCollectorEffectFactory(
							new FragmentHandler() {
						public void onFragment(String xmlFragment) {
							//aggiungo alla lista il valore di IdPagamento
							//ed alla lista che mi consente di sapere se esistono duplicati o meno
							listaIdPagamentoDuplicated.add(new Boolean(listaIdPagamento.add(xmlFragment)));
						}
					}));
			//versione 1.3 - caso update
			String IdPagamentoUpdatePath = "/IdpAllineamentoPendenze/IdpBody/Pendenza/UpdateStatus/InfoPagamento/DettaglioPagamento/IdPagamento";
			bldr.add(IdPagamentoUpdatePath,
					new GetContentTextCollectorEffectFactory(
							new FragmentHandler() {
						public void onFragment(String xmlFragment) {
							//aggiungo alla lista il valore di IdPagamento
							//ed alla lista che mi consente di sapere se esistono duplicati o meno
							listaIdPagamentoDuplicated.add(new Boolean(listaIdPagamento.add(xmlFragment)));
						}
					}));
			//versione 1.3 - caso massivo
			String IdPagamentoUpdateMassivoPath = "/IdpAllineamentoPendenze/IdpBody/Pendenza/UpdateMassivo/InfoPagamento/DettaglioPagamento/IdPagamento";
			bldr.add(IdPagamentoUpdateMassivoPath,
					new GetContentTextCollectorEffectFactory(
							new FragmentHandler() {
						public void onFragment(String xmlFragment) {
							//aggiungo alla lista il valore di IdPagamento
							//ed alla lista che mi consente di sapere se esistono duplicati o meno
							listaIdPagamentoDuplicated.add(new Boolean(listaIdPagamento.add(xmlFragment)));
						}
					}));


			//versione 1.2
			//bldr.add("/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/IdPendenza",
			//versione 1.3
			String IdPendenzaPath = "/IdpAllineamentoPendenze/IdpBody/Pendenza/IdPendenza";
			bldr.add(IdPendenzaPath,
					new GetContentTextCollectorEffectFactory(
							new FragmentHandler() {
						public void onFragment(String xmlFragment) {
							//aggiungo alla lista il valore di IdPendenza
							//ed alla lista che mi consente di sapere se esistono duplicati o meno
							listaIdPendenzaEnteDuplicated.add(new Boolean(listaIdPendenzaEnte.add(xmlFragment)));
						}
					}));
			
			//versione 1.3
			String IbanPath = "/IdpAllineamentoPendenze/IdpBody/Pendenza/Insert/InfoPagamento/DettaglioPagamento/AccreditoPagamento/i:CodiceIBAN";
			bldr.add(IbanPath,
					new GetContentTextCollectorEffectFactory(
							new FragmentHandler() {
						public void onFragment(String xmlFragment) {
							//aggiungo alla lista il valore di IbanPath
							listaIban.add(xmlFragment);
						}
					}));			

			//versione 1.2
			//bldr.add("/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza",
			//versione 1.3
			String PendenzaCount = "/IdpAllineamentoPendenze/IdpBody/Pendenza";
			boolean consistenzaTipoOperazione = true;
			bldr.add(PendenzaCount,
					new FragmentHandler() {
						public void onFragment(String xmlFragment) {
							countHolder[0] = countHolder[0] + 1 ;
							if (
									(xmlFragment.indexOf("TipoOperazione='Insert'")>0
									&& xmlFragment.indexOf("<Insert>")>0) ||
									(xmlFragment.indexOf("TipoOperazione='UpdateStatus'")>0
									&& xmlFragment.indexOf("<UpdateStatus>")>0)	||
									(xmlFragment.indexOf("TipoOperazione='UpdateMassivo'")>0
									&& xmlFragment.indexOf("<UpdateMassivo>")>0)	||
									(xmlFragment.indexOf("TipoOperazione='Replace'")>0
									&& xmlFragment.indexOf("<Replace>")>0) || 
									(xmlFragment.indexOf("TipoOperazione='Delete'")>0)										
							) {
//									logger.info("Messaggio Consistente! (Insert, Update o Replace corretta)" );
							}		else {
//									logger.info("Messaggio NON Consistente! " );
									prevData.setConsistenzaTipoOperazione(false);
							}

							//tipo messaggio
							if ((xmlFragment.indexOf("TipoOperazione='Insert'")>0
								&& xmlFragment.indexOf("<Insert>")>0)
							) {
								prevData.setTipoOperazione("Insert");
							} else if (
								(xmlFragment.indexOf("TipoOperazione='UpdateStatus'")>0
								&& xmlFragment.indexOf("<UpdateStatus>")>0)
							) {
								prevData.setTipoOperazione("UpdateStatus");
							} else if (
								(xmlFragment.indexOf("TipoOperazione='UpdateMassivo'")>0
								&& xmlFragment.indexOf("<UpdateMassivo>")>0)
							)  {
								prevData.setTipoOperazione("UpdateMassivo");
							} else if (
								(xmlFragment.indexOf("TipoOperazione='Replace'")>0
								&& xmlFragment.indexOf("<Replace>")>0)
							) {
								prevData.setTipoOperazione("Replace");
							} else if (xmlFragment.indexOf("TipoOperazione='Delete'")>0)
							{
									prevData.setTipoOperazione("Delete");
							}


							logger.debug(" cerco il tag TipoPendenza = " + xmlFragment);
							if (xmlFragment.indexOf("TipoPendenza")>0)
							{
								String tipoPendenza = (xmlFragment.substring(xmlFragment.indexOf("TipoPendenza='")+14)).substring(0,xmlFragment.substring(xmlFragment.indexOf("TipoPendenza='")+14).indexOf("'"));
								logger.debug(" estratto il tributo = " + tipoPendenza);
								prevData.setTipoPendenza(tipoPendenza);
								//aggiungo alla lista il valore di IdPagamento
								//ed alla lista che mi consente di sapere se esistono duplicati o meno
								listaTipiPendenzaDuplicated.add(new Boolean(listaTipiPendenza.add(tipoPendenza)));
							}

						}

					});


			//nuovo file
			File xmlFile = new File(fileName);

			DefaultHandler handler = bldr.create();
			//parsing
			saxParse(new FileReader(xmlFile), handler);

			//esistono duplicati di idPendenza ?
			boolean duplicated = false;
			Iterator iter = listaIdPendenzaEnteDuplicated.iterator();
			while (iter.hasNext()) {
				Boolean bol = (Boolean)iter.next();
				if (bol.booleanValue()==false) {
					duplicated = true;
				}
			}

			//esistono duplicati di idPagamento ?
			boolean pagamentoDuplicated = false;
			Iterator iterPag = listaIdPagamentoDuplicated.iterator();
			while (iterPag.hasNext()) {
				Boolean bol = (Boolean)iterPag.next();
				if (bol.booleanValue()==false) {
					pagamentoDuplicated = true;
				}
			}

			//NON devono esistere duplicati di tipopendenza ?
			boolean tipoPendenzaUnivoco = true;
			if (listaTipiPendenza.size()>1) {
				tipoPendenzaUnivoco = false;
			}

			long timeEnd  = System.currentTimeMillis();
			long timeAll = timeStart - timeEnd;

			logger.info("Time Needed to parse XML"+timeAll);

			//num pendenze trovate
			prevData.setNumPendXPath(countHolder[0]);
			//num pendenze duplicate
			prevData.setPendenzeUnivoche(duplicated);
			//num pagamento duplicati
			prevData.setIdPagamentoUnivoci(pagamentoDuplicated);
			//tipo tributo duplicati
			prevData.setTipoPendenzaUnivoco(tipoPendenzaUnivoco);
			//lista iban
			prevData.setIban(listaIban);

			logger.info("IdPendenza duplicati = "+duplicated);
			logger.info("IdPagamento duplicati = "+pagamentoDuplicated);				
			logger.info("TipoPendenza univoco (tipo) = "+tipoPendenzaUnivoco + " (" + prevData.getTipoPendenza() + ")");
			logger.info("count(/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza) = "+countHolder[0]);
			logger.info("TipoOperazione CONSISTENTE = "+prevData.isConsistenzaTipoOperazione());
			logger.info("TipoOperazione = "+prevData.getTipoOperazione());
			logger.info("N° Iban diversi = "+  (prevData.getIban()!=null ? prevData.getIban().size() : "NO IBAN") );

		} catch (FileNotFoundException e1) {
			logger.error(this.getClass().getSimpleName() + " : Error Counting Pendenze " + e1.getMessage() );
			throw new RuntimeException("Error Counting Pendenze " + e1.getMessage());
		} catch (FactoryConfigurationError e) {
			logger.error(this.getClass().getSimpleName() + " : Error Counting Pendenze " + e.getMessage() );
			throw new RuntimeException("Error Counting Pendenze " + e.getMessage());
		}

		return prevData;
	}

	/**
	 *
	 * @param em
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param stato
	 */
	protected boolean checkEnteTributo(EntityManager em, String idMittente, String silMittente,
		String tipoPendenza) {
		
			boolean enteUnicoAttivo = false;
			boolean tributoUnicoAttivo = false;
			Enti ente = null;
		
			Query queryUpdatePend = em.createQuery ("select Enti from Enti ENTI " +
					" WHERE ENTI.cdEnte  = :idEnte " +
					" AND ENTI.stato = :stato ");
			queryUpdatePend.setParameter("stato", "A");
			queryUpdatePend.setParameter("idEnte", idMittente);
			List<Enti> listaEnti = queryUpdatePend.getResultList();
			if (listaEnti.size()==1) {
				enteUnicoAttivo = true;
				ente = listaEnti.get(0);
			}
		
			if (enteUnicoAttivo) {
				//se c'è un ente... allora controllo anche il tributo
				Query querySelectTrib = em.createQuery ("select stato from TributiEnti tributi " +
						" WHERE " +
						" tributi.id.idEnte = :idEnte AND " +
						" tributi.id.cdTrbEnte = :cdTrbEnte AND " +
						" tributi.idSystem = :silMittente AND " +
						" tributi.stato = :stato ");
				querySelectTrib.setParameter("idEnte", ente.getIdEnte());
				querySelectTrib.setParameter("cdTrbEnte", tipoPendenza);
				querySelectTrib.setParameter("silMittente", silMittente);
				querySelectTrib.setParameter("stato", "A");
				List<TributiEnti> listaTributi = querySelectTrib.getResultList();
		
				if (listaTributi.size()==1) tributoUnicoAttivo = true;
			}
		
		
		
			if (enteUnicoAttivo==true && tributoUnicoAttivo==true) {
				return true;
			} else {
				return false;
			}
		
		}

	/**
	 *
	 * @param em
	 * @param tx
	 * @param esitoB
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param idRicevente
	 * @param silRicevente
	 */
	protected void manageXmlMassivoNonValido(
		EsitoBuilder esitoB, String serviceNameType, String E2EMsgId, String idMittente, String silMittente,
		String idRicevente, String silRicevente, String trtIdMittente, String trtSilMittente) 
	{
		try {
//			em = getManager();
//			tx = em.getTransaction();
//			tx.begin();
	
			dataTx.updateStatoPendenza(E2EMsgId, idMittente, silMittente, StatoEnum.DA_SPEDIRE);
			//updateStatoPendenza(em, E2EMsgId, idMittente, silMittente, StatoEnum.DA_SPEDIRE);
			logger.info(" (updMassivo) ho cambiato lo stato della pendenza_cart =  " + StatoEnum.DA_SPEDIRE );
	
			//spedisco esito globale per dire che XML contiene id Pagamento non univoci
			Set setSilInesistente = new HashSet<ExtendedErrorInfoImpl>();
			String errore = "TipoPendenza non univoco per TipoOperazione di Update Massivo";
			ExtendedErrorInfoImpl typeTrunc = new ExtendedErrorInfoImpl("A0000019",0,null,null,null,null,null,null,errore);
			setSilInesistente.add(typeTrunc);
			String esitoXML = esitoB.creaEsitoXmlNonValido(serviceNameType, E2EMsgId,idMittente,silMittente,idRicevente,silRicevente, "A0000019", setSilInesistente, trtIdMittente, trtSilMittente);
			logger.info( " (updMassivo) Esito Xml Per ricezione File con TipoPendenza non univoco per TipoOperazione di Update Massivo " );
			logger.debug( esitoXML);
	
			//salvo l'esito da spedire in tabella
			insertEsitoCart(E2EMsgId, idMittente, silMittente, esitoXML, StatoEnum.DA_SPEDIRE, trtIdMittente, trtSilMittente);
			logger.info(" (updMassivo) Inserito esitoCart XML Per ricezione File con TipoPendenza non univoco per TipoOperazione di Update Massivo , idBusta = " + E2EMsgId + " - " + idMittente + " - " + silMittente );
	
			dataTx.updateStatoPendenza(E2EMsgId, idMittente, silMittente, StatoEnum.IN_SPEDIZIONE);
			//updateStatoPendenza(em, E2EMsgId, idMittente, silMittente, StatoEnum.IN_SPEDIZIONE);
			logger.info(" (updMassivo) ho cambiato lo stato della pendenza_cart =  " + StatoEnum.IN_SPEDIZIONE );
	
		} catch (Exception ex) {
			logger.error(" (updMassivo) Error UPDATE stato IN SPEDIZIONE / Spedizione Esito Globale " + ex.getMessage() );
		} 
	}


	//	public DataStorageManagerCommon() {
	//		super();
	//	}
	
	
	/**
	 *
	 * @param em
	 * @param tx
	 * @param esitoB
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param idRicevente
	 * @param silRicevente
	 */
	protected String manageChiaveMessaggioDuplicata(
		EsitoBuilder esitoB, String serviceNameType, String E2EMsgId, String idMittente, String silMittente,
		String idRicevente, String silRicevente, String trtIdMittente, String trtSilMittente) {
			
		String esitoXML = "";
		try {

			//spedisco esito globale per dire che XML contiene e2emsg già processato
			Set setPendenzeUnivoche = new HashSet<ExtendedErrorInfoImpl>();
			String errore = "Messaggio gia' processato (chiave duplicata)";
			ExtendedErrorInfoImpl typeTrunc = new ExtendedErrorInfoImpl("A0000035",0,null,null,null,null,null,null,errore);
			setPendenzeUnivoche.add(typeTrunc);
			esitoXML = esitoB.creaEsitoXmlNonValido(serviceNameType, E2EMsgId,idMittente,silMittente,idRicevente,silRicevente, "A0000035", setPendenzeUnivoche, trtIdMittente, trtSilMittente );
			logger.info( " Esito Xml Per ricezione File con e2emsgid/id/sys duplicato " );
			logger.debug( esitoXML);
			
			dataTx.flush();
	
		} catch (Exception ex) {
			logger.error("Error UPDATE stato IN SPEDIZIONE / Spedizione Esito Globale " + ex.getMessage() );
		} 
		return esitoXML;
	}	

}
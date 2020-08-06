package it.tasgroup.idp.billerservices.controller;

import it.tasgroup.idp.billerservices.api.GestorePendenze;
import it.tasgroup.idp.billerservices.api.GestoreVerificheAnagrafiche;
import it.tasgroup.idp.billerservices.api.LoaderException;
import it.tasgroup.idp.billerservices.api.GestorePendenze.EnumTipoAllineamento;
import it.tasgroup.idp.billerservices.plugin.rfc.RfcToModelMapper;
import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.messaggi.ErroriEsitiPendenza;
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.EsitiCartPK;
import it.tasgroup.idp.domain.messaggi.EsitiPendenza;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartMessage;
import it.tasgroup.idp.domain.messaggi.PendenzeCartPK;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.domain.posizioneDebitoria.Pendenze;
import it.tasgroup.idp.iuvgenerator.IUVGenerator;
import it.tasgroup.idp.iuvgenerator.IUVGeneratorLocal;
import it.tasgroup.idp.util.GeneratoreIdUnivoci;
import it.tasgroup.idp.util.JAXBContextProvider;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.iris.domain.CarrelloGateway;
import it.tasgroup.iris.domain.CarrelloGatewayPK;
import it.tasgroup.iris.domain.CfgIrisGatewayClient;
import it.tasgroup.iris.domain.SessioneGateway;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.IdpAllineamentoPendenzeMultiOTFElementType;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.IdpAllineamentoPendenzeOTF;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.Pendenza;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.Esito;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpMultiEsitoOTFElement;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.IdpOTF;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeMultiEnteOTF;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeMultiEnteOTFEsito;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ComunicazionePosizioniDebitorieControllerTransactionsImpl implements ComunicazionePosizioniDebitorieControllerTransactions{

	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)  
	private EntityManager em;
	
	@EJB(name="IUVGenerator")
	private IUVGeneratorLocal iuvGenerator;
	
	private final Log logger = LogFactory.getLog(this.getClass());	
	
	@Override
	/**
	 * Inserisce le posizioni ed apre in modo transazionale la relativa sessione gateway
	 * ed inserisce i dati di tracking sulle tabelle PENDENZE_CART, ESITI_CART
	 *
	 */
	public IdpAllineamentoPendenzeMultiEnteOTFEsito insertAndOpenSession(IdpAllineamentoPendenzeMultiEnteOTF allineamentoPendenzeOTF) throws LoaderException {
		
		Set<String> condizioni = new HashSet<String>();
		RfcToModelMapper mapper = new RfcToModelMapper();
		ComunicazionePosizioniDebitorieControllerEsitoBuilder esitoBuilder = new ComunicazionePosizioniDebitorieControllerEsitoBuilder();
		Set<Pendenze> pendenzeSet = new HashSet<Pendenze>();  //Array pendenze inserite
		
		// Inserimento delle pendenze
		for(IdpAllineamentoPendenzeMultiOTFElementType msg:allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getIdpAllineamentoPendenzeOTF()) {

			// Univocit� degli E2EMsgid nel messaggio
			String  e2emsgid = msg.getE2EMsgId();
			
			for (Pendenza p:msg.getIdpBody().getPendenza()) {
				
				Enti ente = GestoreVerificheAnagrafiche.getEnteByCodEnte(msg.getE2ESender().getE2ESndrId(), em);
				TributiEnti tributo = GestoreVerificheAnagrafiche.getTributoEnteByIdEnteCodTributo(ente.getIdEnte(), msg.getIdpBody().getPendenza().get(0).getTipoPendenza(), em);
				Pendenze pendenza = mapper.mapMessagePendenzaToModel(p, ente, tributo, msg.getE2ESender().getE2ESndrSys(), true);
				for (CondizioniPagamento condizione : pendenza.getCondizioniPagamento()) {
					condizioni.add(condizione.getIdCondizione());
				}
				
				GestorePendenze.allineaPendenza(pendenza, EnumTipoAllineamento.INSERT, true, em,iuvGenerator); 
				pendenzeSet.add(pendenza);
				
			}	
		}	
			
		// Inserimento nelle tabelle OTF.
		SessioneGateway sessioneGateway = buildAndSaveNewSessioneGateway(allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getIdpOTF(), condizioni);
		
		// Creazione dell'esito positivo
		IdpAllineamentoPendenzeMultiEnteOTFEsito idpEsito = esitoBuilder.buildEsitoOK(allineamentoPendenzeOTF, sessioneGateway) ;
		
		// Salvataggio nelle tabelle di tracking 
		traceCallOK(allineamentoPendenzeOTF,idpEsito, pendenzeSet);
		
		return idpEsito;

	}

	@Override
	/**
	 * Traccia nelle tabelle di tracking: PENDENZE_CART e ESITI_CART le risultanze
	 * di una chiamata in errore.
	 */
	public void traceCallKO(
			IdpAllineamentoPendenzeMultiEnteOTF allineamentoPendenzeOTF,
			IdpAllineamentoPendenzeMultiEnteOTFEsito esito) {
	
		traceCall(allineamentoPendenzeOTF, esito, null);
		
	}

	
	private void traceCallOK(
			IdpAllineamentoPendenzeMultiEnteOTF allineamentoPendenzeOTF,
			IdpAllineamentoPendenzeMultiEnteOTFEsito esito, Set<Pendenze> pendenzeSet) {
		
		traceCall(allineamentoPendenzeOTF, esito, pendenzeSet);
		
		
	}
		
	private void traceCall(IdpAllineamentoPendenzeMultiEnteOTF allineamentoPendenzeOTF,
		IdpAllineamentoPendenzeMultiEnteOTFEsito esito, Set<Pendenze> pendenzeSet) {
		
		//Marshalling input and output
		ByteArrayOutputStream inputRequestBytes = marshall(allineamentoPendenzeOTF);
		ByteArrayOutputStream outputRequestBytes = marshall(esito);

		
		String trtSenderId=allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getHeaderTRT().getSender().getSenderId();
		String trtSenderSys=allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getHeaderTRT().getSender().getSenderSys();
		String trtE2EmsgId=allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getHeaderTRT().getMsgId();
		String trtReceiverId=allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getHeaderTRT().getReceiver().getReceiverId();
		String trtReceiverSys=allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getHeaderTRT().getReceiver().getReceiverSys();
		
		PendenzeCartPK pcpk=new PendenzeCartPK();
		pcpk.setE2emsgid(trtE2EmsgId);
		pcpk.setSenderid(trtSenderId);
		pcpk.setSendersys(trtSenderSys);
		
		PendenzeCartMessage pc = new PendenzeCartMessage();
		pc.setPk(pcpk);
		pc.setReceiverid(trtReceiverId);
		pc.setReceiversys(trtReceiverSys);
		pc.setMessaggioXml(inputRequestBytes.toByteArray());
		pc.setTimestampRicezione(new Timestamp(System.currentTimeMillis()));
		if (esito.getIdpMultiEsitoOTF().getIdpOTF()!=null) {
			pc.setStato("RISPOSTA OK WS");
		} else {
			pc.setStato("RISPOSTA KO WS");
		}
		pc.setPrVersione(0);
		
		//Calcolo il numero delle pendenze.
		int numPend = 0;
		for (IdpAllineamentoPendenzeMultiOTFElementType msg: allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getIdpAllineamentoPendenzeOTF()) {
			numPend = numPend + msg.getIdpBody().getPendenza().size();
		}
		
		pc.setNumPendenze(numPend);  
		pc.setTipoMessaggio("ALL_PEND_OTF_MX");
		pc.setTipoOperazione("Insert");
		pc.setTipoTributo("Mixed");
		pc.setNumPendDeleted(0);
		pc.setTrtSenderId(trtSenderId);
		pc.setTrtSenderSys(trtSenderSys);
		pc.setOpInserimento("IDP");
		pc.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		
		em.persist(pc);
		em.flush();
		
		PendenzeCart pcr=em.find(PendenzeCart.class, pcpk);
		
		EsitiCartPK ecpk = new EsitiCartPK();
		ecpk.setE2emsgid(trtE2EmsgId);
		ecpk.setSenderid(trtSenderId);
		ecpk.setSendersys(trtSenderSys);
		
		EsitiCart ec = new EsitiCart();
		ec.setPk(ecpk);
		ec.setStato("SPEDITO WS");
		ec.setPrVersione(0);
		ec.setTrtSenderId(trtSenderId);
		ec.setTrtSenderSys(trtSenderSys);
		ec.setEsitoXml(outputRequestBytes.toByteArray());
		ec.setTimestampInvio(new Timestamp(System.currentTimeMillis()));
		ec.setPendenzeCart(pcr);
		ec.setOpInserimento("IDP");
		ec.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		
		em.persist(pc);
		
		// Caso OK{
		if (esito.getIdpMultiEsitoOTF().getIdpOTF()!=null && pendenzeSet!=null) { 
			for (Pendenze p: pendenzeSet) {
				EsitiPendenza esitoPendenza = new EsitiPendenza();
				esitoPendenza.setIdEsitoPendenza(GeneratoreIdUnivoci.GetCurrent().generaId());
				esitoPendenza.setEsitoPendenza("OK");
				esitoPendenza.setDescrizioneEsito("");
				esitoPendenza.setStato("RISPOSTA INVIATA WS");
				esitoPendenza.setIdPendenza(p.getIdPendenza());
				esitoPendenza.setIdPendenzaEnte(p.getIdPendenzaente());
				esitoPendenza.setPrVersione(0);
				esitoPendenza.setPendenzeCart(pcr);
				esitoPendenza.setTrtSenderId(pc.getTrtSenderId());
				esitoPendenza.setTrtSenderSys(pc.getTrtSenderSys());
				esitoPendenza.setOpInserimento("WS_OTF_MX");
				esitoPendenza.setTsInserimento(new Timestamp(System.currentTimeMillis()));
				em.persist(esitoPendenza);
			}
		}
				
		// Caso KO. Inserisco solo un generico KO, altrimenti da DB non si evince che c'� stato un errore.
		if (esito.getIdpMultiEsitoOTF().getIdpOTF()==null && esito.getIdpMultiEsitoOTF().getIdpEsitoOTF()!=null) { 
			
				EsitiPendenza esitoPendenza = new EsitiPendenza();
				esitoPendenza.setIdEsitoPendenza(GeneratoreIdUnivoci.GetCurrent().generaId());
				esitoPendenza.setEsitoPendenza("KO");
				esitoPendenza.setDescrizioneEsito("");
				esitoPendenza.setStato("RISPOSTA INVIATA WS");
				esitoPendenza.setIdPendenza(null);
				esitoPendenza.setIdPendenzaEnte("-");
				esitoPendenza.setPrVersione(0);
				esitoPendenza.setPendenzeCart(pcr);
				esitoPendenza.setTrtSenderId(pc.getTrtSenderId());
				esitoPendenza.setTrtSenderSys(pc.getTrtSenderSys());
				esitoPendenza.setOpInserimento("WS_OTF_MX");
				esitoPendenza.setTsInserimento(new Timestamp(System.currentTimeMillis()));
				
				
				Set<ErroriEsitiPendenza> erroriEsitiPendenzaCollection = new HashSet<ErroriEsitiPendenza>();
				ErroriEsitiPendenza erroriEsitiPendenza = new ErroriEsitiPendenza();
				erroriEsitiPendenza.setPrVersione(0);
				erroriEsitiPendenza.setOpInserimento("WS_OTF_MX");
				erroriEsitiPendenza.setTsInserimento(new Timestamp(System.currentTimeMillis()));
				erroriEsitiPendenza.setIdEsitoPendenza(esitoPendenza);
				erroriEsitiPendenza.setIdPendenza(null);
				erroriEsitiPendenza.setIdPendenzaEnte("-"); 
				erroriEsitiPendenza.setIdErrore(GeneratoreIdUnivoci.GetCurrent().generaId());
				
				// Come codice e descrizione, inserisco il primo che trovo.
				Esito primoEsito= esito.getIdpMultiEsitoOTF().getIdpEsitoOTF().get(0).getIdpBody().getInfoMessaggio().getEsiti().getEsito().get(0);
				erroriEsitiPendenza.setCodice(primoEsito.getCodice());
				erroriEsitiPendenza.setDescrizioneErrore(primoEsito.getDescrizione());
				erroriEsitiPendenzaCollection.add(erroriEsitiPendenza);
				
				
				esitoPendenza.setErroriEsitiPendenzaCollection(erroriEsitiPendenzaCollection);
				em.persist(esitoPendenza);
				em.persist(erroriEsitiPendenza);
			
		}
		
		

		em.persist(ec);
	}
	

	/**
	 * Costruisce e persiste la sessione gateway
	 * @param otf
	 * @param idCondizioni
	 * @param em
	 * @return
	 */
	private SessioneGateway buildAndSaveNewSessioneGateway(IdpOTF otf, Set<String> idCondizioni) {
		
		String token = GeneratoreIdUnivoci.GetCurrent().generaId();

		// Costruzione della SessioneGateway
		String urlCancel = otf.getURLCANCEL();	
		String urlBack = 	otf.getURLBACK(); 		
		Boolean pagOffline = false;
		if (otf.isOFFLINEPAYMENTMETHODS()!=null) {
			pagOffline = otf.isOFFLINEPAYMENTMETHODS();
		}
		SessioneGateway session = new SessioneGateway();			
		if (pagOffline==false) {
			session.setOfflinePaymentMethods(0);	
		} else {
			session.setOfflinePaymentMethods(1);
		}					  				
		session.setUrlBack(urlBack);
		session.setUrlCancel(urlCancel); 
		session.setToken(token);
		
		session.completeForInsert();	
		session.setOperatore("ANONYMOUS");
		session.setIntestatario("ANONYMOUS");
		session.setUsata(0);
		session.setImTotale(BigDecimal.ZERO);			
		//session, altri campi
		session.setSessionId("sessionId"); 				
		
		//completo la table session con la config
		CfgIrisGatewayClient cfgGw2 = em.find(CfgIrisGatewayClient.class, new Long(2));  //TODO: OMG! Scablare il  "2" e cercare per KEYWORD! 
		session.setCfgIrisGatewayClient(cfgGw2);
		
		//aggiungo carrello
		Set<CarrelloGateway> setG = new HashSet<CarrelloGateway>();
		

		for (String idCond:idCondizioni) {
			//creo e completo la table carrello
			CarrelloGatewayPK sessPk = new CarrelloGatewayPK();
			sessPk.setToken(session.getToken());
			sessPk.setIdCondizione(idCond);				

			CarrelloGateway sessId = new CarrelloGateway();
			sessId.completeForInsert();
			sessId.setImTotale(BigDecimal.ZERO);
			sessId.setId(sessPk);
			//aggiungo al set
			setG.add(sessId);
			//aggiungo relazione con table sessioni
			sessId.setSessioneGateway(session);
		}			

		//aggiungo su table sessioni
		session.setCarrelloGateways(setG);		

		em.persist(session);
		
		return session;
	}				
	
	
	

ByteArrayOutputStream marshall(Object root) {
	JAXBContext jaxbContext;
	
	ByteArrayOutputStream marshalled = new  ByteArrayOutputStream();
	
	try {
		// jaxbContext = JAXBContext.newInstance("it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze:it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento:it.toscana.rete.cart.servizi.iris_1_1.idpesito:it.toscana.rete.cart.servizi.iris_1_1.idpheader:it.toscana.rete.cart.servizi.iris_1_1.idpinclude");
		jaxbContext = JAXBContextProvider.getInstance()
			.getJAXBContext (
				new String [] {
					  "it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze"
					, "it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento"
					, "it.toscana.rete.cart.servizi.iris_1_1.idpesito"
					, "it.toscana.rete.cart.servizi.iris_1_1.idpheader"
					, "it.toscana.rete.cart.servizi.iris_1_1.idpinclude"
				}
			);

		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));
									
		//marshall
		marshaller.marshal(root, marshalled);
			
		return marshalled;
		
		
	} catch (JAXBException e) {
		logger.error(e);
		throw new RuntimeException(e);
	}
		
}

}

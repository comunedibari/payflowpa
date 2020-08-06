package it.tasgroup.idp.webservices.helper.gestorecanali;

import it.tasgroup.idp.pojo.UtenteIris;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.iris.comunication.ws.impl.ComunicationPortType;
import it.tasgroup.iris.comunication.ws.impl.ComunicazioneServiceImplService;
import it.tasgroup.iris.comunication.ws.impl.MessaggioLogicoType;
import it.tasgroup.iris.comunication.ws.impl.ParametroCanaleType;
import it.tasgroup.iris.comunication.ws.impl.TipoCanaleType;
import it.tasgroup.iris.comunication.ws.impl.TipoMessaggioType;
import it.tasgroup.iris.comunication.ws.impl.UtenteType;

import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class GestoreCanaliWebServiceHelperImpl implements GestoreCanaliWebServiceHelper {
	
	private QName qname;
	private URL url;	
    private ComunicationPortType port;
    /*** Loggers ***/
	private final Log logger = LogFactory.getLog(this.getClass());
	
	
    
    public GestoreCanaliWebServiceHelperImpl() {
    	
	}

	public void setUp() throws Exception {
		logger.debug(this.getClass().getSimpleName() + "inizializzazione dati connessione web services gestore canali...." );
		try {
			String urlStr = IrisProperties.getProperty("iris.gateway.services.url", true);	// il file viene riletto ogni volta			
			String nspuri = IrisProperties.getProperty("iris.gateway.services.qname.namespace");
			String localPart = IrisProperties.getProperty("iris.gateway.services.qname.localpart");

			url = new URL(urlStr);
			qname = new QName(nspuri, localPart);
			
			ComunicazioneServiceImplService serv = new ComunicazioneServiceImplService(url, qname);
			port = serv.getComunicazioneServiceImplPort();

		} catch(Exception e) {
			logger.error(" Fallito il recupero delle informazioni di connessione ai ws di GESTORE CANALI !!!", e);
			throw new Exception("Fallito il recupero delle informazioni di connessione ai ws di GESTORE CANALI !!!");
			//e.printStackTrace();
		}
	}


	public void sendMessage(List<UtenteIris> destinatari, String subject, String content, String messageType) throws Exception {
		logger.debug(this.getClass().getSimpleName() + "chiamata sendMessage gestore canali....");
		if (url == null || qname == null || port == null) {
			setUp();
		}

		UtenteType utente = null;

		List<UtenteType> utenti = new ArrayList<UtenteType>();

		if (destinatari != null) {
			Iterator<UtenteIris> iterDest = destinatari.iterator();
			logger.debug(this.getClass().getSimpleName() + "utenti destinatari email: ");
			UtenteIris utenteCorrente = null;
			while (iterDest.hasNext()) {
				// faccio comunque il subscribe del canale email
				utenteCorrente = iterDest.next();
				utente = new UtenteType();
				utente.setIdUtente(utenteCorrente.getIdUtente());
				utente.setScopoMessaggio(utenteCorrente.getScopoMessaggio());

				logger.debug(this.getClass().getSimpleName() + "utenti destinatari email: " + utente.getIdUtente());
				// solo per la quietanza
				if (utenteCorrente.isSubscribe()) {
					logger.debug(this.getClass().getSimpleName() + "subscribe email utente: " + utente.getIdUtente());
					ParametroCanaleType parametroCanale = new ParametroCanaleType();
					parametroCanale.setParametro(utenteCorrente.getEmail());
					port.subscribeCanali(utente, TipoCanaleType.E_MAIL, parametroCanale);
					logger.debug(this.getClass().getSimpleName() + "subscribe email utente: " + utente.getIdUtente() + "eseguito con success");
				}
				utenti.add(utente);
			}
		}

		MessaggioLogicoType messaggioLogico = new MessaggioLogicoType();
		String mittenteEmail = IrisProperties.getProperty("GESTORE_EVENTI_MITTENTE_MAIL");
		if (mittenteEmail != null) {
			messaggioLogico.setMittente(mittenteEmail);
		} else {
			messaggioLogico.setMittente("iris@regione.it");
		}
		logger.debug(this.getClass().getSimpleName() + "oggetto email: " + subject);
		logger.debug(this.getClass().getSimpleName() + "contenuto email: " + content);
		messaggioLogico.setMessaggio(content);
		messaggioLogico.setOggetto(subject);

		try {
			DatatypeFactory df = DatatypeFactory.newInstance();
			GregorianCalendar gc = new GregorianCalendar(2013, 1, 4); // TODO:MINO verifica la data fissa??
			XMLGregorianCalendar xgc = df.newXMLGregorianCalendar(gc);
			messaggioLogico.setTimestamp(xgc);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace(); //TODO: migliorare
		}

		messaggioLogico.setSeverity("1");
		messaggioLogico.setId(new BigInteger("0"));
		TipoMessaggioType tm = new TipoMessaggioType();
		tm.setId(new BigInteger(messageType));
		logger.debug(this.getClass().getSimpleName() + "tipo messaggio email: " + messageType);
		messaggioLogico.setTipoMessaggio(tm);
		try {
			logger.debug(this.getClass().getSimpleName() + "INVIO MESSAGGIO GESTORE CANALI....");
			port.sendMessage(messaggioLogico, utenti);
			logger.debug(this.getClass().getSimpleName() + "MESSAGGIO INVIATO GESTORE CANALI....");
		} catch (Exception e) {
			logger.error(" fallito invio del messaggio al GESTORE CANALI !!!");
			throw e;
		}

	}

	public void testSendMessage() {
    	try {
			setUp();
		}catch (Exception e) {
			e.printStackTrace();
		}
    
    		
    		UtenteType utente = new UtenteType();
    		utente.setIdUtente("U0000000000000000018-00000000000000000018-OP");

    		List<UtenteType> utenti = new ArrayList<UtenteType>();
    		utenti.add(utente);
    		
    		MessaggioLogicoType messaggioLogico = new MessaggioLogicoType();
    		messaggioLogico.setMessaggio("Avviso pendenza in scadenza");
    		messaggioLogico.setMittente("iris@regione.it");
    		messaggioLogico.setOggetto("[IRIS] prova spedizione avviso pendenza in scadenza");
    	
    		try {
    			DatatypeFactory df = DatatypeFactory.newInstance();
    			GregorianCalendar gc = new GregorianCalendar(2013, 1, 4);
    			XMLGregorianCalendar xgc = df.newXMLGregorianCalendar(gc);
    			messaggioLogico.setTimestamp(xgc);
    		} catch (DatatypeConfigurationException e) {
    			e.printStackTrace();
    		}
    		
    		messaggioLogico.setSeverity("1");
    		messaggioLogico.setId(new BigInteger("0"));
    		TipoMessaggioType tm = new TipoMessaggioType();
    		tm.setId(new BigInteger("2"));
    		messaggioLogico.setTipoMessaggio(tm);
    	
    		port.sendMessage(messaggioLogico, utenti);
    		
    
		
	}

}
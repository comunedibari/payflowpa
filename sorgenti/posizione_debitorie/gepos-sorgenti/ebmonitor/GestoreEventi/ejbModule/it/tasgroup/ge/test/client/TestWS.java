package it.tasgroup.ge.test.client;



import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.iris.comunication.ws.impl.ComunicationPortType;
import it.tasgroup.iris.comunication.ws.impl.ComunicazioneServiceImplService;
import it.tasgroup.iris.comunication.ws.impl.MessaggioLogicoType;
import it.tasgroup.iris.comunication.ws.impl.ParametroCanaleType;
import it.tasgroup.iris.comunication.ws.impl.TipoCanaleType;
import it.tasgroup.iris.comunication.ws.impl.TipoMessaggioType;
import it.tasgroup.iris.comunication.ws.impl.UtenteCanaleType;
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





public final class TestWS {
	
	private QName qname;
	private URL url;	
    private ComunicationPortType port;
    
    
//	    private static final QName SERVICE_NAME 
//	        = new QName("http://server.hw.demo/", "HelloWorld");
//	    private static final QName PORT_NAME 
//	        = new QName("http://server.hw.demo/", "HelloWorldPort");


	    private TestWS() {
	    } 

	    public static void main(String args[]) throws Exception {
	    	/*
	    	
	        Service service = Service.create(ComunicazioneServiceImplService.SERVICE);
	        // Endpoint Address
	        String endpointAddress = "http://localhost:8080/comunicationws/services/sendMessage";

	        // If web service deployed on Tomcat deployment, endpoint should be changed to:
	        // String endpointAddress = "http://localhost:8080/java_first_jaxws/services/hello_world";

	        // Add a port to the Service
	        service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);
	        ComunicationPortType hw = service.getPort(ComunicationPortType.class);
	       */
	    	new TestWS().testSendMessage();
	   
	    }

//	    private void initialiseConn (){
//	    	try {
//				//			ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("gateway-ws-client.properties");
//	
//				String urlStr = "http://localhost:8080/comunicationws/services?wsdl";//props.getProperty("iris.gateway.services.url");				
//				String nspuri = "http://impl.ws.comunication.iris.tasgroup.it/";//props.getProperty("iris.gateway.services.qname.namespace");
//				String localPart = "ComunicazioneServiceImplService";//props.getProperty("iris.gateway.services.qname.localpart");
//	
//				url = new URL(urlStr);
//				qname = new QName(nspuri, localPart);
//	
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
//	    }
	    
	   
	    
	
		public void setUp() throws Exception {
			//logger.debug(this.getClass().getSimpleName() + "inizializzazione dati connessione web services gestore canali...." );
			try {
				String urlStr = "http://10.1.128.153:8280/comunicationws/services";				
				String nspuri = "http://impl.ws.comunication.iris.tasgroup.it/";
				String localPart = "ComunicazioneServiceImplService";

				url = new URL(urlStr);
				qname = new QName(nspuri, localPart);
				
				ComunicazioneServiceImplService serv = new ComunicazioneServiceImplService(url, qname);
				port = serv.getComunicazioneServiceImplPort();

			} catch(Exception e) {
				e.printStackTrace();
				//logger.error(" Fallito il recupero delle informazioni di connessione ai ws di GESTORE CANALI !!!");
				throw new Exception("Fallito il recupero delle informazioni di connessione ai ws di GESTORE CANALI !!!");
				
			}
		}
		public void testGetCanaliComunicazioneUtente() {
			try {
				setUp();
			}catch (Exception e) {
				e.printStackTrace();
			}
			ComunicazioneServiceImplService serv = new ComunicazioneServiceImplService(url, qname);
			ComunicationPortType port = serv.getComunicazioneServiceImplPort();
			UtenteType utente = new UtenteType();
			utente.setIdUtente("123");
			utente.setIsAnonymous(Boolean.TRUE);
			List<UtenteCanaleType> res = port.getCanaliComunicazione(utente);
			
			System.out.println("*** testGetCanaliComunicazioneUtente ***");
			System.out.println("utente: [" + utente + "]");
			System.out.println("con utente " + utente.getIdUtente() + ", ");
			for (Iterator<UtenteCanaleType> iterator = res.iterator(); iterator.hasNext();) {
				UtenteCanaleType tipoCanaleResponseType = iterator.next();
				System.out.println("canale " + tipoCanaleResponseType.getCanale().getDenominazione());
			}
			
			res = port.getCanaliComunicazione(null);
			System.out.println("");
			System.out.println("senza utente, ");
			for (Iterator<UtenteCanaleType> iterator = res.iterator(); iterator.hasNext();) {
				UtenteCanaleType tipoCanaleResponseType = iterator.next();
				System.out.println(tipoCanaleResponseType.getCanale().getDenominazione());
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
	    
	    public void sendMessage(ArrayList<String> to, ArrayList<String> cc, String subject,String content, String messageType)throws Exception {
			//logger.debug(this.getClass().getSimpleName() + "chiamata sendMessage gestore canali...." );
			if (url ==null && qname==null && port ==null){
				setUp(); 
			}
			
			UtenteType utente = null;
			List<UtenteType> utenti = new ArrayList<UtenteType>();
			/*
			if (to !=null){
				Iterator<String> iterDest = to.iterator();
				logger.debug(this.getClass().getSimpleName() + "utenti destinatari email: " );
				
				while (iterDest.hasNext()){
					utente = new UtenteType();
					utente.setIdUtente(iterDest.next());
					logger.debug(this.getClass().getSimpleName() + "utenti destinatari email: " + utente.getIdUtente() );
					utenti.add(utente);
				}
			}*/
			utente = new UtenteType();
			utente.setIdUtente("U0000000000000000018-00000000000000000018-OP");
			utente.setIsAnonymous(Boolean.FALSE);
			utenti.add(utente);
			
			MessaggioLogicoType messaggioLogico = new MessaggioLogicoType();
			String mittenteEmail = IrisProperties.getProperty("GESTORE_EVENTI_MITTENTE_MAIL");
			if (mittenteEmail !=null)
				messaggioLogico.setMittente(mittenteEmail);
			else
				messaggioLogico.setMittente("iris@regione.it");
			//logger.debug(this.getClass().getSimpleName() + "oggetto email: " + subject);
			//logger.debug(this.getClass().getSimpleName() + "contenuto email: " + content );
			messaggioLogico.setMessaggio(content);
			messaggioLogico.setOggetto(subject);
		
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
			tm.setId(new BigInteger(messageType));
			//logger.debug(this.getClass().getSimpleName() + "tipo messaggio email: " + messageType );
			messaggioLogico.setTipoMessaggio(tm);
			try {
				port.sendMessage(messaggioLogico, utenti);
			}catch (Exception e){
				//logger.error(" Non è stato possibile inviare il messaggio al GESTORE CANALI !!!");
				throw e;
			}
			
		}
	    public void testSubscribeCanali() {
			System.out.println("*** testSubscribeCanali ***");
			try {
				setUp();
			}catch (Exception e) {
				e.printStackTrace();
			}
			UtenteType subscriber = new UtenteType();
			subscriber.setIdUtente("AB71360D3DABF0B40000-AB71363A3DABF0B40001-OP");
			subscriber.setIsAnonymous(Boolean.FALSE);
//			List<TipoCanaleType> tipoCanaleResponse = Arrays.asList(TipoCanaleType.values());
			ParametroCanaleType parametroCanale = new ParametroCanaleType();
			parametroCanale.setParametro("utente@domain.com");
			
			port.subscribeCanali(subscriber, TipoCanaleType.E_MAIL, parametroCanale);
			
			
//			System.out.println("subscriber: [" + subscriber + "], tipoCanaleResponse: [" + StringUtils.join(tipoCanaleResponse, ",") + "], parametroCanale: [" + parametroCanale.getParametro() + "]");
			System.out.println("subscribeCanali eseguita con successo");
			
		}
	    /*
	    public void testWsClientTest() {
			ComunicazioneServiceImplService serv = new ComunicazioneServiceImplService(url, qname);
			ComunicationPortType port = serv.getComunicazioneServiceImplPort();
			UtenteType utente = new UtenteType();
			utente.setIdUtente("123");
			List<UtenteCanaleType> res = port.getCanaliComunicazione(utente);
			
			for (Iterator<UtenteCanaleType> iterator = res.iterator(); iterator.hasNext();) {
				UtenteCanaleType canaleResponseType = iterator.next();
				System.out.println(canaleResponseType);
			}
		}*/
}

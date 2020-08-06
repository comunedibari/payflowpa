package it.tasgroup.idp.cart.core.utils;


import it.tasgroup.idp.cart.core.dto.CredenzialiPdDDTO;
import it.tasgroup.idp.cart.core.exception.IrisException;
import it.tasgroup.idp.cart.core.costanti.AuthCostanti;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.soap.SOAPMessage;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;


/**
 * Gestisce l'invocazione della Porta Delegata.
 * @author Lorenzo Nardi (nardi@link.it)
 * @version $Id: PDInvoker.java 358 2013-05-22 15:32:32Z nardi $
 *
 */
public class PDInvoker {
	/**
	 * Costruttore privato.
	 */
	private PDInvoker() { }
	
	/**
	 * Invia un messaggio alla Porta Delegata.
	 * @param url Indirizzo della porta delegata
	 * @param soapRequest Messaggio da inviare
	 * @param credenziali Autenticazione alla PD
	 * @param soapAction Valore dell'header SOAPAction
	 * @param Identificativo del messaggio ricevuto
	 * @param log Logger
	 * @return Connessione per la gestione di eventuali errori
	 * @throws IrisException 
	 */
	public static HttpURLConnection send(URL url, SOAPMessage soapRequest, CredenzialiPdDDTO credenziali, String soapAction, String idMsg, Log log) throws IrisException {
		return send(url, soapRequest, credenziali, soapAction, idMsg, null, log);
	}
	
	/**
	 * Invia un messaggio alla Porta Delegata.
	 * @param url Indirizzo della porta delegata
	 * @param soapRequest Messaggio da inviare
	 * @param credenziali Autenticazione alla PD
	 * @param soapAction Valore dell'header SOAPAction
	 * @param servizioApplicativoDestinatario nome spcoop del servizio applicativo a cui e' destinato il messaggio
	 * @param Identificativo del messaggio ricevuto
	 * @param log Logger
	 * @return Connessione per la gestione di eventuali errori
	 * @throws IrisException 
	 */
	
	public static HttpURLConnection send(URL url, SOAPMessage soapRequest, CredenzialiPdDDTO credenziali, String soapAction, String idMsg, String servizioApplicativoDestinatario, Log log) throws IrisException {

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try{
        	soapRequest.writeTo(bout);
        } catch (Exception e) {
        	throw new IrisException("CON103","Impossibile serializzare il messaggio SOAP verso la Porta Delegata.", e);
        }
        byte[] b = bout.toByteArray();
        
        return send(url, b, credenziali, soapAction, idMsg, servizioApplicativoDestinatario, log);
	}

	public static HttpURLConnection send(URL url, String request, CredenzialiPdDDTO credenziali, String soapAction, String idMsg, Log log) throws IrisException {
		 return send(url, request.getBytes(), credenziali, soapAction, idMsg.toString(), null, log);
	}
	
	public static HttpURLConnection send(URL url, byte[] soapRequest, CredenzialiPdDDTO credenziali, String soapAction, String idMsg, String servizioApplicativoDestinatario, Log log) throws IrisException {
		if (url.getPath().contains("SPC" + AuthCostanti.TESTSUITE)) {
			if (log != null) {
				log.info("[" + idMsg + "] Messaggio di Testsuite. Invocazione Porta Delegata [" + url + "] inibita. ");
			}
			try{
				HttpURLConnection httpConn = (HttpURLConnection) (new URL("http://localhost:8080/cart/PD?wsdl")).openConnection();
				httpConn.setRequestMethod("GET");
				httpConn.connect();
				return  httpConn;
			} catch(Exception e) {
				throw new IrisException("CON101","Errore nell'invocazione dell'invocazione della PD: http://localhost:8080/cart/PD?wsdl", e);
			}
		}
		if (log != null) {
			log.debug("[" + idMsg + "] Inoltro del messaggio in corso [" + url + "]");
		}
		URLConnection connection = null;
		try { 
			connection = url.openConnection();
		} catch(Exception e) {
			throw new IrisException("CON102","Impossibile creare la connessione alla Porta Delegata: " + url, e);
		}
        HttpURLConnection httpConn = (HttpURLConnection) connection;
        byte[] b = soapRequest;
        // Authentication
        if (credenziali != null) {
            String authorization = credenziali.getUsername() + ":" + credenziali.getPassword();
            authorization = "Basic " + new String(Base64.encodeBase64(authorization.getBytes()));
            httpConn.setRequestProperty("authorization", authorization);
            if (log != null) { log.debug("[" + idMsg + "] HTTP Authorization [" + authorization + "]"); }
        }
        // Set the appropriate HTTP parameters.
        httpConn.setRequestProperty("X-CART_PdA", "interna");
        httpConn.setRequestProperty("X-CART_TipoIntegrazione", "v1");
        httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
        if (log != null) { log.debug("[" + idMsg + "] HTTP Content-Length [" + String.valueOf(b.length) + "]"); }
        httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        if (log != null) { log.debug("[" + idMsg + "] HTTP Content-Type [text/xml; charset=utf-8]"); }
        httpConn.setRequestProperty("SOAPAction", soapAction);
        if (log != null) { log.debug("[" + idMsg + "] HTTP SOAPAction [" + soapAction + "]"); }
		if (servizioApplicativoDestinatario != null) {
			httpConn.setRequestProperty("SPCoopServizioApplicativoDestinatario", servizioApplicativoDestinatario);
			if (log != null) { log.debug("[" + idMsg + "] HTTP SPCoopServizioApplicativoDestinatario: [" + servizioApplicativoDestinatario + "]"); }
		}
		try {
			httpConn.setRequestMethod("POST");
		} catch (java.net.ProtocolException e) {
			throw new IrisException("CON104","Errore nell'impostazione del protocollo POST.", e);
		}
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);

		// Everything's set up; send the XML that was read in to b.
		try {
	        OutputStream out = httpConn.getOutputStream();
	        out.write(b);
	        out.flush();
	        out.close();
		} catch (IOException e) {
			throw new IrisException("CON105","Impossibile serializzare i dati nella connessione HTTP.", e);
		}
        return httpConn;
	}
}

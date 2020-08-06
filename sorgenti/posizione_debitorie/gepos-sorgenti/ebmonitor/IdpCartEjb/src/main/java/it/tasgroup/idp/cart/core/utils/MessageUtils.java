package it.tasgroup.idp.cart.core.utils;

import it.tasgroup.idp.cart.core.exception.IrisException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;


/**
 * Utilities per la gestione dei messaggi.
 * @author Lorenzo Nardi (nardi@link.it)
 * @version $Id: MessageUtils.java 358 2013-05-22 15:32:32Z nardi $
 */
public final class MessageUtils {
	/**
	 * Costruttore privato.
	 */
	private MessageUtils() { }
	
	/**
	 * Estrae il contenuto del Body meno l'elemento Wrapper da un messaggio SOAP.
	 * @param is Stream del messaggio SOAP
	 * @param service Nome del servizio (individua il localname del node)
	 * @return XML del contenuto del body.
	 * @throws IOException in caso di errori di lettura dallo stream.
	 * @throws SOAPException in caso il messaggio sia malformato.
	 * @throws TransformerException in caso di errori di trasformazione da oggetti a stringa xml
	 */
	public static String soap2string(SOAPMessage msg) throws IrisException {
		try { 
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			msg.writeTo(baos);
			return baos.toString();
		} catch (Exception e) {
			throw new IrisException("JMS003","Non e' stato possibile serializzare il messaggio soap: " + e,"Fallita pubblicazione sulla coda JMS");
		}
		
	}
	/**
	 * Estrae il contenuto del Body meno l'elemento Wrapper da un messaggio SOAP.
	 * @param is Stream del messaggio SOAP
	 * @param service Nome del servizio (individua il localname del node)
	 * @return XML del contenuto del body.
	 * @throws IOException in caso di errori di lettura dallo stream.
	 * @throws SOAPException in caso il messaggio sia malformato.
	 * @throws TransformerException in caso di errori di trasformazione da oggetti a stringa xml
	 */
	public static String soap2xml(final InputStream is, final String service) throws Exception {
		SOAPMessage msg = soap2saaj(is);
		return saaj2xml(msg, service);
	}
	/**
	 * Estrae il contenuto del Body meno l'elemento Wrapper da un messaggio SOAP.
	 * @param msg SOAPMessage da sbustare
	 * @param service Nome del servizio (individua il localname del node)
	 * @return XML del contenuto del body, tolto l'elemento wrapper.
	 * @throws SOAPException in caso il messaggio sia malformato.
	 * @throws TransformerException in caso di errori di trasformazione da oggetti a stringa xml.
	 */
	public static String saaj2xml(final SOAPMessage msg, final String service) throws IrisException {
		try { 
			SOAPElement node = null;
			node = DOMUtils.getChildElement(msg.getSOAPBody(), service);
			return DOMUtils.nodeToString(node);
		} catch (Exception e) {
			throw new IrisException("JMS003","Non e' stato possibile serializzare il messaggio soap: " + e,"Fallita pubblicazione sulla coda JMS");
		}
	}
	
	/**
	 * Estrae il contenuto del Body meno l'elemento Wrapper da un messaggio SOAP.
	 * @param msg SOAPMessage da sbustare
	 * @param service Nome del servizio (individua il localname del node)
	 * @param namespace del wrapper
	 * @return XML del contenuto del body, tolto l'elemento wrapper.
	 * @throws SOAPException in caso il messaggio sia malformato.
	 * @throws TransformerException in caso di errori di trasformazione da oggetti a stringa xml.
	 */
	public static String saaj2xml(final SOAPMessage msg, final String service, final String namespace) throws SOAPException, TransformerException {
		SOAPElement node = null;
		node = DOMUtils.getChildElement(msg.getSOAPBody(), service, namespace);
		return DOMUtils.nodeToString(node);
	}
	
	/**
	 * Wrappa il documento xml e lo imbusta in un messaggio SOAP.
	 * @param is Stream dei dati del messaggio da imbustare
	 * @param wrapper Nome dell'elemento wrapper
	 * @return SOAPMessage del messaggio imbustato
	 * @throws SOAPException in caso il messaggio sia malformato.
	 * @throws IOException in caso di errori di lettura dallo stream.
	 */
	public static SOAPMessage xml2saaj(final InputStream is, final String wrapper) throws SOAPException, IOException {
		String soap = xml2soap(StreamUtils.convertStreamToString(is), wrapper);
		return soap2saaj(new ByteArrayInputStream(soap.getBytes()));
	}
	/**
	 * Wrappa il documento xml e lo imbusta in un messaggio SOAP.
	 * @param is Stringa dei dati del messaggio da imbustare
	 * @param wrapper Nome dell'elemento wrapper
	 * @return SOAPMessage del messaggio imbustato
	 * @throws SOAPException in caso il messaggio sia malformato.
	 * @throws IOException in caso di errori di lettura dallo stream.
	 */
	public static SOAPMessage xml2saaj(final String is, final String wrapper) throws SOAPException, IOException {
		String soap = xml2soap(is, wrapper);
		return soap2saaj(new ByteArrayInputStream(soap.getBytes()));
	}
	/**
	 * Costruisce il SOAPMessage del messaggio soap.
	 * @param soap Messaggio soap
	 * @return SOAPMessage
	 * @throws SOAPException in caso il messaggio sia malformato.
	 * @throws IOException in caso di errori di lettura dallo stream.
	 */
	public static SOAPMessage soap2saaj(final String soap) throws SOAPException, IOException {
		return soap2saaj(new ByteArrayInputStream(soap.getBytes()));
	}
	/**
	 * Costruisce il SOAPMessage con lo stream di dati inviato.
	 * @param xml Stream dei dati del messaggio soap
	 * @return SOAPMessage
	 * @throws SOAPException in caso il messaggio sia malformato.
	 * @throws IOException in caso di errori di lettura dallo stream.
	 */
	public static SOAPMessage soap2saaj(final InputStream xml) throws SOAPException, IOException {
		MessageFactory mf = MessageFactory.newInstance();
		MimeHeaders mhs = new MimeHeaders();
		mhs.addHeader("Content-Type", "text/xml");
		return mf.createMessage(mhs, xml);
	}
	/**
	 * Rimuove la dichiarazione xml e wrappa il documento con l'elemento indicato.
	 * @param xml Dcoumento xml
	 * @param wrapper Elemento wrapper
	 * @return Documento xml wrappato.
	 */
	public static String xml2soap(final String xml, final String wrapper) {
		 String cleanxml = xml;
		 int match = cleanxml.indexOf("<?xml");
		 if (match != -1) {
			 cleanxml = cleanxml.substring(cleanxml.indexOf(">", match + 1) + 1);
		 }
		 String head = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body>";
		 String wrap = wrapper != null ? ("<iris:" + wrapper + " xmlns:iris=\"http://www.cart.rete.toscana.it/servizi/iris_1_1/proxy\">") : "";
		 String unwrap = wrapper != null ? ("</iris:" + wrapper + ">") : "";
		 String tail = "</soapenv:Body></soapenv:Envelope>";
		 return head + wrap + cleanxml + unwrap + tail;
	 }
}

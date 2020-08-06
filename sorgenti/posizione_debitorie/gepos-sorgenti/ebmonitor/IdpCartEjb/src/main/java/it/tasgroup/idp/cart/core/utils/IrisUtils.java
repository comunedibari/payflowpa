package it.tasgroup.idp.cart.core.utils;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import it.tasgroup.idp.cart.core.exception.IrisException;

/**
 * Classe di utility per l'accesso ai valori dell'header Idp.
 *
 * @author Lorenzo Nardi (nardi@link.it)
 * @version $Id: IrisUtils.java 417 2013-07-26 08:53:14Z nardi $
 */

public final class IrisUtils {

	/** Costruttore. */
	private IrisUtils() { }

	/**
	 * Restituisce il nome della porta delegata da invocare
	 * per il ProxyIRIS per la consegna di un esito.
	 *
	 * @param msg SOAPMessage di esito
	 * @return Nome della porta delegata
	 * @throws SOAPException se il messaggio non e' valido
	 */

	public static String getEsitoPD(String senderId, String receiverId, IdPServizio serviceName) throws Exception {
		String pd = "";

		switch (serviceName) {
		case IdpInformativaPagamento:
			pd += senderId+ "/" +  receiverId  + "/";
			pd += "SPCInvioNotificaPagamento-v3/IdpInformativaPagamento";
			break;
		case IdpAllineamentoPendenzeEnteEsito:
			pd += senderId + "/" + receiverId + "/";
			pd += "SPCComunicazionePosizioniDebitorieEsito-v3/IdpAllineamentoPendenzeEnte.Esito";
			break;
		default: throw new Exception("Errore nella creazioen della URL, Servizio ["+serviceName+"] non gestito.");
		}
		return pd;

	}


	/** 
	 * Costruisce il messaggio di SOAPFalt.
	 *
	 * @param e faultString
	 * @param status codice di errore
	 * @return ServiceName
	 */

	public static String createXMLFault(IrisException e) {
		return "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
				"<soap:Body><soap:Fault>" +
				"<faultcode>soap:Client</faultcode>" +
				"<faultstring>" + e.getFaultString() + "</faultstring>" +
				"<faultactor>ProxyIRIS</faultactor>" +
				"<detail>" +
				"<code>" + e.getCodiceErrore() + "</code>" +
				"<description>" + e.getMessage() + "</description>" +
				"</detail>" +
				"</soap:Fault></soap:Body>" +
				"</soap:Envelope>";
	}
	

	/** Estrae il faultstring da un SOAPFault.
	 *
	 * @param msg Messaggio di fault
	 * @return faultstring
	 */
	public static String getFaultString(SOAPMessage msg) {
		try {
			return DOMUtils.getChildElement(msg.getSOAPBody(), "faultstring").getValue();
		} catch (Exception e) {
			return "Impossibile trovare fault string";
		}
	}



	/** Estrae il faultactor da un SOAPFault.
	 *
	 * @param msg Messaggio di fault
	 * @return faultActor
	 */
	public static String getFaultActor(final String msg) {
		try {
			SOAPMessage m = MessageUtils.soap2saaj(msg);
			return DOMUtils.getChildElement(m.getSOAPBody(), "faultactor").getValue();
		} catch (Exception e) {
			return "Impossibile trovare fault actor";
		}
	}

	/** Estrae il faultcode da un SOAPFault.
	 *
	 * @param msg Messaggio di fault
	 * @return faultCode
	 */
	public static String getFaultCode(final String msg) {
		try {
			SOAPMessage m = MessageUtils.soap2saaj(msg);
			return DOMUtils.getChildElement(m.getSOAPBody(), "faultcode").getValue();
		} catch (Exception e) {
			return "Impossibile trovare fault code";
		}
	}

	/** Estrae il faultcode da un SOAPFault.
	 *
	 * @param msg Messaggio di fault
	 * @return faultCode
	 */
	public static String getFaultCode(SOAPMessage msg) {
		try {
			//                  SOAPMessage m = MessageUtils.soap2saaj(msg);
			return DOMUtils.getChildElement(msg.getSOAPBody(), "faultcode").getValue();
		} catch (Exception e) {
			return "Impossibile trovare fault code";
		}
	}

}

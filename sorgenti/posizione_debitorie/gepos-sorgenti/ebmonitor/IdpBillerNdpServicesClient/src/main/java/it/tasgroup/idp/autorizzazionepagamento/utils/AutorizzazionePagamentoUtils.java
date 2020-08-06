package it.tasgroup.idp.autorizzazionepagamento.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Format;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;

import org.apache.commons.logging.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import it.tasgroup.idp.autorizzazionepagamento.AttivaPagamentoResponseBodyType;
import it.tasgroup.idp.autorizzazionepagamento.exception.AutorizzazionePagamentoException;
import it.tasgroup.idp.autorizzazionepagamento.servlet.AutorizzazionePagamentoVO;


public final class AutorizzazionePagamentoUtils {

	
	private AutorizzazionePagamentoUtils() { }

	private static Log log;
	private static AutorizzazionePagamentoErrorHandler handler ;


	/** 
	 * Costruisce il messaggio di SOAPFalt.
	 *
	 * @param e faultString
	 * @param status codice di errore
	 * @return ServiceName
	 */

	public static String createXMLFault(AutorizzazionePagamentoException e) {
		return "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
				"<soap:Body>"
				+"<ns2:root xmlns:ns2=\"it.tasgroup.idp.autorizzazionepagamento\">"
				+ "<Fault>" +
				"<FaultCode>"+e.getCodiceErrore()+"</FaultCode>" +
				"<FaultString>" + e.getFaultString() + "</FaultString>" +
				"<FaultDescription>" + e.getMessage() + "</FaultDescription>" +
				"</Fault>"
				+"</ns2:root>"
				+ "</soap:Body>" +
				"</soap:Envelope>";
	}
	
	public static String createVerificaResponse(AutorizzazionePagamentoVO datiRisposta) {
		

		return "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" 
				+" <soap:Body>"
				+"<ns2:root xmlns:ns2=\"it.tasgroup.idp.autorizzazionepagamento\">"
				+"<IdentificativoDominio>" + datiRisposta.getIdentificativoDominio() + "</IdentificativoDominio>"
				+ "<IdentificativoUnivocoVersamento>" + datiRisposta.getIdentificativoUnivocoVersamento() + "</IdentificativoUnivocoVersamento>"
				+ "<CodiceContestoPagamento>" + datiRisposta.getCodiceContestoPagamento() + "</CodiceContestoPagamento>"
				+ "<ImportoPagamento>" + datiRisposta.getImportoPagamento() + "</ImportoPagamento>"
				+ "<CausaleVersamento>" + datiRisposta.getCausaleVersamento()+ "</CausaleVersamento>"
				+"</ns2:root>"
				+ "</soap:Body> "
				+ "</soap:Envelope>" ;
	}
	
	public static String createAttivaResponse(AttivaPagamentoResponseBodyType respBody ) {
		return "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" 
				+"<soap:Body>"
				+"<ns2:root xmlns:ns2=\"it.tasgroup.idp.autorizzazionepagamento\">"
				+"<IdentificativoDominio>" + respBody.getIdentificativoDominio() + "</IdentificativoDominio>"
				+ "<IdentificativoUnivocoVersamento>" + respBody.getIdentificativoUnivocoVersamento()+ "</IdentificativoUnivocoVersamento>"
				+ "<CodiceContestoPagamento>" + respBody.getCodiceContestoPagamento() + "</CodiceContestoPagamento>"
				+ "<ImportoPagamento>" + respBody.getImportoPagamento() + "</ImportoPagamento>"
				+ "<CausaleVersamento>" +respBody.getCausaleVersamento()+"</CausaleVersamento>"
				+ "<AnagraficaDebitore>" +respBody.getAnagraficaDebitore()+"</AnagraficaDebitore>"
				+ "<AnnoRiferimento>" +respBody.getAnnoRiferimento()+"</AnnoRiferimento>"
				+ "<DescrizioneMittente>" +respBody.getDescrizioneMittente()+"</DescrizioneMittente>"
				+ "<IdDebito>" +respBody.getIdDebito()+"</IdDebito>"
				+ "<IdMittente>" +respBody.getIdMittente()+"</IdMittente>"
				+ "<IdRiscossore>" +respBody.getIdRiscossore()+"</IdRiscossore>"
				+"<TipoDebito>" +respBody.getTipoDebito()+"</TipoDebito>"
				+"<TipoIdentificativoUnivocoDebitore>" +respBody.getTipoIdentificativoUnivocoDebitore()+"</TipoIdentificativoUnivocoDebitore>"
				+"</ns2:root>"
				+ "</soap:Body> "
				+ "</soap:Envelope>" ;
	}

	/** Estrae il faultstring da un SOAPFault.
	 *
	 * @param msg Messaggio di fault
	 * @return faultstring
	 */
	public static String getFaultString(SOAPMessage msg) {
		try {
			return XmlUtils.getChildElement(msg.getSOAPBody(), "faultstring").getValue();
		} catch (Exception e) {
			return "Impossibile trovare fault string";
		}
	}



	
	/** Estrae il faultcode da un SOAPFault.
	 *
	 * @param msg Messaggio di fault
	 * @return faultCode
	 */
	/*
	public static String getFaultCode(final String msg) {
		try {
			SOAPMessage m = MessageUtils.soap2saaj(msg);
			return XmlUtils.getChildElement(m.getSOAPBody(), "faultcode").getValue();
		} catch (Exception e) {
			return "Impossibile trovare fault code";
		}
	}*/

	/** Estrae il faultcode da un SOAPFault.
	 *
	 * @param msg Messaggio di fault
	 * @return faultCode
	 */
	public static String getFaultCode(SOAPMessage msg) {
		try {
			//                  SOAPMessage m = MessageUtils.soap2saaj(msg);
			return XmlUtils.getChildElement(msg.getSOAPBody(), "faultcode").getValue();
		} catch (Exception e) {
			return "Impossibile trovare fault code";
		}
	}
	
	public static SOAPMessage validate(InputStream xml) throws Exception  {
		try{
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbfactory.newDocumentBuilder();
			builder.setErrorHandler(handler);
			Document doc = builder.parse( xml );

			DOMSource src = new DOMSource(doc);
			MessageFactory mf = MessageFactory.newInstance();
			SOAPMessage soapMsg = mf.createMessage();
			soapMsg.getSOAPPart().setContent(src);
			return soapMsg;
		} catch(Exception e) {
			throw new Exception("Errore di validazione sintattica: " + e.getMessage(), e);
		}
	}
	
	
	
	
	class AutorizzazionePagamentoErrorHandler implements ErrorHandler {
		
		public AutorizzazionePagamentoErrorHandler() {
			
		}
		public void warning(SAXParseException exception) throws SAXException {
			
		}
		public void error(SAXParseException exception) throws SAXException {
			if(exception.getMessage().contains("One of '{\"http://www.cart.rete.toscana.it/servizi/iris_1_1/proxy\":IdpAllineamentoPendenzeEnte}' is expected.")){
				warning(exception);
				return;
			}
			if(exception.getMessage().contains("One of '{\"http://www.cart.rete.toscana.it/servizi/iris_1_1/proxy\":IdpInformativaPagamento.Esito}' is expected.")){
				warning(exception);
				return;
			}
			throw exception;
		}
		public void fatalError(SAXParseException exception) throws SAXException {
			throw exception;
		}
	}

}

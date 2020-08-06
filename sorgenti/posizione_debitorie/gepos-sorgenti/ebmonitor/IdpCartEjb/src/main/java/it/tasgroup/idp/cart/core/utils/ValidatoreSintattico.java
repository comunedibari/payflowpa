package it.tasgroup.idp.cart.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.logging.Log;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Gestisce la validazione sintattica del messaggio rispetto agli schemi xml.
 *
 * @author Lorenzo Nardi (nardi@link.it)
 * @version $Id: ValidatoreSintattico.java 408 2013-07-12 14:35:04Z nardi $
 */
public class ValidatoreSintattico {
	/** Nome dello schema xml Allineamento Pendenze. */
	private static final String IDPAP = "IdP.AllineamentoPendenze.xsd";
	public static final String IDPAPSOAP = "IdP.AllineamentoPendenze.SOAP.xsd";
	public static final String IDPAPOTFSOAP = "IdP.AllineamentoPendenzeOTF.SOAP.xsd";
	public static final String IDPAPSOAP_ESITO = "IdP.AllineamentoPendenze.ESITO.SOAP.xsd";
	public static final String IDPAPOTFSOAP_ESITO = "IdP.AllineamentoPendenzeOTF.ESITO.SOAP.xsd";
	/** Nome dello schema xml Informativa Pagamento Pendenze. */
	private static final String IDPIPP = "IdP.InformativaPagamento.xsd";
	public static final String IDPIPPSOAP = "IdP.InformativaPagamento.SOAP.xsd";
	public static final String IDPIPPOTFSOAP = "IdP.InformativaPagamentoOTF.SOAP.xsd";
	public static final String IDPIPPSOAP_ESITO = "IdP.InformativaPagamento.ESITO.SOAP.xsd";
	public static final String IDPIPPOTFSOAP_ESITO = "IdP.InformativaPagamentoOTF.ESITO.SOAP.xsd";
	/** Nome dello schema xml Rendicontazione Enti. */
	private static final String IDPRE = "IdP.RendicontazioneEnti.xsd";
	public static final String IDPRESOAP = "IdP.RendicontazioneEnti.SOAP.xsd";
	public static final String IDPRESOAP_ESITO = "IdP.RendicontazioneEnti.ESITO.SOAP.xsd";
	/** Nome dello schema xml Configurazione Ente. */
	private static final String IDPCE = "IdP.ConfigurazioneEnte.xsd";
	public static final String IDPCESOAP = "IdP.ConfigurazioneEnte.SOAP.xsd";
	public static final String IDPCESOAP_ESITO = "IdP.ConfigurazioneEnte.ESITO.SOAP.xsd";
	/** Nome dello schema xml Esito. */
	private static final String IDPESITO = "IdP.Esito.xsd";
	private static final String IDPESITO_VERIFICA = "IdP.Esito.Verifica.xsd";

	private static final String IDPPROXY = "IdP.Proxy.xsd";
	/** Nome dello schema xml. */
	private static final String XML = "xml.xsd";
	/** Nome dello schema xml Header. */
	private static final String IDPHEADER = "IdP.Header.xsd";
	/** Nome dello schema xml Include. */
	private static final String IDPINCLUDE = "IdP.Include.xsd";
	/** Nome dello schema xml XMLSchema. */
	private static final String XMLSCHEMA = "XMLSchema.xsd";
	/** Document Builder. */
	private DocumentBuilderFactory dbfactory;
	private MessageFactory messageFactory;
	/** Document Builder. */
	private ErrorHandler eh;
	/**
	 * Costruttore del validatore sintattico.
	 * @param schema Filename dello Schema XML per la validazione.
	 */
	public ValidatoreSintattico(String schemaSoap, Log log) throws Exception {
		eh = new IrisErrorHandler(log);

		List<StreamSource> ss = new ArrayList<StreamSource>();
		try {
			ss.add(new StreamSource(getClass().getClassLoader().getResourceAsStream(XML)));
			ss.add(new StreamSource(getClass().getClassLoader().getResourceAsStream(XMLSCHEMA)));
			ss.add(new StreamSource(getClass().getClassLoader().getResourceAsStream(IDPINCLUDE)));
			ss.add(new StreamSource(getClass().getClassLoader().getResourceAsStream(IDPHEADER)));
			ss.add(new StreamSource(getClass().getClassLoader().getResourceAsStream(IDPAP)));
			ss.add(new StreamSource(getClass().getClassLoader().getResourceAsStream(IDPIPP)));
			ss.add(new StreamSource(getClass().getClassLoader().getResourceAsStream(IDPRE)));
			ss.add(new StreamSource(getClass().getClassLoader().getResourceAsStream(IDPCE)));
			ss.add(new StreamSource(getClass().getClassLoader().getResourceAsStream(IDPESITO)));
			ss.add(new StreamSource(getClass().getClassLoader().getResourceAsStream(IDPESITO_VERIFICA)));
			ss.add(new StreamSource(getClass().getClassLoader().getResourceAsStream(IDPPROXY)));
			ss.add(new StreamSource(getClass().getClassLoader().getResourceAsStream(schemaSoap)));
		} catch (Throwable e) {
			throw new Exception("Errore durante il caricamento degli schemi di validazione", e);
		}
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		try {
			Schema idpSchema = factory.newSchema(ss.toArray(new StreamSource[0]));
			dbfactory = DocumentBuilderFactory.newInstance();
			dbfactory.setIgnoringElementContentWhitespace(true);
			dbfactory.setNamespaceAware(true);
			dbfactory.setSchema(idpSchema);

			messageFactory = MessageFactory.newInstance();
		} catch (SAXException e) {
			throw new Exception("Errore durante l'instanziazione del validatore sintattico", e);
		} catch (SOAPException e) {
			throw new Exception("Errore durante l'instanziazione del builder", e);
		} 
	}
	/**
	 * Esegue la validazione dell'xml.
	 * @param xml Documento xml da validare
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws Exception 
	 */
	public SOAPMessage validate(InputStream xml) throws Exception  {
		try{
			DocumentBuilder builder = dbfactory.newDocumentBuilder();
			builder.setErrorHandler(eh);
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

	public SOAPMessage build(InputStream xml) throws Exception  {
		try{
			MimeHeaders mhs = new MimeHeaders();
			mhs.addHeader("Content-Type", "text/xml");
			SOAPMessage msg = messageFactory.createMessage(mhs, xml);
			msg.getSOAPBody();
			return msg;
		} catch(Exception e) {
			throw new Exception("Errore nella costruzione del messaggio SOAP: " + e.getMessage(), e);
		}
	}

	class IrisErrorHandler implements ErrorHandler {
		private Log log;
		public IrisErrorHandler(Log log) {
			this.log = log;
		}
		public void warning(SAXParseException exception) throws SAXException {
			log.info("Warning durante la fase di validazione: " + exception.getMessage());
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


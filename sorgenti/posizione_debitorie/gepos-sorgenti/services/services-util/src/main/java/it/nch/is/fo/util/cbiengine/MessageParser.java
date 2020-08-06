package it.nch.is.fo.util.cbiengine;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.xerces.dom.NodeImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MessageParser {

	public static void main(String[] args) {
		testErrors();
		//testInfos();
	}
	
	public static void testErrors() {
		MessageParser mp = new MessageParser();
		//String xml = "<messages><error><type>ERR_CTRL_CBI</type><id>ERR_CAMPO_VALORI_NON_AMMESSI</id><params><param>RECORD_TYPE=10</param><param>RECORD_NUMBER=2</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=-2</param><param>FIELD_NAME=Causale</param><param>FIELD_VALUE=99999</param><param>FIELD_EXPECTED_VALUE=null</param><param>FIELD_RANGE=48000</param><param>LENGTH_RANGE=5</param><param>FIELD_DESCRIPTION=Causale</param><param>FIELD_START_POS=29</param><param>FIELD_END_POS=33</param></params></error><error><type>ERR_CTRL_CBI</type><id>ERR_TR10_ABIBENEFICIARIO_NONABI</id><params><param>RECORD_TYPE=10</param><param>RECORD_NUMBER=2</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=0</param><param>FIELD_NAME=ABIBeneficiario</param><param>FIELD_VALUE=ABCDE</param><param>FIELD_EXPECTED_VALUE=</param><param>FIELD_RANGE=</param><param>LENGTH_RANGE=</param><param>FIELD_DESCRIPTION=</param><param>FIELD_START_POS=70</param><param>FIELD_END_POS=74</param></params></error></messages>";
		//String xml = "Questo non è XML...";
		//String xml = "<messages><error><type>ERR_CTRL_CBI</type><id>ERR_CAMPO_VALORI_NON_AMMESSI</id><params><param>RECORD_TYPE=20</param><param>RECORD_NUMBER=3</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=-2</param><param>FIELD_NAME=TipoRecord</param><param>FIELD_VALUE=30</param><param>FIELD_EXPECTED_VALUE=null</param><param>FIELD_RANGE=20</param><param>LENGTH_RANGE=2</param><param>FIELD_DESCRIPTION=Tipo Record</param><param>FIELD_START_POS=2</param><param>FIELD_END_POS=3</param></params></error><error><type>ERR_CTRL_CBI</type><id>ERR_CAMPO_LUNGHEZZA_ERRATA</id><params><param>RECORD_TYPE=20</param><param>RECORD_NUMBER=3</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=-4</param><param>FIELD_NAME=DenominazioneOrdinante</param><param>FIELD_VALUE=DELTA SAS</param><param>FIELD_EXPECTED_VALUE=null</param><param>FIELD_RANGE=</param><param>LENGTH_RANGE=30</param><param>FIELD_DESCRIPTION=Denominazione Ordinante</param><param>FIELD_START_POS=11</param><param>FIELD_END_POS=40</param></params></error><error><type>ERR_CTRL_CBI</type><id>ERR_CAMPO_VALORI_NON_AMMESSI</id><params><param>RECORD_TYPE=30</param><param>RECORD_NUMBER=4</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=-2</param><param>FIELD_NAME=TipoRecord</param><param>FIELD_VALUE=40</param><param>FIELD_EXPECTED_VALUE=null</param><param>FIELD_RANGE=30</param><param>LENGTH_RANGE=2</param><param>FIELD_DESCRIPTION=Tipo Record</param><param>FIELD_START_POS=2</param><param>FIELD_END_POS=3</param></params></error><error><type>ERR_CTRL_CBI</type><id>ERR_CAMPO_LUNGHEZZA_ERRATA</id><params><param>RECORD_TYPE=30</param><param>RECORD_NUMBER=4</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=-4</param><param>FIELD_NAME=Denominazione2Beneficiario</param><param>FIELD_VALUE=31100TREVISO		</param><param>FIELD_EXPECTED_VALUE=null</param><param>FIELD_RANGE=</param><param>LENGTH_RANGE=30</param><param>FIELD_DESCRIPTION=Denominazione Beneficiario (secondo segmento)</param><param>FIELD_START_POS=41</param><param>FIELD_END_POS=70</param></params></error><error><type>ERR_CTRL_CBI</type><id>ERR_CAMPO_CONVERSIONE_ERRATA</id><params><param>RECORD_TYPE=40</param><param>RECORD_NUMBER=5</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=-6</param><param>FIELD_NAME=CAPBeneficiario</param><param>FIELD_VALUE=DITOR</param><param>FIELD_EXPECTED_VALUE=null</param><param>FIELD_RANGE=</param><param>LENGTH_RANGE=5</param><param>FIELD_DESCRIPTION=CAP Beneficiario</param><param>FIELD_START_POS=41</param><param>FIELD_END_POS=45</param></params></error><error><type>ERR_CTRL_CBI</type><id>ERR_CAMPO_VALORI_NON_AMMESSI</id><params><param>RECORD_TYPE=40</param><param>RECORD_NUMBER=5</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=-2</param><param>FIELD_NAME=TipoRecord</param><param>FIELD_VALUE=50</param><param>FIELD_EXPECTED_VALUE=null</param><param>FIELD_RANGE=40</param><param>LENGTH_RANGE=2</param><param>FIELD_DESCRIPTION=Tipo Record</param><param>FIELD_START_POS=2</param><param>FIELD_END_POS=3</param></params></error><error><type>ERR_CTRL_CBI</type><id>ERR_CAMPO_LUNGHEZZA_ERRATA</id><params><param>RECORD_TYPE=40</param><param>RECORD_NUMBER=5</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=-4</param><param>FIELD_NAME=BancaSportelloBeneficiario</param><param>FIELD_VALUE=25-07-03</param><param>FIELD_EXPECTED_VALUE=null</param><param>FIELD_RANGE=</param><param>LENGTH_RANGE=50</param><param>FIELD_DESCRIPTION=Banca/Sportello Beneficiario</param><param>FIELD_START_POS=71</param><param>FIELD_END_POS=120</param></params></error></messages>";
		//String xml = "<messages><error><type>ERR_CTRL_CBI</type><id>ERR_TR12_ABIBANCAALLINEAMENTO_NONVALIDO</id><params><param>RECORD_TYPE=12</param><param>RECORD_NUMBER=2</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=502</param><param>FIELD_NAME=ABIBancaAllineamento</param><param>FIELD_VALUE=02002</param><param>FIELD_EXPECTED_VALUE=07601</param><param>FIELD_RANGE=</param><param>LENGTH_RANGE=</param><param>FIELD_DESCRIPTION=</param><param>FIELD_START_POS=48</param><param>FIELD_END_POS=52</param></params></error></messages>";
		//String xml = "<messages><error><type>BUSINESS</type><id>ERR_SPEDIZIONE_FLUSSO_AUTH</id><params><param>MESSAGE=JJ-5655E</param></params></error></messages>";
		//String xml = "<messages><error><type>DB</type><id>ERR_UNABLE_TO_UNZIP_OR_DECODE</id><params><param>message=java.lang.NoClassDefFoundError: org/apache/commons/codec/binary/Base64</param></params></error></messages>";
		//String xml = "<messages><error><type>6</type><id>ParserErrorId</id><params><param>RECORD_TYPE=50</param><param>RECORD_NUMBER=11</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=</param><param>FIELD_NAME=totaleImportoCreditoCompensato</param><param>FIELD_VALUE=0000000000081AA</param><param>FIELD_EXPECTED_VALUE='no provided expectation'</param><param>FIELD_RANGE=</param><param>LENGTH_RANGE=16</param><param>FIELD_DESCRIPTION=</param><param>FIELD_START_POS=781</param><param>FIELD_END_POS=931</param><param>NOMESUPPORTO=NOME SUPPORTO</param></params></error></messages>";
		//String xml = "<messages><error><type>6</type><id>ParserErrorId</id><params><param>RECORD_TYPE=40</param><param>RECORD_NUMBER=3</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=</param><param>FIELD_NAME=numeroFabbricati</param><param>FIELD_VALUE=A01</param><param>FIELD_EXPECTED_VALUE='no provided expectation'</param><param>FIELD_RANGE=</param><param>LENGTH_RANGE=4</param><param>FIELD_DESCRIPTION=</param><param>FIELD_START_POS=641</param><param>FIELD_END_POS=671</param><param>NOMESUPPORTO=NOME SUPPORTO</param></params></error><error><type>6</type><id>ParserErrorId</id><params><param>RECORD_TYPE=40</param><param>RECORD_NUMBER=4</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=</param><param>FIELD_NAME=numeroFabbricati</param><param>FIELD_VALUE=B01</param><param>FIELD_EXPECTED_VALUE='no provided expectation'</param><param>FIELD_RANGE=</param><param>LENGTH_RANGE=4</param><param>FIELD_DESCRIPTION=</param><param>FIELD_START_POS=641</param><param>FIELD_END_POS=671</param><param>NOMESUPPORTO=NOME SUPPORTO</param></params></error><error><type>6</type><id>ParserErrorId</id><params><param>RECORD_TYPE=40</param><param>RECORD_NUMBER=5</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=</param><param>FIELD_NAME=numeroFabbricati</param><param>FIELD_VALUE=C01</param><param>FIELD_EXPECTED_VALUE='no provided expectation'</param><param>FIELD_RANGE=</param><param>LENGTH_RANGE=4</param><param>FIELD_DESCRIPTION=</param><param>FIELD_START_POS=641</param><param>FIELD_END_POS=671</param><param>NOMESUPPORTO=NOME SUPPORTO</param></params></error><error><type>6</type><id>ParserErrorId</id><params><param>RECORD_TYPE=40</param><param>RECORD_NUMBER=6</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=</param><param>FIELD_NAME=numeroFabbricati</param><param>FIELD_VALUE=D01</param><param>FIELD_EXPECTED_VALUE='no provided expectation'</param><param>FIELD_RANGE=</param><param>LENGTH_RANGE=4</param><param>FIELD_DESCRIPTION=</param><param>FIELD_START_POS=641</param><param>FIELD_END_POS=671</param><param>NOMESUPPORTO=NOME SUPPORTO</param></params></error><error><type>6</type><id>ParserErrorId</id><params><param>RECORD_TYPE=40</param><param>RECORD_NUMBER=7</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=</param><param>FIELD_NAME=numeroFabbricati</param><param>FIELD_VALUE=E01</param><param>FIELD_EXPECTED_VALUE='no provided expectation'</param><param>FIELD_RANGE=</param><param>LENGTH_RANGE=4</param><param>FIELD_DESCRIPTION=</param><param>FIELD_START_POS=641</param><param>FIELD_END_POS=671</param><param>NOMESUPPORTO=NOME SUPPORTO</param></params></error><error><type>6</type><id>ParserErrorId</id><params><param>RECORD_TYPE=40</param><param>RECORD_NUMBER=8</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=</param><param>FIELD_NAME=numeroFabbricati</param><param>FIELD_VALUE=F01</param><param>FIELD_EXPECTED_VALUE='no provided expectation'</param><param>FIELD_RANGE=</param><param>LENGTH_RANGE=4</param><param>FIELD_DESCRIPTION=</param><param>FIELD_START_POS=641</param><param>FIELD_END_POS=671</param><param>NOMESUPPORTO=NOME SUPPORTO</param></params></error></messages>";
		//String xml = "<messages><error><type>-1</type><id>ERR_CBI_CODA_MITTENTE_INCONSISTENTE</id><params><param>CBI_ERROR_CODE=</param><param>FIELD_NAME=Mittente</param><param>FIELD_VALUE=07601</param><param>FIELD_EXPECTED_VALUE=07602</param><param>FIELD_RANGE=</param><param>NOMESUPPORTO=NOME SUPPORTO</param></params></error></messages>";
		//String xml = "<messages><error><type>-1</type><id>ERR_F4_NUMERO_RECORD_4007</id><params><param>CBI_ERROR_CODE=</param><param>FIELD_NAME=RecordPagamentoDelega4007</param><param>FIELD_VALUE=6</param><param>FIELD_EXPECTED_VALUE=MAX. 4</param><param>FIELD_RANGE=</param><param>NOMESUPPORTO=NOME SUPPORTO</param></params></error></messages>";
		String xml = "<messages><error><type>6</type><id>ParserErrorId</id><params><param>RECORD_TYPE=40</param><param>RECORD_NUMBER=5</param><param>OPERATION_NUMBER=1</param><param>CBI_ERROR_CODE=</param><param>FIELD_NAME=totaleImportoCreditoCompensato</param><param>FIELD_VALUE=0000000000000AA</param><param>FIELD_EXPECTED_VALUE='no provided expectation'</param><param>FIELD_RANGE=</param><param>LENGTH_RANGE=16</param><param>FIELD_DESCRIPTION=</param><param>FIELD_START_POS=311</param><param>FIELD_END_POS=461</param><param>NOMESUPPORTO=NOME SUPPORTO</param></params></error></messages>";
		byte[] bytes = xml.getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ArrayList errors = mp.parseErrors(bais);
		Iterator it = errors.iterator();
		int errIdx = 0;
		while (it.hasNext()) {
			errIdx++;
			Object cbiError = it.next();
			//System.out.println("Errore # " + errIdx + ": " + cbiError);
		}
		try {
			InputStream propInputStream = new FileInputStream("C:/javasource/nch/iris/services-webapp/src/main/resources/cbiengine.properties");
			Properties cbiProperties = new Properties();
			cbiProperties.load(propInputStream);
			String formatted = mp.format(errors, cbiProperties);
			// Questo System.out si può lasciare, è chiamato solo dal main!
			System.out.println(formatted);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void testInfos() {
		MessageParser mp = new MessageParser();
		String xml = "<messages><info><type>BUSINESS</type><id>IMPORTAZIONE_ESITO_OK</id><params><param>DISPOSIZIONE_ERRATE=1</param><param>DISPOSIZIONE_INSERITE=1</param></params></info></messages>";
		byte[] bytes = xml.getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ArrayList infos = mp.parseInfos(bais);
		Iterator it = infos.iterator();
		while (it.hasNext()) {
			Object cbiError = it.next();
			System.out.println(cbiError);
		}
		try {
			InputStream propInputStream = new FileInputStream("C:/javasource/nch/iris/services-webapp/src/main/resources/cbiengine.properties");
			Properties cbiProperties = new Properties();
			cbiProperties.load(propInputStream);
			String formatted = mp.formatInfos(infos, cbiProperties);
			System.out.println(formatted);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public ArrayList parseErrors(String xml) {
		if (xml != null) {
			byte[] bytes = xml.getBytes();
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			return parse(bais, "error");
		}
		return null;
	}

	public ArrayList parseInfos(String xml) {
		if (xml != null) {
			byte[] bytes = xml.getBytes();
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			return parse(bais, "info");
		}
		return null;
	}

	public ArrayList parseDOM(InputStream is, String type) {
		ArrayList cbiEngineErrors = new ArrayList();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(is);
			NodeList nl = document.getElementsByTagName(type);
			int numNodes = nl.getLength();
			//System.out.println("[MessageParser::parse] Num. of nodes = " + numNodes);
			for (int i = 0; i < numNodes; i++) {
				CBIEngineError cee = new CBIEngineError();
				Node node = nl.item(i);
				NodeList childNodes = node.getChildNodes();
				int numChildNodes = childNodes.getLength();
				//System.out.println("[MessageParser::parse] Num. of child nodes = " + numChildNodes);
				for (int j = 0; j < numChildNodes; j++) {
					NodeImpl childNode = (NodeImpl) childNodes.item(j);
					String childNodeName = childNode.getNodeName();
					String childTextContent = childNode.getTextContent();
					//System.out.println("[MessageParser::parse] Child node # = " + j + ", " + childNodeName + " = " + childTextContent);
					if (childNodeName.equals("type")) {
						cee.type = childTextContent;
					} else if (childNodeName.equals("id")) {
						cee.id = childTextContent;
					} else if (childNodeName.equals("params")) {
						NodeList grandChildNodes = childNode.getChildNodes();
						int numGrandChildNodes = grandChildNodes.getLength();
						//System.out.println("[MessageParser::parse] Num. of grand child nodes = " + numGrandChildNodes);
						for (int k = 0; k < numGrandChildNodes; k++) {
							NodeImpl grandChildNode = (NodeImpl) grandChildNodes.item(k);
							String grandChildNodeName = grandChildNode.getNodeName();
							String grandChildTextContent = grandChildNode.getTextContent();
							//System.out.println("[MessageParser::parse] Grand child node # = " + k + ", " + grandChildNodeName + " = " + grandChildTextContent);
							CBIEngineErrorParam cbieep = new CBIEngineErrorParam(grandChildTextContent);
							cee.addErrorParam(cbieep);
						}
					}
				}
				cbiEngineErrors.add(cee);
			}
		} catch (ParserConfigurationException e) {
			//System.out.println("[MessageParser::parse] ParserConfigurationException : " + e.getMessage());
		} catch (SAXException e) {
			//System.out.println("[MessageParser::parse] SAXException : " + e.getMessage());
		} catch (IOException e) {
			//System.out.println("[MessageParser::parse] IOException : " + e.getMessage());
		}
		return cbiEngineErrors;
	}
	
	public ArrayList parseInfos(InputStream is) {
		return parse(is, "info");
	}
	
	public ArrayList parseErrors(InputStream is) {
		return parse(is, "error");
	}

	public String format(ArrayList errors, Properties properties) {
		return format(errors, properties, false);
	}

	public String format(ArrayList errors, Properties properties, boolean is4HTML) {
		StringBuffer sb = new StringBuffer();
		if (errors != null && !errors.isEmpty()) {
			//System.out.println("[MessageParser::format] Number of errors: " + errors.size());
			ArrayList grouped = groupByDisposizione(errors);
			//System.out.println("[MessageParser::format] Number of errors (grouped): " + grouped.size());
			Iterator it = grouped.iterator();
			while (it.hasNext()) {
				CBIEngineError cee = (CBIEngineError) it.next();
				//System.out.println("[MessageParser::format] Grouped, dispo = " + cee.getDisposizione());
				String formatted = cee.format(properties, is4HTML, true);
				sb.append(formatted);
				if (is4HTML) {
					if (it.hasNext()) {
						sb.append("<BR>");
					}
				} else {
					sb.append("\r\n");
				}
			}
		}

		return sb.toString();
	}

	public String formatInfos(ArrayList infos, Properties properties) {
		StringBuffer sb = new StringBuffer();
		Iterator it = infos.iterator();
		while (it.hasNext()) {
			CBIEngineError cee = (CBIEngineError) it.next();
			//System.out.println("[MessageParser::formatInfos] Id = " + cee.id);
			String formatted = cee.formatInfo(properties);
			sb.append(formatted);
			sb.append(" ");
		}

		return sb.toString();

	}
	
	private ArrayList groupByDisposizione(ArrayList errors) {
		ArrayList grouped = new ArrayList();
		Iterator it = errors.iterator();
		String lastDispo = null;
		CBIEngineError lastAppended = null;
		while (it.hasNext()) {
			CBIEngineError cee = (CBIEngineError) it.next();
			//System.out.println("[MessageParser::groupByDisposizione] Not grouped, dispo = " + cee.getDisposizione());
			String currentDispo = cee.getDisposizione();
			if (lastDispo == null || !lastDispo.equalsIgnoreCase(currentDispo)) {
				grouped.add(cee);
				lastDispo = currentDispo;
			} else {
				//CBIEngineError lastAppended = (CBIEngineError) grouped.get(grouped.size() - 1);
				lastAppended.nextSibling = cee;
				cee.prevSibling = lastAppended;
			}			
			lastAppended = cee;
		}
		return grouped;
	}

	public ArrayList parse(InputStream is, String type) {
		CBIEngineErrorHandler handler = new CBIEngineErrorHandler(type);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(is, handler);
		} catch (Throwable t) {
		//	t.printStackTrace();
			//	We expect that in some cases input is not XML
		}
		ArrayList errors = handler.errors;
		/*
		Iterator it = errors.iterator();
		System.out.println("---------------------------------------------");
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println("---------------------------------------------");
		*/
		return errors;
	}
	
}

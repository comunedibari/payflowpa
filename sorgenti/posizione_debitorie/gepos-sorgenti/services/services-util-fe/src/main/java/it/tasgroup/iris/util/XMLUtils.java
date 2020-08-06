/**
 * 
 */
package it.tasgroup.iris.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * @author pazzik
 *
 */
public class XMLUtils {
	
	private static final Logger LOGGER = LogManager.getLogger(XMLUtils.class);

	
	public static String parseNodeToString(Node node){
		
        String str=null;
        
        try {

			Source source = new DOMSource(node);
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.transform(source, result);
			str=stringWriter.getBuffer().toString();

        } catch (TransformerConfigurationException e) {
        	LOGGER.error("TransformerConfigurationException while parsing node to string: " + e.getMessage());
        	LOGGER.debug("Stack Trace: ", e);
        } catch (TransformerException e) {
        	LOGGER.error("TransformerException while parsing node to string: " + e.getMessage());
        	LOGGER.debug("Stack Trace: ", e);
        }
        
        return str;
   }

	public static void validate(String xml, String schemaUrl) throws ParserConfigurationException, IOException, MalformedURLException, SAXException {

		DocumentBuilder parser = null;
		Document document = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		parser = dbf.newDocumentBuilder();

		byte bytes[] = xml.getBytes();
		document = parser.parse(new ByteArrayInputStream(bytes));
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		Schema schema = null;
		//schema = factory.newSchema(new URL(schemaUrl));
		schema = factory.newSchema(Thread.currentThread().getContextClassLoader().getResource(schemaUrl));
		javax.xml.validation.Validator validator = schema.newValidator();

		validator.validate(new DOMSource(document));
	}

}

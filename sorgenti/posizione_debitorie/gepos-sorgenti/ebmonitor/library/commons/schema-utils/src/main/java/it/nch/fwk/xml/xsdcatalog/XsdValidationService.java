/**
 * 26/ago/2009
 */
package it.nch.fwk.xml.xsdcatalog;

import it.nch.eb.cbi2.validator.ErrorSetHandler;
import it.nch.eb.cbi2.validator.ValidationHandlerFactory;
import it.nch.fwk.xml.validation.ValidationService;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * @author gdefacci
 */
public class XsdValidationService implements Serializable, ValidationService {
	
	private static final long	serialVersionUID	= -3707750022842359166L;

	private transient Map/*<String, Schema>*/ 		schemaCache = new HashMap();
	private transient SchemaFactory 				schemaFactory;
	private transient LSResourceResolver 			resourceResolver;
	private final String 							xsdNamespaceURI;
	private final ReaderFactoryMap 					catalog;
	                     
	private final ValidationHandlerFactory validationHandlerFactory;
	
	public XsdValidationService(
			String xsdUri,
			ValidationHandlerFactory svhf,
			ReaderFactoryMap catalog) {
		
		this.xsdNamespaceURI = xsdUri;
		this.validationHandlerFactory = svhf;
		this.catalog = catalog;
	}

	public Set/*<QualifiedError>*/validate(Reader rdr) {
		try {
			ErrorSetHandler vh = validationHandlerFactory.create();
			saxParse(new InputSource(rdr), vh);
			return vh.getErrorSet();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void saxParse(InputSource source, DefaultHandler validationHandler) throws Exception {
		
		Schema schema = getSchema(xsdNamespaceURI);
		xsdSaxParse(source, validationHandler, schema);

	}

	private void xsdSaxParse(InputSource source, DefaultHandler validationHandler, Schema schema) {
		try {
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			parserFactory.setNamespaceAware(true);
			parserFactory.setSchema(schema);

			SAXParser saxParser = parserFactory.newSAXParser();

			saxParser.parse(source, validationHandler);
		} catch (Exception se) {
			throw new RuntimeException(se);
		}
	}

	private synchronized Schema getSchema(String namespaceURI) throws Exception {
		try {
			if (schemaCache == null) {
				schemaCache = new HashMap();
			}
			if (!schemaCache.containsKey(namespaceURI)) {
				feedCache(namespaceURI);
			}
			return (Schema) schemaCache.get(namespaceURI);
		} catch (Exception e) {
			throw new RuntimeException("error retrieving schema for " + namespaceURI, e);
		}

	}

	private void feedCache(String namespaceURI) throws SAXException, IOException, MalformedURLException {
		LSInput input = getResourceResolver().resolveResource(XMLConstants.DEFAULT_NS_PREFIX, namespaceURI, null, null,
				null);
		InputStream schemaIs;
		if (input.getByteStream() == null) {
			URL schemaUrl = new URL(input.getSystemId());
			schemaIs = schemaUrl.openStream() ;
		} else {
			schemaIs = input.getByteStream();
		}
		StreamSource schemaStreamSource = new StreamSource(schemaIs);
//		if (schemaStreamSource == null) {
//			throw new IllegalStateException("cant resove the xsd with id " + namespaceURI);
//		}
		Schema schema = getSchemaFactory().newSchema(schemaStreamSource);
		schemaCache.put(namespaceURI, schema);
	}

	protected synchronized LSResourceResolver getResourceResolver() {
		if (resourceResolver==null) {
			resourceResolver = new XsdLSResourceResolver(catalog);
		}
		return resourceResolver;
	}

	private synchronized SchemaFactory getSchemaFactory() {
		if (schemaFactory==null) {
			this.schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			this.schemaFactory.setResourceResolver(getResourceResolver());
		}
		return schemaFactory;
	}
	
}

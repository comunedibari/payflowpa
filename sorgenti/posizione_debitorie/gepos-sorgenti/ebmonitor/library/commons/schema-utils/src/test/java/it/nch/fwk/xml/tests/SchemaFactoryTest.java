/**
 * 25/gen/2010
 */
package it.nch.fwk.xml.tests;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import junit.framework.TestCase;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/**
 * @author gdefacci
 */
public class SchemaFactoryTest extends TestCase {
	
	static final File baseFolder = new File("D:/java/projects/flowmanager-svn/flowmanager/flowmanager-xsd-configuration/src/cbi2/conf/v3/");
	
	static final File commonXsdFolder = new File(baseFolder, "common/xsd");
	static final File advinfXsdFolder = new File(baseFolder, "advinf/xsd");
	static final File pmtreqXsdFolder = new File(baseFolder, "pmtreq/xsd");
	
	String file(File folder, String name) {
		return new File(folder, name).getAbsolutePath();
	}
	
	static final Map xsdsMap = new HashMap();
	static {
		xsdsMap.put("", "");
	}
	
	static class FakeLSResourceResolver implements LSResourceResolver {

		public LSInput resolveResource(String type, String namespaceURI, 
				String publicId, String systemId,
				String baseURI) {
			System.out.println("type:" + type );
			System.out.println("namespaceURI:" + namespaceURI );
			System.out.println("publicId:" + publicId );
			System.out.println("systemId:" + systemId );
			System.out.println("baseURI:" + baseURI );
			return null;
		}
		
	}
	private static final String	W3C_XML_SCHEMA_NS_URI	= "http://www.w3.org/2001/XMLSchema";
	
	static {
		System.setProperty("jaxp.debug", "true");
		System.setProperty("javax.xml.validation.SchemaFactory:http://www.w3.org/2001/XMLSchema", 
							"org.apache.xerces.jaxp.validation.XMLSchemaFactory");
	}
	
	public void testSchemaFactory() throws Exception {
		SAXParserFactory fact = SAXParserFactory.newInstance();
		fact.setNamespaceAware(true);
		fact.setValidating(true);
		
		
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		factory.setResourceResolver(new FakeLSResourceResolver());
		File f = new File("D:/java/projects/flowmanager-svn/flowmanager/flowmanager-xsd-configuration/src/cbi2/conf/v3/pmtreq/xsd/CBIPaymentRequestMsg.00.03.06.xsd"); 
		Schema schema = factory.newSchema(f);
		System.out.println(schema);
	}

}

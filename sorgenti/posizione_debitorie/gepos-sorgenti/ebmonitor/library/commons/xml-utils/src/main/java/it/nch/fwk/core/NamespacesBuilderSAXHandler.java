/**
 * Created on 10/ago/07
 */
package it.nch.fwk.core;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * collect namespaces declared in a xml document
 * @author gdefacci
 */
public class NamespacesBuilderSAXHandler extends DefaultHandler {

	public static final XSDRegistry VOID_XSD_REGISTRY = new XSDRegistry() {
		public String getLocation(String schemaId) {
			return null;
		}
	};

	private static final String	NS_SEPARATOR	= NamespacesInfos.NS_SEPARATOR;
	protected XSDRegistry registry;
	protected NamespacesInfos namespaces =  new NamespacesInfos();
	
	public NamespacesBuilderSAXHandler() {
		this(VOID_XSD_REGISTRY);
	}
	
	public NamespacesBuilderSAXHandler(XSDRegistry registry) {
		this.registry = registry;
	}

	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		processAttributes(attributes);
	}

	static String getAttributePrefix(String qname) {
		String[] parts = qname.split(NS_SEPARATOR);
		if (parts.length>0) {
			return parts[0];
		} 
		return null;
	}
	
	static String getAttributeLocalPrefix(String qname) {
		String[] parts = qname.split(NS_SEPARATOR);
		if (parts.length>1) {
			return parts[1];
		} 
		return "";
	}
	
	private void processAttributes(Attributes attributes) {
		for (int i=0; i< attributes.getLength(); i++) {
//			String location = registry.getClasspathLocation(schemaId);
			String qname = attributes.getQName(i);
			String xmlnsString = getAttributePrefix(qname);
			if ((xmlnsString!=null) && ("xmlns".equals(xmlnsString))) {
				String localPrefix = getAttributeLocalPrefix(qname);
				String location = registry.getLocation(localPrefix);
				String uri = attributes.getValue(i);
				this.namespaces.add(localPrefix, uri, location);
			}
		}
	}

	public NamespacesInfos getNamespaces() {
		return namespaces;
	}
	
}

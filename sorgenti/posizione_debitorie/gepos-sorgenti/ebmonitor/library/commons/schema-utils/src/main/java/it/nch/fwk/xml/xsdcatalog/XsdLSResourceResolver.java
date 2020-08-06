/**
 * 27/gen/2010
 */
package it.nch.fwk.xml.xsdcatalog;

import java.io.InputStream;
import java.io.Reader;

import org.apache.xerces.dom.DOMInputImpl;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/**
 * @author gdefacci
 */
public class XsdLSResourceResolver implements LSResourceResolver{
	
	private static final String W3_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	
	private final ReaderFactoryMap readerFactoryMap;
	public XsdLSResourceResolver(ReaderFactoryMap readerFactoryMap) {
		this.readerFactoryMap = readerFactoryMap;
	}

	public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
		if (type!=null && type.length() >0 && !type.equals(W3_SCHEMA)) {
			throw new UnsupportedOperationException("xsd specific resover, cant resolve resource type " + type);
		}
		InputStream rdr = readerFactoryMap.get(namespaceURI);
		if (rdr==null) return null;
		else {
			return new DOMInputImpl(publicId, systemId, baseURI, rdr, null);
		}
	}

}

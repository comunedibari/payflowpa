package it.nch.fwk.xml.util;

import java.io.Reader;

import javax.xml.namespace.QName;

/**
 * FIXME : rename in XmlHelper or the like.
 */
public interface IXmlParser {

	QName getRootNode(Reader document);
	DocumentInfo getDocumentInfo(Reader document);
	
}

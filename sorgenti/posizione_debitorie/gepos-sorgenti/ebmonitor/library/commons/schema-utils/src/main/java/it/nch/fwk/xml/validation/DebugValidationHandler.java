package it.nch.fwk.xml.validation;

import java.io.IOException;


import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * TODO: never used
 * @author FG@2007
 */
public class DebugValidationHandler implements EntityResolver, DTDHandler, ContentHandler, ErrorHandler {

	public InputSource resolveEntity(String arg0, String arg1)
			throws SAXException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public void notationDecl(String arg0, String arg1, String arg2)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void unparsedEntityDecl(String arg0, String arg1, String arg2,
			String arg3) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void endPrefixMapping(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void processingInstruction(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void setDocumentLocator(Locator arg0) {
		// TODO Auto-generated method stub
		
	}

	public void skippedEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startElement(String arg0, String arg1, String arg2,
			Attributes arg3) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void error(SAXParseException arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void fatalError(SAXParseException arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void warning(SAXParseException arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}


	
}

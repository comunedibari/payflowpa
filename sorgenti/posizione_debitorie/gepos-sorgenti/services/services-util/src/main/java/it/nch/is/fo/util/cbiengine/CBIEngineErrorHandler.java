package it.nch.is.fo.util.cbiengine;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CBIEngineErrorHandler extends DefaultHandler{

	public ArrayList errors = new ArrayList();

	CBIEngineError currentError;
	CBIEngineErrorParam currentParam;
	String type; // "error" or "info"
	StringBuffer sb;
	
	

	public CBIEngineErrorHandler(String type) {
		this.type = type;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		if (qName.equals("type")) {
			currentError = new CBIEngineError();
		}
		sb = new StringBuffer();
		if (attributes != null) {
			int num = attributes.getLength();
			for (int i = 0; i < num; i++) {
				String attrQName = attributes.getQName(i);
				String attrValue = attributes.getValue(i);
				
			}
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		
		if (qName.equals(type)) {
			errors.add(currentError);
		} else if (qName.equals("type")) {
			currentError.type = sb.toString();
		} else if (qName.equals("id")) {
			currentError.id = sb.toString();
		} else if (qName.equals("param")) {
			currentParam = new CBIEngineErrorParam(sb.toString());
			currentError.addErrorParam(currentParam);
		}
		sb = new StringBuffer();
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		String str = new String(ch, start, length);
		sb.append(str);
	}

}

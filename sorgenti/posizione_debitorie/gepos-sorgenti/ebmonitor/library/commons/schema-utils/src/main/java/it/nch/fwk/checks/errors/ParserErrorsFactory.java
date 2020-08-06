/**
 * Created on 04/dic/08
 */
package it.nch.fwk.checks.errors;

import it.nch.eb.common.utils.loaders.ResourceLoaders;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;

import org.xml.sax.SAXParseException;


/**
 * @author gdefacci
 */
public class ParserErrorsFactory implements Serializable {
	
	private static final long	serialVersionUID	= 1072984913669137445L;
	private transient PropertyResourceBundle i18nXsdErrors;
	private final String	classpathLocation;
	
	public ParserErrorsFactory(final String classpathLocation) {
		this.classpathLocation = classpathLocation;
	}

	
	protected synchronized PropertyResourceBundle getI18nXsdErrors() {
		if (i18nXsdErrors==null) {
			try {
				i18nXsdErrors  = new PropertyResourceBundle(ResourceLoaders.CLASSPATH.loadInputStream(classpathLocation));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}	
		}
		return i18nXsdErrors;
	}

	public String getErrorMessage(SAXParseException saxParseException) {
		String errorMessage = saxParseException.getMessage();
		String res = parseErrorMessage(errorMessage);
		if (res==null) return errorMessage;
		else return res;
	}
	
	public String getI18NErrorMessage(SAXParseException saxParseException) {
		String errorMessage = saxParseException.getMessage();
		return parseErrorMessage(errorMessage);
	}
	
	private String parseErrorMessage(String errorMessage) {
		int spiltpoint = errorMessage.indexOf(':');
		String errorcode = errorMessage.substring(0, spiltpoint);
		errorMessage = errorMessage.substring(spiltpoint + 1, errorMessage.length());

		try {
			String propStr = (String) getI18nXsdErrors().handleGetObject(errorcode);

			if (propStr == null || propStr.length() == 0) {
				return errorMessage; // if no match return the error message
			}

			StringTokenizer stringTokenizer = new StringTokenizer(errorMessage, "''");
			String[] values = new String[stringTokenizer.countTokens() / 2 + 1];
			int i = 0;
			// Extract data within the quotes into the array
			while (stringTokenizer.hasMoreElements()) {
				stringTokenizer.nextElement();
				if (stringTokenizer.hasMoreElements()) {
					values[i] = (String) stringTokenizer.nextElement();
					i++;
				}
			}

			String resMsg = MessageFormat.format(propStr, values);
			return resMsg; 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ParserErrorInfo create(SAXParseException e, String severity) {
		String emsg = e.getMessage();
		String i18nEmsg = parseErrorMessage(emsg);
		String messagecode = emsg.substring(0,emsg.indexOf(":")).trim();
		if (i18nEmsg==null) i18nEmsg = emsg;
		return new ParserErrorInfo(messagecode, i18nEmsg, emsg, e.getColumnNumber(), e.getLineNumber(), severity);
	}

	
	
}

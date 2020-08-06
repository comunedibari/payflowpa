package it.nch.fwk.xml.validation.exception;

import javax.xml.namespace.QName;

import org.xml.sax.SAXParseException;

/**
 * @author gedfr
 * @version $Header$
 * 
 */
public class ValidationException extends Exception {

	private static final long	serialVersionUID	= 7002578982826866540L;

	private QName element;
	
	private String errorType;

	private String errorSeverity;

	private SAXParseException parserException;

	private String errorCode;

	/**
	 * @param errorSeverity
	 * @param parserException
	 */
	public ValidationException(String errorSeverity, SAXParseException parserException) {
		this(errorSeverity, parserException, null);
	}

	/**
	 * @param errorSeverity
	 * @param parserException
	 */
	public ValidationException(String errorSeverity, SAXParseException parserException, QName element) {
		super(parserException);
		this.errorSeverity = errorSeverity;
		this.parserException = parserException;
		this.element = element;
		
		init();
	}

	private void init() {
		int pos = -1;
		if(parserException != null && (pos = parserException.getMessage().indexOf(":")) > -1) {
			this.errorCode = parserException.getMessage().substring(0, pos).trim();
		}
	}
	
	/**
	 * @return the cause
	 */
	public SAXParseException getParserException() {
		return parserException;
	}

	/**
	 * @return the columnNumber
	 */
	public int getColumnNumber() {
		return parserException != null? parserException.getColumnNumber() : -1;
	}

	/**
	 * @return the element
	 */
	public QName getElement() {
		return element;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return parserException != null ? parserException.getMessage() : "";
	}

	/**
	 * @return the errorSeverity
	 */
	public String getErrorSeverity() {
		return errorSeverity;
	}

	/**
	 * @return the errorType
	 */
	public String getErrorType() {
		return errorType;
	}

	/**
	 * @return the lineNumber
	 */
	public int getLineNumber() {
		return parserException != null ? parserException.getLineNumber() : -1;
	}

}

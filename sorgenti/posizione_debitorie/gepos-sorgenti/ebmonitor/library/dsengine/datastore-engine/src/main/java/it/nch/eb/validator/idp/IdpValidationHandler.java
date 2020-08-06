package it.nch.eb.validator.idp;

import it.nch.eb.cbi2.validator.ErrorSetHandler;
import it.nch.eb.common.utils.bindings.BindingManagerFactory;
import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.fwk.checks.errors.ErrorInfo;
import it.nch.fwk.checks.errors.ParserErrorInfo;
import it.nch.fwk.checks.errors.ParserErrorsFactory;
import it.nch.fwk.checks.errors.QualifiedError;
import it.nch.fwk.xml.validation.SchemaElement;
import it.tasgroup.dse.errorInfo.ExtendedErrorInfoImpl;

import java.util.Set;
import java.util.TreeSet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class IdpValidationHandler extends ErrorSetHandler { 
	
	private Set errorsBag = new TreeSet();
	
	private SchemaElement currentElement;
	private String currentPath;
	
	private ParserErrorsFactory xsdErrorsFactory;

	private IBindingManager	bindings = BindingManagerFactory.instance.createBindingManager();
	
	public IdpValidationHandler(
			ParserErrorsFactory parserErrorsFactory) {
		this.xsdErrorsFactory = parserErrorsFactory;
	}
	
	public IBindingManager getBindingManager() {
		return bindings;
	}
	
	public QualifiedError collectError(QualifiedError err) {
		this.errorsBag.add(err);
		return err;
	}

	public final void startDocument() throws SAXException{
	}
	 
	public final void endDocument() throws SAXException{
	}
	
	public final void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		currentElement = new SchemaElement(uri, localName, qName, attributes, currentPath, true);
		currentPath = currentElement.getXPath();
		
		
	}

	public final void endElement(String uri, String localName, String qName) throws SAXException {
		currentElement = new SchemaElement(uri, localName, qName, null, currentPath, false);
		currentPath = currentElement.getXPath();		
	}

	public final void characters (char ch[], int start, int length) {	
		 currentElement.setValue(String.valueOf(ch, start, length).trim());
		 

    }	

	public void error(SAXParseException e) throws SAXException {
		collectError(e, ErrorInfo.Severity.ERROR);
	}

	public void fatalError(SAXParseException e) throws SAXException {
		collectError(e, ErrorInfo.Severity.FATAL);
	}
	
	public String createMessage(SAXParseException e) {
		return this.xsdErrorsFactory.getErrorMessage(e);	
	}

	
	public ErrorInfo collectError(SAXParseException e, String severity) {
		ErrorInfo errorInfo = createErroInfo(e, severity, currentElement);
		QualifiedError err = collectError(errorInfo);
//		downcast work since BaseErrorCollector.collectError(QualifiedError) returns the same instance given as paaremeter
		if (err instanceof ErrorInfo) {
			return (ErrorInfo) err;
		} else {
			throw new IllegalStateException("[should never happen]expeceting a error info but got a qulified error");
		}
	}
	
	private ErrorInfo createErroInfo(ParserErrorInfo parserErrorInfo, SchemaElement currentElement2) {
		return new ExtendedErrorInfoImpl(parserErrorInfo, currentElement2);
	}
	
	public ErrorInfo createErroInfo(SAXParseException e, String severity, SchemaElement currentElement2) {
		ParserErrorInfo parserErrorInfo = xsdErrorsFactory.create(e, severity);
		return createErroInfo(parserErrorInfo, currentElement2);
	}
	
	public Set getErrorSet() {
		return errorsBag;
	}


	public String getCurrentPath() {
		return currentPath;
	}

	public SchemaElement getCurrentElement() {
		return currentElement;
	}
	
}

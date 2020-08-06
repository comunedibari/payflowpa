package it.nch.fwk.fo.xml;

public class XMLHelperNodeNotFoundException extends XMLHelperException {
	XMLHelperNodeNotFoundException(String xpathExpression) {
		this(xpathExpression, null);
	}

	XMLHelperNodeNotFoundException(String xpathExpression, Throwable rootCause) {
		super("node not found: " + xpathExpression, rootCause);
	}
}

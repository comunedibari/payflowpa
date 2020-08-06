package it.nch.fwk.fo.xml;

public class XMLHelperParseValueException extends XMLHelperException {
	XMLHelperParseValueException(String item, String type) {
		super("error converting " + item + ": " + type + " conversion failed");
	}

	XMLHelperParseValueException(String item, String type, Throwable rootCause) {
		super("error converting " + item + ": " + type + " conversion failed",
				rootCause);
	}
}

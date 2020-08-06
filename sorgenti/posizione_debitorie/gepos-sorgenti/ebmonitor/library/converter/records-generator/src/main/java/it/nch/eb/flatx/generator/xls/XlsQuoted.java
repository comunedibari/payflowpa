/**
 * Created on 20/mar/08
 */
package it.nch.eb.flatx.generator.xls;


/**
 * @author gdefacci
 */
public class XlsQuoted implements XlsExpression {
	
	private final String value;

	public XlsQuoted(String value) {
		this.value = stripQuotes(value);
	}

	private static String stripQuotes(String value) {
		return value.substring(1, value.length()-1);
	}
	
	private XlsQuoted(boolean valueIsQuoted, String value) {	
		if (valueIsQuoted) this.value = stripQuotes(value);
		else this.value = value;
	}
	
	public static final XlsQuoted createRaw(String value) {
		return new XlsQuoted(false, value);
	}

	public String getValue() {
		return value;
	}

	public XlsExpression copy() {
		return createRaw(value);
	}
	

}

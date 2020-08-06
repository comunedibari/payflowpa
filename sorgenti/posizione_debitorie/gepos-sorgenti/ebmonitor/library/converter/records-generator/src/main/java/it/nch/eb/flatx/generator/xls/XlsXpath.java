/**
 * Created on 20/mar/08
 */
package it.nch.eb.flatx.generator.xls;


/**
 * @author gdefacci
 */
public class XlsXpath implements XlsExpression {
	
	private final String value;

	public XlsXpath(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public XlsExpression copy() {
		return new XlsXpath(value);
	}

}

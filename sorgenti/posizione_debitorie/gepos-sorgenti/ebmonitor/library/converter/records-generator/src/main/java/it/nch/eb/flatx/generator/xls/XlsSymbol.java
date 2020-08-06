/**
 * Created on 20/mar/08
 */
package it.nch.eb.flatx.generator.xls;


/**
 * @author gdefacci
 */
public class XlsSymbol implements XlsExpression {
	
	private final String name;

	public XlsSymbol(String value) {
		this.name = value;
	}

	public String getValue() {
		return name;
	}
	
	public XlsExpression copy() {
		return new XlsSymbol(name);
	}
}

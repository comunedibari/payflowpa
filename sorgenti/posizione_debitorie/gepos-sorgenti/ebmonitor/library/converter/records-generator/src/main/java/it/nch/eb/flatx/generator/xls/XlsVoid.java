/**
 * 03/dic/2009
 */
package it.nch.eb.flatx.generator.xls;

/**
 * @author gdefacci
 */
public class XlsVoid implements XlsExpression {

	private final int length;

	public XlsVoid(int len2) {
		this.length = len2;
	}

	public XlsExpression copy() {
		return new XlsVoid(length);
	}
	
	public int getSize() {
		return length;
	}

	public String getValue() {
		return null;
	}

}

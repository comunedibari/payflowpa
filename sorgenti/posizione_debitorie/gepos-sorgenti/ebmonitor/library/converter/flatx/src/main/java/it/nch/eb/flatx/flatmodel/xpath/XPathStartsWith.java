/**
 * Created on 30/mar/2009
 */
package it.nch.eb.flatx.flatmodel.xpath;


/**
 * @author gdefacci
 */
public class XPathStartsWith implements XPathPositionPredicate {
	
	private final XPathPosition prefix;

	public XPathStartsWith(XPathPosition prefix) {
		this.prefix = prefix;
	}

	public boolean match(BaseXPathPosition pos) {
		return pos.startsWith(prefix);
	}

}

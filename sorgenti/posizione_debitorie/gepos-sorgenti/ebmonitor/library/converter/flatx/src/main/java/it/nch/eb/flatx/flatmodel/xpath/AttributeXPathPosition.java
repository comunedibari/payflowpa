/**
 * Created on 25/ago/2008
 */
package it.nch.eb.flatx.flatmodel.xpath;



/**
 * @author gdefacci
 */
public class AttributeXPathPosition extends BaseXPathPosition {
	
	private static final long	serialVersionUID	= -3636545468686699780L;

	public AttributeXPathPosition(XPathPosition parent, String qname) {
		this(parent, new AttributeXPathSegment(qname));
	}
	
	public AttributeXPathPosition(XPathPosition parent, String prefix, String name) {
		this(parent, new AttributeXPathSegment(prefix, name));
	}
	
	public AttributeXPathPosition(XPathPosition parent, AttributeXPathSegment seg) {
		super(parent, seg);
	}
	
	public synchronized BaseXPathPosition getUnindexed() {
		XPathPosition par = (XPathPosition) this.getParent().getUnindexed();
		return new AttributeXPathPosition(par, (AttributeXPathSegment)getLastSegment()) ;
	}

}

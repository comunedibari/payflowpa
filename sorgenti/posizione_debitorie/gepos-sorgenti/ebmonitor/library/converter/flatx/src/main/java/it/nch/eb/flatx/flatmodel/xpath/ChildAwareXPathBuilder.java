/**
 * Created on 04/ago/2008
 */
package it.nch.eb.flatx.flatmodel.xpath;

import it.nch.eb.flatx.flatmodel.sax.IllegalXmlException;



/**
 * @author gdefacci
 */
public class ChildAwareXPathBuilder implements IXPathBuilder {
	
	private XPathPositionChildrenAware actual;
	
	public synchronized IXPathBuilder pathSegment(String qualifiedName) {
		String[] parts = XPathUtils.sharedInstance.segmentPair(qualifiedName);
		return pathSegment(parts[0], parts[1]);
	}

	public synchronized IXPathBuilder pathSegment(String prefix, String name) {
		actual = new XPathPositionChildrenAware(actual, prefix, name);
		return this;
	}
	
	public synchronized IXPathBuilder pathSegmentEnd(String qualifiedName) {
		String[] parts = XPathUtils.sharedInstance.segmentPair(qualifiedName);
		return pathSegmentEnd(parts[0], parts[1]);
	}
	
	public synchronized IXPathBuilder pathSegmentEnd(String prefix, String name) {
		if (!actual.getLastSegment().match(prefix, name)) {
			throw new IllegalXmlException("not well formed " + prefix + ":" + name 
					+ " isd closing no tag\nexpecting element end for " + actual.getPrefix() + ":" + actual.getName());
		}
		this.actual = this.actual.getChildrenAwareParent();
		return this;
	}
	
	public String getXPath() {
		return actual.getXpath();	
	}
	
	public XPathPosition getPosition() {
		return actual;
	}

}

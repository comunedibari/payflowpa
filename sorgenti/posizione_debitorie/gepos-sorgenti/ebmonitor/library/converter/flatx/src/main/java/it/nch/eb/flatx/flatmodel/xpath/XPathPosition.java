/**
 * Created on 04/ago/2008
 */
package it.nch.eb.flatx.flatmodel.xpath;

import java.util.Iterator;
import java.util.List;





/**
 * @author gdefacci
 */
public class XPathPosition extends BaseXPathPosition implements Comparable {
	
	private static final long	serialVersionUID	= 2979745127179595560L;

	public XPathPosition(String prefix, String name) {
		super(null, prefix, name);
	}
	
	public XPathPosition(XPathPosition parent, String prefix, String name) {
		super(parent, new XPathSegment(prefix, name));
	}
	
	protected XPathPosition(XPathPosition parent, String prefix, String name, int idx) {
		super(parent, new XPathSegment(prefix, name, idx));
	}
	
	public XPathPosition(XPathPosition parent, XPathSegment seg) {
		super(parent, seg);
	}
	
	public XPathPosition element(String name) {
		return element(null, name);
	}
	
	public XPathPosition element(String prefix, String name) {
		return new XPathPosition(this, prefix, name);
	}
	
	public synchronized BaseXPathPosition getUnindexed() {
		if (getParentUnindexed()==null) {
			if (this.parent==null) {
				setParentUnindexed( new XPathPosition(null, getPrefix(), getName(), 1) );
			} else {
				XPathPosition par = (XPathPosition) this.getParent().getUnindexed();
				setParentUnindexed( new XPathPosition(par, getPrefix(), getName(), 1) );
			}
		}
		return getParentUnindexed();
	}
	
	public AttributeXPathPosition attribute(String name) {
		return attribute(null, name);
	}
	
	public AttributeXPathPosition attribute(String prefix, String name) {
		return new AttributeXPathPosition(this, prefix, name);
	}

	public XPathPosition concat(XPathPosition othrPath) {
		return (XPathPosition) concat((BaseXPathPosition)othrPath);
	}
	
	public XPathPosition withIndex(int idx) {
		XPathSegment sg1 =  new XPathSegment(getLastSegment().prefix, getLastSegment().name, idx);
		return new XPathPosition(getParent(), sg1);
	}
	
	public BaseXPathPosition concat(BaseXPathPosition othrPath) {
		List othrSegs = othrPath.segmentsList();
		BaseXPathPosition resPos = this;
		for (Iterator it = othrSegs.iterator(); it.hasNext();) {
			XPathSegment seg = (XPathSegment) it.next();
			if (!seg.equals(XPathSegment.IDENTITY_PATH_SEGMENT)) {
				if (seg.equals(XPathSegment.PARENT_PATH_SEGMENT)) {
					if (resPos.getParent()==null) throw new IllegalStateException("cant move on parent from " + resPos);
					resPos = resPos.getParent();
				} else {
					resPos = XPathUtils.sharedInstance.createXPathPosition(resPos, seg);										
				}
			}
		}
		return resPos;
	}
}

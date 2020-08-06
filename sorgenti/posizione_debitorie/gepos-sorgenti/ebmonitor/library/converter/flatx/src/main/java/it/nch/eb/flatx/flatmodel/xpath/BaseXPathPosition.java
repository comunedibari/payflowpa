/**
 * Created on 04/ago/2008
 */
package it.nch.eb.flatx.flatmodel.xpath;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author gdefacci
 */
public abstract class BaseXPathPosition implements Comparable, Serializable {
	
	private static final long	serialVersionUID	= 4833211899690540322L;
	
	protected final XPathPosition parent;
	protected final int depth;
	protected final XPathSegment lastSegment;
	protected String xpath;
	
	private XPathPosition unindexed;
	
	public BaseXPathPosition(String prefix, String name) {
		this(null, prefix, name);
	}
	
	public BaseXPathPosition(XPathPosition parent, String prefix, String name) {
		this(parent, new XPathSegment(prefix, name));
	}
	
	protected BaseXPathPosition(XPathPosition parent, String prefix, String name, int idx) {
		this(parent, new XPathSegment(prefix, name, idx));
	}
	
	public BaseXPathPosition(XPathPosition parent, XPathSegment seg) {
		this.parent = parent;
		this.lastSegment = seg;
		this.depth =  parent==null ? 1 : parent.getDepth() + 1;
	}
	
	public synchronized String getXpath() {
		if (xpath==null) {
			StringBuffer sb = new StringBuffer();
			describeTo(sb);
			xpath = sb.toString();
		}
		return xpath;
	}
	
	protected synchronized XPathPosition getParentUnindexed() {
		return unindexed;
	}

	protected synchronized void setParentUnindexed(XPathPosition unindexed) {
		this.unindexed = unindexed;
	}

	public String getName() {
		return lastSegment.name;
	}
	
	public void describeTo(StringBuffer sb) {
		if (parent!=null) parent.describeTo(sb);
		lastSegment.describeTo(sb);
	}
	
	/**
	 * when the prefix is missing, this method returns null
	 * @return
	 */
	public String getPrefix() {
		return lastSegment.prefix;
	}
	
	public int getIndex() {
		return this.lastSegment.getIndex();
	}
	public int getDepth() {
		return depth;
	}
	
	public XPathPosition getParent() {
		return parent;
	}
	
	public XPathSegment getLastSegment() {
		return lastSegment;
	}

	public abstract BaseXPathPosition getUnindexed(); 
	
	private XPathPosition lastSegmentUnindexed = null;
	public synchronized XPathPosition getLastSegmentUnindexed() {
		if (lastSegmentUnindexed==null) {
			if (this.parent==null) {
				unindexed = new XPathPosition(null, getPrefix(), getName(), 1);
			} else {
				unindexed = new XPathPosition(this.getParent(), getPrefix(), getName(), 1);
			}
		}
		return unindexed;
	}

	/**
	 * @override
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof BaseXPathPosition)) return false; // greater than anything not comparable
		BaseXPathPosition othr = (BaseXPathPosition) obj;
		if (depth != othr.depth) return false;
		if (!this.lastSegment.equals(othr.lastSegment)) return false;
		else if ((parent==null && othr.parent == null) || (parent!=null && othr.parent!=null && parent.equals(othr.parent))) {
			return true;
		} else return false;
	}
	
	/**
	 * @override
	 */
	public int hashCode() {
		int tot = 0;
		if (parent!=null) {
			tot += parent.hashCode() * 64;
		}
		tot += lastSegment.hashCode();
		return tot;
	}
	
	/**
	 * @override
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		describeTo(sb);
		return sb.toString();
	}

	public int compareTo(Object o) {
		if (!(o instanceof BaseXPathPosition)) return 1; // greater than anything not comparable
		BaseXPathPosition othr = (BaseXPathPosition) o;
		BaseXPathPosition op = othr.getParent();
		int res;
		if ((this.parent==null) && (op==null)) res = 0 ;
		else if (this.parent!=null && op!=null) res = this.parent.compareTo(op);
		else if (this.parent == null) res = -1;
		else res = 1;
		if (res==0) {
			try {
				res = lastSegment.compareTo( othr.lastSegment );
			} catch (Exception e) {
				throw new RuntimeException("error comparing " + this + " to " + o);
			}
		}
		return res;
	}
	
	public boolean startsWith(XPathSegment[] othrs) {
		List tsl = segmentsList();
		
		int idx = 0;
		boolean startWith = false;
		Iterator tit = tsl.iterator();
		while (tit.hasNext() && idx < othrs.length) {
			XPathSegment ts = (XPathSegment) tit.next();
			XPathSegment os = othrs[idx];
			if (!ts.equals(os)) return false;
			else startWith = true;
			
			idx++;
		}
		if (idx == othrs.length && startWith) return true;
		else return false;
	}
	
	
	public boolean startsWith(BaseXPathPosition othr) {
		List tsl = segmentsList();
		List osl = othr.segmentsList();
		
		boolean startWith = false;
		Iterator tit = tsl.iterator();
		Iterator oit = osl.iterator();
		while (tit.hasNext() && oit.hasNext()) {
			XPathSegment ts = (XPathSegment) tit.next();
			XPathSegment os = (XPathSegment) oit.next();
			if (!ts.equals(os)) return false;
			else startWith = true;
		}
		if (!oit.hasNext() && startWith) return true;
		else return false;
	}
	
	public BaseXPathPosition stripPrefix(BaseXPathPosition othr) {
		List tsl = segmentsList();
		List osl = othr.segmentsList();
		
		boolean startWith = false;
		Iterator tit = tsl.iterator();
		Iterator oit = osl.iterator();
		while (tit.hasNext() && oit.hasNext()) {
			XPathSegment ts = (XPathSegment) tit.next();
			XPathSegment os = (XPathSegment) oit.next();
			if (!ts.equals(os)) return null;
			else startWith = true;
		}
		if (!oit.hasNext() && startWith) {
			List/*<XPathSegment>*/ segs = new ArrayList();
			while (tit.hasNext()) {
				XPathSegment sg = (XPathSegment) tit.next();
				segs.add(sg);
			}
			return XPathUtils.sharedInstance.createXPath(segs);
		}
		else {
			return null;
		}
	}
	
	public List/*<XPathSegment>*/ segmentsList() {
		List res = new ArrayList/*<XPathSegment>*/(getDepth()+1);
		XPathPosition par = getParent();
		res.add(getLastSegment());
		while (par!=null) {
			res.add(0, par.getLastSegment());
			par = par.getParent();
		}
		return res;
	}
	
}

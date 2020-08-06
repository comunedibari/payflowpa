/**
 * Created on 22/ago/08
 */
package it.nch.eb.flatx.flatmodel.xpath;

import java.util.Map;
import java.util.TreeMap;


/**
 * @author gdefacci
 */
public class XPathPositionChildrenAware extends XPathPosition {

	private static final long	serialVersionUID	= 1L;
	private Map/*<String, Integer>*/ childrenIdxMasp = new TreeMap();
	
	public XPathPositionChildrenAware(XPathPositionChildrenAware parent, String prefix, String name) {
		super(parent, prefix, name, getPathSegmentIdx(parent, prefix, name));
		if (parent!=null) parent.putChild(getLastSegment());
	}
	
	static int getPathSegmentIdx(XPathPositionChildrenAware pos, String prefix, String name) {
		String strSegment = pathSegmentRapresentation(prefix, name); 
		Integer i = null;
		if (pos!=null) i = (Integer) pos.childrenIdxMasp.get(strSegment);
		if (i==null) return 1;
		else return i.intValue() + 1;
	}

	private static String pathSegmentRapresentation(String prefix, String name) {
		StringBuffer sb= new StringBuffer();
		if (prefix!=null) {
			sb.append(prefix);
			sb.append(":");
		}
		sb.append(name);
		return sb.toString();
	}
	
	public XPathPositionChildrenAware getChildrenAwareParent() {
		return (XPathPositionChildrenAware) this.getParent();
	}
	
	public void putChild(XPathSegment seg) {
		String strSegment = pathSegmentRapresentation(seg.prefix, seg.name);
		Integer i = (Integer) childrenIdxMasp.get(strSegment);
		if (i==null) this.childrenIdxMasp.put(strSegment, new Integer(1));
		else this.childrenIdxMasp.put(strSegment, new Integer(i.intValue() + 1));
	}

	public XPathPosition element(String prefix, String name) {
		return new XPathPositionChildrenAware(this, prefix, name);
	}
	
}
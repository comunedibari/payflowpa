/**
 * Created on 04/ago/2008
 */
package it.nch.eb.flatx.flatmodel.xpath;

import java.io.Serializable;





/**
 * @author gdefacci
 */
public class XPathSegment implements Comparable/*<XPathSegment>*/, Serializable {
	
	private static final long	serialVersionUID	= -181961455968050608L;
	
	public static final XPathSegment IDENTITY_PATH_SEGMENT = new XPathSegment(null,".");
	public static final XPathSegment PARENT_PATH_SEGMENT = new XPathSegment(null,"..");
	
	public final String prefix;
	public final String name;
	private final int	index;
	
	public XPathSegment(String prefix2, String name2) {
		this(prefix2, name2, 1);
	}
	
	public XPathSegment(String prefix, String name, int idx) {
		if (name == null) throw new NullPointerException();
		if (idx < 1) throw new IllegalArgumentException("invalid path segment index " + idx);
		this.name = name;
		this.prefix = prefix;
		this.index = idx;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof XPathSegment)) return false;
		XPathSegment othr = (XPathSegment) obj;
		return (othr.getIndex() == getIndex()) && (othr.name.equals(name)) && 
			((othr.prefix == null && this.prefix == null) || (othr.prefix != null && this.prefix != null && othr.prefix.equals(prefix)));
	}

	public int hashCode() {
		return ((name == null) ? 66 : name.hashCode() * 7) + ((prefix == null) ? 666 : prefix.hashCode()) + this.getIndex() * 11;
	}
	
	public boolean match(String prfx, String nm) {
		return XPathUtils.sharedInstance.areSegmentsPairsEquals(
				new String[] { prefix, name }, 
				new String[] { prfx, nm } );
	}
//	a little slower than match (i cant understand why)
	public boolean match1(String prfx, String nm) {
		if (this.name.equals(nm)) { 
			if ((prefix == null && prfx == null) || (prefix!=null && prfx!=null && (this.prefix.equals(prfx)))) return true;
			else return false;	
		} else return false;
	}

	public void describeTo(StringBuffer sb) {
		final XPathUtils pu = XPathUtils.sharedInstance;
		
		sb.append(pu.PATH_SEGMENT_SEPARATOR);
		if (this.prefix!=null && prefix.trim().length()>0) {
			sb.append(prefix);
			sb.append(pu.NSS_NAME_SEPARATOR);
		}
		sb.append(name);
		if (this.getIndex() > 1) {
			sb.append("[");
			sb.append(getIndex());
			sb.append("]");
		}
	}

	/**
	 * override describeTo instead
	 */
	public final String toString() {
		StringBuffer sb = new StringBuffer();
		describeTo(sb);
		return sb.toString();
	}

	public int compareTo(Object o) {
		if (!(o instanceof XPathSegment)) return 1; // greater the anything different
		XPathSegment othr = (XPathSegment)o;
		String oprfx = othr.prefix;
		String onm = othr.name;
		int res = compareTo(oprfx, onm);
		if (res==0) res = getIndex() == othr.getIndex() ? 0 : (getIndex() < othr.getIndex() ? -1 : 1);
		return res;
	}

	public int compareTo(String oprfx, String onm) {
		int res = 0;
		if (prefix!=null && oprfx!=null) res = prefix.compareTo(oprfx);
		else if (prefix==null && oprfx==null) res = 0;
		else if (prefix==null) res = -1;
		else res = 1;
		if (res==0) res = name.compareTo(onm);
		return res;
	}
	
	public boolean isAttribute() {
		return false;
	}

	public int getIndex() {
		return index;
	}
	
}

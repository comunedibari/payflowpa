/**
 * Created on 25/ago/2008
 */
package it.nch.eb.flatx.flatmodel.xpath;



/**
 * @author gdefacci
 */
public class AttributeXPathSegment extends XPathSegment {
	
	private static final long	serialVersionUID	= 2514389365028138239L;

	public AttributeXPathSegment(String qname) {
		this(	XPathUtils.sharedInstance.prefixPart(qname), 
				XPathUtils.sharedInstance.namePart(qname));
	}
	
	public AttributeXPathSegment(String prefix2, String name2) {
		super(prefix2, name2);
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof AttributeXPathSegment)) return false;
		return super.equals(obj);
	}
	
	public int compareTo(Object o) {
		if (!(o instanceof XPathSegment)) return 1; // greater the anything different
		return super.compareTo(o);
	}
	
	public boolean isAttribute() {
		return true;
	}

	public void describeTo(StringBuffer sb) {
		final XPathUtils pu = XPathUtils.sharedInstance;
		
		sb.append(pu.PATH_SEGMENT_SEPARATOR);
		sb.append(pu.ATTRIBUTE_PATH_SEGMENT_PREFIX);
		if (this.prefix!=null) {
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

}

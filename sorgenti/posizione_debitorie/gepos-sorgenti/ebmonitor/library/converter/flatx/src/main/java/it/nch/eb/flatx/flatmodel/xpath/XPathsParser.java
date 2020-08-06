/**
 * Created on 29/ago/2008
 */
package it.nch.eb.flatx.flatmodel.xpath;

import java.io.Serializable;


/**
 * FIXME replace with a "non toy" one
 * @author gdefacci
 */
public class XPathsParser implements Serializable {
	
	private static final long serialVersionUID = -1905134753578228933L;
	public static XPathsParser	instance = new XPathsParser();

	public XPathSegment parse(String segment) {
		if (segment.equals(".")) {
			return XPathSegment.IDENTITY_PATH_SEGMENT;
		} else if (segment.startsWith("@")) {
			SegmentInfo si = parseSegment(segment.substring(1));
			return attributePathSegment(si);
		} else {
			SegmentInfo si = parseIndexedSegment(segment);
			return xpathSegment(si);
		}
	}
	
	protected XPathSegment xpathSegment(SegmentInfo si) {
		return new XPathSegment(si.prefix, si.name, si.idx);
	}

	protected AttributeXPathSegment attributePathSegment(SegmentInfo si) {
		return new AttributeXPathSegment(si.prefix, si.name);
	}

	protected SegmentInfo parseIndexedSegment(String str) {
		int sqrStartIdx = str.indexOf('[');
		if (sqrStartIdx >= 0) {
			int sqrEndIdx = str.indexOf(']');
			SegmentInfo si = parseSegment(str.substring(0, sqrStartIdx));
			int idx = parseIndex(str.substring(sqrStartIdx + 1, sqrEndIdx));
			si.setIdx(idx);
			return si;
		} else {
			return parseSegment(str);
		}
	}
	
	protected int parseIndex(String substring) {
		return Integer.valueOf(substring).intValue();
	}

	protected SegmentInfo parseSegment(String str) {
		String[] parts = str.split(":");
		if (parts.length>2) throw new IllegalStateException("inavlid path segment"+  str);
		else return new SegmentInfo(parts);
	}
	
	public XPathPosition parseXPathPosition(String xpath) {
		return (XPathPosition) parseXPath(xpath);
	}
	
	public AttributeXPathPosition parseXPathAttribute(String xpath) {
		return (AttributeXPathPosition) parseXPath(xpath);
	}
	
	public BaseXPathPosition parseXPath(String xpath) {
		try {
			String[] sgparts = xpath.split(XPathUtils.sharedInstance.PATH_SEGMENT_SEPARATOR);
			BaseXPathPosition partial = null;
			for (int i = 0; i < sgparts.length; i++) {
				if (sgparts[i].trim().length()>0) {
					XPathSegment seg = XPathsParser.instance.parse(sgparts[i]);
					if (seg.isAttribute()) 
						partial = new AttributeXPathPosition((XPathPosition)partial, (AttributeXPathSegment)seg);
					else {
						partial = new XPathPosition((XPathPosition)partial, seg);					
					}
				}
			}
			return partial;
		} catch (Exception e) {
			throw new RuntimeException("error parsing xpath " + xpath, e);
		}
	}
	
	private static class SegmentInfo {
		private int idx = 1;
		private String	prefix;
		private String	name;
		SegmentInfo(String[] parts) {
			if (parts.length>2) throw new IllegalStateException();
			else if (parts.length==2) {
				this.prefix = parts[0];
				this.name = parts[1];
			} else if (parts.length==1) {
				this.prefix = null;
				this.name = parts[0];
			}
		}
		
		void setIdx(int idx) {
			this.idx = idx;
		}
		
	}

}
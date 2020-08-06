/**
 * Created on 04/ago/2008
 */
package it.nch.eb.flatx.flatmodel.xpath;

import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.flatx.flatmodel.sax.IllegalXmlException;
import it.nch.fwk.core.NamespaceInfo;
import it.nch.fwk.core.NamespacesInfos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;



/**
 * @author gdefacci
 */
public class XPathUtils {
	
	public final String	NSS_NAME_SEPARATOR	= ":";
	public final char	PATH_SEGMENT_SEPARATOR_CHAR	= '/';
	public final String	PATH_SEGMENT_SEPARATOR	= String.valueOf(PATH_SEGMENT_SEPARATOR_CHAR);
	public final String ATTRIBUTE_PATH_SEGMENT_PREFIX	= "@";
	
	public final static XPathUtils sharedInstance = new XPathUtils();
	
	private XPathUtils() {
	}
	
	public BaseXPathPosition createXPath(List/*<XPathSegment>*/ segs) {
		BaseXPathPosition pos = null;
		for (Iterator it = segs.iterator(); it.hasNext();) {
			XPathSegment seg = (XPathSegment) it.next();
//			pos = createXPathPosition(pos, seg);
			if (!seg.equals(XPathSegment.IDENTITY_PATH_SEGMENT)) {
				if (seg instanceof AttributeXPathSegment) {
					pos = new AttributeXPathPosition((XPathPosition) pos, (AttributeXPathSegment) seg);
				} else {
					pos = new XPathPosition((XPathPosition) pos, seg);
				}
			}
		}
		return pos;
	}

	public BaseXPathPosition createXPathPosition(BaseXPathPosition pos, XPathSegment seg) {
		if (seg==null || pos ==null) throw new NullPointerException();
		if (!(pos instanceof XPathPosition)) 
			throw new IllegalArgumentException("could non append segments to xpath position " + pos + " segment " + seg);
		if (!seg.equals(XPathSegment.IDENTITY_PATH_SEGMENT)) {
			if (seg instanceof AttributeXPathSegment) {
				pos = new AttributeXPathPosition((XPathPosition) pos, (AttributeXPathSegment) seg);
			} else {
				pos = new XPathPosition((XPathPosition) pos, seg);
			}
		}
		return pos;
	}
	
	public BaseXPathPosition createXPathPosition(BaseXPathPosition pos, String ns, String name, int idx) {
		if (pos ==null) throw new NullPointerException();
		if (!(pos instanceof XPathPosition)) 
			throw new IllegalArgumentException("could non append segments to xpath position " + pos + " segment " + ns+":"+name+"[" + idx + "]");
		
		XPathSegment seg = null;
		if (name.startsWith("@")) {
			seg = new AttributeXPathSegment(ns, name);
		} else {
			seg = new XPathSegment(ns, name, idx);	
		}
		
		if (!seg.equals(XPathSegment.IDENTITY_PATH_SEGMENT)) {
			if (seg instanceof AttributeXPathSegment) {
				pos = new AttributeXPathPosition((XPathPosition) pos, (AttributeXPathSegment) seg);
			} else {
				pos = new XPathPosition((XPathPosition) pos, seg);
			}
		}
		return pos;
	}
	
	public void describePathSegmentTo(String prefix, String name , StringBuffer sb) {
		sb.append("/");
		if (prefix!=null) {
			sb.append(prefix);
			sb.append(":");
		}
		sb.append(name);	
	}
	
	public String calculateXPath(List/*<XPathSegment>*/ pathSegments) {
		StringBuffer sb = new StringBuffer();
		for (Iterator it = pathSegments.iterator(); it.hasNext();) {
			XPathSegment seg = (XPathSegment) it.next();
			sb.append("/");
			if (seg.prefix!=null) {
				sb.append(seg.prefix);
				sb.append(":");
			}
			sb.append(seg.name);
		}
		return sb.toString();
	}

	/**
	 * @param nsNamePairs
	 * @return
	 */
	public String calculateXPath(String[][] nsNamePairs) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < nsNamePairs.length; i++) {
			String[] nsNamePair = nsNamePairs[i];
			if (nsNamePair.length < 1 || nsNamePair.length > 2) throw new IllegalXmlException("invalid path segment " + StringUtils.toString(nsNamePair));
			else if (nsNamePair.length == 2 && nsNamePair[0]!=null) {
				sb.append("/");
				sb.append(nsNamePair[0]);
				sb.append(":");
				sb.append(nsNamePair[1]);
			} else {
				sb.append("/");
				sb.append(nsNamePair[0]);
			}
		}
		return sb.toString();
	}
	
	public String[] segmentPair(String qualifiedName) {
		if (qualifiedName == null || "".equals(qualifiedName.trim())) throw new NullPointerException("empty path segment");
		String[] parts = qualifiedName.split(":");
		if (parts.length==1) return new String[] { null, parts[0] };
		else if (parts.length==2) return parts;
		else throw new IllegalXmlException(qualifiedName + " is not a valid path segment");
	}
	
	public boolean areSegmentsPairsEquals(String[] pair1, String[] pair2) {
		if (pair1.length < 1 || pair1.length > 2)  
			throw new IllegalXmlException("invalid path segment " + StringUtils.toString(pair1));
		if (pair2.length < 1 || pair2.length > 2)  
			throw new IllegalXmlException("invalid path segment " + StringUtils.toString(pair2));
		
		if (pair1.length == 1) {
			if (pair2.length == 1) return pair1[0].equals(pair2[0]);
			else return pair2[0] == null && pair1[0].equals(pair2[1]);
		} else { // pair1.length == 2
			if (pair2.length==2) return StringUtils.areEquals(pair1[0], pair2[0]) && StringUtils.areEquals(pair1[1], pair2[1]); 
			else return pair1[0] == null && pair1[1].equals(pair2[0]);
		}
	}

	public String prefixPart(String qname) {
		int idx = qname.indexOf(":");
		if (idx == -1) return null;
		return qname.substring(0, idx-1);
	}

	public String namePart(String qname) {
		int idx = qname.indexOf(":");
		if (idx == -1) return qname;
		return qname.substring(idx+1, qname.length());
	}
	
	public XPathSegment[] segmentsList(BaseXPathPosition p1) {
		List res = new ArrayList/*<XPathSegment>*/(p1.getDepth()+1);
		XPathPosition par = p1.getParent();
		res.add(p1.getLastSegment());
		while (par!=null) {
			res.add(0, par.getLastSegment());
			par = par.getParent();
		}
		return (XPathSegment[]) res.toArray(new XPathSegment[0]);
	}
	
	/**
	 * return null when there's no common prefix, otherwise returns the comon prefix of p1 and p2
	 */
	public BaseXPathPosition commonPrefix(BaseXPathPosition p1, BaseXPathPosition p2) {
		XPathSegment[] lp1 = segmentsList(p1);
		XPathSegment[] lp2 = segmentsList(p2);
		final int minLen = (lp1.length > lp2.length) ? lp2.length : lp1.length;
		boolean doesMatch = true;
		BaseXPathPosition posBuffer = null;
		for (int i = 0; i < minLen && doesMatch; i++) {
			XPathSegment ps1 = lp1[i];
			XPathSegment ps2 = lp2[i];
			if (ps1.equals(ps2)) {
				if (ps1.isAttribute()) {
					posBuffer = new AttributeXPathPosition( (XPathPosition)posBuffer, (AttributeXPathSegment) ps1);
				} else {
					posBuffer = new XPathPosition((XPathPosition) posBuffer, ps1);
				}
			} else {
				doesMatch = false;
			}
		}
		return posBuffer;		
	}
	
	public XPathPosition commonPrefix(XPathPosition p1, XPathPosition p2) {
		return (XPathPosition) commonPrefix((BaseXPathPosition) p1, (BaseXPathPosition)p2);
	}
	
	public XPathPosition commonXPathPositionPrefix(Collection/*<XPathPosition>*/ poss) {
		XPathPosition cmn = null;
		for (Iterator it = poss.iterator(); it.hasNext();) {
			XPathPosition pos = (XPathPosition) it.next();
			if (cmn==null) cmn = pos;
			else cmn = commonPrefix(cmn, pos);
		}
		return cmn;
	}
	
	public boolean match(BaseXPathPosition p1, NamespacesInfos nss1, BaseXPathPosition p2, NamespacesInfos nss2) {
		XPathSegment[] sgs1 = segmentsList(p1);
		XPathSegment[] sgs2 = segmentsList(p2);
		
		if (sgs1.length!= sgs2.length) return false;
		else {
			boolean stillMatch = true;
			for (int i = 0; i < sgs2.length && stillMatch; i++) {
				XPathSegment s1 = sgs1[i];
				XPathSegment s2 = sgs2[i];
				stillMatch = match(s1, nss1, s2, nss2);
			}
			return stillMatch;
		}
		
	}

	public boolean match(XPathSegment p1, NamespacesInfos nss1, XPathSegment p2, NamespacesInfos nss2) {
		boolean res = p1.name.equals(p2.name);
		if (res) res = p1.getIndex() == p2.getIndex();
		if (res) {
			NamespaceInfo u1 = nss1.getNamespaceInfoByPrefix(p1.prefix);
			NamespaceInfo u2 = nss2.getNamespaceInfoByPrefix(p2.prefix);
			if (u1==null) 
				throw new IllegalStateException("the prefix for " + p1 + " is not included in " + nss1);
			if (u2==null) 
				throw new IllegalStateException("the prefix for " + p2 + " is not included in " + nss2);
			res = u1.getUri().equals(u2.getUri());
		}
		return res;
	}
	
	public boolean isRelativePosition(BaseXPathPosition pos) {
		return pos.startsWith(new XPathSegment[] { XPathSegment.PARENT_PATH_SEGMENT })
		|| pos.startsWith(new XPathSegment[] { XPathSegment.IDENTITY_PATH_SEGMENT });
	}
	
	public BaseXPathPosition relative(XPathPosition base, BaseXPathPosition xpth) {
		if (xpth.startsWith(base)) {
			BaseXPathPosition res = xpth.stripPrefix(base);
			if (res==null) res = XPathsParser.instance.parseXPath(".");
			return res;
		} else {
			if (isRelativePosition(xpth)) return xpth;
			
			BaseXPathPosition cmnPrefix = XPathUtils.sharedInstance.commonPrefix(base, xpth);
			BaseXPathPosition sfx1 = base.stripPrefix(cmnPrefix);
			BaseXPathPosition sfx2 = xpth.stripPrefix(cmnPrefix);
			List res = new ArrayList/*<XPathSegment>*/();
			for (int i=0; i< sfx1.getDepth(); i++) {
				res.add(XPathSegment.PARENT_PATH_SEGMENT);
			}
			res.addAll(sfx2 .segmentsList());
			return XPathUtils.sharedInstance.createXPath(res);
		}
	}
	
	public XPathPosition adaptIndexes(XPathPosition pos, XPathPosition baseXPthOrig) {
		return (XPathPosition) adaptIndexes(pos, (BaseXPathPosition)baseXPthOrig);
	}
	public BaseXPathPosition adaptIndexes(XPathPosition pos, BaseXPathPosition baseXPthOrig) {
		BaseXPathPosition rel = null;
		List idxPos = pos.segmentsList();
		List origPos = baseXPthOrig.segmentsList();
		
		if (idxPos.size() > origPos.size()) 
			throw new IllegalStateException("cant adppt idx of " + baseXPthOrig + 
					" from " + pos + 
					"(" + pos + " is not a prefix of " + baseXPthOrig + ")" );
		
		Iterator idxPosIt = idxPos.iterator();
		Iterator origPosIt = origPos.iterator();
		
		while (idxPosIt.hasNext()) {
			XPathSegment idxSg = (XPathSegment) idxPosIt.next();
			XPathSegment origSg = (XPathSegment) origPosIt.next();
			
			if (idxSg.match(origSg.prefix, origSg.name)) {
				rel = new XPathPosition((XPathPosition) rel, idxSg);
			} else {
				throw new IllegalStateException(pos + " is not a prefix of " + baseXPthOrig );
			}
			
		}
		
		while (origPosIt.hasNext()) {
			XPathSegment origSg = (XPathSegment) origPosIt.next();
			if (origSg.isAttribute()) {
				rel = new AttributeXPathPosition((XPathPosition) rel, (AttributeXPathSegment) origSg);
			} else {
				rel = new XPathPosition((XPathPosition) rel, origSg);
			}
		}

		if (rel==null) throw new NullPointerException();
		return rel;
	}
	
	public BaseXPathPosition adaptCommonPrefixIndexes(XPathPosition pos, BaseXPathPosition baseXPthOrig) {
		BaseXPathPosition rel = null;
		List idxPos = pos.segmentsList();
		List origPos = baseXPthOrig.segmentsList();
		
		Iterator idxPosIt = idxPos.iterator();
		Iterator origPosIt = origPos.iterator();
		boolean finished = false;
		
		while (!finished && idxPosIt.hasNext()) {
			XPathSegment idxSg = (XPathSegment) idxPosIt.next();
			XPathSegment origSg = (XPathSegment) origPosIt.next();
			
			if (idxSg.match(origSg.prefix, origSg.name)) {
				rel = new XPathPosition((XPathPosition) rel, idxSg);
			} else {
				rel = new XPathPosition((XPathPosition) rel, origSg);
				finished = true;
			}
		}
		
		while (origPosIt.hasNext()) {
			XPathSegment origSg = (XPathSegment) origPosIt.next();
			if (origSg.isAttribute()) {
				rel = new AttributeXPathPosition((XPathPosition) rel, (AttributeXPathSegment) origSg);
			} else {
				rel = new XPathPosition((XPathPosition) rel, origSg);
			}
		}

		if (rel==null) throw new NullPointerException();
		return rel;
	}
	
	public boolean containSegment(BaseXPathPosition pos, String segStr) {
		return containSegment(pos, XPathsParser.instance.parse(segStr));
	}
	
	public boolean containSegment(BaseXPathPosition pos, XPathSegment seg) {
		Set segs = new TreeSet( pos.segmentsList() );
		return segs.contains(seg);
	}
	
	public String[] splitOnSegment(String pos, String splitSeg) {
		return splitOnSegment(XPathsParser.instance.parseXPath( pos ), XPathsParser.instance.parse(splitSeg));
	}
	
	public String[] splitOnSegment(BaseXPathPosition pos, String splitSeg) {
		return splitOnSegment(pos, XPathsParser.instance.parse(splitSeg));
	}
	
	public String[] splitOnSegment(BaseXPathPosition pos, XPathSegment splitSeg) {
		List segs = pos.segmentsList();
		XPathPosition prfx = null;
		BaseXPathPosition sfx = null;
		boolean feedPrefix = true;
		for (Iterator it = segs.iterator(); it.hasNext();) {
			XPathSegment segment = (XPathSegment) it.next();
			if (!splitSeg.equals(segment)) {
				if (feedPrefix) prfx = new XPathPosition(prfx, segment);
				else {
					if (segment instanceof AttributeXPathSegment) {
						sfx = new AttributeXPathPosition( (XPathPosition)sfx, (AttributeXPathSegment)segment );
					} else {
						sfx = new XPathPosition( (XPathPosition)sfx, segment );
					}
				}
			} else {
				feedPrefix = false;
			}
		}
		/* if (feedPrefix == true) => we've didnt found splitSeg so an error should be raised */
		if (feedPrefix) {
			throw new RuntimeException("the segment " + splitSeg + " is not included inside xpath  " + pos);
		} else {
			String prfxStr = (prfx==null) ? null : prfx.toString();
			String sfxStr = (sfx==null) ? null : sfx.toString();
			return new String[] { prfxStr, sfxStr };
		}
	}

}

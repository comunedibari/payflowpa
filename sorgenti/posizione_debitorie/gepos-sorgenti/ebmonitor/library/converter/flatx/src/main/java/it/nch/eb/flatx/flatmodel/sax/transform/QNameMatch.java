package it.nch.eb.flatx.flatmodel.sax.transform;

import org.xml.sax.Attributes;

/**
 * @author gdefacci
 */
public final class QNameMatch implements AttributeMatcher {
	private String qname;
	
	public QNameMatch(String qname) {
		this.qname = qname;
	}

	public boolean match(Attributes attrs, int i) {
		return attrs.getQName(i).equals(qname);
	}
}
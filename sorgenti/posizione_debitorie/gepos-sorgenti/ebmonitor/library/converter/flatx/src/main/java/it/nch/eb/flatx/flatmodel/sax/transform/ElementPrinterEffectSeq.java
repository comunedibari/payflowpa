/**
 * 25/set/2009
 */
package it.nch.eb.flatx.flatmodel.sax.transform;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

import org.xml.sax.Attributes;

public class ElementPrinterEffectSeq implements ElementPrinterEffect {
	private final ElementPrinterEffect[] efects;

	public ElementPrinterEffectSeq(ElementPrinterEffect[] efects) {
		this.efects = efects;
	}

	public StartElementResult start(XPathPosition pos, Attributes attrs) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < this.efects.length; i++) {
			ElementPrinterEffect efct = this.efects[i];
			StartElementResult startResult = efct.start(pos, attrs);
			sb.append( startResult.getContent() );
		}
		return new StartElementResult( sb.toString() );
	}
	
	public String end(XPathPosition pos, String content) {
		StringBuffer sb = new StringBuffer();
		for (int i = efects.length - 1 ; i > -1 ; i--) {
			ElementPrinterEffect efct = efects[i];
			sb.append(efct.end(pos, content));
		}
		return sb.toString();
	}
	
}
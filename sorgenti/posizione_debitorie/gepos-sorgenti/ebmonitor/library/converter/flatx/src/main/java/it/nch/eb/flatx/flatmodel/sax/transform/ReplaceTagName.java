/**
 * 25/set/2009
 */
package it.nch.eb.flatx.flatmodel.sax.transform;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

/**
 * @author gdefacci
 */
public class ReplaceTagName extends DefaultElementPrinterEffect {
	private final String tagName;
	
	public ReplaceTagName(String tagName) {
		this.tagName = tagName;
	}
	
	public ReplaceTagName(String tagName, AttributesDescriber attributesDescriber) {
		super(attributesDescriber);
		this.tagName = tagName;
	}

	protected String tagName(XPathPosition pos) {
		return tagName;
	}

}
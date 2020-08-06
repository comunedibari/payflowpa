/**
 * 25/set/2009
 */
package it.nch.eb.flatx.flatmodel.sax.transform;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

/**
 * @author gdefacci
 */
public class ReplaceTagNameAndText extends DefaultElementPrinterEffect {
	private final String tagName;
	private final String content;
	
	public ReplaceTagNameAndText(String tagName, String content) {
		this.tagName = tagName;
		this.content = content;
	}
	
	public ReplaceTagNameAndText(String tagName, String content, AttributesDescriber attributesDescriber) {
		super(attributesDescriber);
		this.tagName = tagName;
		this.content = content;
	}

	protected String tagName(XPathPosition pos) {
		return tagName;
	}

	protected String getContent(String cnt) {
		return this.content;
	}

}
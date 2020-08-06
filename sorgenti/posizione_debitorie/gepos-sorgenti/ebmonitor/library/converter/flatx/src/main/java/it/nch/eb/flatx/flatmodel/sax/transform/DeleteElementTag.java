/**
 * 25/set/2009
 */
package it.nch.eb.flatx.flatmodel.sax.transform;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

/**
 * @author gdefacci
 */
public class DeleteElementTag extends InsertStringPrinterEffect {
	
	public DeleteElementTag() {
		super("");
	}

	public String end(XPathPosition pos, String content) {
		return content +  "";
	}
}
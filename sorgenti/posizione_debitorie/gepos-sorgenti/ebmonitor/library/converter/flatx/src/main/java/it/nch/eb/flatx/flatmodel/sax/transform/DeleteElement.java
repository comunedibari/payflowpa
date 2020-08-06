/**
 * 25/set/2009
 */
package it.nch.eb.flatx.flatmodel.sax.transform;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

import org.xml.sax.Attributes;


/**
 * @author gdefacci
 */
public final class DeleteElement extends InsertStringPrinterEffect {
	
	public DeleteElement() {
		super("", "");
	}

	public StartElementResult start(XPathPosition pos, Attributes attrs) {
		return super.start(pos, attrs).withVisitChildren(false);
	}

}
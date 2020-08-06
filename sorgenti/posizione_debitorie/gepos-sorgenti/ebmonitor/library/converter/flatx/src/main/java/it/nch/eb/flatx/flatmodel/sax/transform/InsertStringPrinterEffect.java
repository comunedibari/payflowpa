/**
 * 25/set/2009
 */
package it.nch.eb.flatx.flatmodel.sax.transform;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

import org.xml.sax.Attributes;

public class InsertStringPrinterEffect implements ElementPrinterEffect {

	private String startSequence;
	private String endSequence;
	
	public InsertStringPrinterEffect(String startSequence) {
		this(startSequence, "");
	}
	public InsertStringPrinterEffect(String startSequence, String endSequence) {
		this.startSequence = startSequence;
		this.endSequence = endSequence;
	}

	public String end(XPathPosition pos, String content) {
		return endSequence;
	}

	public StartElementResult start(XPathPosition pos, Attributes attrs) {
		return new StartElementResult( startSequence );
	}
}
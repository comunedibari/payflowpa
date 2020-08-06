/**
 * 
 */
package it.nch.eb.flatx.flatmodel.sax.transform;


import org.xml.sax.Attributes;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

/**
 * @author Admin
 *
 */
public interface ElementPrinterEffect {

	StartElementResult start(XPathPosition pos, Attributes attrs);
	
	String end(XPathPosition pos, String content);
}

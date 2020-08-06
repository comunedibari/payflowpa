/**
 * Created on 07/ago/2008
 */
package it.nch.eb.flatx.flatmodel.sax;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;





/**
 * @author gdefacci
 */
public interface ElementHandler {
	
	void startElement(XPathPosition pos);
	void endElement(XPathPosition pos);

}

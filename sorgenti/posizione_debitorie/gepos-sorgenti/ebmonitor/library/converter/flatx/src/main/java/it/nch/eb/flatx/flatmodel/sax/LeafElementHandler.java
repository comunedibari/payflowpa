/**
 * Created on 07/ago/2008
 */
package it.nch.eb.flatx.flatmodel.sax;

import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;




/**
 * @author gdefacci
 */
public interface LeafElementHandler extends ElementHandler {
	
	void text(BaseXPathPosition pos, String text);

}

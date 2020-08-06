/**
 * Created on 04/lug/2008
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;



/**
 * @author gdefacci
 */
public interface ElementPredicate {
	
	boolean match(XPathPosition pos, IXPathMapScope elemsMap);

}

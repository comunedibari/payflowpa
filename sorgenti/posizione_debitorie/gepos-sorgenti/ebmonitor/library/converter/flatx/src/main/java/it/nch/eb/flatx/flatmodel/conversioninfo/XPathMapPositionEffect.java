/**
 * Created on 02/set/2008
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;



/**
 * @author gdefacci
 */
public interface XPathMapPositionEffect {
	
	void apply(XPathPosition pos, XPathsMapBindings xpathBindings);

}

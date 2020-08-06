/**
 * Created on 09/gen/08
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;




/**
 * @author gdefacci
 */
public interface ElementToStringFunction {

	String getValue(XPathPosition pos, XPathsMapBindings elemsMap);
}

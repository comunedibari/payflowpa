/**
 * Created on 11/gen/08
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;



/**
 * @author gdefacci
 */
public interface XPathsMapBindinsManagerStringFunction {

	String getValue(XPathPosition pos, XPathsMapBindings elemsMap, IBindingManager bindingManager);
}

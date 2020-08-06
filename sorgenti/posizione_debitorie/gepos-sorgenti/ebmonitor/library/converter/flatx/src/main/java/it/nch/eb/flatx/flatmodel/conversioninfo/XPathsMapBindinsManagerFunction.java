/**
 * Created on 11/gen/08
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;



/**
 * TODO: actually not used
 * @author gdefacci
 */
public interface XPathsMapBindinsManagerFunction {

	Object getValue(XPathPosition pos, XPathsMapBindings/*<String,String>*/ elemsMap, IBindingManager bindingManager);
}

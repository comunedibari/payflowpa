/**
 * Created on 15/gen/08
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;



/**
 * simply return the content of the given element
 * @author gdefacci
 */
public class GetTextValue implements XPathsMapBindinsManagerStringFunction {
	
	private final String	value;

	public GetTextValue(String value) {
		this.value = value;
	}
	
	public String getValue(XPathPosition pos, XPathsMapBindings elemsMap, IBindingManager bindingManager) {
		return value;
	}

}

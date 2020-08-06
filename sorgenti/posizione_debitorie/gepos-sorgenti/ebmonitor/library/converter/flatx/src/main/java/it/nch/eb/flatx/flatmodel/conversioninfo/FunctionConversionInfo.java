/**
 * Created on 07/gen/08
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.bean.types.SizedConverter;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;



/**
 * @author gdefacci
 */
public class FunctionConversionInfo extends ExpressionConversionInfo  {
	
	private static final long	serialVersionUID	= -7920906483767200595L;
	public final XPathsMapBindinsManagerStringFunction	function;

	public FunctionConversionInfo(int pos, SizedConverter converter, String name, XPathsMapBindinsManagerStringFunction function) {
		super(pos, converter, name);
		this.function = function;
	}

	public FunctionConversionInfo(int pos, SizedConverter converter, XPathsMapBindinsManagerStringFunction function2) {
		this(pos, converter, null, function2);
	}

	public String getValue(XPathPosition pos, XPathsMapBindings elemsMap, IBindingManager bindingManager) {
		return function.getValue(pos, elemsMap, bindingManager);
	}

}

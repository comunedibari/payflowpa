/**
 * Created on 07/gen/08
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.converters.NullConverter;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;



/**
 * @author gdefacci
 */
public class VoidExpressionConversionInfo extends ExpressionConversionInfo  {
	
	private static final long	serialVersionUID	= -7920906483767200595L;

	public VoidExpressionConversionInfo(int pos, int len, String name) {
		super(pos, new NullConverter(len), name);
	}

	public VoidExpressionConversionInfo(int pos, int len) {
		this(pos, len, null);
	}

	public String getValue(XPathPosition pos, XPathsMapBindings elemsMap, IBindingManager bindingManager) {
		throw new IllegalStateException("VoidExpressionConversionInfo");
	}

}

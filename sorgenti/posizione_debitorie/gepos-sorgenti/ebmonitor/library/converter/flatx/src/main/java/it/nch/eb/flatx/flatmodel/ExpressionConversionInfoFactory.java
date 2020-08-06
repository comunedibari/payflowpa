/**
 * Created on 28/mag/08
 */
package it.nch.eb.flatx.flatmodel;

import java.io.Serializable;

import it.nch.eb.flatx.bean.types.CoreConverters;
import it.nch.eb.flatx.bean.types.SizedConverter;
import it.nch.eb.flatx.flatmodel.conversioninfo.ExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.FunctionConversionInfoAdapters;
import it.nch.eb.flatx.flatmodel.conversioninfo.FunctionConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.VoidExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathsMapBindinsManagerStringFunction;
import it.nch.eb.flatx.flatmodel.converters.NullConverter;


/**
 * @author gdefacci
 */
public class ExpressionConversionInfoFactory implements Serializable {
	
	private static final long	serialVersionUID	= 5955963701525023371L;
	public final static ExpressionConversionInfoFactory instance = new ExpressionConversionInfoFactory();
	
	protected ExpressionConversionInfoFactory() {
	}
	
	public ExpressionConversionInfo createGetBindingValue(int pos, SizedConverter converter, String bindingName) {
//		return create(pos, converter, new FunctionConversionInfoAdapters.FromBindingManagerValue(bindingName));
		return createGetBindingValue(pos, converter, bindingName, false);
	}
	
	public ExpressionConversionInfo createGetBindingValue(int pos, SizedConverter converter, String bindingName, boolean optional) {
		return create(pos, converter, new FunctionConversionInfoAdapters.FromBindingManagerValue(bindingName, optional));
	}
	
	public FunctionConversionInfo createFixedValue(int pos, String value) {
		return createFixedValue(pos, new CoreConverters.StringIdentity(value), value);
	}
	
	public FunctionConversionInfo createFixedValue(int pos, SizedConverter converter, String value) {
		return create(pos, converter, new FunctionConversionInfoAdapters.FromString(value));
	}
	
	public ExpressionConversionInfo create(int pos, XPathsMapBindinsManagerStringFunction bmAction) {
		return create(pos, NullConverter.instance, bmAction);
	}
	
	public FunctionConversionInfo create(int pos, SizedConverter converter, XPathsMapBindinsManagerStringFunction function ) {
		return new FunctionConversionInfo(pos, converter, function);
	}
	
	public VoidExpressionConversionInfo createVoid(int pos, int len) {
		return new VoidExpressionConversionInfo(pos, len);
	}
	
}

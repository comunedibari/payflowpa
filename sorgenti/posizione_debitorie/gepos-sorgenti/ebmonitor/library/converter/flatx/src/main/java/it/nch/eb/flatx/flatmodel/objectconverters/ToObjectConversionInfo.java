/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters;

import it.nch.eb.common.utils.ReflectionUtils;
import it.nch.eb.common.utils.ReflectionUtils.Setter;
import it.nch.eb.flatx.bean.types.SizedConverter;
import it.nch.eb.flatx.flatmodel.StringPropertySetter;
import it.nch.eb.flatx.flatmodel.conversioninfo.ConversionInfo;


/**
 * @author gdefacci
 */
public class ToObjectConversionInfo extends ConversionInfo implements StringPropertySetter {
	
	private static final long	serialVersionUID	= 8894599018009193000L;
	private final ToObjectConverter toObjectConverter;
	
	public ToObjectConversionInfo(int pos, SizedConverter delegate, String name) {
		super(pos, delegate, name);
		this.toObjectConverter = new IdentityToObjectConverter(delegate);
	}

	public ToObjectConversionInfo(int pos, SizedConverter delegate) {
		super(pos, delegate);
		this.toObjectConverter = new IdentityToObjectConverter(delegate);
	}
	
	public ToObjectConversionInfo(int pos, SizedConverter delegate, ToObjectConverter toObjectConverter) {
		super(pos, delegate);
		this.toObjectConverter = toObjectConverter;
	}
	
	public ToObjectConversionInfo(int pos, SizedConverter delegate, String name, ToObjectConverter toObjectConverter) {
		super(pos, delegate, name);
		this.toObjectConverter = toObjectConverter;
	}
	
	public ToObjectConverter getToObjectConverter() {
		return toObjectConverter;
	}

	public void setProperty(Object target, String propValue) {
		String c1 = getConverter().encode(propValue);
		Object realObjectvalue = getToObjectConverter().convert(c1);
		Setter setter = ReflectionUtils.getSetterForProperty(target, getName(), getToObjectConverter().getConversionTargetClass());
		setter.setValue(realObjectvalue);
	}

}

/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters;

import it.nch.eb.flatx.bean.types.SizedConverter;
import it.nch.eb.flatx.flatmodel.objectconverters.converters.VoidToObjectConverter;


/**
 * @author gdefacci
 */
public class ToObjectConvertBuilder {
	
	static final ToObjectConverter VOID_CONVERTER = new VoidToObjectConverter();
	
	private int pos = 1;
	
	public ToObjectConversionInfo create(SizedConverter converter, ToObjectConverter toObjectConverter) {
		ToObjectConversionInfo toObjectConversionInfo = new ToObjectConversionInfo(pos, converter, toObjectConverter);
		pos++;
		return toObjectConversionInfo;
	}
	
	public ToObjectConversionInfo create(SizedConverter converter) {
		return create(converter, new IdentityToObjectConverter(converter));
	}

	public ToObjectConversionInfo createVoid(SizedConverter converter) {
		return create(converter, VOID_CONVERTER);
	}

}

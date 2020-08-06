/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters.converters;

import it.nch.eb.flatx.flatmodel.objectconverters.ToObjectConverter;
import it.nch.eb.flatx.flatmodel.objectconverters.TypeSafeToObjectConverter;


/**
 * @author gdefacci
 */
public class ToLongConverter extends TypeSafeToObjectConverter implements ToObjectConverter {

	private static final long serialVersionUID = 830616226422541396L;

	public ToLongConverter() {
		super(Long.class);
	}

	public Object safeConvert(String str) {
		return Long.valueOf(str.trim());
	}
	
}

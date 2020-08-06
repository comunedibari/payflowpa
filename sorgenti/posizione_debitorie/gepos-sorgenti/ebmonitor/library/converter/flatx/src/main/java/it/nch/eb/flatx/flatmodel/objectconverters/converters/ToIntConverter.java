/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters.converters;

import it.nch.eb.flatx.flatmodel.objectconverters.ToObjectConverter;
import it.nch.eb.flatx.flatmodel.objectconverters.TypeSafeToObjectConverter;


/**
 * @author gdefacci
 */
public class ToIntConverter extends TypeSafeToObjectConverter implements ToObjectConverter {

	private static final long serialVersionUID = -8619622661523054104L;

	public ToIntConverter() {
		super(Integer.class);
	}

	public Object safeConvert(String str) {
		return Integer.valueOf(str.trim());
	}
	
}

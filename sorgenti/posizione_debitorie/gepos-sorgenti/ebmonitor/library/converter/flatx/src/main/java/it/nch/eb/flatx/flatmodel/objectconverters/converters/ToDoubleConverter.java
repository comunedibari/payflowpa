/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters.converters;

import it.nch.eb.flatx.flatmodel.objectconverters.TypeSafeToObjectConverter;


/**
 * @author gdefacci
 */
public class ToDoubleConverter extends TypeSafeToObjectConverter {

	private static final long serialVersionUID = -6311150927818357195L;

	public ToDoubleConverter() {
		super(Double.class);
	}

	public Object safeConvert(String str) {
		return Double.valueOf(str.trim());
	}

}

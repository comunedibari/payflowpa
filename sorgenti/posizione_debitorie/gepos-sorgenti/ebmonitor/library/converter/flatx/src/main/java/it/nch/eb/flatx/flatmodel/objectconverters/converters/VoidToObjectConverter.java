/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters.converters;

import it.nch.eb.flatx.flatmodel.objectconverters.ToObjectConverter;
import it.nch.eb.flatx.flatmodel.objectconverters.TypeSafeToObjectConverter;


/**
 * marker class that states no conversion can be performed using this converter
 * @author gdefacci
 */
public class VoidToObjectConverter extends TypeSafeToObjectConverter implements ToObjectConverter {

	private static final long serialVersionUID = -4641481842408286858L;

	public VoidToObjectConverter() {
		super(Void.TYPE);
	}

	public Object safeConvert(String str) {
		throw new UnsupportedOperationException("a void converter cant convert");
	}
	
	public static boolean isVoid(ToObjectConverter toObjConverter) {
		if (toObjConverter instanceof VoidToObjectConverter) return true;
		return false;
	}

}

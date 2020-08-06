/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters;

import it.nch.eb.common.utils.ReflectionUtils;
import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.flatx.exceptions.ToObjectConversionException;


/**
 * base class for converters that:
 * - return null on null input or emtpy string, or empty trimmed string
 * - the target of the conversion should be of a specific type => convert(String) return null or a toClass object 
 * instance or a ClassCastException is raised
 * @author gdefacci
 */
public abstract class TypeSafeToObjectConverter implements ToObjectConverter, ReversibleObjectConverter {
	
	private static final long serialVersionUID = 2618681594684302720L;
	private final Class toClass;

	public TypeSafeToObjectConverter(Class toClass) {
		this.toClass = toClass;
	}

	public abstract Object safeConvert(String str);
	
	public final Object convert(String str) {
		if (str==null) return str;
		if ("".equals(str.trim())) return null;
		
		Object object = null;
		try {
			object = safeConvert(str);
		} catch (Exception e) {
			throw new ToObjectConversionException(this, str, e);
		}
		return ReflectionUtils.cast(object, toClass);
	}
	
	public final Class getConversionTargetClass() {
		return toClass;
	}

	public String toString() {
		return StringUtils.getSimpleName(TypeSafeToObjectConverter.class) 
			+ " convert to " + StringUtils.getSimpleName( getConversionTargetClass() );
	}

	public ToStringObjectConverter getReverse() {
		return ToStringObjectConverter.DEFAULT;
	}
	
}

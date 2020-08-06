/**
 * Created on 30/lug/07
 */
package it.nch.eb.flatx.bean.types;

import it.nch.eb.flatx.flatmodel.converters.ClassIdentityConverter;

public abstract class TypedConverterImpl extends ClassIdentityConverter implements Converter {
	
	private static final long	serialVersionUID	= 1013448306222663092L;
	private Class	propertyClass;
	
	public TypedConverterImpl(Class propertyClass) {
		this.propertyClass = propertyClass;
	}

	public Class getPropertyClass() {
		return propertyClass;
	}
}
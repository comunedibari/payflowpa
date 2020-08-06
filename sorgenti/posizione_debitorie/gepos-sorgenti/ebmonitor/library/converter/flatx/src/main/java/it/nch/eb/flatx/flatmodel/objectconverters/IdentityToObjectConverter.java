/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters;

import it.nch.eb.flatx.bean.types.Converter;


/**
 * @author gdefacci
 */
public class IdentityToObjectConverter implements ToObjectConverter {

	private static final long serialVersionUID = 6632780820918075869L;
	final Converter converter;
	
	public IdentityToObjectConverter(Converter converter) {
		this.converter = converter;
	}

	public Object convert(String str) {
		return converter.encode(str);
	}

	public Class getConversionTargetClass() {
		return String.class;
	}

	public String toString() {
		return "IdentityToObjectConverter";
	}
	
}

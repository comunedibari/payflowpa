/**
 * 25/nov/2009
 */
package it.nch.eb.flatx.bean.types;

import it.nch.eb.flatx.exceptions.ConversionException;

/**
 * @author gdefacci
 */
public class EnumConverter implements Converter {
	
	private String[] enumValues;

	public EnumConverter(String[] enumValues) {
		this.enumValues = enumValues;
	}


	public String encode(String stringModel) {
		for (int i = 0; i < enumValues.length; i++) {
			String enm = enumValues[i];
			if (enm.equalsIgnoreCase(stringModel)) {
				return stringModel;
			}
		}
		throw new ConversionException(stringModel + " non vale uno dei " + enumValues, this);
	}

}

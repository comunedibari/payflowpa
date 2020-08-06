/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters.converters;

import java.util.Date;

import it.nch.eb.flatx.flatmodel.objectconverters.TypeSafeToObjectConverter;

/**
 * convert a string <code>str</code> containing a number in a date <code>d</code> where:
 * <code>String.valueOf( d.getTime() )</code> should match <code>str</code>
 * @author gdefacci
 */
public class LongAsStringToDateConverter extends TypeSafeToObjectConverter {

	private static final long serialVersionUID = -1483489581367216865L;

	public LongAsStringToDateConverter() {
		super(Date.class);
	}

	public Object safeConvert(String str) {
		if (str==null) return null;
		Long i = Long.valueOf(str.trim());
		return new Date(i.longValue());
	}

}

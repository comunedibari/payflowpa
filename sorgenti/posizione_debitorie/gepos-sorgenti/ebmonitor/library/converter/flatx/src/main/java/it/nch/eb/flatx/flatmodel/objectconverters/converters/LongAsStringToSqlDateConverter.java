/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters.converters;

import it.nch.eb.flatx.flatmodel.objectconverters.TypeSafeToObjectConverter;

/**
 * convert a string <code>str</code> containing a number in a sql date <code>d</code> where:
 * <code>String.valueOf( d.getTime() )</code> should match <code>str</code>
 * @author gdefacci
 */
public class LongAsStringToSqlDateConverter extends TypeSafeToObjectConverter {

	private static final long serialVersionUID = 6602065862663209546L;

	public LongAsStringToSqlDateConverter() {
		super(java.sql.Date.class);
	}

	public Object safeConvert(String str) {
		if (str==null) return null;
		Long i = Long.valueOf(str.trim());
		return new java.sql.Date(i.longValue());
	}

}

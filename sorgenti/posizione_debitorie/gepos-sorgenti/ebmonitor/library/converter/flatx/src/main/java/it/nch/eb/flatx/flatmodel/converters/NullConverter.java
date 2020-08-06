/**
 * Created on 09/gen/08
 */
package it.nch.eb.flatx.flatmodel.converters;

import it.nch.eb.flatx.bean.types.SizedConverter;


/**
 * @author gdefacci
 */
public final class NullConverter implements SizedConverter {
	
	private static final long	serialVersionUID	= 185623543670931062L;
	public static final SizedConverter instance = new NullConverter();
	private final int length;
	
	private NullConverter() {
		this(0);
	}
	
	public NullConverter(int len) {
		this.length = len;
	}

	public final Integer getLength() {
		return new Integer(length);
	}

	public final String encode(String stringModel) {
		return null;
	}

	public static final boolean isNull(SizedConverter converter) {
		return converter==null || converter instanceof NullConverter;
	}

	public String toString() {
		return "NullConverter";
	}

}

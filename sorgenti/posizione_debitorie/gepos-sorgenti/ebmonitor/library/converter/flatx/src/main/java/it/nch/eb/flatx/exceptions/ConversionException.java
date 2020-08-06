/**
 * Created on 15/lug/07
 */
package it.nch.eb.flatx.exceptions;

import it.nch.eb.flatx.bean.types.Converter;


/**
 * @author gdefacci
 */
public class ConversionException extends ParserException implements IRecoverableParserException {
	
	protected Converter	converter;

	public ConversionException(Throwable cause) {
		super(cause);
	}

	public ConversionException(Throwable cause, Converter converter) {
		super(cause);
		this.converter = converter;
	}
	
	public ConversionException(String message, Throwable cause, Converter converter) {
		super(message, cause);
		this.converter = converter;
	}
	
	public ConversionException(String message, Converter converter) {
		super(message);
		this.converter = converter;
	}

	public ConversionException(Throwable cause, Converter converter, Object value) {
		super(cause, value);
		this.converter = converter;
	}

	public Converter getConverter() {
		return converter;
	}

	public String getMessage() {
		return super.getMessage() + "\nConverter " + converter;
	}

	private static final long	serialVersionUID	= -898078337930695377L;
}

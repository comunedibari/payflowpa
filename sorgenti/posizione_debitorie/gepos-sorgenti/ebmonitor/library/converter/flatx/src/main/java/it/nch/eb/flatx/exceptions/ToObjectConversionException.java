/**
 * Created on 03/mar/08
 */
package it.nch.eb.flatx.exceptions;

import it.nch.eb.flatx.flatmodel.objectconverters.ToObjectConverter;


/**
 * @author gdefacci
 */
public class ToObjectConversionException extends ParserException implements IRecoverableParserException {
	
	private final ToObjectConverter converter;
	
	public ToObjectConversionException(ToObjectConverter converter, Object sourceValue, Exception e) {
		super(e, sourceValue);
		this.converter = converter;
	}
	
	public ToObjectConversionException(String msg, ToObjectConverter converter, Object sourceValue, Exception e) {
		super(msg, e, sourceValue);
		this.converter = converter;
	}
	
	public ToObjectConversionException(String msg, ToObjectConverter converter, Object sourceValue) {
		super(msg, sourceValue);
		this.converter = converter;
	}
	
	public ToObjectConverter getConverter() {
		return converter;
	}

	public String getMessage() {
		return super.getMessage() 
			+ "\nconverter " + this.converter; 
	}

	static final long	serialVersionUID	= -7086031929832564738L;	
}

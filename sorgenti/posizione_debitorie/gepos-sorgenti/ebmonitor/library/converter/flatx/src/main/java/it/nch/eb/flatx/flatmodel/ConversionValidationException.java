/**
 * Created on 11/feb/08
 */
package it.nch.eb.flatx.flatmodel;


/**
 * @author gdefacci
 */
public class ConversionValidationException extends RuntimeException {
	
	public ConversionValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConversionValidationException(String message) {
		super(message);
	}

	private static final long	serialVersionUID	= -6524286152685992458L;

}

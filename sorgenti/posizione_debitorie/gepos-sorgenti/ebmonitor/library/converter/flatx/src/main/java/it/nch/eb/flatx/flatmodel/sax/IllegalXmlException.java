/**
 * Created on 04/ago/2008
 */
package it.nch.eb.flatx.flatmodel.sax;


/**
 * @author gdefacci
 */
public class IllegalXmlException extends RuntimeException {

	private static final long	serialVersionUID	= -5000260752199298371L;

	public IllegalXmlException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalXmlException(String message) {
		super(message);
	}

	
}

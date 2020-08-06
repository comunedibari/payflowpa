/**
 * 
 */
package it.nch.fwk.xml.schema;

/**
 * @author gedfr
 *
 */
public class NameNotFoundException extends Exception {

	private static final long serialVersionUID = -2592761958535843549L;

	/**
	 * 
	 */
	public NameNotFoundException() {
	}

	/**
	 * @param message
	 */
	public NameNotFoundException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NameNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NameNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}

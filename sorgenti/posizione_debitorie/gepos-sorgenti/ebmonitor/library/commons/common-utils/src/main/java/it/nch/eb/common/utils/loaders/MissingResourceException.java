/**
 * Created on 01/ott/07
 */
package it.nch.eb.common.utils.loaders;


/**
 * @author gdefacci
 */
public class MissingResourceException extends RuntimeException {

	private static final long	serialVersionUID	= 1L;
	
	private final String location;
	
	private static String GENERIC_MESSAGE = "Cant find the resource";
	
	public MissingResourceException(Throwable cause, String location) {
		this(cause, location, GENERIC_MESSAGE);
	}
	
	public MissingResourceException(Throwable cause, String location, String genericMessage) {
		super(composeMessage(location, genericMessage), cause);
		this.location = location;
	}
	
	public MissingResourceException(String message, String location) {
		super(message);
		this.location = location;
	}
	
	static String composeMessage(String location, String genMessage) {
		return genMessage + "[" + location + "]";
	}

	public String getLocation() {
		return location;
	}
	
}

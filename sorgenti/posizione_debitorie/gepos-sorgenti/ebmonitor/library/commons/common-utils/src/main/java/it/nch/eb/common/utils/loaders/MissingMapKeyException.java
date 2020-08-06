/**
 * Created on 01/ott/07
 */
package it.nch.eb.common.utils.loaders;


/**
 * @author gdefacci
 */
public class MissingMapKeyException extends RuntimeException {

	private static final long	serialVersionUID	= 1L;
	private String	key;

	public MissingMapKeyException(String key) {
		super(getMissingKeyMessage(key));
		this.key = key;
	}

	public MissingMapKeyException(Throwable cause, String key) {
		super(getMissingKeyMessage(key), cause);
		this.key = key;
	}

	static String getMissingKeyMessage(String key) {
		return "cant find the key : " + key;
	}

	public String getKey() {
		return key;
	}
	
	
}

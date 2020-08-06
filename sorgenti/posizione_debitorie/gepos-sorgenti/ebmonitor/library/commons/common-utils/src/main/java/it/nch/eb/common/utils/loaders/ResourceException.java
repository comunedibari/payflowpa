/**
 * Created on 02/ott/07
 */
package it.nch.eb.common.utils.loaders;

import java.io.IOException;


/**
 * @author gdefacci
 */
public class ResourceException extends RuntimeException {

	private static final long	serialVersionUID	= 666L;

	public ResourceException(String message) {
		super(message);
	}

	public ResourceException(IOException e) {
		super(e);
	}
	

}

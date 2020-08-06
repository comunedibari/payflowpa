/*
 * @(#)MalformedURNException.java 1.0alpha1 98/08/25
 *
 * Place VRMLC Copyright Notice Here
 */

package it.nch.eb.common.utils;

import java.io.IOException;

/**
 * Thrown to indicate that a malformed URN has occurred. Either illegal 
 * tokens were found in a specification string or the string could not be parsed. 
 *
 * @author  Aaron E. Walsh
 * @version 1.0alpha1 98/08/25
 * @since   VRML2.0 and JDK 1.2
 */

public class MalformedURNException extends IOException {
    private static final long	serialVersionUID	= 6601936316823433639L;

	/**
     * Constructs a <code>MalformedURNException</code> with no detail message.
     */
    public MalformedURNException() {
    }

    /**
     * Constructs a <code>MalformedURNException</code> with the 
     * specified detail message. 
     *
     * @param   msg   the detail message.
     */
    public MalformedURNException(String msg) {
	super(msg);
    }
}

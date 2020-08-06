package it.nch.fwk.fo.web.resources.messageresources;

/**
 * Copyright 2005 Allen L. Fogleson
 * 
 * @author FOGLESONA
 * 
 * An Exception class if you care to catch it explicitly not likely but its
 * there.
 * 
 * This code is released under the GPL license version 2.
 * 
 * The GPL License can be viewed at: http://www.gnu.org/copyleft/gpl.html
 * 
 * 
 */
public class MessageResourcesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4698532474692914544L;

	public MessageResourcesException(String msg) {
		super(msg);
	}

	public MessageResourcesException(Exception e) {
		super(e.getLocalizedMessage());
	}
}
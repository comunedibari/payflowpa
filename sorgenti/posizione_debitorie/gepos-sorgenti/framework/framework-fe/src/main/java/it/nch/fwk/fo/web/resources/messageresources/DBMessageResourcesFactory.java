package it.nch.fwk.fo.web.resources.messageresources;

import it.nch.fwk.fo.util.Tracer;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.PropertyMessageResourcesFactory;

/**
 * Copyright 2005 Allen L. Fogleson
 * 
 * @author FOGLESONA
 * 
 * The simplest implementation of the MessageResourcesFactory
 * 
 * This code is released under the GPL license version 2.
 * 
 * The GPL License can be viewed at: http://www.gnu.org/copyleft/gpl.html
 * 
 * 
 */
public class DBMessageResourcesFactory extends PropertyMessageResourcesFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8382697041023542612L;

	private boolean returnNull;

	public MessageResources createResources(String config) {
		try {
			Tracer.info("DBMessageResourcesFactory","createResources","inizio",null);
			
			return new DBMessageResources(this, config, getReturnNull());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean getReturnNull() {
		return returnNull;
	}

	public void setReturnNull(boolean returnNull) {
		this.returnNull = returnNull;
	}
}
package it.nch.struts.util;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.PropertyMessageResourcesFactory;

/**
 * Implementazione custom di MessageResourcesFactory per l'accesso al db
 * 
 */
public class DBMessageResourcesFactory extends PropertyMessageResourcesFactory {

	/* (non-Javadoc)
	 * @see org.apache.struts.util.MessageResourcesFactory#createResources(java.lang.String)
	 */
	public MessageResources createResources(String config) {
		DBMessageResources messageResources =
			new DBMessageResources(this, config, this.returnNull);
		return messageResources;
	}

}

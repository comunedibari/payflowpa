/**
 * Created on 01/ott/07
 */
package it.nch.eb.common.utils.loaders;

import java.util.Properties;


/**
 * @author gdefacci
 */
public interface PropertiesLoader {
	
	Properties load(String name) throws MissingResourceException;

}

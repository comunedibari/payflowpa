/**
 * Created on 16/nov/07
 */
package it.nch.eb.common.utils.map;

import java.util.Properties;


/**
 * @author gdefacci
 */
public interface PropertiesEnhancer {

	/**
	 * responsabilities:
	 * enhance/modify the given properies adding/removing/changig some entries
	 */
	Properties enhance(Properties original);
	
}

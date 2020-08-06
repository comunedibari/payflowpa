/**
 * 07/set/2009
 */
package it.tasgroup.dse.spring;

import it.tasgroup.dse.service.SpringNames.SystemProperties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @author gdefacci
 */
public class DsePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	
	public DsePropertyPlaceholderConfigurer() {
		if (System.getProperty(SystemProperties.ENV_CONFIG_LOCATION) == null) {
			System.setProperty(SystemProperties.ENV_CONFIG_LOCATION, SystemProperties.DEFAULT_CONFIG_LOCATION);
		}
	}
	
}

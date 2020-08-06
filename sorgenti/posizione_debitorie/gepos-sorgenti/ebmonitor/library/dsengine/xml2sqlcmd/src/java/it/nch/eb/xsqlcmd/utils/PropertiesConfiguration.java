/**
 * 03/ago/2009
 */
package it.nch.eb.xsqlcmd.utils;

import java.util.List;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import it.nch.eb.common.utils.loaders.ClasspathProperties;

/**
 * @author gdefacci
 */
public class PropertiesConfiguration extends ClasspathProperties{

	private static final long serialVersionUID = 8976612809404217144L;

	private PropertyPlaceholderConfigurer configurer;
	
	public PropertiesConfiguration(List propsList) {
		super(propsList);
		configurer.setProperties(this);
	}

	public PropertiesConfiguration(String location) {
		super(location);
		configurer.setProperties(this);
	}

	public PropertiesConfiguration(String[] locationsArrays) {
		super(locationsArrays);
		configurer.setProperties(this);
	}

	
}

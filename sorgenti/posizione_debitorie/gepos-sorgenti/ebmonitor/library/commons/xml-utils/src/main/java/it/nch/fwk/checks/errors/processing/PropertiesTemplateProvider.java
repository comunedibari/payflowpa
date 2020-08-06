/**
 * 11/feb/2010
 */
package it.nch.fwk.checks.errors.processing;

import java.util.Map;
import java.util.Properties;

/**
 * @author gdefacci
 */
public class PropertiesTemplateProvider implements ITemplateProvider {

	private Properties properties;
	
	public PropertiesTemplateProvider(Properties properties) {
		this.properties = properties;
	}

	public String getTemplateValue(String id, Map templateContext) {
		return properties.getProperty(id);
	}

}

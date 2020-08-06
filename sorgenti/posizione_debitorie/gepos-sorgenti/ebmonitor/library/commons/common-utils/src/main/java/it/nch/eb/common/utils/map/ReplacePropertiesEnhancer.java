/**
 * Created on 16/nov/07
 */
package it.nch.eb.common.utils.map;

import it.nch.eb.common.utils.loaders.PropertiesUtils;

import java.util.Properties;


/**
 * @author gdefacci
 */
public class ReplacePropertiesEnhancer implements PropertiesEnhancer{

	protected final Properties[] toReplaceProperties;
	
	public ReplacePropertiesEnhancer(Properties properties) {
		this(new Properties[] { properties 	});
	}
	
	public ReplacePropertiesEnhancer(Properties[] toReplaceOnEnhance) {
		toReplaceProperties = toReplaceOnEnhance;
	}
	
	public Properties enhance(Properties original) {
		Properties result = new Properties();
		for (int i = 0; i < toReplaceProperties.length; i++) {
			Properties prop = toReplaceProperties[i];
			PropertiesUtils.mergeProperties(result, prop);
		}
		return result;
	}

}

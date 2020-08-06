/**
 * Created on 16/nov/07
 */
package it.nch.eb.common.utils.map;

import it.nch.eb.common.utils.loaders.PropertiesUtils;

import java.util.Properties;


/**
 * @author gdefacci
 */
public class MergePropertiesEnhancer implements PropertiesEnhancer{

	protected final Properties[] toMergeProperties;
	
	public MergePropertiesEnhancer(Properties properties) {
		this(new Properties[] { properties 	});
	}
	
	public MergePropertiesEnhancer(Properties[] toMergeOnEnhance) {
		toMergeProperties = toMergeOnEnhance;
	}
	
	public Properties enhance(Properties original) {
		Properties result = PropertiesUtils.shallowCloneProperties(original) ;
		for (int i = 0; i < toMergeProperties.length; i++) {
			Properties prop = toMergeProperties[i];
			PropertiesUtils.mergeProperties(result, prop);
		}
		return result;
	}

}

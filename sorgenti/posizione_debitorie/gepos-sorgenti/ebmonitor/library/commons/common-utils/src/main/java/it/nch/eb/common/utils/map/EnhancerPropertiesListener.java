/**
 * Created on 16/nov/07
 */
package it.nch.eb.common.utils.map;

import java.util.Properties;


/**
 * @author gdefacci
 */
public abstract class EnhancerPropertiesListener implements PropertiesEnhancer{
	
	private PropertiesEnhancer	enhancer;

	public EnhancerPropertiesListener(PropertiesEnhancer enhancer) {
		this.enhancer = enhancer;
	}
	
	public PropertiesEnhancer onPropertySet(Object key, Object value) {
		if (key instanceof String && value instanceof String) {
			if (triggerEnhance((String) key, (String)value)) return this;
		}
		return null;
	}
	
	public Properties enhance(Properties original) {
		return enhancer.enhance(original);
	}

	protected abstract boolean triggerEnhance(String key, String value);

}

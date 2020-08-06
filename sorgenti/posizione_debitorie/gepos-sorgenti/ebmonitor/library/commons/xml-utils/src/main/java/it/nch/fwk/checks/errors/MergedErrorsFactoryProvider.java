/**
 * Created on 24/nov/08
 */
package it.nch.fwk.checks.errors;

import it.nch.eb.common.utils.loaders.PropertiesUtils;
import it.nch.eb.common.utils.loaders.ResourceLoaders;

import java.util.Iterator;
import java.util.Properties;


/**
 * @author gdefacci
 */
public class MergedErrorsFactoryProvider extends ErrorsFactoryProvider {
	
	public MergedErrorsFactoryProvider(String[] classpathLocations) {
		super(PropertiesUtils.mergePropertiesLocationList(classpathLocations, ResourceLoaders.CLASSPATH));
	}
	
	public MergedErrorsFactoryProvider(Properties[] props) {
		super(PropertiesUtils.mergeProperties(props));
	}

	protected void registerErrors(BaseErrorMessageFactory errorsFactory) {
		Properties props = this.getErrorProperties();
		for (Iterator it = props.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			errorsFactory.registerElementProcessor(key, 0, props.getProperty(key));
		}
	}

}

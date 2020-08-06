/**
 * Created on 11/nov/08
 */
package it.nch.fwk.checks.errors;

import it.nch.eb.common.utils.loaders.ResourceLoaders;


/**
 * @author gdefacci
 */
public abstract class ClasspathErrorsFactoryProvider extends ErrorsFactoryProvider {

	public ClasspathErrorsFactoryProvider(String location) {
		super(ResourceLoaders.CLASSPATH.loadInputStream(location));
	}
	
}

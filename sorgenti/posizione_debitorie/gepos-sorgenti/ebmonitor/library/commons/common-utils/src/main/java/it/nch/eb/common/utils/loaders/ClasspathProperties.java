/**
 * Created on 28/set/07
 */
package it.nch.eb.common.utils.loaders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;



/**
 * @author gdefacci
 */
public class ClasspathProperties extends PropertiesDelegate {

	public ClasspathProperties(String location) {
		super(ResourceLoaders.PROPERTIES_CLASSPATH.load(location));
	}
	
	public ClasspathProperties(List/*<String>*/ propsList) {
		super(mergePropertiesLocations(propsList));
	}
	
	private static Properties mergePropertiesLocations(List propsList) {
		List/*<Properties>*/ props = new ArrayList/*<Properties>*/();
		for (Iterator it = propsList.iterator(); it.hasNext();) {
			String location = (String) it.next();
			props.add(new ClasspathProperties(location));
		}
		return mergePropertiesList(props);
	}

	public ClasspathProperties(String[] locationsArrays) {
		super(mergePropertiesLocationList(locationsArrays, ResourceLoaders.PROPERTIES_CLASSPATH ));
	}

	private static final long	serialVersionUID	= -5160061441023403034L;
}

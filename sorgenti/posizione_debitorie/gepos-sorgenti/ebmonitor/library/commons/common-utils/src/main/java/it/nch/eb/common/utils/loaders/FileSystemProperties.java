/**
 * Created on 01/ott/07
 */
package it.nch.eb.common.utils.loaders;

import java.util.List;
import java.util.Properties;



/**
 * @author gdefacci
 */
public class FileSystemProperties extends PropertiesDelegate {

	private static final long	serialVersionUID	= 1L;
	
	public FileSystemProperties(Properties delegate) {
		super(delegate);
	}
	public FileSystemProperties(String location) {
		super(ResourceLoaders.PROPERTIES_FILESYSTEM.load(location));
	}
	public FileSystemProperties(List/*<Properties>*/ propsList) {
		super(mergePropertiesList(propsList));
	}
	public FileSystemProperties(String[] locationsArrays) {
		super(mergePropertiesLocationList(locationsArrays, ResourceLoaders.PROPERTIES_FILESYSTEM));
	}

}

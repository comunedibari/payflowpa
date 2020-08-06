/**
 * Created on 01/ott/07
 */
package it.nch.eb.common.utils.loaders;

/**
 * @author gdefacci
 */
public interface ResourceLoaders {

	ClasspathLoader		CLASSPATH				= new ClasspathLoader();
	FileSystemLoader	FILESYSTEM				= new FileSystemLoader();

	ClasspathLoader		PROPERTIES_CLASSPATH	= new ClasspathLoader();
	FileSystemLoader	PROPERTIES_FILESYSTEM	= new FileSystemLoader();

}

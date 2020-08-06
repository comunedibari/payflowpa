/**
 * Created on 07/nov/08
 */
package it.nch.eb.common.utils.loaders;

import java.io.Reader;
import java.net.URL;


/**
 * @author gdefacci
 */
public interface ReaderLoader extends StreamsResourcesLoader {
	
	Reader loadReader(String name);
	InputsFactory readerFactory(String name);
	URL getURL(String name);

}

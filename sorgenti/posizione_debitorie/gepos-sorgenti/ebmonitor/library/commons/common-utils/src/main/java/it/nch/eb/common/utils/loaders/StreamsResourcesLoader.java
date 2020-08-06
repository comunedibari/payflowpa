/**
 * Created on 01/ott/07
 */
package it.nch.eb.common.utils.loaders;

import java.io.InputStream;


/**
 * @author gdefacci
 */
public interface StreamsResourcesLoader {

	InputStream loadInputStream(String name);
	
}

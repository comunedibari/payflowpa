/**
 * Created on 16/ott/08
 */
package it.nch.eb.common.utils.loaders;

import java.io.Reader;


/**
 * @author gdefacci
 */
public interface ReaderFactory {
	
	public String getName();
	Reader createReader();

}

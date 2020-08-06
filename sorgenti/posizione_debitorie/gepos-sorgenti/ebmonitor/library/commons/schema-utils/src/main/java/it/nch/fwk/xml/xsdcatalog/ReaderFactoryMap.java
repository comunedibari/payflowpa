/**
 * 27/gen/2010
 */
package it.nch.fwk.xml.xsdcatalog;

import it.nch.eb.common.utils.loaders.InputStreamFactory;
import it.nch.eb.common.utils.loaders.ReaderFactory;

import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gdefacci
 */
public class ReaderFactoryMap {
	
	private final Map/*<String, ReaderFactory>*/ uriReadersMap = new HashMap();
	
	public void put(String uri, InputStreamFactory readerFactory) {
		this.uriReadersMap.put(uri, readerFactory);
	}
	
	public InputStream get(String uri) {
		InputStreamFactory rf = (InputStreamFactory) this.uriReadersMap.get(uri);
		if (rf == null) return null;
		else return rf.createStream();
	}

}

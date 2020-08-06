/**
 * Created on 01/ott/08
 */
package it.nch.eb.common.utils.loaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;


/**
 * @author gdefacci
 */
public abstract class BaseResourcesLoader implements ReaderLoader {

	public Reader loadReader(String name) {
		return new InputStreamReader(loadInputStream(name));
	}
	
	public Reader loadReader(File file) {
		return new InputStreamReader(loadInputStream(file));
	}


//	public Writer loadWriter(String name) {
//		return new OutputStreamWriter(loadOutputStream(name));
//	}
	
	public Properties load(File f) {
		return load(loadInputStream(f));
	}

	public FileInputStream loadInputStream(File f) {
		try {
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			throw new MissingResourceException(e.getMessage(), f.getAbsolutePath());
		}
	}
	
	public Properties load(String location) {
		InputStream is = loadInputStream(location);
		try {
			if (is==null) throw new MissingResourceException("cant find the properties file " + location, location);
			return load(is);
		} catch (Exception e) {
			throw new MissingResourceException(e.getMessage(), location);
		}
	}
	
	public Properties load(InputStream is) {
		try {
			Properties res = null;
			if (is!=null) {
				res = new Properties();
				res.load(is);
			}
			return res;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}

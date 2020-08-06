/**
 * Created on 16/nov/07
 */
package it.nch.eb.common.utils.loaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;


/**
 * @author gdefacci
 */
public class PropertiesUtils {

	public static Properties mergePropertiesList(Collection/*<Properties>*/ propsList) {
		Properties resProps = new Properties();
		for (Iterator it = propsList.iterator(); it.hasNext();) {
			Properties props = (Properties) it.next();
			mergeProperties(resProps, props);
		}
		return resProps;
	}
	
	public static Properties mergeProperties(Properties[] propsList) {
		Properties resProps = new Properties();
		for (int i = 0; i < propsList.length; i++) {
			Properties props = propsList[i];
			mergeProperties(resProps, props);
		}
		return resProps;
	}

	public static void mergeProperties(Properties resProps, Properties props) {
		resProps.putAll(props);
	}
	
	/**
	 * WARNING: not a deep clone!!!!
	 */
	public static Properties shallowCloneProperties(Properties toMerge) {
		Properties props = new Properties();
		props.putAll(toMerge);
		return props;
	}

	public static Properties mergePropertiesLocationList(String[] locationsArray, PropertiesLoader loader) {
		Properties resProps = new Properties();
		for (int i = 0; i < locationsArray.length; i++) {
			String location = locationsArray[i];
			Properties pr = loader.load(location);
			if (pr==null) {
				throw new RuntimeException("properties at location [" +  location + " ] are null");
			}
			mergeProperties(resProps, pr);
		}
		return resProps;
	}

	public static void useAsSystemProperties(Properties props) {
		for (Iterator it = props.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			String value = props.getProperty(key);
			System.setProperty(key, value);
		}
	}
	
	public static void store(Properties props, File f, String hdr) {
		try {
			OutputStream is = new FileOutputStream(f);
			store(props, is, hdr);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("error writing file " + f.getAbsolutePath());
		}
	}
	
	public static void store(Properties props, OutputStream is, String hdr) {
		try {
			props.store(is, hdr);
		} catch (IOException e) {
			throw new RuntimeException("error writing properties ", e);
		}	
	}
	
	public static Properties load(InputStream is) {
		try {
			Properties props = new Properties();
			props.load(is);
			return props;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Properties[] classpathProperties(String[] locations) {
		Properties[] props = new Properties[locations.length];
		for (int i = 0; i < locations.length; i++) {
			String classpathLoc = locations[i];
			props[i] = 	ResourceLoaders.CLASSPATH.load(classpathLoc);
		}
		return props;
	}
	
}

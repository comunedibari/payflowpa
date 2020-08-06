package it.nch.eb.common.utils.conf;

import it.nch.eb.common.utils.resource.ResourcesUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

import org.slf4j.Logger;

public class ResourceLoader {

	private String rootDir = null;
	
	private static Logger log = ResourcesUtil.createLogger(ResourceLoader.class);
	
	public ResourceLoader(String rootPropertyName) throws Exception {
		rootDir = loadSystemProperty(rootPropertyName, false);
	}
	public ResourceLoader() {
	}
	
	public String loadSystemProperty(String propertyName, boolean mandatory) throws Exception{		
		String propertyValue = System.getProperty(propertyName);
		if ( propertyValue == null && mandatory )
			throw new Exception("FAIL TO LOAD SYSTEM PROPERTY ["+propertyName+"]");
		return propertyValue;
	}	

	public Properties load(String resourcePath, boolean mandatory) throws Exception {
		Properties p = new Properties();
		try {
			p.load(loadResurce(resourcePath));
		} catch (Exception e) {
			log.error("error loading resource " + resourcePath, e);
			if (mandatory)
				throw e;
			p = null;
		}	
		return p;
	}
	public Properties load(String resourcePath) throws Exception {
		return load(resourcePath, false);
	}
	
	private InputStream loadResurce(String resourcePath) throws FileNotFoundException{
		
		InputStream is = null;
		
		is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath); 
		
		if( is == null){
			if( rootDir == null)
				throw new FileNotFoundException("CAN'T FIND RESOURCE PATH "+resourcePath+" ON CLASSPATH. YOU MUST SET SYSTEM PROPERTY "+rootDir+ " TO LOAD RESOUCE FROM FILESYSTEM"); 
				
			String filePath = rootDir + "/" +resourcePath;
			
			is = new FileInputStream(getFile(filePath)); 	
		}
		
		return is;
	}

	public  String getRootDir() {
		return rootDir;
	}
	
	/**
	 * cut n' copy n' paste from org.springframework.util
	 * we dont want the dependecy at this level
	 */
	
	public static final String CLASSPATH_URL_PREFIX = "classpath:";
	/**
	 * Resolve the given resource location to a <code>java.io.File</code>,
	 * i.e. to a file in the file system.
	 * <p>Does not check whether the fil actually exists; simply returns
	 * the File that the given location would correspond to.
	 * @param resourceLocation the resource location to resolve: either a
	 * "classpath:" pseudo URL, a "file:" URL, or a plain file path
	 * @return a corresponding File object
	 * @throws FileNotFoundException if the resource cannot be resolved to
	 * a file in the file system
	 */
	public static File getFile(String resourceLocation) throws FileNotFoundException {
		if (resourceLocation==null) throw new IllegalStateException("cant get a file for a null resource");
		if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
			String path = resourceLocation.substring(CLASSPATH_URL_PREFIX.length());
			String description = "class path resource [" + path + "]";
			URL url = ResourcesUtil.getDefaultClassLoader().getResource(path);
			if (url == null) {
				throw new FileNotFoundException(
						description + " cannot be resolved to absolute file path " +
						"because it does not reside in the file system");
			}
			return getFile(url, description);
		}
		try {
			// try URL
			return getFile(new URL(resourceLocation));
		}
		catch (MalformedURLException ex) {
			// no URL -> treat as file path
			return new File(resourceLocation);
		}
	}
	
	/**
	 * Resolve the given resource URL to a <code>java.io.File</code>,
	 * i.e. to a file in the file system.
	 * @param resourceUrl the resource URL to resolve
	 * @return a corresponding File object
	 * @throws FileNotFoundException if the URL cannot be resolved to
	 * a file in the file system
	 */
	public static File getFile(URL resourceUrl) throws FileNotFoundException {
		return getFile(resourceUrl, "URL");
	}

	/**
	 * Resolve the given resource URL to a <code>java.io.File</code>,
	 * i.e. to a file in the file system.
	 * @param resourceUrl the resource URL to resolve
	 * @param description a description of the original resource that
	 * the URL was created for (for example, a class path location)
	 * @return a corresponding File object
	 * @throws FileNotFoundException if the URL cannot be resolved to
	 * a file in the file system
	 */
	public static final String URL_PROTOCOL_FILE = "file";
	public static File getFile(URL resourceUrl, String description) throws FileNotFoundException {
		if (!URL_PROTOCOL_FILE.equals(resourceUrl.getProtocol())) {
			throw new FileNotFoundException(
					description + " cannot be resolved to absolute file path " +
					"because it does not reside in the file system: " + resourceUrl);
		}
		return new File(URLDecoder.decode(resourceUrl.getFile()));
	}


}

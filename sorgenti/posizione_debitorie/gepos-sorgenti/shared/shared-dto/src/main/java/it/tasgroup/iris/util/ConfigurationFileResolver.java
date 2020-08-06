package it.tasgroup.iris.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * <p>Helper class aimed at finding configuration files by mean of file system and / or classpath search.</p>
 * 
 * <p>The search through file system takes place if the system property <i>SYSTEM_PROPERTY_CONFIGURATION_PATH</i>  
 * is defined, otherwise the classpath search will be used.</p> 
 * 
 * @author tasgroup
 *
 */

public class ConfigurationFileResolver {
	private static final String FORWARD_SLASH = "/";
	
	/**
	 * <p>Searches the file that corresponds to filename through file system, if the configuration 
	 * path system property is defined, and through class path, if file name hasn't been found 
	 * in file system search and caller class parameter (optional) is specified.</p> 
	 * 
	 * @param callerClass class that calls this method. It's used only for searching through classpath
	 *        using the caller class classloader, in case file system search didn't find any file 
	 * @param fileName name of the file to search: use the char / as separator for both file system 
	 *        and classpath searches
	 * @return the {@link File} object corresponding to fileName or null if file hasn't been found
	 */
	public static <T> File getFile(Class<T> callerClass, String fileName) {
		// Sanity check
		if (fileName == null || fileName.trim().isEmpty()) throw new IllegalArgumentException("fileName");

		// First check configuration directory pointed by system property, if any
		String configDir = getConfigurationDir();
		if (configDir != null) {
			File configFile = new File (configDir.concat("/" + fileName));
			if (configFile.exists() && configFile.isFile()) {
				return configFile;
			} 
		}
		// Sanity check
		if (callerClass == null) return null;
		// Then try to get file through classloader
		URL fileAsUrl = callerClass.getResource(fileName);
		if (fileAsUrl == null) {
			fileAsUrl = callerClass.getResource(FORWARD_SLASH + fileName);
		}
		if (fileAsUrl != null) {
			try {
				return new File(fileAsUrl.toURI());
			} catch (URISyntaxException e) {
				// Should never happen, because resource is not null
				return null;
			}
		}
		return null;
	}
	
	/**
	 * <p>Searches the file that corresponds to filename through file system, if the configuration 
	 * path system property is defined, and through class path, if file name hasn't been found 
	 * in file system search and caller class parameter (optional) is specified.</p> 
	 * 
	 * @param callerClass class that calls this method. It's used only for searching through classpath
	 *        using the caller class classloader, in case file system search didn't find any file 
	 * @param fileName name of the file to search: use the char / as separator for both file system 
	 *        and classpath searches
	 * @return the {@link InputStream} object corresponding to fileName or null if file hasn't been found
	 */
	public static <T> InputStream getInputStream(Class<T> callerClass, String fileName) {
		File file = getFile(callerClass, fileName);
		try {
			return file == null ? null : new FileInputStream(file);
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	private static String getConfigurationDir() {
		String configPath = System.getProperty("tas.properties.file.configuration");
		if (configPath != null && !configPath.trim().isEmpty()) {
			return configPath;
		} else {
			return null;
		}
	}
	
	private ConfigurationFileResolver(){}
}

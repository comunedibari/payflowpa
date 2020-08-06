/**
 * Created on 01/ott/07
 */
package it.nch.eb.common.utils.loaders;

import it.nch.eb.common.utils.StringUtils;

import java.io.InputStream;
import java.net.URL;


/**
 * @author gdefacci
 */
public class ClasspathLoader extends BaseResourcesLoader implements PropertiesLoader {
	
	private final String root;
	public ClasspathLoader() {
		this(null);
	}

	public ClasspathLoader(String root) {
		this.root = root;
	}

	public InputStream loadInputStream(String location) {
		if (location==null || location.length() < 1) throw new IllegalArgumentException("invalid classpath location \"" + location + "\"");
		
		InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(getLocation(location));
		if (resourceAsStream==null) throw new RuntimeException("cant find the classpath resource \"" + location + "\"");
		return resourceAsStream;
	}

	private String getLocation(String location) {
		if (root==null) return location;
		else return StringUtils.concatPaths(root, location);
	}

	public InputsFactory readerFactory(String loc) {
		return ReadersFactories.instance.classpath(loc);
	}
	
	public URL getURL(String location) {
		if (location==null || location.length() < 1) throw new IllegalArgumentException("invalid classpath location \"" + location + "\"");
		
		URL url = Thread.currentThread().getContextClassLoader().getResource(getLocation(location));
		if (url==null) throw new RuntimeException("cant find the classpath resource \"" + location + "\"");
		return url;
	}
	
}
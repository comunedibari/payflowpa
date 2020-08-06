/**
 * Created on 01/ott/07
 */
package it.nch.eb.common.utils.loaders;

import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.common.utils.resource.ResourcesUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;


/**
 * @author gdefacci
 */
public class FileSystemLoader extends BaseResourcesLoader implements PropertiesLoader{
	
	private final File root;
	
	public FileSystemLoader() {
		this(null);
	}
	
	public FileSystemLoader(File root) {
		this(root, false);
	}
	
	public FileSystemLoader(File root, boolean create) {
		this.root = root;
		if (root!=null) { 
			boolean rootExists = this.root.exists();
			if (rootExists && !root.isDirectory()) {
				throw new IllegalStateException(root + " is not a folder");
			}
			if (create && !rootExists) {
				createDirs(root);
			}
		}
	}

	private void createDirs(File root) {
		try {
			root.mkdirs();
		} catch (Exception e) {
			throw new RuntimeException("error creating folder " + root.getAbsolutePath(), e);
		}
	}

	public Properties load(String location) {
		InputStream is = null;
		try {
			File f = getFileAtLocation(location);
			if (!f.exists()) throw new IllegalStateException("the file " +  f.getAbsolutePath() + " does not exists");
			is = new FileInputStream(f);
			Properties res = null;
			res = new Properties();
			res.load(is);
			return res;
		} catch (IOException e) {
			throw new MissingResourceException(e, location);
		} finally {
			ResourcesUtil.close(is);
		}
	}

	protected File getFileAtLocation(String location) {
		if (location==null) throw new NullPointerException();
		if (root==null) return new File(location);
		else return new File(root, location);
	}

	public InputStream loadInputStream(String location) {
		try {
			File f = getFileAtLocation(location);
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			throw new MissingResourceException(e, location);
		}
	}

	public InputsFactory readerFactory(String name) {
		String nm = (root == null) ? name : StringUtils.concatPaths(root.getAbsolutePath(), name);
		return ReadersFactories.instance.fileSystem(nm);
	}

	public URL getURL(String name) {
		File f = getFileAtLocation(name);
		try {
			return f.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new RuntimeException("error retriving url for file " + f, e);
		}
	}

}

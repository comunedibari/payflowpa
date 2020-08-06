/**
 * Created on 28/ago/07
 */
package it.nch.eb.common.utils.resource;


import it.nch.eb.common.utils.loaders.ClasspathLoader;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * TODO: converter is a too much inflationated name
 * @author gdefacci
 */
public class CharsetConverter {

	public static final String DEFAULT_CHARSET_NAME = null;
	
//	private static final org.slf4j.Logger	logger = ResourcesUtil.createLogger(CharsetConverter.class);
	
	private String charsetName;

	public CharsetConverter(String charsetName) {
		this.charsetName = charsetName;
	}
	
	/**
	 * load the charset from a property file
	 */
	public CharsetConverter(String resourceName, String propertyName) {
		setCharset(resourceName,propertyName);
	}

	/**
	 * set the charset  looking for its value inside the properties file <code>CHARSET_FILE</code>
	 * under the key <code>CHARSET_PROPERTY</code>. The charset is then used in all byte to string and viceversa 
	 * conversions performed iniside methods of class ResourcesUtil.
	 */
	public void setCharset(String charsetFile , String charsetProperty) {
		Properties charsetProp = new Properties();
		try {
			ClasspathLoader clspthLodr = new ClasspathLoader();
			charsetProp.load(clspthLodr.loadInputStream(charsetFile));
			charsetName = (String) charsetProp.get(charsetProperty);
//			logger.info("using charset name " + charsetName);
		} catch (Exception e) {
//			logger.warn("erroR getting property \"" + charsetProperty==null?"null" :charsetProperty
//					+ "\" from file \"" + charsetFile==null?"null":charsetFile
//					+ "\" using \"" + DEFAULT_CHARSET_NAME + "\" charset");
			charsetName = DEFAULT_CHARSET_NAME;
		}
	}
	
	public String getCharsetName() {
		return charsetName;
	}

	public void setCharsetName(String charset_name) {
		charsetName = charset_name;
	}
	
	public String asString(byte[] bytes) {
		if (charsetName == null) return new String(bytes);
		try {
//			logger.info("converting stream using charset name " + charsetName);
			return new String(bytes, charsetName);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] asByteArray(String s) {
		if (charsetName==null) return s.getBytes();
		else try {
			return s.getBytes(charsetName);
		} catch (UnsupportedEncodingException e) {
//			usually a client cant manage e properly
			throw new RuntimeException(e);
		}
	}

//	public synchronized static Logger getLogger() {
//		if (logger == null) logger = ResourcesUtil.createLogger(CharsetConverter.class);
//		return logger;
//	}
}

/**
 * Created on 28/set/07
 */
package it.nch.eb.common.utils.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


/**
 * spring integration can be avoided if needed
 * @author gdefacci
 */
public abstract class PropertiesDelegate extends Properties {
	
	private static final long	serialVersionUID	= 1L;
	
	private Properties	delegate;

	public PropertiesDelegate(Properties delegate) {
		this.setDelegate(delegate);
		if (this.getDelegate()==null) throw new NullPointerException("the properties delegate is null");
	}
	
	/**
	 * @param delegate the delegate to set
	 */
	protected void setDelegate(Properties delegate) {
		this.delegate = delegate;
	}

	/**
	 * @return the delegate
	 */
	protected Properties getDelegate() {
		return delegate;
	}

	
	public static Properties mergePropertiesList(Collection/*<Properties>*/ propsList) {
		return PropertiesUtils.mergePropertiesList(propsList);
	}

	public static Properties mergePropertiesLocationList(String[] locationsArray, PropertiesLoader loader) {
		return PropertiesUtils.mergePropertiesLocationList(locationsArray, loader);
	}
	
	
	public synchronized Object setProperty(String key, String value) {
		return getDelegate().setProperty(key, value);
	}
	
	public synchronized Object put(Object key, Object value) {
		return getDelegate().put(key, value);
	}

	public synchronized void putAll(Map t) {
		getDelegate().putAll(t);
	}
	
	public synchronized String getProperty(String key, String defaultValue) {
		return getDelegate().getProperty(key, defaultValue);
	}

	public synchronized String getProperty(String key) {
		return getDelegate().getProperty(key);
	}
	
	public synchronized Object get(Object key) {
		return getDelegate().get(key);
	}

	public void list(PrintStream out) {
		getDelegate().list(out);
	}

	public void list(PrintWriter out) {
		getDelegate().list(out);
	}

	public synchronized void load(InputStream inStream) throws IOException {
		getDelegate().load(inStream);
	}

	public Enumeration propertyNames() {
		return getDelegate().propertyNames();
	}

	public synchronized void save(OutputStream out, String header) {
		try {
			getDelegate().store(out, header);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void store(OutputStream out, String header) throws IOException {
		getDelegate().store(out, header);
	}

	public synchronized void clear() {
		getDelegate().clear();
	}

	public synchronized Object clone() {
		return getDelegate().clone();
	}

	public synchronized boolean contains(Object value) {
		return getDelegate().contains(value);
	}

	public synchronized boolean containsKey(Object key) {
		return getDelegate().containsKey(key);
	}

	public boolean containsValue(Object value) {
		return getDelegate().containsValue(value);
	}

	public synchronized Enumeration elements() {
		return getDelegate().elements();
	}

	public Set entrySet() {
		return getDelegate().entrySet();
	}

	public synchronized boolean equals(Object o) {
		return getDelegate().equals(o);
	}

	public synchronized int hashCode() {
		return getDelegate().hashCode();
	}

	public synchronized boolean isEmpty() {
		return getDelegate().isEmpty();
	}

	public synchronized Enumeration keys() {
		return getDelegate().keys();
	}

	public Set keySet() {
		return getDelegate().keySet();
	}

	public synchronized Object remove(Object key) {
		return getDelegate().remove(key);
	}

	public synchronized int size() {
		return getDelegate().size();
	}

	public synchronized String toString() {
		return getDelegate().toString();
	}

	public Collection values() {
		return getDelegate().values();
	}

}

/**
 * Created on 01/ott/07
 */
package it.nch.eb.common.utils.map;

import it.nch.eb.common.utils.loaders.MissingMapKeyException;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;


/**
 * @author gdefacci
 */
public class TypedMap {

	private final Map content;
	private final DefaultsMap defaults;

	public TypedMap(Map content) {
		this.content = content;
		this.defaults = new DefaultsMap(new TreeMap());
	}
	
	public TypedMap(DefaultsMap defaults, Map content) {
		this.content = content;
		this.defaults = defaults;
	}
	
	public TypedMap useDefaults(DefaultsMap defaults) {
		return new TypedMap(defaults, this.content);
	}
	
	public Object get(String key) {
		Object res = content.get(key);
		if (res == null) {
			res = defaults.get(key);
		}
		return res;
	}
	
	public boolean containstKey(String key) {
		return get(key)!=null;
	}

//	TODO: for performance reason we could also expecet the map item is given. 
//	However we want to track the failing map key
	public void checkType(String key, Class klass) {
		Object obj = get(key);
		if (!klass.isAssignableFrom(obj.getClass())) 
		throw new IllegalStateException("map key [" + key + "] expeceting a " + klass.getName() + 
				" but got[" + obj + "]class[" + obj.getClass() + "]" );
	}
	
	public Integer getInt(String key) {
		return DataTypesUtils.getInteger(new Map[] {
			this.content,
			this.defaults.asMap(),
		}, key );
	}
	
	public String getString(String key) {
		Object obj = get(key);
		if (obj==null) return null;
		checkType(key, String.class);
		return (String) obj;
	}
	
	public Boolean getBoolean(String key) {
		Object obj = get(key);
		if (obj==null) return null;
		if (obj instanceof String) {
			return DataTypesUtils.getBooleanValue(key);
		}
		checkType(key, Boolean.class);
		return (Boolean) obj;
	}
	
	public byte[] getByteArray(String key) {
		Object obj = get(key);
		if (obj==null) return null;
		checkType(key, byte[].class);
		return (byte[]) obj;
	}
	
	/**
	 * returns a typed map where we get an MissingMapKeyException when trying to access 
	 * a key for which its value is null. MissingMapKeyException could be raised each time we use the method get(String)
	 */
	public MandatoryTypedMap mandatory() {
		return new MandatoryTypedMap(defaults, content);
	}
	
	public static class MandatoryTypedMap extends TypedMap {

		public MandatoryTypedMap(DefaultsMap defaults, Map content) {
			super(defaults, content);
		}

		public Object get(String key) {
			Object res = super.get(key);
			if (res==null) throw new MissingMapKeyException(key);
			return res;
		}
		
		/**
		 *	if key exist not a MissingMapKeyException is thrown using message <code>exceptionMsg</code>
		 */
		public boolean checkExistKey(String key, String exceptionMsg) {
			try {
				return containstKey(key);
			} catch (Exception e) {
				throw new MissingMapKeyException(exceptionMsg);
			}
		}
		
		public boolean checkExistKey(String key) {
			return checkExistKey(key, "the given key [" + key + "] does not exist");
		}
	}
	
	public void setDefaultIfNull(String key) {
		setDefaultForValue(key, null);
	}
	
	public void setDefaultForValue(String key, Object valueForWhichSetDeafult) {
		if (isKeyValue(key, valueForWhichSetDeafult)) {
			setDeafult(key);
		}
	}
	
	public void setDefaultForClass(String key, Class klass) {
		Object res = content.get(key);
		if (res != null) {
			if (klass.isAssignableFrom(res.getClass())) {
				setDeafult(key);
			}
		}
	}

	public void setDeafult(String key) {
		Object res = defaults.get(key);
		if (res==null) throw new MissingMapKeyException("defaults are missing the asked key [" + key + "]");
		else content.put(key, res);
		content.put(key, res);
	}
	
	public boolean isNull(String key) {
		Object res = get(key);
		if (res==null) return true;
		return false;
	}
	
	public boolean isType(String key, Class klass) {
		Object res = get(key);
		if (res==null) return false;
		if (klass.isAssignableFrom(res.getClass())) {
			return true;
		}
		return false;
	}
	
	public boolean isKeyValue(String key, Object obj) {
		Object res = content.get(key);
		if (res==null && obj==null) {
			return true;
		} else if (res != null) {
			if (res.equals(obj)) {
				return true;
			}
		}
		return false;
	}
	
	
	public Map asMegedMap() {
		if (defaults==null) return content;
		else {
			Map res = new TreeMap();
			res.putAll(this.defaults.getDefaults());
			res.putAll(content);
			return res;
		}
	}
	
	public Map asMap() {
		return content;
	}
	
	public Map asUnmodifiableMap() {
		return Collections.unmodifiableMap( content );
	}
	
}

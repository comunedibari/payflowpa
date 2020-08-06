/**
 * Created on 01/ott/07
 */
package it.nch.eb.common.utils.map;


import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;



/**
 * @author gdefacci
 */
public class DefaultsMap {
	
	private final Map defaults;
	
	public DefaultsMap(Map defaults) {
		this.defaults = defaults;
	}
	
	public DefaultsMap(Map[] maps) {
		this.defaults = mergeMaps(maps);
	}
	
	private Map mergeMaps(Map[] maps) {
		Map res = new TreeMap();
		for (int i = 0; i < maps.length; i++) {
			Map map = maps[i];
			res.putAll(map);
		}
		return res;
	}
	
	public Map getDefaults() {
		return defaults;
	}

	public TypedMap getNewTypedMap(Map map) {
		return new TypedMap(this, map);
	}

	public Object get(String key) {
		return defaults.get(key);
	}
	
	public Map asMap() {
		return Collections.unmodifiableMap(defaults);
	}
	
}

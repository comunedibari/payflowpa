/**
 * Created on 24/gen/08
 */
package it.nch.eb.flatx.flatmodel;

import java.util.Map;


/**
 * adapater for map-like data structures 
 * @author gdefacci
 */
public interface StringMap {
	
	String get(String key);
	
	class FromMapAdapter implements StringMap {

		protected final Map	map;
		
		public FromMapAdapter(Map map) {
			this.map = map;
		}


		public String get(String key) {
			return (String) map.get(key);
		}
		
	}

}

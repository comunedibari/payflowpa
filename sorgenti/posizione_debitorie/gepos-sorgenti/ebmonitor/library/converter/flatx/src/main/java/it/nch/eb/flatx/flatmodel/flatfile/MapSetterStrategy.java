/**
 * Created on 12/feb/08
 */
package it.nch.eb.flatx.flatmodel.flatfile;

import it.nch.eb.common.utils.StringUtils;

import java.util.Map;


/**
 * @author gdefacci
 */
public class MapSetterStrategy implements SetterStrategy {

	public void set(Object object, String name, Object value) {
		if (!(object instanceof Map)) 
			throw new IllegalStateException(StringUtils.getSimpleName(MapSetterStrategy.class) 
					+ " can set value only on map instances, but got an [" 
					+ StringUtils.getSimpleName(object.getClass()) + "] instance ");
		setMap((Map) object, name, value);
	}
	
	public void setMap(Map map, String name, Object value) {
		map.put(name, value);
	}

}

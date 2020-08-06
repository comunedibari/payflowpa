/**
 * Created on 12/feb/08
 */
package it.nch.eb.flatx.flatmodel.flatfile;

import java.util.List;

import it.nch.eb.common.utils.ReflectionUtils;
import it.nch.eb.common.utils.ReflectionUtils.Setter;
import it.nch.eb.flatx.exceptions.PropertyAccessException;


/**
 * @author gdefacci
 */
public class ReflectionSetterStrategy implements SetterStrategy {

	public void set(Object object, String name, Object value) {
		try {
			Setter setter = ReflectionUtils.getSetterForProperty(object, name, getClass(value));
			setter.setValue(value);
		} catch (Exception e) {
//			throw new RuntimeException("error while setting property [" +  name 
//					+ "] value [" + value
//					+ "] on object " + object, e);
			throw new PropertyAccessException("error setting property " + name, e, name, object, value);
		}
	}

	private Class getClass(Object value) {
		if (value instanceof List) return List.class;
		return value.getClass();
	}
	
	

}

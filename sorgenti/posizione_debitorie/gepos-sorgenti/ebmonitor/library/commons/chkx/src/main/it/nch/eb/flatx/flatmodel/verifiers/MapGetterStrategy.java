/**
 * Created on 22/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;


import java.util.Map;


/**
 * @author gdefacci
 */
public class MapGetterStrategy implements GetterStrategy {

	public Object getValueFrom(Object getterContainer, String name) {
		if (!(getterContainer instanceof Map)) 
			throw new ClassCastException("expeceting java.util.Map instance but got " + getterContainer.getClass() );
		return ((Map)getterContainer).get(name);
	}

}

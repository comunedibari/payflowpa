/**
 * Created on 22/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;

import it.nch.eb.common.utils.ReflectionUtils;


/**
 * @author gdefacci
 */
public class ReflectionGetterStrategy implements GetterStrategy {
	
	public static final ReflectionGetterStrategy instance = new ReflectionGetterStrategy(); 

	public Object getValueFrom(Object obj, String name) {
		return ReflectionUtils.getGetterValue(obj, name);
	}

}

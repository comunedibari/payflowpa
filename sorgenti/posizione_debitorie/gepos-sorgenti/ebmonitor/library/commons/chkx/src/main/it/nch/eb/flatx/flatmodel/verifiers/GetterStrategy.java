/**
 * Created on 22/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;


/**
 * strategy to get a property value from a object using a string as the model which indicate the property to get
 * @author gdefacci
 */
public interface GetterStrategy {

	Object getValueFrom(Object getterContainer, String name);
}

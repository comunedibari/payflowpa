/**
 * Created on 14/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;



/**
 * represent a contextualized error (an error
 * @author gdefacci
 */
public interface BeanPropertyError extends IBeanError {

	Object getContainerValue();
	
	String getPropertyName();
	
	IBeanError getCause();
	
}

/**
 * Created on 25/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;



/**
 * @author gdefacci
 */
public interface DomainErrorsFactory {

	IBeanError createDecoratedError(Object from, String propertyName, Object propertyValue,
			IBeanError qualifiedError);
}

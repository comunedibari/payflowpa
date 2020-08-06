/**
 * Created on 28/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;

import it.nch.fwk.checks.errors.QualifiedError;


/**
 * @author gdefacci
 */
public interface IBeanError extends QualifiedError {
	
	Object getValue();
	Object getExpectedValue();
	Exception getException();

}

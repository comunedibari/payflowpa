/**
 * Created on 25/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;

import it.nch.fwk.checks.errors.QualifiedError;


/**
 * abstract a single check that can return a single error or not. if check is sucessfull null (== no error) is expeceted
 * @author gdefacci
 */
public interface SimpleAtomicVerifier {

	QualifiedError verify(Object object);
}

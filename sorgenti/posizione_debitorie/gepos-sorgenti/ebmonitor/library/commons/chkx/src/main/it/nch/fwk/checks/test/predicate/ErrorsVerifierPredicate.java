/**
 * 31/lug/2009
 */
package it.nch.fwk.checks.test.predicate;

import it.nch.fwk.checks.errors.QualifiedError;

/**
 * @author gdefacci
 */
public interface ErrorsVerifierPredicate {
	
	boolean verify(QualifiedError[] errs);
	
	void describeTo(StringBuffer sb);

}

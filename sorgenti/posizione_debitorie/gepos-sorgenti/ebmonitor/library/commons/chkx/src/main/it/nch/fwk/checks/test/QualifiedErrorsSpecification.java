/**
 * 31/lug/2009
 */
package it.nch.fwk.checks.test;


import it.nch.fwk.checks.test.predicate.ErrorsVerifierPredicate;

import java.util.Map;

/**
 * @author gdefacci
 */
public class QualifiedErrorsSpecification {
	
	private final Map errorsExpectations;
	
	public QualifiedErrorsSpecification(Map errorsExpectations) {
		this.errorsExpectations = errorsExpectations;
	}
	
	public QEExpectations expect() {
		return  new QEExpectations(errorsExpectations);
	}
	
	public Map getErrorsExpectations() {
		return errorsExpectations;
	}
	
	public ErrorsVerifierPredicate getVerifier(String nm) {
		return (ErrorsVerifierPredicate) errorsExpectations.get(nm);
	}

}

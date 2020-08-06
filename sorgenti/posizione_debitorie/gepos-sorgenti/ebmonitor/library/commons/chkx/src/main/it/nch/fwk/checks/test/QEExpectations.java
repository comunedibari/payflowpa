/**
 * 31/lug/2009
 */
package it.nch.fwk.checks.test;

import it.nch.fwk.checks.test.predicate.ErrorsVerifierPredicate;

import java.util.Map;

public class QEExpectations {
	private final Map/*<String, ErrorsVerifier>*/ verifiers;
	public QEExpectations(Map verifiers) {
		this.verifiers = verifiers;
	}
	public QEExpectations errorsFor(String name, ErrorsVerifierPredicate verifier) {
		Object old = this.verifiers.put(name, verifier);
		if (old!=null) {
			throw new IllegalStateException(name + " has been alredy specified");
		}
		return this;
	}
	public ErrorsVerifierPredicate verifier(String name) {
		return (ErrorsVerifierPredicate) this.verifiers.get(name);
	}
}
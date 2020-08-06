/**
 * 07/ago/2009
 */
package it.nch.fwk.checks.test.predicate;

import it.nch.fwk.checks.errors.QualifiedError;

/**
 * @author gdefacci
 */
public class ContainErrorWithId implements ErrorsVerifierPredicate{
	private final String id;

	public ContainErrorWithId(String id) {
		this.id = id;
	}

	public void describeTo(StringBuffer sb) {
		sb.append("contains error with id ");
		sb.append(id);
	}

	public boolean verify(QualifiedError[] errs) {
		for (int i = 0; i < errs.length; i++) {
			QualifiedError qualifiedError = errs[i];
			if (qualifiedError.getErrorId().equals(id)) return true;
		}
		return false;
	}
	
	

}

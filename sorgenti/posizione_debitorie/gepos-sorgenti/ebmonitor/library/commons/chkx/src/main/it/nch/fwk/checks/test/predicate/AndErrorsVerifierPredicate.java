/**
 * 07/ago/2009
 */
package it.nch.fwk.checks.test.predicate;

import it.nch.fwk.checks.errors.QualifiedError;

/**
 * @author gdefacci
 */
public class AndErrorsVerifierPredicate implements ErrorsVerifierPredicate {
	
	private  final ErrorsVerifierPredicate[] predicates;

	public AndErrorsVerifierPredicate(ErrorsVerifierPredicate[] predicates) {
		this.predicates = predicates;
	}
	
	public static AndErrorsVerifierPredicate and(ErrorsVerifierPredicate p1, ErrorsVerifierPredicate p2) {
		return new AndErrorsVerifierPredicate(new ErrorsVerifierPredicate[] { p1, p2});
	}

	public void describeTo(StringBuffer sb) {
		int len = this.predicates.length;
		if (len > 0) {
			sb.append("(");
			this.predicates[0].describeTo(sb);
		
			for (int i = 1; i < len; i++) {
				ErrorsVerifierPredicate pred = this.predicates[i];
				sb.append(") and (");
				pred.describeTo(sb);
			}		
			sb.append(")");
		} else {
			sb.append("always true");
		}
	}

	public boolean verify(QualifiedError[] errs) {
		boolean res = true;
		for (int i = 0; i < this.predicates.length && res; i++) {
			ErrorsVerifierPredicate pred = this.predicates[i];
			res = pred.verify(errs);
		}
		return res;
	}

}

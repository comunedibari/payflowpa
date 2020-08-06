/**
 * 07/ago/2009
 */
package it.nch.fwk.checks.test;

import it.nch.fwk.checks.test.predicate.AndErrorsVerifierPredicate;
import it.nch.fwk.checks.test.predicate.ContainErrorWithId;
import it.nch.fwk.checks.test.predicate.ErrorsVerifierPredicate;

/**
 * @author gdefacci
 */
public class ErrPredicates {

	public static ErrorsVerifierPredicate errorWithId(String id) {
		return new ContainErrorWithId(id);
	}

	public static ErrorsVerifierPredicate errorsWithIds(String[] ids) {
		ContainErrorWithId[] preds = new ContainErrorWithId[ids.length];
		for (int i = 0; i < ids.length; i++) {
			String errId = ids[i];
			preds[i] = new ContainErrorWithId(errId);
		}
		return new AndErrorsVerifierPredicate(preds);
	}

}

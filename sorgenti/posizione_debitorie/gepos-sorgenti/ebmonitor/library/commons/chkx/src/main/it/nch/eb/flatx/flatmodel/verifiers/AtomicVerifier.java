/**
 * Created on 25/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;

import it.nch.fwk.checks.errors.QualifiedError;



/**
 * abstract a check that can return multiple erros. No informations about the context, from which the object to check is 
 * taken, are provided to this class, so only un-contextualized errors can be collected. 
 * if check is sucessfull an empty QualifiedErrors is expected.
 * @author gdefacci
 */
public interface AtomicVerifier {

	QualifiedErrors verify(Object object);
	
	class SimpleVerifierWrapper implements AtomicVerifier {
		
		final SimpleAtomicVerifier simpleVerifier;
		public SimpleVerifierWrapper(SimpleAtomicVerifier simpleVerifier) {
			this.simpleVerifier = simpleVerifier;
		}

		public QualifiedErrors verify(Object object) {
			QualifiedError qe = simpleVerifier.verify(object);
			return QualifiedErrors.Base.create(qe);
		}

		public static AtomicVerifier create(SimpleAtomicVerifier simpleVerifier) {
			return new SimpleVerifierWrapper(simpleVerifier);
		}
		
	}
}

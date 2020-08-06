/**
 * Created on 25/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;

import it.nch.eb.common.utils.StringUtils;


/**
 * a mapping between a property and the varifier whcih should be used to verify/check the property.
 * there are three variants:
 * new AtomicVerifiersMapping(propertyName,verifier) create a verifier tha verify propertyName if its value is not null
 * new AtomicVerifiersMapping(propertyName,verifier, false):
 * create a verifier tha verify propertyName ( even if the property value is null)
 * new AtomicVerifiersMapping(propertyName,verifier, NULL_ERROR_ID):
 * create a verifier tha verify propertyName if its value is not null. If its value is null an error with id 
 * NULL_ERROR_ID will be collected
 * @author gdefacci
 */
public class AtomicVerifiersMapping extends BaseVerifierMapping implements IAtomicVerifierMapping {

	private final AtomicVerifier	verifier;
	
	public AtomicVerifiersMapping(String propertyName, AtomicVerifier verifier, boolean ignoredIfNull) {
		super(propertyName, ignoredIfNull,  null);
		this.verifier = verifier;
	}
	
	public AtomicVerifiersMapping(String propertyName, AtomicVerifier verifier, String nullErrorId) {
		super(propertyName, false,  nullErrorId);
		this.verifier = verifier;
	}
	
	public AtomicVerifiersMapping(String propertyName, AtomicVerifier verifier) {
		this(propertyName, verifier, true);
	}

	public AtomicVerifier getVerifier() {
		return verifier;
	}
	
	public AtomicVerifier createVerifier(final GetterStrategy getterStrategy) {
		return new AtomicVerifier() {

			public QualifiedErrors verify(Object o) {
				if (o==null) return null;
				Object property = getterStrategy.getValueFrom(o, getPropertyName());
				QualifiedErrors errs = new QualifiedErrors.Base();
				if (!ignoredWhenNull) {  							// se il check non e' da ignorare quando property e null
					if (property == null && nullErrorId!=null) {	// se property e' null e, l'error id in caso di null, e' stato fornito
						IBeanError error = new BaseBeanPropertyError(nullErrorId, o, getPropertyName(), property );
						errs.add(error);
					} else {										// altrimenti verifica la proprieta', anche se 'e null
						errs.add(verifier.verify(property)); 
					}
				} else if (property!=null) {						// altrimenti verifica la proprieta', se non 'e null
					errs.add(verifier.verify(property));
				}
				return errs;
			}
			
		};

	}

	public String toString() {
		return StringUtils.getSimpleName(this.getClass()) + " for property " + propertyName +
			(ignoredWhenNull?" not verified when (property == null) " : "") +
			(nullErrorId!=null?" when null produces error with id " + nullErrorId: "") ;
	}
	
}

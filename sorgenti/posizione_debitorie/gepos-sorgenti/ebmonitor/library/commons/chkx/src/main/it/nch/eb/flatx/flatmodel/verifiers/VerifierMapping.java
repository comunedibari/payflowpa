/**
 * Created on 25/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;



/**
 * a mapping between a property and the varifier whcih should be used to verify/check the property.
 * there are three variants:
 * new VerifierMapping(propertyName,verifier) create a verifier tha verify propertyName if its value is not null
 * new VerifierMapping(propertyName,verifier, false):
 * create a verifier tha verify propertyName ( even if the property value is null)
 * new VerifierMapping(propertyName,verifier, NULL_ERROR_ID):
 * create a verifier tha verify propertyName if its value is not null. If its value is null an error with id 
 * NULL_ERROR_ID will be collected
 * @author gdefacci
 */
public class VerifierMapping extends BaseVerifierMapping implements IVerifierMapping {

	private final Verifier			verifier;
	public VerifierMapping(String propertyName, Verifier verifier, boolean ignoredIfNull) {
		super(propertyName, ignoredIfNull, null);
		this.verifier = verifier;
		
	}
	
	public VerifierMapping(String propertyName, Verifier verifier, String ifNullErrorId) {
		super(propertyName, false, ifNullErrorId);
		this.verifier = verifier;
	}

	public VerifierMapping(String propertyName, Verifier verifier) {
		this(propertyName, verifier, true);
	}

	public Verifier getVerifier() {
		return verifier;
	}
	
	public Verifier createVerifier(final GetterStrategy getterStrategy) {
		return new Verifier() {

			public void verify(Object o, BeanErrorCollector beanErrorCollector) {
				if (o==null) throw new NullPointerException("null container when looking for property " + propertyName);
				Object property = getterStrategy.getValueFrom(o, getPropertyName());
				if (!ignoredWhenNull) {  							// se il check non e' da ignorare quando property e null
					if (property == null && nullErrorId!=null) {	// se property e' null e, l'error id in caso di null, e' stato fornito
						IBeanError error = new BaseBeanPropertyError(nullErrorId, o, getPropertyName(), property );
						beanErrorCollector.collectError(error);
					} else {										// altrimenti verifica la proprieta', anche se 'e null
						verifier.verify(property, beanErrorCollector); 
					}
				} else if (property!=null) {						// altrimenti verifica la proprieta', se non 'e null
					verifier.verify(property, beanErrorCollector);
				}
			}
			
		};
	}

	public String toString() {
		return "verifier mapping for property[" + this.propertyName + "] verifier [" + verifier + "]"; 
	}
	
}

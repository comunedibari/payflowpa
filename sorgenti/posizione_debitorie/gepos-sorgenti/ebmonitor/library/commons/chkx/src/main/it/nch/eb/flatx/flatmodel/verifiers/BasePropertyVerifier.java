/**
 * Created on 25/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;


public class BasePropertyVerifier implements PropertyVerifier {

	private final AtomicVerifier verifier;
	private final DomainErrorsFactory domainErrorsFactory;
	private final GetterStrategy getterStrategy;

	public BasePropertyVerifier(AtomicVerifier verifier, DomainErrorsFactory domainErrorsFactory) {
		this(verifier, domainErrorsFactory, new ReflectionGetterStrategy());
	}

	public BasePropertyVerifier(AtomicVerifier verifier, DomainErrorsFactory domainErrorsFactory,
			GetterStrategy getterStrategy) {
		this.verifier = verifier;
		this.domainErrorsFactory = domainErrorsFactory;
		this.getterStrategy = getterStrategy;
	}

	public void verify(Object o, String property, BeanErrorCollector beanErrorCollector) {
		if (o!=null) {
			QualifiedErrors qe = verifier.verify(o);
			IBeanError[] errors = qe.getErrors();
			if (errors!=null) {
				Object propertyValue = getterStrategy.getValueFrom(o, property);
				for (int i = 0; i < errors.length; i++) {
					IBeanError causeError = errors[i];
					IBeanError baseErr = domainErrorsFactory.createDecoratedError(o, property, propertyValue, causeError);
					beanErrorCollector.collectError( baseErr );
				}
			}
		}
	}

	public Object getProperty(Object o, String property) {
		return getterStrategy.getValueFrom(o, property);
	}

}
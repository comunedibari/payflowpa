/**
 * Created on 27/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;


import it.nch.eb.common.utils.resource.ResourcesUtil;

import java.util.Date;

import org.slf4j.Logger;


/**
 * @author gdefacci
 */
public abstract class BaseVerifier { 
	
	
	protected static final Logger log = ResourcesUtil.createLogger(BaseVerifier.class);
	
	static final DomainErrorsFactory DEFAULT_ERRORS_FACTORY = new DomainErrorsFactory() {

		public IBeanError createDecoratedError(Object from, String propertyName, Object propertyValue,
				IBeanError qualifiedError) {
			return new BaseBeanPropertyError(qualifiedError, from, propertyName, propertyValue);
		}
		
	};
	
	private final GetterStrategy	getterStrategy;
	private final DomainErrorsFactory domainErrorsFactory;
	
	public BaseVerifier() {
		this(DEFAULT_ERRORS_FACTORY, new ReflectionGetterStrategy());
		log.info("default error factory for " + this.getClass().getName());
	}

	public BaseVerifier(GetterStrategy getterStrategy) {
		this(DEFAULT_ERRORS_FACTORY, getterStrategy);
		log.info("default error factory for " + this.getClass().getName());
	}
	
	public BaseVerifier(DomainErrorsFactory domainErrorsFactory) {
		this(domainErrorsFactory, new ReflectionGetterStrategy());
	}

	public BaseVerifier(DomainErrorsFactory domainErrorsFactory, GetterStrategy getterStrategy) {
		if (domainErrorsFactory != DEFAULT_ERRORS_FACTORY) {
			log.info("enriched error factory for class " + this.getClass().getName());
		}
		
		this.getterStrategy = getterStrategy;
		this.domainErrorsFactory = domainErrorsFactory;
	}

	protected QualifiedErrors verify(AtomicVerifier verifier, Object container, String[] propertiesNames) {
		if (container==null) return new QualifiedErrors.Base();
		
		Object[] parameters = new Object[propertiesNames.length];
		for (int i = 0; i < propertiesNames.length; i++) {
			String propName = propertiesNames[i];
			parameters[i] = getPropertyValueFrom(container, propName); 
		}
		return verifier.verify(parameters);
	}
	
	public void verifyProperties(Object rec, IAtomicVerifierMapping[] propertiesToVerify2,
			BeanErrorCollector beanErrorCollector) {
		
		for (int i = 0; i < propertiesToVerify2.length; i++) {
			IAtomicVerifierMapping propertyVerifierMapping = propertiesToVerify2[i];
			PropertyVerifier pv = getPropertyVerifier(propertyVerifierMapping);
			BeanErrorCollector decoratedBeanErrorCollector = 
				createDecoratedBeanErrorCollector(beanErrorCollector, rec, propertyVerifierMapping.getPropertyName());
			if (rec!=null)pv.verify(rec, propertyVerifierMapping.getPropertyName(), decoratedBeanErrorCollector);
		}
			
	}
	
	public void verifyProperties(Object rec, IVerifierMapping[] propertiesToVerify2,
			final BeanErrorCollector beanErrorCollector) {
		
		for (int i = 0; i < propertiesToVerify2.length; i++) {
			Verifier verifier = propertiesToVerify2[i].createVerifier( getterStrategy );
			String propName = propertiesToVerify2[i].getPropertyName();
			BeanErrorCollector errCollector = 
				createDecoratedBeanErrorCollector(beanErrorCollector, rec, propName);
			if (rec!=null) verifier.verify(rec, errCollector);
		}
			
	}

	protected BeanErrorCollectorDomainErrorsFactory createDecoratedBeanErrorCollector(final BeanErrorCollector beanErrorCollector,
			Object rec, String propName) {
		return new BeanErrorCollectorDomainErrorsFactory.ErroCollectorDelegate(beanErrorCollector) {

			public IBeanError createDecoratedError(Object from, String propertyName, Object propertyValue,
					IBeanError qualifiedError) {
				return new BaseBeanPropertyError(qualifiedError, from, propertyName, propertyValue);
			}
			
		};
	}

	protected PropertyVerifier getPropertyVerifier(IAtomicVerifierMapping propertyVerifierMapping) {
//		return new BasePropertyVerifier(propertyVerifierMapping.createVerifier(getterStrategy), domainErrorsFactory, getterStrategy);
		return new BasePropertyVerifier(propertyVerifierMapping.createVerifier(getterStrategy), domainErrorsFactory, getterStrategy);
	}
	
	protected Object getPropertyValueFrom(Object container, String propName) {
		return getterStrategy.getValueFrom(container, propName);
	}

	protected IBeanError createError(String id, Object container, String propertyName,
			Object propertyValue) {
		return new BaseBeanPropertyError(id, container, propertyName, propertyValue);
	}
	
	protected IBeanError createError(String id, Object container, String propertyName,
			Object propertyValue, Object expecetedValue) {
		return new BaseBeanPropertyError(id, container, propertyName, propertyValue, expecetedValue);
	}
	
	protected IBeanError createError(String id, Object rec16, String propertyName) {
		return new BaseBeanPropertyError(id, rec16, propertyName);
	}
	
	protected GetterStrategy getGetterStrategy() {
		return getterStrategy;
	}

	/**
	 *  return <code>object</code> if <code>object</code> is not null, otherwise <code>elseObject</code> is returned.
	 *  keep this method private and expose needed type safe methods
	 */
	private Object genericGetOrElse(Object object, Object elseObject) {
		if (object != null) {
			return object;
		} else {
			return elseObject;
		}
	}

	public Date getOrElse(Date d1, Date now) {
		return (Date) genericGetOrElse(d1, now);
	}
	
	public String getOrElse(String s1, String sElse) {
		return (String) genericGetOrElse(s1, sElse);
	}
	
	public Integer getOrElse(Integer i1, Integer iElse) {
		return (Integer) genericGetOrElse(i1, iElse);
	}
	
}

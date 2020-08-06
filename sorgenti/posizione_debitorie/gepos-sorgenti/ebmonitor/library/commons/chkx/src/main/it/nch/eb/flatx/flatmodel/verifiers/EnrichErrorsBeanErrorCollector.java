package it.nch.eb.flatx.flatmodel.verifiers;



import java.util.Iterator;
import java.util.List;

/**
 * @author gdefacci
 */
public class EnrichErrorsBeanErrorCollector implements BeanErrorCollectorDomainErrorsFactory {
	
	final private BeanErrorCollector delegate;
	final private Object	from;
	final private String	propertyName;
	final private DomainErrorsFactory domainErrorsFactory;
	final private GetterStrategy getterStrategy;

	public EnrichErrorsBeanErrorCollector(BeanErrorCollector delegate, Object from, String propertyName,
			DomainErrorsFactory domainErrorsFactory, GetterStrategy getterStrategy) {
		this.delegate = delegate;
		this.from = from;
		this.propertyName = propertyName;
		this.domainErrorsFactory = domainErrorsFactory;
		this.getterStrategy = getterStrategy;
	}
	
	/** FIXME invokable when ovveriiding public IBeanError createDomainSpecificError(Object from, String propertyName, Object propertyValue, IBeanError qualifiedError) */
	protected EnrichErrorsBeanErrorCollector(BeanErrorCollector delegate, Object from, String propertyName,
			GetterStrategy getterStrategy) {
		this(delegate, from, propertyName, null, getterStrategy);
	}
	
	static BeanErrorCollector getInnerDelegate(BeanErrorCollector collector) {
		if (collector instanceof EnrichErrorsBeanErrorCollector) 
			return getInnerDelegate(((EnrichErrorsBeanErrorCollector)collector).delegate);
		else return collector;
	}

	public IBeanError collectError(IBeanError error) {
		Object propertyValue = getPropertyValue();
		IBeanError enriched = createDecoratedError(from, propertyName, propertyValue , error);
		delegate.collectError(enriched);
		return enriched;
	}

	private Object getPropertyValue() {
		return getterStrategy.getValueFrom(from, propertyName);
	}
	
	public IBeanError createDecoratedError(Object from, String propertyName, Object propertyValue,
			IBeanError qualifiedError) {
		return domainErrorsFactory.createDecoratedError(from, propertyName, propertyValue, qualifiedError);
	}

	public QualifiedErrors collectError(QualifiedErrors error) {
		QualifiedErrors enrichedErrors = new QualifiedErrors.Base();
		for (Iterator it = error.getIterator(); it.hasNext();) {
			IBeanError err = (IBeanError) it.next();
			enrichedErrors.add(collectError(err));
		}
		return enrichedErrors;
	}

	public Iterator getIterator() {
		return delegate.getIterator();
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public List filter(BeanErrorMatcher matcher) {
		return delegate.filter(matcher);
	}

	public IBeanError[] getErrors() {
		return delegate.getErrors();
	}
	
}
/**
 * Created on 30/mag/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;

import java.util.Iterator;
import java.util.List;


/**
 * @author gdefacci
 */
public interface BeanErrorCollectorDomainErrorsFactory extends DomainErrorsFactory, BeanErrorCollector {
	
	abstract class ErroCollectorDelegate implements BeanErrorCollectorDomainErrorsFactory {
		private final BeanErrorCollector delegate;
		
		public ErroCollectorDelegate(BeanErrorCollector delegate) {
			this.delegate = delegate;
		}

		public IBeanError collectError(IBeanError error) {
			return delegate.collectError(error);
		}

		public QualifiedErrors collectError(QualifiedErrors error) {
			return delegate.collectError(error);
		}

		public List filter(BeanErrorMatcher matcher) {
			return delegate.filter(matcher);
		}

		public IBeanError[] getErrors() {
			return delegate.getErrors();
		}

		public Iterator getIterator() {
			return delegate.getIterator();
		}

		public boolean isEmpty() {
			return delegate.isEmpty();
		}
	}

}

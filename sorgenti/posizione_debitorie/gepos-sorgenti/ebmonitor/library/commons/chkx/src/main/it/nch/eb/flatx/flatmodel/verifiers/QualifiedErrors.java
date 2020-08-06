/**
 * Created on 25/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.nch.fwk.checks.errors.QualifiedError;


/**
 * a container of qualified errors
 * @author gdefacci
 */
public interface QualifiedErrors {

	void add(QualifiedError error);
	void add(QualifiedErrors error);
	IBeanError[] getErrors();
	int getSize();
	boolean isEmpty();
	Iterator getIterator();
	List/*<IBeanError>*/ filter(BeanErrorMatcher matcher);
	
	class Base implements QualifiedErrors, BeanErrorCollector {

		private final List list = new ArrayList();
		
		public Base() {
		}
		
		public Base(QualifiedError error) {
			add(error);
		}
		
		public void add(QualifiedError error) {
			if (error!=null) {
				list.add(error);
			}
		}

		public IBeanError[] getErrors() {
			if (list.isEmpty()) return null;
			return (IBeanError[]) list.toArray(new IBeanError[0]);
		}
		
		public static QualifiedErrors create(QualifiedError qe) {
			Base res = new Base();
			res.add(qe);
			return res;
		}
		
		public boolean isEmpty() {
			return list.isEmpty();
		}

		public int getSize() {
			return list.size();
		}

		public void add(QualifiedErrors errors) {
			if (errors!=null && !errors.isEmpty()) 
				for (Iterator it = errors.getIterator(); it.hasNext();) {
					IBeanError err = (IBeanError) it.next();
					add(err);
				}
		}

		public Iterator getIterator() {
			return list.iterator();
		}

		public IBeanError collectError(IBeanError error) {
			add(error);
			return error;
		}

		public QualifiedErrors collectError(QualifiedErrors errors) {
			add(errors);
			return errors;
		}

		public String toString() {
			return "errors(n." + list.size() + ")" + list;
		}

		public List filter(BeanErrorMatcher matcher) {
			List res = new ArrayList();
			for (Iterator it = list.iterator(); it.hasNext();) {
				IBeanError err = (IBeanError) it.next();
				if (matcher.match(err)) res.add(err);
			}
			return res;
		}
		
	};
}

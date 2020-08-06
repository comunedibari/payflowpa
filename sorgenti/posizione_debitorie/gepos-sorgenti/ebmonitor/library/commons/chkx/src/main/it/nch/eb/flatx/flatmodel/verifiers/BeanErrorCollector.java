/**
 * Created on 14/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;


import java.util.Iterator;
import java.util.List;


/**
 * @author gdefacci
 */
public interface BeanErrorCollector {
	
	IBeanError collectError(IBeanError error);
	QualifiedErrors collectError(QualifiedErrors error);
	
	Iterator/*<QualifiedError>*/ getIterator();
	boolean isEmpty();
	
	List filter(BeanErrorMatcher matcher);
	public IBeanError[] getErrors();

}

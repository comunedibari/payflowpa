/**
 * Created on 06/mag/07
 */
package it.nch.fwk.checks.errors;


import it.nch.eb.common.utils.bindings.IBindingManager;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author gdefacci
 */
public class BaseErrorCollector implements ErrorCollector {
	
	private Set 					errorsSet 			= new TreeSet();
	protected ErrorMessagesFactory	errorsFactory;
	protected IBindingManager			bindings;
	
	public BaseErrorCollector(ErrorMessagesFactory errorsFactory, IBindingManager bindingManager){
		this.errorsFactory = errorsFactory;
		this.bindings = bindingManager;
	}
	
	protected ErrorMessagesFactory	errorsFactory() {
		return this.errorsFactory;
	}
	
	public QualifiedError collectError(QualifiedError qe) {
		if (qe!=QualifiedError.NO_ERROR) this.errorsSet.add(qe);
		return qe;
	}

	public QualifiedError collectError(String detail) {
		QualifiedError qerror = errorsFactory().createError(detail, this.bindings); 
		return collectError(qerror);
	}

	public QualifiedError collectError(String detail, int type) {
		QualifiedError qerror = errorsFactory().createError(detail, type, this.bindings); 
		return collectError(qerror);
	}
	
	public QualifiedError collectError(String errorId, int errorType, String expecetedValue) {
		QualifiedError qerror = errorsFactory().createError(errorId, errorType, expecetedValue, this.bindings); 
		return collectError(qerror);
	}

	public QualifiedError collectError(String errorId, int errorType, String expecetedValue, String severity) {
		QualifiedError qerror = errorsFactory().createError(errorId, errorType, expecetedValue, severity, this.bindings); 
		return collectError(qerror);
	}

	public Set getErrorSet() {
		return Collections.unmodifiableSet(errorsSet);
	}

	protected void collectErrorSet(Set errorSet) {
		for (Iterator it = errorSet.iterator(); it.hasNext();) {
			QualifiedError err = (QualifiedError) it.next();
			collectError(err);
		}
	}

	public ErrorCollector collectAllErrors(ErrorCollector errorCollector) {
		collectErrorSet(errorCollector.getErrorSet());
		return this;
	}

	public boolean isOk() {
		return this.errorsSet.isEmpty();
	}

	public ErrorCollectorQuery query() {
		return new ErrorCollectorQueryImpl(this);
	}

	public IBindingManager getBindings() {
		return bindings;
	}

}

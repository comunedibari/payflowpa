/**
 * Created on 05/apr/07
 */
package it.nch.fwk.checks.errors;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.fwk.checks.IElementCursor;


/**
 * responsabilities:
 * - errors creation
 * - error handling registering
 * - error messages creation 
 * @author gdefacci 
 */
public class ErrorMessagesFactory extends BaseErrorMessageFactory {
	
	private static final long	serialVersionUID	= 956848618767848580L;

	//	errors non element related 
	public ErrorInfo createError(String id, int type, String expectedValue, String severity, IBindingManager bindings) {
		ErrorInfo res = new ErrorInfoImpl(id, type, expectedValue, severity);
		ErrorInfo filledError = createDomainSpecificErrorInfo(res, null, bindings);
		return setErrorMessage(filledError, bindings);
	}
	public ErrorInfo createError(String id, int type, String expectedValue, IBindingManager bindings) {
		ErrorInfo res = new ErrorInfoImpl(id, type, expectedValue, null);
		ErrorInfo filledError = createDomainSpecificErrorInfo(res, null, bindings);
		return setErrorMessage(filledError, bindings);
	}
	public QualifiedError createError(String id, IBindingManager bindings) {
		ErrorInfo errorInfo = new ErrorInfoImpl(id);
		ErrorInfo filledError = createDomainSpecificErrorInfo(errorInfo, null, bindings);
		return setErrorMessage(filledError, bindings);
	}
	public QualifiedError createError(String id, int type, IBindingManager bindings) {
		ErrorInfo errorInfo = new ErrorInfoImpl(id, type);
		ErrorInfo filledError = createDomainSpecificErrorInfo(errorInfo, null, bindings);
		return setErrorMessage(filledError, bindings);
	}	
	
	/**
	 * gives subclasses a chance of plug a custom error message creation strategy
	 * way (TODO is it really needed?)
	 */
	protected ErrorInfo setErrorMessage(ErrorInfo res, IBindingManager bindings) {
		String msg = createMessage(res, bindings);
		res.setMessage(msg);
		return res;
	}
	/**
	 * gives ubclasses a chance of calculating the error message in a specific
	 * way (TODO is it really needed? if not make it private)
	 */
	protected void setErrorMessage(IElementCursor errorSource, IBindingManager bindings, ErrorInfo filledError) {
		String msg = createMessage(filledError, errorSource, bindings); 
		filledError.setMessage(msg);
	}
	
	/**
	 * give subclasses a way to adapt ErrorInfo to a domain specific ErrorInfo 
	 * possibly getting properties from the error source element and the IBindingManager.
	 * The returned errorInfoMessage is the one will be collected
	 */
	protected ErrorInfo createDomainSpecificErrorInfo(ErrorInfo ei,  IElementCursor errorSource, IBindingManager bindings) {
		return ei;
	}
	
	/**
	 * @param id				error id
	 * @param type				error type
	 * @param currentValue		current value of the checked item
	 * @param expeceted			expeceted value for the checked item
	 * @param severity			error severity
	 * @param errorSource		element cursor in which the error happened
	 * @param bindings			the binding manager instance when the error happened
	 * @return					the proper errorInfo instance
	 */
	public ErrorInfo createError(String id, int type, String expectedValue, String severity, IElementCursor errorSource, IBindingManager bindings) {
//		ErrorInfo res = new ErrorInfoImpl(id, type, errorSource.getPath(), errorSource.getName(), errorSource.getValue(), expectedValue, severity);
		ErrorInfo res = new ErrorInfoImpl(id, type, expectedValue, severity);
		ErrorInfo filledError = createDomainSpecificErrorInfo(res, errorSource, bindings);
		setErrorMessage(errorSource, bindings, filledError);
		return res;
	}

}

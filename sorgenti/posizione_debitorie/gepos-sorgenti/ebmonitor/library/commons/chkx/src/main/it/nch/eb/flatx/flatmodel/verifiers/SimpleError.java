/**
 * Created on 25/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;

import it.nch.fwk.checks.errors.QualifiedError;
import it.nch.fwk.checks.errors.QualifiedErrorImpl;


/**
 * represents some sort of infos about a error. contains only uncontextualized infos :no infos about the context 
 * in which the error has been detected, should be preserved by this object. to provide a contextualized error use
 * {@link BeanPropertyError}
 * @author gdefacci
 */
public class SimpleError extends QualifiedErrorImpl implements IBeanError, ErrorsDeaults {

	private static final long	serialVersionUID	= 3754480017792831188L;
	private final Object value;
	private final Object expectedValue;
	final Exception exception;

	public SimpleError(String errorId, int errorType, Object value) {
		this(errorId, errorType, value,  NO_EXPECTATION_PROVIDED, NO_EXCEPTION_PROVIDED);
	}

	public SimpleError(String errorId, Object value) {
		this(errorId, NO_ERROR_TYPE, value, NO_EXPECTATION_PROVIDED, NO_EXCEPTION_PROVIDED);
	}
	
	public SimpleError(String errorId, Object value, Exception e) {
		this(errorId, NO_ERROR_TYPE, value, NO_EXPECTATION_PROVIDED, e);
	}
	
	public SimpleError(String errorId, int errorType, Object value, Object expectedValue) {
		this(errorId, errorType, value, expectedValue, NO_EXCEPTION_PROVIDED);
	}
	
	public SimpleError(String errorId, Object value, Object expectedValue) {
		this(errorId, NO_ERROR_TYPE,  value, expectedValue, NO_EXCEPTION_PROVIDED);
	}
	
	public SimpleError(QualifiedError errorCause, Object value) {
		this(errorCause.getErrorId(), errorCause.getErrorType(), value, NO_EXPECTATION_PROVIDED, NO_EXCEPTION_PROVIDED);
	}
	
	public SimpleError(String errorId, int errorType, Object value, Object expectedValue, Exception ex) {
		super(errorId, errorType);
		this.value = value;
		this.expectedValue = expectedValue;
		this.exception = ex;
	}

	public Object getValue() {
		return value;
	}

	public Object getExpectedValue() {
		return expectedValue;
	}
	
	public Exception getException() {
		return exception;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		columnsPrint(sb, "error id", getErrorId());
		if (getErrorType()!=NO_ERROR_TYPE) columnsPrint(sb, "error type", new Integer(getErrorType()));
		if (getExpectedValue()!=null && getExpectedValue()!=NO_EXPECTATION_PROVIDED) columnsPrint(sb, "value", value );
		if (getException()!=null && getException()!= NO_EXCEPTION_PROVIDED) columnsPrint(sb, "exception" , exception.getMessage() );
		
		return sb.toString();
	}
	
}

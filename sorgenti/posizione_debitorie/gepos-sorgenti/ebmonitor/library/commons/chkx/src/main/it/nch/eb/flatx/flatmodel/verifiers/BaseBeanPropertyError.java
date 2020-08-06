/**
 * Created on 14/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;

import it.nch.eb.common.utils.StringUtils;
import it.nch.fwk.checks.errors.QualifiedErrorImpl;


/**
 * @author gdefacci
 */
public class BaseBeanPropertyError extends QualifiedErrorImpl implements BeanPropertyError , ErrorsDeaults {

	private static final long	serialVersionUID	= 1318629455421225246L;
	private Object	expectedValue;
	private Object	propertyValue;
	private String	propertyName;
	private Object	containerValue;
	private IBeanError	errorCause;
	
	public BaseBeanPropertyError(String errorId, Object containerValue, String propertyName) {
		this(errorId, NO_ERROR_TYPE, containerValue, propertyName, NO_VALUE_PROVIDED , NO_CAUSE_ERROR);
	}
	
	public BaseBeanPropertyError(String errorId, Object containerValue, String propertyName, Object propertyValue) {
		this(errorId, NO_ERROR_TYPE, containerValue, propertyName, propertyValue, NO_CAUSE_ERROR);
	}
	
	public BaseBeanPropertyError(String errorId, int errorType, Object containerValue, String propertyName, Object propertyValue) {
		this(errorId, errorType, containerValue, propertyName, propertyValue, NO_CAUSE_ERROR);
	}
	
	public BaseBeanPropertyError(String errorId, Object containerValue, String propertyName, 
			Object propertyValue, Object expectedValue) {
		this(errorId, NO_ERROR_TYPE, containerValue, propertyName, propertyValue, expectedValue, NO_CAUSE_ERROR);
	}
	
	public BaseBeanPropertyError(String errorId, int errorType, Object containerValue, String propertyName, 
			Object propertyValue, Object expectedValue) {
		this(errorId, errorType, containerValue, propertyName, propertyValue, expectedValue, NO_CAUSE_ERROR);
	}
	
	public BaseBeanPropertyError(String errorId, int errorType, Object containerValue, String propertyName, Object propertyValue,
			IBeanError errorCause) {
		this(errorId, errorType, containerValue, propertyName, propertyValue, NO_EXPECTATION_PROVIDED, new SimpleError(errorCause, propertyValue));
	}
	
	public BaseBeanPropertyError(IBeanError qe, Object container, String propName, Object propVal) {
		this(qe.getErrorId(), qe.getErrorType(), container, propName, propVal, NO_EXPECTATION_PROVIDED, qe);
	}
	
	public BaseBeanPropertyError(IBeanError qe, String id, Object container, String propName, Object propVal) {
		this(id, qe.getErrorType(), container, propName, propVal, NO_EXPECTATION_PROVIDED, qe);
	}
	
	public BaseBeanPropertyError(String errorId, int errorType, Object containerValue, String propertyName, Object propertyValue,
			Object expectedValue, IBeanError errorCause) {
		super(errorId, errorType);
		this.propertyValue = propertyValue;
		this.propertyName = propertyName;
		this.containerValue = containerValue;
		this.errorCause = errorCause;
		this.expectedValue = expectedValue;
	}

	public Object getContainerValue() {
		return containerValue;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Object getValue() {
		return propertyValue;
	}

	public IBeanError getCause() {
		return errorCause;
	}

	public Object getExpectedValue() {
		return expectedValue;
	}

	public Exception getException() {
		return errorCause.getException();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		
		sb.append(getContainerToString());
		
		if (this.getPropertyName()!=null) 
			columnsPrint(sb, "property name",getPropertyName());
		
		if (this.getValue()!=null && this.getValue()!=NO_VALUE_PROVIDED)
			columnsPrint(sb, "property value", getValue());
		
		if (this.getExpectedValue()!=null && getExpectedValue()!=NO_EXPECTATION_PROVIDED)
			columnsPrint(sb, "expected value", getExpectedValue());
		
		if (this.getCause()!=null && this.getCause()!=NO_CAUSE_ERROR) {
			sb.append("\n---------------------------");
			columnsPrint(sb, "cause ", getCause().toString());
		}
		
		return sb.toString();
	}

	private String getContainerToString() {
		return columnsPrint("container of class" , StringUtils.getSimpleName(this.containerValue.getClass()));
	}

}

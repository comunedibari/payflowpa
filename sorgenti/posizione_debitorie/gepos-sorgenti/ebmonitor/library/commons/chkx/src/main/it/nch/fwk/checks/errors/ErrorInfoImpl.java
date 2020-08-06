/**
 * Created on 15/mag/07
 */
package it.nch.fwk.checks.errors;


/**
 * @author gdefacci
 */
public class ErrorInfoImpl extends QualifiedErrorImpl implements ErrorInfo {
	
	private static final long	serialVersionUID	= 6064285831370530136L;
	protected String	expecetedValue;	// provided
	protected String	severity;		// provided
	protected String	message;		// obtained trough processor
	
	public ErrorInfoImpl(String id) {
		this(id, ErrorInfo.UNQUALIFIED_ERROR_TYPE);
	}
	
	public ErrorInfoImpl(String errorId, int type) {
		this(errorId, type, NO_EXPECETED_VALUE, Severity.DEFAULT);
	}
	
	public ErrorInfoImpl(QualifiedError qerror) {
		this(qerror.getErrorId(), qerror.getErrorType(), NO_EXPECETED_VALUE, Severity.DEFAULT);
	}
	
	public ErrorInfoImpl(String errorId, int type, String expectedValue,  String severity) {
		super(errorId,type);
		this.expecetedValue = expectedValue;
		this.severity = severity==null ? Severity.DEFAULT : severity;
	}
	
	public String getExpectedValue() {
		return expecetedValue;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String str) {
		this.message = str;
	}
	
	public String getSeverity() {
		return severity;
	}

	public String getExpeceted() {
		return expecetedValue;
	}

	public String toString() {
		return super.toString() + "\nmessage[" + this.message + "]\nexpeceted[" + this.expecetedValue + "]\nseverity[" + this.severity + "]";
	}

	public ErrorInfo create(QualifiedError qError) {
		ErrorInfoImpl res;
		if (qError instanceof ErrorInfo) {
			ErrorInfo eiError = (ErrorInfo) qError;
			res = new ErrorInfoImpl(eiError.getErrorId(), eiError.getErrorType(), eiError.getExpectedValue(), eiError.getExpectedValue() );
		} else {
			res = new ErrorInfoImpl(qError.getErrorId(), qError.getErrorType());
		}
		return res;
	}

}

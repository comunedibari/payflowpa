package it.nch.fwk.checks.errors;

public class ParserErrorInfo {

	private static final int	ERROR_TYPE	= 2;

	private final String		severity;

	private final String		errorId;

	private final int			errorType	= ERROR_TYPE;

	private final String		errorMessage;

	private final String		xsdErrorCode;				// NATIVE ERROR CODE

	private final int			columnNumber;				// NATIVE

	private final int			lineNumber;				// NATIVE

	private final String		originalErrorMessage;

	public ParserErrorInfo(String xsdErrorCode, 
			String errorMessage, 
			String originalErrorMessage, 
			int columnNumber, 
			int lineNumber, 
			String severity) {
		this.xsdErrorCode = xsdErrorCode;
		this.errorMessage = errorMessage;
		this.columnNumber = columnNumber;
		this.lineNumber = lineNumber;
		this.severity = severity;
		this.errorId = lineNumber + ":" + columnNumber;
		this.originalErrorMessage = originalErrorMessage;
	}

	public String getSeverity() {
		return severity;
	}

	public String getErrorCode() {
		return this.xsdErrorCode;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getErrorId() {
		return errorId;
	}

	public int getErrorType() {
		return errorType;
	}

	public String getOriginalErrorMessage() {
		return originalErrorMessage;
	}

}

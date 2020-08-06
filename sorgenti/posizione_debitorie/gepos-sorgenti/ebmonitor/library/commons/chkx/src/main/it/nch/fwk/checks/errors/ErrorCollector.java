/**
 * Created on 06/mag/07
 */
package it.nch.fwk.checks.errors;


import java.util.Set;


/**
 * allow error collecting with a few support to qualify error which have been 
 * collected.
 * @author gdefacci
 */
public interface ErrorCollector {

	QualifiedError collectError(QualifiedError error);
//	** helpers
	QualifiedError collectError(String detail);
	QualifiedError collectError(String detail, int type);
	QualifiedError collectError(String errorId, int errorType, String expecetedValue);
	QualifiedError collectError(String errorId, int errorType, String expecetedValue, String severity);
	
	ErrorCollector collectAllErrors(ErrorCollector errorCollector);
	
//	** quering support
	Set getErrorSet();
	boolean isOk();
	public ErrorCollectorQuery query();
	
	static class Delegate implements ErrorCollector {
		
		protected ErrorCollector	delegate;
		
		public ErrorCollector collectAllErrors(ErrorCollector errorCollector) {
			return delegate.collectAllErrors(errorCollector);
		}

		public QualifiedError collectError(String detail) {
			return delegate.collectError(detail);
		}

		public QualifiedError collectError(String detail, int type) {
			return delegate.collectError(detail, type);
		}

		public Set getErrorSet() {
			return delegate.getErrorSet();
		}

		public boolean isOk() {
			return delegate.isOk();
		}

		public QualifiedError collectError(QualifiedError error) {
			return delegate.collectError(error);
		}

		public QualifiedError collectError(String errorId, int errorType, String expecetedValue) {
			return delegate.collectError(errorId, errorType, expecetedValue);
		}

		public QualifiedError collectError(String errorId, int errorType, String expecetedValue, String severity) {
			return delegate.collectError(errorId, errorType, expecetedValue, severity);
		}

		public ErrorCollectorQuery query() {
			return delegate.query();
		}

	};
	
}

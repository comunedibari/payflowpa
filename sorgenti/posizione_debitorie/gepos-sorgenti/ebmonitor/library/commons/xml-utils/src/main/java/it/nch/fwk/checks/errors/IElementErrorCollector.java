/**
 * Created on 22/apr/07
 */
package it.nch.fwk.checks.errors;

import it.nch.fwk.checks.IElementCursor;


/**
 * @author gdefacci
 */
public interface IElementErrorCollector extends ErrorCollector {

	QualifiedError collectError(String detail, IElementCursor element);
	QualifiedError collectError(String detail, int type, IElementCursor element);
	QualifiedError collectError(String errorId, int errorType, IElementCursor element, String expecetedValue);
	QualifiedError collectError(String errorId, int errorType, IElementCursor element, String expecetedValue, String severity);
	
	/**
	 * collect all errors from <code>errorCollector</code> provinding <code>elem</code>
	 * as the error source element
	 * @param elem
	 * @param errorCollector
	 * @return
	 */
//	IElementErrorCollector collectAllErrors(IElementCursor elem, ErrorCollector errorCollector);
	
	public static class Delegate extends ErrorCollector.Delegate implements IElementErrorCollector {

		public QualifiedError collectError(String detail, IElementCursor element) {
			return errorCollectorDelegate().collectError(detail, element);
		}

		public QualifiedError collectError(String detail, int type, IElementCursor element) {
			return errorCollectorDelegate().collectError(detail, type, element);
		}

//		public IElementErrorCollector collectAllErrors(IElementCursor elem, ErrorCollector errorCollector) {
//			return errorCollectorDelegate().collectAllErrors(elem, errorCollector);
//		}

		protected IElementErrorCollector errorCollectorDelegate() {
			return (IElementErrorCollector)delegate;
		}

		public QualifiedError collectError(String errorId, int errorType, IElementCursor element, String expecetedValue) {
			return errorCollectorDelegate().collectError(errorId, errorType, element, expecetedValue);
		}

		public QualifiedError collectError(String errorId, int errorType, IElementCursor element, String expecetedValue, String severity) {
			return errorCollectorDelegate().collectError(errorId, errorType, element, expecetedValue, severity);
		}
		
	}

}

/**
 * Created on 27/ago/07
 */
package it.nch.fwk.checks.errors;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.fwk.checks.IElementCursor;

/**
 * @author gdefacci
 */
public class BaseElementErrorCollector extends BaseErrorCollector implements IElementErrorCollector {

	public BaseElementErrorCollector(ErrorMessagesFactory errorsFactory, IBindingManager bindingManager) {
		super(errorsFactory, bindingManager);
	}

	public QualifiedError collectError(String errorId, int errorType, IElementCursor element, String expecetedValue, String severity) {
		return collectError(
				errorsFactory().createError(errorId, 
						errorType, 
						expecetedValue, 
						severity, 
						element, 
						getBindings() )
		);
	}
	public QualifiedError collectError(String detail, IElementCursor element) {
		return collectError(detail, ErrorInfo.UNQUALIFIED_ERROR_TYPE, element, ErrorInfo.NO_EXPECETED_VALUE, ErrorInfo.Severity.DEFAULT);
	}
	public QualifiedError collectError(String detail, int type, IElementCursor element) {
		return collectError(detail, type, element, ErrorInfo.NO_EXPECETED_VALUE, ErrorInfo.Severity.DEFAULT);
	}
	public QualifiedError collectError(String errorId, int errorType, IElementCursor element, String expecetedValue) {
		return this.collectError(errorId, errorType, element, expecetedValue, ErrorInfo.Severity.DEFAULT);
	}

}

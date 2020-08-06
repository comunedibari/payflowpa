/**
 * Created on 21/ago/07
 */
package it.nch.fwk.checks;

import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.fwk.checks.context.xom.ElementCheckContext;
import it.nch.fwk.checks.context.xom.ElementCursorFactory;
import it.nch.fwk.checks.errors.ErrorCollector;
import it.nch.fwk.checks.errors.ErrorInfo;
import it.nch.fwk.checks.errors.ErrorMessagesFactory;
import it.nch.fwk.checks.errors.IElementErrorCollector;
import it.nch.fwk.checks.errors.QualifiedError;
import it.nch.fwk.checks.xom.NullElementCursor;

import java.util.Iterator;
import java.util.Set;

/**
 * @author gdefacci
 */
public abstract class BaseElementCheck extends QualifiedCheck  implements IElementErrorCollector {
	
	protected static org.slf4j.Logger log = ResourcesUtil.createLogger(BaseElementCheck.class);
	protected IElementCursor elementCursor;
	
	protected BaseElementCheck(IElementCursor cursor) {
		this.elementCursor = cursor;
	}
	
	public IElementCursor current() {
		return elementCursor;
	}

	public ElementCursorFactory elementCursorFactory() {
		return checkContext().getElementCursorFactory();
	}
	public ErrorMessagesFactory errorsFactory() {
		return checkContext().getErrorsFactory();
	}
	
	
	protected ElementCheckContext checkContext() {
		return (ElementCheckContext) context();
	}
	
	/**
	 * ovveriden to provide the current element as the error relative element.
	 * @Override
	 */
	public ErrorCollector collectAllErrors(ErrorCollector errorCollector) {
		Set errorSet = errorCollector.getErrorSet();
		for (Iterator it = errorSet .iterator(); it.hasNext();) {
			QualifiedError err = (QualifiedError) it.next();
			ErrorInfo resErr;
			if (!(err instanceof ErrorInfo)) {
				resErr =  ((ErrorInfo) err).create(err);
			} else {
				resErr = (ErrorInfo) err;
			}
			collectError(resErr);
		}
		return null;
	}
	
	public QualifiedError collectError(String errorId) {
		return this.collectError(errorId, ErrorInfo.UNQUALIFIED_ERROR_TYPE, ErrorInfo.NO_EXPECETED_VALUE, ErrorInfo.Severity.DEFAULT);
	}
	public QualifiedError collectError(String errorId, int errorType) {
		return this.collectError(errorId, errorType, ErrorInfo.NO_EXPECETED_VALUE, ErrorInfo.Severity.DEFAULT);
	}

	public QualifiedError collectError(String errorId, int errorType, String expecetedValue) {
		return this.collectError(errorId, errorType, expecetedValue, ErrorInfo.Severity.DEFAULT);
	}
	public QualifiedError collectError(String errorId, int errorType, String expecetedValue, String severity) {
		return collectError(errorId, errorType, current(), expecetedValue, severity);
	}
	/**
	 * @param errorId
	 * @param errorType
	 * @param element
	 * @param currentValue
	 * @param expecetedValue
	 * @param severity
	 * @return
	 */
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
	
	public boolean isNullElement(IElementCursor element) {
		if (element == null || NullElementCursor.isNullElementCursor(element)) return true;
		else return false;
	}
	
}

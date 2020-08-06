/**
 * Created on 14/apr/07
 */
package it.nch.fwk.checks;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.fwk.checks.context.CheckContext;
import it.nch.fwk.checks.errors.BaseErrorCollector;
import it.nch.fwk.checks.errors.ErrorCollector;
import it.nch.fwk.checks.errors.ErrorCollectorCheck;
import it.nch.fwk.checks.errors.ErrorMessagesFactory;
import it.nch.fwk.checks.errors.QualifiedError;

/**
 * @author gdefacci
 */
public abstract class QualifiedCheck extends ErrorCollector.Delegate implements ErrorCollectorCheck {
	
	private CheckContext		context;
	protected ErrorCollector	errorCollector;
	public CheckContext context() {
		return context;
	}
	
	public void setCheckContext(CheckContext context) {
		this.context = context;
		ErrorMessagesFactory errorsFactory = context().getErrorsFactory();
		this.delegate = this.errorCollector = new BaseErrorCollector(errorsFactory, context.getBindingManager());
	}

	public ErrorCollector checkAndCollectAllErrors(ErrorCollectorCheck qc) {
		initCheckContext(qc);
		qc.check();
		return collectAllErrors(qc);
	}
	
	public void initCheckContext(ErrorCollectorCheck qc) {
		if (qc.context()==null) {
			qc.setCheckContext(this.context());
		}
	}

	public QualifiedError checkAndCollectError(String detail, Check qc) {
		if (qc.check()) return collectError(detail);
		return QualifiedError.NO_ERROR;
	}
	
	public ErrorCollector getErrorCollector() {
		return errorCollector;
	}
	
//	TODO: move this method on the context
	public ErrorCollectorCheck enhance(ErrorCollectorCheck check) {
		check.setCheckContext(context());
		return check;
	}

	public IBindingManager getBindings() {
		return context().getBindingManager();
	}
	
}

/**
 * Created on 06/mag/07
 */
package it.nch.fwk.checks.context;

import java.io.Serializable;

import it.nch.eb.common.utils.bindings.BindingManagerFactory;
import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.fwk.checks.errors.ErrorMessagesFactory;

/**
 * @author gdefacci
 */
public class CheckContext implements Serializable {

	private static final long	serialVersionUID	= -2731277886889997198L;
	
	protected IBindingManager	bindingManager;
	private ErrorMessagesFactory	errorsFactory;
	
	public CheckContext() {
		this(createBindingManager(), null);
	}
	
	public CheckContext(CheckContext context) {
		this( context.getBindingManager(), context.getErrorsFactory());
	}

	public CheckContext(ErrorMessagesFactory errorsFactory) {
		this(createBindingManager(), errorsFactory);
	}
	public CheckContext(IBindingManager bindingManager, ErrorMessagesFactory errorsFactory) {
		this.bindingManager = bindingManager;
		this.errorsFactory = errorsFactory;
	}

//	protected void initContext() {
//		bindingManager = createBindingManager();
//	}

	private static IBindingManager createBindingManager() {
		return BindingManagerFactory.instance.createBindingManager();
	}

	public IBindingManager getBindingManager() {
		return bindingManager;
	}
	
	public void setBindingManager(IBindingManager bindingManager) {
		this.bindingManager = bindingManager;
	}

	public ErrorMessagesFactory getErrorsFactory() {
		return errorsFactory;
	}

	public void setErrorsFactory(ErrorMessagesFactory errorsFactory) {
		this.errorsFactory = errorsFactory;
	}

}

/**
 * Created on 23/mag/07
 */
package it.nch.fwk.checks.errors;


import it.nch.eb.common.utils.loaders.PropertiesUtils;
import it.nch.fwk.checks.errors.processing.TemplateErrorProcessor;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author gdefacci
 */
public abstract class ErrorsFactoryProvider extends BaseErrorsFactoryProvider {
	
	private ErrorMessagesFactory  errorsFactory;
	
	public ErrorsFactoryProvider(InputStream errorMsgsStream) {
		this(PropertiesUtils.load(errorMsgsStream));
	}
	
	public ErrorsFactoryProvider(Properties properties) {
		super(properties);
		this.errorsFactory = createErrorFactory();
		registerErrors(errorsFactory);
	}

	protected void registerElementProcessor(String id, int errorType, TemplateErrorProcessor res) {
		getErrorFactory().registerElementProcessor(id, errorType, res);
	}

	public ErrorMessagesFactory getErrorFactory() {
		return this.errorsFactory;
	}
	
	/**
	 * give subclasses a chance to provide a custom <code>BaseErrorMessageFactory</code>
	 * which is the used to register various errors
	 * @return
	 */
	protected ErrorMessagesFactory createErrorFactory() {
		return new ErrorMessagesFactory();
	}
	
	protected abstract void registerErrors(BaseErrorMessageFactory errorsFactory);
	
}

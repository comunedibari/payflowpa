/**
 * Created on 22/ago/07
 */
package it.nch.fwk.checks.errors;

import it.nch.fwk.checks.errors.processing.ITemplateProvider;
import it.nch.fwk.checks.errors.processing.PropertiesTemplateProvider;
import it.nch.fwk.checks.errors.processing.TemplateErrorProcessor;

import java.util.Properties;

/**
 * @author gdefacci
 */
public abstract class BaseErrorsFactoryProvider {

	private Properties	properties;
	protected ITemplateProvider	templateProvider;
	
	public BaseErrorsFactoryProvider(Properties is) {
		this.properties = is;
	}

	public ITemplateProvider getTemplateProvider() {
		if (templateProvider==null) {
			createTemplateProvider();
		}
		return templateProvider;
	}

	public TemplateErrorProcessor register(String id) {
		return register(id, QualifiedError.UNQUALIFIED_ERROR_TYPE, false);
	}

	public TemplateErrorProcessor register(String id, int errorType) {
		return register(id, errorType, false);
	}

	public TemplateErrorProcessor register(String id, int errorType, boolean includeErrorInfo) {
		TemplateErrorProcessor res = new TemplateErrorProcessor(id, getTemplateProvider(), includeErrorInfo);
		registerElementProcessor(id, errorType, res);
		return res;
	}

	protected abstract void registerElementProcessor(String id, int errorType, TemplateErrorProcessor res);

	public void setTemplateProvider(ITemplateProvider templateProvider) {
		this.templateProvider = templateProvider;
	}

	/**
	 *	ovveride to provide  a custom template provider or use setter
	 */
	protected void createTemplateProvider()  {
		templateProvider = new PropertiesTemplateProvider(this.properties);
	}
	
	protected Properties getErrorProperties() {
		return properties;
	}

}

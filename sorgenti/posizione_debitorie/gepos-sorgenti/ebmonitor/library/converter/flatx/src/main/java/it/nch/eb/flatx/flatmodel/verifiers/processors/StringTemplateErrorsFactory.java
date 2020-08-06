/**
 * Created on 12/mar/08
 */
package it.nch.eb.flatx.flatmodel.verifiers.processors;

import it.nch.eb.flatx.flatmodel.verifiers.IBeanError;

import java.util.Map;
import java.util.TreeMap;

import org.antlr.stringtemplate.StringTemplate;


/**
 * @author gdefacci
 */
public class StringTemplateErrorsFactory implements ErrorMessagesFactory {

	private final StringTemplateProvider templateProvider;
	private final Map/*<String, StringTemplateAttributeSetter>*/ errorSetStrategy = new TreeMap();
	
	public StringTemplateErrorsFactory(StringTemplateProvider templateProvider) {
		this.templateProvider = templateProvider;
	}

	public final String process(IBeanError err) {
		return process(err.getErrorId(), err);
	}
	
	public final String process(String templateName, IBeanError err) {
		StringTemplate st = templateProvider.getTemplate(templateName);
		setAttributes(err, st);
		return st.toString();
	}

	private void setAttributes(IBeanError err, StringTemplate st) {
		StringTemplateAttributeSetter attributesSettingStrategy = (StringTemplateAttributeSetter) errorSetStrategy.get(err.getClass().getName());
		if (attributesSettingStrategy==null) {
			throw new IllegalStateException("cant get a processor for erros of type " + err.getClass().getName());
		}
		attributesSettingStrategy.setAttributes(st, err);
	}

	public StringTemplateErrorsFactory registerSetStrategy(Class/*<? extends IBeanError>*/ errorClass, 
			StringTemplateAttributeSetter attributesSettingStrategy) {
		this.errorSetStrategy.put(errorClass.getName(), attributesSettingStrategy);
		return this;
	}

}

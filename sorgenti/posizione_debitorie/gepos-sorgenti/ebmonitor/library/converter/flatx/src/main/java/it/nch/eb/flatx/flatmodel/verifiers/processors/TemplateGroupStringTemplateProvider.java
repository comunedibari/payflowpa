/**
 * Created on 12/mar/08
 */
package it.nch.eb.flatx.flatmodel.verifiers.processors;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;


/**
 * @author gdefacci
 */
public class TemplateGroupStringTemplateProvider implements StringTemplateProvider {

	final StringTemplateGroup stringTemplateGroup;
	
	public TemplateGroupStringTemplateProvider(StringTemplateGroup stringTemplateGroup) {
		this.stringTemplateGroup = stringTemplateGroup;
	}

	public StringTemplate getTemplate(String name) {
		return stringTemplateGroup.getInstanceOf(name);
	}

}

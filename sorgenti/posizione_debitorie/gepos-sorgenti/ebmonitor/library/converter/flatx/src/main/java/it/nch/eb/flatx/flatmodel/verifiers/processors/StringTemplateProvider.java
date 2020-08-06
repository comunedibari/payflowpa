/**
 * Created on 12/mar/08
 */
package it.nch.eb.flatx.flatmodel.verifiers.processors;

import org.antlr.stringtemplate.StringTemplate;


/**
 * abstraction over StringTemplateGroup and properties with string template placeholders
 * @author gdefacci
 */
public interface StringTemplateProvider {
	
	StringTemplate getTemplate(String name);

}

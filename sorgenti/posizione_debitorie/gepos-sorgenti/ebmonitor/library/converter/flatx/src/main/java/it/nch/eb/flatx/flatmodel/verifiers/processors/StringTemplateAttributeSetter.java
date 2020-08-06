/**
 * Created on 12/mar/08
 */
package it.nch.eb.flatx.flatmodel.verifiers.processors;

import it.nch.eb.flatx.flatmodel.verifiers.IBeanError;

import org.antlr.stringtemplate.StringTemplate;


/**
 * @author gdefacci
 */
public interface StringTemplateAttributeSetter {
	
	void setAttributes(StringTemplate st, IBeanError err);

}

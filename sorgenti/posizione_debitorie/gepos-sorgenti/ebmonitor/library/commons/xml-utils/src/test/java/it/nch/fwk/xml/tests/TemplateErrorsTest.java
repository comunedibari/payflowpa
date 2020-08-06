/**
 * Created on 19/mag/07
 */
package it.nch.fwk.xml.tests;

import it.nch.eb.common.utils.bindings.BindingManagerFactory;
import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.fwk.checks.errors.ErrorMessagesFactory;
import it.nch.fwk.checks.errors.processing.TemplateErrorProcessor;

import java.util.Properties;

import junit.framework.TestCase;

/**
 * @author gdefacci
 */
public class TemplateErrorsTest extends TestCase {
	
	public void test1() {
		Properties props  = new Properties();
		props.setProperty("id1", "we hope freemarker ${doesItRule}");
		FreemarkerTemplateProvider templateProvider = new FreemarkerTemplateProvider(props);
		ErrorMessagesFactory errorsFactory = new ErrorMessagesFactory();
		errorsFactory.registerElementProcessor(
				"error1", 0, 
				new TemplateErrorProcessor("id1", templateProvider)
					.mapBindingManager("doesItRule", "willFreemarkerRule"));
		
		IBindingManager bindings = BindingManagerFactory.instance.createBindingManager();
		bindings.define("willFreemarkerRule", "YES IT RULE");
		
		System.out.println(
		errorsFactory.createError("error1",0, bindings )
		);
	}

}

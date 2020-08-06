/**
 * Created on 12/mar/08
 */
package it.nch.eb.flatx.flatmodel.errors;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.Properties;

import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.flatx.flatmodel.verifiers.BaseBeanPropertyError;
import it.nch.eb.flatx.flatmodel.verifiers.IBeanError;
import it.nch.eb.flatx.flatmodel.verifiers.QualifiedErrors;
import it.nch.eb.flatx.flatmodel.verifiers.SimpleError;
import it.nch.eb.flatx.flatmodel.verifiers.processors.PropertiesStringTemplateProvider;
import it.nch.eb.flatx.flatmodel.verifiers.processors.StringTemplateAttributeSetter;
import it.nch.eb.flatx.flatmodel.verifiers.processors.StringTemplateErrorsFactory;
import it.nch.eb.flatx.flatmodel.verifiers.processors.StringTemplateProvider;
import it.nch.eb.flatx.flatmodel.verifiers.processors.TemplateGroupStringTemplateProvider;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

import junit.framework.Assert;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class ProcessorsTest extends TestCase {

	public void testProcSimple1() throws Exception {
		SimpleError serr = new SimpleError("errorId" , 6, new Integer(10), new Integer(12), new RuntimeException("holes"));
		StringTemplate stm = new StringTemplate("an error message $error.errorId$ $error.errorType$ $error.expectedValue$ $error.exception$ ");
		stm.setAttribute("error", serr);
		
		Assert.assertEquals("an error message errorId 6 12 java.lang.RuntimeException: holes ", stm.toString());
	}
	
	QualifiedErrors createSampleErrors() {
		QualifiedErrors.Base collector = new QualifiedErrors.Base();
		SimpleError se = new SimpleError("templateSimple", 5, "real", "expeceted"); 
		TestContainetr container = new TestContainetr();
		container.setPropInt(14);
		container.setPropStr("string property");
		BaseBeanPropertyError be = new BaseBeanPropertyError("templateProp2", 5, container,  "propInt", new Integer(10) );
		BaseBeanPropertyError be1 = new BaseBeanPropertyError(se, "templateProp", container,  "propStr", "a value");

		collector.add(se);
		collector.add(be);
		collector.add(be1);
		return collector;
	}
	
	public void testPropErrorProcessor()  {
		String expected = "errId:	templateSimple\n"			+
		"propertyName222:	propInt\n"  +
		"propertyName:	propStr\n"    ;
		Properties props = ResourceLoaders.PROPERTIES_CLASSPATH.load("it/nch/eb/flatx/flatmodel/errors/sample_stg.properties");
		StringTemplateProvider templateProvider = new PropertiesStringTemplateProvider(props);
		StringTemplateErrorsFactory errorsFactory = new StringTemplateErrorsFactory(templateProvider);
		StringTemplateAttributeSetter simpleErrStratefy = new StringTemplateAttributeSetter() {

			public void setAttributes(StringTemplate st, IBeanError err) {
				SimpleError se = (SimpleError) err;
				st.setAttribute("errId", se.getErrorId());
			}
			
		};
		StringTemplateAttributeSetter propertyErrStrategy = new StringTemplateAttributeSetter() {

			public void setAttributes(StringTemplate st, IBeanError err) {
				BaseBeanPropertyError error = (BaseBeanPropertyError) err;
				st.setAttribute("propName", error.getPropertyName());
			}
			
		};
		errorsFactory.registerSetStrategy(SimpleError.class, simpleErrStratefy);
		errorsFactory.registerSetStrategy(BaseBeanPropertyError.class, propertyErrStrategy);
		
		QualifiedErrors errs = createSampleErrors();
		StringBuffer sb = new StringBuffer();
		for (Iterator it = errs.getIterator(); it.hasNext();) {
			IBeanError beanError = (IBeanError) it.next();
			sb.append( errorsFactory.process(beanError) + "\n" );
		}
		System.out.println(sb.toString());
		assertEquals(expected, sb.toString());
	}
	
	public void testErrorProcessor()  {
		String expected = "errId:	templateSimple\n"			+
		"propertyName222:	propInt\n"  +
		"propertyName:	propStr\n"    ;
		
		Reader rdr = new InputStreamReader(ResourceLoaders.CLASSPATH.loadInputStream("it/nch/eb/flatx/flatmodel/errors/sample.stg"));
		StringTemplateGroup stringTemplateGroup = new StringTemplateGroup(rdr, DefaultTemplateLexer.class);
		StringTemplateProvider templateProvider = new TemplateGroupStringTemplateProvider(stringTemplateGroup);
		StringTemplateErrorsFactory errorsFactory = new StringTemplateErrorsFactory(templateProvider);
		StringTemplateAttributeSetter simpleErrStratefy = new StringTemplateAttributeSetter() {

			public void setAttributes(StringTemplate st, IBeanError err) {
				SimpleError se = (SimpleError) err;
				st.setAttribute("errId", se.getErrorId());
			}
			
		};
		StringTemplateAttributeSetter propertyErrStrategy = new StringTemplateAttributeSetter() {

			public void setAttributes(StringTemplate st, IBeanError err) {
				BaseBeanPropertyError error = (BaseBeanPropertyError) err;
				st.setAttribute("propName", error.getPropertyName());
			}
			
		};
		errorsFactory.registerSetStrategy(SimpleError.class, simpleErrStratefy);
		errorsFactory.registerSetStrategy(BaseBeanPropertyError.class, propertyErrStrategy);
		
		QualifiedErrors errs = createSampleErrors();
		StringBuffer sb = new StringBuffer();
		for (Iterator it = errs.getIterator(); it.hasNext();) {
			IBeanError beanError = (IBeanError) it.next();
			sb.append( errorsFactory.process(beanError) + "\n" );
		}
		System.out.println(sb.toString());
		assertEquals(expected, sb.toString());
	}
	
	static class TestContainetr {
		private int propInt;
		private String propStr;
		
		public int getPropInt() {
			return propInt;
		}
		
		public void setPropInt(int propInt) {
			this.propInt = propInt;
		}
		
		public String getPropStr() {
			return propStr;
		}
		
		public void setPropStr(String propStr) {
			this.propStr = propStr;
		}
		
	}
	
}

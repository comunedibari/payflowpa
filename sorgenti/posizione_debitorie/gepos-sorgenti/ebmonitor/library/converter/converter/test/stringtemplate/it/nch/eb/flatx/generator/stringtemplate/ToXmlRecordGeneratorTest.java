/**
 * Created on 16/gen/08
 */
package it.nch.eb.flatx.generator.stringtemplate;

import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.flatx.generator.XlsLoader;
import it.nch.eb.flatx.generator.XlsModel;
import it.nch.eb.flatx.generator.javamodel.FieldDeclStrategy;
import it.nch.eb.flatx.generator.javamodel.FixedValueFieldDeclStrategy;
import it.nch.eb.flatx.generator.javamodel.GetBindingValueFieldDeclStrategy;
import it.nch.eb.flatx.generator.javamodel.XPathDeclStrategy;
import it.nch.eb.flatx.generator.toxml.ToXmlClassGenerator;

import java.io.InputStream;

import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class ToXmlRecordGeneratorTest extends TestCase {
	
	public void testG1() throws Exception {
		XlsLoader loader = new XlsLoader();
		InputStream is = ResourceLoaders.CLASSPATH.loadInputStream("xls/Test1.xls");
		XlsModel[] models = loader.load(is);
		ToXmlClassGenerator generator = new ToXmlClassGenerator();
		generator.setStrategies(new FieldDeclStrategy[] {
			new GetBindingValueFieldDeclStrategy(),
			new XPathDeclStrategy(),
			new FixedValueFieldDeclStrategy(),
		});
		System.out.println(
		generator.generate(models, "obl.rule")
		);
	}

}

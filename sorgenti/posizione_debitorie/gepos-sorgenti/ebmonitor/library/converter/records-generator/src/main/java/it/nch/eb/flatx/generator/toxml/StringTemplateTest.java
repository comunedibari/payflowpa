/**
 * Created on 21/feb/08
 */
package it.nch.eb.flatx.generator.toxml;

import java.io.InputStreamReader;
import java.io.Reader;

import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.stringtemplate.TemplateWriter;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class StringTemplateTest extends TestCase {
	
	static class DataClass {
		boolean ok;
		Object value;
		public DataClass(boolean ok, Object value) {
			super();
			this.ok = ok;
			this.value = value;
		}
		
		public DataClass() {
		}
		
		public boolean isOk() {
			return ok;
		}
		
		public Object getValue() {
			return value;
		}
	}
	
	public void testConditional() throws Exception {
		String name = "it/nch/eb/flatx/generator/toxml/conditional.stg";
		Reader reader = new InputStreamReader( ResourceLoaders.CLASSPATH.loadInputStream(name) );
		TemplateWriter templ = new TemplateWriter(reader, System.out);
		templ.write("cond", "data", new DataClass(true,"fuck you"));
	}

}

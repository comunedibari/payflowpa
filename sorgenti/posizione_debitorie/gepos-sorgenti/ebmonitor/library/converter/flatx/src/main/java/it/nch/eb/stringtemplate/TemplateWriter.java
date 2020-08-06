/**
 * Created on 21/feb/08
 */
package it.nch.eb.stringtemplate;

import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.flatx.exceptions.PropertyAccessException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Iterator;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateErrorListener;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;


/**
 * @author gdefacci
 */
public class TemplateWriter {

	final StringTemplateGroup group;
	final Writer writer;
	private StringTemplateErrorListener	stringTemplateterrorListener;

	public TemplateWriter(Reader is, OutputStream os) {
		this(is, new PrintWriter(os), new FakeStringTemplateErrorListener());
	}
	public TemplateWriter(Reader is, OutputStream os, StringTemplateErrorListener listener) {
		this(is, new PrintWriter(os), listener);
	}

	public TemplateWriter(Reader is, Writer os, StringTemplateErrorListener listener) {
		if (is==null) throw new IllegalStateException("the stream does not exists");
		group = new StringTemplateGroup(is,DefaultTemplateLexer.class);
		group.registerRenderer(Integer.class, new IntegerRenderer());
		group.registerRenderer(String.class, new StringRenderer());
		group.registerRenderer(java.sql.Timestamp.class, new DateRenderer());
		group.registerRenderer(BigDecimal.class, new BigDecimalRenderer());
//		group.setStringTemplateWriter(NoIndentWriter.class);
		writer = os;
		this.stringTemplateterrorListener = listener;
	}

	public void write(String name, String valueName, Object obj) {
		try {
		StringTemplate template = getStringTemplate(name);
		template.setAttribute(valueName, obj);

			writer.write(template.toString());
		} catch (Exception e) {
			throw new PropertyAccessException("cant set attribute " + valueName
					+ " on template " + name
					+ " included in group " + this.group.getName() , e  );
		}
		flushWriter(writer);
	}

	public void write(String name, TemplateModel model) {
		try {
			StringTemplate template = getStringTemplate(name);
			for (Iterator it = model.names(); it.hasNext();) {
				String pname = (String) it.next();
				Object modelValue = model.get(pname);
				template.setAttribute(pname, modelValue);
			}
			writer.write(template.toString());
		} catch (Exception e) {
			throw new RuntimeException("error on template group:" + group!=null ? group.getName() : "null"
					+ " when using template " + name, e);
		}
		flushWriter(writer);
	}
	private StringTemplate getStringTemplate(String name) {
		StringTemplate res = group.getTemplateDefinition(name);
		res.setErrorListener(stringTemplateterrorListener);
		return res;
	}

	private static void flushWriter(Writer writer) {
		try {
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * FIXME used only in tests move
	 */
	public static TemplateWriter fromClasspath(String resLocation, OutputStream os) {
		Reader is = fromClasspath(resLocation);
		return new TemplateWriter(is, os, new FakeStringTemplateErrorListener());
	}

	public static TemplateWriter fromClasspath(String resLocation, Writer os) {
		Reader is = fromClasspath(resLocation);
		return new TemplateWriter(is, os, new FakeStringTemplateErrorListener());
	}

	private static InputStreamReader fromClasspath(String string) {
		return new InputStreamReader(ResourceLoaders.CLASSPATH.loadInputStream(string));
	}
}

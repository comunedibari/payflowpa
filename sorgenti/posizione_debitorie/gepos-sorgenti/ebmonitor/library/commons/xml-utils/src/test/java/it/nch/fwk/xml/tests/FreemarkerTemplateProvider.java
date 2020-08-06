/**
 * Created on 18/mag/07
 */
package it.nch.fwk.xml.tests;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import it.nch.fwk.checks.errors.processing.ITemplateProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author gdefacci
 */
public class FreemarkerTemplateProvider implements ITemplateProvider, Serializable {
	
	private static final long	serialVersionUID	= -7109393766061347479L;
	private transient Configuration configuration = null;
	private Class	objectWrapperClass = DefaultObjectWrapper.class;
	
	private final 	Properties	templates;

	public FreemarkerTemplateProvider(Properties templates) {
		this.templates = templates;
	}
	
	private DefaultObjectWrapper createObjectWrapper() {
		return new DefaultObjectWrapper();
	}
	
	public Class getObjectWrapperClass() {
		return objectWrapperClass;
	}

	public void setObjectWrapperClass(Class objectWrapperClass) {
		this.objectWrapperClass = objectWrapperClass;
		this.getConfiguration().setObjectWrapper(createObjectWrapper());
	}

	private Configuration loadPropeties(Properties templates) {
		StringTemplateLoader templateLoader = new StringTemplateLoader();
		for (Iterator it = templates.keySet().iterator(); it.hasNext();) {
			String templateName = (String) it.next();
			String template = templates.getProperty(templateName);
			templateLoader.putTemplate(templateName, template);
		}
		Configuration lconf = new Configuration();
		lconf.setTemplateLoader(templateLoader);
		lconf.setObjectWrapper(createObjectWrapper());
		return lconf;
	}
	
	public Template getTemplate(String templateId) {
		try {
			return getConfiguration().getTemplate(templateId);
		} catch (Exception e) {
			throw new RuntimeException("error getting template " + templateId, e);
		}
	}
	
	public String getTemplateValue(String id, Map templateContext) {
		try {
			Template temp = getTemplate(id);
			return processTemplate(temp, templateContext);
		} catch (Exception e) {
			throw new RuntimeException("error getting template " + id, e);
		}
	}
	
	private String processTemplate(Template template, Map templateContext) throws TemplateException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Writer out = new OutputStreamWriter(new PrintStream(baos));
		try {
			template.process(templateContext, out);
			out.flush();
			return getString(baos);
		} finally {
			out.close();
		}
	}
	
	private String getString(ByteArrayOutputStream baos)  {
		return new String( baos.toByteArray());
	}
	
	protected synchronized Configuration getConfiguration() {
		if (configuration == null) {
			configuration = loadPropeties(templates);
		}
		return configuration;
	}
	
}

/**
 * Created on 26/feb/08
 */
package it.nch.eb.stringtemplate;

import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.files.FixedColumnsLineFactory;
import it.nch.eb.flatx.files.LinesFactoryBuilder;
import it.nch.eb.flatx.files.model.InputFile;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import it.nch.eb.flatx.flatmodel.verifiers.BeanErrorCollector;
import it.nch.eb.flatx.flatmodel.verifiers.QualifiedErrors;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.antlr.stringtemplate.StringTemplateErrorListener;
import org.slf4j.Logger;


/**
 * @author gdefacci
 */
public class XmlGenerator {

	static final String TEMPLATE_NAME = "templateMain";
	static final String DEFAULT_TEMPLATE_PARAMETER_NAME = "model";
	
	static final Logger log = ResourcesUtil.createLogger(XmlGenerator.class);
	
	private LinesFactoryBuilder lfBuilder;
	private TemplateModel	templateModel;
	private StringTemplateErrorListener	stringTemplateterrorListener;
	
	public XmlGenerator() {
		this(FixedColumnsLineFactory.builder, BaseTemplateModel.EMPTY);
	}
	
	public XmlGenerator(TemplateModel model, StringTemplateErrorListener listener) {
		this(FixedColumnsLineFactory.builder, model,  listener);
	}
	public XmlGenerator(TemplateModel model) {
		this(FixedColumnsLineFactory.builder, model);
	}
	
	public XmlGenerator(LinesFactoryBuilder lfBuilder) {
		this(lfBuilder, BaseTemplateModel.EMPTY);
	}
	
	public XmlGenerator(LinesFactoryBuilder lfBuilder, TemplateModel model) {
		this(lfBuilder,model, null);
	}
	public XmlGenerator(LinesFactoryBuilder lfBuilder, TemplateModel model, StringTemplateErrorListener listener) {
		this.lfBuilder = lfBuilder;
		this.templateModel = model;
		if (listener!=null) this.stringTemplateterrorListener = listener;
		else stringTemplateterrorListener = new FakeStringTemplateErrorListener();
	}

	public void generateXml(String templateGroupClasspathLocation, IParser parser, InputFile inputFile,
			OutputStream output) {
		
		generateXml(templateGroupClasspathLocation, TEMPLATE_NAME, DEFAULT_TEMPLATE_PARAMETER_NAME, parser, inputFile, output);
	}
	
	public void generateXml(String templateGroupClasspathLocation, IParser parser, InputFile inputFile,
			Writer output) {
		
		generateXml(templateGroupClasspathLocation, TEMPLATE_NAME, DEFAULT_TEMPLATE_PARAMETER_NAME, parser, inputFile, output);
	}
	
	public void generateXml(Reader templateLocation, IParser parser, InputFile inputFile,
			Writer output) {
		
		generateXml(templateLocation, TEMPLATE_NAME, DEFAULT_TEMPLATE_PARAMETER_NAME, parser, inputFile, output);
	}

	public void generateXml(String templateGroupClasspathLocation, String templateName, String templateParameterName,
			IParser parser, InputFile inputFile, OutputStream output) {
		generateXml(templateGroupClasspathLocation, templateName, templateParameterName, parser, inputFile, new OutputStreamWriter(output));
	}
	
	public void generateXml(String templateGroupClasspathLocation, String templateName, String templateParameterName,
			IParser parser, InputFile inputFile, Writer output) {
		InputStream is = ResourceLoaders.CLASSPATH.loadInputStream(templateGroupClasspathLocation);
		Reader templ = new InputStreamReader(
				is);
		
		if (is==null) throw new IllegalStateException("the template " + templateGroupClasspathLocation + " does not exists in classpath");
		
		generateXml(templ, templateName, templateParameterName, parser, inputFile, output);
	}

	public void generateXml(Reader templateStream, String templateName, String templateParameterName, IParser parser,
			InputFile inputFile, Writer output) {
		
		TemplateWriter tw = createTemplateWriter(output, templateStream, stringTemplateterrorListener);
		generateXml(templateName, templateParameterName, parser, inputFile, tw);
	}

	private TemplateWriter createTemplateWriter(Writer output, Reader templ, StringTemplateErrorListener templateErrorListener) {
		return new TemplateWriter(templ, output, templateErrorListener);
	}

	public void generateXml(String templateName, String templateParameterName, IParser parser, InputFile stream, TemplateWriter tw) {
		BeanErrorCollector errorCollector = new QualifiedErrors.Base();
		Object model = parser.createObject(stream, lfBuilder, errorCollector);
//		log.debug("*********************************");
//		log.debug("rendering template " + templateName);
//		log.debug("with model \n" + model);
//		log.debug("*********************************");
		
		BaseTemplateModel tm = createNewTemplateModel();
		tm.set(templateParameterName, model);
		tw.write(templateName, tm);
	}

	private BaseTemplateModel createNewTemplateModel() {
		return new BaseTemplateModel(this.templateModel);
	}

}

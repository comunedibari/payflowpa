/**
 * Created on 21/mar/08
 */
package it.nch.eb.common.converter;

import it.nch.eb.flatx.files.IgnoreErrorsLinesFactory;
import it.nch.eb.flatx.files.model.InputFile;
import it.nch.eb.flatx.files.model.InputFileImpl;
import it.nch.eb.flatx.flatmodel.SerializableConversionService;
import it.nch.eb.flatx.flatmodel.flatfile.ParsersFactory;
import it.nch.eb.stringtemplate.BaseTemplateModel;
import it.nch.eb.stringtemplate.TemplateModel;
import it.nch.eb.stringtemplate.XmlGenerator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.antlr.stringtemplate.StringTemplateErrorListener;


/**
 * @author gdefacci
 */
public class FromFlatToXmlConverter implements SerializableConversionService {

	private static final long	serialVersionUID	= 1792640705875020728L;

	//	private EitherReaderOrString template;
	private StringHolder template;
	
	private ParsersFactory parsersFactory;
	private TemplateModel templateModel = new BaseTemplateModel();
	private StringTemplateErrorListener errorListener = new ThrowRuntimeExceptionStringTemplateErrorListener();
	
	private boolean strict = true;
	
	public FromFlatToXmlConverter() {
	}

	public void convert(InputStream is, OutputStream os) {
		convert(new InputStreamReader(is), new OutputStreamWriter(os));
	}
	
	public void convert(Reader input, Writer writer) {
//		spring is vomit: since consturctor injection doenst work we have to che that paraameters 
//		are already been set

		if (template == null) throw new NullPointerException("the template is null");
		if (parsersFactory == null) throw new NullPointerException("the parsersFactory is null");
		try {
			InputFile inputFile = createInputFile(input);
			XmlGenerator generator = createGenerator();
			generator.generateXml(template.getValue(), parsersFactory.createParser(), inputFile, writer);

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	protected InputFile createInputFile(Reader input) {
		return new InputFileImpl(input);
	}
	
	public void set(String name, Object obj) {
		templateModel.set(name, obj);
	}

	private XmlGenerator createGenerator() {
		if (strict) return new XmlGenerator(templateModel, errorListener);
		else return new XmlGenerator(IgnoreErrorsLinesFactory.builder, templateModel, errorListener);
	}

	/**
	 * TODO
	 * spring is a vomit: we are forced to use setter injiection (which is bad) since spring couldnt distinguish properly 
	 * different contructor signatures
	 */
	
	public ParsersFactory getParsersFactory() { return parsersFactory;	}

	public void setParsersFactory(ParsersFactory parsersFactory) {		this.parsersFactory = parsersFactory;	}
	
	public TemplateModel getTemplateModel() { return templateModel;	}
	
	public void setTemplateModel(TemplateModel templateModel) { this.templateModel = templateModel;	}

	public StringTemplateErrorListener getErrorListener() {return errorListener;}
	
	public void setErrorListener(StringTemplateErrorListener errorListener) {
		this.errorListener = errorListener;
	}
	
	public boolean isStrict() {	return strict;	}
	
	public void setStrict(boolean strict) { this.strict = strict;	}
	
	public void setTemplate(Reader rdr) { this.template = new StringHolder(rdr); }
	
	public void setTemplateClasspathLocation(String rdr) { this.template = new StringHolder(rdr); }
	

}

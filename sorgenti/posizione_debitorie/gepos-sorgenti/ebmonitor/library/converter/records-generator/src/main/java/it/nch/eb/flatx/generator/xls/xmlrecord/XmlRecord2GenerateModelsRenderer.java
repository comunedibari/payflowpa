/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.flatx.generator.ConversionGenerationModel;
import it.nch.eb.flatx.generator.OutputStreamGenerator;
import it.nch.eb.flatx.generator.xls.recordimpl.StringTemplateRecordRenderer;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;

import org.antlr.stringtemplate.StringTemplate;

/**
 * @author gdefacci
 *
 */
public class XmlRecord2GenerateModelsRenderer extends StringTemplateRecordRenderer implements OutputStreamGenerator{
	
	private final ConversionGenerationModel cm; 
	private final String className; 
	private final XmlRecordWithExtraFieldsInfo[] records;
	
	public XmlRecord2GenerateModelsRenderer(ConversionGenerationModel cm,
			String className, XmlRecordWithExtraFieldsInfo[] records) {
		this.cm = cm;
		this.className = className;
		this.records = records;
	}

	protected String getMainTemplateName() {
		return "compilationUnit";
	}

	protected Reader getTemplateReader() {
		return ResourceLoaders.CLASSPATH.loadReader("it/nch/eb/flatx/generator/xls/xmlrecord/xmlRecordModelsGenerator.stg");
	}
	
	public void generate(OutputStream os) {
		generate(os, className, cm.getBaseFolder(), cm.getPackageName(), cm.getModelsPackageName(), records, cm.getModelsInterfaces());
	}
	
	private void generate(OutputStream os,
			String className,
			File srcFolder,
			String packageName, 
			String modelPackageName,
			XmlRecordWithExtraFieldsInfo[] records,
			String[] modelsInterfaces) {
		PrintStream printer = new PrintStream(os);
		String res = generate(srcFolder, className, packageName, modelPackageName, records, modelsInterfaces);
		printer.print(res);
	}

	private String generate(File srcFolder,
			String className,
			String packageName, 
			String modelPackageName,
			XmlRecordWithExtraFieldsInfo[] records, 
			String[] modelsInterfaces) {
		StringTemplate template = getStringTemplate();
		template.setAttribute("className", className);
		String srcFldrPath = srcFolder.getAbsolutePath().replace('\\', '/');
		template.setAttribute("sourceFolderPath", srcFldrPath);
		template.setAttribute("pkgName", packageName);
		template.setAttribute("modelsPackageName", modelPackageName);
		template.setAttribute("records", records);
		if (modelsInterfaces==null || modelsInterfaces.length < 1)
			template.setAttribute("modelInterfaces", null);
		else 
			template.setAttribute("modelInterfaces", modelsInterfaces);
		return template.toString();
	}

}

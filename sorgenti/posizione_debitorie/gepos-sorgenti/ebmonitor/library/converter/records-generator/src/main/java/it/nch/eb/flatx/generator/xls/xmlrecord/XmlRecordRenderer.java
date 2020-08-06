/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.flatx.generator.xls.recordimpl.JavaClassStringTemplateRecordRenderer;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;

import org.antlr.stringtemplate.StringTemplate;

/**
 * @author gdefacci
 */
public class XmlRecordRenderer extends JavaClassStringTemplateRecordRenderer {
	
	
	public XmlRecordRenderer(String pkgName) {
		super(pkgName);
	}
	
	protected Reader getTemplateReader() {
		return ResourceLoaders.CLASSPATH.loadReader("it/nch/eb/flatx/generator/xls/xmlrecord/xmlrecord.stg");
	}

	public String generate(XmlRecordRecordSheetModel sheet) {
		StringTemplate template = getStringTemplate();
		template.setAttribute("pkgName", getTargetPackageName());
		template.setAttribute("recordSheet", sheet);
		return template.toString();
	}
	
	public void generate(OutputStream os, XmlRecordRecordSheetModel sheet) {
		PrintStream printer = new PrintStream(os);
		String res = generate(sheet);
		printer.print(res);
	}

}

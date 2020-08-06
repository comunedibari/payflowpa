/**
 * Created on 20/mar/08
 */
package it.nch.eb.flatx.generator.xls.recordimpl;

import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.flatx.generator.xls.RecordSheet;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

import org.antlr.stringtemplate.StringTemplate;


/**
 * @author gdefacci
 */
public class RecordSheetXmlRecordImplRenderer extends JavaClassStringTemplateRecordRenderer implements IRecordSheetXmlRecordImplRenderer {

	private final String	tipoRecord;
	private final String[] interfaces;

	public RecordSheetXmlRecordImplRenderer(String pkgName, String tipoRecord, String[] interfaces) {
		super(pkgName);
		this.tipoRecord = tipoRecord;
		this.interfaces = interfaces;
	}
	
	protected Reader getTemplateReader() {
		return new InputStreamReader( getTemplateStream() );
	}

	protected InputStream getTemplateStream() {
		return ResourceLoaders.CLASSPATH.loadInputStream("it/nch/eb/flatx/generator/xls/recordimpl/recordImpl.stg");
	}

	public String generate(RecordSheet sheet) {
		StringTemplate template = getStringTemplate();
		setAttributes(sheet, template);
		return template.toString();
	}

	protected void setAttributes(RecordSheet sheet, StringTemplate template) {
		template.setAttribute("pkgName", getTargetPackageName());
		template.setAttribute("tipoRecord", tipoRecord);
		template.setAttribute("recordSheet", sheet);
		template.setAttribute("interfaces", interfaces);
	}
	
	public void generate(OutputStream os, RecordSheet sheet) {
		PrintStream printer = new PrintStream(os);
		String res = generate(sheet);
		printer.print(res);
	}

}

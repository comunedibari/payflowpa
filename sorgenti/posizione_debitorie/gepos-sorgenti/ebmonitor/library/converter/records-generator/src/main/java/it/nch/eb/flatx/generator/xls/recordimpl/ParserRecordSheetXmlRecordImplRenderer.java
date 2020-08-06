/**
 * Created on 20/mar/08
 */
package it.nch.eb.flatx.generator.xls.recordimpl;

import it.nch.eb.common.utils.NamingStrategy;
import it.nch.eb.flatx.generator.xls.RecordSheet;

import java.io.InputStream;

import org.antlr.stringtemplate.StringTemplate;


/**
 * @author gdefacci
 */
public class ParserRecordSheetXmlRecordImplRenderer extends RecordSheetXmlRecordImplRenderer implements IRecordSheetXmlRecordImplRenderer {

	private final String 	modelsPackageName;
	
	private final NamingStrategy modelNamingStrategy;
	
	public ParserRecordSheetXmlRecordImplRenderer(String pkgName, String tipoRecord, String modelsPackageName, String[] interfaces, NamingStrategy modelNamingStrategy) {
		super(pkgName, tipoRecord, interfaces);
		this.modelsPackageName = modelsPackageName;
		this.modelNamingStrategy = modelNamingStrategy;
	}
	
	protected InputStream getTemplateStream() {
		return ParserRecordSheetXmlRecordImplRenderer.class.getResourceAsStream("parserRecordImpl.stg");
	}
	
	protected void setAttributes(RecordSheet sheet, StringTemplate template) {
		super.setAttributes(sheet, template);
		template.setAttribute("modelsPkgName", modelsPackageName);
		template.setAttribute("modelClassName", this.modelNamingStrategy.apply(sheet.getName()));
	}
	
	public NamingStrategy getModelNamingStrategy() {
		return modelNamingStrategy;
	}

}

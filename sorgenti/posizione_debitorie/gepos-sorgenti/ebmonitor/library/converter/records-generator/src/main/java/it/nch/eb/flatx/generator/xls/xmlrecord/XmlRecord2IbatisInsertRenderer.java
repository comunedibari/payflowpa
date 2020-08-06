/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.flatx.generator.xls.recordimpl.StringTemplateRecordRenderer;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;

import org.antlr.stringtemplate.StringTemplate;

/**
 * @author gdefacci
 *
 */
public class XmlRecord2IbatisInsertRenderer extends StringTemplateRecordRenderer {
	
	protected String getMainTemplateName() {
		return "templateMain";
	}

	protected Reader getTemplateReader() {
		return ResourceLoaders.CLASSPATH.loadReader("it/nch/eb/flatx/generator/xls/xmlrecord/ibatis-sql-map.stg");
	}
	
	public void generate(OutputStream os, 
			String parameterClassName, String typeAliasName, String tabName, 
			InsertStatementModel insertStm,
			UpdateStatementModel updateStm ) {
		generate(os, parameterClassName, typeAliasName, tabName, null, insertStm, new UpdateStatementModel[] { updateStm });
	}
	
	public void generate(OutputStream os, 
			String parameterClassName, String typeAliasName, String tabName,
			IBatisResultMapModel resultMapModel,
			InsertStatementModel insertStm,
			UpdateStatementModel[] updateStaments ) {
		PrintStream printer = new PrintStream(os);
		String res = generate(parameterClassName, typeAliasName, tabName, 
				resultMapModel,
				insertStm,
				updateStaments);
		printer.print(res);
	}

	private String generate(  
			String parameterClassName, String typeAliasName, String tabName, 
			IBatisResultMapModel resultMapModel,
			InsertStatementModel insertStm,
			UpdateStatementModel[] updateStaments ) {
		StringTemplate template = getStringTemplate();
		template.setAttribute("tableName", tabName);
		if (resultMapModel!=null) {
			template.setAttribute("resultMap", resultMapModel);
		}
		template.setAttribute("insertStatement", insertStm);
		template.setAttribute("updateStatements", updateStaments);
		template.setAttribute("ibatisParameterClass", parameterClassName);
		template.setAttribute("typeAliasName", typeAliasName);
		return template.toString();
	}

}

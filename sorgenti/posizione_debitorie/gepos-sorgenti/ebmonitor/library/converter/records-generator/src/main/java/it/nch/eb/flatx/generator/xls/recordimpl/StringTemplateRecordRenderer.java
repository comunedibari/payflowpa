/**
 * 
 */
package it.nch.eb.flatx.generator.xls.recordimpl;

import java.io.Reader;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

/**
 * @author gdefacci
 *
 */
public abstract class StringTemplateRecordRenderer {

	private static final String MAIN_TEMPLATE = "compilationUnit";

	/**
	 * 
	 */
	public StringTemplateRecordRenderer() {
		super();
	}

	public final StringTemplateGroup newTemplateGroup() {
		return new StringTemplateGroup( getTemplateReader(), DefaultTemplateLexer.class);
	}

	protected String getMainTemplateName() {
		return MAIN_TEMPLATE;
	}

	protected final StringTemplate getStringTemplate() {
		return newTemplateGroup().getTemplateDefinition(getMainTemplateName());
	}

	protected abstract Reader getTemplateReader();

}
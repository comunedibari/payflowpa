/**
 * Created on 15/apr/08
 */
package it.nch.eb.flatx.generator.xls;

import it.nch.eb.flatx.generator.xls.recordimpl.RecordSheetXmlRecordImplRenderer;

import java.io.InputStreamReader;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;


/**
 * @author gdefacci
 */
public class XlsOrFactory implements XlsCustomsFactory {
	
	public XlsCustom create(GenTypeModel type, String[] args, String basePath) {
		return new XlsOrExpression(type, args, basePath);
	}
	
	static class XlsOrExpression implements XlsCustom {
		
		private final StringTemplateGroup templateGroup = 
			new StringTemplateGroup( 
					new InputStreamReader( RecordSheetXmlRecordImplRenderer.class.getResourceAsStream("recordImpl.stg") ),
					DefaultTemplateLexer.class);
		
		private final GenTypeModel 		xlsType;
		private final String 		basePath;
		private final String[]		args;

		public XlsOrExpression(GenTypeModel xlsType, String[] args, String basePath) {
			this.xlsType = xlsType;
			this.basePath = basePath;
			if (basePath!=null) {
				this.args = args;
			} else {
				this.args = XlsUtil.stripLeadingPath(basePath, args);
			}
		}

		public XlsExpression copy() {
			return copy(null);
		}

		public String getValue() {
			StringTemplate template = templateGroup.getTemplateDefinition("orExpressions");
			template.setAttribute("type", this.xlsType);
			for (int i = 0; i < args.length; i++) template.setAttribute("exprs", args[i]);
			return template.toString();
		}

		public XlsExpression copy(String basePath) {
			return new XlsOrExpression(xlsType, args, basePath);
		}

		public String getBasePath() {
			return basePath;
		}
		
	}

}

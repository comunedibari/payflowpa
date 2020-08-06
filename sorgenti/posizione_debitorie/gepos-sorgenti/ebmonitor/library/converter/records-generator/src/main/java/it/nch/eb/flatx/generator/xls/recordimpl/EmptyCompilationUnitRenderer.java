/**
 * 30/nov/2009
 */
package it.nch.eb.flatx.generator.xls.recordimpl;

import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.generator.OutputStreamGenerator;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;

import org.antlr.stringtemplate.StringTemplate;

/**
 * @author gdefacci
 */
public class EmptyCompilationUnitRenderer extends StringTemplateRecordRenderer  implements OutputStreamGenerator{ 

	private final String packageName;
	private final String className;
	
	public EmptyCompilationUnitRenderer(String packageName, String className) {
		this.packageName = packageName;
		this.className = className;
	}

	protected Reader getTemplateReader() {
		return ResourceLoaders.CLASSPATH.loadReader("it/nch/eb/flatx/generator/xls/recordimpl/emptyCompilationUnit.stg");
	}

	public void generate(OutputStream os) {
		PrintStream ps = null;
		try {
			ps = new PrintStream(os);
			StringTemplate template = getStringTemplate();
			template.setAttribute("className", className);
			template.setAttribute("packageName", packageName);
			ps.print( template.toString() );
		} finally {
			ResourcesUtil.close(ps);
		}
	}

}

/**
 * Created on 04/gen/08
 */
package it.nch.eb.flatx.generator;

import it.nch.eb.common.utils.loaders.ReaderFactory;

import java.io.File;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;


/**
 * @author aborym, gdefacci
 */
public class Generator {
	
	final StringTemplateGroup templateGroup;

	public Generator(ReaderFactory readrFactory) {
		this.templateGroup = new StringTemplateGroup(readrFactory.createReader(),DefaultTemplateLexer.class );
	}
	
	public StringTemplate template(String name) {
		return templateGroup.getInstanceOf(name);
	}
	
	public static File createFolder(File folder) {
		if (folder.isFile()) throw new IllegalStateException(folder.getAbsolutePath() + " is a file, not a folder");
		if (folder.isDirectory() && folder.exists()) return folder;
		else {
			folder.mkdirs();
			if (!folder.exists()) 
				throw new IllegalStateException("could not create folder " + folder.getAbsolutePath());
			else 
				return folder;
		}
		
	}
	
	public static File createFolder(String realPath) {
		File resFolder = new File(realPath);
		if (!resFolder.exists()) {
			resFolder = createFolder(resFolder);
		}
		return resFolder;
	}

}

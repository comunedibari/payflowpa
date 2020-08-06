/**
 * Created on 03/gen/08
 */
package it.nch.eb.flatx.generator;

import it.nch.eb.common.utils.loaders.ReadersFactories;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;
import it.nch.eb.flatx.generator.javamodel.FieldDecl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.antlr.stringtemplate.StringTemplate;


/**
 * FIXME: erase, never used
 * @author gdefacci
 */
public class ModelClassFromXmlRecord extends JavaGenerator {

	public ModelClassFromXmlRecord() {
		super(ReadersFactories.instance.classpathAnt("it/nch/eb/flatx/generator/flatmodels/FlatModelGenerator.stg") );
	}
	
	public String generate(String packageName, String className, XmlRecord rec) {
		IXPathToObjectConversionInfo[] cis = rec.getConversionInfos();
		StringTemplate compilationUnit = template("compilationUnitDecl");
		compilationUnit.setAttribute("packageName", packageName);
		compilationUnit.setAttribute("className", className);
		for (int i = 0; i < cis.length; i++) {
			IXPathToObjectConversionInfo toci = cis[i];
			Class targetType = toci.getTargetClass();
			if (targetType!=Void.TYPE) {
				compilationUnit.setAttribute("props", new FieldDecl( toci.getName(), targetType.getName() ) );
			}
		}
		return compilationUnit.toString();
	}
	
	public void generate(File f, String packageName, XmlRecord rec) {
		generate(f, packageName, rec.getName() + "Model" , rec);	
	}
	
	public String generate(String packageName, XmlRecord rec) {
		return generate(packageName, rec.getName() + "Model" , rec);	
	}
	
	public void generate(File prjFolder, String packageName, String className, XmlRecord rec) {
		PrintStream out = null;
		File realf;
		try {
			File parentFolder = createPackageFolder(prjFolder, packageName);
			realf = new File( parentFolder, className + ".java" );
			System.out.println("creting " + realf.getAbsolutePath());
			out = new PrintStream( new FileOutputStream(realf) );
			out.print(generate(packageName, className, rec));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			ResourcesUtil.close(out);
		}
	}

}

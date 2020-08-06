/**
 * Created on 03/gen/08
 */
package it.nch.eb.flatx.generator.flatmodels;

import it.nch.eb.common.utils.NamingStrategy;
import it.nch.eb.common.utils.loaders.ReadersFactories;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.flatmodel.flatfile.ParserRecord;
import it.nch.eb.flatx.flatmodel.objectconverters.ToObjectConversionInfo;
import it.nch.eb.flatx.generator.JavaGenerator;
import it.nch.eb.flatx.generator.javamodel.FieldDecl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.antlr.stringtemplate.StringTemplate;


/**
 * @author gdefacci
 */
public class FileParserModelGeneratorFromRecord extends JavaGenerator {

	public FileParserModelGeneratorFromRecord() {
//		super(FileParserModelGeneratorFromRecord.class, "FlatModelGenerator.stg");
//		super("it/nch/eb/flatx/generator/flatmodels/FlatModelGenerator.stg");
		super(ReadersFactories.instance.classpathAnt("it/nch/eb/flatx/generator/flatmodels/FlatModelGenerator.stg") );
	}
	
	public String generate(String apackageNameckageName, String className, ParserRecord rec) {
		return generate(apackageNameckageName, className, null, rec);
	}
	public String generate(String apackageNameckageName, String className, String[] interfaces, ParserRecord rec) {
		ToObjectConversionInfo[] conversions = rec.getConversionInfos();
		
		StringTemplate compilationUnit = template("compilationUnitDecl");
		compilationUnit.setAttribute("packageName", apackageNameckageName);
		compilationUnit.setAttribute("className", className);
		if (interfaces!=null && interfaces.length>0) compilationUnit.setAttribute("interfaces", interfaces);
		for (int i = 0; i < conversions.length; i++) {
			String targetTypeName = getTypeName( conversions[i].getToObjectConverter().getConversionTargetClass() );
			if (!targetTypeName.equals(Void.TYPE)) {
				compilationUnit.setAttribute("props", 
						new FieldDecl(conversions[i].getName(), 
						targetTypeName ) );
			}
		}
		return compilationUnit.toString();
	}
	
	public void generate(File f, String packageName, ParserRecord rec) {
		generate(f, packageName, getClassNameForRecord(rec) , rec);	
	}
	
	
	public String generate(String packageName, ParserRecord rec) {
		return generate(packageName, getClassNameForRecord(rec), null , rec);	
	}

	private String getClassNameForRecord(ParserRecord rec) {
//		return rec.getName() + "Model";
		return RECORD_TO_MODEL_NAMING_STRATEGY.apply(rec.getName());
	}
	
	public static final NamingStrategy RECORD_TO_MODEL_NAMING_STRATEGY =
		new NamingStrategy() {

			public String apply(String orig) {
				return orig + "Model";
			}
		
	};
	
	public void generate(File prjFolder, String packageName, String className, ParserRecord rec) {
		generate(prjFolder, packageName, className, null, rec); 
	}
	
	public void generate(File prjFolder, String packageName, String className, String[] interfaces,  ParserRecord rec) {
		PrintStream out = null;
		File realf;
		try {
			File parentFolder = createPackageFolder(prjFolder, packageName);
			realf = new File( parentFolder, className + ".java" );
			System.out.println("creating " + realf.getAbsolutePath());
			out = new PrintStream( new FileOutputStream(realf) );
			out.print(generate(packageName, className, interfaces, rec));
			System.out.println("created file [" + realf.getAbsolutePath() + "]");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			ResourcesUtil.close(out);
		}
	}

	public void generate(File baseSourcefolder, String packageName,
			String[] interfaces, ParserRecord parserRecord) {
		generate(baseSourcefolder, packageName, getClassNameForRecord(parserRecord), interfaces, parserRecord);
		
	}
	
}

/**
 * Created on 03/gen/08
 */
package it.nch.eb.flatx.generator;

import it.nch.eb.common.utils.loaders.ReadersFactories;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.ExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.converters.NullConverter;
import it.nch.eb.flatx.generator.javamodel.FieldDecl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.antlr.stringtemplate.StringTemplate;


/**
 * @author gdefacci
 */
public class StringModelClassFromRecord extends JavaGenerator {
	
	private static final RecordNameStrategy DEFAULT_NAME_STRATEGY = 
		new RecordNameStrategy() {

			public String get(IRecord rec) {
				return rec.getName() + "Model";
			}
		
		};
		
	private final RecordNameStrategy recordToModelNameStrategy;		

	public StringModelClassFromRecord() {
		this(DEFAULT_NAME_STRATEGY);
	}
	
	public StringModelClassFromRecord(RecordNameStrategy nmStrategy) {
		super(ReadersFactories.instance.classpathAnt("it/nch/eb/flatx/generator/flatmodels/FlatModelGenerator.stg") );
		if (nmStrategy==null) this.recordToModelNameStrategy = DEFAULT_NAME_STRATEGY;
		else this.recordToModelNameStrategy = nmStrategy;
	}
	
	public String generate(String apackageNameckageName, String className, IRecord rec) {
		return generate(apackageNameckageName, null, className , rec);
	}
	public String generate(String apackageNameckageName, String[] interfaces, String className, IRecord rec) {
		ExpressionConversionInfo[] cis = rec.getConversionInfos();
		
		StringTemplate compilationUnit = template("compilationUnitDecl");
		compilationUnit.setAttribute("packageName", apackageNameckageName);
		compilationUnit.setAttribute("className", className);
		if (interfaces!=null && interfaces.length>0) compilationUnit.setAttribute("interfaces", interfaces);
		for (int i = 0; i < cis.length; i++) {
			if (!NullConverter.isNull(cis[i].getConverter())) {
				compilationUnit.setAttribute("props", new FieldDecl(cis[i].getName(), "String") );
			} 
		}
		return compilationUnit.toString();
	}
	
	public void generate(File f, String packageName, IRecord rec) {
		generate(f, packageName, recordToModelNameStrategy.get(rec) , rec);	
	}
	public void generate(File f, String packageName, String[] interfaces, IRecord record) {
		generate(f, packageName, interfaces, recordToModelNameStrategy.get(record) , record);
	}
	
	public String generate(String packageName, IRecord rec) {
		return generate(packageName, recordToModelNameStrategy.get(rec) , rec);	
	}
	
	public void generate(File prjFolder, String packageName, String className, IRecord rec) {
		generate(prjFolder, packageName, null, className, rec);
	}
	public void generate(File prjFolder, String packageName, String[] interfaces, String className, IRecord rec) {
		PrintStream out = null;
		File realf;
		try {
			File parentFolder = createPackageFolder(prjFolder, packageName);
			realf = new File( parentFolder, className + ".java" );
			out = new PrintStream( new FileOutputStream(realf) );
			out.print(generate(packageName, interfaces, className, rec));
			System.out.println("created file [" + realf.getAbsolutePath() + "]");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			ResourcesUtil.close(out);
		}
	}
	
	
	
}

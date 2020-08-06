/**
 * Created on 12/feb/08
 */
package it.nch.eb.flatx.generator.flatmodels;

import it.nch.eb.common.utils.loaders.ReadersFactories;
import it.nch.eb.flatx.flatmodel.conversioninfo.IConversionInfo;
import it.nch.eb.flatx.flatmodel.flatfile.parser.BeanParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.LineParser;
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
public class FileParserModelGenerator extends JavaGenerator {
	
	public FileParserModelGenerator() {
//		super(FileParserModelGenerator.class, "FlatModelGenerator.stg");
//		super("it/nch/eb/flatx/generator/flatmodels/FlatModelGenerator.stg");
		super(ReadersFactories.instance.classpathAnt("it/nch/eb/flatx/generator/flatmodels/FlatModelGenerator.stg") );
	}
	
	public void generate(File prjFolder, String packageName, String className, IParser rec, String[] interfaces) {
		if (rec instanceof LineParser) generate(prjFolder, packageName, className, (LineParser)rec, interfaces);
		else if (rec instanceof BeanParser) generate(prjFolder, packageName, className, (BeanParser)rec, interfaces);
		else throw new IllegalStateException("this [" + rec + "]is a sequence parser and we cant generate a copmpilation unit");
	}
	
	public String generate(String packageName, String className, IParser rec) {
		if (rec instanceof LineParser) generate(packageName, className, (LineParser)rec);
		else if (rec instanceof BeanParser) generate(packageName, className, (BeanParser)rec);
		throw new IllegalStateException();
	}
	
	public void generate(File prjFolder, String packageName, String className, IParser rec) {
		generate(prjFolder, packageName, className, rec, null);
	}
	
	public void generate(File prjFolder, String packageName, String className, LineParser rec) {
		String cu = generate(packageName, className, rec);
		generateCompilationUnit(prjFolder, packageName, className, cu);
	}
	
	public void generate(File prjFolder, String packageName, String className, String[] interfaces, IParser[] parsers) {
		String cu = generate(packageName, className, interfaces, parsers);
		generateCompilationUnit(prjFolder, packageName, className, cu);
	}
	
	public void generate(File prjFolder, String packageName, String className, IParser[] parsers) {
		String cu = generate(packageName, className, null, parsers);
		generateCompilationUnit(prjFolder, packageName, className, cu);
	}
	
	public void generate(File prjFolder, String packageName, String className, LineParser rec, String[] interfaces) {
		String cu = generate(packageName, className, rec, interfaces);
		generateCompilationUnit(prjFolder, packageName, className, cu);
	}
	
	public void generate(File prjFolder, String packageName, String className, BeanParser rec, String[] interfaces) {
		String cu = generate(packageName, className, rec, interfaces);
		generateCompilationUnit(prjFolder, packageName, className, cu);
	}
	
	public String generate(String packageName, String className, BeanParser rec) {
		return generate(packageName, className, rec, null);
	}
	
	public String generate(String packageName, String className, LineParser rec) {
		return generate(packageName, className, rec, null);
	}
	
	private void generateCompilationUnit(File prjFolder, String packageName, String className,
			String compilationUnit) {
		File genFile = null;
		try {
			File f = createPackageFolder(prjFolder, packageName);
			genFile = new File(f, className + ".java");
			if (!genFile.exists()) genFile.createNewFile();
			PrintStream fos = new PrintStream(new FileOutputStream(genFile));
			fos.print( compilationUnit );
			System.out.println("written file " + genFile);
		} catch (Exception e) {
			throw new RuntimeException("could not open file " + genFile == null? "null": genFile.getAbsolutePath(), e);
		}
	}
	
	public String generate(String packageName, String className, BeanParser rec, String[] interfaces) { 
		IParser[] parsers = rec.getParsers();
		return generate(packageName, className, interfaces, parsers);
	}

	public String generate(String packageName, String className, String[] interfaces, IParser[] parsers) {
		StringTemplate compilationUnit = setCommonAttributes(packageName, className, interfaces);
		for (int i = 0; i < parsers.length; i++) {
			IParser parser = parsers[i];
			Class type = parser.getObjectBuilder().getProductClass();
			System.out.println("***** " + type);
			if (type!=Void.TYPE) {
				compilationUnit.setAttribute("props", new FieldDecl( parser.getName(), type.getName() ));
			}
			if (parser instanceof LineParser) {
				generate(packageName, className, (LineParser)parser);
			}
		}
		return compilationUnit.toString();
	}
	
	public String generate(String packageName, String className, LineParser rec, String[] interfaces) {
		StringTemplate compilationUnit = setCommonAttributes(packageName, className, interfaces);

		IConversionInfo[] cis = rec.getConversionInfos();
		for (int i = 0; i < cis.length; i++) {
			IConversionInfo conversionInfo = cis[i];
			String typeName = getTyperFor(conversionInfo);
			if (!typeName.equals(Void.TYPE.getName())) {
				compilationUnit.setAttribute("props", 
						new FieldDecl( conversionInfo.getName(), typeName ) );
			}
		}
		return compilationUnit.toString();		
	}
	
	private StringTemplate setCommonAttributes(String packageName, String className, String[] interfaces) {
		StringTemplate compilationUnit = template("compilationUnitDecl");
		compilationUnit.setAttribute("packageName", packageName);
		compilationUnit.setAttribute("className", className);
		if (interfaces!=null) compilationUnit.setAttribute("interfaces", interfaces);
		return compilationUnit;
	}
	
	private String getTyperFor(IConversionInfo conversionInfo) {
		if (conversionInfo instanceof ToObjectConversionInfo) {
			return getTypeName( ((ToObjectConversionInfo)conversionInfo).getToObjectConverter().getConversionTargetClass() );
		}
		return String.class.getName();
	}

}

/**
 * Created on 03/gen/08
 */
package it.nch.eb.flatx.generator.flatmodels;

import it.nch.eb.common.utils.loaders.ReadersFactories;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;
import it.nch.eb.flatx.generator.JavaGenerator;
import it.nch.eb.flatx.generator.javamodel.FieldDecl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.antlr.stringtemplate.StringTemplate;


/**
 * @author gdefacci
 */
public class FileParserModelGeneratorFromXmlRecord extends JavaGenerator {
	
	private static final FieldDecl[] NO_EXTRA_FIELDS = new FieldDecl[0];

	public FileParserModelGeneratorFromXmlRecord() {
		super(ReadersFactories.instance.classpathAnt("it/nch/eb/flatx/generator/flatmodels/FlatModelGeneratorNoLineNumber.stg") );
	}
	
	public FileParserModelGeneratorFromXmlRecord(String templateName) {
		super(ReadersFactories.instance.classpathAnt(templateName) );
	}
	
	public String generate(String apackageNameckageName, String className, XmlRecord rec) {
		return generate(apackageNameckageName, className, null, rec, NO_EXTRA_FIELDS);
	}
	public String generate(String packageName, String className, String[] interfaces, XmlRecord rec, FieldDecl[] extraFields) {
		IXPathToObjectConversionInfo[] conversions = rec.getConversionInfos();
		
		StringTemplate compilationUnit = template("compilationUnitDecl");
		compilationUnit.setAttribute("packageName", packageName);
		compilationUnit.setAttribute("className", className);
		if (interfaces!=null && interfaces.length>0) compilationUnit.setAttribute("interfaces", interfaces);
		for (int i = 0; i < conversions.length; i++) {
			try {
				Class targetType = conversions[i].getTargetClass();
				if (!targetType.equals(Void.TYPE)) {
					String typeNm = getTypeName(targetType);
					compilationUnit.setAttribute("props", 
							new FieldDecl(conversions[i].getName(), 
							typeNm ) );
				}
			} catch (Exception e) {
				throw new RuntimeException("error while generating from " + conversions[i], e);
			}
		}
		if (extraFields!=null && extraFields.length > 0) {
			for (int i = 0; i < extraFields.length; i++) {
				FieldDecl fieldDecl = extraFields[i];
				compilationUnit.setAttribute("props", fieldDecl);
			}
		}
		return compilationUnit.toString();
	}
	
	public void generate(File f, String packageName, XmlRecord rec) {
		generate(f, packageName, getClassNameForRecord(rec) , rec);	
	}
	
	
	public String generate(String packageName, XmlRecord rec) {
		return generate(packageName, getClassNameForRecord(rec), null , rec, NO_EXTRA_FIELDS);	
	}

	public static String getClassNameForRecord(XmlRecord rec) {
		String recNm = rec.getName();
		if (recNm.endsWith("Record")) {
			recNm = recNm.substring(0, recNm.length() - 6);
		}
		return recNm + "Model";
	}
	
	public void generate(File prjFolder, String packageName, String className, XmlRecord rec) {
		generate(prjFolder, packageName, className, null, rec, NO_EXTRA_FIELDS); 
	}
	
	public void generate(File prjFolder, String packageName, String cuName, String className, String[] interfaces,  XmlRecord rec, FieldDecl[] extraFlds) {
		PrintStream out = null;
		File realf;
		try {
			File parentFolder = createPackageFolder(prjFolder, packageName);
			realf = new File( parentFolder, cuName );
			System.out.println("creating " + realf.getAbsolutePath());
			out = new PrintStream( new FileOutputStream(realf) );
			out.print(generate(packageName, className, interfaces, rec, extraFlds));
			System.out.println("created file [" + realf.getAbsolutePath() + "]");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			ResourcesUtil.close(out);
		}
	}
	
	public void generate(File prjFolder, String packageName, String className, String[] interfaces,  XmlRecord rec, FieldDecl[] extraFlds) {
		generate(prjFolder, packageName, className + ".java" , className, interfaces, rec, extraFlds);
	}
	
	public void generate(File baseSourcefolder, String packageName,
			String[] interfaces, XmlRecord parserRecord) {
		generate(baseSourcefolder, packageName, getClassNameForRecord(parserRecord), interfaces, parserRecord, NO_EXTRA_FIELDS);
		
	}
	
	public void generate(File baseSourcefolder, String packageName,
			String[] interfaces, XmlRecord parserRecord, FieldDecl[] extraFlds) {
		generate(baseSourcefolder, packageName, getClassNameForRecord(parserRecord), interfaces, parserRecord, extraFlds);
	}
	
}

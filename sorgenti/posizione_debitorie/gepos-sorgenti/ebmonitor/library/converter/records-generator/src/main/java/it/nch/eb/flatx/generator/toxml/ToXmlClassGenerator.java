/**
 * Created on 15/gen/08
 */
package it.nch.eb.flatx.generator.toxml;

import java.util.ArrayList;
import java.util.List;

import org.antlr.stringtemplate.StringTemplate;

import it.nch.eb.common.utils.loaders.ReadersFactories;
import it.nch.eb.flatx.generator.Generator;
import it.nch.eb.flatx.generator.XlsModel;
import it.nch.eb.flatx.generator.javamodel.ClassDecl;
import it.nch.eb.flatx.generator.javamodel.FieldDecl;
import it.nch.eb.flatx.generator.javamodel.FieldDeclStrategy;


/**
 * @author gdefacci
 */
public class ToXmlClassGenerator extends Generator {
	
	final static String[] PUBLIC_FINAL = new String[] {
		"public", "final", 
	};
	
	private FieldDeclStrategy[] strategies;
	
	public ToXmlClassGenerator() {
//		super(ToXmlClassGenerator.class, "ToXmlRecord.st");
//		super("it/nch/eb/flatx/generator/toxml/ToXmlRecord.st");
		super(ReadersFactories.instance.classpathAnt("it/nch/eb/flatx/generator/toxml/ToXmlRecord.st") );
	}
	
	public String generate(XlsModel[] models, String pkgName) {
		ClassDecl classDeclarationModel = toClassDeclModel(models, pkgName);	
		StringTemplate templ = template("compilationUnitDecl");
		templ.setAttribute("clasDeclModel", classDeclarationModel);
		return templ.toString();
	}

	private ClassDecl toClassDeclModel(XlsModel[] models, String pkgName) {
		List fieldDecls = new ArrayList();
		String recordName =	models[0].getInfos()[0];
		
		int y=1;
		boolean headConsumed = false;		// position y on the first row with non empty converter name
		while (!headConsumed)  {
			String converterName = models[y].getInfos()[1];
			if (converterName!=null && !"".equals(converterName.trim())) {
				headConsumed = true;
			} else {
				y++;
			}
		}
		
		for (int i = y; i < models.length; i++) {
			XlsModel xlsModel = models[i];
			FieldDeclStrategy resStrategy = getStrategy(xlsModel);
			String initializer = resStrategy.createInitializer(xlsModel, i);
			FieldDecl decl = new FieldDecl(PUBLIC_FINAL, resStrategy.createName(xlsModel), "Converter", initializer );
			fieldDecls.add(decl);
		}
		
		FieldDecl[] fields = (FieldDecl[]) fieldDecls.toArray(new FieldDecl[0]);
		ClassDecl cdecl = new ClassDecl(recordName, pkgName, fields);		
		return cdecl;
	}

	private FieldDeclStrategy getStrategy(XlsModel model) {
		for (int i = 0; i < strategies.length; i++) {
			FieldDeclStrategy strategy = strategies[i];
			if (strategy.match(model)) {
				return strategy;
			}
		}
		return null;
	}
	
	public FieldDeclStrategy[] getStrategies() {
		return strategies;
	}
	public void setStrategies(FieldDeclStrategy[] strategies) {
		this.strategies = strategies;
	}

}

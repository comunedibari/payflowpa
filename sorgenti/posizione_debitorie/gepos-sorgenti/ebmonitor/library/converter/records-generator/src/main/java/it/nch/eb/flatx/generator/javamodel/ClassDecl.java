/**
 * Created on 15/gen/08
 */
package it.nch.eb.flatx.generator.javamodel;



/**
 * @author gdefacci
 */
public class ClassDecl {
	
	String name;
	String packageName;
	FieldDecl[] fields;
	public ClassDecl(String name, String packageName, FieldDecl[] fields) {
		super();
		this.name = name;
		this.packageName = packageName;
		this.fields = fields;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public FieldDecl[] getFields() {
		return fields;
	}
	
}

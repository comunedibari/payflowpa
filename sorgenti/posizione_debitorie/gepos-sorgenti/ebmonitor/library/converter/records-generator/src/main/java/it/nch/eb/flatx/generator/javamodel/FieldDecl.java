/**
 * Created on 15/gen/08
 */
package it.nch.eb.flatx.generator.javamodel;

import it.nch.eb.common.utils.StringUtils;

public class FieldDecl {
	final String[] modifiers;
	final String name;
	final String fieldName;
	final String type;
	final String initializer;
	
	public FieldDecl(String name, String type) {
		this(null, name, type, null);
	}
	
	public FieldDecl(String[] modifiers, String name, String type, String initializer) {
		this.modifiers = modifiers;
		this.name = StringUtils.capitalized(name);;
		this.fieldName = fixCase(name);
		this.type = type;
		this.initializer = initializer;
	}
	
	private static String fixCase(String part) {
		if (StringUtils.isUpperCase(part)) return part.toLowerCase();
		else return StringUtils.decapitalized(part);
	}

	public String getName() {
		return name;
	}
	
	public String getField() {
		return fieldName;
	}

	public String getType() {
		return type;
	}

	public String[] getModifiers() {
		return modifiers;
	}

	public String getFieldName() {
		return fieldName;
	}
	
	public String getInitializer() {
		return initializer;
	}
	
	
	
}
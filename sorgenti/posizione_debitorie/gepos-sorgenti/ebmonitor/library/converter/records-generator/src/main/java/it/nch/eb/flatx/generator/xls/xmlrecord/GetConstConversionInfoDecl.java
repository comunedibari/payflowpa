/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

/**
 * @author gdefacci
 *
 */
public class GetConstConversionInfoDecl implements Xml2SqlConversionInfoDecl {

	private final String name;
	private final String javaType;
	private final String value;

	public GetConstConversionInfoDecl(String name, String javaType,
			String value) {
		this.name = name;
		this.javaType = javaType;
		this.value = value;
	}

	public String getArgumentsString() {
		String res = null;
		boolean quoteArg = false;
		if (javaType.equals("java.lang.String")) {
			res = "S";
			quoteArg = true;
		} else if (javaType.equals("java.lang.Integer")) {
			res = "I";
		} else if (javaType.equals("java.lang.Long")) {
			res = "L";
		} else if (javaType.equals("java.lang.Boolean")) {
			res = "B";
		} else {
			throw new IllegalStateException("Not a supported const type " + javaType);
		}
		if (quoteArg) return "new " + res + "(\"" + value + "\")";
		else return "new " + res + "(" + value + ")";
	}

	public String getName() {
		return name;
	}

}

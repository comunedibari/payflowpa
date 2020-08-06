/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

/**
 * @author gdefacci
 * XPathConversionInfoDecl
 */
public class XPathConversionInfoDecl implements Xml2SqlConversionInfoDecl {

	private static final String DQ = "\"";

	private final String name;
	
	private final String xpath;
	private final String converterName;
	private final String toObjectConcverterName;
	private final boolean optional;

	public XPathConversionInfoDecl(String name, String xpath, String converterName,
			String toObjectConcverterName, boolean optional) {
		if (name==null) throw new IllegalStateException("cant have a empty name");
		if (xpath==null) throw new IllegalStateException("cant have a empty xpath");
		this.name = name;
		this.xpath = xpath;
		this.converterName = converterName;
		this.toObjectConcverterName = toObjectConcverterName;
		this.optional = optional;
	}
	
	public String getArgumentsString() {
		StringBuffer sb = new StringBuffer();
		if (converterName!=null) {
			sb.append(converterName);
			sb.append(",");
		} 
		if (toObjectConcverterName!=null) {
			sb.append(toObjectConcverterName);
			sb.append(",");
		}
		
		sb.append(DQ);
		sb.append(xpath);
		sb.append(DQ);
		
		if (optional) {
			sb.append(", ");
			sb.append("true");
		}
		return sb.toString();
	}

	public String getName() {
		return name;
	}
	
//	public String getDbType() {
//		return dbType;
//	}

	public String getXpath() {
		return xpath;
	}

	public String getConverterName() {
		return converterName;
	}

	public String getToObjectConcverterName() {
		return toObjectConcverterName;
	}

	public boolean getOptional() {
		return optional;
	}
	
	
}

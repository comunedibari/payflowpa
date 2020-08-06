/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

/**
 * @author gdefacci
 * XPathConversionInfoDecl
 */
public class ChildRelativeXPathConversionInfoDecl implements Xml2SqlConversionInfoDecl {

	private static final String DQ = "\"";
	
	private final String name;
	
	private final String xpathPrefix;
	private final String xpathSuffix;
	private final String converterName;
	private final String toObjectConcverterName;
	private final boolean optional;

	public ChildRelativeXPathConversionInfoDecl(
			String name, 
			String pathPrefix,
			String pathSuffix,
			String converterName,
			String toObjectConcverterName, 
			boolean optional) {
		if (name==null) throw new IllegalStateException("cant have a empty name");
		this.name = name;
		this.xpathPrefix = pathPrefix;
		this.xpathSuffix = pathSuffix;
		this.converterName = converterName;
		this.toObjectConcverterName = toObjectConcverterName;
		this.optional = optional;
		
//		super(name, xpath, dbType, converterName, toObjectConcverterName, optional);
//		
//		if (name==null) throw new IllegalStateException("cant have a empty name");
//		if (xpath==null) throw new IllegalStateException("cant have a empty xpath");
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
		
		sb.append("concat(");
		if (xpathPrefix!=null) {
			sb.append(DQ);
			sb.append(xpathPrefix);
			sb.append(DQ);
			sb.append(", ");
		}
		sb.append("getChildPath()");
		if (xpathSuffix!=null) {
			sb.append(", ");
			sb.append(DQ);
			sb.append(xpathSuffix);
			sb.append(DQ);
		}
		sb.append(")");
		
		if (optional) {
			sb.append(", ");
			sb.append("true");
		}
		return sb.toString();
	}

	public String getName() {
		return name;
	}
	
}

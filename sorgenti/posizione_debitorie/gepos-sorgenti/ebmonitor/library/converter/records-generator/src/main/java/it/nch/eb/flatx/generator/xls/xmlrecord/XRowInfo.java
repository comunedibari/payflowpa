/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;


public class XRowInfo {
	private final String name;
	private final String typeName;
	private final boolean optional;
	
	private final String xpath;
	private final String constValue;
	
	public static XRowInfo xpath(String name, 
			String typeName, 
			String xpath,
			boolean optional) {
		return new XRowInfo(name, typeName, optional, xpath, null);
	}
	
	public static XRowInfo constValue(String name, 
			String typeName, 
			String value) {
		return new XRowInfo(name, typeName, false, null, value);
	}
	
	private XRowInfo(String name, String typeName, boolean optional,
			String xpath, String constValue) {
		this.name = name;
		this.typeName = typeName;
		this.optional = optional;
		this.xpath = xpath;
		this.constValue = constValue;
		
		if (xpath!=null && constValue!=null) throw new IllegalStateException("both xpath and constValue");
	}

	public boolean isXPath() { return this.xpath != null; }
	public boolean isConstValue() { return this.constValue != null; }

	public String getDbName() {
		return name.replaceAll("\\&", "");
	}

	public String getName() {
		return name;
	}

	public String getTypeName() {
		return typeName;
	}
	
	public String getXPath() {
		return xpath;
	}
	
	public String getConstValue() {
		return constValue;
	}

	public boolean isOptional() {
		return optional;
	}
	
	
	public String getJavaName() {
		return XRowInfoUtils.instance.toJavaName(name);
	}
	
	public XRowInfo withType(String dbType) {
		if (isConstValue()) return constValue(name, dbType, constValue);
		else return xpath(name, dbType, xpath, optional);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("XRowInfo\nname:");
		sb.append(name);
		sb.append("\ntypeName:");
		sb.append(typeName);
		if (isXPath()) {
			sb.append("\nxpath:");
			sb.append(xpath);
		} else {
			sb.append("\nconst value:");
			sb.append(constValue);
		}
		sb.append("\ndbName:");
		sb.append(getDbName());
		return sb.toString();
	}
	
}
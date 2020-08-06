/**
 * 26/nov/2009
 */
package it.nch.eb.flatx.generator.xls;

/**
 * @author gdefacci
 */
public class SimpleType implements GenTypeModel {

	private final String typeName;

	public SimpleType(String typeName) {
		super();
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public String toString() {
		return typeName;
	}
	
	public boolean isSimpleType() {
		return true;
	}
	
}

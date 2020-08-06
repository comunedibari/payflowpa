/**
 * 25/mag/2009
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

/**
 * @author gdefacci
 */
public class IBatisResultPropertyModel {
	
	private final String propertyName;
	private final String columnName;
	public IBatisResultPropertyModel(String propertyName, String columnName) {
		super();
		this.propertyName = propertyName;
		this.columnName = columnName;
	}
	
	public String getPropertyName() {
		return propertyName;
	}
	public String getColumnName() {
		return columnName;
	}
	

}

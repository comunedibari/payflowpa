/**
 * 
 */
package it.nch.eb.flatx.generator.xls.recordimpl;

import it.nch.eb.flatx.generator.xls.ConverterGenTypeModel;

/**
 * @author gdefacci
 *
 */
public class AliasedGenTypeModel extends ConverterGenTypeModel {
	
	private final String alias;

	public AliasedGenTypeModel(String dbType, 
			String converterName,
			String toObjectConverterName) {
		super(converterName, toObjectConverterName);
		if (dbType == null)
			throw new NullPointerException("dbType");
		this.alias = dbType;
	}

	public String getAlias() {
		return alias;
	}
	
}

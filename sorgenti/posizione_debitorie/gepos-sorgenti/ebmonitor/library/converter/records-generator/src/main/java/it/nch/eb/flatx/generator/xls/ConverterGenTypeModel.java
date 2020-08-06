/**
 * 26/nov/2009
 */
package it.nch.eb.flatx.generator.xls;

/**
 * @author gdefacci
 */
public class ConverterGenTypeModel implements GenTypeModel {
	
	private final String converter;
	private final String toObjConverter;
	
	public ConverterGenTypeModel(String converterName, String toObjConverterName) {
		this.converter = converterName;
		this.toObjConverter = toObjConverterName;
	}

	public String getConverterName() {
		return converter;
	}

	public String getToObjectConverterName() {
		return toObjConverter;
	}

	public String toString() {
		if (converter!=null && toObjConverter!=null) {
			return converter + ", " + toObjConverter;
		} else if (converter==null && toObjConverter==null) {
			return "";
		} else {
			return converter!=null ? converter : toObjConverter;
		}
	}
	
}

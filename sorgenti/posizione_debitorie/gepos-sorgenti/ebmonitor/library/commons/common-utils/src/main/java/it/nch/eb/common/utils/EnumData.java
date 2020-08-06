/**
 * Created on 22/nov/07
 */
package it.nch.eb.common.utils;

/**
 * @author gdefacci
 */
public class EnumData {

	protected final int enumId;
	protected final String name;
	
	protected EnumData(int serviceId, String name) {
		this.enumId = serviceId;
		this.name = name;
	}
	
	protected EnumData(EnumData enumData) {
		this(enumData.enumId, enumData.name);
	}
	
	protected EnumData(int serviceId) {
		this(serviceId, null);
	}
	
	public boolean is(EnumData enumVal) {
		if (enumVal.enumId==enumId) return true;
		return false;
	}
	
	public boolean is(String enumVal) {
		if (name==null) return false;
		return name.equals(enumVal);
	}

	public String toString() {
		if (name!=null) return "[" + enumId + "]" + name;
		return super.toString();
	}
	
	public String stringValue() {
		return name;
	}

	public int getEnumId() {
		return enumId;
	}

	public String getName() {
		return name;
	}
	
}
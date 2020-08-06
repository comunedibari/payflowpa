/**
 * 23/apr/2009
 */
package it.nch.eb.xsqlcmd.commands;

import java.io.Serializable;
/**
 * @author gdefacci
 */
public class RecordOperationEnum implements Serializable {
	private static final long serialVersionUID = 4069347045908962666L;
	private String name;
	
	public RecordOperationEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean equals(Object obj) {
		if (obj instanceof RecordOperationEnum) {
			RecordOperationEnum othr = (RecordOperationEnum) obj;
			return othr.getName().equals(getName());
		} else return false;
	}

	public int hashCode() {
		return getName().hashCode();
	}
	
}
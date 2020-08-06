/**
 * 
 */
package it.nch.eb.flatx.flatmodel.converters;

import it.nch.eb.flatx.bean.types.Converter;

/**
 * @author gdefacci
 */
public abstract class ClassIdentityConverter implements Converter {

	private static final long serialVersionUID = -6635091604331268938L;

	public final boolean equals(Object obj) {
		if (obj == null) return false;
		if (this.getClass().getName().equals(obj.getClass().getName())) return true;
		else return false;
	}

	public final int hashCode() {
		return this.getClass().getName().hashCode();
	}
	
}

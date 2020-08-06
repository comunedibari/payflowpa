/**
 * Created on 03/apr/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters;


/**
 * @author gdefacci
 */
public interface ToStringObjectConverter {

	String convert(Object obj);
	
	ToStringObjectConverter DEFAULT = new ToStringObjectConverter() {

		public String convert(Object obj) {
			if (obj==null) return null; 
			return obj.toString();
		}
		
	};
	
}

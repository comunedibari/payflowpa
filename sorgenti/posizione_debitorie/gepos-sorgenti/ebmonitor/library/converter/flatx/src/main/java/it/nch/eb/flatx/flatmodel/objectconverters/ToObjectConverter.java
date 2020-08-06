/**
 * Created on 24/gen/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters;

import java.io.Serializable;


/**
 * @author gdefacci
 */
public interface ToObjectConverter extends Serializable {
	
	Object convert(String str);
	Class getConversionTargetClass();

}

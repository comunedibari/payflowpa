/**
 * Created on 03/apr/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters;


/**
 * @author gdefacci
 */
public interface ReversibleObjectConverter extends ToObjectConverter {

	ToStringObjectConverter getReverse();
	
}

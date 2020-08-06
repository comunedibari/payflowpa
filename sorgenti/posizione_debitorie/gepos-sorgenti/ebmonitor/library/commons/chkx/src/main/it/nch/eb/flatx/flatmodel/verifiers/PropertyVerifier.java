/**
 * Created on 25/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;


/**
 * a verifier which verify a property of a compex object.
 * @author gdefacci
 */
public interface PropertyVerifier {

	public void verify(Object o, String property, BeanErrorCollector beanErrorCollector);
}
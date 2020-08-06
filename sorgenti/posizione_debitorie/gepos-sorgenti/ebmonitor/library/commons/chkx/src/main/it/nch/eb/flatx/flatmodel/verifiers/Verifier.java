/**
 * Created on 25/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;



/**
 * @author gdefacci
 */
public interface Verifier {

	public void verify(Object o, BeanErrorCollector beanErrorCollector);
}

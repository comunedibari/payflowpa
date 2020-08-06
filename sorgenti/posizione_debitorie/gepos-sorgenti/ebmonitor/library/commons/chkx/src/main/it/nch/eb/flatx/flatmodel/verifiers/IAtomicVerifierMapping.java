/**
 * Created on 25/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;


/**
 * @author gdefacci
 */
public interface IAtomicVerifierMapping {

//	AtomicVerifier getVerifier();
	String getPropertyName();
	boolean isToIgnoreWhenNull();
	
	AtomicVerifier createVerifier(GetterStrategy getterStrategy);
}

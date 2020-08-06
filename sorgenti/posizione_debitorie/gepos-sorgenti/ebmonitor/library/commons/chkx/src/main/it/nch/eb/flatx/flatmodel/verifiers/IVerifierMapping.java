/**
 * Created on 25/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;


/**
 * @author gdefacci
 */
public interface IVerifierMapping {

//	Verifier getVerifier();
	String getPropertyName();
	boolean isToIgnoreWhenNull();
	
	Verifier createVerifier(GetterStrategy getterStrategy);
}

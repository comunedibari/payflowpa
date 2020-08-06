/**
 * Created on 25/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;


/**
 * @author gdefacci
 */
public interface Severities {

	Severity INFO 		= new BaseSeverity("info", 0);
	Severity WARNING 	= new BaseSeverity("warning", 10);
	Severity ERROR 		= new BaseSeverity("error", 100);
	Severity FATAL 		= new BaseSeverity("fatal", 1000);
	
	Severity DEFAULT	= ERROR;

}

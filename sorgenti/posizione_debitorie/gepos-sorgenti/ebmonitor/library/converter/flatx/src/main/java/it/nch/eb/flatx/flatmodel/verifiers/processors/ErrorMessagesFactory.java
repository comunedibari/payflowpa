/**
 * Created on 12/mar/08
 */
package it.nch.eb.flatx.flatmodel.verifiers.processors;

import it.nch.eb.flatx.flatmodel.verifiers.IBeanError;


/**
 * @author gdefacci
 */
public interface ErrorMessagesFactory {
	
	String process(String templateName, IBeanError err);
}

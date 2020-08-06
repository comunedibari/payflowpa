/**
 * Created on 17/mag/07
 */
package it.nch.fwk.checks.errors.processing;

import it.nch.fwk.checks.errors.ErrorInfo;


/**
 * classes implementing this interface collect parameters from <code>IBindingManager</code>,
 * the error source element (<code>ElementCursor</code>), the produced error (all 
 * properties except the message, which is created by this interface subclasses). 
 * The method <br/>
 * <pre>
 * String produceErrorMessage();
 * </pre>
 * responsability is to glue all the collected parameters togheter to produce an 
 * error message expressend in a natural language.
 * @author gdefacci
 */
public interface ErrorProcessor extends IContextProcessor {

	void collectParams(final ErrorInfo bindings);
	String produceErrorMessage();
}
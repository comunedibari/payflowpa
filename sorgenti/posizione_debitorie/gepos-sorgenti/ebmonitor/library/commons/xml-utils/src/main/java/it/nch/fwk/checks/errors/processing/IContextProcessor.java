/**
 * Created on 17/mag/07
 */
package it.nch.fwk.checks.errors.processing;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.fwk.checks.IElementCursor;

import java.util.Map;

/**
 * astraction to allow a clean separation between context processing and related
 * products.
 * 
 * 
 * package visibility since error processing design aint actually so clear.
 * @author gdefacci
 */
/*package*/interface IContextProcessor {
	
	Map getParametersMap();
	
	void initDefaultParameters();
	void collectParams(final IElementCursor elem);
	void collectParams(final IBindingManager bindings);
	
	public String getStringParam(final String id);
	public Object getParameter(final String id);
	public void setParameter(final String key, final Object value);

}

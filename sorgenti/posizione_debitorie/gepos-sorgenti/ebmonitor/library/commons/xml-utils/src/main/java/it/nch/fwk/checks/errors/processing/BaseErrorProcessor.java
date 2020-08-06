/**
 * Created on 17/mag/07
 */
package it.nch.fwk.checks.errors.processing;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.fwk.checks.IElementCursor;
import it.nch.fwk.checks.errors.ErrorInfo;

import java.util.Map;
import java.util.TreeMap;

public abstract class BaseErrorProcessor implements ErrorProcessor {

	private static org.slf4j.Logger log = ResourcesUtil.createLogger(BaseErrorProcessor.class);
	
	private Map	map = new TreeMap();
	
	public void initDefaultParameters() {
	}

	public void collectParams(final IElementCursor elem) {
	}

	public void collectParams(final IBindingManager bindings) {
	}
	
	public void collectParams(final ErrorInfo bindings) {
	}
	
	public abstract String produceErrorMessage();
	
	public final Map getParametersMap() {
		return map;
	}
	
	public final String getStringParam(final String id) {
		Object res = getParameter(id);
		if (res==null) {
			log.info("the given parameter [" + id + "] does not exits");
		}
		return res.toString();
	}

	public final Object getParameter(final String id) {
		return getParametersMap().get(id);
	}

	public final void setParameter(final String key, final Object value) {
		getParametersMap().put(key, value);
	}

}
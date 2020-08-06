/**
 * Created on 04/mag/07
 */
package it.nch.fwk.checks.context.xom;

import it.nch.fwk.checks.AbstractElementCursor;
import it.nch.fwk.checks.IElementCursor;
import it.nch.fwk.checks.context.BaseElementCursorFactory;
import it.nch.fwk.checks.xom.ElementCursor;
import it.nch.fwk.core.NamespacesInfos;
import nu.xom.Element;


/**
 * @author gdefacci
 */
public class ElementCursorFactory extends BaseElementCursorFactory {
	
	private static final long	serialVersionUID	= -2087022779429537850L;

	public ElementCursorFactory(NamespacesInfos namespaces) {
		super(namespaces);
	}
	
	public IElementCursor create(Element elem) {
		IElementCursor res = new ElementCursor(elem, getQueryNamespaces());
		return res;
	}
	
	public IElementCursor create(Element elem, int idx) {
		AbstractElementCursor res = new ElementCursor(elem, idx, getQueryNamespaces());
		return res;
	}
	
}

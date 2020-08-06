/**
 * Created on 21/ago/07
 */
package it.nch.fwk.checks.xom;

import it.nch.fwk.checks.Attribute;

/**
 * @author gdefacci
 */
public class XomAttribute implements Attribute {
	
	private nu.xom.Attribute	delegate;

	public XomAttribute(nu.xom.Attribute realAttr) {
		this.delegate = realAttr;
	}

	public String getName() {
		return delegate.getLocalName();
	}

	public String getPrefix() {
		return delegate.getNamespacePrefix();
	}

	public String getValue() {
		return delegate.getValue();
	}
	
}

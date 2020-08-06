/**
 * Created on 21/ago/07
 */
package it.nch.fwk.checks.context;

import java.io.Serializable;

import it.nch.fwk.core.NamespacesInfos;

/**
 * @author gdefacci
 */
public class BaseElementCursorFactory implements Serializable {

	private static final long	serialVersionUID	= -88309279123582960L;
	private NamespacesInfos	queryNamespaces;
	
	public BaseElementCursorFactory(NamespacesInfos namespaces) {
		this.queryNamespaces = namespaces;
	}
	
	public NamespacesInfos getQueryNamespaces() {
		return queryNamespaces;
	}

}
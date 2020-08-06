/*
 * Copyright (C) 2004, 2007 Riccardo Solmi.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the GNU Lesser General Public License
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 */
package it.nch.eb.common.utils.bindings;



/**
 * @author Riccardo Solmi
 */
public class BindingManagerFactory {
	public static BindingManagerFactory instance = new BindingManagerFactory();
	private BindingManagerFactory() {
	}

	public IBindingManager createBindingManager() {
		return createBindingManager(createSimpleScope());
	}
	public IBindingManager createBindingManager(IBindingScope scope) {
		return new BindingManager(scope);
	}

	public IBindingScope createSimpleScope() {
		return new SimpleScope();
	}
	
	public IBindingScope createNestedDynamicSimpleScope(IBindingScope enclosingScope) {
		return new NestedDynamicSimpleScope(enclosingScope);
	}
	
	public IBindingScope createNestedScope(final IBindingScope enclosingScope, IBindingScope nestedScope) {
		return new NestedDynamicScope(enclosingScope, nestedScope);
	}

}

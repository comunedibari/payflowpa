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
public class BindingManager extends AbstractDelegatingScope implements IBindingManager {
	private static final long	serialVersionUID	= 4210736107783562086L;
	
	protected BindingManager(IBindingScope currentScope) {
		super(currentScope);
	}

	public void enterScope() {
		currentScope = BindingManagerFactory.instance.createNestedDynamicSimpleScope(currentScope);
	}
	public void enterScope(IBindingScope scope) {
		currentScope = BindingManagerFactory.instance.createNestedScope(currentScope, scope);
	}
	public void exitScope() {
//		Object rc = currentScope.get("record count");
//		boolean check = false;
//		if (rc != null) {
//			System.out.println("xs");
//			check = true;
//		}
		
		currentScope = currentScope.enclosingScope();
		if (currentScope == NullScope.instance)
			throw new IllegalStateException("exitScope from top level");
		
//		if (check) {
//			Object rc1 = currentScope.get("record count");
//			if (rc1 == null) {
//				throw new RuntimeException("");
//			} else System.out.println(rc1);
//		}
	}

}

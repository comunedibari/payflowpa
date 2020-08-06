/*
 * Copyright (C) 2004, 2007 Riccardo Solmi.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the GNU Lesser General Public License
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 */
package it.nch.eb.common.utils.bindings;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author Riccardo Solmi
 */
public abstract class AbstractDelegatingScope implements IBindingScope {
	private static final long serialVersionUID = -6403109461958128247L;
	protected IBindingScope currentScope;
	
	protected AbstractDelegatingScope(IBindingScope currentScope) {
		this.currentScope = currentScope;
	}

//	public void wClear() {
//		currentScope.wClear();
//	}

	public Set/*<String>*/ names() {
		return currentScope.names();
	}

	public Set/*<String>*/ localNames() {
		return currentScope.localNames();
	}

	public IBindingScope wCurrentScope() {
		return currentScope;
	}

	public IBindingScope enclosingScope() {
		return currentScope.enclosingScope();
	}

//	public void wAddAll(IBindingScope scope) {
//		currentScope.wAddAll(scope);
//	}

	public Object get(String name) {
		return currentScope.get(name);
	}
//	public void wAdd(String name, Object value) {
//		currentScope.wAdd(name, value);
//	}
	public void set(String name, Object value) {
		currentScope.set(name, value);
	}
	public void define(String name, Object value) {
		currentScope.define(name, value);
	}
	public void define(String name, int value) {
		currentScope.define(name, new Integer(value));
	}
	public boolean isSet(String name) {
		return currentScope.isSet(name);
	}
	public void unset(String name) {
		currentScope.unset(name);
	}

	public int intValue(String name) {
		return currentScope.intValue(name);
	}
	
	public BigDecimal bigDecimalValue(String name) {
		return currentScope.bigDecimalValue(name);
	}

	public Integer integerValue(String name) {
		return currentScope.integerValue(name);
	}

	public Long longValue(String name) {
		return currentScope.longValue(name);
	}

	public String stringValue(String name) {
		return currentScope.stringValue(name);
	}

	public void setOrDefine(String name, Object value) {
		currentScope.setOrDefine(name, value);
	}
	
	public String toString() {
		return currentScope.toString();
	}
	
}

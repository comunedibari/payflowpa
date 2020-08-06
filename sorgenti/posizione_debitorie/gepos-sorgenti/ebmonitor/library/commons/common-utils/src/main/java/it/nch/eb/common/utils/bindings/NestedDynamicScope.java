/*
 * Copyright (C) 2004, 2007 Riccardo Solmi.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the GNU Lesser General Public License
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 */
package it.nch.eb.common.utils.bindings;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Riccardo Solmi
 */
public class NestedDynamicScope extends AbstractScope {
	private static final long	serialVersionUID	= -5427341617163154128L;
	
	private IBindingScope currentScope;
	private IBindingScope enclosingScope;

	protected NestedDynamicScope(IBindingScope enclosingScope, IBindingScope nestedScope) {
		this.enclosingScope = enclosingScope;
		this.currentScope = nestedScope;
	}

	public IBindingScope currentScope() {
		return currentScope;
	}
	public IBindingScope enclosingScope() {
		return enclosingScope;
	}

	public Set/*<String>*/ localNames() {
		return currentScope.localNames();
	}

	public Set/*<String>*/ names() {
		Set/*<String>*/ nameSet = new TreeSet(enclosingScope().names());
		nameSet.addAll(currentScope.names());
		return Collections.unmodifiableSet(nameSet);
	}

	public boolean isSet(String name) {
		return currentScope.isSet(name) ? true : enclosingScope().isSet(name);
	}
	public Object get(String name) {
		Object value = currentScope.get(name);
//		log.debug("getting[" + name + "]value[" + value + "]");
		return (value != null) ? value : enclosingScope().get(name);
	}
	public void set(String name, Object value) {
//		log.debug("setting[" + name + "]value[" + value + "]");
		if (currentScope.isSet(name))
			define(name, value);
		else
			enclosingScope().set(name, value);
	}
	public void define(String name, int value) {
//		log.debug("defining[" + name + "]value[" + value + "]");
		Integer i = new Integer(value);
		if (currentScope.isSet(name))
			define(name, i);
		else
			enclosingScope().set(name, i);
	}

	public void define(String name, Object value) {
//		log.debug("defining[" + name + "]value[" + value + "]");
		currentScope.define(name, value);
	}

	public void unset(String name) {
		if (currentScope.isSet(name))
			currentScope.unset(name);
		else
			enclosingScope().unset(name);
	}

	public String toString() {
		return enclosingScope.toString() + currentScope.toString();
	}
	
	

}

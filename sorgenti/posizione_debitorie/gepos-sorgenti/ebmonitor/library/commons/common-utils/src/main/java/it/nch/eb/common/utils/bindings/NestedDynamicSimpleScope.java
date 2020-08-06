/*
 * Copyright (C) 2004, 2007 Riccardo Solmi.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the GNU Lesser General Public License
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 */
package it.nch.eb.common.utils.bindings;


import java.util.HashMap;
import java.util.Map;

/**
 * @author Riccardo Solmi
 */
public class NestedDynamicSimpleScope extends SimpleScope {
	private static final long	serialVersionUID	= 9178233734114707380L;

	private IBindingScope enclosingScope;
	
	protected NestedDynamicSimpleScope(IBindingScope enclosingScope) {
		this(enclosingScope, new HashMap()/*<String, Object>*/);
	}
	protected NestedDynamicSimpleScope(IBindingScope enclosingScope, Map/*<String, Object>*/ map) {
		super(map);
		this.enclosingScope = enclosingScope;
	}

	public IBindingScope enclosingScope() {
		return enclosingScope;
	}

	public boolean isSet(String name) {
		if (super.isSet(name)) {
			return true; 
		}
		return enclosingScope().isSet(name);
	}
	public Object get(String name) {
		Object value = super.get(name);
//		log.debug("getting[" + name + "]value[" + value + "]");
		return (value != null) ? value : enclosingScope().get(name);
	}
	public void set(String name, Object value) {
//		log.debug("getting[" + name + "]value[" + value + "]");
		if (super.isSet(name)) {
			define(name, value);
		} else {
			enclosingScope().set(name, value);
		}
	}

	public void unset(String name) {
		if (super.isSet(name)) {
			super.unset(name);
		} else {
			enclosingScope().unset(name);
		}
	}
	
	public String toString() {
		return enclosingScope.toString() + super.toString();
	}
	
	
	

}
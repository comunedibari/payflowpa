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
import java.util.Collections;

/**
 * @author Riccardo Solmi
 */
public class NullScope implements IBindingScope {
	private static final long	serialVersionUID	= -1919338830743102962L;
	
	public static final NullScope instance = new NullScope();
	protected NullScope() {
	}

	public void wClear() {
	}

	public Set/*<String>*/ names() {
		return Collections.EMPTY_SET;
	}

	public Set/*<String>*/ localNames() {
		return Collections.EMPTY_SET;
	}

	public IBindingScope enclosingScope() {
		throw new IllegalStateException("NullBindingManager");
	}

	public void wAddAll(IBindingScope scope) {
		throw new IllegalStateException("NullBindingManager");		
	}

	public Object get(String name) {
		return null;
	}
	public void set(String name, Object value) {
		throw new UnsupportedOperationException("cant set var on nullScope instances");		
	}
	
	public void wAdd(String name, Object value) {
		throw new UnsupportedOperationException("cant add var on nullScope instances");		
	}		
	public void define(String name, Object value) {
		throw new UnsupportedOperationException("cant def var on nullScope instances");
	}
	public void define(String name, int value) {
		throw new UnsupportedOperationException("cant def var on nullScope instances");
	}		
	public boolean isSet(String name) {
		return false;		
	}
	public void unset(String name) {
	}

	public void setOrDefine(String name, Object value) {
		throw new UnsupportedOperationException("cant set var on nullScope instances");		
	}

	public String stringValue(String name) {
		throw new IllegalStateException("NullBindingManager");		
	}
	public int intValue(String name) {
		throw new IllegalStateException("NullBindingManager");		
	}

	public BigDecimal bigDecimalValue(String name) {
		throw new IllegalStateException("NullBindingManager");
	}

	public Integer integerValue(String name) {
		throw new IllegalStateException("NullBindingManager");
	}

	public Long longValue(String name) {
		throw new IllegalStateException("NullBindingManager");
	}

	
}

/*
 * Copyright (C) 2004, 2007 Riccardo Solmi.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the GNU Lesser General Public License
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 */
package it.nch.eb.common.utils.bindings;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * @author Riccardo Solmi
 */
public interface IBindingScope extends Serializable {
//	public IBindingScope wClone();

	public IBindingScope enclosingScope();

	/**
	 * Unbind all local names.
	 */
//	public void wClear();
	/**
	 * Return the set of names defined in the current scope (local).
	 */
	public Set/*<String>*/ localNames();
	/**
	 * Return the set of names local or in scope (dynamic scope chain).
	 */
	public Set/*<String>*/ names();

	/**
	 * Add (wDef) to the current scope all bindings (local or in scope) defined
	 * in the passed scope.
	 * @param scope
	 */
//	public void wAddAll(IBindingScope scope);

	/**
	 * Get the value associated to the existing name (local or in scope).
	 * Returns null if the name is undefined.
	 */
	public Object get(String name);
	
	/**
	 * Bind an existing name (local or in scope) to the value.
	 * Throws IllegalArgumentException if the name is undefined.
	 */
	public void set(String name, Object value);
	
	/**
	 * Add a value to an existing name (local or in scope).
	 * Throws IllegalArgumentException if the name is undefined.
	 */
//	public void wAdd(String name, Object value);

	/**
	 * Bind the local name to the value; create it if undefined.
	 */
	public void define(String name, Object value);
	public void define(String string, int i);

	
	/**
	 * Returns true if the name is defined (local or in scope).
	 */
	public boolean isSet(String name);
	
	/**
	 * never used
	 * Unbind the value associated to the name (local or in scope).
	 */
	public void unset(String name);
	
//	public boolean wBooleanValue(String name);
//	public byte wByteValue(String name);
//	public char wCharValue(String name);
//	public double wDoubleValue(String name);
//	public float wFloatValue(String name);
	public int intValue(String name);
//	public long wLongValue(String name);
//	public short wShortValue(String name);
	public String stringValue(String name);
//	public Date wDateValue(String name);
//	public EnumValue wEnumValue(String name);
//	public Object wGetValue(String name);
	
	public void setOrDefine(String name, Object value);
	
	public Integer integerValue(String name);
	public BigDecimal bigDecimalValue(String name);
	public Long longValue(String name);
	
}

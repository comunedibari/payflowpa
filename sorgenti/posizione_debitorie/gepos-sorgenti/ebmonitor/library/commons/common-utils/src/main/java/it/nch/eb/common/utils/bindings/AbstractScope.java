/*
 * Copyright (C) 2004, 2007 Riccardo Solmi.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the GNU Lesser General Public License
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 */
package it.nch.eb.common.utils.bindings;


import it.nch.eb.common.utils.StringUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


/**
 * @author Riccardo Solmi
 */
public abstract class AbstractScope implements IBindingScope {
	private static final long serialVersionUID = -6012411743911469672L;
	public Set names() {
		Set nameSet = new TreeSet(enclosingScope().names());
		nameSet.addAll(localNames());
		return Collections.unmodifiableSet(nameSet);
	}

	public int intValue(String name) {
		Integer intValue = integerValue(name);
		if (intValue==null) throw new IllegalArgumentException("id: " + name + "aint defined");
		return intValue.intValue();
	}
	
	public String stringValue(String name) {
		Object res = get(name);
		if (res==null) return null;
		else if (res instanceof String) return (String) res;
		else return res.toString();
		
	}
	
	public BigDecimal bigDecimalValue(String name) {
		return ((BigDecimal)get(name));
	}

	public Integer integerValue(String name) {
		return ((Integer)get(name));
	}

	public Long longValue(String name) {
		return ((Long)get(name));
	}

	
	public void setOrDefine(String name, Object value) {
		if (isSet(name)) set(name, value);
		else define(name, value);
		
	}
	/**
	 * @Override
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Iterator it = names().iterator(); it.hasNext();) {
			String item = (String) it.next();
			Object obj = get(item);
			sb.append("\nname : " + item + "\nvalue : " + StringUtils.toString(obj));
		}
		return sb.toString();
	}
	
	
	
}

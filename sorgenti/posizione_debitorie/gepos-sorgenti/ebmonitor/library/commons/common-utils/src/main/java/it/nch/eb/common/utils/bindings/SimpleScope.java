/*
 * Copyright (C) 2004, 2007 Riccardo Solmi.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the GNU Lesser General Public License
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 */
package it.nch.eb.common.utils.bindings;


import it.nch.eb.common.utils.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @author Riccardo Solmi
 */
public class SimpleScope extends AbstractScope {
	private static final long	serialVersionUID	= -8007020062679185321L;

	protected Map/*<String, Object>*/ map;
	
	protected SimpleScope() {
		this(new HashMap/*<String, Object>*/());
	}
	protected SimpleScope(Map/*<String, Object>*/ map) {
		this.map = map;
	}

	public void wClear() {
		map.clear();
	}

	public Set/*<String>*/ localNames() {
		return Collections.unmodifiableSet(map.keySet());
	}

//	public IBindingScope wClone() {
//		Map copy_map = mapClone();
//		SimpleScope copy = new SimpleScope(copy_map);
//		return copy;
//	}
	protected Map mapClone() {
		Map/*<String, Object>*/ copy_map = new HashMap();
		for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
			Entry src = (Entry) it.next();
//			FIXME should perform a deep clone copy
			copy_map.put(src.getKey(), src.getValue());
		}
		return copy_map;
	}
	public IBindingScope enclosingScope() {
		return NullScope.instance;
	}

	public Object get(String name) {
		Object value = map.get(name);
//		log.debug("getting[" + name + "]value[" + value + "]");
		return value;
	}
	public void set(String name, Object value) {
//		log.debug("setting[" + name + "]value[" + value + "]");
		if (isSet(name))
			map.put(name, value);
		else
			throw new IllegalArgumentException("id \"" + name + "\" aint defined");
	}
//	public void wAdd(String name, Object value) {
//		Object composite = wGet(name);
//		if (composite != null) {
//			if (!EntityUtils.isComposite(composite)) {
//				//FIXME wrap in a composite
//				//map.put(name, composite);
//			}
//			composite.wAdd(value);
//		} else
//			throw BindingManagerFactory.instance.createNoBindingException(name);		
//	}
	public void define(String name, Object value) {
//		log.debug("defining[" + name + "]value[" + value + "]");
		map.put(name, value);
	}
	public void define(String name, int value) {
//		log.debug("defining[" + name + "]value[" + value + "]");
		map.put(name, new Integer(value));
	}
	public boolean isSet(String name) {
		return map.containsKey(name);
	}
	public void unset(String name) {
		map.remove(name);
	}
	
	public String toString() {
		return StringUtils.toString(this.map);
	}
	

}

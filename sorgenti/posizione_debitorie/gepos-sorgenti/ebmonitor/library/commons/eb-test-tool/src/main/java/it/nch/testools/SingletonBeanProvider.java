/**
 * Created on 10/feb/2009
 */
package it.nch.testools;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SingletonBeanProvider implements BeanProvider {
	private final BeanProvider delegate;
	private Map cache = Collections.synchronizedMap(new HashMap()); 

	public SingletonBeanProvider(BeanProvider delegate) {
		this.delegate = delegate;
	}

	public Object getBean(String name) {
		if (cache.containsKey(name)) return cache.get(name);
		else {
			Object res = delegate.getBean(name);
			cache.put(name, res);
			return res;
		}
	}
	
}
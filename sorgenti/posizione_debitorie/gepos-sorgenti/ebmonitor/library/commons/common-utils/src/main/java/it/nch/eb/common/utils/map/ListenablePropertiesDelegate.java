/**
 * Created on 16/nov/07
 */
package it.nch.eb.common.utils.map;

import it.nch.eb.common.utils.loaders.PropertiesDelegate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;


/**
 * @author gdefacci
 */
public class ListenablePropertiesDelegate extends PropertiesDelegate {

	private static final long	serialVersionUID	= 1L;
//	PropertyChangeListener 
	private List/*<EnhancerPropertiesListener>*/ listeners;

	public ListenablePropertiesDelegate(Properties delegate) {
		this(delegate, null);
	}
	
	public ListenablePropertiesDelegate(Properties delegate, List listeners) {
		super(delegate);
		checkListClassHomogeneity(listeners, EnhancerPropertiesListener.class);	
		this.listeners = listeners!=null ? listeners : new ArrayList/*<EnhancerPropertiesListener>*/();
	}

	private void checkListClassHomogeneity(List collection, Class klass) {
		if (collection!=null && !collection.isEmpty())
			for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
				Object item = iterator.next();
				if ( !klass.isAssignableFrom(item.getClass()) ) 
					throw new IllegalStateException("expeceting a list of " 
							+ klass.getName() + " but a " 
							+ item.getClass().getName() + " instance has been found");
			}
	}

	public void afterPropertySet(Object key, Object value) {
		for (Iterator it = listeners.iterator(); it.hasNext();) {
			EnhancerPropertiesListener epl = (EnhancerPropertiesListener) it.next();
			PropertiesEnhancer enhancer = epl.onPropertySet(key, value); 
			if (enhancer!=null) { 
				setDelegate( enhancer.enhance(getDelegate()) );  // thanks to composition (over inhiterance) we can do something like that
			}
		}
	}
	
	protected final void afterPropertiesSet(Map map) {	// final ?
		for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
			Entry entry = (Entry) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			afterPropertySet(key, value );
		}
	}
	
	public synchronized Object setProperty(String key, String value) {
		Object res = getDelegate().setProperty(key, value);
		afterPropertySet(key, value);
		return res;
	}
	
	public synchronized Object put(Object key, Object value) {
		Object res = getDelegate().put(key, value);
		afterPropertySet(key, value);
		return res;
	}

	public synchronized void putAll(Map t) {
		getDelegate().putAll(t);
		afterPropertiesSet(t);
	}
	
	public ListenablePropertiesDelegate add(EnhancerPropertiesListener enhancerListener) {
		this.listeners.add(enhancerListener);
		return this;
	}

}

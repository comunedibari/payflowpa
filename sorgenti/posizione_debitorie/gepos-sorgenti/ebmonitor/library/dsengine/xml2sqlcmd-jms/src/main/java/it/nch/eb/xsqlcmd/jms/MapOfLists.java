/**
 * 10/set/2009
 */
package it.nch.eb.xsqlcmd.jms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author gdefacci
 */
public class MapOfLists<K extends Comparable<K>,V> {

	private final Map<K, List<V>> mapOfLists = Collections.synchronizedMap( new TreeMap<K, List<V>>() );
	
	public void put(K k,V v) {
		List<V> lst = mapOfLists.get(k);
		if (lst == null) {
			lst = newList();
			mapOfLists.put(k, lst);
		}
		lst.add(v);
	}

	private List<V> newList() {
		return new ArrayList<V>();
	}
	
	public List<V> getAll(K k) {
		return mapOfLists.get(k);
	}
	
	public V pop(K k) {
		List<V> lst = mapOfLists.get(k);
		V res = lst == null || lst.isEmpty() ? null : lst.remove(0);
		if (lst != null && lst.isEmpty()) mapOfLists.remove(k);
		return res;
	}
	
	public List<V> pop() {
		Set<K> keyToRemove = new TreeSet<K>();
		List<V> res = newList();
		for (K k : this.mapOfLists.keySet()) {
			List<V> lst = mapOfLists.get(k);
			V res1 = lst == null || lst.isEmpty() ? null : lst.remove(0);
			if (lst != null && lst.isEmpty()) {
				keyToRemove.add(k);
			}
			V item = res1;
			if (item !=null) {
				res.add(item);
			}
		}
		for (K k : keyToRemove) {
			mapOfLists.remove(k);
		}
		return res;
	}
	
	public boolean isEmpty() {
		return this.mapOfLists.isEmpty();
	}

}

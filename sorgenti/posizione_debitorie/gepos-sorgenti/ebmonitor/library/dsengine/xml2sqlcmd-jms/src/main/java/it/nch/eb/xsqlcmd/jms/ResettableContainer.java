/**
 * 01/set/2009
 */
package it.nch.eb.xsqlcmd.jms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResettableContainer<T> {
	private List<T> list = Collections.synchronizedList( new ArrayList<T>() );
	
	public ResettableContainer() {
		this(new ArrayList<T>());
	}
	
	public ResettableContainer(List<T> lst) {
		this.list = lst;
	}
	
	public synchronized List<T> conditionalGetAndReset(int n) {
		if (list.size() > n)  {
			return getAndReset();
		} else {
			return null;
		}
	}
	
	public synchronized List<T> getAndReset() {
		List<T> res = list;
		list = Collections.synchronizedList(new ArrayList<T>());
		return res;
	}
	
	public synchronized List<T> getAndReset(Function1<ResettableContainer<T>, Boolean> pred) {
		if (pred.apply(this).booleanValue()) {
			List<T> res = list;
			list = Collections.synchronizedList(new ArrayList<T>());
			return res;
		} else {
			return null;
		}
	}
	
	public List<T> getList() {
		return list;
	}
	
	public void add(T pend) {
		this.list.add(pend);
	}
	
}
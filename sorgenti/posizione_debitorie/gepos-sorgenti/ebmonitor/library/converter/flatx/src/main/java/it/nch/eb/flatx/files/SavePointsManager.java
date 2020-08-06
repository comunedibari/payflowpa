/**
 * Created on 05/mar/08
 */
package it.nch.eb.flatx.files;

import it.nch.eb.flatx.files.model.InputFile;

import java.util.Iterator;
import java.util.Stack;

public class SavePointsManager {
	Stack/*<Integer>*/ savepoints = new Stack();
	
	public void decAll() {
		Stack res = new Stack();
		for (Iterator it = savepoints.iterator(); it.hasNext();) {
			Integer intg = (Integer) it.next();
			if (intg.intValue()<1) throw new IllegalStateException("rollback will erase a savepoint");
			res.push(new Integer(intg.intValue()-1));
		}
		this.savepoints = res;
	}
	
	public void incAll() {
		Stack res = new Stack();
		for (Iterator it = savepoints.iterator(); it.hasNext();) {
			Integer intg = (Integer) it.next();
			res.push(new Integer(intg.intValue()+1));
		}
		this.savepoints = res;
	}
	
	public void dec() {
		if (!savepoints.isEmpty()) {
			decAll();
		}
	}
	
	public void inc() {
		if (!savepoints.isEmpty()) {
			incAll();
		}
	}
	
	public boolean isSet() {
		return !savepoints.isEmpty();
	}
	
	public void push() {
		savepoints.push(new Integer(0));
	}
	
	public int currentValue() {
		return ((Integer)this.savepoints.peek()).intValue();
	}
	
	public int pop() {
		if (savepoints.isEmpty()) throw new IllegalStateException("no savepoints have been set");
		Integer res = (Integer) savepoints.pop();
		return res.intValue();
	}
	
	public void rollback(InputFile f) {
		if (savepoints.isEmpty()) throw new IllegalStateException("no savepoints have been set");
		dec();
	}
	
}
/**
 * Created on 15/apr/07
 */
package it.nch.fwk.checks.util;

public class Counter {
	private int idx=0;
	public Counter increment() { 
		idx++;
		return this;
	}
	public int getValue() { return idx; }
}
/**
 * Created on 03/ott/08
 */
package it.nch.streaming.services.impl;

/**
 * @author gdefacci
 */
public class SimpleCounter {

	private int	counter = 0;
	

	public int get() {
		return counter;
	}

	public void inc() {
		counter = counter + 1;
	}

	public void reset() {
		counter = 0;
	}

}
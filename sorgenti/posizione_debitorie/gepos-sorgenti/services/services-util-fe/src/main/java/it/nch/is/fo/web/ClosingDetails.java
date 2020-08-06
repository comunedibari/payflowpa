package it.nch.is.fo.web;

import java.io.Serializable;

public class ClosingDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String startOpen;
	public String endOpen;
	
	public String toString() {
		return "inizio apertura: " + startOpen + ", fine apertura: " + endOpen;
	}
	
}

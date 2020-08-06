package it.nch.utility.enumeration;

import java.io.Serializable;

public enum FlagEnrollmentAvvisatura implements Serializable {

	Y("Y"), N("N");

	private final String description;

	FlagEnrollmentAvvisatura(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
}

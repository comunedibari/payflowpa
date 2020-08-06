package it.tasgroup.idp.message;

import java.io.Serializable;

public class TestMessage implements Serializable {
	
	private String test;

	public TestMessage() {
		super();
	}
	
	public TestMessage(String test) {
		this.test = test;
	}

	public String getTest() {
		return this.test;
	}

	public void setTest(String test) {
		this.test = test;
	}
	
}

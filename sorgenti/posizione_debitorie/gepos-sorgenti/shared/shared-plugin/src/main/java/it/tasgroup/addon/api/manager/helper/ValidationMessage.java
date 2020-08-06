package it.tasgroup.addon.api.manager.helper;

import java.util.ArrayList;
import java.util.List;

public class ValidationMessage {

	public enum Esit {
		OK, KO
	}

	private Esit esit;
	private List<String> messages;

	public ValidationMessage(Esit esit) {
		super();
		this.esit = esit;
		this.messages = new ArrayList<String>();
	}

	public ValidationMessage(Esit esit, String message) {
		super();
		this.esit = esit;
		this.messages = new ArrayList<String>();
		this.messages.add(message);
	}

	public ValidationMessage(Esit esit, List<String> messages) {
		super();
		this.esit = esit;
		this.messages = messages;
	}

	public void addMessage(String message) {
		this.messages.add(message);
	}

	public Esit getEsit() {
		return esit;
	}

	public void setEsit(Esit esit) {
		this.esit = esit;
	}

	public List<String> getMessages() {
		return messages;
	}

}

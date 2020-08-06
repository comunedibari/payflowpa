package it.tasgroup.iris.dto.tavolooperativo;

import it.tasgroup.services.util.enumeration.EnumSeverityLevelTO;

import java.io.Serializable;
import java.sql.Timestamp;

public class DatiCART_DTO implements Serializable{

	private static final long serialVersionUID = -4295620198690244812L;
	
	private String messaggio;
	private Timestamp timestamp_registrazione;
	private EnumSeverityLevelTO severity;
	
	
	public DatiCART_DTO() {
		
	}


	public String getMessaggio() {
		return messaggio;
	}


	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}


	public Timestamp getTimestamp_registrazione() {
		return timestamp_registrazione;
	}


	public void setTimestamp_registrazione(Timestamp timestamp_registrazione) {
		this.timestamp_registrazione = timestamp_registrazione;
	}


	public EnumSeverityLevelTO getSeverity() {
		return severity;
	}


	public void setSeverity(EnumSeverityLevelTO severity) {
		this.severity = severity;
	}
	
	public String getConcatData(){
		
//		return timestamp_registrazione + " [ " + severity + " ] " + messaggio;
		return timestamp_registrazione + " [ " + severity + " ] ";
	}
	
}

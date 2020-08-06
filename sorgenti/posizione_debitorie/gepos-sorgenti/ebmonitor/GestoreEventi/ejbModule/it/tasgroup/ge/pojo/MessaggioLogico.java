package it.tasgroup.ge.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe che rappresenta una e-mail
 * TODO: creare il content con header e footer letto da file di prop.
 */
public class MessaggioLogico implements Serializable {

	private static final long serialVersionUID = -4727619756809174612L;
	
	// Mittente dell'email
	private String from;

	// Destinatari dell'email
	private ArrayList<String> to;
	
	// Destinatari dell'email in cc
	private ArrayList<String> cc;

	private String subject;	
	
	// Allegato
	private byte[] pdfData;
	
	// Nome del file in attachment
	private String nomeFile;
	
	// Testo dell'email
	private String content;
	

	// Istante di invio del messaggio
	private Date dataInvio;


	public MessaggioLogico() {
	}

	public MessaggioLogico(ArrayList<String> destinatari) {
		to = destinatari;
	}

	public MessaggioLogico(String testo, ArrayList<String> destinatario) {
		this(destinatario);
		setContent(testo);
	}
		
	public String getFrom() {
		return from;
	}

	public void setFrom(String newFrom) {
		from = newFrom;
	}

	public ArrayList<String> getTo() {
		return to;
	}

	public void setTo(ArrayList<String> destinatari) {
		to = destinatari;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String newSubject) {
		subject = newSubject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String newContent) {
		content = newContent;
	}

	public byte[] getPdfData() {
		return pdfData;
	}

	public void setPdfData(byte[] newPdfData) {
		pdfData = newPdfData;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String newNomeFile) {
		nomeFile = newNomeFile;
	}
	
	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date newDataInvio) {
		dataInvio = newDataInvio;
	}
	
	
	public ArrayList<String> getCc() {
		return cc;
	}

	public void setCc(ArrayList<String> cc) {
		this.cc = cc;
	}

	private String getToAsString() {
		String destinatari = "";
		if (to != null) {			
			destinatari = (String)to.get(0);
			for (int j=1; j<to.size(); j++)
				destinatari += ";" + to.get(j);
		}
		return destinatari;
	}

	public String toString() {
		String ret = "";
		ret += "\n\r*************\n";
		ret += "* Email *\n";
		ret += "*************\n";
		ret += "	From:                       #" + getFrom() + "#\n";					
		ret += "	To:                         #" + getToAsString() + "#\n";					
		ret += "	Subject:                    #" + getSubject() + "#\n";
		ret += "	Content:                    #" + getContent() + "#\n\r";
		ret += "	Nome File:                  #" + getNomeFile() + "#\n\r";
		return ret;
	}
}

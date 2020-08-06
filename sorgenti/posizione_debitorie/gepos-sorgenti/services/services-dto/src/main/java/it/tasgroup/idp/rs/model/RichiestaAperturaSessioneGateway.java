package it.tasgroup.idp.rs.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RichiestaAperturaSessioneGateway {
	
	
	private String codiceFiscaleVersante;
	private String anagraficaVersante;
	private String email;
	private String urlBack;
	private String urlCancel;
	private String senderSil;
	private String senderSys;
	private int flagOpposizione730;
	private String redirectOnly;
	private List<CondizionePagamentoReference> condizioniList;
	
	public RichiestaAperturaSessioneGateway() {
		
	}
	
	@XmlElement(required = true)
	public String getRedirectOnly() {
		return redirectOnly;
	}

	public void setRedirectOnly(String redirectOnly) {
		this.redirectOnly = redirectOnly;
	}

	@XmlElement(required = true)
	public String getSenderSil() {
		return senderSil;
	}

	public void setSenderSil(String senderSil) {
		this.senderSil = senderSil;
	}

	@XmlElement(required = true)
	public String getSenderSys() {
		return senderSys;
	}

	public void setSenderSys(String senderSys) {
		this.senderSys = senderSys;
	}

	@XmlElement(required = true)
	public int getFlagOpposizione730() {
		return flagOpposizione730;
	}

	public void setFlagOpposizione730(int flagOpposizione730) {
		this.flagOpposizione730 = flagOpposizione730;
	}

	@XmlElement(required = true)
	public String getCodiceFiscaleVersante() {
		return codiceFiscaleVersante;
	}

	public void setCodiceFiscaleVersante(String codiceFiscaleVersante) {
		this.codiceFiscaleVersante = codiceFiscaleVersante;
	}

	@XmlElement(required = true)
	public String getAnagraficaVersante() {
		return anagraficaVersante;
	}

	public void setAnagraficaVersante(String anagraficaVersante) {
		this.anagraficaVersante = anagraficaVersante;
	}

	@XmlElement(required = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement(required = true)
	public String getUrlBack() {
		return urlBack;
	}

	public void setUrlBack(String urlBack) {
		this.urlBack = urlBack;
	}

	@XmlElement(required = true)
	public String getUrlCancel() {
		return urlCancel;
	}

	public void setUrlCancel(String urlCancel) {
		this.urlCancel = urlCancel;
	}

	@XmlElement(required = true)
	public List<CondizionePagamentoReference> getCondizioniList() {
		return condizioniList;
	}

	public void setCondizioniList(List<CondizionePagamentoReference> condizioniList) {
		this.condizioniList = condizioniList;
	}

}

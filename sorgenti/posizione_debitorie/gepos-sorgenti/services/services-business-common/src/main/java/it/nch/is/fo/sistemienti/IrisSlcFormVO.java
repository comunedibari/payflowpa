package it.nch.is.fo.sistemienti;

import java.util.List;

public class IrisSlcFormVO {
	
	private String username;
	private String password;
	private String backUrl;
	private String soggetto;
	private String sil;
	private String rfc127;
	private String rfc127IP;
	private String rfc145;
	private String rfc145IP;
	private String proxy;
	private String aderente;
	private String otf;
	private String actionOfTheForm;
	
	private List<String> tributiList;
	
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getBackUrl() {
		return backUrl;
	}
	
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	
	
	public String getSoggetto() {
		return soggetto;
	}
	
	public void setSoggetto(String soggetto) {
		this.soggetto = soggetto;
	}
	
	
	public String getSil() {
		return sil;
	}
	
	public void setSil(String sil) {
		this.sil = sil;
	}
	
	
	public List<String> getTributiList() {
		return tributiList;
	}
	
	public void setTributiList(List<String> tributiList) {
		this.tributiList = tributiList;
	}
	
	
	public String getRfc127() {
		return rfc127;
	}
	
	public void setRfc127(String rfc127) {
		this.rfc127 = rfc127;
	}
	
	
	public String getRfc127IP() {
		return rfc127IP;
	}
	
	public void setRfc127IP(String rfc127ip) {
		rfc127IP = rfc127ip;
	}
	
	
	public String getRfc145() {
		return rfc145;
	}
	
	public void setRfc145(String rfc145) {
		this.rfc145 = rfc145;
	}
	
	
	public String getRfc145IP() {
		return rfc145IP;
	}
	
	public void setRfc145IP(String rfc145ip) {
		rfc145IP = rfc145ip;
	}
	
	
	public String getProxy() {
		return proxy;
	}
	
	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	
	
	public String getAderente() {
		return aderente;
	}
	
	public void setAderente(String aderente) {
		this.aderente = aderente;
	}
	
	
	public String getOtf() {
		return otf;
	}

	public void setOtf(String otf) {
		this.otf = otf;
	}

	public String getActionOfTheForm() {
		return actionOfTheForm;
	}
	
	public void setActionOfTheForm(String actionOfTheForm) {
		this.actionOfTheForm = actionOfTheForm;
	}

}

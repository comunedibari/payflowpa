package it.tasgroup.ge.pojo;


public class PendenzaInScadenza extends CommunicationEventDetail {
	public String dataScadenza;
	public String importo;
	public String ente;
	public String tributo;
	public String descrizionePendenza;
	public String username;
	public String causale;
	public String Url_Home_Page;
	
	public String getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(String dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public String getImporto() {
		return importo;
	}
	public void setImporto(String importo) {
		this.importo = importo;
	}
	public String getEnte() {
		return ente;
	}
	public void setEnte(String ente) {
		this.ente = ente;
	}
	public String getTributo() {
		return tributo;
	}
	public void setTributo(String tributo) {
		this.tributo = tributo;
	}
	public String getDescrizionePendenza() {
		return descrizionePendenza;
	}
	public void setDescrizionePendenza(String descrizionePendenza) {
		this.descrizionePendenza = descrizionePendenza;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCausale() {
		return causale;
	}
	public void setCausale(String causale) {
		this.causale = causale;
	}
	public String getUrl_Home_Page() {
		return Url_Home_Page;
	}
	public void setUrl_Home_Page(String url_Home_Page) {
		Url_Home_Page = url_Home_Page;
	}
	
	
}

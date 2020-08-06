package it.tasgroup.ge.pojo;

import java.math.BigDecimal;
import java.util.Date;



public class InvioQuietanza extends CommunicationEventDetail {
	public String token;
	public Date dataPagamento;
	public BigDecimal importo;
	public String quietanza;
	public String Url_Home_Page;
	public String causale;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public String getQuietanza() {
		return quietanza;
	}
	public void setQuietanza(String quietanza) {
		this.quietanza = quietanza;
	}
	public String getUrl_Home_Page() {
		return Url_Home_Page;
	}
	public void setUrl_Home_Page(String url_Home_Page) {
		Url_Home_Page = url_Home_Page;
	}
	public String getCausale() {
		return causale;
	}
	public void setCausale(String causale) {
		this.causale = causale;
	}
	
	
}

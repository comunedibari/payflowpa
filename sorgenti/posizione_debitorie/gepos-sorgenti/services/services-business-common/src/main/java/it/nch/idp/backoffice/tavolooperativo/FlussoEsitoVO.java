package it.nch.idp.backoffice.tavolooperativo;

import java.math.BigDecimal;
import java.util.Date;

public class FlussoEsitoVO {
	
	private String idDisposizione;
	private String idEsito;
	private Date dataValuta;
	private BigDecimal importo;
	private String cro;
	public String getIdDisposizione() {
		return idDisposizione;
	}
	public void setIdDisposizione(String idDisposizione) {
		this.idDisposizione = idDisposizione;
	}
	public String getIdEsito() {
		return idEsito;
	}
	public void setIdEsito(String idEsito) {
		this.idEsito = idEsito;
	}
	public Date getDataValuta() {
		return dataValuta;
	}
	public void setDataValuta(Date dataValuta) {
		this.dataValuta = dataValuta;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public String getCro() {
		return cro;
	}
	public void setCro(String cro) {
		this.cro = cro;
	}
	
	

}

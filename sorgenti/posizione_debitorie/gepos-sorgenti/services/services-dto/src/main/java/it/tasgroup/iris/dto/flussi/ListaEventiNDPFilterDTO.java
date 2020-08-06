package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;
import java.sql.Timestamp;

public class ListaEventiNDPFilterDTO implements Serializable {
	
	private Long idEvento;
	private String tipoEvento;
	private String idDominio;
	private String iuv;
	private String codContesto;
	private String idPSP;
	
	private Timestamp dataDa;
	private Timestamp dataA;
    
	public String getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	public String getIdDominio() {
		return idDominio;
	}
	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}
	public String getIuv() {
		return iuv;
	}
	public void setIuv(String iuv) {
		this.iuv = iuv;
	}
	public Timestamp getDataDa() {
		return dataDa;
	}
	public void setDataDa(Timestamp dataDa) {
		this.dataDa = dataDa;
	}
	public Timestamp getDataA() {
		return dataA;
	}
	public void setDataA(Timestamp dataA) {
		this.dataA = dataA;
	}
	public String getCodContesto() {
		return codContesto;
	}
	public void setCodContesto(String codContesto) {
		this.codContesto = codContesto;
	}
	public String getIdPSP() {
		return idPSP;
	}
	public void setIdPSP(String idPSP) {
		this.idPSP = idPSP;
	}
	public Long getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}
     
}

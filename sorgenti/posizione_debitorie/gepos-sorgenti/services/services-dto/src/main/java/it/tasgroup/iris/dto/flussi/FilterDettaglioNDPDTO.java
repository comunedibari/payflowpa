package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class FilterDettaglioNDPDTO implements Serializable{
	
	private String iuv;
	private String idPSP;
	private String transRefNumber;
	private String cfContoTecnico;
	private Timestamp dataDa;
	private Timestamp dataA;
	private BigDecimal importoDa;
	private BigDecimal importoA;
	
	
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
	public BigDecimal getImportoDa() {
		return importoDa;
	}
	public void setImportoDa(BigDecimal importoDa) {
		this.importoDa = importoDa;
	}
	public BigDecimal getImportoA() {
		return importoA;
	}
	public void setImportoA(BigDecimal importoA) {
		this.importoA = importoA;
	}
	public String getIdPSP() {
		return idPSP;
	}
	public void setIdPSP(String idPSP) {
		this.idPSP = idPSP;
	}
	public String getIuv() {
		return iuv;
	}
	public void setIuv(String iuv) {
		this.iuv = iuv;
	}
	public String getTransRefNumber() {
		return transRefNumber;
	}
	public void setTransRefNumber(String transRefNumber) {
		this.transRefNumber = transRefNumber;
	}
	public String getCfContoTecnico() {
		return cfContoTecnico;
	}
	public void setCfContoTecnico(String cfContoTecnico) {
		this.cfContoTecnico = cfContoTecnico;
	}
	
}
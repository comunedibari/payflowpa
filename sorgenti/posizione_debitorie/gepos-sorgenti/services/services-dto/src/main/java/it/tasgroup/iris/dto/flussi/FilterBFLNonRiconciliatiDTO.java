package it.tasgroup.iris.dto.flussi;

import it.tasgroup.services.util.enumeration.EnumStatoChiusuraRiversamento;
import it.tasgroup.services.util.enumeration.EnumStatoRiversamento;
import it.tasgroup.services.util.enumeration.EnumTipoAnomaliaIncasso;
import it.tasgroup.services.util.enumeration.EnumTipoOperazione;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class FilterBFLNonRiconciliatiDTO implements Serializable{
	
	private String idRiconciliazione;
	private Timestamp dataContabile;
	private Timestamp dataAccreditoDa;
	private Timestamp dataAccreditoA;
	private String stato;
	private String codRifOperazione;
	private String transRefNumber;
	private BigDecimal importoDa;
	private BigDecimal importoA;
	private EnumStatoChiusuraRiversamento statoChiusura;
	private EnumStatoRiversamento statoRiversamento;
	private EnumTipoAnomaliaIncasso tipoAnomalia;
	private EnumTipoOperazione tipoOperazione;
	private String codTribEnte;
	private String IBAN;
	private String ibanAccredito;
	
	
	public String getIdRiconciliazione() {
		return idRiconciliazione;
	}
	public void setIdRiconciliazione(String string) {
		this.idRiconciliazione = string;
	}
	public Timestamp getDataContabile() {
		return dataContabile;
	}
	public void setDataContabile(Timestamp dataContabile) {
		this.dataContabile = dataContabile;
	}
	
	public String getStato() {
		return stato;
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public String getCodRifOperazione() {
		return codRifOperazione;
	}
	public void setCodRifOperazione(String codRifOperazione) {
		this.codRifOperazione = codRifOperazione;
	}
	public String getTransRefNumber() {
		return transRefNumber;
	}
	public void setTransRefNumber(String transRefNumber) {
		this.transRefNumber = transRefNumber;
	}
	
	public EnumStatoChiusuraRiversamento getStatoChiusura() {
		return statoChiusura;
	}
	public void setStatoChiusura(EnumStatoChiusuraRiversamento statoChiusura) {
		this.statoChiusura = statoChiusura;
	}
	public EnumStatoRiversamento getStatoRiversamento() {
		return statoRiversamento;
	}
	public void setStatoRiversamento(EnumStatoRiversamento statoRiversamento) {
		this.statoRiversamento = statoRiversamento;
	}
	public EnumTipoAnomaliaIncasso getTipoAnomalia() {
		return tipoAnomalia;
	}
	public void setTipoAnomalia(EnumTipoAnomaliaIncasso tipoAnomalia) {
		this.tipoAnomalia = tipoAnomalia;
	}
	public EnumTipoOperazione getTipoOperazione() {
		return tipoOperazione;
	}
	public void setTipoOperazione(EnumTipoOperazione tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}
	
	public Timestamp getDataAccreditoDa() {
		return dataAccreditoDa;
	}
	public void setDataAccreditoDa(Timestamp dataAccreditoDa) {
		this.dataAccreditoDa = dataAccreditoDa;
	}
	public Timestamp getDataAccreditoA() {
		return dataAccreditoA;
	}
	public void setDataAccreditoA(Timestamp dataAccreditoA) {
		this.dataAccreditoA = dataAccreditoA;
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
	public String getCodTribEnte() {
		return codTribEnte;
	}
	public void setCodTribEnte(String codTribEnte) {
		this.codTribEnte = codTribEnte;
	}
	public String getIBAN() {
		return IBAN;
	}
	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}
	public String getIbanAccredito() {
		return ibanAccredito;
	}
	public void setIbanAccredito(String ibanAccredito) {
		this.ibanAccredito = ibanAccredito;
	}
	
}
package it.tasgroup.iris.dto.flussi;

import it.tasgroup.services.util.enumeration.EnumStatoRiconciliazione;
import it.tasgroup.services.util.enumeration.EnumTipoAccredito;
import it.tasgroup.services.util.enumeration.EnumTipoAnomaliaNDP;
import it.tasgroup.services.util.enumeration.EnumTipoOperazione;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FilterNDPNonRiconciliatiDTO implements Serializable{
	
	private String idRiconciliazione;
	private Timestamp dataContabile;
	private Timestamp dataAccreditoDa;
	private Timestamp dataAccreditoA;
	private String stato;
	private String codRifOperazione;
	private String transRefNumber;
	private BigDecimal importoDa;
	private BigDecimal importoA;
	private EnumTipoAccredito tipoAccredito;
	private EnumStatoRiconciliazione statoRiconciliazione;
	private EnumTipoAnomaliaNDP tipoAnomalia;
	private EnumTipoOperazione tipoOperazione;
	private String codTribEnte;
	private String IBAN;
	private List<String> IBANAccredito = new ArrayList<String>();
	
	
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
	
	public EnumTipoAccredito getTipoAccredito() {
		return tipoAccredito;
	}
	
	public void setTipoAccredito(EnumTipoAccredito tipoAccredito) {
		this.tipoAccredito = tipoAccredito;
	}
	
	public EnumStatoRiconciliazione getStatoRiconciliazione() {
		return statoRiconciliazione;
	}
	
	public void setStatoRiconciliazione(EnumStatoRiconciliazione statoRiconciliazione) {
		this.statoRiconciliazione = statoRiconciliazione;
	}
	
	public EnumTipoAnomaliaNDP getTipoAnomalia() {
		return tipoAnomalia;
	}
	
	public void setTipoAnomalia(EnumTipoAnomaliaNDP tipoAnomalia) {
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
	
	public List<String> getIBANAccredito() {
		return IBANAccredito;
	}
	
	public void setIBANAccredito(List<String> iBANAccredito) {
		IBANAccredito = iBANAccredito;
	}
	
}
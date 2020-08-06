package it.tasgroup.iris.dto.flussi;

import it.tasgroup.services.util.enumeration.EnumStatoChiusuraRiversamento;
import it.tasgroup.services.util.enumeration.EnumStatoIncasso;
import it.tasgroup.services.util.enumeration.EnumStatoRiversamento;
import it.tasgroup.services.util.enumeration.EnumTipoAnomaliaIncasso;
import it.tasgroup.services.util.enumeration.EnumTipoOperazione;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class BFLNonRiconciliatoDTO implements Serializable{
	
	private String idRiconciliazione;
	private String idRiconciliazioneOrig;
	private String idRiversamento;
	private Long idIncasso;
	private Long idCasellario;
	private Timestamp dataContabile;
	private Timestamp dataValutaAccredito;
	private Integer progr61;
	private Integer progr62;
	private String stato;
	private String codRifOperazione;
	private String transRefNumber;
	private String note;
	private String disposizione;
	private BigDecimal importo;
	private EnumStatoChiusuraRiversamento statoChiusura;
	private EnumStatoRiversamento statoRiversamento;
	private EnumTipoAnomaliaIncasso tipoAnomalia;
	private EnumTipoOperazione tipoOperazione;
	private String IBAN;
	private String ibanAccredito;
	private String codTribEnte;
	
	private EnumStatoIncasso statoIncasso;

	
	public String getIdRiconciliazione() {
		return idRiconciliazione;
	}
	public void setIdRiconciliazione(String idRiconciliazione) {
		this.idRiconciliazione = idRiconciliazione;
	}
	public Timestamp getDataContabile() {
		return dataContabile;
	}
	public void setDataContabile(Timestamp dataContabile) {
		this.dataContabile = dataContabile;
	}
	public Timestamp getDataValutaAccredito() {
		return dataValutaAccredito;
	}
	public void setDataValutaAccredito(Timestamp dataValutaAccredito) {
		this.dataValutaAccredito = dataValutaAccredito;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	
	public String getDisposizione() {
		return disposizione;
	}
	public void setDisposizione(String disposizione) {
		this.disposizione = disposizione;
	}
	public Long getIdCasellario() {
		return idCasellario;
	}
	public void setIdCasellario(Long idCasellario) {
		this.idCasellario = idCasellario;
	}
	public Integer getProgr61() {
		return progr61;
	}
	public void setProgr61(Integer progr61) {
		this.progr61 = progr61;
	}
	public Integer getProgr62() {
		return progr62;
	}
	public void setProgr62(Integer progr62) {
		this.progr62 = progr62;
	}
	public Long getIdIncasso() {
		return idIncasso;
	}
	public void setIdIncasso(Long idIncasso) {
		this.idIncasso = idIncasso;
	}
	public String getIdRiversamento() {
		return idRiversamento;
	}
	public void setIdRiversamento(String idRiversamento) {
		this.idRiversamento = idRiversamento;
	}
	public EnumStatoIncasso getStatoIncasso() {
		return statoIncasso;
	}
	public void setStatoIncasso(EnumStatoIncasso statoIncasso) {
		this.statoIncasso = statoIncasso;
	}
	
	public String getIdRiconciliazioneOrig() {
		return idRiconciliazioneOrig;
	}
	
	public void setIdRiconciliazioneOrig(String idRiconciliazioneOrig) {
		this.idRiconciliazioneOrig = idRiconciliazioneOrig;
	}
	
	public boolean isModifiable(){
		
		boolean modifiable = false; //EnumStatoIncasso.RIACCREDITATO_ENTE.equals(this.statoIncasso) || EnumStatoRiversamento.GIA_RIVERSATO.equals(this.statoRiversamento);
		
		modifiable = EnumStatoChiusuraRiversamento.CHIUSA.equals(this.getStatoChiusura());
		
		return modifiable;
		
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

	public String getCodTribEnte() {
		return codTribEnte;
	}
	
	public void setCodTribEnte(String codTribEnte) {
		this.codTribEnte = codTribEnte;
	}
	
	
}
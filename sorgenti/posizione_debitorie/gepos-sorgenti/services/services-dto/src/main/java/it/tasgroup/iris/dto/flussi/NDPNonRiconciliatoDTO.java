package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import it.tasgroup.services.util.enumeration.EnumStatoIncasso;
import it.tasgroup.services.util.enumeration.EnumStatoRiconciliazione;
import it.tasgroup.services.util.enumeration.EnumTipoAccredito;
import it.tasgroup.services.util.enumeration.EnumTipoAnomaliaNDP;
import it.tasgroup.services.util.enumeration.EnumTipoMovimento;

public class NDPNonRiconciliatoDTO implements Serializable{
	
	private String idRiconciliazione;
	private String idRiconciliazioneOrig;
	private String idRiversamento;
	private Long idIncasso;
	private Long id;
	private Long idCasellario;
	private Timestamp dataContabile;
	private Timestamp dataValutaAccredito;
	private Integer progr61;
	private Integer progr62;
	private String contoEvidenza;
	private String numeroDocumento;
	private String codRifOperazione;
	private String transRefNumber;
	private String note;
	private String disposizione;
	private BigDecimal importo;
	private EnumStatoRiconciliazione statoRiconciliazione;
	private EnumTipoAnomaliaNDP tipoAnomalia;
	private EnumTipoAccredito tipoAccredito;
	private EnumTipoAccredito tipoRiconciliazione;
	private String IBAN;
	private String codTribEnte;	
	private EnumStatoIncasso statoIncasso;
    private String idAbbinato;
    private EnumTipoMovimento tipoMovimento;

	
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
	
	public EnumTipoAnomaliaNDP getTipoAnomalia() {
		return tipoAnomalia;
	}
	public void setTipoAnomalia(EnumTipoAnomaliaNDP tipoAnomalia) {
		this.tipoAnomalia = tipoAnomalia;
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
	public String getContoEvidenza() {
		return contoEvidenza;
	}
	public void setContoEvidenza(String contoEvidenza) {
		this.contoEvidenza = contoEvidenza;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
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
	
//	public boolean isModifiable(){
//		
//		boolean modifiable = false; //EnumStatoIncasso.RIACCREDITATO_ENTE.equals(this.statoIncasso) || EnumStatoRiversamento.GIA_RIVERSATO.equals(this.statoRiversamento);
//		
//		modifiable = EnumStatoChiusuraRiversamento.CHIUSA.equals(this.getStatoChiusura());
//		
//		return modifiable;
//		
//	}
	
	public String getIBAN() {
		return IBAN;
	}
	
	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}
	
	public String getCodTribEnte() {
		return codTribEnte;
	}
	
	public void setCodTribEnte(String codTribEnte) {
		this.codTribEnte = codTribEnte;
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
	public void setStatoRiconciliazione(
			EnumStatoRiconciliazione statoRiconciliazione) {
		this.statoRiconciliazione = statoRiconciliazione;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdAbbinato() {
		return idAbbinato;
	}
	public void setIdAbbinato(String idAbbinato) {
		this.idAbbinato = idAbbinato;
	}
	
	public boolean isAbbinato(){
		
		return idAbbinato != null && idAbbinato.trim().length()>0;
		
	}
	public EnumTipoAccredito getTipoRiconciliazione() {
		return tipoRiconciliazione;
	}
	public void setTipoRiconciliazione(EnumTipoAccredito tipoRiconciliazione) {
		this.tipoRiconciliazione = tipoRiconciliazione;
	}
	
	public EnumTipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(EnumTipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	
}
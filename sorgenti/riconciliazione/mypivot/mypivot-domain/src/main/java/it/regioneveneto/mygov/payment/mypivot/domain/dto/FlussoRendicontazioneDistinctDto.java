package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import java.math.BigDecimal;
import java.util.Date;

public class FlussoRendicontazioneDistinctDto {

	private Long mygovEnteId;
	private Long manageFlussoId;
	private String codIdentificativoFlusso;
	private String codIdentificativoUnivocoRegolamento;
	private String codIstMittIdUnivMittCodiceIdentificativoUnivoco;
	private char codIstMittIdUnivMittTipoIdentificativoUnivoco;
	private String codIstRicevIdUnivRicevCodiceIdentificativoUnivoco;
	private Character codIstRicevIdUnivRicevTipoIdentificativoUnivoco;
	private String codiceBicBancaDiRiversamento;
	private String deIstMittDenominazioneMittente;
	private String deIstRicevDenominazioneRicevente;
	private Date dtAcquisizione;
	private Date dtDataOraFlusso;
	private Date dtDataRegolamento;
	private String identificativoPsp;
	private BigDecimal numImportoTotalePagamenti;
	private long numNumeroTotalePagamenti;
	private String deImportoTotalePagamenti;

	public Long getMygovEnteId() {
		return mygovEnteId;
	}

	public void setMygovEnteId(Long mygovEnteId) {
		this.mygovEnteId = mygovEnteId;
	}

	public Long getManageFlussoId() {
		return manageFlussoId;
	}

	public void setManageFlussoId(Long manageFlussoId) {
		this.manageFlussoId = manageFlussoId;
	}

	public String getCodIdentificativoFlusso() {
		return codIdentificativoFlusso;
	}

	public void setCodIdentificativoFlusso(String codIdentificativoFlusso) {
		this.codIdentificativoFlusso = codIdentificativoFlusso;
	}

	public String getCodIdentificativoUnivocoRegolamento() {
		return codIdentificativoUnivocoRegolamento;
	}

	public void setCodIdentificativoUnivocoRegolamento(String codIdentificativoUnivocoRegolamento) {
		this.codIdentificativoUnivocoRegolamento = codIdentificativoUnivocoRegolamento;
	}

	public String getCodIstMittIdUnivMittCodiceIdentificativoUnivoco() {
		return codIstMittIdUnivMittCodiceIdentificativoUnivoco;
	}

	public void setCodIstMittIdUnivMittCodiceIdentificativoUnivoco(
			String codIstMittIdUnivMittCodiceIdentificativoUnivoco) {
		this.codIstMittIdUnivMittCodiceIdentificativoUnivoco = codIstMittIdUnivMittCodiceIdentificativoUnivoco;
	}

	public char getCodIstMittIdUnivMittTipoIdentificativoUnivoco() {
		return codIstMittIdUnivMittTipoIdentificativoUnivoco;
	}

	public void setCodIstMittIdUnivMittTipoIdentificativoUnivoco(char codIstMittIdUnivMittTipoIdentificativoUnivoco) {
		this.codIstMittIdUnivMittTipoIdentificativoUnivoco = codIstMittIdUnivMittTipoIdentificativoUnivoco;
	}

	public String getCodIstRicevIdUnivRicevCodiceIdentificativoUnivoco() {
		return codIstRicevIdUnivRicevCodiceIdentificativoUnivoco;
	}

	public void setCodIstRicevIdUnivRicevCodiceIdentificativoUnivoco(
			String codIstRicevIdUnivRicevCodiceIdentificativoUnivoco) {
		this.codIstRicevIdUnivRicevCodiceIdentificativoUnivoco = codIstRicevIdUnivRicevCodiceIdentificativoUnivoco;
	}

	public Character getCodIstRicevIdUnivRicevTipoIdentificativoUnivoco() {
		return codIstRicevIdUnivRicevTipoIdentificativoUnivoco;
	}

	public void setCodIstRicevIdUnivRicevTipoIdentificativoUnivoco(
			Character codIstRicevIdUnivRicevTipoIdentificativoUnivoco) {
		this.codIstRicevIdUnivRicevTipoIdentificativoUnivoco = codIstRicevIdUnivRicevTipoIdentificativoUnivoco;
	}

	public String getCodiceBicBancaDiRiversamento() {
		return codiceBicBancaDiRiversamento;
	}

	public void setCodiceBicBancaDiRiversamento(String codiceBicBancaDiRiversamento) {
		this.codiceBicBancaDiRiversamento = codiceBicBancaDiRiversamento;
	}

	public String getDeIstMittDenominazioneMittente() {
		return deIstMittDenominazioneMittente;
	}

	public void setDeIstMittDenominazioneMittente(String deIstMittDenominazioneMittente) {
		this.deIstMittDenominazioneMittente = deIstMittDenominazioneMittente;
	}

	public String getDeIstRicevDenominazioneRicevente() {
		return deIstRicevDenominazioneRicevente;
	}

	public void setDeIstRicevDenominazioneRicevente(String deIstRicevDenominazioneRicevente) {
		this.deIstRicevDenominazioneRicevente = deIstRicevDenominazioneRicevente;
	}

	public Date getDtAcquisizione() {
		return dtAcquisizione;
	}

	public void setDtAcquisizione(Date dtAcquisizione) {
		this.dtAcquisizione = dtAcquisizione;
	}

	public Date getDtDataOraFlusso() {
		return dtDataOraFlusso;
	}

	public void setDtDataOraFlusso(Date dtDataOraFlusso) {
		this.dtDataOraFlusso = dtDataOraFlusso;
	}

	public Date getDtDataRegolamento() {
		return dtDataRegolamento;
	}

	public void setDtDataRegolamento(Date dtDataRegolamento) {
		this.dtDataRegolamento = dtDataRegolamento;
	}

	public String getIdentificativoPsp() {
		return identificativoPsp;
	}

	public void setIdentificativoPsp(String identificativoPsp) {
		this.identificativoPsp = identificativoPsp;
	}

	public BigDecimal getNumImportoTotalePagamenti() {
		return numImportoTotalePagamenti;
	}

	public void setNumImportoTotalePagamenti(BigDecimal numImportoTotalePagamenti) {
		this.numImportoTotalePagamenti = numImportoTotalePagamenti;
	}

	public long getNumNumeroTotalePagamenti() {
		return numNumeroTotalePagamenti;
	}

	public void setNumNumeroTotalePagamenti(long numNumeroTotalePagamenti) {
		this.numNumeroTotalePagamenti = numNumeroTotalePagamenti;
	}

	public String getDeImportoTotalePagamenti() {
		return deImportoTotalePagamenti;
	}

	public void setDeImportoTotalePagamenti(String deImportoTotalePagamenti) {
		this.deImportoTotalePagamenti = deImportoTotalePagamenti;
	}

}

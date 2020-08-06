package it.tasgroup.iris.dto.flussi;

import java.math.BigDecimal;
import java.util.Date;

public class ExportCSV_CST_DTO {
	
	private String idPendenza;
	private String coDestinatario;
	private String codEnte;                   // getCodiceEnte
	private Date dataEmissione;
	private Date dataScadenza;
	private Date dataFineValidita;
	private String ente;                       // getDenominazione
	private String tributo;                    // getDeTrb
	private String tipoTributo;
	private String idTributo;
	private String cod_tributo;                // getCdTrbEnte
	
	private String idEnte;
	private String cdPlugin;
	
	private Integer tributo_strutturato;
	private String causale;
	private String codiceFiscale;
	private BigDecimal importo;
	private String modalita;
	private BigDecimal importoPagato;
	private BigDecimal importoRiscosso;
	private String stato;
	private String codicePendenza;
	private BigDecimal importoSbf;
	private BigDecimal importoNonPagabile;
	private Integer condizioniPagabili;
	private Integer condizioniNonPagabili;
	private Integer annoRif;
	private String note;
	private String urlUpdateService;
	private String descrizioneMittente;
	private String riscossore;
	private String riferimento;
	private Date dataCreazione;
	private String idPendenzaEnte;
	
	private String istitutoAttestante;
	private String idAttestante;
	private String tipoIdAttestante;

	private String iuv;
	
	public ExportCSV_CST_DTO(){}
	
	public String getIdPendenza() {
		return idPendenza;
	}

	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}

	public String getCoDestinatario() {
		return coDestinatario;
	}

	public void setCoDestinatario(String coDestinatario) {
		this.coDestinatario = coDestinatario;
	}

	public String getCodEnte() {
		return codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	public Date getDataEmissione() {
		return dataEmissione;
	}

	public void setDataEmissione(Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
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

	public String getTipoTributo() {
		return tipoTributo;
	}

	public void setTipoTributo(String tipoTributo) {
		this.tipoTributo = tipoTributo;
	}

	public String getIdTributo() {
		return idTributo;
	}

	public void setIdTributo(String idTributo) {
		this.idTributo = idTributo;
	}

	public String getCod_tributo() {
		return cod_tributo;
	}

	public void setCod_tributo(String cod_tributo) {
		this.cod_tributo = cod_tributo;
	}

	public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	public String getCdPlugin() {
		return cdPlugin;
	}

	public void setCdPlugin(String cdPlugin) {
		this.cdPlugin = cdPlugin;
	}

	public Integer getTributo_strutturato() {
		return tributo_strutturato;
	}

	public void setTributo_strutturato(Integer tributo_strutturato) {
		this.tributo_strutturato = tributo_strutturato;
	}

	public String getCausale() {
		return causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getModalita() {
		return modalita;
	}

	public void setModalita(String modalita) {
		this.modalita = modalita;
	}

	public BigDecimal getImportoPagato() {
		return importoPagato;
	}

	public void setImportoPagato(BigDecimal importoPagato) {
		this.importoPagato = importoPagato;
	}

	public BigDecimal getImportoRiscosso() {
		return importoRiscosso;
	}

	public void setImportoRiscosso(BigDecimal importoRiscosso) {
		this.importoRiscosso = importoRiscosso;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getCodicePendenza() {
		return codicePendenza;
	}

	public void setCodicePendenza(String codicePendenza) {
		this.codicePendenza = codicePendenza;
	}

	public BigDecimal getImportoSbf() {
		return importoSbf;
	}

	public void setImportoSbf(BigDecimal importoSbf) {
		this.importoSbf = importoSbf;
	}

	public BigDecimal getImportoNonPagabile() {
		return importoNonPagabile;
	}

	public void setImportoNonPagabile(BigDecimal importoNonPagabile) {
		this.importoNonPagabile = importoNonPagabile;
	}

	public Integer getCondizioniPagabili() {
		return condizioniPagabili;
	}

	public void setCondizioniPagabili(Integer condizioniPagabili) {
		this.condizioniPagabili = condizioniPagabili;
	}

	public Integer getCondizioniNonPagabili() {
		return condizioniNonPagabili;
	}

	public void setCondizioniNonPagabili(Integer condizioniNonPagabili) {
		this.condizioniNonPagabili = condizioniNonPagabili;
	}

	public Integer getAnnoRif() {
		return annoRif;
	}

	public void setAnnoRif(Integer annoRif) {
		this.annoRif = annoRif;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUrlUpdateService() {
		return urlUpdateService;
	}

	public void setUrlUpdateService(String urlUpdateService) {
		this.urlUpdateService = urlUpdateService;
	}

	public String getDescrizioneMittente() {
		return descrizioneMittente;
	}

	public void setDescrizioneMittente(String descrizioneMittente) {
		this.descrizioneMittente = descrizioneMittente;
	}

	public String getRiscossore() {
		return riscossore;
	}

	public void setRiscossore(String riscossore) {
		this.riscossore = riscossore;
	}

	public String getRiferimento() {
		return riferimento;
	}

	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getIdPendenzaEnte() {
		return idPendenzaEnte;
	}

	public void setIdPendenzaEnte(String idPendenzaEnte) {
		this.idPendenzaEnte = idPendenzaEnte;
	}

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}
	public String getIstitutoAttestante() {
		return istitutoAttestante;
	}

	public void setIstitutoAttestante(String istitutoAttestante) {
		this.istitutoAttestante = istitutoAttestante;
	}

	public String getIdAttestante() {
		return idAttestante;
	}

	public void setIdAttestante(String idAttestante) {
		this.idAttestante = idAttestante;
	}

	public String getTipoIdAttestante() {
		return tipoIdAttestante;
	}

	public void setTipoIdAttestante(String tipoIdAttestante) {
		this.tipoIdAttestante = tipoIdAttestante;
	}

}

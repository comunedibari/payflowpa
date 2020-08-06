package it.regioneveneto.mygov.payment.mypivot.domain.po;
// Generated Nov 15, 2017 2:45:28 PM by Hibernate Tools 4.0.1.Final

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * FlussoExport generated by hbm2java
 */
@Entity
@Table(name = "mygov_flusso_export")
public class FlussoExport implements java.io.Serializable {

	private FlussoExportId id;
	private int version;
	private ManageFlusso manageFlusso;
	private Ente ente;
	private Date dtCreazione;
	private Date dtUltimaModifica;
	private String deNomeFlusso;
	private Integer numRigaFlusso;
	private String codIud;
	private String deEVersioneOggetto;
	private String codEDomIdDominio;
	private String codEDomIdStazioneRichiedente;
	private String codEIdMessaggioRicevuta;
	private Date dtEDataOraMessaggioRicevuta;
	private String codERiferimentoMessaggioRichiesta;
	private Date dtERiferimentoDataRichiesta;
	private Character codEIstitAttIdUnivAttTipoIdUnivoco;
	private String codEIstitAttIdUnivAttCodiceIdUnivoco;
	private String deEIstitAttDenominazioneAttestante;
	private String codEIstitAttCodiceUnitOperAttestante;
	private String deEIstitAttDenomUnitOperAttestante;
	private String deEIstitAttIndirizzoAttestante;
	private String deEIstitAttCivicoAttestante;
	private String codEIstitAttCapAttestante;
	private String deEIstitAttLocalitaAttestante;
	private String deEIstitAttProvinciaAttestante;
	private String codEIstitAttNazioneAttestante;
	private Character codEEnteBenefIdUnivBenefTipoIdUnivoco;
	private String codEEnteBenefIdUnivBenefCodiceIdUnivoco;
	private String deEEnteBenefDenominazioneBeneficiario;
	private String codEEnteBenefCodiceUnitOperBeneficiario;
	private String deEEnteBenefDenomUnitOperBeneficiario;
	private String deEEnteBenefIndirizzoBeneficiario;
	private String deEEnteBenefCivicoBeneficiario;
	private String codEEnteBenefCapBeneficiario;
	private String deEEnteBenefLocalitaBeneficiario;
	private String deEEnteBenefProvinciaBeneficiario;
	private String codEEnteBenefNazioneBeneficiario;
	private Character codESoggVersIdUnivVersTipoIdUnivoco;
	private String codESoggVersIdUnivVersCodiceIdUnivoco;
	private String codESoggVersAnagraficaVersante;
	private String deESoggVersIndirizzoVersante;
	private String deESoggVersCivicoVersante;
	private String codESoggVersCapVersante;
	private String deESoggVersLocalitaVersante;
	private String deESoggVersProvinciaVersante;
	private String codESoggVersNazioneVersante;
	private String deESoggVersEmailVersante;
	private Character codESoggPagIdUnivPagTipoIdUnivoco;
	private String codESoggPagIdUnivPagCodiceIdUnivoco;
	private String codESoggPagAnagraficaPagatore;
	private String deESoggPagIndirizzoPagatore;
	private String deESoggPagCivicoPagatore;
	private String codESoggPagCapPagatore;
	private String deESoggPagLocalitaPagatore;
	private String deESoggPagProvinciaPagatore;
	private String codESoggPagNazionePagatore;
	private String deESoggPagEmailPagatore;
	private Character codEDatiPagCodiceEsitoPagamento;
	private BigDecimal numEDatiPagImportoTotalePagato;
	private String codEDatiPagIdUnivocoVersamento;
	private String codEDatiPagCodiceContestoPagamento;
	private BigDecimal numEDatiPagDatiSingPagSingoloImportoPagato;
	private String deEDatiPagDatiSingPagEsitoSingoloPagamento;
	private Date dtEDatiPagDatiSingPagDataEsitoSingoloPagamento;
	private String deEDatiPagDatiSingPagCausaleVersamento;
	private String deEDatiPagDatiSingPagDatiSpecificiRiscossione;
	private String codTipoDovuto;
	private Date dtAcquisizione;
	private String deImportaDovutoEsito;
	private String deImportaDovutoFaultCode;
	private String deImportaDovutoFaultString;
	private String deImportaDovutoFaultId;
	private String deImportaDovutoFaultDescription;
	private Integer numImportaDovutoFaultSerial;
	private String bilancio;

	public FlussoExport() {
	}

	public FlussoExport(FlussoExportId id, ManageFlusso manageFlusso, Ente ente, Date dtCreazione,
			Date dtUltimaModifica, Date dtAcquisizione) {
		this.id = id;
		this.manageFlusso = manageFlusso;
		this.ente = ente;
		this.dtCreazione = dtCreazione;
		this.dtUltimaModifica = dtUltimaModifica;
		this.dtAcquisizione = dtAcquisizione;
	}

	public FlussoExport(FlussoExportId id, ManageFlusso manageFlusso, Ente ente, Date dtCreazione,
			Date dtUltimaModifica, String deNomeFlusso, Integer numRigaFlusso, String codIud, String deEVersioneOggetto,
			String codEDomIdDominio, String codEDomIdStazioneRichiedente, String codEIdMessaggioRicevuta,
			Date dtEDataOraMessaggioRicevuta, String codERiferimentoMessaggioRichiesta,
			Date dtERiferimentoDataRichiesta, Character codEIstitAttIdUnivAttTipoIdUnivoco,
			String codEIstitAttIdUnivAttCodiceIdUnivoco, String deEIstitAttDenominazioneAttestante,
			String codEIstitAttCodiceUnitOperAttestante, String deEIstitAttDenomUnitOperAttestante,
			String deEIstitAttIndirizzoAttestante, String deEIstitAttCivicoAttestante, String codEIstitAttCapAttestante,
			String deEIstitAttLocalitaAttestante, String deEIstitAttProvinciaAttestante,
			String codEIstitAttNazioneAttestante, Character codEEnteBenefIdUnivBenefTipoIdUnivoco,
			String codEEnteBenefIdUnivBenefCodiceIdUnivoco, String deEEnteBenefDenominazioneBeneficiario,
			String codEEnteBenefCodiceUnitOperBeneficiario, String deEEnteBenefDenomUnitOperBeneficiario,
			String deEEnteBenefIndirizzoBeneficiario, String deEEnteBenefCivicoBeneficiario,
			String codEEnteBenefCapBeneficiario, String deEEnteBenefLocalitaBeneficiario,
			String deEEnteBenefProvinciaBeneficiario, String codEEnteBenefNazioneBeneficiario,
			Character codESoggVersIdUnivVersTipoIdUnivoco, String codESoggVersIdUnivVersCodiceIdUnivoco,
			String codESoggVersAnagraficaVersante, String deESoggVersIndirizzoVersante,
			String deESoggVersCivicoVersante, String codESoggVersCapVersante, String deESoggVersLocalitaVersante,
			String deESoggVersProvinciaVersante, String codESoggVersNazioneVersante, String deESoggVersEmailVersante,
			Character codESoggPagIdUnivPagTipoIdUnivoco, String codESoggPagIdUnivPagCodiceIdUnivoco,
			String codESoggPagAnagraficaPagatore, String deESoggPagIndirizzoPagatore, String deESoggPagCivicoPagatore,
			String codESoggPagCapPagatore, String deESoggPagLocalitaPagatore, String deESoggPagProvinciaPagatore,
			String codESoggPagNazionePagatore, String deESoggPagEmailPagatore,
			Character codEDatiPagCodiceEsitoPagamento, BigDecimal numEDatiPagImportoTotalePagato,
			String codEDatiPagIdUnivocoVersamento, String codEDatiPagCodiceContestoPagamento,
			BigDecimal numEDatiPagDatiSingPagSingoloImportoPagato, String deEDatiPagDatiSingPagEsitoSingoloPagamento,
			Date dtEDatiPagDatiSingPagDataEsitoSingoloPagamento, String deEDatiPagDatiSingPagCausaleVersamento,
			String deEDatiPagDatiSingPagDatiSpecificiRiscossione, String codTipoDovuto, Date dtAcquisizione,
			String deImportaDovutoEsito, String deImportaDovutoFaultCode, String deImportaDovutoFaultString,
			String deImportaDovutoFaultId, String deImportaDovutoFaultDescription, Integer numImportaDovutoFaultSerial,
			String bilancio) {
		this.id = id;
		this.manageFlusso = manageFlusso;
		this.ente = ente;
		this.dtCreazione = dtCreazione;
		this.dtUltimaModifica = dtUltimaModifica;
		this.deNomeFlusso = deNomeFlusso;
		this.numRigaFlusso = numRigaFlusso;
		this.codIud = codIud;
		this.deEVersioneOggetto = deEVersioneOggetto;
		this.codEDomIdDominio = codEDomIdDominio;
		this.codEDomIdStazioneRichiedente = codEDomIdStazioneRichiedente;
		this.codEIdMessaggioRicevuta = codEIdMessaggioRicevuta;
		this.dtEDataOraMessaggioRicevuta = dtEDataOraMessaggioRicevuta;
		this.codERiferimentoMessaggioRichiesta = codERiferimentoMessaggioRichiesta;
		this.dtERiferimentoDataRichiesta = dtERiferimentoDataRichiesta;
		this.codEIstitAttIdUnivAttTipoIdUnivoco = codEIstitAttIdUnivAttTipoIdUnivoco;
		this.codEIstitAttIdUnivAttCodiceIdUnivoco = codEIstitAttIdUnivAttCodiceIdUnivoco;
		this.deEIstitAttDenominazioneAttestante = deEIstitAttDenominazioneAttestante;
		this.codEIstitAttCodiceUnitOperAttestante = codEIstitAttCodiceUnitOperAttestante;
		this.deEIstitAttDenomUnitOperAttestante = deEIstitAttDenomUnitOperAttestante;
		this.deEIstitAttIndirizzoAttestante = deEIstitAttIndirizzoAttestante;
		this.deEIstitAttCivicoAttestante = deEIstitAttCivicoAttestante;
		this.codEIstitAttCapAttestante = codEIstitAttCapAttestante;
		this.deEIstitAttLocalitaAttestante = deEIstitAttLocalitaAttestante;
		this.deEIstitAttProvinciaAttestante = deEIstitAttProvinciaAttestante;
		this.codEIstitAttNazioneAttestante = codEIstitAttNazioneAttestante;
		this.codEEnteBenefIdUnivBenefTipoIdUnivoco = codEEnteBenefIdUnivBenefTipoIdUnivoco;
		this.codEEnteBenefIdUnivBenefCodiceIdUnivoco = codEEnteBenefIdUnivBenefCodiceIdUnivoco;
		this.deEEnteBenefDenominazioneBeneficiario = deEEnteBenefDenominazioneBeneficiario;
		this.codEEnteBenefCodiceUnitOperBeneficiario = codEEnteBenefCodiceUnitOperBeneficiario;
		this.deEEnteBenefDenomUnitOperBeneficiario = deEEnteBenefDenomUnitOperBeneficiario;
		this.deEEnteBenefIndirizzoBeneficiario = deEEnteBenefIndirizzoBeneficiario;
		this.deEEnteBenefCivicoBeneficiario = deEEnteBenefCivicoBeneficiario;
		this.codEEnteBenefCapBeneficiario = codEEnteBenefCapBeneficiario;
		this.deEEnteBenefLocalitaBeneficiario = deEEnteBenefLocalitaBeneficiario;
		this.deEEnteBenefProvinciaBeneficiario = deEEnteBenefProvinciaBeneficiario;
		this.codEEnteBenefNazioneBeneficiario = codEEnteBenefNazioneBeneficiario;
		this.codESoggVersIdUnivVersTipoIdUnivoco = codESoggVersIdUnivVersTipoIdUnivoco;
		this.codESoggVersIdUnivVersCodiceIdUnivoco = codESoggVersIdUnivVersCodiceIdUnivoco;
		this.codESoggVersAnagraficaVersante = codESoggVersAnagraficaVersante;
		this.deESoggVersIndirizzoVersante = deESoggVersIndirizzoVersante;
		this.deESoggVersCivicoVersante = deESoggVersCivicoVersante;
		this.codESoggVersCapVersante = codESoggVersCapVersante;
		this.deESoggVersLocalitaVersante = deESoggVersLocalitaVersante;
		this.deESoggVersProvinciaVersante = deESoggVersProvinciaVersante;
		this.codESoggVersNazioneVersante = codESoggVersNazioneVersante;
		this.deESoggVersEmailVersante = deESoggVersEmailVersante;
		this.codESoggPagIdUnivPagTipoIdUnivoco = codESoggPagIdUnivPagTipoIdUnivoco;
		this.codESoggPagIdUnivPagCodiceIdUnivoco = codESoggPagIdUnivPagCodiceIdUnivoco;
		this.codESoggPagAnagraficaPagatore = codESoggPagAnagraficaPagatore;
		this.deESoggPagIndirizzoPagatore = deESoggPagIndirizzoPagatore;
		this.deESoggPagCivicoPagatore = deESoggPagCivicoPagatore;
		this.codESoggPagCapPagatore = codESoggPagCapPagatore;
		this.deESoggPagLocalitaPagatore = deESoggPagLocalitaPagatore;
		this.deESoggPagProvinciaPagatore = deESoggPagProvinciaPagatore;
		this.codESoggPagNazionePagatore = codESoggPagNazionePagatore;
		this.deESoggPagEmailPagatore = deESoggPagEmailPagatore;
		this.codEDatiPagCodiceEsitoPagamento = codEDatiPagCodiceEsitoPagamento;
		this.numEDatiPagImportoTotalePagato = numEDatiPagImportoTotalePagato;
		this.codEDatiPagIdUnivocoVersamento = codEDatiPagIdUnivocoVersamento;
		this.codEDatiPagCodiceContestoPagamento = codEDatiPagCodiceContestoPagamento;
		this.numEDatiPagDatiSingPagSingoloImportoPagato = numEDatiPagDatiSingPagSingoloImportoPagato;
		this.deEDatiPagDatiSingPagEsitoSingoloPagamento = deEDatiPagDatiSingPagEsitoSingoloPagamento;
		this.dtEDatiPagDatiSingPagDataEsitoSingoloPagamento = dtEDatiPagDatiSingPagDataEsitoSingoloPagamento;
		this.deEDatiPagDatiSingPagCausaleVersamento = deEDatiPagDatiSingPagCausaleVersamento;
		this.deEDatiPagDatiSingPagDatiSpecificiRiscossione = deEDatiPagDatiSingPagDatiSpecificiRiscossione;
		this.codTipoDovuto = codTipoDovuto;
		this.dtAcquisizione = dtAcquisizione;
		this.deImportaDovutoEsito = deImportaDovutoEsito;
		this.deImportaDovutoFaultCode = deImportaDovutoFaultCode;
		this.deImportaDovutoFaultString = deImportaDovutoFaultString;
		this.deImportaDovutoFaultId = deImportaDovutoFaultId;
		this.deImportaDovutoFaultDescription = deImportaDovutoFaultDescription;
		this.numImportaDovutoFaultSerial = numImportaDovutoFaultSerial;
		this.bilancio = bilancio;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "mygovEnteId", column = @Column(name = "mygov_ente_id", nullable = false)),
			@AttributeOverride(name = "codRpSilinviarpIdUnivocoVersamento", column = @Column(name = "cod_rp_silinviarp_id_univoco_versamento", nullable = false, length = 35)),
			@AttributeOverride(name = "codEDatiPagDatiSingPagIdUnivocoRiscoss", column = @Column(name = "cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss", nullable = false, length = 35)),
			@AttributeOverride(name = "indiceDatiSingoloPagamento", column = @Column(name = "indice_dati_singolo_pagamento", nullable = false)) })
	public FlussoExportId getId() {
		return this.id;
	}

	public void setId(FlussoExportId id) {
		this.id = id;
	}

	@Version
	@Column(name = "version", nullable = false)
	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mygov_manage_flusso_id", nullable = false)
	public ManageFlusso getManageFlusso() {
		return this.manageFlusso;
	}

	public void setManageFlusso(ManageFlusso manageFlusso) {
		this.manageFlusso = manageFlusso;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mygov_ente_id", nullable = false, insertable = false, updatable = false)
	public Ente getEnte() {
		return this.ente;
	}

	public void setEnte(Ente ente) {
		this.ente = ente;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_creazione", nullable = false, length = 29)
	public Date getDtCreazione() {
		return this.dtCreazione;
	}

	public void setDtCreazione(Date dtCreazione) {
		this.dtCreazione = dtCreazione;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_ultima_modifica", nullable = false, length = 29)
	public Date getDtUltimaModifica() {
		return this.dtUltimaModifica;
	}

	public void setDtUltimaModifica(Date dtUltimaModifica) {
		this.dtUltimaModifica = dtUltimaModifica;
	}

	@Column(name = "de_nome_flusso", length = 50)
	public String getDeNomeFlusso() {
		return this.deNomeFlusso;
	}

	public void setDeNomeFlusso(String deNomeFlusso) {
		this.deNomeFlusso = deNomeFlusso;
	}

	@Column(name = "num_riga_flusso")
	public Integer getNumRigaFlusso() {
		return this.numRigaFlusso;
	}

	public void setNumRigaFlusso(Integer numRigaFlusso) {
		this.numRigaFlusso = numRigaFlusso;
	}

	@Column(name = "cod_iud", length = 35)
	public String getCodIud() {
		return this.codIud;
	}

	public void setCodIud(String codIud) {
		this.codIud = codIud;
	}

	@Column(name = "de_e_versione_oggetto", length = 16)
	public String getDeEVersioneOggetto() {
		return this.deEVersioneOggetto;
	}

	public void setDeEVersioneOggetto(String deEVersioneOggetto) {
		this.deEVersioneOggetto = deEVersioneOggetto;
	}

	@Column(name = "cod_e_dom_id_dominio", length = 35)
	public String getCodEDomIdDominio() {
		return this.codEDomIdDominio;
	}

	public void setCodEDomIdDominio(String codEDomIdDominio) {
		this.codEDomIdDominio = codEDomIdDominio;
	}

	@Column(name = "cod_e_dom_id_stazione_richiedente", length = 35)
	public String getCodEDomIdStazioneRichiedente() {
		return this.codEDomIdStazioneRichiedente;
	}

	public void setCodEDomIdStazioneRichiedente(String codEDomIdStazioneRichiedente) {
		this.codEDomIdStazioneRichiedente = codEDomIdStazioneRichiedente;
	}

	@Column(name = "cod_e_id_messaggio_ricevuta", length = 35)
	public String getCodEIdMessaggioRicevuta() {
		return this.codEIdMessaggioRicevuta;
	}

	public void setCodEIdMessaggioRicevuta(String codEIdMessaggioRicevuta) {
		this.codEIdMessaggioRicevuta = codEIdMessaggioRicevuta;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_e_data_ora_messaggio_ricevuta", length = 29)
	public Date getDtEDataOraMessaggioRicevuta() {
		return this.dtEDataOraMessaggioRicevuta;
	}

	public void setDtEDataOraMessaggioRicevuta(Date dtEDataOraMessaggioRicevuta) {
		this.dtEDataOraMessaggioRicevuta = dtEDataOraMessaggioRicevuta;
	}

	@Column(name = "cod_e_riferimento_messaggio_richiesta", length = 35)
	public String getCodERiferimentoMessaggioRichiesta() {
		return this.codERiferimentoMessaggioRichiesta;
	}

	public void setCodERiferimentoMessaggioRichiesta(String codERiferimentoMessaggioRichiesta) {
		this.codERiferimentoMessaggioRichiesta = codERiferimentoMessaggioRichiesta;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_e_riferimento_data_richiesta", length = 13)
	public Date getDtERiferimentoDataRichiesta() {
		return this.dtERiferimentoDataRichiesta;
	}

	public void setDtERiferimentoDataRichiesta(Date dtERiferimentoDataRichiesta) {
		this.dtERiferimentoDataRichiesta = dtERiferimentoDataRichiesta;
	}

	@Column(name = "cod_e_istit_att_id_univ_att_tipo_id_univoco", length = 1)
	public Character getCodEIstitAttIdUnivAttTipoIdUnivoco() {
		return this.codEIstitAttIdUnivAttTipoIdUnivoco;
	}

	public void setCodEIstitAttIdUnivAttTipoIdUnivoco(Character codEIstitAttIdUnivAttTipoIdUnivoco) {
		this.codEIstitAttIdUnivAttTipoIdUnivoco = codEIstitAttIdUnivAttTipoIdUnivoco;
	}

	@Column(name = "cod_e_istit_att_id_univ_att_codice_id_univoco", length = 35)
	public String getCodEIstitAttIdUnivAttCodiceIdUnivoco() {
		return this.codEIstitAttIdUnivAttCodiceIdUnivoco;
	}

	public void setCodEIstitAttIdUnivAttCodiceIdUnivoco(String codEIstitAttIdUnivAttCodiceIdUnivoco) {
		this.codEIstitAttIdUnivAttCodiceIdUnivoco = codEIstitAttIdUnivAttCodiceIdUnivoco;
	}

	@Column(name = "de_e_istit_att_denominazione_attestante", length = 70)
	public String getDeEIstitAttDenominazioneAttestante() {
		return this.deEIstitAttDenominazioneAttestante;
	}

	public void setDeEIstitAttDenominazioneAttestante(String deEIstitAttDenominazioneAttestante) {
		this.deEIstitAttDenominazioneAttestante = deEIstitAttDenominazioneAttestante;
	}

	@Column(name = "cod_e_istit_att_codice_unit_oper_attestante", length = 35)
	public String getCodEIstitAttCodiceUnitOperAttestante() {
		return this.codEIstitAttCodiceUnitOperAttestante;
	}

	public void setCodEIstitAttCodiceUnitOperAttestante(String codEIstitAttCodiceUnitOperAttestante) {
		this.codEIstitAttCodiceUnitOperAttestante = codEIstitAttCodiceUnitOperAttestante;
	}

	@Column(name = "de_e_istit_att_denom_unit_oper_attestante", length = 70)
	public String getDeEIstitAttDenomUnitOperAttestante() {
		return this.deEIstitAttDenomUnitOperAttestante;
	}

	public void setDeEIstitAttDenomUnitOperAttestante(String deEIstitAttDenomUnitOperAttestante) {
		this.deEIstitAttDenomUnitOperAttestante = deEIstitAttDenomUnitOperAttestante;
	}

	@Column(name = "de_e_istit_att_indirizzo_attestante", length = 70)
	public String getDeEIstitAttIndirizzoAttestante() {
		return this.deEIstitAttIndirizzoAttestante;
	}

	public void setDeEIstitAttIndirizzoAttestante(String deEIstitAttIndirizzoAttestante) {
		this.deEIstitAttIndirizzoAttestante = deEIstitAttIndirizzoAttestante;
	}

	@Column(name = "de_e_istit_att_civico_attestante", length = 16)
	public String getDeEIstitAttCivicoAttestante() {
		return this.deEIstitAttCivicoAttestante;
	}

	public void setDeEIstitAttCivicoAttestante(String deEIstitAttCivicoAttestante) {
		this.deEIstitAttCivicoAttestante = deEIstitAttCivicoAttestante;
	}

	@Column(name = "cod_e_istit_att_cap_attestante", length = 16)
	public String getCodEIstitAttCapAttestante() {
		return this.codEIstitAttCapAttestante;
	}

	public void setCodEIstitAttCapAttestante(String codEIstitAttCapAttestante) {
		this.codEIstitAttCapAttestante = codEIstitAttCapAttestante;
	}

	@Column(name = "de_e_istit_att_localita_attestante", length = 35)
	public String getDeEIstitAttLocalitaAttestante() {
		return this.deEIstitAttLocalitaAttestante;
	}

	public void setDeEIstitAttLocalitaAttestante(String deEIstitAttLocalitaAttestante) {
		this.deEIstitAttLocalitaAttestante = deEIstitAttLocalitaAttestante;
	}

	@Column(name = "de_e_istit_att_provincia_attestante", length = 35)
	public String getDeEIstitAttProvinciaAttestante() {
		return this.deEIstitAttProvinciaAttestante;
	}

	public void setDeEIstitAttProvinciaAttestante(String deEIstitAttProvinciaAttestante) {
		this.deEIstitAttProvinciaAttestante = deEIstitAttProvinciaAttestante;
	}

	@Column(name = "cod_e_istit_att_nazione_attestante", length = 2)
	public String getCodEIstitAttNazioneAttestante() {
		return this.codEIstitAttNazioneAttestante;
	}

	public void setCodEIstitAttNazioneAttestante(String codEIstitAttNazioneAttestante) {
		this.codEIstitAttNazioneAttestante = codEIstitAttNazioneAttestante;
	}

	@Column(name = "cod_e_ente_benef_id_univ_benef_tipo_id_univoco", length = 1)
	public Character getCodEEnteBenefIdUnivBenefTipoIdUnivoco() {
		return this.codEEnteBenefIdUnivBenefTipoIdUnivoco;
	}

	public void setCodEEnteBenefIdUnivBenefTipoIdUnivoco(Character codEEnteBenefIdUnivBenefTipoIdUnivoco) {
		this.codEEnteBenefIdUnivBenefTipoIdUnivoco = codEEnteBenefIdUnivBenefTipoIdUnivoco;
	}

	@Column(name = "cod_e_ente_benef_id_univ_benef_codice_id_univoco", length = 35)
	public String getCodEEnteBenefIdUnivBenefCodiceIdUnivoco() {
		return this.codEEnteBenefIdUnivBenefCodiceIdUnivoco;
	}

	public void setCodEEnteBenefIdUnivBenefCodiceIdUnivoco(String codEEnteBenefIdUnivBenefCodiceIdUnivoco) {
		this.codEEnteBenefIdUnivBenefCodiceIdUnivoco = codEEnteBenefIdUnivBenefCodiceIdUnivoco;
	}

	@Column(name = "de_e_ente_benef_denominazione_beneficiario", length = 70)
	public String getDeEEnteBenefDenominazioneBeneficiario() {
		return this.deEEnteBenefDenominazioneBeneficiario;
	}

	public void setDeEEnteBenefDenominazioneBeneficiario(String deEEnteBenefDenominazioneBeneficiario) {
		this.deEEnteBenefDenominazioneBeneficiario = deEEnteBenefDenominazioneBeneficiario;
	}

	@Column(name = "cod_e_ente_benef_codice_unit_oper_beneficiario", length = 35)
	public String getCodEEnteBenefCodiceUnitOperBeneficiario() {
		return this.codEEnteBenefCodiceUnitOperBeneficiario;
	}

	public void setCodEEnteBenefCodiceUnitOperBeneficiario(String codEEnteBenefCodiceUnitOperBeneficiario) {
		this.codEEnteBenefCodiceUnitOperBeneficiario = codEEnteBenefCodiceUnitOperBeneficiario;
	}

	@Column(name = "de_e_ente_benef_denom_unit_oper_beneficiario", length = 70)
	public String getDeEEnteBenefDenomUnitOperBeneficiario() {
		return this.deEEnteBenefDenomUnitOperBeneficiario;
	}

	public void setDeEEnteBenefDenomUnitOperBeneficiario(String deEEnteBenefDenomUnitOperBeneficiario) {
		this.deEEnteBenefDenomUnitOperBeneficiario = deEEnteBenefDenomUnitOperBeneficiario;
	}

	@Column(name = "de_e_ente_benef_indirizzo_beneficiario", length = 70)
	public String getDeEEnteBenefIndirizzoBeneficiario() {
		return this.deEEnteBenefIndirizzoBeneficiario;
	}

	public void setDeEEnteBenefIndirizzoBeneficiario(String deEEnteBenefIndirizzoBeneficiario) {
		this.deEEnteBenefIndirizzoBeneficiario = deEEnteBenefIndirizzoBeneficiario;
	}

	@Column(name = "de_e_ente_benef_civico_beneficiario", length = 16)
	public String getDeEEnteBenefCivicoBeneficiario() {
		return this.deEEnteBenefCivicoBeneficiario;
	}

	public void setDeEEnteBenefCivicoBeneficiario(String deEEnteBenefCivicoBeneficiario) {
		this.deEEnteBenefCivicoBeneficiario = deEEnteBenefCivicoBeneficiario;
	}

	@Column(name = "cod_e_ente_benef_cap_beneficiario", length = 16)
	public String getCodEEnteBenefCapBeneficiario() {
		return this.codEEnteBenefCapBeneficiario;
	}

	public void setCodEEnteBenefCapBeneficiario(String codEEnteBenefCapBeneficiario) {
		this.codEEnteBenefCapBeneficiario = codEEnteBenefCapBeneficiario;
	}

	@Column(name = "de_e_ente_benef_localita_beneficiario", length = 35)
	public String getDeEEnteBenefLocalitaBeneficiario() {
		return this.deEEnteBenefLocalitaBeneficiario;
	}

	public void setDeEEnteBenefLocalitaBeneficiario(String deEEnteBenefLocalitaBeneficiario) {
		this.deEEnteBenefLocalitaBeneficiario = deEEnteBenefLocalitaBeneficiario;
	}

	@Column(name = "de_e_ente_benef_provincia_beneficiario", length = 35)
	public String getDeEEnteBenefProvinciaBeneficiario() {
		return this.deEEnteBenefProvinciaBeneficiario;
	}

	public void setDeEEnteBenefProvinciaBeneficiario(String deEEnteBenefProvinciaBeneficiario) {
		this.deEEnteBenefProvinciaBeneficiario = deEEnteBenefProvinciaBeneficiario;
	}

	@Column(name = "cod_e_ente_benef_nazione_beneficiario", length = 2)
	public String getCodEEnteBenefNazioneBeneficiario() {
		return this.codEEnteBenefNazioneBeneficiario;
	}

	public void setCodEEnteBenefNazioneBeneficiario(String codEEnteBenefNazioneBeneficiario) {
		this.codEEnteBenefNazioneBeneficiario = codEEnteBenefNazioneBeneficiario;
	}

	@Column(name = "cod_e_sogg_vers_id_univ_vers_tipo_id_univoco", length = 1)
	public Character getCodESoggVersIdUnivVersTipoIdUnivoco() {
		return this.codESoggVersIdUnivVersTipoIdUnivoco;
	}

	public void setCodESoggVersIdUnivVersTipoIdUnivoco(Character codESoggVersIdUnivVersTipoIdUnivoco) {
		this.codESoggVersIdUnivVersTipoIdUnivoco = codESoggVersIdUnivVersTipoIdUnivoco;
	}

	@Column(name = "cod_e_sogg_vers_id_univ_vers_codice_id_univoco", length = 35)
	public String getCodESoggVersIdUnivVersCodiceIdUnivoco() {
		return this.codESoggVersIdUnivVersCodiceIdUnivoco;
	}

	public void setCodESoggVersIdUnivVersCodiceIdUnivoco(String codESoggVersIdUnivVersCodiceIdUnivoco) {
		this.codESoggVersIdUnivVersCodiceIdUnivoco = codESoggVersIdUnivVersCodiceIdUnivoco;
	}

	@Column(name = "cod_e_sogg_vers_anagrafica_versante", length = 70)
	public String getCodESoggVersAnagraficaVersante() {
		return this.codESoggVersAnagraficaVersante;
	}

	public void setCodESoggVersAnagraficaVersante(String codESoggVersAnagraficaVersante) {
		this.codESoggVersAnagraficaVersante = codESoggVersAnagraficaVersante;
	}

	@Column(name = "de_e_sogg_vers_indirizzo_versante", length = 70)
	public String getDeESoggVersIndirizzoVersante() {
		return this.deESoggVersIndirizzoVersante;
	}

	public void setDeESoggVersIndirizzoVersante(String deESoggVersIndirizzoVersante) {
		this.deESoggVersIndirizzoVersante = deESoggVersIndirizzoVersante;
	}

	@Column(name = "de_e_sogg_vers_civico_versante", length = 16)
	public String getDeESoggVersCivicoVersante() {
		return this.deESoggVersCivicoVersante;
	}

	public void setDeESoggVersCivicoVersante(String deESoggVersCivicoVersante) {
		this.deESoggVersCivicoVersante = deESoggVersCivicoVersante;
	}

	@Column(name = "cod_e_sogg_vers_cap_versante", length = 16)
	public String getCodESoggVersCapVersante() {
		return this.codESoggVersCapVersante;
	}

	public void setCodESoggVersCapVersante(String codESoggVersCapVersante) {
		this.codESoggVersCapVersante = codESoggVersCapVersante;
	}

	@Column(name = "de_e_sogg_vers_localita_versante", length = 35)
	public String getDeESoggVersLocalitaVersante() {
		return this.deESoggVersLocalitaVersante;
	}

	public void setDeESoggVersLocalitaVersante(String deESoggVersLocalitaVersante) {
		this.deESoggVersLocalitaVersante = deESoggVersLocalitaVersante;
	}

	@Column(name = "de_e_sogg_vers_provincia_versante", length = 35)
	public String getDeESoggVersProvinciaVersante() {
		return this.deESoggVersProvinciaVersante;
	}

	public void setDeESoggVersProvinciaVersante(String deESoggVersProvinciaVersante) {
		this.deESoggVersProvinciaVersante = deESoggVersProvinciaVersante;
	}

	@Column(name = "cod_e_sogg_vers_nazione_versante", length = 2)
	public String getCodESoggVersNazioneVersante() {
		return this.codESoggVersNazioneVersante;
	}

	public void setCodESoggVersNazioneVersante(String codESoggVersNazioneVersante) {
		this.codESoggVersNazioneVersante = codESoggVersNazioneVersante;
	}

	@Column(name = "de_e_sogg_vers_email_versante", length = 256)
	public String getDeESoggVersEmailVersante() {
		return this.deESoggVersEmailVersante;
	}

	public void setDeESoggVersEmailVersante(String deESoggVersEmailVersante) {
		this.deESoggVersEmailVersante = deESoggVersEmailVersante;
	}

	@Column(name = "cod_e_sogg_pag_id_univ_pag_tipo_id_univoco", length = 1)
	public Character getCodESoggPagIdUnivPagTipoIdUnivoco() {
		return this.codESoggPagIdUnivPagTipoIdUnivoco;
	}

	public void setCodESoggPagIdUnivPagTipoIdUnivoco(Character codESoggPagIdUnivPagTipoIdUnivoco) {
		this.codESoggPagIdUnivPagTipoIdUnivoco = codESoggPagIdUnivPagTipoIdUnivoco;
	}

	@Column(name = "cod_e_sogg_pag_id_univ_pag_codice_id_univoco", length = 35)
	public String getCodESoggPagIdUnivPagCodiceIdUnivoco() {
		return this.codESoggPagIdUnivPagCodiceIdUnivoco;
	}

	public void setCodESoggPagIdUnivPagCodiceIdUnivoco(String codESoggPagIdUnivPagCodiceIdUnivoco) {
		this.codESoggPagIdUnivPagCodiceIdUnivoco = codESoggPagIdUnivPagCodiceIdUnivoco;
	}

	@Column(name = "cod_e_sogg_pag_anagrafica_pagatore", length = 70)
	public String getCodESoggPagAnagraficaPagatore() {
		return this.codESoggPagAnagraficaPagatore;
	}

	public void setCodESoggPagAnagraficaPagatore(String codESoggPagAnagraficaPagatore) {
		this.codESoggPagAnagraficaPagatore = codESoggPagAnagraficaPagatore;
	}

	@Column(name = "de_e_sogg_pag_indirizzo_pagatore", length = 70)
	public String getDeESoggPagIndirizzoPagatore() {
		return this.deESoggPagIndirizzoPagatore;
	}

	public void setDeESoggPagIndirizzoPagatore(String deESoggPagIndirizzoPagatore) {
		this.deESoggPagIndirizzoPagatore = deESoggPagIndirizzoPagatore;
	}

	@Column(name = "de_e_sogg_pag_civico_pagatore", length = 16)
	public String getDeESoggPagCivicoPagatore() {
		return this.deESoggPagCivicoPagatore;
	}

	public void setDeESoggPagCivicoPagatore(String deESoggPagCivicoPagatore) {
		this.deESoggPagCivicoPagatore = deESoggPagCivicoPagatore;
	}

	@Column(name = "cod_e_sogg_pag_cap_pagatore", length = 16)
	public String getCodESoggPagCapPagatore() {
		return this.codESoggPagCapPagatore;
	}

	public void setCodESoggPagCapPagatore(String codESoggPagCapPagatore) {
		this.codESoggPagCapPagatore = codESoggPagCapPagatore;
	}

	@Column(name = "de_e_sogg_pag_localita_pagatore", length = 35)
	public String getDeESoggPagLocalitaPagatore() {
		return this.deESoggPagLocalitaPagatore;
	}

	public void setDeESoggPagLocalitaPagatore(String deESoggPagLocalitaPagatore) {
		this.deESoggPagLocalitaPagatore = deESoggPagLocalitaPagatore;
	}

	@Column(name = "de_e_sogg_pag_provincia_pagatore", length = 35)
	public String getDeESoggPagProvinciaPagatore() {
		return this.deESoggPagProvinciaPagatore;
	}

	public void setDeESoggPagProvinciaPagatore(String deESoggPagProvinciaPagatore) {
		this.deESoggPagProvinciaPagatore = deESoggPagProvinciaPagatore;
	}

	@Column(name = "cod_e_sogg_pag_nazione_pagatore", length = 2)
	public String getCodESoggPagNazionePagatore() {
		return this.codESoggPagNazionePagatore;
	}

	public void setCodESoggPagNazionePagatore(String codESoggPagNazionePagatore) {
		this.codESoggPagNazionePagatore = codESoggPagNazionePagatore;
	}

	@Column(name = "de_e_sogg_pag_email_pagatore", length = 256)
	public String getDeESoggPagEmailPagatore() {
		return this.deESoggPagEmailPagatore;
	}

	public void setDeESoggPagEmailPagatore(String deESoggPagEmailPagatore) {
		this.deESoggPagEmailPagatore = deESoggPagEmailPagatore;
	}

	@Column(name = "cod_e_dati_pag_codice_esito_pagamento", length = 1)
	public Character getCodEDatiPagCodiceEsitoPagamento() {
		return this.codEDatiPagCodiceEsitoPagamento;
	}

	public void setCodEDatiPagCodiceEsitoPagamento(Character codEDatiPagCodiceEsitoPagamento) {
		this.codEDatiPagCodiceEsitoPagamento = codEDatiPagCodiceEsitoPagamento;
	}

	@Column(name = "num_e_dati_pag_importo_totale_pagato", precision = 12)
	public BigDecimal getNumEDatiPagImportoTotalePagato() {
		return this.numEDatiPagImportoTotalePagato;
	}

	public void setNumEDatiPagImportoTotalePagato(BigDecimal numEDatiPagImportoTotalePagato) {
		this.numEDatiPagImportoTotalePagato = numEDatiPagImportoTotalePagato;
	}

	@Column(name = "cod_e_dati_pag_id_univoco_versamento", length = 35)
	public String getCodEDatiPagIdUnivocoVersamento() {
		return this.codEDatiPagIdUnivocoVersamento;
	}

	public void setCodEDatiPagIdUnivocoVersamento(String codEDatiPagIdUnivocoVersamento) {
		this.codEDatiPagIdUnivocoVersamento = codEDatiPagIdUnivocoVersamento;
	}

	@Column(name = "cod_e_dati_pag_codice_contesto_pagamento", length = 35)
	public String getCodEDatiPagCodiceContestoPagamento() {
		return this.codEDatiPagCodiceContestoPagamento;
	}

	public void setCodEDatiPagCodiceContestoPagamento(String codEDatiPagCodiceContestoPagamento) {
		this.codEDatiPagCodiceContestoPagamento = codEDatiPagCodiceContestoPagamento;
	}

	@Column(name = "num_e_dati_pag_dati_sing_pag_singolo_importo_pagato", precision = 12)
	public BigDecimal getNumEDatiPagDatiSingPagSingoloImportoPagato() {
		return this.numEDatiPagDatiSingPagSingoloImportoPagato;
	}

	public void setNumEDatiPagDatiSingPagSingoloImportoPagato(BigDecimal numEDatiPagDatiSingPagSingoloImportoPagato) {
		this.numEDatiPagDatiSingPagSingoloImportoPagato = numEDatiPagDatiSingPagSingoloImportoPagato;
	}

	@Column(name = "de_e_dati_pag_dati_sing_pag_esito_singolo_pagamento", length = 35)
	public String getDeEDatiPagDatiSingPagEsitoSingoloPagamento() {
		return this.deEDatiPagDatiSingPagEsitoSingoloPagamento;
	}

	public void setDeEDatiPagDatiSingPagEsitoSingoloPagamento(String deEDatiPagDatiSingPagEsitoSingoloPagamento) {
		this.deEDatiPagDatiSingPagEsitoSingoloPagamento = deEDatiPagDatiSingPagEsitoSingoloPagamento;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento", length = 13)
	public Date getDtEDatiPagDatiSingPagDataEsitoSingoloPagamento() {
		return this.dtEDatiPagDatiSingPagDataEsitoSingoloPagamento;
	}

	public void setDtEDatiPagDatiSingPagDataEsitoSingoloPagamento(Date dtEDatiPagDatiSingPagDataEsitoSingoloPagamento) {
		this.dtEDatiPagDatiSingPagDataEsitoSingoloPagamento = dtEDatiPagDatiSingPagDataEsitoSingoloPagamento;
	}

	@Column(name = "de_e_dati_pag_dati_sing_pag_causale_versamento", length = 1024)
	public String getDeEDatiPagDatiSingPagCausaleVersamento() {
		return this.deEDatiPagDatiSingPagCausaleVersamento;
	}

	public void setDeEDatiPagDatiSingPagCausaleVersamento(String deEDatiPagDatiSingPagCausaleVersamento) {
		this.deEDatiPagDatiSingPagCausaleVersamento = deEDatiPagDatiSingPagCausaleVersamento;
	}

	@Column(name = "de_e_dati_pag_dati_sing_pag_dati_specifici_riscossione", length = 140)
	public String getDeEDatiPagDatiSingPagDatiSpecificiRiscossione() {
		return this.deEDatiPagDatiSingPagDatiSpecificiRiscossione;
	}

	public void setDeEDatiPagDatiSingPagDatiSpecificiRiscossione(String deEDatiPagDatiSingPagDatiSpecificiRiscossione) {
		this.deEDatiPagDatiSingPagDatiSpecificiRiscossione = deEDatiPagDatiSingPagDatiSpecificiRiscossione;
	}

	@Column(name = "cod_tipo_dovuto", length = 64)
	public String getCodTipoDovuto() {
		return this.codTipoDovuto;
	}

	public void setCodTipoDovuto(String codTipoDovuto) {
		this.codTipoDovuto = codTipoDovuto;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_acquisizione", nullable = false, length = 13)
	public Date getDtAcquisizione() {
		return this.dtAcquisizione;
	}

	public void setDtAcquisizione(Date dtAcquisizione) {
		this.dtAcquisizione = dtAcquisizione;
	}

	@Column(name = "de_importa_dovuto_esito")
	public String getDeImportaDovutoEsito() {
		return this.deImportaDovutoEsito;
	}

	public void setDeImportaDovutoEsito(String deImportaDovutoEsito) {
		this.deImportaDovutoEsito = deImportaDovutoEsito;
	}

	@Column(name = "de_importa_dovuto_fault_code")
	public String getDeImportaDovutoFaultCode() {
		return this.deImportaDovutoFaultCode;
	}

	public void setDeImportaDovutoFaultCode(String deImportaDovutoFaultCode) {
		this.deImportaDovutoFaultCode = deImportaDovutoFaultCode;
	}

	@Column(name = "de_importa_dovuto_fault_string")
	public String getDeImportaDovutoFaultString() {
		return this.deImportaDovutoFaultString;
	}

	public void setDeImportaDovutoFaultString(String deImportaDovutoFaultString) {
		this.deImportaDovutoFaultString = deImportaDovutoFaultString;
	}

	@Column(name = "de_importa_dovuto_fault_id")
	public String getDeImportaDovutoFaultId() {
		return this.deImportaDovutoFaultId;
	}

	public void setDeImportaDovutoFaultId(String deImportaDovutoFaultId) {
		this.deImportaDovutoFaultId = deImportaDovutoFaultId;
	}

	@Column(name = "de_importa_dovuto_fault_description")
	public String getDeImportaDovutoFaultDescription() {
		return this.deImportaDovutoFaultDescription;
	}

	public void setDeImportaDovutoFaultDescription(String deImportaDovutoFaultDescription) {
		this.deImportaDovutoFaultDescription = deImportaDovutoFaultDescription;
	}

	@Column(name = "num_importa_dovuto_fault_serial")
	public Integer getNumImportaDovutoFaultSerial() {
		return this.numImportaDovutoFaultSerial;
	}

	public void setNumImportaDovutoFaultSerial(Integer numImportaDovutoFaultSerial) {
		this.numImportaDovutoFaultSerial = numImportaDovutoFaultSerial;
	}

	@Column(name = "bilancio", length = 4096)
	public String getBilancio() {
		return this.bilancio;
	}

	public void setBilancio(String bilancio) {
		this.bilancio = bilancio;
	}

}
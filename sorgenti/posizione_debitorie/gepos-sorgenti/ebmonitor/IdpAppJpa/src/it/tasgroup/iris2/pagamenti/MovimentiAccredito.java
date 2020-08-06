package it.tasgroup.iris2.pagamenti;

import it.tasgroup.idp.domain.BaseIdEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="MOVIMENTI_ACCREDITO")
@NamedQueries ({
	@NamedQuery (
		  name="findMovimentiAccreditoToUpdateByElaborazioniAndTsInserimento" 
		, query="SELECT m FROM MovimentiAccredito m WHERE m.flagRiconciliazione in (0,3) AND m.numElaborazioni >= :elaborazioni AND m.tsInserimento < :tsLimite"
	)
	,
	@NamedQuery (
		  name="findMovimentiAccreditoToProcess" 
		, query="SELECT m FROM MovimentiAccredito m WHERE m.flagRiconciliazione in (0,3)"
	),
	@NamedQuery (
			  name="findMovimentiAccreditoByIUVAndSIA" 
			, query="SELECT m FROM MovimentiAccredito m WHERE m.idRiconciliazione=:iuv and m.sia=:sia order by ts_inserimento asc"
		)

})
public class MovimentiAccredito extends BaseIdEntity implements Serializable {
	private static final long serialVersionUID = -8551557942879057468L;

	public static enum TipoAccredito {S,C,N}  // S=Singolo, C=Cumulativo, A=Anomalo
	public static enum InfoRiconciliazione {SINGOLO, CHECK_DIGIT, REFERENCE, IMPORTO, TIPO, ID_FLUSSO}
	public static enum InfoRiconciliazioneTipo {RFS,RFB,PUR}
	
	/*** Persistent Values ***/
	
	private String sia;
	private String cuc;
	private String iban;
	private String tipo;
	private String idRiconciliazione;
	private short flagRiconciliazione;
	private String tipoMovimento;
	private String idMovimento;
	private Timestamp dataValutaBeneficiario;
	private Timestamp dataContabile;
	private String segno;
	private BigDecimal importo;
	private BigDecimal importoBollo;
	private String causale;
	private String riferimentoBeneficiario;
	private String riferimentoBanca;
	private String trn;
	private String idFiscaleDebitore;
	private Rendicontazioni rendicontazioni;
	private String idRiconciliazioneOrig;
	private String codAnomalia;
	private short numElaborazioni;
	private String infoRiconciliazione;
	private String idBancaOrdinante;

	private String riferimentoContabile;
	private Timestamp dataRegolamento;
	private String nomeDebitore;
	private String idFiscaleCreditore;
	
	/* Transient Values */
	private String contoEvidenza;
	private String codiceEnteTesoreria;
	private String codiceBancaBT;
	
	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="movimenti_accredito_pk_gen")	
	@SequenceGenerator(
	    name="movimenti_accredito_pk_gen",
	    sequenceName="MOVIMENTI_ACCREDITO_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}	
	
	
	/*** Constructor ***/
	
	public MovimentiAccredito() {
		this.numElaborazioni = 0;
	}
	
	/*** Properties ***/
	
	@Column(name="COD_SIA")
	public String getSia() {
		return sia;
	}
	
	public void setSia(String sia) {
		this.sia = sia;
	}

	@Column(name="COD_CUC")
	public String getCuc() {
		return cuc;
	}
	
	public void setCuc(String cuc) {
		this.cuc = cuc;
	}
	
	@Column(name="IBAN_ACCREDITO")
	public String getIban() {
		return iban;
	}
	
	public void setIban(String iban) {
		this.iban = iban;
	}
	
	@Column(name="TIPO_ACCREDITO")
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Column(name="ID_RICONCILIAZIONE")
	public String getIdRiconciliazione() {
		return idRiconciliazione;
	}
	
	public void setIdRiconciliazione(String idRiconciliazione) {
		this.idRiconciliazione = idRiconciliazione;
	}
	
	@Column(name="FLAG_RICONCILIAZIONE")
	public short getFlagRiconciliazione() {
		return flagRiconciliazione;
	}
	
	public void setFlagRiconciliazione(short flagRiconciliazione) {
		this.flagRiconciliazione = flagRiconciliazione;
	}
	
	@Column(name="TIPO_MOVIMENTO")
	public String getTipoMovimento() {
		return tipoMovimento;
	}
	
	public void setTipoMovimento(String tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	
	@Column(name="ID_MOVIMENTO")
	public String getIdMovimento() {
		return idMovimento;
	}
	
	public void setIdMovimento(String idMovimento) {
		this.idMovimento = idMovimento;
	}
	
	@Column(name="DATA_VALUTA_BENEFICIARIO")
	public Timestamp getDataValutaBeneficiario() {
		return dataValutaBeneficiario;
	}
	
	public void setDataValutaBeneficiario(Timestamp dataValutaBeneficiario) {
		this.dataValutaBeneficiario = dataValutaBeneficiario;
	}
	
	@Column(name="DATA_CONTABILE")
	public Timestamp getDataContabile() {
		return dataContabile;
	}
	
	public void setDataContabile(Timestamp dataContabile) {
		this.dataContabile = dataContabile;
	}
	
	@Column(name="SEGNO")
	public String getSegno() {
		return segno;
	}
	
	public void setSegno(String segno) {
		this.segno = segno;
	}
	
	@Column(name="IMPORTO")
	public BigDecimal getImporto() {
		return importo;
	}
	
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	
	@Column(name="IMPORTO_BOLLO")
	public BigDecimal getImportoBollo() {
		return importoBollo;
	}
	
	public void setImportoBollo(BigDecimal importoBollo) {
		this.importoBollo = importoBollo;
	}
	
	@Column(name="CAUSALE")
	public String getCausale() {
		return causale;
	}
	
	public void setCausale(String causale) {
		this.causale = causale;
	}
	
	@Column(name="RIFERIMENTO_BENEFICIARIO")
	public String getRiferimentoBeneficiario() {
		return riferimentoBeneficiario;
	}
	
	public void setRiferimentoBeneficiario(String riferimentoBeneficiario) {
		this.riferimentoBeneficiario = riferimentoBeneficiario;
	}
	
	@Column(name="RIFERIMENTO_BANCA")
	public String getRiferimentoBanca() {
		return riferimentoBanca;
	}
	
	public void setRiferimentoBanca(String riferimentoBanca) {
		this.riferimentoBanca = riferimentoBanca;
	}
	
	@Column(name="TRN")
	public String getTrn() {
		return trn;
	}
	
	public void setTrn(String trn) {
		this.trn = trn;
	}
	
	@Column(name="ID_FISCALE_DEBITORE")
	public String getIdFiscaleDebitore() {
		return idFiscaleDebitore;
	}
	
	public void setIdFiscaleDebitore(String idFiscaleDebitore) {
		this.idFiscaleDebitore = idFiscaleDebitore;
	}
	
	@ManyToOne
	@JoinColumn(name="ID_RENDICONTAZIONE")
	public Rendicontazioni getRendicontazioni() {
		return rendicontazioni;
	}
	
	public void setRendicontazioni(Rendicontazioni rendicontazioni) {
		this.rendicontazioni = rendicontazioni;
	}
	
	@Column(name="ID_RICONCILIAZIONE_ORIG")
	public String getIdRiconciliazioneOrig() {
		return idRiconciliazioneOrig;
	}
	
	public void setIdRiconciliazioneOrig(String idRiconciliazioneOrig) {
		this.idRiconciliazioneOrig = idRiconciliazioneOrig;
	}
	
	@Column(name="COD_ANOMALIA")
	public String getCodAnomalia() {
		return codAnomalia;
	}
	
	public void setCodAnomalia(String codAnomalia) {
		this.codAnomalia = codAnomalia;
	}
	
	@Column(name="NUM_ELABORAZIONI")
	public short getNumElaborazioni() {
		return numElaborazioni;
	}
	
	public void setNumElaborazioni(short numElaborazioni) {
		this.numElaborazioni = numElaborazioni;
	}
	
	@Column(name="INFO_RICONCILIAZIONE")
	public String getInfoRiconciliazione() {
		return infoRiconciliazione;
	}
	
	public void setInfoRiconciliazione(String infoRiconciliazione) {
		this.infoRiconciliazione = infoRiconciliazione;
	}

	@Column(name="ID_BANCA_ORDINANTE")
	public String getIdBancaOrdinante() {
		return idBancaOrdinante;
	}


	public void setIdBancaOrdinante(String idBancaOrdinante) {
		this.idBancaOrdinante = idBancaOrdinante;
	}

	
	
	
	@Column(name="RIFERIMENTO_CONTABILE")
	public String getRiferimentoContabile() {
		return riferimentoContabile;
	}

	public void setRiferimentoContabile(String riferimentoContabile) {
		this.riferimentoContabile = riferimentoContabile;
	}
	
	@Column(name="DATA_REGOLAMENTO")
	public Timestamp getDataRegolamento() {
		return dataRegolamento;
	}

	public void setDataRegolamento(Timestamp dataRegolamento) {
		this.dataRegolamento = dataRegolamento;
	}
	
	@Column(name="NOME_DEBITORE")
	public String getNomeDebitore() {
		return nomeDebitore;
	}

	public void setNomeDebitore(String nomeDebitore) {
		this.nomeDebitore = nomeDebitore;
	}
	
	@Column(name="ID_FISCALE_CREDITORE")
	public String getIdFiscaleCreditore() {
		return idFiscaleCreditore;
	}
	
	public void setIdFiscaleCreditore(String idFiscaleCreditore) {
		this.idFiscaleCreditore = idFiscaleCreditore;
	}
	
	@Transient
	public String getContoEvidenza() {
		return contoEvidenza;
	}

	public void setContoEvidenza(String contoEvidenza) {
		this.contoEvidenza = contoEvidenza;
	}

	@Transient
	public String getCodiceEnteTesoreria() {
		return codiceEnteTesoreria;
	}


	public void setCodiceEnteTesoreria(String codiceEnteTesoreria) {
		this.codiceEnteTesoreria = codiceEnteTesoreria;
	}

	@Transient
	public String getCodiceBancaBT() {
		return codiceBancaBT;
	}

	public void setCodiceBancaBT(String codiceBancaBT) {
		this.codiceBancaBT = codiceBancaBT;
	}

	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MovimentiAccredito [sia=");
		builder.append(sia);
		builder.append(", cuc=");
		builder.append(cuc);
		builder.append(", iban=");
		builder.append(iban);
		builder.append(", tipo=");
		builder.append(tipo);
		builder.append(", idRiconciliazione=");
		builder.append(idRiconciliazione);
		builder.append(", flagRiconciliazione=");
		builder.append(flagRiconciliazione);
		builder.append(", tipoMovimento=");
		builder.append(tipoMovimento);
		builder.append(", idMovimento=");
		builder.append(idMovimento);
		builder.append(", dataValutaBeneficiario=");
		builder.append(dataValutaBeneficiario);
		builder.append(", dataContabile=");
		builder.append(dataContabile);
		builder.append(", segno=");
		builder.append(segno);
		builder.append(", importo=");
		builder.append(importo);
		builder.append(", importoBollo=");
		builder.append(importoBollo);
		builder.append(", causale=");
		builder.append(causale);
		builder.append(", riferimentoBeneficiario=");
		builder.append(riferimentoBeneficiario);
		builder.append(", riferimentoBanca=");
		builder.append(riferimentoBanca);
		builder.append(", trn=");
		builder.append(trn);
		builder.append(", idFiscaleDebitore=");
		builder.append(idFiscaleDebitore);
		builder.append(", rendicontazioni=");
		builder.append(rendicontazioni);
		builder.append(", idRiconciliazioneOrig=");
		builder.append(idRiconciliazioneOrig);
		builder.append(", codAnomalia=");
		builder.append(codAnomalia);
		builder.append(", numElaborazioni=");
		builder.append(numElaborazioni);
		builder.append(", infoRiconciliazione=");
		builder.append(infoRiconciliazione);
		builder.append(", idBancaOrdinante=");
		builder.append(idBancaOrdinante);
		return builder.toString();
	}
}

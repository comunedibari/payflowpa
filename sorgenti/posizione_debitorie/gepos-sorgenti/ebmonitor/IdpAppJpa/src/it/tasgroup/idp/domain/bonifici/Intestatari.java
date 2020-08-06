package it.tasgroup.idp.domain.bonifici;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.tasgroup.idp.anagrafica.Indirizzipostali;

@NamedQueries({ 
	@NamedQuery(name = "IntestatarioBySia", query = "select intestatari "
			+ "from Intestatari intestatari where intestatari.sia = :sia "),
	@NamedQuery(name = "IntestatarioBySiaCBI", query = "select intestatari "
			+ "from Intestatari intestatari where intestatari.siaCBI = :siaCBI "),
	@NamedQuery(name = "IntestatarioByLapl", query = "select intestatari from Intestatari intestatari where intestatari.lapl = :lapl "),
	@NamedQuery(name = "IntestatarioByLaplAndCF", query = "select intestatari from Intestatari intestatari where intestatari.lapl = :lapl and intestatari.indirizzipostali.fiscalCode=:fiscalCode"),
	@NamedQuery(name = "IntestatarioByFlagEnrollmentAvvisatura", query = "select intestatari from Intestatari intestatari where intestatari.flagEnrollmentAvvisatura = :flagEnrollmentAvvisatura "),
	@NamedQuery(name = "findAllIdDominio", query = "select intestatari.lapl from Intestatari intestatari, Enti enti "
			+ "where intestatari.intestatario = enti.intestatario and enti.stato = 'A' ")
})
/**
 * The persistent class for the JLTE037 database table.
 *
 */
@Entity
@Table(name="INTESTATARI")
public class Intestatari implements Serializable {
	private static final long serialVersionUID = 1L;


	private String intestatario;

	private String abi;

	private String abiaccentratore;

	private String aziendamigrata;

	private String bollovirtuale;

	private String cab;

	private String categoria;

	private String chiavessb;

	private String codicecuc;

	private String codicepostel;

	private String codicesoftware;

	private String denominazione;

	private String email;

	private String funzioniabilitate;

	private String gruppo;

	private BigDecimal importomaxflusso;

	private String issr;

	private String lapl;

	private String nonresidente;

	
	private String opAggiornamento;

	
	private String opInserimento;

	
	private Integer prVersione;

	private String ragionesociale;

	private String rapl;

	private String rcz;

	private Short recordlock;

	private String sia;
	
	private String siaCBI;

	private String sottocategoria;

	private Integer stato;

	private String tipointestatario;

	private String tiposicurezza;

	
	private Timestamp tmbPrimaAtt;

	
	private Timestamp tmbUltimaAtt;

	
	private Timestamp tsAggiornamento;

	
	private Timestamp tsInserimento;

	private String uffpostale;

	private String flagEnrollmentAvvisatura;
	
	private Indirizzipostali indirizzipostali;

    public Intestatari() {
    }
	
    @Id
	public String getIntestatario() {
		return this.intestatario;
	}

	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}

	public String getAbi() {
		return this.abi;
	}

	public void setAbi(String abi) {
		this.abi = abi;
	}

	public String getAbiaccentratore() {
		return this.abiaccentratore;
	}

	public void setAbiaccentratore(String abiaccentratore) {
		this.abiaccentratore = abiaccentratore;
	}

	public String getAziendamigrata() {
		return this.aziendamigrata;
	}

	public void setAziendamigrata(String aziendamigrata) {
		this.aziendamigrata = aziendamigrata;
	}

	public String getBollovirtuale() {
		return this.bollovirtuale;
	}

	public void setBollovirtuale(String bollovirtuale) {
		this.bollovirtuale = bollovirtuale;
	}

	public String getCab() {
		return this.cab;
	}

	public void setCab(String cab) {
		this.cab = cab;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getChiavessb() {
		return this.chiavessb;
	}

	public void setChiavessb(String chiavessb) {
		this.chiavessb = chiavessb;
	}

	public String getCodicecuc() {
		return this.codicecuc;
	}

	public void setCodicecuc(String codicecuc) {
		this.codicecuc = codicecuc;
	}

	public String getCodicepostel() {
		return this.codicepostel;
	}

	public void setCodicepostel(String codicepostel) {
		this.codicepostel = codicepostel;
	}

	public String getCodicesoftware() {
		return this.codicesoftware;
	}

	public void setCodicesoftware(String codicesoftware) {
		this.codicesoftware = codicesoftware;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFunzioniabilitate() {
		return this.funzioniabilitate;
	}

	public void setFunzioniabilitate(String funzioniabilitate) {
		this.funzioniabilitate = funzioniabilitate;
	}

	public String getGruppo() {
		return this.gruppo;
	}

	public void setGruppo(String gruppo) {
		this.gruppo = gruppo;
	}

	public BigDecimal getImportomaxflusso() {
		return this.importomaxflusso;
	}

	public void setImportomaxflusso(BigDecimal importomaxflusso) {
		this.importomaxflusso = importomaxflusso;
	}

	public String getIssr() {
		return this.issr;
	}

	public void setIssr(String issr) {
		this.issr = issr;
	}

	public String getLapl() {
		return this.lapl;
	}

	public void setLapl(String lapl) {
		this.lapl = lapl;
	}

	public String getNonresidente() {
		return this.nonresidente;
	}

	public void setNonresidente(String nonresidente) {
		this.nonresidente = nonresidente;
	}
    
	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}
	
	@Column(name="PR_VERSIONE")
	public Integer getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(Integer prVersione) {
		this.prVersione = prVersione;
	}

	public String getRagionesociale() {
		return this.ragionesociale;
	}

	public void setRagionesociale(String ragionesociale) {
		this.ragionesociale = ragionesociale;
	}

	public String getRapl() {
		return this.rapl;
	}

	public void setRapl(String rapl) {
		this.rapl = rapl;
	}

	public String getRcz() {
		return this.rcz;
	}

	public void setRcz(String rcz) {
		this.rcz = rcz;
	}

	public Short getRecordlock() {
		return this.recordlock;
	}

	public void setRecordlock(Short recordlock) {
		this.recordlock = recordlock;
	}

	public String getSia() {
		return this.sia;
	}

	public void setSia(String sia) {
		this.sia = sia;
	}
	
	@Column(name="SIA_CBI")
	public String getSiaCBI() {
		return this.siaCBI;
	}

	public void setSiaCBI(String sia) {
		this.siaCBI = sia;
	}

	public String getSottocategoria() {
		return this.sottocategoria;
	}

	public void setSottocategoria(String sottocategoria) {
		this.sottocategoria = sottocategoria;
	}

	public Integer getStato() {
		return this.stato;
	}

	public void setStato(Integer stato) {
		this.stato = stato;
	}

	public String getTipointestatario() {
		return this.tipointestatario;
	}

	public void setTipointestatario(String tipointestatario) {
		this.tipointestatario = tipointestatario;
	}

	public String getTiposicurezza() {
		return this.tiposicurezza;
	}

	public void setTiposicurezza(String tiposicurezza) {
		this.tiposicurezza = tiposicurezza;
	}

	@Column(name="TMB_PRIMA_ATT")
	public Timestamp getTmbPrimaAtt() {
		return this.tmbPrimaAtt;
	}

	public void setTmbPrimaAtt(Timestamp tmbPrimaAtt) {
		this.tmbPrimaAtt = tmbPrimaAtt;
	}

	@Column(name="TMB_ULTIMA_ATT")
	public Timestamp getTmbUltimaAtt() {
		return this.tmbUltimaAtt;
	}

	public void setTmbUltimaAtt(Timestamp tmbUltimaAtt) {
		this.tmbUltimaAtt = tmbUltimaAtt;
	}

	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}
	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	public String getUffpostale() {
		return this.uffpostale;
	}

	public void setUffpostale(String uffpostale) {
		this.uffpostale = uffpostale;
	}
    
	@Column(name="FLAG_ENROLLMENT_AVVISATURA")
	public String getFlagEnrollmentAvvisatura() {
		return flagEnrollmentAvvisatura;
	}

	public void setFlagEnrollmentAvvisatura(String flagEnrollmentAvvisatura) {
		this.flagEnrollmentAvvisatura = flagEnrollmentAvvisatura;
	}
	
	@OneToOne(targetEntity=Indirizzipostali.class,fetch=FetchType.LAZY ,cascade=CascadeType.ALL)
    @JoinColumn(name="ID_INDIRIZZIPOSTALI")
    public Indirizzipostali getIndirizzipostali() {
 		return this.indirizzipostali;
 	}
    public void setIndirizzipostali(Indirizzipostali indirizzipostali){
 		this.indirizzipostali=indirizzipostali;
    }
    
    @Override
	public String toString() {
		return "Intestatari [intestatario=" + intestatario + ", \nabi=" + abi
				+ ", \nabiaccentratore=" + abiaccentratore + ", \ncab=" + cab
				+ ", \nchiavessb=" + chiavessb + ", \ncodicepostel=" + codicepostel
				+ ", \ncodicesoftware=" + codicesoftware + ", \ndenominazione="
				+ denominazione + ", \ngruppo=" + gruppo + ", \nlapl=" + lapl
				+ ", \nragionesociale=" + ragionesociale + ", \nrapl=" + rapl
				+ ", \nrcz=" + rcz + ", \nsia=" + sia + ",  \nsiaCBI=" + siaCBI + ",\nstato=" + stato
				+ ", \ntipointestatario=" + tipointestatario + ", \ntiposicurezza="
				+ tiposicurezza + ", \nuffpostale=" + uffpostale + ", \ncodicecuc="
				+ codicecuc + ", \nnonresidente=" + nonresidente + ", \ncategoria="
				+ categoria + ", \nsottocategoria=" + sottocategoria + ", \nimportoMaxFlusso="
				+ importomaxflusso 
				+ "\nindirizzipostali=" + indirizzipostali==null?"<empty>":indirizzipostali.toString()
				+ "]";
	}
}
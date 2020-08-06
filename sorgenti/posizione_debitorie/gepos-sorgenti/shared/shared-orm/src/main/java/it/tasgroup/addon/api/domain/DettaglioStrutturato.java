package it.tasgroup.addon.api.domain;

import it.tasgroup.addon.api.validation.Amount;
import it.tasgroup.addon.api.validation.CodiceFiscale;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@SequenceGenerator(name = "tributoDettaglio", sequenceName = "TRIBUTO_DETTAGLIO_SEQ", allocationSize = 1)
//@TableGenerator(name = "tributoDettaglioTable", 
//				table = "ID_TABLE_GEN",
//				pkColumnName = "ID_NAME",
//				valueColumnName = "ID_VALUE",
//				pkColumnValue="TRIBUTO_STRUTTURATO")
public abstract class DettaglioStrutturato implements Serializable {
	
	private Long version;
	private long id;
	private String opInserimento;
	private Timestamp tsInserimento;
	private TributoStrutturato tributoStrutturato;
	private String idEnte;
	private BigDecimal importo;
	private String cfSoggettoPassivo;
	private int annoRiferimento;
	
	@Deprecated
	public DettaglioStrutturato() {
		super();
	}

	public DettaglioStrutturato(TributoStrutturato tributoStrutturato) {
		super();
		this.tributoStrutturato = tributoStrutturato;
	}

	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO, generator = "tributoDettaglioTable")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Transient
	public TributoStrutturato getTributoStrutturato() {
		return tributoStrutturato;
	}

	protected void setTributoStrutturato(TributoStrutturato tributoStrutturato) {
		this.tributoStrutturato = tributoStrutturato;
	}

	@Version
	@Column(name = "VERSION")
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Column(name = "OP_INSERIMENTO", nullable = false, length = 40)
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(name = "TS_INSERIMENTO", nullable = false)
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	@Column(name = "ID_ENTE", nullable = false)
	public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	@NotNull
	@Amount
	@Max(value=9999999,message="Il valore deve essere compreso tra 0,01 e 9.999.999,00")
	@Min(value=0,message="Il valore deve essere compreso tra 0,01 e 9999999,99")
	@Digits(integer = 7, fraction = 2,message="Il valore deve essere compreso tra 0,00 e 9.999.999,00 e deve avere al massimo due cifre decimali")
	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	@CodiceFiscale
	@Column(name="CF_SOGG_PASSIVO")
	public String getCfSoggettoPassivo() {
		return cfSoggettoPassivo;
	}

	public void setCfSoggettoPassivo(String cfSoggettoPassivo) {
		this.cfSoggettoPassivo = cfSoggettoPassivo != null ? cfSoggettoPassivo.toUpperCase() : null;
	}

	/*
	 * valutare se aggiungere una annotation Year
	 */
	@Min(2000)
	@Max(2050)
	@Digits(integer = 4, fraction = 0)
	@Column(name = "ANNO_RIF")
	@NotNull
	public int getAnnoRiferimento() {
		return annoRiferimento;
	}

	public void setAnnoRiferimento(int annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}

	@Override
	public String toString() {
		return "version=" + version + ", id=" + id
				+ ", opInserimento=" + opInserimento + ", tsInserimento="
				+ tsInserimento + ", tributoStrutturato=" + tributoStrutturato
				+ ", idEnte=" + idEnte + ", importo=" + importo
				+ ", cfSoggettoPassivo=" + cfSoggettoPassivo
				+ ", annoRiferimento=" + annoRiferimento;
	}

	

}

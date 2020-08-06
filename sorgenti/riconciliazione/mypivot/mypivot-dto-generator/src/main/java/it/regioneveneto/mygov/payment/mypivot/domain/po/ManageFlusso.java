package it.regioneveneto.mygov.payment.mypivot.domain.po;
// Generated Nov 15, 2017 2:45:28 PM by Hibernate Tools 4.0.1.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * ManageFlusso generated by hbm2java
 */
@Entity
@Table(name = "mygov_manage_flusso")
public class ManageFlusso implements java.io.Serializable {

	private Long id;
	private int version;
	private Ente ente;
	private Utente utente;
	private AnagraficaStato anagraficaStato;
	private TipoFlusso tipoFlusso;
	private String identificativoPsp;
	private String codIdentificativoFlusso;
	private Date dtDataOraFlusso;
	private String dePercorsoFile;
	private String deNomeFile;
	private Long numDimensioneFileScaricato;
	private String codRequestToken;
	private Date dtCreazione;
	private Date dtUltimaModifica;
	private String codProvenienzaFile;
	private Long idChiaveMultitabella;

	public ManageFlusso() {
	}

	public ManageFlusso(Ente ente, AnagraficaStato anagraficaStato, TipoFlusso tipoFlusso, Date dtCreazione,
			Date dtUltimaModifica) {
		this.ente = ente;
		this.anagraficaStato = anagraficaStato;
		this.tipoFlusso = tipoFlusso;
		this.dtCreazione = dtCreazione;
		this.dtUltimaModifica = dtUltimaModifica;
	}

	public ManageFlusso(Ente ente, Utente utente, AnagraficaStato anagraficaStato, TipoFlusso tipoFlusso,
			String identificativoPsp, String codIdentificativoFlusso, Date dtDataOraFlusso, String dePercorsoFile,
			String deNomeFile, Long numDimensioneFileScaricato, String codRequestToken, Date dtCreazione,
			Date dtUltimaModifica, String codProvenienzaFile, Long idChiaveMultitabella) {
		this.ente = ente;
		this.utente = utente;
		this.anagraficaStato = anagraficaStato;
		this.tipoFlusso = tipoFlusso;
		this.identificativoPsp = identificativoPsp;
		this.codIdentificativoFlusso = codIdentificativoFlusso;
		this.dtDataOraFlusso = dtDataOraFlusso;
		this.dePercorsoFile = dePercorsoFile;
		this.deNomeFile = deNomeFile;
		this.numDimensioneFileScaricato = numDimensioneFileScaricato;
		this.codRequestToken = codRequestToken;
		this.dtCreazione = dtCreazione;
		this.dtUltimaModifica = dtUltimaModifica;
		this.codProvenienzaFile = codProvenienzaFile;
		this.idChiaveMultitabella = idChiaveMultitabella;
	}

	@SequenceGenerator(name = "generator", allocationSize=1, sequenceName = "mygov_manage_flusso_mygov_manage_flusso_id_seq")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")

	@Column(name = "mygov_manage_flusso_id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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
	@JoinColumn(name = "mygov_ente_id", nullable = false)
	public Ente getEnte() {
		return this.ente;
	}

	public void setEnte(Ente ente) {
		this.ente = ente;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mygov_utente_id")
	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mygov_anagrafica_stato_id", nullable = false)
	public AnagraficaStato getAnagraficaStato() {
		return this.anagraficaStato;
	}

	public void setAnagraficaStato(AnagraficaStato anagraficaStato) {
		this.anagraficaStato = anagraficaStato;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mygov_tipo_flusso_id", nullable = false)
	public TipoFlusso getTipoFlusso() {
		return this.tipoFlusso;
	}

	public void setTipoFlusso(TipoFlusso tipoFlusso) {
		this.tipoFlusso = tipoFlusso;
	}

	@Column(name = "identificativo_psp", length = 35)
	public String getIdentificativoPsp() {
		return this.identificativoPsp;
	}

	public void setIdentificativoPsp(String identificativoPsp) {
		this.identificativoPsp = identificativoPsp;
	}

	@Column(name = "cod_identificativo_flusso", length = 35)
	public String getCodIdentificativoFlusso() {
		return this.codIdentificativoFlusso;
	}

	public void setCodIdentificativoFlusso(String codIdentificativoFlusso) {
		this.codIdentificativoFlusso = codIdentificativoFlusso;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_ora_flusso", length = 29)
	public Date getDtDataOraFlusso() {
		return this.dtDataOraFlusso;
	}

	public void setDtDataOraFlusso(Date dtDataOraFlusso) {
		this.dtDataOraFlusso = dtDataOraFlusso;
	}

	@Column(name = "de_percorso_file", length = 256)
	public String getDePercorsoFile() {
		return this.dePercorsoFile;
	}

	public void setDePercorsoFile(String dePercorsoFile) {
		this.dePercorsoFile = dePercorsoFile;
	}

	@Column(name = "de_nome_file", length = 256)
	public String getDeNomeFile() {
		return this.deNomeFile;
	}

	public void setDeNomeFile(String deNomeFile) {
		this.deNomeFile = deNomeFile;
	}

	@Column(name = "num_dimensione_file_scaricato")
	public Long getNumDimensioneFileScaricato() {
		return this.numDimensioneFileScaricato;
	}

	public void setNumDimensioneFileScaricato(Long numDimensioneFileScaricato) {
		this.numDimensioneFileScaricato = numDimensioneFileScaricato;
	}

	@Column(name = "cod_request_token")
	public String getCodRequestToken() {
		return this.codRequestToken;
	}

	public void setCodRequestToken(String codRequestToken) {
		this.codRequestToken = codRequestToken;
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

	@Column(name = "cod_provenienza_file", length = 10)
	public String getCodProvenienzaFile() {
		return this.codProvenienzaFile;
	}

	public void setCodProvenienzaFile(String codProvenienzaFile) {
		this.codProvenienzaFile = codProvenienzaFile;
	}

	@Column(name = "id_chiave_multitabella")
	public Long getIdChiaveMultitabella() {
		return this.idChiaveMultitabella;
	}

	public void setIdChiaveMultitabella(Long idChiaveMultitabella) {
		this.idChiaveMultitabella = idChiaveMultitabella;
	}

}

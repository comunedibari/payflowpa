package it.tasgroup.iris.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the CASELLARIO_DISPO database table.
 * 
 */
@Entity
@Table(name="CASELLARIO_DISPO")
public class CasellarioDispo extends BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Timestamp dataElaborazione;
	private String descErrore;
	private Integer dimensione;
	private Integer flagElaborazione;
	private byte[] flussoCbi;
	private String nomeSupporto;
	private Integer numeroRecord;
	private String opAggiornamento;
	private String opInserimento;
	private Integer tipoErrore;
	private String tipoFlusso;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private DistinteRiaccredito distinteRiaccredito;
	private GestioneFlussi distintePagamento;
	
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="casellario_dispo_pk_gen")	
	@SequenceGenerator(
		    name="casellario_dispo_pk_gen",
		    sequenceName="CASELLARIO_DISPO_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}		
	public void setId(Long id) {		
		this.id = id;	 
	} 		

    public CasellarioDispo() {
    }

	@Column(name="DATA_ELABORAZIONE")
	public Timestamp getDataElaborazione() {
		return this.dataElaborazione;
	}

	public void setDataElaborazione(Timestamp dataElaborazione) {
		this.dataElaborazione = dataElaborazione;
	}


	@Column(name="DESC_ERRORE")
	public String getDescErrore() {
		return this.descErrore;
	}

	public void setDescErrore(String descErrore) {
		this.descErrore = descErrore;
	}


	public Integer getDimensione() {
		return this.dimensione;
	}

	public void setDimensione(Integer dimensione) {
		this.dimensione = dimensione;
	}


	@Column(name="FLAG_ELABORAZIONE")
	public Integer getFlagElaborazione() {
		return this.flagElaborazione;
	}

	public void setFlagElaborazione(Integer flagElaborazione) {
		this.flagElaborazione = flagElaborazione;
	}


    @Lob()
	@Column(name="FLUSSO_CBI")
	public byte[] getFlussoCbi() {
		return this.flussoCbi;
	}

	public void setFlussoCbi(byte[] flussoCbi) {
		this.flussoCbi = flussoCbi;
	}

	@Column(name="NOME_SUPPORTO")
	public String getNomeSupporto() {
		return this.nomeSupporto;
	}

	public void setNomeSupporto(String nomeSupporto) {
		this.nomeSupporto = nomeSupporto;
	}


	@Column(name="NUMERO_RECORD")
	public Integer getNumeroRecord() {
		return this.numeroRecord;
	}

	public void setNumeroRecord(Integer numeroRecord) {
		this.numeroRecord = numeroRecord;
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


	@Column(name="TIPO_ERRORE")
	public Integer getTipoErrore() {
		return this.tipoErrore;
	}

	public void setTipoErrore(Integer tipoErrore) {
		this.tipoErrore = tipoErrore;
	}


	@Column(name="TIPO_FLUSSO")
	public String getTipoFlusso() {
		return this.tipoFlusso;
	}

	public void setTipoFlusso(String tipoFlusso) {
		this.tipoFlusso = tipoFlusso;
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

	//bi-directional one-to-one association to DistinteRiaccredito
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DISTINTE_RIACCREDITO", nullable=true)
	public DistinteRiaccredito getDistinteRiaccredito() {
		return this.distinteRiaccredito;
	}

	public void setDistinteRiaccredito(DistinteRiaccredito distinteRiaccredito) {
		this.distinteRiaccredito = distinteRiaccredito;
	}

	//bi-directional one-to-one association to DistinteRiaccredito
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DISTINTE_PAGAMENTO", nullable=true)
	public GestioneFlussi getDistintePagamento() {
		return distintePagamento;
	}

	public void setDistintePagamento(GestioneFlussi distintePagamento) {
		this.distintePagamento = distintePagamento;
	}
	
}
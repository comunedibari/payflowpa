package it.tasgroup.iris.domain;

import it.nch.is.fo.tributi.TributoEnte;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the DISTINTE_RIACCREDITO database table.
 * 
 */
@Entity
@Table(name="DISTINTE_RIACCREDITO")
@SqlResultSetMappings( {  
    @SqlResultSetMapping(
            name="ricercaRiaccreditoEntiMapping",
            columns = { 
                    @ColumnResult(name="DATA_SPEDIZIONE"),
                    @ColumnResult(name="TS_INSERIMENTO"),
                    @ColumnResult(name="CODICE_RIFERIMENTO"),
                    @ColumnResult(name="RAG_SOCIALE_BENEFICIARIO"),
                    @ColumnResult(name="IBAN_BENEFICIARIO"),
                    @ColumnResult(name="IMPORTO"),
                    @ColumnResult(name="ID_PAG_COND_ENTE"),
                    @ColumnResult(name="STATO"),
                    @ColumnResult(name="DE_TRB"),
                    @ColumnResult(name="TS_DECORRENZA"),
                    @ColumnResult(name="COD_PAGAMENTO"),
                    @ColumnResult(name="CAUSALE")
            }         
            )
})
public class DistinteRiaccredito extends BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codTransazione;
	private Timestamp dataCreazione;
	private Timestamp dataSpedizione;
	private String divisa;
	private BigDecimal importo;
	private BigDecimal importoCommissioni;
	private int numeroDisposizioni;
	private String opAggiornamento;
	private String opInserimento;
	private String stato;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private String utenteCreatore;
	private Set<BonificiRiaccredito> bonificiRiaccredito;
	private CasellarioDispo casellarioDispo;

    public DistinteRiaccredito() {
    }

	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="distinte_riaccredito_pk_gen")	
	@SequenceGenerator(
		    name="distinte_riaccredito_pk_gen",
		    sequenceName="DISTINTE_RIACCREDITO_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 		    

	@Column(name="COD_TRANSAZIONE")
	public String getCodTransazione() {
		return this.codTransazione;
	}

	public void setCodTransazione(String codTransazione) {
		this.codTransazione = codTransazione;
	}


	@Column(name="DATA_CREAZIONE")
	public Timestamp getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


	@Column(name="DATA_SPEDIZIONE")
	public Timestamp getDataSpedizione() {
		return this.dataSpedizione;
	}

	public void setDataSpedizione(Timestamp dataSpedizione) {
		this.dataSpedizione = dataSpedizione;
	}


	public String getDivisa() {
		return this.divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}


	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}


	@Column(name="IMPORTO_COMMISSIONI")
	public BigDecimal getImportoCommissioni() {
		return this.importoCommissioni;
	}

	public void setImportoCommissioni(BigDecimal importoCommissioni) {
		this.importoCommissioni = importoCommissioni;
	}


	@Column(name="NUMERO_DISPOSIZIONI")
	public int getNumeroDisposizioni() {
		return this.numeroDisposizioni;
	}

	public void setNumeroDisposizioni(int numeroDisposizioni) {
		this.numeroDisposizioni = numeroDisposizioni;
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


	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
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


	@Column(name="UTENTE_CREATORE")
	public String getUtenteCreatore() {
		return this.utenteCreatore;
	}

	public void setUtenteCreatore(String utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	//bi-directional many-to-one association to BonificiRiaccredito
	@OneToMany(mappedBy="distinteRiaccredito", fetch=FetchType.LAZY)
	public Set<BonificiRiaccredito> getBonificiRiaccredito() {
		return this.bonificiRiaccredito;
	}

	public void setBonificiRiaccredito(Set<BonificiRiaccredito> bonificiRiaccreditos) {
		this.bonificiRiaccredito = bonificiRiaccreditos;
	}
	

	//bi-directional one-to-one association to CasellarioDispo
	@OneToOne(cascade=CascadeType.ALL,mappedBy="distinteRiaccredito")
	public CasellarioDispo getCasellarioDispo() {
		return this.casellarioDispo;
	}

	public void setCasellarioDispo(CasellarioDispo casellarioDispos) {
		this.casellarioDispo = casellarioDispos;
	}
	
}
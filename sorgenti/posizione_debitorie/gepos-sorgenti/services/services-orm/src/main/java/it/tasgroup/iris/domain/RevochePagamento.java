package it.tasgroup.iris.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the REVOCHE_PAGAMENTO database table.
 */
@Entity
@Table(name = "REVOCHE_PAGAMENTO") 
public class RevochePagamento extends BaseIdEntity  { 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
    private Pagamenti pagamento;
    
    
    private String statoRevoca; //-	STATO_REVOCA: VARCHAR (1) Valori R=Richiesta, A=Accettata, N=Negata, W=In attesa decisione
    private String esitoRevoca;	//	-	ESITO_REVOCA_INVIATO:  VARCHAR (1)  Valori = 0, 1.
    private String tipoRevocaRichiesta; // TIPO_REVOCA_RICHIESTA :  VARCHAR (1) - Valori: 0,1,2 Default Null (come da messaggio RR)
    private String causaleRevocaRichiesta; //		-	CAUSALE_REVOCA_RICHIESTA : VARCHAR (140)
    private String datiAggRevocaRichiesta; //		-	DATI_AGG_REVOCA_RICHIESTA :  VARCHAR (140)
    private String idMessaggioRevoca; //		-	ID_MESSAGGIO_REVOCA: VARCHAR (35)
    private Timestamp tsMessaggioRevoca; //		-	TS_MESSAGGIO_REVOCA:  TIMESTAMP
    private String causaleEsitoRevoca; 	//	-	CAUSALE_ESITO_REVOCA: VARCHAR (140)
    private String datiAggiuntiviEsitoRevoca; //		-	DATI_AGG_ESITO_REVOCA: VARCHAR (140)

    
    private String opAggiornamento;
    private String opInserimento;
    private Timestamp tsAggiornamento;  
    private Timestamp tsInserimento;
    
 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="revoche_pagamento_pk_gen")
	@SequenceGenerator(
		    name="revoche_pagamento_pk_gen",
		    sequenceName="REVOCHE_PAGAMENTO_ID_SEQ",
		    allocationSize=1
		)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

 
	@Column(name = "STATO_REVOCA")
    public String getStatoRevoca() {
		return statoRevoca;
	}

	public void setStatoRevoca(String statoRevoca) {
		this.statoRevoca = statoRevoca;
	}
    
	@Column(name = "ESITO_REVOCA_INVIATO")
	public String getEsitoRevoca() {
		return esitoRevoca;
	}

	public void setEsitoRevoca(String esitoRevoca) {
		this.esitoRevoca = esitoRevoca;
	}

	@Column(name = "TIPO_REVOCA_RICHIESTA")
	public String getTipoRevocaRichiesta() {
		return tipoRevocaRichiesta;
	}

	public void setTipoRevocaRichiesta(String tipoRevocaRichiesta) {
		this.tipoRevocaRichiesta = tipoRevocaRichiesta;
	}
	
	@Column(name = "CAUSALE_REVOCA_RICHIESTA")
	public String getCausaleRevocaRichiesta() {
		return causaleRevocaRichiesta;
	}

	public void setCausaleRevocaRichiesta(String causaleRevocaRichiesta) {
		this.causaleRevocaRichiesta = causaleRevocaRichiesta;
	}
	
	@Column(name = "DATI_AGG_REVOCA_RICHIESTA")
	public String getDatiAggRevocaRichiesta() {
		return datiAggRevocaRichiesta;
	}

	public void setDatiAggRevocaRichiesta(String datiAggRevocaRichiesta) {
		this.datiAggRevocaRichiesta = datiAggRevocaRichiesta;
	}
	
	@Column(name = "ID_MESSAGGIO_REVOCA")
	public String getIdMessaggioRevoca() {
		return idMessaggioRevoca;
	}

	public void setIdMessaggioRevoca(String idMessaggioRevoca) {
		this.idMessaggioRevoca = idMessaggioRevoca;
	}
	
	@Column(name = "TS_MESSAGGIO_REVOCA")
	public Timestamp getTsMessaggioRevoca() {
		return tsMessaggioRevoca;
	}

	public void setTsMessaggioRevoca(Timestamp tsMessaggioRevoca) {
		this.tsMessaggioRevoca = tsMessaggioRevoca;
	}
	
	@Column(name = "CAUSALE_ESITO_REVOCA")
	public String getCausaleEsitoRevoca() {
		return causaleEsitoRevoca;
	}

	public void setCausaleEsitoRevoca(String causaleEsitoRevoca) {
		this.causaleEsitoRevoca = causaleEsitoRevoca;
	}

	@Column(name = "DATI_AGG_ESITO_REVOCA")
	public String getDatiAggiuntiviEsitoRevoca() {
		return datiAggiuntiviEsitoRevoca;
	}

	public void setDatiAggiuntiviEsitoRevoca(String datiAggiuntiviEsitoRevoca) {
		this.datiAggiuntiviEsitoRevoca = datiAggiuntiviEsitoRevoca;
	}

	@Column(name = "OP_AGGIORNAMENTO")
    public String getOpAggiornamento() {
        return this.opAggiornamento;
    }

    public void setOpAggiornamento(String opAggiornamento) {
        this.opAggiornamento = opAggiornamento;
    }

    @Column(name = "OP_INSERIMENTO")
    public String getOpInserimento() {
        return this.opInserimento;
    }

    public void setOpInserimento(String opInserimento) {
        this.opInserimento = opInserimento;
    }

    

    @Column(name = "TS_AGGIORNAMENTO")
    public Timestamp getTsAggiornamento() {
        return this.tsAggiornamento;
    }

    public void setTsAggiornamento(Timestamp tsAggiornamento) {
        this.tsAggiornamento = tsAggiornamento;
    }

  

    @Column(name = "TS_INSERIMENTO")
    public Timestamp getTsInserimento() {
        return this.tsInserimento;
    }

    public void setTsInserimento(Timestamp tsInserimento) {
        this.tsInserimento = tsInserimento;
    }

    

    @ManyToOne(targetEntity = Pagamenti.class, fetch = FetchType.LAZY) 
    @JoinColumn(name = "ID_PAGAMENTI", nullable = true)
    public Pagamenti getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamenti pag) {
        this.pagamento = pag;
    }

 
}

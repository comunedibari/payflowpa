package it.tasgroup.iris2.pagamenti;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import it.tasgroup.idp.domain.BaseEntity;

/**
 * The persistent class for the JLTPAGA database table.
 */
@Entity
@Table(name = "REVOCHE_PAGAMENTO")
@NamedQueries(
{
@NamedQuery(
		name="findRevochePagamDaElaborare",
		query="SELECT revoche FROM RevochePagamento revoche " +
				" WHERE revoche.statoRevoca = 'R' "),
@NamedQuery(
		name="findRevochePagamDaInviare",
		query="SELECT revoche FROM RevochePagamento revoche " +
				" WHERE revoche.statoRevoca IN ('A','N') and revoche.esitoRevocaInviato = '0' "),
@NamedQuery(
		name="findRevochePagamByPagamento",
		query="SELECT revoche FROM RevochePagamento revoche " +
				" WHERE revoche.pagamento.id = :idPagamento ")

}
)
public class RevochePagamento extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
    private Pagamenti pagamento;
    
    
    private String statoRevoca; //-	STATO_REVOCA: VARCHAR (1) â€“ Valori =Richiesta, A=Accettata, N=Negata, W=In attesa decisioneâ€�
    private String esitoRevocaInviato;	//	-	ESITO_REVOCA_INVIATO:  VARCHAR (1) â€“ Valori = 0, 1.
    private String tipoRevocaRichiesta; // TIPO_REVOCA_RICHIESTA :  VARCHAR (1) - Valori: â€œ0,1,2â€� Default Null (come da messaggio RR)
    private String causaleRevocaRichiesta; //		-	CAUSALE_REVOCA_RICHIESTA : VARCHAR (140)
    private String datiAggRevocaRichiesta; //		-	DATI_AGG_REVOCA_RICHIESTA :  VARCHAR (140)
    private String idMessaggioRevoca; //		-	ID_MESSAGGIO_REVOCA: VARCHAR (35)
    private Timestamp tsMessaggioRevoca; //		-	TS_MESSAGGIO_REVOCA:  TIMESTAMP
    private String causaleEsitoRevoca; 	//	-	CAUSALE_ESITO_REVOCA: VARCHAR (140)
    private String datiAggiuntiviEsitoRevoca; //		-	DATI_AGG_ESITO_REVOCA: VARCHAR (140)

 
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
	public String getEsitoRevocaInviato() {
		return esitoRevocaInviato;
	}

	public void setEsitoRevocaInviato(String esitoRevoca) {
		this.esitoRevocaInviato = esitoRevoca;
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

    @ManyToOne(targetEntity = Pagamenti.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PAGAMENTI", nullable = true)
    public Pagamenti getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamenti pag) {
        this.pagamento = pag;
    }

 
}

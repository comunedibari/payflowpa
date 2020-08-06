package it.tasgroup.iris.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the CANALI_PAGAMENTO database table.
 * 
 */
@Entity
@Table(name="CANALI_PAGAMENTO") 
@NamedQueries(
{
@NamedQuery(
		name="getByTipoCanale",
		query="select cp from CanaliPagamento cp "+
			  "where cp.tipoCanale = :tipoCanale")
	}
)
public class CanaliPagamento implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
    private String codCanale;
    private String tipoCanale;
	private String descCanale;
	private String flStato;
	private String opAggiornamento;
	private String opInserimento;
	private int prVersione;
	private String stRiga;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private int version;

    public CanaliPagamento() {
    }


	@Id
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
    @Column(name="COD_CANALE")
	public String getCodCanale() {
		return codCanale;
	}


	public void setCodCanale(String codCanale) {
		this.codCanale = codCanale;
	}

    @Column(name="DESC_CANALE")
	public String getDescCanale() {
		return descCanale;
	}


	public void setDescCanale(String descCanale) {
		this.descCanale = descCanale;
	}

    @Column(name="TIPO_CANALE")
	public String getTipoCanale() {
		return tipoCanale;
	}


	public void setTipoCanale(String tipoCanale) {
		this.tipoCanale = tipoCanale;
	}


	@Column(name="FL_STATO")
	public String getFlStato() {
		return this.flStato;
	}

	public void setFlStato(String flStato) {
		this.flStato = flStato;
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
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}


	@Column(name="ST_RIGA")
	public String getStRiga() {
		return this.stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
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


	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
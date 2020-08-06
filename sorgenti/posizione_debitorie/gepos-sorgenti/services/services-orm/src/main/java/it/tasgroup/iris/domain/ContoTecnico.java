package it.tasgroup.iris.domain;

import it.nch.is.fo.profilo.Intestatari;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the CONTOTECNICO database table.
 * 
 */
@Entity
@Table(name="CONTOTECNICO")
@NamedQueries(
{
	@NamedQuery(
		name="getUniqueContoTecnicoAttivo",
		query="select conto from ContoTecnico conto "+
			  "where intestatario.corporate = :intestatario and conto.tsAnnullamento is null and conto.opAnnullamento is null")
	}
)
public class ContoTecnico implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String descrizione;
	private String iban;
	private String intestazione;
	private String opAggiornamento;
	private String opAnnullamento;
	private String opInserimento;
	private Timestamp tsAggiornamento;
	private Timestamp tsAnnullamento;
	private Timestamp tsInserimento;
	private int version;
	private Intestatari intestatario;

    public ContoTecnico() {
    }


	@Id
	@Column(unique=true, nullable=false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(nullable=false, length=200)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	@Column(nullable=false, length=54)
	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}


	@Column(nullable=false, length=80)
	public String getIntestazione() {
		return this.intestazione;
	}

	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}


	@Column(name="OP_AGGIORNAMENTO", length=80)
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}


	@Column(name="OP_ANNULLAMENTO", length=80)
	public String getOpAnnullamento() {
		return this.opAnnullamento;
	}

	public void setOpAnnullamento(String opAnnullamento) {
		this.opAnnullamento = opAnnullamento;
	}


	@Column(name="OP_INSERIMENTO", length=80)
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}


	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}


	@Column(name="TS_ANNULLAMENTO")
	public Timestamp getTsAnnullamento() {
		return this.tsAnnullamento;
	}

	public void setTsAnnullamento(Timestamp tsAnnullamento) {
		this.tsAnnullamento = tsAnnullamento;
	}


	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}


	@Column(name="\"VERSION\"", nullable=false)
	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}


	//bi-directional many-to-one association to Intestatari
    @ManyToOne
	@JoinColumn(name="INTESTATARIO", nullable=false)
	public Intestatari getIntestatario() {
		return this.intestatario;
	}

	public void setIntestatario(Intestatari intestatario) {
		this.intestatario = intestatario;
	}
	
}
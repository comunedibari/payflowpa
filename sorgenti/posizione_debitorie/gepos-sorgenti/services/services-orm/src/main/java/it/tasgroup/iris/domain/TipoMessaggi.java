package it.tasgroup.iris.domain;

/**
 * @author FabriziE
 */

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "GEC_TIPO_MESSAGGI")

public class TipoMessaggi extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String denominazione;
	private String stato;

	private String opInserimento;
	private String opAggiornamento;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;

	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tipomessaggi_pk_gen")
	@SequenceGenerator(name = "tipomessaggi_pk_gen", sequenceName = "GEC_TIPO_MESSAGGI_ID_SEQ", allocationSize = 1)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "STATO")
	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name = "TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}

	@Column(name = "TS_INSERIMENTO", nullable = false)
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	@Column(name = "DENOMINAZIONE")
	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	@Column(name = "OP_INSERIMENTO")
	public String getOpInserimento() {
		return opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(name = "OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Canale [id=");
		builder.append(getId());
		builder.append(", denominazione=");
		builder.append(getDenominazione());
		builder.append(", stato=");
		builder.append(getStato());
		builder.append(", tsAggiornamento=");
		builder.append(getTsAggiornamento());
		builder.append(", opAggiornamento=");
		builder.append(getOpAggiornamento());
		builder.append(", tsInserimento=");
		builder.append(getTsInserimento());
		builder.append(", opInserimento=");
		builder.append(getOpInserimento());
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append("]");
		return builder.toString();
	}

}
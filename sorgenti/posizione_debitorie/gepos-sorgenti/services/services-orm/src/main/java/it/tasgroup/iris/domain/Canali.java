package it.tasgroup.iris.domain;

import java.math.BigInteger;
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
@Table(name = "GEC_CANALI")
@NamedQueries({ @NamedQuery(name = "findCanaliByDenominazione", query = "from Canali c " + "where c.denominazione = :denominazione") })
public class Canali extends BaseEntity {
	private static final long serialVersionUID = 1231963839181791205L;

	private String denominazione;
	private String stato;
	private BigInteger numTentativi;

	private BigInteger tempoRetry;
	private String configurazione;

	private String opInserimento;
	private String opAggiornamento;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;

	private int idTipoMessaggio;

	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "canali_pk_gen")
	@SequenceGenerator(name = "canali_pk_gen", sequenceName = "GEC_CANALI_ID_SEQ", allocationSize = 1)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NUM_TENTATIVI")
	public BigInteger getNumTentativi() {
		return this.numTentativi;
	}

	public void setNumTentativi(BigInteger NumTentativi) {
		this.numTentativi = NumTentativi;
	}

	@Column(name = "STATO")
	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name = "TEMPO_RETRY")
	public BigInteger getTempoRetry() {
		return tempoRetry;
	}

	public void setTempoRetry(BigInteger tempoRetry) {
		this.tempoRetry = tempoRetry;
	}

	@Column(name = "CONFIGURAZIONE")
	public String getConfigurazione() {
		return configurazione;
	}

	public void setConfigurazione(String configurazione) {
		this.configurazione = configurazione;
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

	@Column(name = "ID_TIPO_MESSAGGIO")
	public int getIdTipoMessaggio() {
		return idTipoMessaggio;
	}

	public void setIdTipoMessaggio(int idTipoMessaggio) {
		this.idTipoMessaggio = idTipoMessaggio;
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
		builder.append(", numTentativi=");
		builder.append(getNumTentativi());
		builder.append(", tempoRetry=");
		builder.append(getTempoRetry());
		builder.append(", configurazione=");
		builder.append(getConfigurazione());
		builder.append(", idTipoMessaggio=");
		builder.append(getIdTipoMessaggio());
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
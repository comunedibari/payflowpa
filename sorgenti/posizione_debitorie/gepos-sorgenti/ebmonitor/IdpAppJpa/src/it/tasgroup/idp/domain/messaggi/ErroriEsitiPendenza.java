package it.tasgroup.idp.domain.messaggi;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ERRORI_ESITI_PENDENZA")
public class ErroriEsitiPendenza implements Serializable {

	private static final long serialVersionUID = 1L;

	/*** Persistent Values***/
	private String idErrore;
	private String idPendenza;
	private String idPendenzaEnte;
	private String codice;
	private String descrizioneErrore;	
	private int prVersione;
	private String opInserimento;
	private Timestamp tsInserimento;

	/*** Persistent Associations***/
	private EsitiPendenza idEsitoPendenza;


	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Id
	@Column(name="ID_ERRORE")
	public String getIdErrore() {
		return this.idErrore;
	}
	public void setIdErrore(String idErrore) {
		this.idErrore = idErrore;
	}

	@Column(name="ID_PENDENZA")
	public String getIdPendenza() {
		return this.idPendenza;
	}
	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}

	@Column(name="ID_PENDENZA_ENTE")
	public String getIdPendenzaEnte() {
		return this.idPendenzaEnte;
	}
	public void setIdPendenzaEnte(String idPendenzaEnte) {
		this.idPendenzaEnte = idPendenzaEnte;
	}

	public String getCodice() {
		return this.codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}

	@Column(name="PR_VERSIONE")
	public int getPrVersione() {
		return this.prVersione;
	}
	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}

	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}
	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	@Column(name="DESCRIZIONE_ERRORE")
	public String getDescrizioneErrore() {
		return descrizioneErrore;
	}
	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}

	@ManyToOne
	@JoinColumn(name="ID_ESITO_PENDENZA")
	public EsitiPendenza getIdEsitoPendenza() {
		return this.idEsitoPendenza;
	}
	public void setIdEsitoPendenza(EsitiPendenza idEsitoPendenza) {
		this.idEsitoPendenza = idEsitoPendenza;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErroriEsitiPendenza [idErrore=");
		builder.append(idErrore);
		builder.append(", idPendenza=");
		builder.append(idPendenza);
		builder.append(", idPendenzaEnte=");
		builder.append(idPendenzaEnte);
		builder.append(", codice=");
		builder.append(codice);
		builder.append(", descrizioneErrore=");
		builder.append(descrizioneErrore);
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", opInserimento=");
		builder.append(opInserimento);
		builder.append(", tsInserimento=");
		builder.append(tsInserimento);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idErrore == null) ? 0 : idErrore.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ErroriEsitiPendenza other = (ErroriEsitiPendenza) obj;
		if (idErrore == null) {
			if (other.idErrore != null) {
				return false;
			}
		} else if (!idErrore.equals(other.idErrore)) {
			return false;
		}
		return true;
	}	
	
	
}

package it.tasgroup.idp.avvisi.digitali;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import it.tasgroup.iris2.pagamenti.CasellarioDispo;


/**
 * The persistent class for the AVVISI_DIGITALI_FILE  database table.
 *
 */
@Entity
@Table(name="AVVISI_DIGITALI_FILE")
public class FileInvioAvvisiDigitali extends it.tasgroup.idp.domain.BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/
	private String idFile;
	private String idEnte;
	private String tipoFile;
	private String stato;
	private Long numRetrySend;
	private Long version;

	/*** Persistent Associations ***/
	private CasellarioDispo casellarioDispo;
	
	
	@Id
	@Column(name="ID_FILE")
	public String getIdFile() {
		return idFile;
	}
    
	@Column(name="ID_ENTE")
	public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	
	@Column(name="STATO")
	public String getStato() {
		return stato;
	}
	@Column(name="TIPO_FILE")
	public String getTipoFile() {
		return tipoFile;
	}

	public void setTipoFile(String tipoFile) {
		this.tipoFile = tipoFile;
	}
	@Column(name="NUM_RETRY_SEND")
	public Long getNumRetrySend() {
		return numRetrySend;
	}

	public void setNumRetrySend(Long numRetrySend) {
		this.numRetrySend = numRetrySend;
	}

	public void setIdFile(String idFileF) {
		this.idFile = idFileF;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
	//bi-directional many-to-one association to CasellarioDispo
    @OneToOne
	@JoinColumn(name="ID_CASELLARIO_DISPO", nullable=false)
	public CasellarioDispo getCasellarioDispo() {
		return this.casellarioDispo;
	}

	public void setCasellarioDispo(CasellarioDispo casellarioDispo) {
		this.casellarioDispo = casellarioDispo;
	}
 
	
	@Version
	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(" [FileInvioAvvisiDigitaliNdp= [ idFile = ");
		builder.append(idFile);
		builder.append(", tipoFile = ");
		builder.append(tipoFile);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", numRetrySend=");
		builder.append(numRetrySend);
		builder.append(", casellarioDispo=");
		builder.append(casellarioDispo);
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append(", getOpInserimento()=");
		builder.append(getOpInserimento());
		builder.append(", getOpAggiornamento()=");
		builder.append(getOpAggiornamento());
		builder.append(", getTsInserimento()=");
		builder.append(getTsInserimento());
		builder.append(", getTsAggiornamento()=");
		builder.append(getTsAggiornamento());
		builder.append("]");
		return builder.toString();
	}
	
}

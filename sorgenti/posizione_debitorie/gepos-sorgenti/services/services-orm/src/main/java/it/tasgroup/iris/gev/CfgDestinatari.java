package it.tasgroup.iris.gev;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import it.tasgroup.iris.domain.BaseEntity;


/**
 * The persistent class for the GEV_CFG_DESTINATARI database table.
 * 
 */
@Entity
@Table(name="GEV_CFG_DESTINATARI")
public class CfgDestinatari extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/	
	private Long id;
	private String codiceEvento;
	private String tipoInvio;
	private String tipoDestinatario;
	
	private String opInserimento;
	private String opAggiornamento;
	private Timestamp tsInserimento;
	private Timestamp tsAggiornamento;
	
	/*** Persistent Associations ***/
	private CfgEventi configurazioneEvento;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="gev_cfg_destinatari_pk_gen")	
	@SequenceGenerator(name="gev_cfg_destinatari_pk_gen", sequenceName="GEV_CFG_DESTINATARI_ID_SEQ", allocationSize=1)	
	public Long getId() {
		return this.id; 
	}		
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="TIPOINVIO")
	public String getTipoInvio() {
		return tipoInvio;
	}

	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}
	@Column(name="TIPODESTINATARIO")
	public String getTipoDestinatario() {
		return tipoDestinatario;
	}

	public void setTipoDestinatario(String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}
	
	
	@ManyToOne
	@JoinColumn(name="CODICEEVENTO",referencedColumnName = "codiceEvento")
	public CfgEventi getConfigurazioneEvento() {
		return this.configurazioneEvento;
	}
	
	public void setConfigurazioneEvento(CfgEventi configurazioneEvento) {
		this.configurazioneEvento = configurazioneEvento;
	}

	
	@Column(name = "OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(name = "OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	@Column(name = "TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	@Column(name = "TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CfgEventi [codiceEvento=");
		builder.append(codiceEvento);
	
		builder.append(", tipoInvio=");
		builder.append(tipoInvio);
		builder.append(", tipoDestinatario=");
		builder.append(tipoDestinatario);
		builder.append(", template=");
		builder.append(", getId()=");
		builder.append(getId());
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
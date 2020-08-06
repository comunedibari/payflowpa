package it.tasgroup.ge;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the GEV_CFG_DESTINATARI database table.
 * 
 */
@Entity
@Table(name="GEV_CFG_DESTINATARI")
public class CfgDestinatari extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/	
	private String codiceEvento;
	private String tipoInvio;
	private String tipoDestinatario;
	
	/*** Persistent Associations ***/
	private CfgEventi configurazioneEvento;
	
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="gev_cfg_destinatari_pk_gen")	
	@SequenceGenerator(
	    name="gev_cfg_destinatari_pk_gen",
	    sequenceName="GEV_CFG_DESTINATARI_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
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
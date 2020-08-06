package it.tasgroup.ge;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the CFG_EVENTI database table.
 * 
 */

@NamedQueries({
@NamedQuery(
	name="ConfEventoByCodEvento", 
	query=
	"SELECT cfgEventi FROM CfgEventi cfgEventi " +
	" WHERE cfgEventi.codiceEvento = :codiceEvento"),
@NamedQuery(
	name="CfgEventi",
	query="SELECT cfgEventi FROM CfgEventi cfgEventi")
})

@Entity
@Table(name="GEV_CFG_EVENTI")
public class CfgEventi extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/	
	private String codiceEvento;
	private String descrizione;
	private String severity;
	private String attivo;
	private String template;
	
	/*** Persistent Associations ***/
	private Set<CfgDestinatari> cfgDestinatari;
	
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="gev_cfg_eventi_pk_gen")	
	@SequenceGenerator(
	    name="gev_cfg_eventi_pk_gen",
	    sequenceName="GEV_CFG_EVENTI_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}		
	
	@Column(name="CODICEEVENTO")
	public String getCodiceEvento() {
		return codiceEvento;
	}

	public void setCodiceEvento(String codiceEvento) {
		this.codiceEvento = codiceEvento;
	}
	
	@Column(name="DESCRIZIONE")
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	@Column(name="SEVERITY")
	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	@Column(name="ATTIVO")
	public String getAttivo() {
		return attivo;
	}

	public void setAttivo(String attivo) {
		this.attivo = attivo;
	}

	@Column(name="TEMPLATE")
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	
	@OneToMany(mappedBy="configurazioneEvento")
	public Set<CfgDestinatari> getCfgDestinatari() {
		return this.cfgDestinatari;
	}

	public void setCfgDestinatari(Set<CfgDestinatari> cfgDestinatari) {
		this.cfgDestinatari = cfgDestinatari;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		//builder.append("CfgEventi [codiceEvento=");
		//builder.append(codiceEvento);
		builder.append(", descrizione=");
		builder.append(descrizione);
		builder.append(", template=");
		builder.append(template);
		builder.append(", attivo=");
		builder.append(attivo);
		builder.append(", severity=");
		builder.append(severity);
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
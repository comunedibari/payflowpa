package it.tasgroup.iris.gev;

import java.io.Serializable;
import java.sql.Timestamp;
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

import it.tasgroup.iris.domain.BaseEntity;

/**
 * The persistent class for the CFG_EVENTI database table.
 * 
 */

@NamedQueries({
@NamedQuery(
	name="CfgEventiByCodiceEvento",
	query="SELECT cfgEventi FROM CfgEventi cfgEventi WHERE cfgEventi.codiceEvento = :codiceEvento")
})

@Entity
@Table(name="GEV_CFG_EVENTI")
public class CfgEventi extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/	
	private Long id;
	private String codiceEvento;
	private String descrizione;
	private String severity;
	private String attivo;
	private String template;

	private String opInserimento;
	private String opAggiornamento;
	private Timestamp tsInserimento;
	private Timestamp tsAggiornamento;
	
	/*** Persistent Associations ***/
	private Set<CfgDestinatari> cfgDestinatari;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="gev_cfg_eventi_pk_gen")	
	@SequenceGenerator(name="gev_cfg_eventi_pk_gen", sequenceName="GEV_CFG_EVENTI_ID_SEQ", allocationSize=1)	
	public Long getId() {
		return this.id; 
	}		
	
	public void setId(Long id) {
		this.id = id;
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
package it.tasgroup.ge;

import java.io.Serializable;
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


/**
 * The persistent class for the EVENTI database table.
 * 
 */

@NamedQueries({
@NamedQuery(
	name = "EventiByCodiciEventoAndDatiEventoAndStato",
	query = "SELECT eventi FROM Eventi eventi" +
			" WHERE eventi.codiceEvento IN (:codiciEvento)" + 
			" AND eventi.datiEvento = :datiEvento" +
			" AND eventi.stato = :stato" +
			" ORDER BY eventi.tsInserimento DESC")})
@Entity
@Table(name="GEV_EVENTI")
public class Eventi extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/	
	private String codiceEvento;
	private String datiEvento;
	private Timestamp dataUltimaEsecuz;
	private int numeroTentativi;
	private String stato;
	private String descrStato;
	
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="gev_eventi_pk_gen")	
	@SequenceGenerator(
	    name="gev_eventi_pk_gen",
	    sequenceName="GEV_EVENTI_ID_SEQ",
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
	
	
	
	@Column(name="DATIEVENTO")
	public String getDatiEvento() {
		return datiEvento;
	}

	public void setDatiEvento(String datiEvento) {
		this.datiEvento = datiEvento;
	}


	@Column(name="DATAULTIMAESECUZ")
	public Timestamp getDataUltimaEsecuz() {
		return dataUltimaEsecuz;
	}

	public void setDataUltimaEsecuz(Timestamp dataUltimaEsecuz) {
		this.dataUltimaEsecuz = dataUltimaEsecuz;
	}
	@Column(name="NUMEROTENTATIVI")
	public int getNumeroTentativi() {
		return numeroTentativi;
	}

	public void setNumeroTentativi(int numeroTentativi) {
		this.numeroTentativi = numeroTentativi;
	}
	@Column(name="STATO")
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	@Column(name="DESCRSTATO")
	public String getDescrStato() {
		return descrStato;
	}

	public void setDescrStato(String descrStato) {
		this.descrStato = descrStato;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CfgEventi [codiceEvento=");
		builder.append(codiceEvento);
		builder.append(", datievento=");
		builder.append(datiEvento);
		builder.append(", dataUltimaEsecuzione=");
		builder.append(dataUltimaEsecuz);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", numerotentativi=");
		builder.append(numeroTentativi);
		builder.append(", descrizioneStato=");
		builder.append(descrStato);
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
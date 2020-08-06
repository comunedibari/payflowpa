package it.tasgroup.idp.gateway;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the FLUSSI_NDP database table.
 * 
 */
@Entity
@Table(name="FLUSSI_NDP")
@NamedQueries({
	@NamedQuery(name="FlussiNdp.findAll", query="SELECT f FROM FlussiNdp f"),
	@NamedQuery(name="FlussiNdp.findIdentificativoFlusso", 
		query="SELECT f FROM FlussiNdp f where f.identificativoFlusso = :identificativoFlusso"),
	@NamedQuery(name="FlussiNdp.findExistingFlussoNdp", 
	query="SELECT f FROM FlussiNdp f where f.identificativoFlusso = :identificativoFlusso and identificativoPsp=:identificativoPsp and anno=:anno " ),
	@NamedQuery(name="FlussiNdp.findExistingFlussoNdp1.7", 
	query="SELECT f FROM FlussiNdp f where f.identificativoFlusso = :identificativoFlusso and anno=:anno " )

})


public class FlussiNdp extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Timestamp dataOraFlusso;
	private String identificativoFlusso;
	private String identificativoDominio;
	private String identificativoPsp; // default, per ora non gestito
	private String stato;
	private Long anno;

	public FlussiNdp() {
	}

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="flussiNdp_pk_gen")	
	@SequenceGenerator(
	    name="flussiNdp_pk_gen",
	    sequenceName="FLUSSI_NDP_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}	
	
	@Column(name="DATA_ORA_FLUSSO")
	public Timestamp getDataOraFlusso() {
		return this.dataOraFlusso;
	}

	public void setDataOraFlusso(Timestamp dataOraFlusso) {
		this.dataOraFlusso = dataOraFlusso;
	}


	@Column(name="IDENTIFICATIVO_FLUSSO")
	public String getIdentificativoFlusso() {
		return this.identificativoFlusso;
	}

	public void setIdentificativoFlusso(String identificativoFlusso) {
		this.identificativoFlusso = identificativoFlusso;
	}

	@Column(name="IDENTIFICATIVO_PSP")
	public String getIdentificativoPsp() {
		return identificativoPsp;
	}


	public void setIdentificativoPsp(String identificativoPsp) {
		this.identificativoPsp = identificativoPsp;
	}



	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Long getAnno() {
		return anno;
	}

	public void setAnno(Long anno) {
		this.anno = anno;
	}

	@Column(name="ID_DOMINIO")
	public String getIdentificativoDominio() {
		return identificativoDominio;
	}

	public void setIdentificativoDominio(String identificativoDominio) {
		this.identificativoDominio = identificativoDominio;
	}





}
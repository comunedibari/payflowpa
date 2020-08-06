package it.tasgroup.iris.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the UNIPG_ANAG_CORSI_DOTT database table.
 * 
 */
@Entity
@Table(name="UNIPG_ANAG_CORSI_DOTT")
@NamedQueries(
{
	@NamedQuery(name="listaCorsiDottorato", 
			query="select p from AnagraficaCorsiDottorato p order by p.descrizione asc")
})

public class AnagraficaCorsiDottorato extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COD_CORSO")
	private String codCorso;

	
	@Column(name="COD_TASSA")
	private String codTassa;

	@Column(name="DATA_SCADENZA")
	private Date dataScadenza;

	@Column(name="IMPORTO")
	BigDecimal importo;

	@Column(name="DESCRIZIONE")
	private String descrizione;

	@Column(name="VISIBILE")
	private String visibile;

    public AnagraficaCorsiDottorato() {
    }

	public String getCodTassa() {
		return codTassa;
	}

	public void setCodTassa(String codTassa) {
		this.codTassa = codTassa;
	}

	public String getCodCorso() {
		return codCorso;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getVisibile() {
		return visibile;
	}

	public void setCodCorso(String codCorso) {
		this.codCorso = codCorso;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public void setVisibile(String visibile) {
		this.visibile = visibile;
	}

}

package it.tasgroup.idp.domain.enti;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import it.tasgroup.idp.domain.BaseEntity;
@Entity
@Table(name="JLTENTP")
public class TipologiaEnti extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*** Persistent Properties ***/
	private String tipoEnte;    
	private String descrizione;    
	private String stato;    
	private int prVersione;

            
    @Id
    @Column(name="TP_ENTE")
    public String getTipoEnte() {
		return tipoEnte;
	}
	public void setTipoEnte(String tipoEnte) {
		this.tipoEnte = tipoEnte;
	}

	@Column(name="DE_ENTE")
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name="STATO")
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name="PR_VERSIONE")
	public int getPrVersione() {
		return prVersione;
	}
	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}

}

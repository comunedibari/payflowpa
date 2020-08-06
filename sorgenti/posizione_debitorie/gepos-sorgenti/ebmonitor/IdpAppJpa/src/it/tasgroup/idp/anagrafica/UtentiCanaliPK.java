package it.tasgroup.idp.anagrafica;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the GEC_UTENTI_CANALI database table.
 * 
 */
@Embeddable
public class UtentiCanaliPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String idUtente;
	private Long idCanale;

    public UtentiCanaliPK() {
    }


	@Column(name="ID_UTENTE")
    public String getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	
	@Column(name="ID_CANALE")
	public Long getIdCanale() {
		return idCanale;
	}


	public void setIdCanale(Long idCanale) {
		this.idCanale = idCanale;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UtentiCanaliPK)) {
			return false;
		}
		UtentiCanaliPK castOther = (UtentiCanaliPK)other;
		return 
			this.idUtente.equals(castOther.idUtente)
			&& this.idCanale.equals(castOther.idCanale);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idUtente.hashCode();
		hash = hash * prime + this.idCanale.hashCode();
		
		return hash;
    }
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UtentiCanaliPK [idUtente=");
		builder.append(getIdUtente());
		builder.append(", idCanale=");
		builder.append(getIdCanale());
		builder.append("]");
		return builder.toString();
	}
}
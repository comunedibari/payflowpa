package it.tasgroup.iris.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CARRELLO_GATEWAY database table.
 * 
 */
@Embeddable
public class CarrelloGatewayPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String token;
	private String idCondizione;

    public CarrelloGatewayPK() {
    }
    
    @Column(name="TOKEN")
	public String getToken() {
		return this.token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	@Column(name="ID_CONDIZIONE")
	public String getIdCondizione() {
		return this.idCondizione;
	}
	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CarrelloGatewayPK)) {
			return false;
		}
		CarrelloGatewayPK castOther = (CarrelloGatewayPK)other;
		return 
			this.token.equals(castOther.token)
			&& this.idCondizione.equals(castOther.idCondizione);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.token.hashCode();
		hash = hash * prime + this.idCondizione.hashCode();
		
		return hash;
    }
}
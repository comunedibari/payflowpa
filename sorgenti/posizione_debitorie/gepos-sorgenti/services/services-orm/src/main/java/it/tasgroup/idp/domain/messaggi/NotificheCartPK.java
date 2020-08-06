package it.tasgroup.idp.domain.messaggi;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the NOTIFICHE_CART database table.
 * 
 */
@Embeddable
public class NotificheCartPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private String e2emsgid;
	private String receiverid;
	private String receiversys;

	public String getE2emsgid() {
		return this.e2emsgid;
	}
	public void setE2emsgid(String e2emsgid) {
		this.e2emsgid = e2emsgid;
	}
	
	public String getReceiverid() {
		return this.receiverid;
	}
	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}
	
	public String getReceiversys() {
		return this.receiversys;
	}
	public void setReceiversys(String receiversys) {
		this.receiversys = receiversys;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NotificheCartPK [e2emsgid=");
		builder.append(e2emsgid);
		builder.append(", receiverid=");
		builder.append(receiverid);
		builder.append(", receiversys=");
		builder.append(receiversys);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof NotificheCartPK)) {
			return false;
		}
		NotificheCartPK castOther = (NotificheCartPK)other;
		return 
			this.e2emsgid.equals(castOther.e2emsgid)
			&& this.receiverid.equals(castOther.receiverid)
			&& this.receiversys.equals(castOther.receiversys);
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.e2emsgid.hashCode();
		hash = hash * prime + this.receiverid.hashCode();
		hash = hash * prime + this.receiversys.hashCode();		
		return hash;
    }
}
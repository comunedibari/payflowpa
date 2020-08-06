package it.tasgroup.idp.domain.messaggi;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CONFERME_CART database table.
 * 
 */
@Embeddable
public class ConfermeCartPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*** Persistent Values***/
	private String e2emsgid;
	private String senderid;
	private String sendersys;

	
	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	public String getE2emsgid() {
		return this.e2emsgid;
	}
	public void setE2emsgid(String e2emsgid) {
		this.e2emsgid = e2emsgid;
	}
	public String getSenderid() {
		return this.senderid;
	}
	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}
	public String getSendersys() {
		return this.sendersys;
	}
	public void setSendersys(String sendersys) {
		this.sendersys = sendersys;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConfermeCartPK [e2emsgid=");
		builder.append(e2emsgid);
		builder.append(", senderid=");
		builder.append(senderid);
		builder.append(", sendersys=");
		builder.append(sendersys);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ConfermeCartPK)) {
			return false;
		}
		ConfermeCartPK castOther = (ConfermeCartPK)other;
		return 
			this.e2emsgid.equals(castOther.e2emsgid)
			&& this.senderid.equals(castOther.senderid)
			&& this.sendersys.equals(castOther.sendersys);
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.e2emsgid.hashCode();
		hash = hash * prime + this.senderid.hashCode();
		hash = hash * prime + this.sendersys.hashCode();		
		return hash;
    }
}
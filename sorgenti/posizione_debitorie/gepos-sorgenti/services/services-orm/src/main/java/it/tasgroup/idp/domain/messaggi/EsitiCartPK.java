package it.tasgroup.idp.domain.messaggi;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EsitiCartPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/*** Persistent Values***/
	private String e2emsgid;
	private String senderid;
	private String sendersys;
	
	
	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Column(insertable=false, updatable=false)
	public String getE2emsgid() {
		return this.e2emsgid;
	}
	public void setE2emsgid(String e2emsgid) {
		this.e2emsgid = e2emsgid;
	}

	@Column(insertable=false, updatable=false)
	public String getSenderid() {
		return this.senderid;
	}
	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}

	@Column(insertable=false, updatable=false)
	public String getSendersys() {
		return this.sendersys;
	}
	public void setSendersys(String sendersys) {
		this.sendersys = sendersys;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EsitiCartPK [e2emsgid=");
		builder.append(e2emsgid);
		builder.append(", senderid=");
		builder.append(senderid);
		builder.append(", sendersys=");
		builder.append(sendersys);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if ( ! (o instanceof EsitiCartPK)) {
			return false;
		}
		EsitiCartPK other = (EsitiCartPK) o;
		return this.e2emsgid.equals(other.e2emsgid)
			&& this.senderid.equals(other.senderid)
			&& this.sendersys.equals(other.sendersys);
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

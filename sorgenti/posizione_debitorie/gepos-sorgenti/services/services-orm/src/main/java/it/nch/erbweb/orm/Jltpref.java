package it.nch.erbweb.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="JLTPREF")
public class Jltpref implements java.io.Serializable {

	/*** PK Reference ***/
	private JltprefId jltprefId;
	
	private String valore;

	public Jltpref() {
	}

	public Jltpref(JltprefId jltprefId, String valore) {
		this.jltprefId = jltprefId;
		this.valore = valore;
	}

	@Id
	public JltprefId getJltprefId() {
		return this.jltprefId;
	}
	public void setJltprefId(JltprefId jltprefId) {
		this.jltprefId = jltprefId;
	}

	@Column(name="VALORE", nullable=false)
	public String getValore() {
		return this.valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((jltprefId == null) ? 0 : jltprefId.hashCode());
		result = prime * result + ((valore == null) ? 0 : valore.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jltpref other = (Jltpref) obj;
		if (jltprefId == null) {
			if (other.jltprefId != null)
				return false;
		} else if (!jltprefId.equals(other.jltprefId))
			return false;
		if (valore == null) {
			if (other.valore != null)
				return false;
		} else if (!valore.equals(other.valore))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Jltpref [jltprefId=");
		builder.append(jltprefId);
		builder.append(", valore=");
		builder.append(valore);
		builder.append("]");
		return builder.toString();
	}

	
}

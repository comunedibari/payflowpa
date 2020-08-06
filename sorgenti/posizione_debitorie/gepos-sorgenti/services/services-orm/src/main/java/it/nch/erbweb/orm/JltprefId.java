package it.nch.erbweb.orm;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class JltprefId implements java.io.Serializable {

	private String categoria;
	private String intestatario;
	private String operatore;
	private String tiposervizio;
	private String proprieta;

	public JltprefId() {
	}

	public JltprefId(String categoria, String intestatario, String operatore,
			String tiposervizio, String proprieta) {
		this.categoria = categoria;
		this.intestatario = intestatario;
		this.operatore = operatore;
		this.tiposervizio = tiposervizio;
		this.proprieta = proprieta;
	}

	@Column(name="CATEGORIA")
	public String getCategoria() {
		return this.categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Column(name="INTESTATARIO")
	public String getIntestatario() {
		return this.intestatario;
	}
	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}

	@Column(name="OPERATORE")
	public String getOperatore() {
		return this.operatore;
	}
	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	@Column(name="TIPOSERVIZIO")
	public String getTiposervizio() {
		return this.tiposervizio;
	}
	public void setTiposervizio(String tiposervizio) {
		this.tiposervizio = tiposervizio;
	}

	@Column(name="PROPRIETA")
	public String getProprieta() {
		return this.proprieta;
	}
	public void setProprieta(String proprieta) {
		this.proprieta = proprieta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result
				+ ((intestatario == null) ? 0 : intestatario.hashCode());
		result = prime * result
				+ ((operatore == null) ? 0 : operatore.hashCode());
		result = prime * result
				+ ((proprieta == null) ? 0 : proprieta.hashCode());
		result = prime * result
				+ ((tiposervizio == null) ? 0 : tiposervizio.hashCode());
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
		JltprefId other = (JltprefId) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (intestatario == null) {
			if (other.intestatario != null)
				return false;
		} else if (!intestatario.equals(other.intestatario))
			return false;
		if (operatore == null) {
			if (other.operatore != null)
				return false;
		} else if (!operatore.equals(other.operatore))
			return false;
		if (proprieta == null) {
			if (other.proprieta != null)
				return false;
		} else if (!proprieta.equals(other.proprieta))
			return false;
		if (tiposervizio == null) {
			if (other.tiposervizio != null)
				return false;
		} else if (!tiposervizio.equals(other.tiposervizio))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JltprefId [categoria=");
		builder.append(categoria);
		builder.append(", intestatario=");
		builder.append(intestatario);
		builder.append(", operatore=");
		builder.append(operatore);
		builder.append(", tiposervizio=");
		builder.append(tiposervizio);
		builder.append(", proprieta=");
		builder.append(proprieta);
		builder.append("]");
		return builder.toString();
	}
	
}

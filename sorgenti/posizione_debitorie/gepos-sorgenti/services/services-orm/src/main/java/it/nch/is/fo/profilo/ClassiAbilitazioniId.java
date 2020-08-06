package it.nch.is.fo.profilo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class ClassiAbilitazioniId implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeClasse;
	private String applicazione;
	private String funzione;

	public ClassiAbilitazioniId() {
	}

	public ClassiAbilitazioniId(String nomeclasse, String applicazione, String funzione) {
		this.nomeClasse = nomeclasse;
		this.applicazione = applicazione;
		this.funzione = funzione;
	}

	@Column(name="NOMECLASSE")
	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

	@Column(name="APPLICAZIONE")
	public String getApplicazione() {
		return applicazione;
	}

	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
	}

	@Column(name="FUNZIONE")
	public String getFunzione() {
		return funzione;
	}

	public void setFunzione(String funzione) {
		this.funzione = funzione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicazione == null) ? 0 : applicazione.hashCode());
		result = prime * result + ((funzione == null) ? 0 : funzione.hashCode());
		result = prime * result + ((nomeClasse == null) ? 0 : nomeClasse.hashCode());
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
		ClassiAbilitazioniId other = (ClassiAbilitazioniId) obj;
		if (applicazione == null) {
			if (other.applicazione != null)
				return false;
		} else if (!applicazione.equals(other.applicazione))
			return false;
		if (funzione == null) {
			if (other.funzione != null)
				return false;
		} else if (!funzione.equals(other.funzione))
			return false;
		if (nomeClasse == null) {
			if (other.nomeClasse != null)
				return false;
		} else if (!nomeClasse.equals(other.nomeClasse))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClassiAbilitazioniId [nomeClasse=" + nomeClasse + ", applicazione=" + applicazione + ", funzione=" + funzione + "]";
	}

}

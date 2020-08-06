package it.tasgroup.idp.pojo;

public class UtenteIris {
	private String idUtente;
	private String email;
	private String scopoMessaggio;
	private boolean subscribe;
	

	public String getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getScopoMessaggio() {
		return scopoMessaggio;
	}

	public void setScopoMessaggio(String scopoMessaggio) {
		this.scopoMessaggio = scopoMessaggio;
	}

	public boolean isSubscribe() {
		return subscribe;
	}

	public void setSubscribe(boolean subscribe) {
		this.subscribe = subscribe;
	}

}

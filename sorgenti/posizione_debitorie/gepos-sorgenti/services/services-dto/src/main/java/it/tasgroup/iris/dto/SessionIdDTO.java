package it.tasgroup.iris.dto;

import java.io.Serializable;

public class SessionIdDTO implements Serializable{
	private String idSistema;
	private String idTerminale;
	private String token;
	private String dataOraAccesso;
	private boolean weakEqualityCheck;
	
	public SessionIdDTO(){};
	
	public SessionIdDTO(String idSistema, String idTerminale, String token, String dataOraAccesso) {
		this.idSistema = idSistema;
		this.idTerminale = idTerminale;
		this.token = token;
		this.dataOraAccesso = dataOraAccesso;
	}

	public String getIdSistema() {
		return idSistema;
	}
	
	public void setIdSistema(String idSistema) {
		this.idSistema = idSistema;
	}
	
	public String getIdTerminale() {
		return idTerminale;
	}
	
	public void setIdTerminale(String idTerminale) {
		this.idTerminale = idTerminale;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getDataOraAccesso() {
		return dataOraAccesso;
	}
	
	public void setDataOraAccesso(String dataOraAccesso) {
		this.dataOraAccesso = dataOraAccesso;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		SessionIdDTO other = (SessionIdDTO) obj;
		
		// PAZZIK 24-09-2013
		//nelle fasi di Notifica e Cancellazione potremmo non avere 
		// altra informazione che il TOKEN, per cui ocorre un controllo 
		// più lasco
		if (this.isWeakEqualityCheck() || other.isWeakEqualityCheck())
			return equalsWeakly(obj);
		
		return (this.idSistema.equals(other.idSistema)
				&& this.idTerminale.equals(other.idTerminale)
				&& this.token.equals(other.token)
				&& this.dataOraAccesso.equals(other.dataOraAccesso));
			
	}
	
	
	public boolean equalsWeakly(Object obj) {
		
		SessionIdDTO other = (SessionIdDTO) obj;
		
		return (this.token.equals(other.token));
			
	}

	@Override
	public String toString() {
		return "SessionIdDTO [idSistema=" + idSistema + ", idTerminale=" + idTerminale + ", Token=" + token + ", DataOraAccesso=" + dataOraAccesso + "]";
	}

	public boolean isWeakEqualityCheck() {
		return weakEqualityCheck;
	}

	public void setWeakEqualityCheck(boolean weakEqualityCheck) {
		this.weakEqualityCheck = weakEqualityCheck;
	}

	
}

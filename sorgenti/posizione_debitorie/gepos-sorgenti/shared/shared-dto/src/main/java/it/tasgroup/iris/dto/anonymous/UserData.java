package it.tasgroup.iris.dto.anonymous;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang.WordUtils;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

@ManagedBean(name = "user")
@SessionScoped
public class UserData implements Serializable {
	
	private String irisSessionId;
	private String ragioneSociale;
	private String codiceFiscale;
	private String email;
	private boolean singleProfile = true;

	public UserData() {

	}
	
	
	
	public String getIrisSessionId() {
		return irisSessionId;
	}



	public void setIrisSessionId(String irisSessionId) {
		this.irisSessionId = irisSessionId;
	}

	public void setSingleProfile(boolean singleProfile) {
		this.singleProfile = singleProfile;
	}

	public boolean getSingleProfile() {
		return singleProfile;
	}

	public boolean isSSOAccess() {
		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("authentication.properties");
		return Boolean.valueOf(cpl.getProperty("sso.login"));
	}
	
	public boolean isSingleProfile() {
		return singleProfile;
	}
	
	public boolean isAuthenticated() {
		return irisSessionId != null;
	}
	
	public UserData(String codiceFiscale, String email, String ragioneSociale) {
		this.codiceFiscale = codiceFiscale;
		this.email = email;
        ragioneSociale = ragioneSociale.toLowerCase();
        ragioneSociale = WordUtils.capitalizeFully(ragioneSociale);
        this.ragioneSociale = ragioneSociale;
	}
	
	

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

package it.tasgroup.iris.sso.manager;

import java.util.List;

public class SSOUser {

	private String ssoName=null;
	private String ssoSurname=null;
	private String ssoCodiceFiscale=null;
	private List<SSOUser> ssoDeleganti = null;
		
	public String getSSOName() {
		return ssoName;
	}
	public void setSSOName(String ssoName) {
		this.ssoName = ssoName;
	}
	public String getSSOSurname() {
		return ssoSurname;
	}
	public void setSSOSurname(String ssoSurname) {
		this.ssoSurname = ssoSurname;
	}
	public String getSSOCodiceFiscale() {
		return ssoCodiceFiscale;
	}
	public void setSSOCodiceFiscale(String ssoCodiceFiscale) {
		this.ssoCodiceFiscale = ssoCodiceFiscale;
	}
	public List<SSOUser> getSSODeleganti() {
		return ssoDeleganti;
	}
	public void setSSODeleganti(List<SSOUser> ssoDeleganti) {
		this.ssoDeleganti = ssoDeleganti;
	}
	
	@Override
	public String toString() {
		return "SSOUser [ssoName=" + ssoName + ", ssoSurname=" + ssoSurname
				+ ", ssoCodiceFiscale=" + ssoCodiceFiscale + ", ssoDeleganti="
				+ ssoDeleganti + "]";
	}
	
	
	
}

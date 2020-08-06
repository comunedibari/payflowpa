package it.nch.is.fo.gateway;


import org.apache.struts.validator.ValidatorForm;


public class GatewayContainerForm extends ValidatorForm
{
	private String codFiscale;
		
	private String email;
	
	private String emailConfirm;
	  
	
	public String getCodFiscale() {
		return codFiscale;
	}
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailConfirm() {
		return emailConfirm;
	}
	public void setEmailConfirm(String emailConfirm) {
		this.emailConfirm = emailConfirm;
	}
}

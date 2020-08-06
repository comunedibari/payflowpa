package it.tasgroup.idp.rs.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RispostaAperturaSessioneGateway {
	
	private String token;
	private String urlGateway;
	
	public RispostaAperturaSessioneGateway() {
		
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUrlGateway() {
		return urlGateway;
	}

	public void setUrlGateway(String urlGateway) {
		this.urlGateway = urlGateway;
	}

}

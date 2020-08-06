package it.tasgroup.idp.util;

public class ServiceLocation {
	private String context;
	private String pkgs;
	private String provider;
	private boolean defined;
	
	private String securityPrincipal;
	private String securityCredentials;
	private String clientEjbContext;
	
	
	
	public String getSecurityPrincipal() {
		return securityPrincipal;
	}
	public void setSecurityPrincipal(String securityPrincipal) {
		this.securityPrincipal = securityPrincipal;
	}
	public String getSecurityCredentials() {
		return securityCredentials;
	}
	public void setSecurityCredentials(String securityCredentials) {
		this.securityCredentials = securityCredentials;
	}
	public String getClientEjbContext() {
		return clientEjbContext;
	}
	public void setClientEjbContext(String clientEjbContext) {
		this.clientEjbContext = clientEjbContext;
	}	
	
	public boolean isDefined() {
		if (context==null || context.isEmpty() || 
			pkgs==null || pkgs.isEmpty() || 
			provider==null || provider.isEmpty())
			return false;
		else
			return true;
	}
	public void setDefined(boolean defined) {
		this.defined = defined;
	}
	public String getContext() {
		return this.context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getPkgs() {
		return this.pkgs;
	}
	public void setPkgs(String pkgs) {
		this.pkgs = pkgs;
	}
	public String getProvider() {
		return this.provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
}

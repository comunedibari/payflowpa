/**
 * 
 */
package it.tasgroup.iris.dto.gateway;

import java.io.Serializable;

/**
 * @author pazzik
 *
 */
public class IrisGatewayClientDTO implements Serializable{
	
	private String applicationId;
	
	private String systemId;
	
	private boolean authenticated;
	
	public boolean isAuthenticated() {
		return authenticated;
	}
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}

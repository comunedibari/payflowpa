package it.regioneveneto.mygov.payment.mypivot.domain.dto.myprofile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TenantDto {
	
	private String tenantCode;
    private String tenantDescription;
	public String getTenantCode() {
		return tenantCode;
	}
	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}
	public String getTenantDescription() {
		return tenantDescription;
	}
	public void setTenantDescription(String tenantDescription) {
		this.tenantDescription = tenantDescription;
	}
    
    
    
    
}

package it.regioneveneto.mygov.payment.mypivot.domain.dto.myprofile;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TenantsDto {

	private String message;
	private List<TenantDto> resultTenants;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<TenantDto> getResultTenants() {
		return resultTenants;
	}

	public void setResultTenants(List<TenantDto> resultTenants) {
		this.resultTenants = resultTenants;
	}

}

/**
 * 
 */
package it.tasgroup.iris.dto.ddp;

import java.io.Serializable;

/**
 * @author PazziK
 *
 */
public abstract class DettaglioDTO implements Serializable {
	
	private String id;
	
	private String cfIntestatarioPendenza;
	
	private String cfPagante;
	
	private String emailPagante;
	
	private String beneficiario;

	private String coordinateBancarie;
	
	private Long idDocRepository;
	
	
	public abstract String getFormattedID();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Long getIdDocRepository() {
		return idDocRepository;
	}

	public void setIdDocRepository(Long idDocRepository) {
		this.idDocRepository = idDocRepository;
	}

	public String getCfIntestatarioPendenza() {
		return cfIntestatarioPendenza;
	}

	public void setCfIntestatarioPendenza(String cfIntestatarioPendenza) {
		this.cfIntestatarioPendenza = cfIntestatarioPendenza;
	}

	public String getCfPagante() {
		return cfPagante;
	}

	public void setCfPagante(String cfPagante) {
		this.cfPagante = cfPagante;
	}
	
	public String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public String getCoordinateBancarie() {
		return coordinateBancarie;
	}

	public void setCoordinateBancarie(String coordinateBancarie) {
		this.coordinateBancarie = coordinateBancarie;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("id="+this.getId());
		sb.append(", beneficiario="+this.getBeneficiario());
		sb.append(", cfPagante="+this.getCfPagante());		
		sb.append(", cfIntestatarioPendenza="+this.getCfIntestatarioPendenza());	
		sb.append(", coordinateBancarie="+this.getCoordinateBancarie());
		return sb.toString();
	}

	public String getEmailPagante() {
		return emailPagante;
	}

	public void setEmailPagante(String emailPagante) {
		this.emailPagante = emailPagante;
	}
}

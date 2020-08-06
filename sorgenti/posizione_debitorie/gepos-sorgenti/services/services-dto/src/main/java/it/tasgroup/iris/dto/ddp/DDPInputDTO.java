package it.tasgroup.iris.dto.ddp;

import it.tasgroup.services.util.enumeration.EnumTipoDDP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DDPInputDTO implements Serializable{
	
	private String operatorUsername;
	
	private String loggedIntestatario;
	
	private String cfPagante;
	
	private String emailPagante;
	
	private List<String> condizioniCarrello = new ArrayList<String>();
	
	private EnumTipoDDP tipo;
	
	private long idGateway;
	
	private String codEnte;
	
	private String tipoDocumento;
	
	private Boolean includiRTAgid;
	
	
	public long getIdGateway() {
		return idGateway;
	}

	public void setIdGateway(long idGateway) {
		this.idGateway = idGateway;
	}
	
	public EnumTipoDDP getTipo() {
		return tipo;
	}

	public void setTipo(EnumTipoDDP tipo) {
		this.tipo = tipo;
	}

	public List<String> getCondizioniCarrello() {
		return condizioniCarrello;
	}

	public void setCondizioniCarrello(List<String> condizioniCarrello) {
		this.condizioniCarrello = condizioniCarrello;
	}
	
	public boolean addCondizioneCarrello(String condizione){
		return condizioniCarrello.add(condizione);
	}

	public String getOperatorUsername() {
		return operatorUsername;
	}

	public void setOperatorUsername(String operatorUsername) {
		this.operatorUsername = operatorUsername;
	}

	public String getLoggedIntestatario() {
		return loggedIntestatario;
	}

	public void setLoggedIntestatario(String loggedIntestatario) {
		this.loggedIntestatario = loggedIntestatario;
	}

	public String getCfPagante() {
		return cfPagante;
	}

	public void setCfPagante(String cfPagante) {
		this.cfPagante = cfPagante;
	}

	public String getEmailPagante() {
		return emailPagante;
	}

	public void setEmailPagante(String emailPagante) {
		this.emailPagante = emailPagante;
	}

	public String getCodEnte() {
		return codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public boolean isIncludiRTAgid() {
		return includiRTAgid!= null && includiRTAgid;
	}

	public void setIncludiRTAgid(Boolean incudiRTAgid) {
		this.includiRTAgid = incudiRTAgid;
	}	

}

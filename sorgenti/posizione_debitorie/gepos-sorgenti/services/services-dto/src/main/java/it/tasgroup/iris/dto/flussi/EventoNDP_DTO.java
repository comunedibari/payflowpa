/**
 * 
 */
package it.tasgroup.iris.dto.flussi;

import it.tasgroup.services.util.enumeration.EnumTipoEventiNDP;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author pazzik
 *
 */
public class EventoNDP_DTO implements Serializable {
	
	
	private String idEvento;
	private String idDominio;
	private String iuv;
	private String codContesto;
	private String idPSP;
	private String intermediarioPA;
	private Timestamp data;
	private String idErogatore;
	private String idFruitore;
	private EnumTipoEventiNDP tipo;
	private String sottoTipo;
	private String categoria;
	private String idEgov;
	private boolean gde;
	private String componente;
	private String canalePagamento;
	private String tipoVersamento;
	private String esito;
	private String interfaccia;
	private String faultString;
	private String faultCode;
	private String faultSerial;
	private String faultID;
	private String faultDescr;
	
	private String originalFaultCode; 
 	private String originalFaultString; 
 	private String originalFaultDescr; 
	
	public String getIdDominio() {
		return idDominio;
	}
	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}
	public String getIuv() {
		return iuv;
	}
	public void setIuv(String iuv) {
		this.iuv = iuv;
	}
	public String getCodContesto() {
		return codContesto;
	}
	public void setCodContesto(String codContesto) {
		this.codContesto = codContesto;
	}
	public String getIdPSP() {
		return idPSP;
	}
	public void setIdPSP(String idPSP) {
		this.idPSP = idPSP;
	}
	
	public String getIntermediarioPA() {
		return intermediarioPA;
	}
	public void setIntermediarioPA(String intermediarioPA) {
		this.intermediarioPA = intermediarioPA;
	}
	public String getIdErogatore() {
		return idErogatore;
	}
	public void setIdErogatore(String idErogatore) {
		this.idErogatore = idErogatore;
	}
	public String getIdFruitore() {
		return idFruitore;
	}
	public void setIdFruitore(String idFruitore) {
		this.idFruitore = idFruitore;
	}
	public String getFaultString() {
		return faultString;
	}
	public void setFaultString(String faultString) {
		this.faultString = faultString;
	}
	public String getFaultCode() {
		return faultCode;
	}
	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}
	public String getFaultSerial() {
		return faultSerial;
	}
	public void setFaultSerial(String faultSerial) {
		this.faultSerial = faultSerial;
	}
	public String getFaultID() {
		return faultID;
	}
	public void setFaultID(String faultID) {
		this.faultID = faultID;
	}
	public String getFaultDescr() {
		return faultDescr;
	}
	public void setFaultDescr(String faultDescr) {
		this.faultDescr = faultDescr;
	}
	public String getOriginalFaultCode() { 
		return originalFaultCode; 
	} 
	public void setOriginalFaultCode(String originalFaultCode) { 
		this.originalFaultCode = originalFaultCode; 
	} 
	public String getOriginalFaultString() { 
		return originalFaultString; 
	} 
	public void setOriginalFaultString(String originalFaultString) { 
		this.originalFaultString = originalFaultString; 
	} 
	public String getOriginalFaultDescr() { 
		return originalFaultDescr; 
	} 
	public void setOriginalFaultDescr(String originalFaultDescr) { 
		this.originalFaultDescr = originalFaultDescr; 
	} 
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
	public String getIdEgov() {
		return idEgov;
	}
	public void setIdEgov(String gde) {
		this.idEgov = gde;
	}
	public String getComponente() {
		return componente;
	}
	public void setComponente(String componente) {
		this.componente = componente;
	}
	public String getInterfaccia() {
		return interfaccia;
	}
	public void setInterfaccia(String parametriInterfaccia) {
		this.interfaccia = parametriInterfaccia;
	}
	public EnumTipoEventiNDP getTipo() {
		return tipo;
	}
	public void setTipo(EnumTipoEventiNDP tipo) {
		this.tipo = tipo;
	}
	public String getSottoTipo() {
		return sottoTipo;
	}
	public void setSottoTipo(String sottoTipo) {
		this.sottoTipo = sottoTipo;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}
	public String getCanalePagamento() {
		return canalePagamento;
	}
	public void setCanalePagamento(String canalePagamento) {
		this.canalePagamento = canalePagamento;
	}
	public String getTipoVersamento() {
		return tipoVersamento;
	}
	public void setTipoVersamento(String tipoVersamento) {
		this.tipoVersamento = tipoVersamento;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public boolean isGde() {
		return gde;
	}
	public void setGde(boolean gde) {
		this.gde = gde;
	}

}

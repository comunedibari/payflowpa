package it.nch.idp.backoffice.monitoraggioflussi;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.validator.ValidatorActionForm;

public class RicercaEventiNDPForm extends ValidatorActionForm{
    
    private static final long serialVersionUID = 1L;
    
    private String dataDaGG;
    private String dataDaMM;
    private String dataDaYY;
    private String dataAGG;
    private String dataAMM;
    private String dataAYY;
    
    private String idDominio;
    private String iuv;
    private String codContesto;
    private String idPSP;
    
    private String tipoEvento;

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

	public String getDataDaGG() {
		return dataDaGG;
	}

	public void setDataDaGG(String dataDaGG) {
		this.dataDaGG = dataDaGG;
	}

	public String getDataDaMM() {
		return dataDaMM;
	}

	public void setDataDaMM(String dataDaMM) {
		this.dataDaMM = dataDaMM;
	}

	public String getDataAGG() {
		return dataAGG;
	}

	public void setDataAGG(String dataAGG) {
		this.dataAGG = dataAGG;
	}

	public String getDataAMM() {
		return dataAMM;
	}

	public void setDataAMM(String dataAMM) {
		this.dataAMM = dataAMM;
	}

	public String getDataAYY() {
		return dataAYY;
	}

	public void setDataAYY(String dataAYY) {
		this.dataAYY = dataAYY;
	}

	public String getDataDaYY() {
		return dataDaYY;
	}

	public void setDataDaYY(String dataDaYY) {
		this.dataDaYY = dataDaYY;
	}

	public String getIdPSP() {
		return idPSP;
	}

	public void setIdPSP(String idPSP) {
		this.idPSP = idPSP;
	}
	public String getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getIdDominio() {
		return idDominio;
	}

	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}
	
	public Boolean checkRequiredFieldsFlussi() {
		
		if (StringUtils.isEmpty(idDominio) && StringUtils.isEmpty(iuv) && StringUtils.isEmpty(iuv) && StringUtils.isEmpty(dataAMM) && StringUtils.isEmpty(dataAYY) && StringUtils.isEmpty(dataDaGG) && StringUtils.isEmpty(dataDaMM) && StringUtils.isEmpty(dataDaYY))
		
			return false;
					
		return true;
		
	}
}

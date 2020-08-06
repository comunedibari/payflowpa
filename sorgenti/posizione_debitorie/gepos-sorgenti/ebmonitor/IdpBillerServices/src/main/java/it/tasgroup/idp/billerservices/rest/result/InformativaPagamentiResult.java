
package it.tasgroup.idp.billerservices.rest.result;

import java.util.Date;

public class InformativaPagamentiResult {
	
	private final static long serialVersionUID = 1L;

	protected String systemId;
	protected String systemSys;
    protected Long idTrasmissione;
    protected String tipoNotifica;
    protected String dataCreazione;

    public String getSystemId() {
        return systemId;
    }
    public void setSystemId(String value) {
        this.systemId = value;
    }

    public String getSystemSys() {
        return systemSys;
    }
    public void setSystemSys(String value) {
        this.systemSys = value;
    }

    public long getIdTrasmissione() {
        return idTrasmissione;
    }
    public void setIdTrasmissione(Long value) {
        this.idTrasmissione = value;
    }

    public String getTipoNotifica() {
        return tipoNotifica;
    }
    public void setTipoNotifica(String value) {
        this.tipoNotifica = value;
    }

    public String getDataCreazione() {
        return dataCreazione;
    }
    public void setDataCreazione(String value) {
        this.dataCreazione = value;
    }

}

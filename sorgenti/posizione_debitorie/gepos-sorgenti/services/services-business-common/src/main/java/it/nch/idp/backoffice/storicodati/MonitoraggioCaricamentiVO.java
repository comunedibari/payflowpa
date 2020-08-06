package it.nch.idp.backoffice.storicodati;

import java.io.Serializable;
import java.sql.Timestamp;


public class MonitoraggioCaricamentiVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String nomeTabella;
	private int recordId;
	private Timestamp dataOraOperazione;
	private String tipoOperazione;
	private String tipoMessaggio;
	private String infoOperazione;
	
	public MonitoraggioCaricamentiVO() {
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNomeTabella() {
		return nomeTabella;
	}
	public void setNomeTabella(String nomeTabella) {
		this.nomeTabella = nomeTabella;
	}
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	public Timestamp getDataOraOperazione() {
		return dataOraOperazione;
	}
	public void setDataOraOperazione(Timestamp dataOraOperazione) {
		this.dataOraOperazione = dataOraOperazione;
	}
	public String getTipoOperazione() {
		return tipoOperazione;
	}
	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}
	public String getTipoMessaggio() {
		return tipoMessaggio;
	}
	public void setTipoMessaggio(String tipoMessaggio) {
		this.tipoMessaggio = tipoMessaggio;
	}
	public String getInfoOperazione() {
		return infoOperazione;
	}
	public void setInfoOperazione(String infoOperazione) {
		this.infoOperazione = infoOperazione;
	}
	
	
}

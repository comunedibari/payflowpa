package it.tasgroup.iris.dto.flussi;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class PrenotazioniDTO implements Serializable {

	private Long id;	
	private Timestamp dataPrenotazione;
	private String stato;
	private String tipoDato;
	private String tipoEsportazioni;
	private String cfOperatore;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Timestamp getDataPrenotazione() {
		return dataPrenotazione;
	}
	public void setDataPrenotazione(Timestamp dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
	}
	public String getTipoDato() {
		return tipoDato;
	}
	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}
    public String getCfOperatore() {
        return cfOperatore;
    }
    public void setCfOperatore(String cfOperatore) {
        this.cfOperatore = cfOperatore;
    }
    public Long getIdToRemove() {
        return getId();
    }
	public String getTipoEsportazioni() {
		return tipoEsportazioni;
	}
	public void setTipoEsportazioni(String tipoEsportazioni) {
		this.tipoEsportazioni = tipoEsportazioni;
	}
}

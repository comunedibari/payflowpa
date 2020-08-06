package it.tasgroup.idp.cart.core.model;

import java.io.Serializable;
import java.util.Date;

public class MessaggioModel implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	private TipoMessaggio tipo;
	private String soggetto;
	private String sil;
	private String msgId;
	private Date dataCreazione;
	private Date dataUltimaConsegnaRichiesta;
	private boolean richiestaConsegnata;
	private Date dataUltimaConsegnaEsito;
	private boolean esitoConsegnato;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TipoMessaggio getTipo() {
		return tipo;
	}
	public void setTipo(TipoMessaggio tipo) {
		this.tipo = tipo;
	}
	public String getSoggetto() {
		return soggetto;
	}
	public void setSoggetto(String soggetto) {
		this.soggetto = soggetto;
	}
	public String getSil() {
		return sil;
	}
	public void setSil(String sil) {
		this.sil = sil;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public Date getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public Date getDataUltimaConsegnaRichiesta() {
		return dataUltimaConsegnaRichiesta;
	}
	public void setDataUltimaConsegnaRichiesta(Date dataUltimaConsegnaRichiesta) {
		this.dataUltimaConsegnaRichiesta = dataUltimaConsegnaRichiesta;
	}
	public boolean isRichiestaConsegnata() {
		return richiestaConsegnata;
	}
	public void setRichiestaConsegnata(boolean richiestaConsegnata) {
		this.richiestaConsegnata = richiestaConsegnata;
	}
	public Date getDataUltimaConsegnaEsito() {
		return dataUltimaConsegnaEsito;
	}
	public void setDataUltimaConsegnaEsito(Date dataUltimaConsegnaEsito) {
		this.dataUltimaConsegnaEsito = dataUltimaConsegnaEsito;
	}
	public boolean isEsitoConsegnato() {
		return esitoConsegnato;
	}
	public void setEsitoConsegnato(boolean esitoConsegnato) {
		this.esitoConsegnato = esitoConsegnato;
	}
	
	
}

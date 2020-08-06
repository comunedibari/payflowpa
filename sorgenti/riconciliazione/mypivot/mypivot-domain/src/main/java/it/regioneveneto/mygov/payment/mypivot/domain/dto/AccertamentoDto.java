package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaStatoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;

/**
 * DTO per la presentazione lato Web delle informazioni caratterizzanti l'accertamento (Tabelle coinvolte: mygov_accertamento).
 * 
 * @author Marianna Memoli
 */
public class AccertamentoDto {

	/**
	 * Identificativo interno
	 */
	private String id;
	/**
	 * Anagrafica del tipo dovuto 
	 */
	private EnteTipoDovutoTO enteTipoDovuto;
	/**
	 * Anagrafica dell'utente che ha creato l'accertamento
	 */
	private UtenteTO utente;
	/**
	 * Anagrafica dello stato 
	 */
	private AnagraficaStatoTO stato;
	/**
	 * Testo descrittivo dell'accertamento
	 */
	private String deNomeAccertamento;
	/**
	 * Data creazione accertamento
	 */
	private String dtCreazione;
	/**
	 * Data di ultima modifica
	 */
	private String dtUltimaModifica;
	/**
	 * Individua se il report dell'accertamento è stato stampato almeno una volta.
	 */
	private Boolean printed;
	
	 
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the enteTipoDovuto
	 */
	public EnteTipoDovutoTO getEnteTipoDovuto() {
		return enteTipoDovuto;
	}
	/**
	 * @param enteTipoDovuto the enteTipoDovuto to set
	 */
	public void setEnteTipoDovuto(EnteTipoDovutoTO enteTipoDovuto) {
		this.enteTipoDovuto = enteTipoDovuto;
	}
	/**
	 * @return the utente
	 */
	public UtenteTO getUtente() {
		return utente;
	}
	/**
	 * @param utente the utente to set
	 */
	public void setUtente(UtenteTO utente) {
		this.utente = utente;
	}
	/**
	 * @return the stato
	 */
	public AnagraficaStatoTO getStato() {
		return stato;
	}
	/**
	 * @param stato the stato to set
	 */
	public void setStato(AnagraficaStatoTO stato) {
		this.stato = stato;
	}
	/**
	 * @return the deNomeAccertamento
	 */
	public String getDeNomeAccertamento() {
		return deNomeAccertamento;
	}
	/**
	 * @param deNomeAccertamento the deNomeAccertamento to set
	 */
	public void setDeNomeAccertamento(String deNomeAccertamento) {
		this.deNomeAccertamento = deNomeAccertamento;
	}
	/**
	 * @return the dtCreazione
	 */
	public String getDtCreazione() {
		return dtCreazione;
	}
	/**
	 * @param dtCreazione the dtCreazione to set
	 */
	public void setDtCreazione(String dtCreazione) {
		this.dtCreazione = dtCreazione;
	}
	/**
	 * @return the dtUltimaModifica
	 */
	public String getDtUltimaModifica() {
		return dtUltimaModifica;
	}
	/**
	 * @param dtUltimaModifica the dtUltimaModifica to set
	 */
	public void setDtUltimaModifica(String dtUltimaModifica) {
		this.dtUltimaModifica = dtUltimaModifica;
	}
	/**
	 * @return the printed
	 */
	public Boolean getPrinted() {
		return printed;
	}
	/**
	 * @param printed the printed to set
	 */
	public void setPrinted(Boolean printed) {
		this.printed = printed;
	}
}

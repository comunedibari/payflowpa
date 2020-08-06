/**
 * 
 */
package it.tasgroup.iris.dto;

import java.io.Serializable;

/**
 * @author pazzik
 *
 */
public class DettaglioNotificaDTO implements Serializable{
	
	private String idPagamentoCreditore;
   
	private String idRiscossione;
	
	private String notePagamento;
	
    protected AllegatiPendenzaDTO allegato;

	public String getNotePagamento() {
		return notePagamento;
	}

	public void setNotePagamento(String notePagamento) {
		this.notePagamento = notePagamento;
	}

	public String getIdPagamentoCreditore() {
		return idPagamentoCreditore;
	}

	public void setIdPagamentoCreditore(String idPagamentoCreditore) {
		this.idPagamentoCreditore = idPagamentoCreditore;
	}

	public String getIdRiscossione() {
		return idRiscossione;
	}

	public void setIdRiscossione(String idRiscossione) {
		this.idRiscossione = idRiscossione;
	}

	public AllegatiPendenzaDTO getAllegato() {
		return allegato;
	}

	public void setAllegato(AllegatiPendenzaDTO allegato) {
		this.allegato = allegato;
	}

}

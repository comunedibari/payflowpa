/**
 * 07/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.xsqlcmd.dao.UIdProvider;

/**
 * @author gdefacci
 */
public class BaseTablesUidProvider implements TablesUidProvider{
	
	private final UIdProvider allegato;
	private final UIdProvider allineamenti;
	private final UIdProvider condizioniPagamento;
	private final UIdProvider destinatari;
	private final UIdProvider flussi;
	private final UIdProvider pendenze;
	private final UIdProvider vociBilancio;
	private final UIdProvider esitiPendenza;
	private final UIdProvider erroriEsitiPendenza;
	private final UIdProvider tributiStrutturati;
	
	public BaseTablesUidProvider(UIdProvider allegato, UIdProvider allineamenti,
			UIdProvider condizioniPagamento, UIdProvider destinatari,
			UIdProvider flussi, UIdProvider pendenze, UIdProvider vociBilancio,
			UIdProvider esitiPendenza, UIdProvider erroriEsitiPendenza, UIdProvider tributiStrutturati) {
		this.allegato = allegato;
		this.allineamenti = allineamenti;
		this.condizioniPagamento = condizioniPagamento;
		this.destinatari = destinatari;
		this.flussi = flussi;
		this.pendenze = pendenze;
		this.vociBilancio = vociBilancio;
		this.esitiPendenza = esitiPendenza;
		this.erroriEsitiPendenza = erroriEsitiPendenza;
		this.tributiStrutturati=tributiStrutturati;
	}

	public UIdProvider getAllegato() {
		return allegato;
	}
	public UIdProvider getAllineamenti() {
		return allineamenti;
	}
	public UIdProvider getCondizioniPagamento() {
		return condizioniPagamento;
	}
	public UIdProvider getDestinatari() {
		return destinatari;
	}
	public UIdProvider getFlussi() {
		return flussi;
	}
	public UIdProvider getPendenze() {
		return pendenze;
	}
	public UIdProvider getVociBilancio() {
		return vociBilancio;
	}
	public UIdProvider getEsitiPendenza() {
		return esitiPendenza;
	}
	public UIdProvider getErroriEsitiPendenza() {
		return erroriEsitiPendenza;
	}
	public UIdProvider getTributiStrutturati() {
		return tributiStrutturati;
	}
}

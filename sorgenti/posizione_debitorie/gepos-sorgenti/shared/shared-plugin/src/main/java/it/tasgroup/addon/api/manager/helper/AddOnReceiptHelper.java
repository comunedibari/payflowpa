package it.tasgroup.addon.api.manager.helper;

import it.tasgroup.addon.api.domain.TributoStrutturato;

import java.util.Locale;
import java.util.Map;

public interface AddOnReceiptHelper<T extends TributoStrutturato> {

	/**
	 * Costruisce una mappa di dettagli del tributo da mostrare nella ricevuta del pagamento.
	 * La chiave della mappa è la chiave del file di bundle (PluginMessages) con l'etichetta del campo.
	 * Il valore corrispondente è il valore (già formattato) da mostrare nella ricevuta.
	 * @param tributo
	 * @param locale
	 * @return mappa dei dettagli del tributo
	 */
	public Map<String, String> extractReceiptDetails(T tributo, Locale locale);
}
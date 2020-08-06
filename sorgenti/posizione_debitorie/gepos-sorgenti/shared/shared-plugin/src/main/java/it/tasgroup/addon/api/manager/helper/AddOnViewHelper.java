package it.tasgroup.addon.api.manager.helper;

import it.tasgroup.addon.api.domain.TributoStrutturato;

import java.util.Locale;
import java.util.Map;

public interface AddOnViewHelper<T extends TributoStrutturato> {

	/**
	 * Costruisce una mappa di dettagli del tributo da mostrare a video.
	 * La chiave della mappa è la chiave del file di bundle (PluginMessages) con l'etichetta del campo.
	 * Il valore corrispondente è il valore (già formattato) da mostrare a video.
	 * @param tributo
	 * @param locale
	 * @return mappa dei dettagli del tributo
	 */
	Map<String, String> extractViewDetails(T tributo, Locale locale);

	/**
	 * Decodifica la causale (proveniente dal DB, vedi JLTPEND.DE_CAUSALE) 
	 * rendendola leggibile dagli umani.
	 * (utilizzato per mostrare la causale nei dettagli a video)
	 * @param causale
	 * @return la causale decodificata
	 */
	String getCausale(String causale);
}

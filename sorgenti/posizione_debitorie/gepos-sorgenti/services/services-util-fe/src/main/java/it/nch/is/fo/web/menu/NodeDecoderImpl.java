/*
 * Creato il 23-gen-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.is.fo.web.menu;

import it.nch.fwk.fo.web.menu.NodeDecoder;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Implementazione di un oggetto in grado di interpretare le coppie presenti in un file
 * di properties i18n per costruire un nodo di menu.
 *
 * @author EE10057
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class NodeDecoderImpl implements NodeDecoder
{
	/**
	 * Mappa che associa le chiavi del file di properties ad un array di stringhe  ottenuto
	 * mediante split in base al separatore ',' dal messaggio associato alla chiave sul medesimo
	 * file di properties.
	 */
	private HashMap<String, String[]> map = null;

	/**
	 * Nome del file di properties privato del suffisso (opzionale) relativo al linguaggio
	 * e dell'estensione.
	 */
	String menuProperties = "menu";

	/**
	 * Restituisce la prima stringa della lista associata al codice in ingresso,
	 * che per convenzione è l'etichetta del nodo dell'albero di menu.
	 *
	 * @see it.nch.fwk.fo.web.menu.NodeDecoder#getLabel(java.lang.String)
	 */
	public String getLabel(String code){
		String value = "";
	    if (map.get(code)!= null)
	    	value = map.get(code)[0];
	    	return value;
	}


	/**
	 * Restituisce la seconda stringa della lista associata al codice in ingresso,
	 * che per convenzione è la URL a cui il nodo dell'albero di menu deve puntare.
	 * Se la lista associata al codice in ingresso ha un solo elemento (la label)
	 * restituisce la stringa vuota.
	 *
	 * @see it.nch.fwk.fo.web.menu.NodeDecoder#getUrl(java.lang.String)
	 */
	public String getUrl(String code){
		String value = "";
	    if (map.get(code)!= null){
	    	String[] elements = map.get(code);
	    	if(elements.length > 1)
	    		value = elements[1];
	    }
		return value;
	}

	/**
	 * Costruttore di questa implementazione di NodeDecoder che non specifica il linguaggio
	 * del file di properties in base al quale costruisce il menu.
	 */
	public NodeDecoderImpl(){
		this(null);
	}

	/**
	 * Costruttore di questa implementazione di NodeDecoder.
	 * Popola la map della classe con coppie (chiave, valore) in cui chiave è il codice
	 * dei messaggi I18N sul file menu_language.properties e valore è un array di stringhe
	 * ottenuto mediante split in base al separatore ',' dal messaggio associato alla chiave
	 * sul medesimo file di properties.
	 *
	 * @param language l'identificatore ISO del linguaggio per cui interessano le properties.
	 */
	public NodeDecoderImpl(Locale locale)
	{
		map = new HashMap<String, String[]>();
		String code = null;
		String[] values;

		ResourceBundle bundle = ResourceBundle.getBundle(menuProperties,locale);
		Enumeration<String> enumeration = bundle.getKeys();
		while (enumeration.hasMoreElements()) {
			code = enumeration.nextElement();
			values = bundle.getString(code).split(",");
			map.put(code, values);
		}

	}


}

/*
 * Creato il 26-gen-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.web.menu;

/**
 * Interfaccia che designa un tipo di oggetto in grado di interpretare le coppie presenti in un file
 * di properties per costruire un nodo di menu.
 *
 * @author EE10057
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public interface NodeDecoder {

	/**
	 * Restituisce l'etichetta di un nodo del menu
	 * @param code
	 * @return
	 */
	public String getLabel(String code);


	/**
	 * Restituisce l'URL a cui deve puntare un nodo del menu
	 * @param code
	 * @return
	 */
	public String getUrl(String code);


}

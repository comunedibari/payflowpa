/*
 * Creato il 25-ott-05
 *
 * Per modificare il modello associato a questo file generato, aprire
 * Finestra&gt;Preferenze&gt;Java&gt;Generazione codice&gt;Codice e commenti
 */
package it.nch.utility;

/**
 * Questa interfaccia deve essere implementata da quegli oggetti 
 * preposti al caricamento di file di configurazione e per i quali
 * se ne vuole gestire il il reload.
 * 
 * Tali oggetti saranno 'osservatori' di 'ObservableConfigReloader'
 * sul quale devono registrarsi.
 * 
 */
public interface IConfigObserver {
	
	/**
	 * Questo metodo effettua il reload dei parametri di configurazione
	 * letti dai file di property e memorizzati in cache (attributi statici).
	 * 
	 */	
	public void reloadConfiguration();
	
}

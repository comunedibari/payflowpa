package it.tasgroup.idp.plugin.api;

import java.util.HashMap;

/**
 * Interfaccia da implementare per realizzare un plugin.
 * Un plugin permette di decodificare il campo descrizioneCausale di una pendenza
 * e di inserire i dati in una tabella specifica che contenga i dati di business. 
 *
 * @author repettis
 *
 */
public interface BackEndPlugin {
	
	/**
	 * Metodo che permette di validare la formattazione del campo descrizioneCausale. 
	 * Tipicamente per una pendenza che si intende pagara in modo spontaneo, 
	 * il campo descrizioneCausale deve essere formattato in coppie chiave=valore con un
	 * separatore. Implementare questo metodo permette di definire una propria sintassi  
	 * specifica per il tipo tributo.
	 * 
	 * Nota: nel caso in cui la formattazione della stringa descrizioneCausale sia 
	 * KEY1=VALUE1; KEY2=VALUE2; è possibile creare il plugin estendendo la classe:
	 * BackEndPluginAdapter, che ha già realizzato la logica di parsing. 
	 * 
	 * @param causale
	 * @return null se la causale è conforme, una stringa contenente la descrizione dell'anomalia
	 * 		   riscontrata, se la causale non è conforme.
	 */
	String validaCausale(String causale);
	
	/**
	 * Metodo che permette di validare il contenuto del campo descrizioneCausale
	 * 
	 *  
	 * 
	 * @param causale
	 * @return null se la causale è conforme alle specifiche, una stringa contenente la descrizione dell'anomalia
	 * 		   riscontrata, se la causale non è conforme.
	 */
	
	String validaCausale(HashMap<String, String> causaleStrutturata);
	
	
	DettaglioStrutturatoModel getDettaglioStrutturato(String causale);
	DettaglioStrutturatoModel getDettaglioStrutturato(HashMap<String, String> causaleStrutturata);
}

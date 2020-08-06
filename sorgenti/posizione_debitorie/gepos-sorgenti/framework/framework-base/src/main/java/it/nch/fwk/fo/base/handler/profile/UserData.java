/*
 * File: UserData.java
 * Package: com.etnoteam.service.handler.profile
 * 
 * Revision: $Revision: 1.1.1.1 $ 
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $ 
 * Created on: 13-lug-04 - 12.00.00
 * Created by: finsaccanebbia (Etnoteam)
 */

package it.nch.fwk.fo.base.handler.profile;

import java.io.Serializable;

/**
 * @author finsaccanebbia
 *
 * Interfaccia che deve essere implementate dalle classi
 * che si occupano di immagazzinare i dati
 * dell'utente in modo generico. Questi dati possono essere 
 * dati di profilo o specifici dell'applicazione.
 * 
 * Non viene definito alcun insieme di nomi di proprieta'
 * da utilizzare. E' comunque consigliato ridurre il piu'
 * possibile la parte di codice che conosce come recuperare
 * l'informazione (ad esempio tramite un UserDataHelper che
 * espone interfaccie specifiche dell'applicazione)
 *
 */
public interface UserData extends Serializable {
    
	/**
	 * Verifica se un utente ha una determinata proprieta'.
	 * Utile quando si vuole richiedere una proprieta' opzionale
	 * e non si vuole incorrere nel rischio di una eccezione
	 * 
	 * @param property la proprieta' di cui verificare l'esistenza
	 * @return true se l'utente ha la proprieta' impostata, false altrimenti
	 */
	public abstract boolean hasProperty(String property);

	/**
	 * Recupero di una proprieta' booleana
	 * 
	 * @param property il nome della proprieta'
	 * @return il valore della proprieta'
	 * @throws IllegalArgumentException nel caso l'utente non possieda la proprieta'
	 */
	public abstract boolean getBooleanProperty(String property)
			throws IllegalArgumentException;

	
	/**
	 * Impostazione di una proprieta' booleana
	 * 
	 * @param property il nome della proprieta'
	 * @param value il valore della proprieta'
	 * @throws IllegalArgumentException nel caso l'utente non possieda la proprieta'
	 */	
	public abstract void setBooleanProperty(String property, boolean value)
			throws IllegalArgumentException;


	/**
	 * Recupero di una proprieta' stringa
	 * 
	 * @param property il nome della proprieta'
	 * @return il valore della proprieta'
	 * @throws IllegalArgumentException nel caso l'utente non possieda la proprieta'
	 */
	
	public abstract String getProperty(String property)
	throws IllegalArgumentException;

	/**
	 * Impostazione di una proprieta' stringa
	 * 
	 * @param property il nome della proprieta'
	 * @param value il valore della proprieta'
	 * @throws IllegalArgumentException nel caso l'utente non possieda la proprieta'
	 */		
    public abstract void setProperty(String property, String value)
	throws IllegalArgumentException;
	
	
	/**
	 * Recupero di una proprieta' legata ad un servizio
	 * 
	 * @param profileServiceName codice del servizio
	 * @param propertyName codice della proprieta'
	 * @return valore della proprieta' nel profilo utente
	 */
	public abstract String getServiceProperty(String profileServiceName, String propertyName);
	
	/**
	 * Impostazione di una proprieta' legata ad un servizio
	 * 
	 * @param profileServiceName codice del servizio
	 * @param propertyName codice della proprieta'
	 * @param value valore della proprieta'
	 */
	public abstract void setServiceProperty(String profileServiceName, String propertyName, String value);	

	/**
	 * Impostazione di un valore per la chiave di una proprieta'. Normalmente
	 * la chiave viene creata a partire dal codice del servizio e della proprieta'
	 * 
	 * @param propertyKey
	 * @param value
	 */
	public abstract void setServiceProperty(String propertyKey, String value);	
    
	/**
	 * Recupero di una proprieta' generica
	 * 
	 * @param propertyName il nome della proprieta'
	 * @return l'oggetto che rappresenta il valore della proprieta'
	 * @throws IllegalArgumentException nel caso l'utente non possieda la proprieta'
	 */
	
	public abstract Object getPropertyObject(String propertyName);
	
	/**
	 * Impostazione di una proprieta' generica
	 * 
	 * @param propertyName il nome della proprieta'
	 * @param object l'oggetto che rappresenta il valore della proprieta'
	 * @throws IllegalArgumentException nel caso l'utente non possieda la proprieta'
	 */
	
	public abstract void setPropertyObject(String propertyName, Object object);

	/**
	 * Recupero di una proprieta' generica di tipo "int"
	 * 
	 * @param propertyName il nome della proprieta'
	 * @return l'oggetto che rappresenta il valore della proprieta'
	 * @throws IllegalArgumentException nel caso l'utente non possieda la proprieta'
	 */
	
	public abstract int getPropertyInt(String propertyName);
	
	/**
	 * Impostazione di una proprieta' generica di tipo "int"
	 * 
	 * @param propertyName il nome della proprieta'
	 * @param integer l'oggetto che rappresenta il valore della proprieta'
	 * @throws IllegalArgumentException nel caso l'utente non possieda la proprieta'
	 */
	
	public abstract void setPropertyInt(String propertyName, int integer);
	
}
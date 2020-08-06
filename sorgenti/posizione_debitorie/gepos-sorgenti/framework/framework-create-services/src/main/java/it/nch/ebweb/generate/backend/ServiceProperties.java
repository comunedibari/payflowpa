/*
 * Creato il 21-apr-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.ebweb.generate.backend;


import java.util.HashMap;

/**
 * @author FelicettiA
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class ServiceProperties {
	
	
	private static ServiceProperties serviceProperties;

	private HashMap categorie = new HashMap();
	private HashMap internalService = new HashMap();
	
	
	private ServiceProperties() {
	}

	public static ServiceProperties getIstance() {
		if (serviceProperties == null) {
			serviceProperties = new ServiceProperties();
		}
		return serviceProperties;
	}
	
	
	public String getCategoriaByServizio(String nomeServizio){
		return (String)this.categorie.get(nomeServizio);
	}
	
	public void setCategoriaByServizio(String nomeServizio,String categoria,boolean isInternalFrw){
		this.categorie.put(nomeServizio,categoria);
		this.internalService.put(nomeServizio,"ffff");
	}
	
	
	
	

}

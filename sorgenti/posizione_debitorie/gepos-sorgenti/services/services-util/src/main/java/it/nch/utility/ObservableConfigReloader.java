package it.nch.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Questa classe, sulla quale si registrano oggetti 'osservatori' di tipo
 * 'IConfigObserver' e preposti al caricamento di file di configurazione, 
 * provvede a notificare questi ultimi di ricaricare la configurazione, 
 * invocandone il metodo reloadConfiguration() che implementano. 
 *
 */
public class ObservableConfigReloader {
	
	// Implementazione thread-safe
	private final static ObservableConfigReloader INSTANCE = new ObservableConfigReloader();

	private List configObservers = Collections.synchronizedList(new ArrayList());

	
	private ObservableConfigReloader(){
	}
	
	public static ObservableConfigReloader getInstance() {
		return INSTANCE;
	}	
	
	public void addConfigObserver(IConfigObserver o) {
		configObservers.add(o);
	}

	public void removeConfigObserver(IConfigObserver o) {
		configObservers.remove(o);
	}

	/**
	 * Questo metodo notifica gli oggetti registrati a ricaricare la configurazione, 
	 * invocandone il metodo reloadConfiguration() che implementano. 
	 *
	 */
	public void notifyConfigObservers() {
		
		// L'iterazione deve essere protetta da eventuali modifiche concorrenti
		// (es: aggiunta di elementi) sulla Collection 'configObservers'.
		synchronized(configObservers) {
			Iterator i = configObservers.iterator();
			while (i.hasNext()){
				((IConfigObserver)i.next()).reloadConfiguration();
			}	
		}
	}
}

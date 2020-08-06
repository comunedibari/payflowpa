package it.nch.fwk.fo.xml;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.dom4j.Document;

/**
 * <p>La classe XMLCache gestisce il caricamento e il successivo caching di
 * documenti XML ({@see org.dom4j.Document}) esterni all'applicazione 
 * </p>
 * <p>I documenti XML sorgenti, sono individuati, usando il <code>ClassLoader</code>
 * dell'applicazione, interpretando il nome del file XML indicato come path name 
 * relativo ad un file in una directory o in uno jar/zip file presente nel CLASSPATH.
 * </p>
 * <p>Qualora non sia possibile individuare il sorgente XML attraverso il 
 * <code>ClassLoader</code> il nome del file XML indicato viene interpretato
 * come path name (relativo o assoluto) di un file localizzato nel
 * file system ospite.
 * </p>
 * <p>Una volta individuato e caricato un documento XML, ne viene mantenuta una
 * copia, per soddisfare le successive richieste mediante 'clonazione' evitando
 * ulteriori accessi al file system e il costo coputazionale di un nuovo parsing
 * XML.
 * </p>
 * <p>La gestione del caching si basa sui seguenti criteri:
 * <ul>
 *   <li>contenimento del numero di elementi presenti nella cache basato entro
 *       un massimo prefissato secondo uno schema LRU (least recently used)</li>
 *   <li>adozione di una politica di aggiornamento 'lazy' nella
 *       verifica/aggiornamento/pulizia dei contenuti della cache;</li>
 *   <li>verifica della "freschezza" di un documento XML a fronte di 
 *       una richiesta di accesso ma, in ogni caso, non piu' di una 
 *       volta entro un medesimo intervallo di tempo prefissato 
 *       (CHECK_INTERVAL)</li>
 *   <li>per quei documenti XML per i quali non fosse reperibile una
 *       data di ultima modifica, adozione di una olitica di ageing,
 *       ovvero l'elemento contenuto nella cache e' riutilizzato solo
 *       l'accesso avviene entro un tempo massimo prefissato (MAX_AGE)</li>
 * </ul>
 * </p>
 * <dt><b>NOTA:</b><dt>
 * <ds>
 *    <p>Per i jar file, abbiamo tentato una gestione piu' fine  utilizando sia 
 *    il modify time del jar file che quello dell'elemento tuttavia, il metodo 
 *	  <code>getJarEntry().getTime()</code>. per un elemento modificato
 *    a run time (overwrite del jar file n.d.a) non riporta il nuovo
 *    modify time.</p>
 *    <p>Come risultato, se un jar/zip file risulta modificato, tutti
 *    i suoi elementi presenti nella cache sono considerati modificati
 *    e al primo accesso/verifica sono ricaricati.
 * </ds>
  */
public class XMLFileCache {
	final static int DEFAULT_CAPACITY			= 256;
	final static int DEFAULT_CHECK_INTERVAL 	= 30000;
	final static int DEFAULT_MAX_AGE			= 10 * DEFAULT_CHECK_INTERVAL;
	
	static Logger trace = Logger.getLogger(XMLFileCache.class);
	
	HashMap    cache;
	LinkedList lru;
	
	int  capacity;
	long checkInterval;
	long maxAge;

	/** <p>Inizializza una istanza di XMLCache con capacity, check interval e
	 *  max age di default.</p>
	 */	
	public XMLFileCache() {
		this(DEFAULT_CAPACITY, DEFAULT_CHECK_INTERVAL, DEFAULT_MAX_AGE);
	}
	
	/** <p>Inizializza una istanza di XMLCache con la capacity indicata
	 * utilizzando come check interval e max age i valori di default.</p>
	 * 
	 * @param capacity		la capacity/dimensione della cache
	 */
	public XMLFileCache(int capacity) {
		this(capacity, DEFAULT_CHECK_INTERVAL, DEFAULT_MAX_AGE);
	}
	
	/** <p>Inizializza una istanza di XMLCache con capacity, check interval e
	 *  max age indicati.</p>
	 * 
	 * @param capacity			la capacity/dimensione della cache
	 * @param checkInterval	il tempo minimo di refresh
	 * @param maxAge			il empo massimo di vita per gli elementi
	 *                          per i quali non e' definito un
	 *                          modify time.
	 */
	public XMLFileCache(int capacity, long checkInterval, long maxAge) {
		this.capacity		= capacity;
		this.checkInterval	= checkInterval;
		this.maxAge		= maxAge;
		
		this.cache = new HashMap(capacity);
		this.lru = new LinkedList();
	}
	
	/**
	 * <p>Ritorna il documento XML indicato, dalla cache se gia presente e 
	 * ancora valido, dopo averlo ricaricato, in caso contrario.
	 * </p>
	 * <p>I documenti XML sorgenti, sono individuati, usando il 
	 * <code>ClassLoader</code> dell'applicazione, interpretando il nome 
	 * del file XML indicato come path name relativo ad un file in una 
	 * directory o in uno jar/zip file presente nel CLASSPATH.
	 * </p>
	 * <p>Qualora non sia possibile individuare il sorgente XML attraverso il 
	 * <code>ClassLoader</code> il nome del file XML indicato viene 
	 * interpretato come path name (relativo o assoluto) di un file localizzato 
	 * nel file system ospite.
	 * </p>
	 * @param key  il nome del documento XML da reperire
	 * @return <code>Document<code> - il documento XML richiesto, o null se 
	 *                                 non e' stato possibile reperirlo. 
	 */
	public synchronized Document get(String key) {
		try {
			XMLResource entry = (XMLResource)cache.get(key);
		
			if( entry != null ) {
				// abbiamo il documento in cache
				//
				if( !entry.isModofiedSinceOrExpired() ) {
					// ed e' ancora buono quindi, lo portiami
					// in testa alla lru e ..
					//					
// CS
					int index = lru.indexOf(key);
					lru.remove(index);
					lru.addFirst(key);
// CS
					
					// .. ritorniamo un clone del documento
					// XML individuato.
					//
					return (Document)entry.value().clone();								
				} else { 
					// il documento e' vecchio e lo rinnoviamo 
					// (sempre che, nel frattempo, non sia stato 
					// rimosso)
					//
					cache.remove(key);
					lru.remove(key);
					
					return get(key);
				}
			} else {
				// il documento non e' presente nella cache
				// tentiamo percio' di recuperarlo..
				//
				entry = new XMLResource(key, maxAge, this.checkInterval);
				
				// se abbiamo raggiunto la capacita massima
				// della cache rimuoviamo prima l'elemento piu' vecchio.
				//
				if( lru.size() == capacity ) {
					cache.remove(lru.removeLast());
				}
				
				// quindi aggiungiamo il nuovo elemento alla cache
				//	
				lru.addFirst(key);
				cache.put(key,entry);

				// dopo di che, ritorniamo un clone del documento
				// XML creato.
				//
				return (Document)entry.value().clone();								
			}
		}
		catch( FileNotFoundException e ) {
			// trace.debug(key+" not found");
			return null;
		}
		catch( Exception e ) {
			trace.error("fallito il recupero del documento XML '"+key+"'");
			trace.debug("l'eccezione occorsa e':", e);
			
			return null;
		}
	}
	
	public synchronized URL getURL(String key) {
		// solo per essere "sicuro" che sia in cache
		get(key);
		XMLResource entry = (XMLResource)cache.get(key);
		
		return entry.getSource();
	}
	
	
	public static void main(String argv[]) {
		XMLFileCache cache = new XMLFileCache(100);
		
		long start;
		long end;
		long lap=0;
		long i;
		for( i = 0; i < 5000; ++i ) {
			long sample = (int)(Math.random() * 4) + 1;
			start = System.currentTimeMillis();
			cache.get("test-data/file"+sample+".xml");
			cache.get("element"+sample+".xml");
			end = System.currentTimeMillis();
			lap += (end - start);
		}
	}
}


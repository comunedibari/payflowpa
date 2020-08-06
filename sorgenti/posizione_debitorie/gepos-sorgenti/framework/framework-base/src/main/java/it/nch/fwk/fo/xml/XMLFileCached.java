package it.nch.fwk.fo.xml;

import java.io.FileNotFoundException;

import org.dom4j.Document;

/**
 * <p>Utility class che incapsula l'accesso e il caching di un documento
 * XML esterno all'applicazione.
 * </p>
 * <p>Il documento e' localizzato interpretando il nome indicato come
 * un path name relativo di una risorsa accessibile tramite ClassLoader
 * o, in subordine, come path relativo/assoluto di un file nel file 
 * system ospite.
 * </p>
 * <p>Il caching prevede il controllo del modify time, nel caso che
 * questo sia supportato dalla risorsa in questione, o, una politica di
 * aging/expiration in caso contrio (ma non entrambe).
 * </p>
 * <dt><b>NOTA:</b><dt>
 * <ds>
 *    <p>Nel caso quindi di jar/zip entry, la decisione di rinfrecare
 *    il documento XML cached e' basata, non sulla sua data di ultima 
 *    modifica, ma su quella del corisspondente jar/zip file.
 *    </p>
 *    <p>Abbiamo rilevato sperimentalmente, su alcuni sistemi, 
 *    che se sostituiamo "a caldo", un jar file con un altro in cui 
 *    l'elemento riferito e' stato modificato, in nuovo "modify time" 
 *     non viene restituito (bug? caching?).
 *    </p>
 * </ds>
 */
public class XMLFileCached {
	private String			resourceName;
	private long			maxAge;
	private XMLResource	resource;
	
	/** 
	 * <p>Costruttore di un XMLCacheedFile.
	 * </p>
	 * @throw <code>FileNotFoundException</code>  quando il file specificato non 
	 *                                            viene individuato nel classpath
	 *                                            o sul file system.
	 * 
	 * @throw <code>Exeption<code>                quando falisce il caricamnto 
	 *                                            del sorgente XML richiesto
	 *                                            (e.g. parse error, IO error, ecc..).
	 */	
	public XMLFileCached(String resourceName, long maxAge) 
		throws 
			FileNotFoundException,
		    Exception 
	{
		this.resourceName = resourceName;
		this.maxAge = maxAge;
		this.resource = new XMLResource(resourceName, maxAge);
	}
	
	public XMLFileCached(String resourceName) 
	throws 
		FileNotFoundException,
	    Exception 
{
	this.resourceName = resourceName;
	this.resource = new XMLResource(resourceName);
}
	

	/**
	 * <p>Ritorna il documento XML a suo tempo caricato.</p>		
	 */
	public synchronized Document document() 
		throws 
			FileNotFoundException,
		    Exception 
	{
		if( resource.isModofiedSinceOrExpired() ) {
			resource = new XMLResource(resourceName, maxAge);
		}
		return resource.value();
	}		
}

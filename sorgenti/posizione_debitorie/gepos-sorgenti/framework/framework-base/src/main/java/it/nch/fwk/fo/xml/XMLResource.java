package it.nch.fwk.fo.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * <p>Package private utility class che consente di implementare 
 * l'accesso e il caching di un documento XML esterno all'applicazione
 * reperibile cme risorsa attraverso il ClassLoader o, in subordine,
 * come file direttamente sul file system ospite.
 * </p>
 * <p>Il class loader utilizzato e' quello dell'applicazione e non 
 * quello di sistema in modo da consentirne l'uso all'interno di
 * web applications.
 * </p>
 * <p>Note:
 * <ul>
 * 	 <li>Non tutte le risorse supportano un modify time nel qual
 *       caso viene adottata una politica di aging. </li>
 * 
 *   <li><p>Per gli jar/zip entries, assumiamo come data di ultima
 *       mofifica quella del jar che li contiene.</p>
 *  
 *       <p>Abbiamo riscontrato che sostituendo a caldo un jar 
 *       file con un altro uguale a meno di un elemento, la data 
 *       di modifica di quell elemento ritornata da 
 *       <code>java.util.jar.JarEntry</code> non cambia 
 *       (bug? caching di java?).</p></li>
 * 
 *   <li>La verifica del modify time (o dell'espiration), per ragioni 
 *       di efficienza, non viene eseguita ad ogni richiesta ma, al
 *       piu' una volta nel medesimo intervallo intervallo di tempo 
 *       (checkInterval).</li>
 * </ul> 
 */
class XMLResource implements EntityResolver { /*package private */ 
	
	final static int DEFAULT_CHECK_INTERVAL 	= 30000;
	final static int DEFAULT_MAX_AGE			= 10 * DEFAULT_CHECK_INTERVAL;
	
	private long 		checkInterval;
	private long 		maxAge;
	
	private URL 		source;
	private String 	protocol;
	private File 		sourceFile;

	private Document	content;		

	private long 		checkTime;
	private long		expireTime;
	private long		modifyTime;

	private InputSource devNull = new InputSource(new StringReader(""));

	/** 
	 * <p>Costruttore di una istanza di XMLResource, implementa l'accesso 
	 * ad documento XML esterno all'applicazione reperibile cme risorsa 
	 * attraverso il ClassLoader o, in subordine, come file direttamente 
	 * sul file system ospite.
	 * </p>
	 * 
	 * @param resource       il nome della risorsa xml da localizzare e
	 *                        caricare.
	 * 
	 * @param maxAge         per le risorse di cui non si puo' avere il
	 *                        modify time, e' l'eta massima oltre la 
	 *                        quale il documento e' da considerare vecchio.
	 * 
	 * @param checkInterval  il tempo minimo tra due accessi alla risorsa
	 *                        esterna per cotrollare il suo modify time 
	 * 
	 * 
	 * @throw <code>FileNotFoundException</code>  quando il file specificato non 
	 *                                            viene individuato nel classpath
	 *                                            o sul file system.
	 * 
	 * @throw <code>Exeption<code>                quando falisce il caricamnto 
	 *                                            del sorgente XML richiesto
	 *                                            (e.g. parse error, IO error, ecc..).
	 */	
	XMLResource(String resource, long maxAge, long checkInterval) 
		throws 
			FileNotFoundException,
		    Exception 
	{
		this.maxAge = maxAge;
		this.checkInterval = checkInterval;
		
		source = getClass().getClassLoader().getResource(resource);
		if( source == null ) {
			sourceFile = new File(resource);
			if( sourceFile.exists() && sourceFile.isFile() ) {
				source = new URL("file:"+resource);
			}
		}
		
		if( source == null ) {
			throw new FileNotFoundException(resource);
		}
		
		checkTime = System.currentTimeMillis();
		protocol = source.getProtocol();

		// carichiamo il documento XML 
		//
		URLConnection conn = source.openConnection();
		InputStream is = conn.getInputStream();				
		
		SAXReader r = new SAXReader(false);
		r.setEntityResolver(this);

		content = r.read(is);
		
		
		// da ultimo ci salviamo il modify time.
		//
		// Note: 
		//    * per i jar file, abbiamo tentato una gestione piu' fine
		//      conserviamo sia il modify time del jar file che 
		//      quello dell'elemento tuttavia, il metodo 
		//
		//              getJarEntry().getTime()
		//
		//      continua a restituire lo stesso modify time.
		//
		//    *  per qualcosa che non sia ne' un jar file element
		//       ne' un semplice file, usiamo 
		//
		//                   conn.lastModified() 
		//
		//       che tuttavia in molti casi e' '0', nel qual caso 
		//       adottiamo una politica di ageing.
		//
		if ("jar".equals(protocol)) {
			String s = source.getFile();				
			sourceFile = new File(s.substring(5, s.indexOf('!')));
			modifyTime = sourceFile.lastModified();
		} 
		else if ("file".equals(protocol)) {
			sourceFile = new File(source.getFile());
			modifyTime = sourceFile.lastModified();
		}
		else {
			if( (modifyTime = conn.getLastModified()) == 0 ) {
				expireTime = checkTime + maxAge;
			}				
		}
		try { is.close(); } catch(Exception ignore) {;}

	}
	

	/** 
	 * <p>Costruttore di una istanza di XMLResource con 
	 * default checkInterval 
	 * ({@see 	XMLResource(String resource, long maxAge, long checkInterval)}) .
	 * </p>
	 * 
	 * @param resource       il nome della risorsa xml da localizzare e
	 *                        caricare.
	 * 
	 * @param maxAge         per le risorse di cui non si puo' avere il
	 *                        modify time, e' l'eta massima oltre la 
	 *                        quale il documento e' vecchio.
	 * 
	 * 
	 * @throw <code>FileNotFoundException</code>  quando il file specificato non 
	 *                                            viene individuato nel classpath
	 *                                            o sul file system.
	 * 
	 * @throw <code>Exeption<code>                quando falisce il caricamnto 
	 *                                            del sorgente XML richiesto
	 *                                            (e.g. parse error, IO error, ecc..).
	 */	
	XMLResource(String resource, long maxAge) 
		throws 
			FileNotFoundException,
		    Exception 
	{
		this(resource, maxAge, DEFAULT_CHECK_INTERVAL);
	}

	/** 
	 * <p>Costruttore di una istanza di XMLResource con default maxAge e
	 * checkInterval
	 * ({@see XMLResource(String resource, long maxAge, long checkInterval) }).
	 * </p>
	 * 
	 * @param resource       il nome della risorsa xml da localizzare e
	 *                        caricare.
	 * 
	 * 
	 * @throw <code>FileNotFoundException</code>  quando il file specificato non 
	 *                                            viene individuato nel classpath
	 *                                            o sul file system.
	 * 
	 * @throw <code>Exeption<code>                quando falisce il caricamnto 
	 *                                            del sorgente XML richiesto
	 *                                            (e.g. parse error, IO error, ecc..).
	 */	
	XMLResource(String resource) 
		throws 
			FileNotFoundException,
		    Exception 
	{
		this(resource, DEFAULT_MAX_AGE, DEFAULT_CHECK_INTERVAL);
	}
	
	/**
	 * <p>Ritorna il documento XML a suo tempo caricato.</p>		
	 */
	Document value() {
		return content;
	}		
	
	/**
	 * <p>Stabilisce se la risorsa (cached) e' da considerare vecchia
	 * ovvero, se, avendo un modify time, risulta modificata alla fonte
	 * o, mancando il modify time, verificando se ha raggiunto i
	 * limiti di eta' (maxAge).
	 * </p>
	 * <p>Nota: se sono trascorsi meno di checkInterval millisecondi
	 * dall'ultima verifica, l'accesso alla risorsa per controlare
	 * il modify time e' rimandato ad uno dei prossimi tentativi.
	 * </p>
	 */
	boolean isModofiedSinceOrExpired() 
		throws 
			Exception
	{
		if(modifyTime == 0) {
			// se la nostra sorgente non supporta un modify time
			// ci limitiamo a verificare che non abbiamo superato
			// i limiti di eta'
			//
			return System.currentTimeMillis() > expireTime;
		} else {
			
			// se invece abbiamo il modify time, andiamo a controllarlo
			// evitando pero di farlo ad ogni richiesta ma almeno una 
			// volta in un checkInterval;
			//
			if((System.currentTimeMillis() - checkTime) < checkInterval) {
				return false;
			}
			checkTime = System.currentTimeMillis();
			
			if ("jar".equals(protocol) || "file".equals(protocol)) {
				// per i files e i jar file entry andiamo a verificare
				// la data di modifica del file sorgente
				//			
				return ( modifyTime != sourceFile.lastModified() );
			} else {
				// per gli altri(?) che supportano un modify time attraverso
				// conn.getLastModified() andiamo a verificarlo di nuovo
				//
				URLConnection conn = source.openConnection();
				long lastModified = conn.getLastModified();
				try { conn.getOutputStream().close(); }
					catch(Exception ignored) {;}
				return (modifyTime != lastModified);
			}
			
		}		
		
	}	
	/**
	 * @return
	 */
	URL getSource() {
		return source;
	}

	public InputSource resolveEntity (String publicId, String systemId) {
	  return devNull;
	}
}


/*
 * File: SessionHandler.java
 * Package: com.etnoteam.service.handler.session
 *
 * Revision: $Revision: 1.1.1.1 $
 * Last revision by: $Author
 * Last revised on: $Date: 2006/05/03 11:06:46 $
 * Created on: 16-lug-03 - 19.25.56
 */

package it.nch.fwk.fo.web.session;


import it.nch.fwk.fo.base.config.ConfigProperties;
import it.nch.fwk.fo.base.config.Configurations;
import it.nch.fwk.fo.base.constants.BaseConfigSources;
import it.nch.fwk.fo.base.handler.profile.UserContext;
import it.nch.fwk.fo.base.text.DataFormatter;
import it.nch.fwk.fo.base.text.DataSorter;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.LRUMap;
import org.apache.log4j.Logger;

/**
 *
 * <br>
 * La classe gestisce l'interazione con il sottostante oggetto HttpSession.
 * Essa fornisce metodi per la gestione della sincronizzazione tra
 * la sessione in memoria e la sua storicizzazione su database
 *
**/
public class BaseSessionHandler implements Serializable
{
	private static final long	serialVersionUID	= 1L;

	/* logger (non serializzato) */
	private static transient Logger logger = Logger.getLogger(BaseSessionHandler.class);

	/* flag statico (per ragioni di performance, non viene letto a caldo) per
	 * abilitare/disabilitare il controllo sull'accesso mutualmente esclusivo
	 * default: esegui controllo
	 */
	 private static boolean mutualExclusive = false;

	 /* parametro statico con l'intervallo (in millisecondi) di controllo del timestamp di sessione
	  * default: nessun intervallo
	  * */
	 private static long checkInterval = 0;

	/* inizializzazione statica dei parametri di controllo sessione esclusiva */
	static {
		initMutualExclusiveCheck();
	}

	/**
     *
     */
    private static void initMutualExclusiveCheck() {
		ConfigProperties prop = Configurations.getProperties(BaseConfigSources.WEB);
		mutualExclusive = prop.getBooleanProperty("MUTUAL.ACCESS.ENABLE");
		checkInterval = prop.getLongProperty("MUTUAL.ACCESS.REFRESH");
		checkInterval *= 1000; /* millisecondi */

		logger.debug("Flag accesso esclusivo:"+mutualExclusive);
		logger.debug("Intervallo check:"+checkInterval);
    }

    /* attributo di sessione Http nel quale viene memorizzato
	 * un riferimento all'oggetto corrente
	 */
	static final String SESSION_ATTRIBUTE ="com.etnoteam.inweb.session";

    /* proprieta' della sessione applicativa ove memorizziamo le informazioni */
	private static final String DATA_FORMATTER_PROPERTY = "dataformatter.property";
	private static final String DATA_SORTER_PROPERTY = "datasorter.property";
	private static final String USER_CONTEXT_PROPERTY = "user.context.property";

	/*
	 * Chiave per estrarre dal pageContext il nome del
	 * bundle da utilizzare
	*/
    private static final String USER_BUNDLE = "USER_BUNDLE";

	public static final String HISTORY_HANDLER_PROPERTY = "history.handler.property";


	/* Dimensione contenitore di oggetti con algoritmo LRU */
	private static final int LRU_SIZE = 5;

	/*
	 * interfaccia di sessione
	 */
	private HttpSession applicationSession;

	/* Map contenitore di oggetti (non serializzabile) */
	private transient Map sessionMap;

	/* Contenitore di oggetti LRU (non serializzabile) */
	private transient LRUMap sessionLru;

	/* timer x gestione sessione esclusiva */
	private transient long ctime = System.currentTimeMillis();

	/**
	* Costruttore privato.
	*
	* @param session oggetto di sessione istanziato dal web container
	**/
	public BaseSessionHandler(HttpSession session) {

		/* memorizzo sessione http */
		this.applicationSession = session;

		/* inizializzo collections dati */
		this.sessionMap = Collections.synchronizedMap(new HashMap());
		this.sessionLru = new LRUMap(LRU_SIZE);

		/* memorizzo riferimento SessionHandler in sessione http */
		this.applicationSession.setAttribute(SESSION_ATTRIBUTE, this);
	}

	public static BaseSessionHandler getFromSession(HttpSession session) {
	    BaseSessionHandler bsh=(BaseSessionHandler)session.getAttribute(SESSION_ATTRIBUTE);
	    if(bsh==null) {
	        bsh=new BaseSessionHandler(session);
	    }
	    return bsh;
	}

	/**
	* Set dell'oggetto utilizando la chiave specificata.
	*
	* @return nessuno
	* @param key chiave di memorizzazione
	* @param value oggetto memorizzato
	*
	**/
	public void setSessionProperty(String key, Object value) {
		this.sessionMap.put(key, value);
	}

	/**
	* Set dell'oggetto utilizando la chiave specificata (LRU).
	*
	* @return nessuno
	* @param key chiave di memorizzazione
	* @param value oggetto memorizzato
	*
	**/
	public void setLruSessionProperty(String key, Object value) {
		this.sessionLru.put(key, value);
	}

	/**
	* Get dell'oggetto memorizzato nella chiave specificata da oggetto in sessione con algoritmo LRU
	* (dim this.LRU_SIZE)
	*
	* @return Object
	* @param key chiave di memorizzazione
	*
	**/
	public Object getLruSessionProperty(String key) {
		return sessionLru.get(key);
	}

	/**
	* Get dell'oggetto memorizzato nella chiave specificata.
	*
	* @return Object
	* @param key chiave di memorizzazione
	*
	**/
	public Object getSessionProperty(String key) {
		return this.sessionMap.get(key);
	}


	/**
	* Remove dell'oggetto memorizzato con la chiave specificata
	*
	* @param key chiave di memorizzazione
	* @return Object l'oggetto precedentemente istanziato
	**/
	public Object removeSessionProperty(String key) {
		return this.sessionMap.remove(key);
	}


	/**
	 * Costruisce un nuovo contesto e lo memorizza in sessione.<br>
	 * Attualmente crea e memorizza in sessione gli oggetti UserContext,DataFormatter,DataSorter
	 * Metodo tipicamente chiamato al login, per memorizzare le informazioni
	 * dell'utente.
	 *
	 * @param language - la lingua per il nuovo contesto
	 * @param channel - il canale per il nuovo contesto
	 * @param bank - la banca per il nuovo contesto
	 */
	public void setNewContext(String localeKey, String language, String country, String channel, String bank)
		throws Exception {

		DataFormatter df = null;
		DataSorter ds = null;

        /* memorizzo formattatore per utente in sessione applicativa */
		df = DataFormatter.getInstance(bank, channel, language);
		setSessionProperty(DATA_FORMATTER_PROPERTY, df);

        /* recupero il datasorter e lo imposto in sessione applicativa */
		ds = DataSorter.getInstance(bank, channel, language);
		setSessionProperty(DATA_SORTER_PROPERTY, ds);

        /* STRUTS: memorizzo in attributi della sessione Http le informazioni per la risoluzione del resource bundle */
		this.applicationSession.setAttribute(localeKey, new Locale( language, country));
		this.applicationSession.setAttribute(USER_BUNDLE,bank+"_"+channel);
	}

	public String getBundleIdentifier() {
	    return (String) this.applicationSession.getAttribute(USER_BUNDLE);
	}


	/**
	 * Memorizza in sessione un nuovo oggetto DataFormatter
	 *
	 * @param DataFormatter - l'oggetto da memorizzare in sessione
	 */
	public void setDataFormatter(DataFormatter df) {
		setSessionProperty(DATA_FORMATTER_PROPERTY, df);
	}

	/**
	 * Restituisce l'oggetto DataFormatter memorizzato in sessione.
	 *
	 * @return DataFormatter - l'oggetto memorizzato in sessione.
	 */
	public DataFormatter getDataFormatter() {
		return (DataFormatter) getSessionProperty(DATA_FORMATTER_PROPERTY);
	}

	/**
	 * Memorizza in sessione un nuovo oggetto DataFormatter
	 *
	 * @param DataSorter - l'oggetto da memorizzare in sessione
	 */
	public void setDataSorter(DataSorter ds) {
		setSessionProperty(DATA_SORTER_PROPERTY, ds);
	}

	/**
	 * Restituisce l'oggetto DataSorter memorizzato in sessione.
	 *
	 * @return DataSorter - l'oggetto memorizzato in sessione.
	 */
	public DataSorter getDataSorter() {
		return (DataSorter) getSessionProperty(DATA_SORTER_PROPERTY);
	}

	/**
	 * Restituisce l'identificativo di sessione Http
	 *
	 * @return String - identificativo di sessione
	 */
	public String getSessionId() {
		return this.applicationSession.getId();

	}

	/**
 	 * Sostituisce una proprieta' in sessione in modo atomico e
	 * restituisce il vecchio valore della propriet&agrave;
	 *
	 * @param propName - il nome della propriet&agrave;
	 * @param val      - l'oggetto da settare come valore
	 * @return Object  - il vecchio valore della propriet&agrave;
	 */
	public final synchronized Object testAndSetContextProperty(String propName, Object val) {
		Object objret = getSessionProperty(propName);
		setSessionProperty(propName, val);
		return objret;
	}





	/**
	 * Restituisce true in caso vada effettuato il controllo sulla login esclusiva
	 * (sulla base dei parametri specificati nel file di configurazione inweb.properties)
	 * Imposta in sessione un timestamp per il controllo del timeout oltre il quale effettura
	 * il controllo di accesso
	 * @return boolean
	 */
    public boolean checkExclusiveLogin() {

    	boolean check = true;
    	long now = System.currentTimeMillis();

    	if(BaseSessionHandler.mutualExclusive) {
	    	if(now - ctime < checkInterval) {
	    		check = false;
	    	} else {
	    		this.ctime = now;
	    	}
    	} else {
    		check = false;
    	}

    	return check;
    }


	/**
	 * Restituisce flag abilitazione sessione esclusiva
	 * @return boolean
	 */
    public boolean isExclusiveLoginEnabled() {
    	return BaseSessionHandler.mutualExclusive;
    }

    /*
     *  Metodi per la gestione degli oggetti "volatili" di sessione con CRC code specifico
     *  Per ora sono utilizzati nella paginazione e nell export
     */

	/**
 	 * Mette in sessione un oggetto "volatile"
 	 * NOTA: "Volatile" &egrave;  messo tra virgolette poiche in realta ora e un oggetto normale
 	 *
	 *
	 * @param strName 		- 	Nome della funzionalita che l ha messo ( es: MovementList )
	 *
	 * @param strCrcCode   - 	Firma ( CRC32 ) dei parametri del request piu' importatni
	 * 							che hanno portato a ricevere l'oggetto di business che si vuole salvare
	 *
	 * @return objObject  - 	L'oggetto da salvare in sessione
	 */
    public  void putVolatileSessionObject(String strName,String strCrcCode,Object objObject) {
    	SessionVolatileObject svObj=new SessionVolatileObject(strCrcCode,objObject);
    	setSessionProperty(strName,svObj);
    }

	/**
	 *
	 * Prende dalla sessione l'oggetto di tipo strName con hash-code strCode
	 *
	 * @param strName			- Nome della funzionalita che l ha messo in sessione
	 *
	 * @param strCrcCode		- Crc code dell oggetto che si vuole recuperare
	 *
	 * @return Object			- wrapper dell oggetto di business con crcCode strCrcCode
	 *
	 */
    public Object  getVolatileSessionObject(String strName,String strCrcCode) {

    	Object objReturn=null;

		/* Mi prendo dalle session l'oggetto */
		SessionVolatileObject svObj=(	SessionVolatileObject) this.getSessionProperty(strName);
		if ((svObj!=null ) &&  (svObj.getObjectHash().equals(strCrcCode))) {
    		objReturn=svObj.getObj();
    	}

    	return objReturn;
    }


	/**
	 *
	 * Rimuove  dalla sessione l'oggetto di tipo strName con hash-code strCode
	 * Se lo trova lo rimuove e ritorna true
	 * Se non lo trova ritorna false
	 *
	 * @param strName			- Nome della funzionalita che l ha messo in sessione
	 *
	 * @param strCrcCode		- Crc code dell oggetto che si vuole rimuovere
	 *
	 * @return boolean			- oggetto rimosso o no
	 *
	 */
	public boolean removeVolatileSessionObject(String strName,String strCrcCode) {

    	boolean bFound=false;

    	/*
		Vedo se in sessione c e  l oggetto
		Se c e lo tolgo e ritorno true viceversa ritorno false
		*/
		SessionVolatileObject svObj=(	SessionVolatileObject) this.getSessionProperty(strName);

		if ((svObj!=null ) &&  (svObj.getObjectHash().equals(strCrcCode))) {
    		this.removeSessionProperty(strName);
    		bFound = true;
    	}

      	return bFound;
    }

	/**
	 *
	 * Rimuove  dalla sessione l'oggetto di tipo strName
	 * Se lo trova lo rimuove e ritorna true
	 * Se non lo trova ritorna false
	 *
	 * @param strName			- Nome della funzionalita che l ha messo in sessione
	 *
	 * @return boolean			- oggetto rimosso o no
	 *
	 */
	public boolean removeVolatileFunctionSessionObject(String strName) {

    	boolean bFound=false;

    	/*
    	Vedo se in sessione c e  l oggetto
		Se c e lo tolgo e ritorno true viceversa ritorno false
		*/
		SessionVolatileObject svObj=(	SessionVolatileObject) this.getSessionProperty(strName);

		if (svObj!=null ) {
    		this.removeSessionProperty(strName);
    		bFound = true;
    	}
    	return bFound;
    }

	/**
	 *
	 * Verifica se ce  in  sessione l'oggetto di tipo strName con hash-code strCode
	 * Se lo trova  ritorna true
	 * Se non lo trova ritorna false
	 *
	 * @param strName			- Nome della funzionalita che l ha messo in sessione
	 *
	 * @param strCrcCode		- Crc code dell oggetto che si vuole rimuovere
	 *
	 * @return boolean			- oggetto rimosso o no
	 *
	 */
	public boolean isThereVolatileSessionObject(String strName,String strCrcCode) {

    	boolean bFound=false;

    	/*
		Vedo se in sessione c e  l oggetto
		Se c e lo tolgo e ritorno true viceversa ritorno false
		*/
		SessionVolatileObject svObj=(	SessionVolatileObject) this.getSessionProperty(strName);

		if ((svObj!=null ) &&  (svObj.getObjectHash().equals(strCrcCode))) {
    			bFound = true;
    	}
       	return bFound;
    }

	/**
	 * Memorizza in sessione un nuovo oggetto UserContext.
	 *
	 * @param UserContext - l'oggetto da memorizzare in sessione
	 */
	public void setUserContext(UserContext uc) {
		setSessionProperty(USER_CONTEXT_PROPERTY, uc);
	}


	/**
	 * Restituisce l'oggetto UserContext memorizzato in sessione.
	 *
	 * @return UserContext - l'oggetto memorizzato in sessione.
	 */
	public UserContext getUserContext() {
		return (UserContext) getSessionProperty(USER_CONTEXT_PROPERTY);
	}
    class SessionVolatileObject  {

      	private String strObjectHash = null;

      	private Object obj = null;

		SessionVolatileObject(String strCrcCode,Object obj) {
				this.strObjectHash=strCrcCode;
				this.obj=obj;
		}

		public String getObjectHash() {
				return this.strObjectHash;
		}

		public Object getObj() {
				return this.obj;
		}

    }//end inner class

}



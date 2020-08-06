/*
 * File: UserContext.java
 * Package: com.etnoteam.service.handler.profile
 *
 * Revision: $Revision: 1.1.1.1 $
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $
 * Created on: 16-lug-03 - 19.25.56
 * Created by: dcerri (Etnoteam)
 */

package it.nch.fwk.fo.base.handler.profile;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;


/**
 *
 * <br>
 * La classe UserContext gestisce le informazioni di base (banca, canale, lingua) dell'utente
 * ed i parametri di funzionamento del servizio
 *
**/
public class UserContext
implements UserContextInterface, Serializable, Cloneable
{/* circuiti attivati per l'utente */
	private static final long	serialVersionUID	= 4710352873417311792L;

	private String userName;  //ragione sociale

	private int rqstNr = 0;

	/* utilizzato per la generazione di un identificativo di richiesta univoco */
	private SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy-HHmmss");

	/* locale legata a language --> seguite le direttive del dataformatter
	 * country = String.UpperCase(language)
	 */
	private Locale locale;

	/*
	 * Identifica lo userAgent del browser utilizzato
	 */
	private Map userAgent;

	/**
	 * Restituisce un id univoco per le transazioni
	 */
	public synchronized String getRequestId() {
		return sdf.format(new Date()) + "-" + rqstNr++;
	}

	/**
	 * Returns the userName, ovvero il nome dell'utente
	 * @return String
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * Sets the userName.
	 * @param userName The userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Object clone() {
		try {
			// internal collection are not (expected) changed after initial UserContect build
			// super.clone() will do
			//
			return super.clone();
		}
		catch (CloneNotSupportedException e) {
			return null;
		}
	}

	/**
	 * Returns the locale.
	 * @return Locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @return
	 */
	public Map getUserAgent() {
		return userAgent;
	}

	/**
	 * @param map
	 */
	public void setUserAgent(Map map) {
		userAgent = map;
	}


	/* codice banca IN */
	private String bank = null;
	/* codice canale logico */
	private String channel = null;

	// NCH provare ad eliminare questo campo (che e' sensato solo verso la comunicazione ad host)
	private String channelHost = null;
	/* codice ISO lingua di riferimento utente */
	private String language = null;

	private String sessionId = null;
	private String actionId = null;
	private String loginName = null;


	// NCH provare ad eliminare questo campo (che e' sensato solo verso la comunicazione ad host)
    // passarlo ad esempio nello USERDATA

	/**
	 *  Sistema Informativo del Cliente
	 **/
	private String originalHost = null;

	public UserContext( String bank,
						String channel,
						String channelHost,
						String language,
						String sessionId,
						String actionId,
						String loginName) {

		this.bank = bank;
		this.channel = channel;
		this.language = language;

		this.sessionId = sessionId;
		this.actionId = actionId;
		this.loginName = loginName;
		this.channelHost = channelHost;

        this.locale = new Locale(language, language.toUpperCase());

		UserData data = UserDataFactory.getInstance();
		setUserData(data);
	}

	/**
	 * @return
	 */
	public String getActionId() {
		return (actionId == null)? "<no-action>" : actionId;
	}

	/**
	 * @return
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * @return
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @return
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @return
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param string
	 */
	public void setActionId(String string) {
		actionId = string;
	}

	/**
	 * @param string
	 */
	public void setBank(String string) {
		bank = string;
	}

	/**
	 * @param string
	 */
	public void setChannel(String string) {
		channel = string;
	}

	/**
	 * @param string
	 */
	public void setLanguage(String string) {
		language = string;
	}

	/**
	 * @param string
	 */
	public void setSessionId(String string) {
		sessionId = string;
	}

	/**
	 * @return
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param string
	 */
	public void setLoginName(String string) {
		loginName = string;
	}

	/**
	 * Returns the originalHost.
	 * @return String
	 */
	public String getOriginalHost() {
		return originalHost;
	}

	/**
	 * Method setOriginalHost.
	 * @param originalHost
	 */
	public void setOriginalHost(String originalHost) {
		this.originalHost = originalHost;
	}

	/**
	 * @return Returns the channelHost.
	 */
	public String getChannelHost() {
		return channelHost;
	}

	/**
	 * @param channelHost The channelHost to set.
	 */
	public void setChannelHost(String channelHost) {
		this.channelHost = channelHost;
	}

   	private UserData userData=null;
    private final static UserData NULL_USER_DATA=new NullUserData();

	/**
	 * Aggiunge l'oggetto di dati utenti che
	 * dipendono dall'applicazione e profilazione
	 */

	public final void setUserData(UserData data) {
		userData = data;
	}

	/**
	 * recupero l'oggetto di dati utenti che
	 * dipendono dall'applicazione e profilazione
	 *
	 * Se non e' settato alcun userData viene restituito un NullUserData
	 */

	public UserData getUserData() {
		return (userData==null)?NULL_USER_DATA:userData;
	}

}

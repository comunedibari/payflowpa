/*
 * Created on Jul 18, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.profile;



/**
 * Interfaccia che rappresenta un gestore di profili.
 *
 * @author MaranesiL
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IProfileManager{

	public static String PROFILE_SESSION_PREFIX = "cderf4w5f4w5ferwvw45grw_";

	/**
	 * @return Abi azienda in sessione
	 */
	public String getAbi();

	/**
	 * @return Azienda in sessione
	 */
	public String getAzienda();

	/**
	 * @return Canale
	 */
	public String getCanale();

	/**
	 * @return Certificato utente in sessione
	 */
	public String getCertificato();

	/**
	 * @return Timestamp di collegamento
	 */
	public Long getCollegamento();

	/**
	 * @return Lingua utilizzata
	 */
	public String getLingua();

	/**
	 * @return Codice MAC in sessione
	 */
	//public String getMac() throws SessionException;
	public String getMac();

	/**
	 * @return Password utente in sessione
	 */
	public String getPassword();

	/**
	 * @return ID di sessione
	 */
	public String getSessionID();

	/**
	 * @return Stato della sessione
	 */
	public String getStato();

	/**
	 * @return Timestamp ultima operazione eseguita
	 */
	//public Long getUltimaOperazione() throws SessionException;
	public Long getUltimaOperazione();
	/**
	 * @return Username utente in sessione
	 */
	public String getUsername();



  /**
   * @return Abi accentratore azienda in sessione (see getAbiAzienda()).
   */
  public String getAbiAccentratore();

  /**
   * @return Abi azienda in sessione
   */
  public String getAbiAzienda();
  /**
   * @return Cab azienda in sessione
   */
  public String getCab();
  /**
   * @return Cap azienda in sessione
   */
  public String getCap();

  /**
   * @return Codice fiscale azienda in sessione
   */
  public String getCodiceFiscale();

  /**
   * @return Comune azienda in sessione
   */
  public String getComune();

  /**
   * @return Importo massimo flusso spedibile per l'azienda in sessione
   */
  public Long getImportoMax();

  /**
   * @return Indirizzo azienda in sessione
   */
  //TODO getindirizzo
  //public String getIndirizzo();

  /**
   * @return Codice Host azienda in sessione
   */
  public String getLapl();

  /**
   * @return Provincia azienda in sessione
   */
  public String getProvincia();

  /**
   * @return Ragione sociale azienda in sessione
   */
  public String getRagioneSocialeAzienda();

  /**
   * @return Codice SIA azienda in sessione
   */
  public String getSia();

  /**
   * @return Tipo intestatario azienda in sessione
   */
  public String getTipoIntestatario();

  /**
   * @return Tipo di sicurezza azienda in sessione
   */
  public String getTipoSicurezza();

  /**
	 * @param funzione (il codice a tre lettere del servizio)
	 * @return Un Bean contente le abilitazioni per le operazioni elementari di un servizio
	 *
	 * Questa funzione è pensata per abilitare/disabilitare i bottoni CLIENT SIDE in funzione
	 * delle abilitazioni di profilazione estesa che un operatore ha.
	 * Per i controlli serverside si utilizzi il metodo isOperatoreAbilitatoFunzioneServizio();
	 *
	 * Il metodo segue la seguente logica:
	 *
	 * a) se funzione è null oppure stringa vuota oppure è un servizio che non prevede la profilazione estesa
	 * 	  ==> NULL
	 * b) Se il servizio è soggetto a profilazione estesa il risultato dipende dall'operatore:
	 * 		1. Operatore con flag PROFILAZIONEESTESA=0 or null: => il metodo restituisce un bean che lo abilita a tutte
	 *         le operazioni elementari del servizio.
	 * 		2. Operatore con flag PROFILAZIONEESTESA=1: il metodo restituisce il contenuto della tabella JLTOPEA per quel servizio.
	 * 		3. Operatore = Operatore DEMO: il metodo restituisce un bean che lo abilita a tutte
	 * 	 	   le operazioni elementari del servizio.
	 *
	 * Questa funzione riconosce infatti l'utente definito per la Demo On Line  su IBIS.properties
	 * (properties: azDemo,opDemo,pwDemo) e per tale utente abilita di ufficio tutte le operazioni.
	 * La motivazione è che l'utente DEMO deve poter cliccare tutti i bottoni dell'applicativo salvo poi venir bloccato dai
	 * controlli server side.
	 */
  	//TODO getFlagsAbilitazioni
   	FlagsAbilitazioniBean getFlagsAbilitazioni(String codiceServizio);

	/**
 	* @return true se il profilo recuperato dalla Sessione è NULL.
 	*/
	public boolean isProfileNull();

	/**
 	* @param codiceF = codice servizio a tre lettere.
 	* @param flag = un flag relativo ad un'operazione elementare: @see it.nch.profile.ProfileConstants per i valori
 	*        utilizzabili.
 	* @return true se l'operatore è abilitato o meno all'operazione elementare (flag) del servizio.
 	*
 	* Questo metodo è da utilizzarsi per i controlli SERVER SIDE in quanto non controlla l'utente DEMO.
 	* Se CodiceF==null oppure "" o l'utente non possiede le abilitazioni estese per il servizio torna false;
 	* Per tutti gli utenti che hanno PROFILAZIONEESTESA a 0 o null: torna true.
 	*
 	**/
	public boolean isOperatoreAbilitatoFunzioneServizio(String codiceServizio, String flagAbilitazione);

	/**
	 *
	 * @param codiceServizio = codice servizio a tre lettere.
	 * @return true se l'operatore è abilitato al servizio.
	 *
	 * Questo metodo è da utilizzarsi per i controlli SERVER SIDE come ridondanza sull'effettiva
	 * abilitazione del servizio. Si consiglia di utilizzarlo solo prima di una transazione (esempio creazione o spedizione
	 * di un flusso) in modo da non affidare al menù web soltanto il compito di gestire l'accesso degli utenti ai servizi.
	 *
	 */
	public boolean isOperatoreAbilitatoServizio(String codiceServizio);

	public boolean hasProfilazioneEstesa();

	public boolean isOperatoreAbilitatoAzioneSuFlusso(String codiceServizio, String azioneFlusso, String statoFlusso);

	/**
	 * @return Controllo e aggiornamento MAC in sessione
	 */
	//public String validateMac(String pSessionID, String pMac, String username) throws SessionException;
	public String validateMac(String pSessionID, String pMac, String username);

	public String getCategoria();
	
//	public String getEMail();
	
	public String getEmailPagante();
	
	public String getCodFiscalePagante();
	
	public Boolean isRedirectOnly();
	
	public String getIdEnte();
	
	public String getCdEnte();


	/////////////

}

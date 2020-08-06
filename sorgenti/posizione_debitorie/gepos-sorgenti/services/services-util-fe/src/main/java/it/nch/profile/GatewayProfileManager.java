/*
 * Created on Jul 18, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.profile;

import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.is.fo.profilo.CurrentCorporateVOCommon;
import it.nch.is.fo.profilo.OperatoriCommon;

import java.io.Serializable;



/**
 * Interfaccia che rappresenta un gestore di profili per Iris Gateway Webapp.
 *
 * @author Pazzik
 *
 */
public class GatewayProfileManager extends ProfileManager implements IProfileManager, Serializable{

	public static String PROFILE_SESSION_PREFIX = "cderf4w5f4w5ferwvw45grw_";
	
	private FrontEndContext fec;
	
	private String codPagante;
	
	private String emailPagante;
	
	private Boolean redirectOnly;
	
	/**
	 * Costruttore di un IrisProfileManager di default.
	 */
	public GatewayProfileManager(){}

	/**
	 * Costruttore di un IrisProfileManager con il FrontEndContext
	 * ricevuto in ingresso.
	 *
	 * @param fec il FrontEndContext
	 */
	public GatewayProfileManager(FrontEndContext fec)  {
		this.fec = fec;
	}

	/**
	 * @return Abi azienda in sessione
	 */
	public String getAbi() {
		return null;
	}

	/**
	 * @return Azienda in sessione
	 */
	public String getAzienda() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getCorporateIForm();
	}


	/**
	 * @return Canale
	 */
	public String getCanale() {
		return null;
	}

	/**
	 * @return Certificato utente in sessione
	 */
	public String getCertificato() {
		return null;
	}

	/**
	 * @return Timestamp di collegamento
	 */
	public Long getCollegamento() {
		return null;
	}

	/**
	 * @return Lingua utilizzata
	 */
	public String getLingua() {
		return null;
	}

	/**
	 * @return Codice MAC in sessione
	 */
	public String getMac() {
		return null;
	}

	/**
	 * @return Password utente in sessione
	 */
	public String getPassword() {
		return null;
	}

	/**
	 * @return ID di sessione
	 */
	public String getSessionID() {
		return fec.getHttpSessionID();
	}

	/**
	 * @return Stato della sessione
	 */
	public String getStato() {
		return null;
	}

	/**
	 * @return Timestamp ultima operazione eseguita
	 */
	//public Long getUltimaOperazione() throws SessionException;
	public Long getUltimaOperazione() {
		return null;
	}
	/**
	 * @return Username utente in sessione
	 */
	public String getUsername() {
		return null;
	}



  /**
   * @return Abi accentratore azienda in sessione (see getAbiAzienda()).
   */
  public String getAbiAccentratore() {
	return null;
}

  /**
   * @return Abi azienda in sessione
   */
  public String getAbiAzienda() {
	return null;
}
  /**
   * @return Cab azienda in sessione
   */
  public String getCab() {
	return null;
}
  /**
   * @return Cap azienda in sessione
   */
  public String getCap() {
	return null;
}

  /**
   * @return Codice fiscale azienda in sessione
   */
  public String getCodiceFiscale() {
	  CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
	  return intes.getFiscalCodeIForm();
}
  


  /**
   * @return Comune azienda in sessione
   */
  public String getComune() {
	return null;
}

  /**
   * @return Importo massimo flusso spedibile per l'azienda in sessione
   */
  public Long getImportoMax() {
	return null;
}

  /**
   * @return Indirizzo azienda in sessione
   */
  //TODO getindirizzo
  //public String getIndirizzo();

  /**
   * @return Codice Host azienda in sessione
   */
  public String getLapl() {
	  CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
	  return intes.getLaplCodeIForm();
}

  
  /**
   * @return Provincia azienda in sessione
   */
  public String getProvincia() {
	return null;
}

  /**
   * @return Ragione sociale azienda in sessione
   */
  public String getRagioneSocialeAzienda() {
	return null;
}

  /**
   * @return Codice SIA azienda in sessione
   */
  public String getSia() {
	return null;
}

  /**
   * @return Tipo intestatario azienda in sessione
   */
  public String getTipoIntestatario() {
	return null;
}

  /**
   * @return Tipo di sicurezza azienda in sessione
   */
  public String getTipoSicurezza() {
	return null;
}

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
  	public  FlagsAbilitazioniBean getFlagsAbilitazioni(String codiceServizio) {
	return null;
}

	/**
 	* @return true se il profilo recuperato dalla Sessione è NULL.
 	*/
	public boolean isProfileNull() {
		return false;
	}

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
	public boolean isOperatoreAbilitatoFunzioneServizio(String codiceServizio, String flagAbilitazione) {
		return false;
	}

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
	public boolean isOperatoreAbilitatoServizio(String codiceServizio) {
		return false;
	}

	public boolean hasProfilazioneEstesa() {
		return false;
	}

	public boolean isOperatoreAbilitatoAzioneSuFlusso(String codiceServizio, String azioneFlusso, String statoFlusso) {
		return false;
	}

	/**
	 * @return Controllo e aggiornamento MAC in sessione
	 */
	//public String validateMac(String pSessionID, String pMac, String username) throws SessionException;
	public String validateMac(String pSessionID, String pMac, String username) {
		return null;
	}

	public String getCategoria() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getCategoria();
	}


	/////////////

	@Override
	public FrontEndContext getFec() {
		return fec;
	}

	@Override
	public void setFec(FrontEndContext fec) {
		this.fec = fec;
	}
	
	public boolean isAnonymous(){
		return getAzienda().equals("ANONYMOUS") && getLapl().equals("ANONYMOUS");
	}
	
	@Override
	public String getCodFiscalePagante() {
		
		return codPagante;
		
	}
	
	public void setCodFiscalePagante(String codPagante) {
		this.codPagante = codPagante;
	}
	
//	@Override
//	public String getEMail(){
//		OperatoriCommon op = (OperatoriCommon)fec.getOperatore().getPojo();
//		return op.getEmailIForm();
//	}
	
	@Override
	public String getEmailPagante() {
		return emailPagante;
	}

	public void setEmailPagante(String emailPagante) {
		this.emailPagante = emailPagante;
	}
	
	

	public Boolean getRedirectOnly() {
		return redirectOnly;
	}

	public void setRedirectOnly(Boolean redirectOnly) {
		this.redirectOnly = redirectOnly;
	}

	@Override
	public Boolean isRedirectOnly() {
		return redirectOnly;
	}
	
	@Override
	public String getIdEnte() {
		//Non applicabile
		return null;
	}
	
	@Override
	public String getCdEnte() {
		//Non applicabile
		return null;
	}
}

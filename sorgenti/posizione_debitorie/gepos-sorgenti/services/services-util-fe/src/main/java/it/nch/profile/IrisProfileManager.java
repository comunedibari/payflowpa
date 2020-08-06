/*
 * Created on Jul 18, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.profile;

import it.nch.fwk.fo.common.Traslator;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.is.fo.profilo.CurrentCorporateVOCommon;
import it.nch.is.fo.profilo.OperatoriCommon;
import it.nch.utility.IbisConfiguration;
import it.nch.utility.exception.NotFoundException;

import java.io.Serializable;


/**
 * Implementazione di IProfileManager per IDP.
 *
 * @author repettil
 *
 */
public class IrisProfileManager extends ProfileManager implements  IProfileManager, Serializable {

	//private Profile profileBean;
	private FrontEndContext fec;
	//private ProfiloserviceServiceLocal profiloService;
	private static String usernameDemo = null;
	private static String aziendaDemo = null;

	private static final String STR_NULL = "";
	private static final Long LONG_NULL = 1L;


	private static boolean isUsernameDemo(String username, String azienda) {

		boolean returnValue = false;

		if (usernameDemo == null && aziendaDemo == null) {
			try {
				usernameDemo = IbisConfiguration.Get("Ibis", "opDemo");
				aziendaDemo = IbisConfiguration.Get("Ibis", "azDemo");
				if (usernameDemo == null) {
					usernameDemo = "";
				}

				if (aziendaDemo == null) {
					aziendaDemo = "";
				}
			} catch (NotFoundException e) {
				usernameDemo = "";
				aziendaDemo = "";
			}
		}

		returnValue = usernameDemo.equals(username)
				&& aziendaDemo.equals(azienda);

		return returnValue;
	}

	/**
	 * Costruttore di un IrisProfileManager di default.
	 */
	public IrisProfileManager(){}

	/**
	 * Costruttore di un IrisProfileManager con il FrontEndContext
	 * ricevuto in ingresso.
	 *
	 * @param fec il FrontEndContext
	 */
	public IrisProfileManager(FrontEndContext fec)  {
		this.fec = fec;
	}



	public FlagsAbilitazioniBean getFlagsAbilitazioni(String funzione) {

		FlagsAbilitazioniBean abilitazioniBean = null;

		if (funzione != null && !funzione.equals("")) {
			if (!hasProfilazioneEstesa()) {
				// Se non ha profilazione estesa ha Tutte le operazioni
				// abilitate
				// Questo ramo copre il 99,9% dei casi.
				abilitazioniBean = new FlagsAbilitazioniBean(funzione);
			} else {
				// Per l'utente DEMO
				// ritornato tutto abilitato
				boolean isDemoUser = isUsernameDemo(this.getUsername(), this.getAzienda());

				if (isDemoUser) {
					// Tutto abilitato client side per l'utente DEMO
					abilitazioniBean = new FlagsAbilitazioniBean(funzione);
				} else {
//					if (this.flagAbilitazioneServizio(funzione) != null) {
//						// Caso normale: leggo il bean dal profilo.
//						abilitazioniBean = this.profileBean
//								.flagAbilitazioneServizio(funzione);
//					} else {
//						// Se null sul profilo torna un bean tutto disabilitato
//						abilitazioniBean = FlagsAbilitazioniBean
//								.createAllDisabled(funzione);
//					}
				}
			}
		}

		return abilitazioniBean;
	}


	/**
	 *
	 */
	public String getAzienda() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getCorporateIForm();
	}

	/**
	 *
	 */
	public String getCanale() {
		return this.STR_NULL;
	}

	/**
	 *
	 */
	public String getCertificato() {
		return this.STR_NULL;
	}

	/**
	 *
	 */
	public Long getCollegamento() {
		return 1L;
	}

	/**
	 *
	 */
	public String getLingua() {
		return this.STR_NULL;
	}

	/**
	 *
	 */
	public String getMac() {

		/*
		 * // L'invocazione di questo metodo comporta SEMPRE un recupero del
		 * dato // fresco da DB in quanto il MAC può cambiare tra due diverse
		 * invocazioni.
		 *
		 * System.out.println("getMac()___Called");
		 *
		 * String currentSessionID = profileBean.getSessionID(); String
		 * returnValue = retrieveProfileBean(currentSessionID).getMac();
		 *
		 * return returnValue;
		 */

		return this.STR_NULL;

	}

	/**
	 * Significa controllare: - mac del sessionID
	 */
	public String validateMac(String pSessionID, String pMac, String username) {
		return this.STR_NULL;
	}

	/**
	 *
	 */
	public String getPassword() {
		OperatoriCommon op = (OperatoriCommon)fec.getOperatore().getBusinessObject();
		return op.getPasswordIForm();
	}

	/**
	 *
	 */
	public String getSessionID() {
		return fec.getHttpSessionID();
	}

	/**
	 *
	 */
	public String getStato() {
		return this.STR_NULL;
	}

	/**
	 *
	 */
	public Long getUltimaOperazione() {
		return this.LONG_NULL;
	}

	/**
	 *
	 */
	public String getUsername() {
		return fec.getUsername();
	}

	/**
	 *
	 */
	public String getAbiAccentratore() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getAbiaccentratoreIForm();
	}


	/**
	 *
	 */
	public String getCab() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getCabCodeIForm();
	}

	/**
	 *
	 */
	public String getCap() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getCapCodeIForm();
	}

	/**
	 *
	 */
	public String getCodiceFiscale() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getFiscalCodeIForm();
	}

	/**
	 *
	 */
	public String getComune() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getCityIForm();
	}


	/**
	 *
	 */
	public Long getImportoMax() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		Double ret = Traslator.stringToDouble(intes.getImportoMaxFlussoIForm());
		return (ret != null) ? ret.longValue() : null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see it.nch.profile.IProfileManager#getIndirizzo()
	 */
//	public String getIndirizzo() {
//		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getBusinessObject();
//		return intes.get;
//	}

	/**
	 * @see it.nch.profile.IProfileManager#getLapl()
	 */
	public String getLapl() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getLaplCodeIForm();
	}

	/**
	 * @see it.nch.profile.IProfileManager#getProvincia()
	 */
	public String getProvincia() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getProvinceIForm();
	}

	/**
	 * @see it.nch.profile.IProfileManager#getRagioneSocialeAzienda()
	 */
	public String getRagioneSocialeAzienda() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getDenominazioneIForm();
	}

	/**
	 * @see it.nch.profile.IProfileManager#getSia()
	 */
	public String getSia() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getSiaCodeIForm();
	}

	/**
	 * @see it.nch.profile.IProfileManager#getSiaCbi()
	 */
	public String getSiaCbi() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getSiaCbiCodeIForm();
	}

	/**
	 * @see it.nch.profile.IProfileManager#getTipoIntestatario()
	 */
	public String getTipoIntestatario() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getTipointestatarioIForm();
	}

	/**
	 * @see it.nch.profile.IProfileManager#getTipoSicurezza()
	 */
	public String getTipoSicurezza() {
		return STR_NULL;
	}

	/**
	 * @see it.nch.profile.IProfileManager#isProfileNull()
	 */
	public boolean isProfileNull() {
		return (this.fec == null);
	}

	/**
	 * Qui l'operatore DEMO non viene controllato
	 */
	public boolean isOperatoreAbilitatoFunzioneServizio(String codiceF,
			String flag) {
		boolean result = false;

		if (!hasProfilazioneEstesa()) {
			// Se l'operatore non è soggetto a profilazione estesa:
			// Abilitato senza ulteriori indagini
			result = true;
		}
		else if ((codiceF != null) && !"".equals(codiceF)) {
//			FlagsAbilitazioniBean b = this.profileBean.flagAbilitazioneServizio(codiceF);
//			if (b != null) {
//				result = b.getAbilitazione(flag);
//			}
		}
		return result;
	}

	/**
	 * @see it.nch.profile.IProfileManager#isOperatoreAbilitatoFunzione(java.lang.String)
	 */
	public boolean isOperatoreAbilitatoServizio(String codiceF) {
		boolean result = true;
		return result;
	}

	public boolean hasProfilazioneEstesa() {

		return false;
	}

	/**
	 * @see
	 * it.nch.profile.IProfileManager#isOperatoreAbilitatoAzioneSuFlusso(java.lang.String, java.lang.String)
	 */
	public boolean isOperatoreAbilitatoAzioneSuFlusso(String codiceServizio,
			String azioneFlusso, String statoFlusso) {

		boolean abilitato = true;

		if (hasProfilazioneEstesa()) {
			if (azioneFlusso.equalsIgnoreCase("gestioneAtomica")) {

				abilitato = isOperatoreAbilitatoFunzioneServizio(
						codiceServizio, ProfileConstants.FLAG_CREATEFLUX)
						&& isOperatoreAbilitatoFunzioneServizio(codiceServizio,
								ProfileConstants.FLAG_SIGNFLUX)
						&& isOperatoreAbilitatoFunzioneServizio(codiceServizio,
								ProfileConstants.FLAG_SENDFLUX);

			} else if (azioneFlusso.equalsIgnoreCase("creazione")) {

				abilitato = isOperatoreAbilitatoFunzioneServizio(
						codiceServizio, ProfileConstants.FLAG_CREATEFLUX);

			} else if (azioneFlusso.equalsIgnoreCase("firma1")) {

				abilitato = isOperatoreAbilitatoFunzioneServizio(
						codiceServizio, ProfileConstants.FLAG_SIGNFLUX);

			} else if (azioneFlusso.equalsIgnoreCase("firma2")) {

				abilitato = isOperatoreAbilitatoFunzioneServizio(
						codiceServizio, ProfileConstants.FLAG_SIGNFLUX);

			} else if (azioneFlusso.equalsIgnoreCase("firmaCongiunta")) {

				abilitato = isOperatoreAbilitatoFunzioneServizio(
						codiceServizio, ProfileConstants.FLAG_SIGNFLUX);

			} else if (azioneFlusso.equalsIgnoreCase("creaFirmaCongiunta")) {

				abilitato = isOperatoreAbilitatoFunzioneServizio(
						codiceServizio, ProfileConstants.FLAG_CREATEFLUX)
						&& isOperatoreAbilitatoFunzioneServizio(codiceServizio,
								ProfileConstants.FLAG_SIGNFLUX);

			} else if (azioneFlusso.equalsIgnoreCase("creaFirma1")) {

				abilitato = isOperatoreAbilitatoFunzioneServizio(
						codiceServizio, ProfileConstants.FLAG_CREATEFLUX)
						&& isOperatoreAbilitatoFunzioneServizio(codiceServizio,
								ProfileConstants.FLAG_SIGNFLUX);

			} else if (azioneFlusso.equalsIgnoreCase("firma1Spedizione")) {

				abilitato = isOperatoreAbilitatoFunzioneServizio(
						codiceServizio, ProfileConstants.FLAG_SIGNFLUX)
						&& isOperatoreAbilitatoFunzioneServizio(codiceServizio,
								ProfileConstants.FLAG_SENDFLUX);

			} else if (azioneFlusso
					.equalsIgnoreCase("firmaCongiuntaSpedizione")) {

				abilitato = isOperatoreAbilitatoFunzioneServizio(
						codiceServizio, ProfileConstants.FLAG_SIGNFLUX)
						&& isOperatoreAbilitatoFunzioneServizio(codiceServizio,
								ProfileConstants.FLAG_SENDFLUX);

			} else if (azioneFlusso.equalsIgnoreCase("firma2Spedizione")) {

				abilitato = isOperatoreAbilitatoFunzioneServizio(
						codiceServizio, ProfileConstants.FLAG_SIGNFLUX)
						&& isOperatoreAbilitatoFunzioneServizio(codiceServizio,
								ProfileConstants.FLAG_SENDFLUX);

			} else if (azioneFlusso.equalsIgnoreCase("spedizione")) {

				abilitato = isOperatoreAbilitatoFunzioneServizio(
						codiceServizio, ProfileConstants.FLAG_SENDFLUX);

			} else {
				// Azione sconosciuta: abilitato=true
			}
		}

		return abilitato;
	}

	@Override
	public FrontEndContext getFec() {
		return fec;
	}
	@Override
	public void setFec(FrontEndContext fec) {
		this.fec = fec;
	}

//	public ProfiloserviceServiceLocal getProfiloService() {
//		return profiloService;
//	}
//
//	public void setProfiloService(ProfiloserviceServiceLocal profiloService) {
//		this.profiloService = profiloService;
//	}

	@Override
	public String getAbiAzienda() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getAbiCodeIForm();
	}

//	@Override
//	public it.nch.profile.FlagsAbilitazioniBean getFlagsAbilitazioni(
//			String codiceServizio) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public String getAbi() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getAbiCodeIForm();
	}

	@Override
	public String getCategoria() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getCategoria();
	}

//	@Override
//	public String getEMail() {
//		OperatoriCommon op = (OperatoriCommon)fec.getOperatore().getBusinessObject();
//		return op.getEmailIForm();
//	}
	
	@Override
	public String getEmailPagante() {
		// TODO Auto-generated method stub
		return "fake@email.it";
	}

	@Override
	public String getCodFiscalePagante() {
		// TODO Auto-generated method stub
		return "MZZSND60A01A944G";
	}

	@Override
	public Boolean isRedirectOnly() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getIdEnte() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getIdEnte();
	}
	
	@Override
	public String getCdEnte() {
		CurrentCorporateVOCommon intes = (CurrentCorporateVOCommon)fec.getAziendaCorrente().getPojo();
		return intes.getCdEnte();
	}

}

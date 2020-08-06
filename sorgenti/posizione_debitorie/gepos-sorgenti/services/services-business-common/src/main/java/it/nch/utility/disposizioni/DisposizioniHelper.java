/*
 * Created on Jul 16, 2008 by Riccardo Cannas
 *
 */
package it.nch.utility.disposizioni;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @author Riccardo
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DisposizioniHelper {

	public static final String STATO_DISPOSIZIONE_INSERITA = "INSERITA";
	public static final String STATO_DISPOSIZIONE_ERR_IMPORTAZIONE = "ERR.IMPORTAZIONE";
	public static final String STATO_DISPOSIZIONE_ERR_CREAZIONE = "ERR.CREAZIONE";
	public static final String STATO_DISPOSIZIONE_IN_ATTESA = "IN ATTESA";
	public static final String STATO_DISPOSIZIONE_CREATA = "CREATA";
	public static final String STATO_DISPOSIZIONE_UNA_FIRMA = "UNA FIRMA";
	public static final String STATO_DISPOSIZIONE_DUE_FIRME = "DUE FIRME";
	public static final String STATO_DISPOSIZIONE_SPEDITA = "SPEDITA";
	public static final String STATO_DISPOSIZIONE_IN_ERRORE = "IN ERRORE";
	public static final String STATO_DISPOSIZIONE_ACCETTATA = "ACCETTATA";
	public static final String STATO_DISPOSIZIONE_RIFIUTATA = "RIFIUTATA";
	public static final String STATO_DISPOSIZIONE_ERRATA = "ERRATA";
	public static final String STATO_DISPOSIZIONE_RIFIUTATA_RETE = "RIFIUTATA RETE";
	public static final String STATO_DISPOSIZIONE_IN_CARICO_BANCA = "IN CARICO BANCA";
	public static final String STATO_DISPOSIZIONE_PARZ_ACCETTATA = "PARZ.ACCETTATA";
	public static final String STATO_DISPOSIZIONE_OPERAZIONE_IN_CORSO = "OPERAZIONEINCORSO";

	public static final String STATO_FLUSSO_GATEWAY_ERRORE = "GATEWAY.ERRORE";
	public static final String STATO_FLUSSO_ABBANDONATO = "ABBANDONATO";
	public static final String STATO_FLUSSO_ERR_CREAZIONE = "ERR.CREAZIONE";
	public static final String STATO_FLUSSO_IN_ATTESA = "IN ATTESA";
	public static final String STATO_FLUSSO_CREATO = "CREATO";
	public static final String STATO_FLUSSO_UNA_FIRMA = "UNA FIRMA";
	public static final String STATO_FLUSSO_DUE_FIRME = "DUE FIRME";
	public static final String STATO_FLUSSO_SPEDITI = "SPEDITI";
	public static final String STATO_FLUSSO_IN_ERRORE = "IN ERRORE";
	public static final String STATO_FLUSSO_ACCETTATO = "ACCETTATO";
	public static final String STATO_FLUSSO_RIFIUTATO = "RIFIUTATO";
	public static final String STATO_FLUSSO_ERRATO = "ERRATO";
	public static final String STATO_FLUSSO_RIFIUTATA_RETE = "RIFIUTATO RETE";
	public static final String STATO_FLUSSO_IN_CARICO_BANCA = "IN CARICO BANCA";
	public static final String STATO_FLUSSO_PARZ_ACCETTATO = "PARZ.ACCETTATO";
	public static final String STATO_FLUSSO_OPERAZIONE_IN_CORSO = "OPERAZIONEINCORSO";
	
	public static final String STATO_DISTINTA_APERTA = "APERTE";
	public static final String STATO_DISTINTA_CHIUSA = "CHIUSE";
	public static final String STATO_DISTINTA_INVIATA = "INVIATE";

	private static final Map MAPPA_STATO_FLUSSO_IN_DISPOSIZIONE = initMappaStatoFlussoInDisposizione();
	private static final List STATI_DISPOSIZIONE = initStatiDisposizione();
	private static final List STATI_SPEDIZIONE = initStatiSpedizione();	
	
	private static final List STATI_DISPOSIZIONI_INVIATE = initStatiDisposizioneInviate();
	private static final List STATI_DISPOSIZIONI_NONINVIATE = initStatiDisposizioneNonInviate();
	private static final List STATI_DISPOSIZIONI_ANNULLABILI = initStatiDisposizioneAnnullabili();
	private static final List STATI_DISPOSIZIONI_SPEDIBILI = initStatiDisposizioneSpedibili();	
	
	private static final List STATI_SPEDIZIONI_NONINVIATE = initStatiSpedizioniNonInviate();
	private static final List STATI_SPEDIZIONI_ANNULLABILI = initStatiSpedizioniAnnullabili();
	private static final List STATI_SPEDIZIONI_SPEDIBILI = initStatiSpedizioniSpedibili();	
	private static final List STATI_SPEDIZIONI_DUPLICABILI	= initStatiSpedizioniDuplicabili();
	private static final List STATI_SPEDIZIONI_FINALI	= initStatiSpedizioniFinali();
	
	private static final List STATI_DISTINTE_APERTE = initStatiDistinteAperte();
	private static final List STATI_DISTINTE_CHIUSE = initStatiDistinteChiuse();	
	private static final List STATI_DISTINTE_INVIATE = initStatiDistinteInviate();	

	public static final String FLUSSO_ASSENTE = "NO";
	public static final String FLUSSO_PRESENTE = "SI";

	private static final Map initMappaStatoFlussoInDisposizione() {
		Map retval = new HashMap();
		retval.put(STATO_FLUSSO_ERR_CREAZIONE, STATO_DISPOSIZIONE_ERR_CREAZIONE);
		retval.put(STATO_FLUSSO_IN_ATTESA, STATO_DISPOSIZIONE_IN_ATTESA);
		retval.put(STATO_FLUSSO_CREATO, STATO_DISPOSIZIONE_CREATA);
		retval.put(STATO_FLUSSO_UNA_FIRMA, STATO_DISPOSIZIONE_UNA_FIRMA);
		retval.put(STATO_FLUSSO_DUE_FIRME, STATO_DISPOSIZIONE_DUE_FIRME);
		retval.put(STATO_FLUSSO_SPEDITI, STATO_DISPOSIZIONE_SPEDITA);
		retval.put(STATO_FLUSSO_IN_ERRORE, STATO_DISPOSIZIONE_IN_ERRORE);
		retval.put(STATO_FLUSSO_ACCETTATO, STATO_DISPOSIZIONE_ACCETTATA);
		retval.put(STATO_FLUSSO_RIFIUTATO, STATO_DISPOSIZIONE_RIFIUTATA);
		retval.put(STATO_FLUSSO_ERRATO, STATO_DISPOSIZIONE_ERRATA);
		retval.put(STATO_FLUSSO_RIFIUTATA_RETE, STATO_DISPOSIZIONE_RIFIUTATA_RETE);
		retval.put(STATO_FLUSSO_IN_CARICO_BANCA, STATO_DISPOSIZIONE_IN_CARICO_BANCA);
		retval.put(STATO_FLUSSO_PARZ_ACCETTATO, STATO_DISPOSIZIONE_PARZ_ACCETTATA);
		retval.put(STATO_FLUSSO_OPERAZIONE_IN_CORSO, STATO_DISPOSIZIONE_OPERAZIONE_IN_CORSO);
		return retval;
	}

	private static final List initStatiDisposizione() {
		List retval = new Vector();
		retval.add(STATO_DISPOSIZIONE_INSERITA);
//		retval.add(STATO_DISPOSIZIONE_ERR_IMPORTAZIONE);
//		retval.add(STATO_DISPOSIZIONE_ERR_CREAZIONE);
//		retval.add(STATO_DISPOSIZIONE_CREATA);
//		retval.add(STATO_DISPOSIZIONE_UNA_FIRMA);
//		retval.add(STATO_DISPOSIZIONE_DUE_FIRME);
//		retval.add(STATO_DISPOSIZIONE_SPEDITA);
//		retval.add(STATO_DISPOSIZIONE_IN_ERRORE);
//		retval.add(STATO_DISPOSIZIONE_ACCETTATA);
//		retval.add(STATO_DISPOSIZIONE_RIFIUTATA);
//		retval.add(STATO_DISPOSIZIONE_ERRATA);
//		retval.add(STATO_DISPOSIZIONE_RIFIUTATA_RETE);
//		retval.add(STATO_DISPOSIZIONE_IN_CARICO_BANCA);
//		retval.add(STATO_DISPOSIZIONE_PARZ_ACCETTATA);
		retval.add(STATO_FLUSSO_ERR_CREAZIONE);
		retval.add(STATO_FLUSSO_IN_ATTESA);
		retval.add(STATO_FLUSSO_CREATO);
		retval.add(STATO_FLUSSO_UNA_FIRMA);
		retval.add(STATO_FLUSSO_DUE_FIRME);
		
		retval.add(STATO_FLUSSO_SPEDITI);
		retval.add(STATO_FLUSSO_IN_ERRORE);
		retval.add(STATO_FLUSSO_ACCETTATO);
		retval.add(STATO_FLUSSO_RIFIUTATO);
		retval.add(STATO_FLUSSO_ERRATO);
		retval.add(STATO_FLUSSO_RIFIUTATA_RETE);
		retval.add(STATO_FLUSSO_IN_CARICO_BANCA);
		retval.add(STATO_FLUSSO_PARZ_ACCETTATO);		
		return retval;
	}
	
	private static final List initStatiSpedizione() {
		List retval = new Vector();
		retval.add(STATO_FLUSSO_ERR_CREAZIONE);
		retval.add(STATO_FLUSSO_IN_ATTESA);
		retval.add(STATO_FLUSSO_CREATO);
		retval.add(STATO_FLUSSO_UNA_FIRMA);
		retval.add(STATO_FLUSSO_DUE_FIRME);
		
		retval.add(STATO_FLUSSO_SPEDITI);
		retval.add(STATO_FLUSSO_IN_ERRORE);
		retval.add(STATO_FLUSSO_ACCETTATO);
		retval.add(STATO_FLUSSO_RIFIUTATO);
		retval.add(STATO_FLUSSO_ERRATO);
		retval.add(STATO_FLUSSO_RIFIUTATA_RETE);
		retval.add(STATO_FLUSSO_IN_CARICO_BANCA);
		retval.add(STATO_FLUSSO_PARZ_ACCETTATO);		
		return retval;
	}	
	
	private static final List initStatiDisposizioneSpedibili() {
		List retval = new Vector();
		retval.add(STATO_DISPOSIZIONE_INSERITA);
		retval.add(STATO_DISPOSIZIONE_CREATA);
		retval.add(STATO_FLUSSO_CREATO);
		retval.add(STATO_FLUSSO_UNA_FIRMA);
		retval.add(STATO_FLUSSO_DUE_FIRME);		
		return retval;
	}	
	
	private static final List initStatiDisposizioneAnnullabili() {
		List retval = new Vector();
		retval.add(STATO_FLUSSO_ERR_CREAZIONE);
		retval.add(STATO_FLUSSO_IN_ATTESA);
		retval.add(STATO_FLUSSO_CREATO);
		retval.add(STATO_FLUSSO_UNA_FIRMA);
		retval.add(STATO_FLUSSO_DUE_FIRME);		
		retval.add(STATO_FLUSSO_IN_ERRORE);
		return retval;
	}	
	
	/**
	 * preparo un elenco di stati delle dispo inviate
	 * tutti gli stati mappano direttamente sul campo stato del flusso
	 * @return
	 */
	private static final List initStatiDisposizioneInviate() {
		List retval = new Vector();
	
		retval.add(STATO_FLUSSO_SPEDITI);		
		retval.add(STATO_FLUSSO_ACCETTATO);
		retval.add(STATO_FLUSSO_RIFIUTATO);
		retval.add(STATO_FLUSSO_ERRATO);
		retval.add(STATO_FLUSSO_RIFIUTATA_RETE);
		retval.add(STATO_FLUSSO_IN_CARICO_BANCA);
		retval.add(STATO_FLUSSO_PARZ_ACCETTATO);
		return retval;
	}
	
	
	private static final List initStatiDistinteInviate() {
		List retval = new Vector();
	
		retval.add(STATO_FLUSSO_SPEDITI);		
		retval.add(STATO_FLUSSO_ACCETTATO);
		retval.add(STATO_FLUSSO_RIFIUTATO);
		retval.add(STATO_FLUSSO_ERRATO);
		retval.add(STATO_FLUSSO_RIFIUTATA_RETE);
		retval.add(STATO_FLUSSO_IN_CARICO_BANCA);
		retval.add(STATO_FLUSSO_PARZ_ACCETTATO);
		return retval;
	}	
	
	/**
	 * preparo un elenco di stati delle sped inviate
	 * tutti gli stati mappano direttamente sul campo stato del flusso
	 * @return
	 */
	private static final List initStatiSpedizioniNonInviate() {
		List retval = new Vector();
	
		retval.add(STATO_FLUSSO_ERR_CREAZIONE);
		retval.add(STATO_FLUSSO_IN_ERRORE);
		retval.add(STATO_FLUSSO_IN_ATTESA);
		retval.add(STATO_FLUSSO_CREATO);
		retval.add(STATO_FLUSSO_UNA_FIRMA);
		retval.add(STATO_FLUSSO_DUE_FIRME);	
		return retval;
	}
	
	
	/**
	 * preparo un elenco di stati delle sped inviate
	 * tutti gli stati mappano direttamente sul campo stato del flusso
	 * @return
	 */
	private static final List initStatiDistinteAperte() {
		List retval = new Vector();
	
		retval.add(STATO_DISPOSIZIONE_INSERITA);
		return retval;
	}	
	
	/**
	 * preparo un elenco di stati delle sped annullabili
	 * @return
	 */
	private static final List initStatiSpedizioniAnnullabili() {
		List retval = new Vector();
	
		retval.add(STATO_FLUSSO_ERR_CREAZIONE);
		retval.add(STATO_FLUSSO_IN_ATTESA);
		retval.add(STATO_FLUSSO_CREATO);
		retval.add(STATO_FLUSSO_UNA_FIRMA);
		retval.add(STATO_FLUSSO_DUE_FIRME);
		retval.add(STATO_FLUSSO_IN_ERRORE);
		return retval;
	}
	
	
	/**
	 * preparo un elenco di stati delle sped spedibili
	 * @return
	 */
	private static final List initStatiSpedizioniSpedibili() {
		List retval = new Vector();
	
		retval.add(STATO_FLUSSO_CREATO);
		retval.add(STATO_FLUSSO_UNA_FIRMA);
		retval.add(STATO_FLUSSO_DUE_FIRME);
		return retval;
	}
	
	
	/**
	 * preparo un elenco di stati delle sped spedibili
	 * @return
	 */
	private static final List initStatiDistinteChiuse() {
		List retval = new Vector();	
		retval.add(STATO_FLUSSO_CREATO);
		retval.add(STATO_FLUSSO_UNA_FIRMA);
		retval.add(STATO_FLUSSO_DUE_FIRME);
		retval.add(STATO_FLUSSO_ERR_CREAZIONE);
		retval.add(STATO_FLUSSO_IN_ATTESA);
		retval.add(STATO_FLUSSO_IN_ERRORE);
		return retval;
	}	
	
	/**
	 * preparo un elenco di stati delle sped duplicabili
	 * @return
	 */
	private static final List initStatiSpedizioniDuplicabili() {
		List retval = new Vector();
	
		retval.add(STATO_FLUSSO_IN_CARICO_BANCA);
		retval.add(STATO_FLUSSO_SPEDITI);
		retval.add(STATO_FLUSSO_ACCETTATO);
		return retval;
	}
	
	/**
	 * preparo un elenco di stati delle sped finali, 
	 * quelli per cui si fa vedere lo stato finale anche quando 
	 * non sono passati 15 minuti dall inizio dell'operazione
	 * @return
	 */
	private static final List initStatiSpedizioniFinali() {
		List retval = new Vector();
	
		retval.add(STATO_FLUSSO_IN_ERRORE);
		retval.add(STATO_FLUSSO_ERR_CREAZIONE);
		retval.add(STATO_FLUSSO_SPEDITI);
		return retval;
	}			
	
	/**
	 * preparo un elenco di stati delle dispo NON inviate
	 * tutti gli stati mappano direttamente 
	 * sul campo flusso della tabella corrispondente
	 * 
	 * @return
	 */
	private static final List initStatiDisposizioneNonInviate() {
		List retval = new Vector();
		retval.add(STATO_DISPOSIZIONE_INSERITA);
//		retval.add(STATO_DISPOSIZIONE_ERR_IMPORTAZIONE);
		
		retval.add(STATO_FLUSSO_ERR_CREAZIONE);
		retval.add(STATO_FLUSSO_IN_ERRORE);
		retval.add(STATO_FLUSSO_IN_ATTESA);
		retval.add(STATO_FLUSSO_CREATO);
		retval.add(STATO_FLUSSO_UNA_FIRMA);
		retval.add(STATO_FLUSSO_DUE_FIRME);		
		return retval;
	}	
	

	public static List getElencoStatiDistinteAperte(){
		return STATI_DISTINTE_APERTE;
	}
	
	public static List getElencoStatiDistinteChiuse(){
		return STATI_DISTINTE_CHIUSE;
	}
		
	public static List getElencoStatiDistinteInviate(){
		return STATI_DISTINTE_INVIATE;
	}		

	public static List getElencoStatiDisposizione(){
		return STATI_DISPOSIZIONE;
	}	
	
	public static List getElencoStatiSpedizione(){
		return STATI_SPEDIZIONE;
	}	
	
	public static List getElencoStatiDisposizioneInviate(){
		return STATI_DISPOSIZIONI_INVIATE;
	}
	
	public static List getElencoStatiDisposizioneNonInviate(){
		return STATI_DISPOSIZIONI_NONINVIATE;
	}	
	
	public static List getElencoStatiSpedizioniNonInviate(){
		return STATI_SPEDIZIONI_NONINVIATE;
	}
	
	public static List getElencoStatiSpedizioniAnnullabili(){
		return STATI_SPEDIZIONI_ANNULLABILI;
	}
	
	public static List getElencoStatiSpedizioniSpedibili(){
		return STATI_SPEDIZIONI_SPEDIBILI;
	}		
	
	public static List getElencoStatiDisposizioniAnnullabili(){
		return STATI_DISPOSIZIONI_ANNULLABILI;
	}
	
	public static List getElencoStatiDisposizioniSpedibili(){
		return STATI_DISPOSIZIONI_SPEDIBILI;
	}	
	public static List getElencoStatiSpedizioniDuplicabili(){
		return STATI_SPEDIZIONI_DUPLICABILI;
	}			
	
	// Scorro la mappa in cerca del valore( stato della disposizione) che corrisponda
	// allo stato del flusso passato. Quando lo trovo restituisco la chiave (stato flusso)
	public static String recuperaStatoFlusso(String statoDisposizione) {
		statoDisposizione = (statoDisposizione == null) ? statoDisposizione : statoDisposizione.trim();
		Iterator keyIter = MAPPA_STATO_FLUSSO_IN_DISPOSIZIONE.keySet().iterator();

		String statoFlusso = null;
		while (keyIter.hasNext() && statoFlusso == null) {
			String currStatoFlusso = (String) keyIter.next();
			if (MAPPA_STATO_FLUSSO_IN_DISPOSIZIONE.get(currStatoFlusso).equals(statoDisposizione)) {
				statoFlusso = currStatoFlusso;
			}
		}
		return statoFlusso;
	}
	
	public static String recuperaStatoDisposizioneDaStatoFlusso(String statoFlusso) {
		statoFlusso = (statoFlusso == null) ? statoFlusso : statoFlusso.trim();
		String statoDisposizione = (String) MAPPA_STATO_FLUSSO_IN_DISPOSIZIONE.get(statoFlusso);
		return statoDisposizione;
	}

	/**
	 * Calcola lo stato di una disposizione priva di flusso collegato
	 * 
	 * @param statoDisposizione
	 * @param erroreImportazione
	 * @return
	 */
	public static String calcolaStatoDisposizioneSenzaFlusso(String statoDisposizione, String erroreImportazione) {
		return calcolaStatoDisposizione(statoDisposizione, null, erroreImportazione, null);
	}

	/**
	 * Calcola lo stato di una disposizione
	 * 
	 * @param statoDisposizione campo stato dell'oggetto disposizione
	 * @param flusso campo flusso dell'oggetto disposizione
	 * @param erroreImportazione campo erroreImportazione dell'oggetto Disposizione
	 * @param statoFlusso campo stato dell'oggetto Flusso
	 * @return stato corrente del flusso
	 */
	//1140001091171005LW2K idDisposizione x inserita
	//11449263225000002W3K idDisposizione x Err.importazione
	//1094028590594000FW2K idDisposizione x ALTRO
	public static String calcolaStatoDisposizione(String statoDisposizione, String flusso, String erroreImportazione, String statoFlusso) {
		statoDisposizione = (statoDisposizione == null) ? statoDisposizione : statoDisposizione.trim();
		statoFlusso = (statoFlusso == null) ? statoFlusso : statoFlusso.trim();
		String stato;
		if (isEmpty(statoDisposizione)) {
			if (isEmpty(flusso) || FLUSSO_ASSENTE.equals(flusso)) {
				if (isEmpty(erroreImportazione)) {
					stato = STATO_DISPOSIZIONE_INSERITA;
				} else {
					stato = STATO_DISPOSIZIONE_ERR_IMPORTAZIONE;
				}
			} else {
				stato = (String) MAPPA_STATO_FLUSSO_IN_DISPOSIZIONE.get(statoFlusso);
			}
		} else {
			stato = statoDisposizione;
		}
		//		getStati();
//		System.out.println("CALCOLO STATO = statoDisposizione " + statoDisposizione + " + statoFlusso " +statoFlusso + " flusso " + flusso );
//		System.out.println("STATO CALCOLATO = " + stato );
		return stato;
	}

	public static boolean isOperazioneFlussoInCorso(String stato, Timestamp tsupdate, Timestamp tsfineupdate) {
		//System.out.println("isOperazioneFlussoInCorso "+stato+" - "+" - "+tsupdate+" - "+tsfineupdate);
		boolean operazioneInCorso = false;
		if ((stato != null 
				&& (!stato.equalsIgnoreCase(STATO_DISPOSIZIONE_INSERITA) 
				&& !stato.equalsIgnoreCase(STATO_DISPOSIZIONE_ERR_IMPORTAZIONE)))
				&& tsupdate!= null) {
			Calendar delay = Calendar.getInstance();
			delay.add(Calendar.MINUTE, -15);
			Calendar updateTime = Calendar.getInstance(); 
			updateTime.setTime(tsupdate);
					
			Date now = new Date();
			operazioneInCorso = !STATO_FLUSSO_IN_ERRORE.equalsIgnoreCase(stato.trim()) 
				&& (tsfineupdate == null || tsfineupdate.after(now))
				&& delay.before(updateTime);
		}
		//System.out.println("isOperazioneFlussoInCorso "+operazioneInCorso);
		//riabilitato 07 gen 2009
		return operazioneInCorso;
		//disabilitato per demo 11 nov 2008
//		return false;
	}
	/**
	 * Verifica se la disposizione è ESEGUIBILE, per farlo non necessita dello stato del flusso anche nel caso 
	 * abbia un flusso abbinato. Infatti per calcolare lo stati INSERITA non necessito di sapere lo stato del flusso
	 * 
	 * @param statoDisposizione
	 * @param flusso
	 * @param erroreImportazione
	 * @return
	 */
	public static boolean isDisposizioneEseguibile(String statoDisposizione, String flusso, String erroreImportazione) {
		String stato = calcolaStatoDisposizione(statoDisposizione, flusso, erroreImportazione, null);
		return (STATO_DISPOSIZIONE_INSERITA.equals(stato));
	}

	/**
	 * Verifica se la disposizione è MODIFICABILE, per farlo non necessita dello stato del flusso anche nel 
	 * caso abbia un flusso abbinato. Infatti per calcolare gli stati INSERITA e ERRORE IMPORTAZIONE non 
	 * necessito di sapere lo stato del flusso
	 * 
	 * @param statoDisposizione
	 * @param flusso
	 * @param erroreImportazione
	 * @return
	 */
	public static boolean isDisposizioneModificabile(String statoDisposizione, String flusso, String erroreImportazione) {
		String stato = calcolaStatoDisposizione(statoDisposizione, flusso, erroreImportazione, null);
		return (STATO_DISPOSIZIONE_INSERITA.equals(stato) || STATO_DISPOSIZIONE_ERR_IMPORTAZIONE.equals(stato));
	}
	
	
	/**
	 * Verifica se la disposizione è MODIFICABILE, per farlo non necessita dello stato del flusso anche nel 
	 * caso abbia un flusso abbinato. Infatti per calcolare gli stati INSERITA e ERRORE IMPORTAZIONE non 
	 * necessito di sapere lo stato del flusso
	 * 
	 * @param statoDisposizione
	 * @param flusso
	 * @param erroreImportazione
	 * @return
	 */
	public static boolean isDisposizioneNecessitaAnnullamentoSpedizione(String statoDisposizione, String flusso, String erroreImportazione) {
		String stato = calcolaStatoDisposizione(statoDisposizione, flusso, erroreImportazione, null);
		return (STATO_DISPOSIZIONE_CREATA.equals(stato) || 
				STATO_DISPOSIZIONE_DUE_FIRME.equals(stato) || 
				STATO_DISPOSIZIONE_UNA_FIRMA.equals(stato) ||
				STATO_DISPOSIZIONE_IN_ERRORE.equals(stato) ||
				STATO_DISPOSIZIONE_IN_ATTESA.equals(stato)
				);
	}
	
	
	/**
	 * 
	 * @param statoDisposizione
	 * @param flusso
	 * @param erroreImportazione
	 * @return
	 */
	public static boolean isDisposizioneSpedibile(String statoDisposizione, String flusso, String erroreImportazione) {
		String stato = calcolaStatoDisposizione(statoDisposizione, flusso, erroreImportazione, null);		
		return DisposizioniHelper.getElencoStatiDisposizioniSpedibili().contains(stato);
	}
	
	/**
	 * 
	 * @param statoDisposizione
	 * @param flusso
	 * @param erroreImportazione
	 * @return
	 */
	public static boolean isSpedizioneAnnullabile(String statoFlusso) {
		return DisposizioniHelper.getElencoStatiDisposizioniAnnullabili().contains(statoFlusso);
	}	
	
	
	/**
	 * 
	 * @param statoDisposizione
	 * @param flusso
	 * @param erroreImportazione
	 * @return
	 */	
	public static boolean isSpedizioneCancellabile(String statoDisposizione, String flusso, String erroreImportazione) {
		String stato = calcolaStatoDisposizione(statoDisposizione, flusso, erroreImportazione, null);
		return (STATO_DISPOSIZIONE_CREATA.equals(stato) || 
				STATO_DISPOSIZIONE_DUE_FIRME.equals(stato) || 
				STATO_DISPOSIZIONE_UNA_FIRMA.equals(stato) ||
				STATO_DISPOSIZIONE_IN_ERRORE.equals(stato) ||
				STATO_DISPOSIZIONE_IN_ATTESA.equals(stato)
				);
	}	

	/**
	 * Verifica se la disposizione è CANCELLABILE, per farlo non necessita dello stato del flusso anche nel 
	 * caso abbia un flusso abbinato. Infatti per calcolare gli stati INSERITA e ERRORE IMPORTAZIONE non 
	 * necessito di sapere lo stato del flusso
	 * 
	 * @param statoDisposizione
	 * @param flusso
	 * @param erroreImportazione
	 * @return
	 */
	public static boolean isDisposizioneCancellabile(String statoDisposizione, String flusso, String erroreImportazione) {
		return (isDisposizioneModificabile(statoDisposizione, flusso, erroreImportazione));
	}

	private static boolean isEmpty(String s) {
		return (s == null || s.trim().length() == 0);
	}

	public static Map getStati() {
		return MAPPA_STATO_FLUSSO_IN_DISPOSIZIONE;

	}
}

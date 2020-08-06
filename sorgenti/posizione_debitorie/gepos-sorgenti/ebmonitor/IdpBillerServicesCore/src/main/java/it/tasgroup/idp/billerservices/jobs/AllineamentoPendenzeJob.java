package it.tasgroup.idp.billerservices.jobs;

import it.tasgroup.idp.billerservices.api.LoaderException;
import it.tasgroup.idp.billerservices.api.plugin.DatiPiazzaturaFlusso;
import it.tasgroup.idp.billerservices.jobs.status.AllineamentoPendenzeJobStatus;

import java.io.InputStream;

import javax.ejb.Local;
import javax.ejb.Timer;


/**
 * Processo di allineamento delle pendenze.
 * Può essere scatenato sia da un web service (Loader) sia
 * da un qualsiasi altro sistema di alimentazione che faccia
 * pervenire il flusso.
 * 
 * @author RepettiS
 *
 */
@Local
public interface AllineamentoPendenzeJob {

	/** 
	 * Metodo che permette di estrarre la struttura di piazzatura del flusso
	 * ovvero la chiave univoca del flusso ( senderId, systemId, e2eMsgId ),
	 * i parametri di elaborazione, i dati di testata del flusso.   
	 * Il job instanzia il plugin corretto per elaborare il flusso, in base al 
	 * parametro tipoFile.
	 * Questo metodo ha due signature ed accetta il file sia come stringa che 
	 * come InputStream.
	 * Il metodo lancia una LoaderException se il flusso non è parsabile, non è 
	 * conforme alla sintassi prevista dal Plugin, non contiene i dati necessari per la
	 * piazzatura (SenderId, TipoDebito, etc.).
	 * 
	 * @param inputFile
	 * @param tipoFile
	 * @return
	 * @throws LoaderException
	 */
	DatiPiazzaturaFlusso piazzaturaFlusso(String inputFile, String tipoFile) throws LoaderException ;
	DatiPiazzaturaFlusso piazzaturaFlusso(InputStream inputFile, String tipoFile) throws LoaderException ;

	/** 
	 * Registrazione della trasmissione (su tabella PENDENZE_CART)    
	 * Questo metodo ha due signature ed accetta il file sia come stringa che 
	 * come InputStream.
	 * 
	 * @param inputFile
	 * @param tipoFile
	 * @return
	 * @throws LoaderException
	 */	
	void registrazioneTrasmissione(DatiPiazzaturaFlusso datiPiazzaturaFlusso, String file);
	void registrazioneTrasmissione(DatiPiazzaturaFlusso datiPiazzaturaFlusso, InputStream file);

	
	/**
	 * Metodo che realizza il processing effettivo del flusso.
	 * 
	 * @param datiPiazzaturaFlusso
	 * @return
	 */
	AllineamentoPendenzeJobStatus doJob(DatiPiazzaturaFlusso datiPiazzaturaFlusso) ;

	
	/**
	 * Metodo che permette di lanciare il job in modo asincrono dopo "sleepBefore" millisecondi
	 * @param datiPiazzaturaFlusso
	 * @param sleepBefore
	 */
	void fork(DatiPiazzaturaFlusso datiPiazzaturaFlusso, long sleepBefore);	
	
	/**
	 * Metodo necessario per attivazione asincrona, è quello che 
	 * viene chiamato dal Timer al raggiungimento del Timeout.
	 * @param timer
	 */
	void excecuteJobAsynch(Timer timer);

}

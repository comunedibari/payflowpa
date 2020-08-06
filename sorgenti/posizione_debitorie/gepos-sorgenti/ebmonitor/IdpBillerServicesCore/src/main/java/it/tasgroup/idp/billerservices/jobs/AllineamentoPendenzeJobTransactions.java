package it.tasgroup.idp.billerservices.jobs;

import javax.ejb.Local;

import it.tasgroup.idp.billerservices.api.GestorePendenze.EnumEsitoAllineamentoPendenza;
import it.tasgroup.idp.billerservices.api.GestoreTrasmissioni.EnumStatoTrasmissione;
import it.tasgroup.idp.billerservices.api.LoaderException;
import it.tasgroup.idp.billerservices.api.plugin.DatiPiazzaturaFlusso;
import it.tasgroup.idp.billerservices.api.plugin.ILoaderPlugin.DatiAllineamento;
import it.tasgroup.idp.domain.messaggi.ErroriEsitiPendenza;
import it.tasgroup.idp.domain.messaggi.EsitiPendenza;

/**
 * Singoli Task, in transazione separata, lanciati
 * da AllineamentoPendenzeJob
 * @author user
 *
 */
@Local
public interface AllineamentoPendenzeJobTransactions {

	/*
	 * Registra una trasmissione (in transazione separata e autoconsistente)
	 */
	void registrazioneTrasmissione(DatiPiazzaturaFlusso datiPiazzaturaFlusso, String file);

	/*
	 * Cambia lo stato di una trasmissione
	 */	
	void cambioStatoTrasmissione(DatiPiazzaturaFlusso datiPiazzaturaFlusso, EnumStatoTrasmissione nuovoStato);
	
	/*
	 * Registra l'esito di una trasmissione (in transazione separata e autoconsistente)
	 */
	void registrazioneEsitoTrasmissione(DatiPiazzaturaFlusso datiPiazzaturaFlusso, String msgEsito, EnumStatoTrasmissione nuovoStatoTrasmissione);

	/*
	 * Esegue l'operazione di allineamento
	 */
	EnumEsitoAllineamentoPendenza allineamentoPendenza(DatiAllineamento datiAllineamento) throws LoaderException;
	
	/*
	 * Salvataggio dell'esito
	 */
	void persistEsitoPendenza(EsitiPendenza esito, ErroriEsitiPendenza errore);

}

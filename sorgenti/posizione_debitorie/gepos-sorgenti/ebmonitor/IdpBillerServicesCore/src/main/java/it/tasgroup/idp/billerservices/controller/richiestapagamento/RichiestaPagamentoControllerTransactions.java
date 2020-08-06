package it.tasgroup.idp.billerservices.controller.richiestapagamento;

import it.tasgroup.idp.billerservices.api.LoaderException;
import it.tasgroup.idp.xmlbillerservices.ws.richiestapagamento.RichiestaPagamento;
import it.tasgroup.idp.xmlbillerservices.ws.richiestapagamento.RichiestaPagamentoResponse;

import javax.ejb.Local;

/**
 * Transazioni separate implicate dal processo ComunicazionePosizioniDebitorieControllerImpl
 * @author user
 *
 */
@Local
public interface RichiestaPagamentoControllerTransactions {
	
	/**
	 * Inserisce pendenze, sessione gateway e tabelle di tracking. In singola transazione
	 * @param allineamentoPendenzeOTF
	 * @return
	 */
	RichiestaPagamentoResponse insertAndOpenSession(RichiestaPagamento allineamentoPendenzeOTF)  throws LoaderException;
	
	
	/**
	 * Inserisce nelle tabelle di tracking l'esito di una chiamata in errore
	 * @param allineamentoPendenzeOTF
	 * @param esito
	 * @param inputRequestBytes
	 * @param outputRequestBytes
	 */
	void traceCallKO(RichiestaPagamento allineamentoPendenzeOTF,RichiestaPagamentoResponse esito);

	 
	
}

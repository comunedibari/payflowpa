package it.tasgroup.idp.billerservices.controller;

import java.io.ByteArrayOutputStream;

import it.tasgroup.idp.billerservices.api.LoaderException;
import it.tasgroup.iris.domain.SessioneGateway;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeMultiEnteOTF;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeMultiEnteOTFEsito;

import javax.ejb.Local;

/**
 * Transazioni separate implicate dal processo ComunicazionePosizioniDebitorieControllerImpl
 * @author user
 *
 */
@Local
public interface ComunicazionePosizioniDebitorieControllerTransactions {
	
	/**
	 * Inserisce pendenze, sessione gateway e tabelle di tracking. In singola transazione
	 * @param allineamentoPendenzeOTF
	 * @return
	 */
	IdpAllineamentoPendenzeMultiEnteOTFEsito insertAndOpenSession(IdpAllineamentoPendenzeMultiEnteOTF allineamentoPendenzeOTF)  throws LoaderException;
	
	
	/**
	 * Inserisce nelle tabelle di tracking l'esito di una chiamata in errore
	 * @param allineamentoPendenzeOTF
	 * @param esito
	 * @param inputRequestBytes
	 * @param outputRequestBytes
	 */
	void traceCallKO(IdpAllineamentoPendenzeMultiEnteOTF allineamentoPendenzeOTF,IdpAllineamentoPendenzeMultiEnteOTFEsito esito);

	 
	
}

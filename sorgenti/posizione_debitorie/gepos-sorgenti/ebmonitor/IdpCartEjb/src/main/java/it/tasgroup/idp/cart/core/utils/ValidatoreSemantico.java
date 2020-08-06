package it.tasgroup.idp.cart.core.utils;

import it.tasgroup.idp.cart.core.dto.IdpHeaderDTO;
import it.tasgroup.idp.cart.core.dto.SPCoopHeaderDTO;
import it.tasgroup.idp.cart.core.exception.IrisException;

/**
 * Gestisce la validazione semantica dei messaggi iris.
 * @author Lorenzo Nardi (nardi@link.it)
 * @version $Id: ValidatoreSemantico.java 412 2013-07-26 08:02:25Z nardi $
 */
public class ValidatoreSemantico {
	boolean isCentrale = false;
	String service = "";
	boolean isRichiesta = true;
	
	public ValidatoreSemantico(boolean isCentrale, String service){
		this.isCentrale = isCentrale;
		this.service = service;
	}
	
	public ValidatoreSemantico(boolean isCentrale, String service, boolean isRichiesta){
		this.isCentrale = isCentrale;
		this.service = service;
		this.isRichiesta = isRichiesta;
	}
	
	/**
	 * Operazione di validazione del messaggio.
	 * @param req Servlet per l'accesso agli headers http
	 * @param msg Messaggio da validare
	 * @param service Nome del servizio
	 * @throws Exception in caso di messaggio malformato o di problemi di accesso agli header
	 */
	public void validate(SPCoopHeaderDTO spcoopHeaderDTO, IdpHeaderDTO idpHeaderDTO) throws IrisException {
		validate(spcoopHeaderDTO, idpHeaderDTO, false);
	}
	
	public void validate(SPCoopHeaderDTO spcoopHeaderDTO, IdpHeaderDTO idpHeaderDTO, boolean fromIRIS) throws IrisException {
		
		if ("SPC".compareTo(spcoopHeaderDTO.getSpcoopTipoMittente()) != 0) {
			throw new IrisException("VAL004","SPCoopTipoMittente non valido: [" + spcoopHeaderDTO.getSpcoopTipoMittente() + "]. Tipi consentiti [SPC]","SPCoopTipoMittente non valido: " + spcoopHeaderDTO.getSpcoopTipoMittente());
		}
		if ("SPC".compareTo(spcoopHeaderDTO.getSpcoopTipoDestinatario()) != 0) {
			throw new IrisException("VAL005","SPCoopTipoDestinatario non valido: [" + spcoopHeaderDTO.getSpcoopTipoDestinatario() + "]. Tipi consentiti [SPC]", "SPCoopTipoDestinatario non valido: " + spcoopHeaderDTO.getSpcoopTipoDestinatario());
		}
		if ("SPC".compareTo(spcoopHeaderDTO.getSpcoopTipoServizio()) != 0) {
			throw new IrisException("VAL006","SPCoopTipoServizio non valido: [" + spcoopHeaderDTO.getSpcoopTipoServizio() + "]. Tipi consentiti [SPC]", "SPCoopTipoServizio non valido: " + spcoopHeaderDTO.getSpcoopTipoServizio());
		}
		if (isRichiesta) {
			if (spcoopHeaderDTO.getSpcoopMittente().compareTo(idpHeaderDTO.getSenderId()) != 0) {
				throw new IrisException("VAL007","Il soggetto fruitore [" + spcoopHeaderDTO.getSpcoopMittente() + "] ed il senderId [" + idpHeaderDTO.getSenderId() + "] sono diversi", "Il soggetto fruitore [" + spcoopHeaderDTO.getSpcoopMittente() + "] ed il senderId [" + idpHeaderDTO.getSenderId() + "] sono diversi");
			}
		}
		if ((service).compareTo(idpHeaderDTO.getServiceName()) != 0 ) {
			throw new IrisException("VAL008","Il servizio fruito [" + service + "] ed il ServiceName [" + idpHeaderDTO.getServiceName() + "] sono diversi", "Il servizio specificato [" + service + "] e' diverso da quello fruito [" + idpHeaderDTO.getServiceName() + "]");
		}
		// Non posso fare questo controllo per i messaggi provenienti da IRIS.
		if (isRichiesta && spcoopHeaderDTO.getSpcoopServizioApplicativo().compareTo(idpHeaderDTO.getSenderSys()) != 0 && !isCentrale && !fromIRIS) {
			throw new IrisException("VAL009","Il servizio applicativo fruitore [" + spcoopHeaderDTO.getSpcoopServizioApplicativo() + "] ed il SenderSys [" + idpHeaderDTO.getSenderSys() + "] sono diversi", "Il servizio applicativo mittente specificato [" + idpHeaderDTO.getSenderSys() + "] non e' l'effettivo mittente [" + spcoopHeaderDTO.getSpcoopServizioApplicativo() + "]");
		}
		
	}
}

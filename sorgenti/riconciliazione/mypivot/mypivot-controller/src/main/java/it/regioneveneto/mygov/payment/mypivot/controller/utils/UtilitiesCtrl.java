package it.regioneveneto.mygov.payment.mypivot.controller.utils;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;

/**
 * 
 * @author Marianna Memoli
 *
 */
public class UtilitiesCtrl {

	private static Log logger = LogFactory.getLog(UtilitiesCtrl.class);

	/**
	 * La funzione verifica che l'utente autenticato abbia selezionato l'ente.
	 * 
	 * @return {@link Boolean}
	 * 
	 * @author Marianna Memoli
	 */
	public static boolean hasSecurityEnte() throws Exception {
		
		logger.debug("CONTROLLO SELEZIONE ENTE :: ContextFields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");

		/**
		 * Dal context leggo i dati dell'ente
		 */
		EnteTO enteTO = SecurityContext.getEnte();			/* Get anagrafica ente selezionato come beneficiario */

		/**
		 */
		if (enteTO == null) {
			logger.warn("CONTROLLO SELEZIONE ENTE :: ContextFields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: Ente nullo.");
			/**
			 */
			return false;
		}
		
		logger.debug("CONTROLLO SELEZIONE ENTE :: ContextFields[Ente: " + enteTO.toString() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return true;
	}
	
	/**
	 * La funzione verifica che l'utente autenticato abbia dei ruoli attivi.
	 * 
	 * @return {@link Boolean}
	 * 
	 * @author Marianna Memoli
	 */
	public static boolean hasSecurityRole() throws Exception {
		
		logger.debug("CONTROLLO RUOLI UTENTE :: ContextFields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");

		/**
		 * Dal context leggo i dati dei ruoli
		 */
		List<String> roles = SecurityContext.getRoles();	/* Get ruoli dell'utente autenticato per ente selezionato come beneficiario */

		/**
		 * Controllo se l'utente ha ruoli attivi
		 */
		if (roles == null || roles.isEmpty()) {
			logger.warn("CONTROLLO RUOLI UTENTE :: ContextFields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: Non risultano ruoli attivi.");
			/**
			 */
			return false;
		}
		
		logger.debug("CONTROLLO RUOLI UTENTE :: ContextFields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", Ruoli: " + StringUtils.collectionToCommaDelimitedString(roles) + "] :: END");

		return true;
	}
	
	/**
	 * Ritorna l'oggetto @ModelAndView configurato per la pagina di errore.
	 * 
	 * @param {@link String} keyMessage, key label del messaggio di errore
	 * 
	 * @return {@link ModelAndView}
	 * 
	 * @author Marianna Memoli
	 */
	public static ModelAndView getViewErrorMessage(String keyMessage) {
		
		MessagesDto messagesDto = new MessagesDto();
		/* */
		messagesDto.getErrorMessages().add(new DynamicMessageDto(keyMessage));
		
		/* 
		 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
		 */
		ModelAndView mav = new ModelAndView("message");
		/* */
		mav.addObject("messagesDto", messagesDto);
		/* */
		return mav;
	}
	
	/**
	 *  Ritorna l'oggetto @ModelAndView configurato per la pagina di errore.
	 * 
	 * @param {@link String} keyMessage, key label del messaggio di errore
	 * @param {@link Object[]} arguments, valori da sostituire nella label del messaggio di errore
	 * 
	 * @return {@link ModelAndView}
	 * 
	 * @author Marianna Memoli
	 */
	public static ModelAndView getViewErrorMessage(String keyMessage, Object[] arguments) {
		
		MessagesDto messagesDto = new MessagesDto();
		/* */
		messagesDto.getErrorMessages().add(new DynamicMessageDto(keyMessage, arguments));
		
		/* 
		 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
		 */
		ModelAndView mav = new ModelAndView("message");
		/* */
		mav.addObject("messagesDto", messagesDto);
		/* */
		return mav;
	}

	/**
	 * La funzione verifica che l'utente possa accedere alla sezione delle Statistiche.
	 * I ruoli necessari sono ROLE_ADMIN e/o ROLE_STATS.
	 *  
	 * @return {@link Boolean}
	 * 
	 * @author Marianna Memoli
	 */
	public static boolean hasSecurityAccessFunctionalityCruscottoIncassi() throws Exception {
		
		logger.debug("VERIFICA AUTORIZZAZIONE SEZIONE STATISTICHE :: ContextFields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");

		/**
		 * Get ruoli dell'utente autenticato per ente selezionato come beneficiario
		 */
		List<String> roles = SecurityContext.getRoles();	
		
		/**
		 */
		if (!(roles.contains(Constants.RUOLO_AMMINISTRATORE) || roles.contains(Constants.RUOLO_STATISTICHE))) {
			logger.warn("VERIFICA AUTORIZZAZIONE SEZIONE STATISTICHE :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", Ruoli: " + StringUtils.collectionToCommaDelimitedString(roles) + "] :: L'utente NON È AUTORIZZATO ad accedere alla funzionalità.");
			/**
			 */
			return false;
		}
		
		logger.debug("VERIFICA AUTORIZZAZIONE SEZIONE STATISTICHE :: ContextFields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return true;
	}
}
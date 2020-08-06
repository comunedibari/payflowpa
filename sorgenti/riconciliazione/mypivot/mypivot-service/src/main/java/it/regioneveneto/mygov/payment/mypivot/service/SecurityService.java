/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.service;

import it.regioneveneto.mygov.payment.mypivot.domain.po.Utente;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Giorgio Vallini
 *
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface SecurityService {
	
	/**
	 * @param codFedUserId
	 * @return
	 */
	Utente getUtenteByCodFedUserId(final String codFedUserId);

	/**
	 * @param codFedUserId
	 * @param codCodiceFiscaleUtente
	 * @param flgFedAuthorized
	 * @param deEmailAddress
	 * @param deFirstname
	 * @param deLastname
	 * @param deFedLegalEntity
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	Utente insertUtente(final String codFedUserId, final String codCodiceFiscaleUtente, final String deEmailAddress,
			final String deFirstname, final String deLastname);

	/**
	 * @param codFedUserId
	 * @param codCodiceFiscaleUtente
	 * @param flgFedAuthorized
	 * @param deEmailAddress
	 * @param deFirstname
	 * @param deLastname
	 * @param deFedLegalEntity
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	Utente updateUtenteByCodFedUserId(final String codFedUserId, final String codCodiceFiscaleUtente,
			final String deEmailAddress, final String deFirstname, final String deLastname);


}

/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.dao.UtenteDao;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Utente;
import it.regioneveneto.mygov.payment.mypivot.service.SecurityService;

/**
 * @author Giorgio Vallini
 *
 */
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private UtenteDao utenteDao;

	@Resource
	private Environment env;

	public SecurityServiceImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.regioneveneto.mygov.payment.pa.service.SecurityService#
	 * getUtenteByCodFedUserId(java.lang.String)
	 */
	@Override
	public Utente getUtenteByCodFedUserId(final String codFedUserId) {
		return utenteDao.findByCodFedUserIdIgnoreCase(codFedUserId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.regioneveneto.mygov.payment.pa.service.SecurityService#insertUtente
	 * (java.lang.String, java.lang.String, boolean, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Utente insertUtente(final String codFedUserId, final String codCodiceFiscaleUtente, final String deEmailAddress, final String deFirstname,
			final String deLastname) {

		Utente utente = new Utente();

		boolean componenteAutenticazioneFedera = Boolean.parseBoolean(env.getProperty("mypivot.componente.autenticazione.federa"));
		String finalCodFedUserId = "";
		if (componenteAutenticazioneFedera) {
			String[] codFedUserIdParts = codFedUserId.split("@");
			finalCodFedUserId = codFedUserIdParts[0].toUpperCase() + "@" + codFedUserIdParts[1];
		}
		else {
			finalCodFedUserId = codFedUserId;
		}

		String upperCodCodiceFiscaleUtente = codCodiceFiscaleUtente.toUpperCase();

		utente.setCodFedUserId(finalCodFedUserId);
		utente.setCodCodiceFiscaleUtente(upperCodCodiceFiscaleUtente);
		utente.setDeEmailAddress(deEmailAddress);
		utente.setDeFirstname(deFirstname);
		utente.setDeLastname(deLastname);
		utente.setDtUltimoLogin(new Date());

		return utenteDao.saveAndFlush(utente);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.regioneveneto.mygov.payment.pa.service.SecurityService#
	 * updateUtenteByCodFedUserId(java.lang.String, java.lang.String, boolean,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Utente updateUtenteByCodFedUserId(final String codFedUserId, final String codCodiceFiscaleUtente, final String deEmailAddress,
			final String deFirstname, final String deLastname) {

		Utente utente = utenteDao.findByCodFedUserIdIgnoreCase(codFedUserId);

		boolean componenteAutenticazioneFedera = Boolean.parseBoolean(env.getProperty("mypivot.componente.autenticazione.federa"));
		String finalCodFedUserId = "";
		if (componenteAutenticazioneFedera) {
			String[] codFedUserIdParts = codFedUserId.split("@");
			finalCodFedUserId = codFedUserIdParts[0].toUpperCase() + "@" + codFedUserIdParts[1];
		}
		else {
			finalCodFedUserId = codFedUserId;
		}

		String upperCodCodiceFiscaleUtente = codCodiceFiscaleUtente.toUpperCase();

		utente.setCodFedUserId(finalCodFedUserId);
		utente.setCodCodiceFiscaleUtente(upperCodCodiceFiscaleUtente);
		utente.setDeEmailAddress(deEmailAddress);
		utente.setDeFirstname(deFirstname);
		utente.setDeLastname(deLastname);

		return utenteDao.saveAndFlush(utente);
	}

}

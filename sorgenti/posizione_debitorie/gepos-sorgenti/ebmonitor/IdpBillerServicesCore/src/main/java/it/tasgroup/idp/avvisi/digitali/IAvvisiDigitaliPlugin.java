package it.tasgroup.idp.avvisi.digitali;

import javax.persistence.EntityManager;

import it.tasgroup.idp.avvisi.digitali.exceptions.AvvisiDigitaliException;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.domain.posizioneDebitoria.Pendenze;

public interface IAvvisiDigitaliPlugin {	
	
	/**
	 * 
	 * @param p
	 * @param em
	 * @throws AvvisiDigitaliException
	 */
	void deletePendenza(Pendenze p, EntityManager em) throws AvvisiDigitaliException;
	
	/**
	 * 
	 * @param c
	 * @param em
	 * @throws AvvisiDigitaliException
	 */
	void deleteCondizione(CondizioniPagamento c, EntityManager em) throws AvvisiDigitaliException;
	
	/**
	 * 
	 * @param p
	 * @param em
	 * @throws AvvisiDigitaliException
	 */
	void insertPendenza(Pendenze p, EntityManager em) throws AvvisiDigitaliException;
	
	/**
	 * 
	 * @param p
	 * @param em
	 * @throws AvvisiDigitaliException
	 */
	void updatePendenza(Pendenze p, EntityManager em) throws AvvisiDigitaliException;
	
	/**
	 * 
	 * @param p
	 * @param em
	 * @throws AvvisiDigitaliException
	 */
	void updateCondizione(CondizioniPagamento p, EntityManager em) throws AvvisiDigitaliException;
	
	/**
	 * 
	 * @param p
	 * @param em
	 * @throws AvvisiDigitaliException
	 */	
	void generaBollettinoCondizione(CondizioniPagamento p, EntityManager em) throws AvvisiDigitaliException;
	
}

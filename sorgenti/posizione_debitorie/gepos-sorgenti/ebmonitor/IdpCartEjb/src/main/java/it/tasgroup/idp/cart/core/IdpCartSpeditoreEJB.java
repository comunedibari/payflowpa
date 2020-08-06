package it.tasgroup.idp.cart.core;

import it.tasgroup.idp.cart.core.exception.IdpCartSpeditoreException;

import javax.ejb.Local;


/**
 * @author Lorenzo Nardi (nardi@link.it)
 * @version $Id: GestioneCredenzialiEJB.java 358 2013-05-22 15:32:32Z nardi $
 */

@Local
public interface IdpCartSpeditoreEJB {

	public String processAPE(String ente, String sil, String msgId, String xml) throws IdpCartSpeditoreException;
	public String processIP(String ente, String sil, String msgId, String xml) throws IdpCartSpeditoreException;
	
	public String processAPE(String ente, String sil,String enteIntermediario, String silIntermediario, String msgId, String xml) throws IdpCartSpeditoreException;
	public String processIP(String ente, String sil,String enteIntermediario, String silIntermediario, String msgId, String xml) throws IdpCartSpeditoreException;
	
}

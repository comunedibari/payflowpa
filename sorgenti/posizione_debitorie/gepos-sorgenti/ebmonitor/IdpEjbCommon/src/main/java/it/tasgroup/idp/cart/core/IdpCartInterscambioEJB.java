package it.tasgroup.idp.cart.core;

import javax.ejb.Local;

import it.tasgroup.idp.cart.core.exception.IrisException;
import it.tasgroup.idp.cart.core.exception.MessaggioDuplicatoException;
import it.tasgroup.idp.cart.core.exception.MsgIdDuplicatoException;


/**
 * @author Lorenzo Nardi (nardi@link.it)
 * @version $Id: GestioneCredenzialiEJB.java 358 2013-05-22 15:32:32Z nardi $
 */

@Local
public interface IdpCartInterscambioEJB {

	public void processAP(String ente, String sil, String enteIntermediario, String silIntermediario, String msgId, String hash, String xml) throws MessaggioDuplicatoException, MsgIdDuplicatoException, IrisException;
	public void processIPE(String ente, String sil, String enteIntermediario, String silIntermediario, String msgId, String hash, String xml) throws MessaggioDuplicatoException, MsgIdDuplicatoException, IrisException;
	
	public String processAPOTF(String ente, String sil, String enteIntermediario, String silIntermediario, String msgId, String hash, String xml) throws MessaggioDuplicatoException, MsgIdDuplicatoException, IrisException;
	public String processIPOTF(String ente, String sil, String enteIntermediario, String silIntermediario, String msgId, String hash, String xml) throws MessaggioDuplicatoException, MsgIdDuplicatoException, IrisException;
	
}

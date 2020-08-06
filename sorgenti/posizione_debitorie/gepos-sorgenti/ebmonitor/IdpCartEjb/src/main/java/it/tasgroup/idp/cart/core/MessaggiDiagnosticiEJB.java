package it.tasgroup.idp.cart.core;

import java.util.List;

import javax.ejb.Local;

import org.openspcoop2.core.diagnostica.MessaggioDiagnostico;
import org.openspcoop2.core.diagnostica.management.DiagnosticaServiceException_Exception;

/**
 * 
 * 
 * @author pintori
 *
 */

@Local
public interface MessaggiDiagnosticiEJB {

	public List<MessaggioDiagnostico> getMessaggiDiagnostici(String idEgov) throws DiagnosticaServiceException_Exception;
	public List<MessaggioDiagnostico> getMessaggiDiagnosticiAP_Richiesta(String soggetto, String sil, String msgId) throws DiagnosticaServiceException_Exception;
	public List<MessaggioDiagnostico> getMessaggiDiagnosticiAP_Risposta(String soggetto, String sil, String msgId) throws DiagnosticaServiceException_Exception;
	public List<MessaggioDiagnostico> getMessaggiDiagnosticiIPP_Richiesta(String soggetto, String sil, String msgId) throws DiagnosticaServiceException_Exception;
	public List<MessaggioDiagnostico> getMessaggiDiagnosticiIPP_Risposta(String soggetto, String sil, String msgId) throws DiagnosticaServiceException_Exception;

}

/**
 * 
 */
package it.tasgroup.iris.comunication.helper;

import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.locator.ServiceLocatorException;
import it.nch.fwk.fo.web.util.WebUtil;
import it.nch.is.fo.profilo.CurrentCorporateVOImpl;
import it.nch.utility.exception.SessionException;
import it.tasgroup.iris.comunication.dto.UtentiCanaliDTO;
import it.tasgroup.iris.comunication.ws.client.IrisComunicationWSInvoker;
import it.tasgroup.iris.comunication.ws.impl.StatoCanaleType;
import it.tasgroup.iris.comunication.ws.impl.TipoCanaleType;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author pazzik
 *
 */
public class IRISComunicationHelper {
	
private static final Logger LOG = LogManager.getLogger(IRISComunicationHelper.class.getName());

private IRISComunicationHelper(){}

public static String getEmail(String irisId) {
		
		try {
			
			IrisComunicationWSInvoker comunication = new IrisComunicationWSInvoker();

			boolean isAnonymous = false;
			
			// Recupero tutti i canali associati all'utente
			List<UtentiCanaliDTO> myCanali = comunication.getCanaliComunicazione(irisId, isAnonymous);
			
			for (UtentiCanaliDTO utentiCanaliDTO : myCanali) {
				
				// se il canale è E-MAIL (value1) ed è attivo allora prendo la configurazione 
				if(TipoCanaleType.value1.getValue().equals(utentiCanaliDTO.getDenominazioneCanale())
						&& StatoCanaleType.ATTIVO.getValue().equals(utentiCanaliDTO.getStato())) 
					
					return utentiCanaliDTO.getConfigurazione();
				
			}
			
		} catch (Exception e) {
			
			LOG.error("ERRORE NEL RECUPERO EMAIL DA COMUNICATION", e);
			
		}
		return null;
	}

	/**
	 * Restituisce l'identificativo dell'utente attualmente loggato e che sta effettuando l'operazione, costruito come la concatenazione dei campi INTESTATARIOPERATORI.INTESTATARIO.CORPORATE, 
	 * INTESTATARIOPERATORI.OPERATORE.OPERATORE E INTESTATARIOPERATORI.TP_OPERATORE separati da un trattino
	 * @param request
	 * @return
	 * @throws SessionException
	 * @throws ServiceLocatorException
	 */
	public static String getIrisId(HttpServletRequest request){
		
		FrontEndContext fec = WebUtil.getLocatedFrontEndContext(request);
		
		StringBuilder irisId = new StringBuilder();
		
		
		String tipoIntestatario= fec.getTipoIntestatarioperatore();
		
		if (tipoIntestatario==null)   
			tipoIntestatario="OP";   
		
		irisId.append(((CurrentCorporateVOImpl)fec.getAziendaCorrente().getPojo()).getCorporate())
			  .append("-")
			  .append(fec.getUsername())
			  .append("-")
			  .append(tipoIntestatario);
		
		return irisId.toString();
	}

}

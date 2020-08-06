package it.tasgroup.idp.cart.core;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.ws.BindingProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openspcoop2.core.diagnostica.MessaggioDiagnostico;
import org.openspcoop2.core.diagnostica.management.DiagnosticaNotAuthorizedException_Exception;
import org.openspcoop2.core.diagnostica.management.DiagnosticaNotImplementedException_Exception;
import org.openspcoop2.core.diagnostica.management.DiagnosticaServiceException_Exception;
import org.openspcoop2.core.diagnostica.management.MessaggioDiagnosticoSoap11Service;
import org.openspcoop2.core.diagnostica.management.SearchFilterMessaggioDiagnostico;

import it.tasgroup.idp.cart.core.dao.IIdpCartDbManager;
import it.tasgroup.idp.cart.core.model.GestioneModel;
import it.tasgroup.idp.cart.core.model.TipoGestione;
import it.tasgroup.idp.cart.core.model.TipoMessaggio;
import it.tasgroup.idp.util.IrisProperties;

/***
 * 
 * 
 * @author pintori
 *
 */

@Stateless
public class MessaggiDiagnosticiEJBImpl implements MessaggiDiagnosticiEJB {

	private Log log;
	private String url = null;
	private String username = null;
	private String password = null;
	private org.openspcoop2.core.diagnostica.management.MessaggioDiagnostico port = null;
	
	@EJB(beanName = "IdpCartDbManager")
	protected IIdpCartDbManager idpCartDbManager;

	public MessaggiDiagnosticiEJBImpl() {
		this.log = LogFactory.getLog(this.getClass());

		this.port = new MessaggioDiagnosticoSoap11Service().getMessaggioDiagnosticoPortSoap11();

		try{
			this.url = IrisProperties.getProperty("messaggidiagnostici.url");
			this.username = IrisProperties.getProperty("messaggidiagnostici.username");
			this.password = IrisProperties.getProperty("messaggidiagnostici.password");
			
			this.log.info("Servizio di ricerca diagnostici si trova alla url ["+this.url+"]");
		}catch(Exception e){
			this.log.error("Errore durante la lettura delle properties: "+e.getMessage(), e );
		}

		((BindingProvider)port).getRequestContext().put("thread.local.request.context", "true"); 
		if(this.username != null && this.password != null){
			((BindingProvider)port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, username);
			((BindingProvider)port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
		}
		((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
	}


	@Override
	public List<MessaggioDiagnostico> getMessaggiDiagnostici(String idEgov) throws DiagnosticaServiceException_Exception{

		log.info("Ricerca dei messaggi diagnostici per l'idEgov ["+idEgov+"] in corso...");

		if(idEgov == null)
			return null;

		SearchFilterMessaggioDiagnostico filter = new SearchFilterMessaggioDiagnostico();
		// E la risposta?
		filter.setIdentificativoRichiesta(idEgov);
		try {
			log.info("Invocazione del servizio diagnostici alla url ["+this.url+"].");
			
			List<MessaggioDiagnostico> response = this.port.findAll(filter);
			log.info("Ricerca dei messaggi diagnostici per l'idEgov ["+idEgov+"] completata. Trovati ["+(response != null ? response.size()  : 0)+"]");
			return response;
		} catch (DiagnosticaServiceException_Exception e) {
			log.error("Si e' verificato un errore durante la ricerca dei messaggi diagnostici per l'idEgov ["+idEgov+"]",e);
			throw e;
		} catch (DiagnosticaNotAuthorizedException_Exception e) {
			log.error("Si e' verificato un errore durante la ricerca dei messaggi diagnostici per l'idEgov ["+idEgov+"]",e);
			throw new DiagnosticaServiceException_Exception(e.getMessage(), e);
		} catch (DiagnosticaNotImplementedException_Exception e) {
			log.error("Si e' verificato un errore durante la ricerca dei messaggi diagnostici per l'idEgov ["+idEgov+"]",e);
			throw new DiagnosticaServiceException_Exception(e.getMessage(), e);
		} catch (Exception e){
			log.error("Si e' verificato un errore durante la ricerca dei messaggi diagnostici per l'idEgov ["+idEgov+"]",e);
			throw new DiagnosticaServiceException_Exception(e.getMessage(), e);
		}
	}

	@Override
	public List<MessaggioDiagnostico> getMessaggiDiagnosticiAP_Richiesta(String soggetto, String sil, String msgId) throws DiagnosticaServiceException_Exception {

		TipoMessaggio tipoMessaggio = TipoMessaggio.ALLINEAMENTO_PENDENZE;
		TipoGestione tipoSpedizione = TipoGestione.INBOUND;

		log.info("Ricerca dei messaggi diagnostici per il Messaggio ["+tipoSpedizione+"] ID ["+msgId+"]di tipo ["+ tipoMessaggio+"] Soggetto ["+soggetto+"] Sil ["+sil+"], calcolo IdEgov in corso ...");

		String idEgov = getIdEgov(soggetto, sil, msgId, tipoMessaggio, tipoSpedizione);

		log.info("Ricerca dei messaggi diagnostici per il Messaggio ["+tipoSpedizione+"] ID ["+msgId+"]di tipo ["+ tipoMessaggio+"] Soggetto ["+soggetto+"] Sil ["+sil+"], IdEgov trovato ["+idEgov+"].");

		return this.getMessaggiDiagnostici(idEgov);
	}


	private String getIdEgov(String soggetto, String sil, String msgId, TipoMessaggio tipoMessaggio,
			TipoGestione tipoSpedizione) throws DiagnosticaServiceException_Exception {
		log.info("Ricerca IDEgov per il Messaggio di ["+tipoSpedizione+"] ID ["+msgId+"]di tipo ["+ tipoMessaggio+"] Soggetto ["+soggetto+"] Sil ["+sil+"] in corso ...");

		GestioneModel spedizione = null;

		try {
			spedizione = this.idpCartDbManager.getUltimaGestione(soggetto, sil, msgId, tipoMessaggio, tipoSpedizione);
		} catch (Exception e) {
			log.error("Si e' verificato un errore durante la ricerca dell'IdEgov per il Messaggio ["+tipoSpedizione+"] ID ["+msgId+"]di tipo ["+ tipoMessaggio+"] Soggetto ["+soggetto+"] Sil ["+sil+"]",e);
			throw new DiagnosticaServiceException_Exception(e.getMessage(), e);
		}

		if(spedizione == null){
			log.info("Non e' stato trovata spedizione corrispondente ai parametri indicati: Messaggio ["+tipoSpedizione+"] ID ["+msgId+"]di tipo ["+ tipoMessaggio+"] Soggetto ["+soggetto+"] Sil ["+sil+"]");
			return null;
		}

		log.info("Ricerca IDEgov per il Messaggio di ["+tipoSpedizione+"] ID ["+msgId+"]di tipo ["+ tipoMessaggio+"] Soggetto ["+soggetto+"] Sil ["+sil+"] completata.");

		return spedizione.getIdEgov();
	}

	@Override
	public List<MessaggioDiagnostico> getMessaggiDiagnosticiAP_Risposta(String soggetto, String sil, String msgId)  throws DiagnosticaServiceException_Exception{
		TipoMessaggio tipoMessaggio = TipoMessaggio.ALLINEAMENTO_PENDENZE;
		TipoGestione tipoSpedizione = TipoGestione.OUTBOUND;

		log.info("Ricerca dei messaggi diagnostici per il Messaggio ["+tipoSpedizione+"] ID ["+msgId+"]di tipo ["+ tipoMessaggio+"] Soggetto ["+soggetto+"] Sil ["+sil+"], calcolo IdEgov in corso ...");

		String idEgov = getIdEgov(soggetto, sil, msgId, tipoMessaggio, tipoSpedizione);

		log.info("Ricerca dei messaggi diagnostici per il Messaggio ["+tipoSpedizione+"] ID ["+msgId+"]di tipo ["+ tipoMessaggio+"] Soggetto ["+soggetto+"] Sil ["+sil+"], IdEgov trovato ["+idEgov+"].");

		return this.getMessaggiDiagnostici(idEgov);
	}

	@Override
	public List<MessaggioDiagnostico> getMessaggiDiagnosticiIPP_Richiesta(String soggetto, String sil, String msgId) throws DiagnosticaServiceException_Exception {
		TipoMessaggio tipoMessaggio = TipoMessaggio.INFORMATIVA_PAGAMENTO;
		TipoGestione tipoSpedizione = TipoGestione.OUTBOUND;

		log.info("Ricerca dei messaggi diagnostici per il Messaggio ["+tipoSpedizione+"] ID ["+msgId+"]di tipo ["+ tipoMessaggio+"] Soggetto ["+soggetto+"] Sil ["+sil+"], calcolo IdEgov in corso ...");

		String idEgov = getIdEgov(soggetto, sil, msgId, tipoMessaggio, tipoSpedizione);

		log.info("Ricerca dei messaggi diagnostici per il Messaggio ["+tipoSpedizione+"] ID ["+msgId+"]di tipo ["+ tipoMessaggio+"] Soggetto ["+soggetto+"] Sil ["+sil+"], IdEgov trovato ["+idEgov+"].");

		return this.getMessaggiDiagnostici(idEgov);
	}

	@Override
	public List<MessaggioDiagnostico> getMessaggiDiagnosticiIPP_Risposta(String soggetto, String sil, String msgId) throws DiagnosticaServiceException_Exception {
		TipoMessaggio tipoMessaggio = TipoMessaggio.INFORMATIVA_PAGAMENTO;
		TipoGestione tipoSpedizione = TipoGestione.INBOUND;

		log.info("Ricerca dei messaggi diagnostici per il Messaggio ["+tipoSpedizione+"] ID ["+msgId+"]di tipo ["+ tipoMessaggio+"] Soggetto ["+soggetto+"] Sil ["+sil+"], calcolo IdEgov in corso ...");

		String idEgov = getIdEgov(soggetto, sil, msgId, tipoMessaggio, tipoSpedizione);

		log.info("Ricerca dei messaggi diagnostici per il Messaggio ["+tipoSpedizione+"] ID ["+msgId+"]di tipo ["+ tipoMessaggio+"] Soggetto ["+soggetto+"] Sil ["+sil+"], IdEgov trovato ["+idEgov+"].");

		return this.getMessaggiDiagnostici(idEgov);
	}

}

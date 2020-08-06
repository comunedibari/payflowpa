package it.regioneveneto.mygov.payment.mypivot.controller.preparer;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;

/**
 * 
 * @author Giorgio Vallini
 * 
 */
@Component
public class MainViewPreparer implements ViewPreparer {

	@Resource
	private Environment env;
	
	@Autowired
	private EnteService enteService;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.tiles.preparer.ViewPreparer#execute(org.apache.tiles.request
	 * .Request, org.apache.tiles.AttributeContext)
	 */
	public void execute(Request tilesContext, AttributeContext attributeContext) throws PreparerException {
		
		UtenteTO utente = SecurityContext.getUtente();
		if (utente == null){
			return;
		}
		
		tilesContext.getContext(Request.REQUEST_SCOPE).put("username", utente.getDeFirstname() + " " + utente.getDeLastname());
		
		String urlRegistrazioneFedera = env.getProperty("federa.mypivot.urlRegistrazioneFedera");
		tilesContext.getContext(Request.REQUEST_SCOPE).put("urlRegistrazioneFedera", urlRegistrazioneFedera);

		String urlModificaProfiloFedera = env.getProperty("federa.mypivot.modificaProfilo");
		tilesContext.getContext(Request.REQUEST_SCOPE).put("urlModificaProfiloFedera", urlModificaProfiloFedera);

		String logoDefault =  env.getProperty("mypivot.logoDefault");
		
		EnteTO ente = SecurityContext.getEnte();
		
		if (ente != null) {
			tilesContext.getContext(Request.REQUEST_SCOPE).put("ente", (ente == null) ? null : ente.getDeNomeEnte());
			tilesContext.getContext(Request.REQUEST_SCOPE).put("codIpaEnte", (ente == null) ? null : ente.getCodIpaEnte());
			tilesContext.getContext(Request.REQUEST_SCOPE).put("deLogoEnte", (ente == null) ? null
					: enteService.getLogoEnteByCodIpaEnte(ente.getCodIpaEnte()));
		}
		
		List<String> ruoli = SecurityContext.getRoles();
		if (ruoli == null || ruoli.size() == 0) {
			return;
		}
		
//		if(ruoli.contains(Constants.RUOLO_VISUALIZZATORE) && ruoli.contains(Constants.RUOLO_AMMINISTRATORE)){
			if (ruoli.contains(Constants.RUOLO_VISUALIZZATORE)){
				tilesContext.getContext(Request.REQUEST_SCOPE).put("gestioneVisualizzaActive", "true");
				tilesContext.getContext(Request.REQUEST_SCOPE).put("gestioneFlussiExportActive","true");
			}
			if(ruoli.contains(Constants.RUOLO_AMMINISTRATORE)){
				tilesContext.getContext(Request.REQUEST_SCOPE).put("gestioneFlussiImportActive", "true");
			}
			/**
			 * La funzione di accertamento è accessibile solo per gli utenti che hanno ruolo ROLE_ADMIN e/o ROLE_ACC.
			 *  
			 * Perciò, se autorizzato, valorizzo una variabile che lato VIEW uso per determinare se 
			 * abilitare o meno il pulsantone di accesso alla funzionalità di Accertamento.
 			 */
			if (ruoli.contains(Constants.RUOLO_AMMINISTRATORE) || ruoli.contains(Constants.RUOLO_ACCERTAMENTO)){
				/* Aggiungo alla request il parametro che abilita la porzione di VIEW dell'accertamento */ 
				tilesContext.getContext(Request.REQUEST_SCOPE).put("gestioneAccertamentoActive", "true");
			}
			
			/**
			 * L'accesso alla sezione delle "Statistiche" è accessibile solo per gli utenti che hanno ruolo ROLE_ADMIN e/o ROLE_STATS.
			 *  
			 * Perciò, se autorizzato, valorizzo una variabile che lato VIEW uso per determinare se abilitare o meno il pulsantone di 
			 * accesso alla funzionalità.
 			 */
			if (ruoli.contains(Constants.RUOLO_AMMINISTRATORE) || ruoli.contains(Constants.RUOLO_STATISTICHE)){
				/* */ 
				tilesContext.getContext(Request.REQUEST_SCOPE).put("cruscottoIncassiActive", "true");
			}
			
			if(ruoli.contains(Constants.RUOLO_AMMINISTRATORE)){
				/* Aggiungo alla request il parametro che abilita il bottone gestione anagrafica */ 
				tilesContext.getContext(Request.REQUEST_SCOPE).put("gestioneAnagraficaActive", "true");
			}
			
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String requestUrl = request.getRequestURL().toString();
			
			/** 
			 * Individua se evidenziare come selezionato il pulsantone 
			 */
			boolean gestioneFlussiImportTileActive = requestUrl.contains("flussiUpload");
			boolean gestioneFlussiExportTileActive = requestUrl.contains("flussiExport");
			boolean gestioneAccertamentoTileActive = requestUrl.contains("accertamenti"); 
			boolean gestioneDovutiTileActive = requestUrl.contains("visualizzaCompleta");
			boolean cruscottoIncassiTileActive = requestUrl.contains("cruscottoIncassi");
			
			tilesContext.getContext(Request.REQUEST_SCOPE).put("gestioneFlussiImportTileActive", gestioneFlussiImportTileActive);
			tilesContext.getContext(Request.REQUEST_SCOPE).put("gestioneFlussiExportTileActive", gestioneFlussiExportTileActive);
			tilesContext.getContext(Request.REQUEST_SCOPE).put("gestioneAccertamentoTileActive", gestioneAccertamentoTileActive);
			tilesContext.getContext(Request.REQUEST_SCOPE).put("gestioneVisualizzaTileActive", gestioneDovutiTileActive);
			tilesContext.getContext(Request.REQUEST_SCOPE).put("cruscottoIncassiTileActive", cruscottoIncassiTileActive);
			
			tilesContext.getContext(Request.REQUEST_SCOPE).put("viewHeader", true);
//		}
//		else{
//			tilesContext.getContext(Request.REQUEST_SCOPE).put("viewHeader", false);
//		}
	}

}

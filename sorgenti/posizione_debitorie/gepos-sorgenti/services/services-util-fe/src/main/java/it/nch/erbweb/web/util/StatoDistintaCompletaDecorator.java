package it.nch.erbweb.web.util;

import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.utility.web.WebUtility;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class StatoDistintaCompletaDecorator implements DisplaytagColumnDecorator {

	private static final String PREFISSO_STATO = "flusso.statoflusso.";
	private static final String PREFISSO_TITLESTATO = "flusso.msgStatoflusso.";
	private static final String STATO_NULL = PREFISSO_STATO + "null";

	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {								 									
		String stato = (String) columnValue;
		return decorateStatoDistintaHtml(stato, pageContext);
	}
	
	public static String decorateStatoDistintaHtml(String stato, PageContext pageContext){
		String statoMsg = "";
		if (stato == null) {
			try {
				statoMsg = WebUtility.getResourceMessage(pageContext, STATO_NULL);
			} catch (JspException e) {
				trace(STATO_NULL);
			}
		} else {
			// recupero lo stato e lo compongo con un prefisso per formare la chiave corretta da utilizzare 
			// per recuperare il messaggio dal MessageResource.properties									 									
			String statoFormatted = stato.toLowerCase().replaceAll(" ", "").replaceAll("[.]", "");
			
			String key = PREFISSO_STATO + statoFormatted;			
			String titleKey = PREFISSO_TITLESTATO + statoFormatted;

			// recupero il messaggio

			statoMsg = stato.trim();
			try {
				String msg = WebUtility.getResourceMessage(pageContext, key);
				String titleMsg = WebUtility.getResourceMessage(pageContext, titleKey);

				// nel caso il messaggio non sia mappato ritorna null, in quel caso setto come messaggio lo stato originale
				// recuperato dal VO, altrimenti setto il messaggio recuperato
				if (msg != null) {
					
					if(stato.equals(StatiPagamentiIris.ANNULLATO.getFludMapping())) 
						statoMsg="<span title='"+titleMsg+"' class='testonormalerosso'>"+msg+"</span>";
					/*else if(stato.equals(StatiPagamentiIris.DISPOSTO.getFludMapping())) {
						statoMsg="<span title='"+titleMsg+"' class='testonormalegiallo'>"+msg+"</span>";
					}*/ else if(stato.equals(StatiPagamentiIris.ESEGUITO.getFludMapping())) 
						statoMsg="<span title='"+titleMsg+"' class='testonormaleverde'>"+msg+"</span>";
					else if(stato.equals(StatiPagamentiIris.STORNATO.getFludMapping())) 
						statoMsg="<span title='"+titleMsg+"' class='testonormalegiallo'>"+msg+"</span>";
					else if(stato.equals(StatiPagamentiIris.IN_CORSO.getFludMapping())) 
						statoMsg="<span title='"+titleMsg+"' class='testonormalegiallo'>"+msg+"</span>";
					else if(stato.equals(StatiPagamentiIris.IN_ERRORE.getFludMapping())) 
						statoMsg="<span title='"+titleMsg+"' class='testonormalerosso'>"+msg+"</span>";
					else if(stato.equals(StatiPagamentiIris.NON_ESEGUITO.getFludMapping())) 
						statoMsg="<span title='"+titleMsg+"' class='testonormalerosso'>"+msg+"</span>";
					else if(stato.equals(StatiPagamentiIris.ANNULLATO_DA_OPERATORE.getFludMapping())) 
						statoMsg="<span title='"+titleMsg+"' class='testonormalerosso'>"+msg+"</span>";
					else if(stato.equals(StatiPagamentiIris.ESEGUITO_SBF.getFludMapping())) 
						statoMsg="<span title='"+titleMsg+"' class='testonormalegiallo'>"+msg+"</span>";
					else
						statoMsg = msg;
					

					/**************
					if(statoFormatted.equals("inserita")){
						statoMsg="<span title='" + titleMsg  + "' class='testonormalegiallo'>"+WebUtility.getResourceMessage(pageContext, "flusso.statoflusso.inserita")+"</span>";
					} else  if(statoFormatted.equals("creata")||statoFormatted.equals("creato")){
						statoMsg="<span title='" + titleMsg  + "' class='testonormalegiallo'>"+WebUtility.getResourceMessage(pageContext, "flusso.statoflusso.chiusa")+"</span>";
					} else  if(statoFormatted.equals("unafirma")||statoFormatted.equals("inattesa")||statoFormatted.equals("duefirme")){
						statoMsg="<span title='" + titleMsg  + "' class='testonormalegiallo'>" +msg+ "</span>";
					} else if(statoFormatted.equals("err.importazione")||statoFormatted.equals("err.creazione")||statoFormatted.equals("inerrore")
					||statoFormatted.equals("rifiutata")||statoFormatted.equals("rifiutato")||statoFormatted.equals("errata")||statoFormatted.equals("errato")
					||statoFormatted.equals("rifiutatarete")||statoFormatted.equals("rifiutatorete")){
						statoMsg="<span title='" + titleMsg  + "' class='testonormalerosso'>" +msg+ "</span>";
					}else if(statoFormatted.equals("spedita")||statoFormatted.equals("spediti")||statoFormatted.equals("accettata")||statoFormatted.equals("accettato")
					||statoFormatted.equals("incaricobanca")||statoFormatted.equals("parz.accettata")||statoFormatted.equals("parz.accettato")){
						statoMsg="<span title='" + titleMsg  + "' class='testonormaleverde'>" +msg+ "</span>";
					} else  if(statoFormatted.equals("operazioneincorso")){
						statoMsg="<span title='" + titleMsg  + "' class='testonormalegiallo'>" +msg+ "</span><IMG border='0' src='"+WebUtility.getContextPath(pageContext)+"/images/rotella_gialla.gif'/>";
					}else  if(statoFormatted.equals("gateway.errore")){
						statoMsg="<span title='" + titleMsg  + "' class='testonormalerosso'>" +msg+ "</span>";
					}else  if(statoFormatted.equals("abbandonata")){
						statoMsg="<span title='" + titleMsg  + "' class='testonormalerosso'>" +msg+ "</span>";
					}else{
						statoMsg = msg;
					}
					**************/
				}
			} catch (JspException e) {
				trace(key);
			}
		}
		return statoMsg;
	}

	private static void trace(String key) {
		Tracer.debug("StatoDistintaCompletaDecorator", "decorate", key);		
	}
}

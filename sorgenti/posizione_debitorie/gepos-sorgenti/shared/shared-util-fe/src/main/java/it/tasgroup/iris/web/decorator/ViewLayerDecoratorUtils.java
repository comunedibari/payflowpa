/**
 * 
 */
package it.tasgroup.iris.web.decorator;

import static it.tasgroup.iris.web.decorator.MarkupHelper.createClickableIcon;
import static it.tasgroup.iris.web.decorator.MarkupHelper.createIconButtonLink;

import java.util.Properties;

import it.tasgroup.crypt.url.DownloadQuietanzaParametersEncrypter;
import it.tasgroup.crypt.url.DownloadRicevutaParametersEncrypter;
import it.tasgroup.iris.constants.SharedConstants;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.web.decorator.MarkupHelper.ButtonLevel;
import it.tasgroup.iris.web.decorator.MarkupHelper.IconDimension;

/**
 * @author pazzik
 *
 */
public class ViewLayerDecoratorUtils {
	
	public static String getPaymentsListRowButtons(String idPendenza, String codPagamento, String idPagamento, String idFlusso, String codPagante,
			String stato, boolean flagIncasso, boolean flagRicevuta,boolean documentAvailable4Download) {
		return getPaymentsListRowButtons(idPendenza, codPagamento, idPagamento, idFlusso, codPagante,
				 stato, flagIncasso, flagRicevuta, documentAvailable4Download, null);
	}
	
	public static String getPaymentsListRowButtons(String idPendenza, String codPagamento, String idPagamento, String idFlusso, String codPagante,
			String stato, boolean flagIncasso, boolean flagRicevuta,boolean documentAvailable4Download, String flagConsegnaRicevutaIdp) {
		
		Properties gwProperties = ConfigurationPropertyLoader.getProperties("gateway-ws-client.properties");		
		String gwBaseUrl= gwProperties.getProperty("iris.gateway.webapp.baseUrl");

		
    	String dettaglioUrl = "PDC/avvisiMain.do?method=visualizzaAvviso&amp;isLente=true&amp;selCheckbox=" + idPendenza;
    	
    	/*
		DownloadRicevutaParametersEncrypter dpr = new DownloadRicevutaParametersEncrypter(codPagamento,codPagante,idFlusso);
		String crParam=dpr.encrypt();    	
		String ricevutaUrl = gwBaseUrl+"/documentiPagamento.do?method=downloadRicevuta&" + SharedConstants.CRYPTEDPARAMS + "=" + crParam;		
		*/
	    	
		DownloadQuietanzaParametersEncrypter dpq = new DownloadQuietanzaParametersEncrypter(codPagamento, codPagante, idPagamento);
		String cqParam = dpq.encrypt();
		String quietanzaUrl = gwBaseUrl+"/documentiPagamento.do?method=downloadQuietanza&" + SharedConstants.CRYPTEDPARAMS + "=" + cqParam;
			
		StringBuilder sb = new StringBuilder();
		
		if (stato.equals("IC")) {
			
			sb.append(createIconButtonLink(dettaglioUrl, "icon-question-sign", ButtonLevel.PRIMARY, "Verifica")).append(" ");
			
		} else if (stato.equals("ES"))  {
			
			if("S".equals(flagConsegnaRicevutaIdp) || flagConsegnaRicevutaIdp == null) {
			
				sb.append("<p>");
				
				if (documentAvailable4Download){
					if(flagRicevuta) {
	
						if (flagIncasso) {
							// se � pagata l'icona del documento � "piena"
							sb.append(createClickableIcon(quietanzaUrl, "Download ricevuta", "icon-file-alt", IconDimension.X2, true));
						} else {
							sb.append(createClickableIcon(quietanzaUrl, "Download ricevuta", "icon-file", IconDimension.X2, true));
						}
	
						
					} else {
						sb.append(String.format("<a data-toggle=\"modal\" href=\"%s\" title=\"%s\"><i class=\"clickable %s %s\"></i>&nbsp;%s</a>", "#redirectToLogin", "Download ricevuta", IconDimension.X2, "icon-file", "Download ricevuta"));
					}
				}
				else {
					sb.append("La ricevuta sara' disponibile a breve");
				}
			}
            sb.append("</p>");
		
		}
		
		return sb.toString();

	}
	
	public static String getDownloadRicevutaButton(String codPagamento, String idFlusso, String codPagante) {

		Properties gwProperties = ConfigurationPropertyLoader.getProperties("gateway-ws-client.properties");		
		String gwBaseUrl= gwProperties.getProperty("iris.gateway.webapp.baseUrl");

		DownloadRicevutaParametersEncrypter dpr = new DownloadRicevutaParametersEncrypter(codPagamento,codPagante,idFlusso);
		String crParam=dpr.encrypt();    	
		String ricevutaUrl = gwBaseUrl+"/documentiPagamento.do?method=downloadRicevuta&" + SharedConstants.CRYPTEDPARAMS + "=" + crParam;		
			
		StringBuilder sb = new StringBuilder();
		sb.append("<p>");
		sb.append(createClickableIcon(ricevutaUrl, "Download ricevuta", "icon-file", IconDimension.X2, true));
        sb.append("</p>");
		
		return sb.toString();

	}
	
	
}

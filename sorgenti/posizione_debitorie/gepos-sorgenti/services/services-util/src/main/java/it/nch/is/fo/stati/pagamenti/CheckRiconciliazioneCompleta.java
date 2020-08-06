package it.nch.is.fo.stati.pagamenti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

public class CheckRiconciliazioneCompleta {

	private static final String LISTA_ENTI_RICONCILIAZIONE_COMPLETA = "iris.riconciliazioni.ndp.lista_enti_riconciliazione_completa";
	private static List<String> listaEntiRiconciliazioneCompleta = new ArrayList<String>();  //Default Value: Lista Vuota
	private static boolean soloRiconciliazioneCompleta = false;  	
	private static final Log logger = LogFactory.getLog(CheckRiconciliazioneCompleta.class.getClass());
	private static final ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("iris-fe.properties");

	static {
	// Lettura da file di properties del range temporale (in ore) di visibilita' del flusso
			try {
				String listaEntiRiconciliazioneCompletaString = props.getProperty(LISTA_ENTI_RICONCILIAZIONE_COMPLETA);
				logger.info("Acquisito da file di properties il valore associato al parametro " + LISTA_ENTI_RICONCILIAZIONE_COMPLETA);
				if (listaEntiRiconciliazioneCompletaString!=null) {
					if ("*".equals(listaEntiRiconciliazioneCompletaString.trim())) {
						soloRiconciliazioneCompleta = true;
						logger.info("Tutti gli enti hanno la riconciliazione del nodo, completa. I flussi FR verranno riconciliati solo se presente un movimento accredito relativo.");
					} else {
						listaEntiRiconciliazioneCompleta = Arrays.asList(listaEntiRiconciliazioneCompletaString.split(";"));
						logger.info("I seguenti enti: " + listaEntiRiconciliazioneCompleta + " hanno la riconciliazione del nodo, completa. "
								+"Solo per questi enti i flussi FR verranno riconciliati solo se presente un movimento accredito relativo. Per tutti gli altri verranno riconciliati senza tenere conto "
								+ " Della rendicontazione del conto corrente di tesoreria.");
					}
				}
				
			} catch (Exception e) {
				logger.info("Non e' stato possibile acquisire da file di properties il valore associato al parametro " + LISTA_ENTI_RICONCILIAZIONE_COMPLETA + " - Assunto default: lista vuota.");
			}
	}
	
	public static boolean isEnteRiconciliazioneCompleta(String idEnte){
		if (soloRiconciliazioneCompleta)
			return true;
		for (String idEnteName:listaEntiRiconciliazioneCompleta) {
			if (idEnteName.equals(idEnte)){
				return true;
			}
		}
		
		return false;
	}
}

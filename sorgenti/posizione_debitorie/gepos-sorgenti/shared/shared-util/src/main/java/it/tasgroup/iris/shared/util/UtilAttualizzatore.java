package it.tasgroup.iris.shared.util;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.util.HashMap;
import java.util.Map;

public class UtilAttualizzatore {
	
	public static final String TITOLO_ESITO = "titolo";
	public static final String MSG_ESITO = "messaggio";

	public static Map<String, String> getMappaMessaggio(String descrizione) {

		String titolo = null;
		String messaggio = null;

		if (descrizione != null) {
			// spezzo se serve
			if (descrizione.contains("#")) {
				titolo = descrizione.substring(0, descrizione.indexOf("#"));
				messaggio = descrizione.substring(descrizione.indexOf("#") + 1);
			} else {
				messaggio = descrizione;
			}
			// sostituisco il link se serve
			if (messaggio.contains("{url.reteaci}")) {
				String linkReteAci = "<a href='" + ConfigurationPropertyLoader.getInstance("iris-fe.properties").getProperty("iris.attualizzatore.urlAci")
						+ "'>rete territoriale ACI della Toscana</a>";
				messaggio = messaggio.replace("{url.reteaci}", linkReteAci);
			}
		}

		Map<String, String> mappaMessaggio = new HashMap<String, String>();
		mappaMessaggio.put(TITOLO_ESITO, titolo);
		mappaMessaggio.put(MSG_ESITO, messaggio);

		return mappaMessaggio;
	}

}

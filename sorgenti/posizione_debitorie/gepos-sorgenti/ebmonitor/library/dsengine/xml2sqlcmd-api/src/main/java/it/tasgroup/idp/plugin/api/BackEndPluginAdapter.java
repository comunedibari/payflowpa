package it.tasgroup.idp.plugin.api;

import java.util.HashMap;
import java.util.StringTokenizer;
 
public abstract class BackEndPluginAdapter implements BackEndPlugin {

	
	private HashMap tokenize(String causale) throws IllegalArgumentException {
		HashMap returnValue = new HashMap();
		
		try {
			StringTokenizer st = new StringTokenizer(causale,";");
				while (st.hasMoreTokens()) {
					StringTokenizer st2 = new StringTokenizer(st.nextToken(),"=");
					String key=st2.nextToken().toUpperCase().trim();
					String value=st2.nextToken().toUpperCase().trim();
					returnValue.put(key, value);
				} 
		} catch (Exception e) {
			throw new IllegalArgumentException("Causale non formattata correttamente",e);
		}
		return returnValue; 
	}

	public String validaCausale(String causale) {
		try {
			HashMap keyValuePairs = tokenize(causale);
			return validaCausale(keyValuePairs);
		} catch (IllegalArgumentException e) {
			return "La causale non è formattata secondo lo schema previsto: sequenza di coppie KEY=VALUE separate da ';' ";
		}
	}


	public DettaglioStrutturatoModel getDettaglioStrutturato(String causale) {
		HashMap keyValuePairs = tokenize(causale);
		return getDettaglioStrutturato(keyValuePairs);
	}

}

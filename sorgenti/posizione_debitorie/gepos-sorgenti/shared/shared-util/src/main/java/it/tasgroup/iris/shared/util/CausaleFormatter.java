package it.tasgroup.iris.shared.util;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CausaleFormatter {
	protected static Logger logger = LogManager.getLogger(CausaleFormatter.class);
	
	private static HashMap<String, String> TRIBUTO_CAUSALE;
	
	/**
	 * TRIBUTO_CAUSALE = new HashMap<String, String>
	 * 	key 	-> Codice Tributo Ente
	 *  value 	-> pattern formattazione causale
	 */
	static {
		TRIBUTO_CAUSALE = new HashMap<String, String>();
		Properties beProperties = ConfigurationPropertyLoader.getProperties("iris-fe.properties");
	    String tributoCausale = beProperties.getProperty("iris.backoffice.causale.formattata");
	    if(!tributoCausale.startsWith("$")) {
		    StringTokenizer st = new StringTokenizer(tributoCausale, ";;");
		    while (st.hasMoreTokens()) {
		    	StringTokenizer st2 = new StringTokenizer(st.nextToken(), "=");
		    	String key = st2.nextToken();
				String value = st2.nextToken();
				if (value.isEmpty())
					logger.warn("Causale formattata BO - value not set [key: " + key + "]");
				else {
					TRIBUTO_CAUSALE.put(key, value);
				}
			}
	    }
	}
	
	/**
	 * 
	 * @param causale 
	 * es:
	 * 		LGN_DEB=luogo;DEB=denominazione;TIPO_LIC=Licenza tipo A - Pesca professionale - UPB E372.006 - Capitolo EC372.063;ANNO_RIF=2017;
	 * @param cdTrbEnte
	 * 		codice tributo ente
	 * @return
	 */
	public static String format(String causale, String cdTrbEnte) {
		String result = causale;
		if(TRIBUTO_CAUSALE.containsKey(cdTrbEnte)) {
			String regexString = Pattern.quote("<") + "(.*?)" + Pattern.quote(">");
			Pattern pattern = Pattern.compile(regexString);
			result = TRIBUTO_CAUSALE.get(cdTrbEnte);
			Matcher matcher = pattern.matcher(result);
			while (matcher.find()) {
			  String cdTrb = matcher.group(1); // Since (.*?) is capturing group 1
			  String token = " ";
			  if (causale.indexOf(cdTrb) != -1) {
				  token = causale.substring(causale.indexOf(cdTrb) + cdTrb.length() + 1);
				  token = token.substring(0, token.indexOf(";"));
			  } 
			  result = result.replace("<" + cdTrb + ">", token);
			}
		} 
		return result;
	}

}

package it.nch.struts.util;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.PropertyMessageResources;

/**
 * Implementazione custom di DBMessageResources per l'accesso al db
 */
public class DBMessageResources extends PropertyMessageResources {
	
	/*
	 * Costruttori di PropertyMessageResources
	 */
	public DBMessageResources(MessageResourcesFactory factory,String config) {
		super(factory, config);
		log.trace("Initializing, config='" + config + "'");
	}
	
	public DBMessageResources(MessageResourcesFactory factory,String config, boolean returnNull) {
		super(factory, config, returnNull);
		log.trace("Initializing, config='" + config + "', returnNull="+returnNull);
	}
	
	
	

	/** 
	 * Chiamo MessaggiFromDB per controllore se nel database e' presente una versione del messaggio.
	 * In caso negativo ritorno il messaggio presente nel file.
	 * 
	 * @see org.apache.struts.util.MessageResources#getMessage(java.util.Locale, java.lang.String)
	 */
	public String getMessage(Locale locale, String key) {
		String msgd = null;//MessaggiFromDB.getMessaggioFromDB(getConfig(),"",key);
		if(msgd != null)
			return msgd;
		else
			return super.getMessage(locale, key);
	}
	
	/**
	 * @see org.apache.struts.util.MessageResources#getMessage(java.util.Locale, java.lang.String, java.lang.Object[])
	 */
	public String getMessage(Locale locale, String key, Object[] args) {
		// Cache MessageFormat instances as they are accessed
		if (locale == null) {
			locale = defaultLocale;
		}

		MessageFormat format = null;
		String formatKey = messageKey(locale, key);

		String formatString = getMessage(locale, key);

		if (formatString == null) {
			return returnNull ? null : ("???" + formatKey + "???");
		}

		format = new MessageFormat(escape(formatString));
		format.setLocale(locale);

		return format.format(args);
	}
	
	/**
	 * Ritorna la rappresentazione JSON  del messagesResource dato un certo locale
	 * e una lista di preifssi
	 * 
	 * @return
	 */
	public String toJSON(Locale locale,String[] prefixArray){
		//si potrebbe usare una libreria Map-->JSON come JSONObject
		String localeKey = localeKey(locale);
		loadLocale(localeKey); 
		
		Iterator i = messages.entrySet().iterator();
		StringBuffer jsonString = new StringBuffer(getConfig()+" = {");
		while(i.hasNext()){
			Map.Entry entry = (Map.Entry)i.next();
			
			String decodedKey = decodeMessageKey(localeKey,(String)entry.getKey());			
			if(prefixArray != null && prefixArray.length > 0){
				boolean prefixOk = false;
				for(int y = 0; y<prefixArray.length && !prefixOk;y++){
					String prefix = prefixArray[y];
					if(decodedKey.startsWith(prefix))
						prefixOk = true;
				}
							
				if(!prefixOk)
					continue;
			}
			
//			String msgd = MessaggiFromDB.getMessaggioFromDB(getConfig(),"",decodedKey);
			String msgd = null;
			if(msgd == null)
				msgd = (String) entry.getValue();
				
			
			//costruisco oggetto js
			jsonString.append("\""+decodedKey+"\"");
			jsonString.append(":");
			jsonString.append("\""+StringEscapeUtils.escapeJavaScript(msgd)+"\"");
			if(i.hasNext()){
				jsonString.append(",");
			}
		}
		jsonString.append("}");
		return jsonString.toString();
	}

	/**
	 * decodifica la chiave
	 */
	protected String decodeMessageKey(String localeKey, String key){
		return key.substring(localeKey.length()+1);
	}
}

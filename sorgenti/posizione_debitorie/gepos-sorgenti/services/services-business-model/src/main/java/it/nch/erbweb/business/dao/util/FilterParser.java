package it.nch.erbweb.business.dao.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FilterParser implements QueryParser {
	private final static String REGEX      = "(?si)\\[\\s*filter\\s*:(.+?)~\\s*:(.+?)\\]";
    private final static Pattern PATTERN   = Pattern.compile(REGEX);
	
	
	private final Map values = new HashMap();

	public String prepare(String target) {
        Matcher matcher = PATTERN.matcher(target);
        
        String result = "";
        
        String[] split = PATTERN.split(target, -1);
        for (int i = 0; i < split.length; i++) {
            result += split[i];
            if (matcher.find()) {
            	String alias = matcher.group(2).trim();
            	String operatore = getOperatore(getData(alias));
            	if (operatore != null) {
                    result += matcher.group(1) + operatore + " :" + alias;
            	} else {
            		values.remove(alias);
            	}
            }
        }
        
        return result;
	}

	public PlaceHolder[] getInputs() {
		return (PlaceHolder[])values.values().toArray(new PlaceHolder[0]);
	}

	private PlaceHolder getData(String key) {
		return (PlaceHolder)values.get(key);
	}
	
	private String getOperatore(PlaceHolder placeHolder ) {
		String operatore = null;
		Object value;
		
		if (placeHolder != null && (value = placeHolder.getValue()) != null) {
			if (value instanceof String) {
				String newValue = ((String)value).trim();
				if (newValue.length() > 0) {
					if (newValue.indexOf('*') != -1) {
						newValue = newValue.replace('*', '%');
						placeHolder.setValue(newValue);
						operatore = "like";
					} else {
						operatore = "=";
					}
				}
			} else {
				operatore = "=";
			}
		}
		
		return operatore;
	}

	public boolean add(String alias, Object value) {
		if (value != null && (!(value instanceof String) || ((String)value).trim().length() > 0)){
			values.put(alias, new PlaceHolder(alias, value));
			return true;
		}
		return false;
	}

}

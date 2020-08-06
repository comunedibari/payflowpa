package it.nch.erbweb.business.dao.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IfParser implements QueryParser {
	private final static String REGEX      = "(?si)\\[\\s*if \\s*(.+?)\\s*:(.+?)\\]";
	private final static Pattern PATTERN   = Pattern.compile(REGEX);

	private final Set inputs 	= new HashSet();
	private final List values 	= new ArrayList();

	public String prepare(String target) {
		Matcher matcher = PATTERN.matcher(target);
        
		String result = "";
        
		String[] split = PATTERN.split(target, -1);
		for (int i = 0; i < split.length; i++) {
			result += split[i];
			if (matcher.find()) {
				if (hasData(matcher.group(1).trim())) {
					result += matcher.group(2);
				}
			}
		}
        
		return result;
	}

	private boolean hasData(String key) {
		return inputs.contains(key);
	}

	public void addIf(String alias) {
		inputs.add(alias);
	}
    
	public void addInput(String alias, Object value) {
		values.add(new PlaceHolder(alias, value));
	}

	public boolean ifInput(String alias, Object value) {
		if (value != null && (!(value instanceof String) || ((String)value).trim().length() > 0)){
			addIf(alias);
			addInput(alias, value);
			return true;
		}
		return false;
	}

	public PlaceHolder[] getInputs() {
		return (PlaceHolder[])values.toArray(new PlaceHolder[0]);
	}
	
}

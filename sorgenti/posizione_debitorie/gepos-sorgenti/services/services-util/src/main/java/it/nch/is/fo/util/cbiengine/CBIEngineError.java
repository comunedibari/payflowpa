package it.nch.is.fo.util.cbiengine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

public class CBIEngineError {

	public final static String NEWLINE = "\r\n";

	public final static String NEWLINE_HTML = "<BR>";

	public final static String TAB = "\t";

	public String type;

	public String id;

	private String endUserMessage;

	private String disposizione;

	private int disposizioneAsInt;

	public ArrayList params;

	public CBIEngineError nextSibling;

	public CBIEngineError prevSibling;

	public void addErrorParam(CBIEngineErrorParam param) {
		if (params == null) {
			params = new ArrayList();
		}
		params.add(param);
	}

	public String toString() {
		String out = "";

		out += "type --> " + type;
		out += "\r\n";
		out += "id --> " + id;
		out += "\r\n";
		out += "endUserMessage --> " + endUserMessage;
		out += "\r\n";

		if (params != null) {
			Iterator it = params.iterator();
			while (it.hasNext()) {
				out += it.next();
				out += "\r\n";
			}
		}

		return out;
	}

	public String getValueByKey(String theKey) {
		if (params != null) {
			Iterator it = params.iterator();
			while (it.hasNext()) {
				CBIEngineErrorParam ceep = (CBIEngineErrorParam) it.next();
				if (ceep.getKey().equalsIgnoreCase(theKey)) {
					return ceep.getValue();
				}
			}
		}
		return null;
	}

	public String format(Properties properties) {
		return format(properties, false, false);
	}
	
	public String format(Properties properties, boolean is4HTML, boolean addNewlineForMultipleErrors) {
		StringBuffer sb = new StringBuffer();
		
		String dispo = getDisposizione();
		if (dispo != null && !dispo.equalsIgnoreCase("null") && !dispo.equalsIgnoreCase("") && !dispo.equals("0")) {
			sb.append("Disposizione ");
			sb.append(dispo);
			if (is4HTML) {
				sb.append(": ");
			} else {
				sb.append(NEWLINE);
				sb.append(TAB);
			}
		}
		CBIEngineError current = this;
		do {
			sb.append(current.getEndUserMessage(properties));
			if (current.nextSibling != null) {
				if (addNewlineForMultipleErrors) {
					sb.append(NEWLINE);
					sb.append(TAB);
				} else {
					sb.append("; ");
				}
			}
			current = current.nextSibling;
		} while (current != null);
		return sb.toString();
	}

	public String formatInfo(Properties properties) {
		StringBuffer sb = new StringBuffer();

		sb.append(getEndUserMessage(properties));

		return sb.toString();
	}

	public String getDisposizione() {
		if (disposizione == null) {
			disposizione = getValueByKey("OPERATION_NUMBER");
			try {
				disposizioneAsInt = Integer.parseInt(disposizione);
			} catch (NumberFormatException e) {
				disposizioneAsInt = -1;
			}
		}
		return disposizione;
	}

	public int getDisposizioneAsInt() {
		getDisposizione();
		return disposizioneAsInt;
	}

	public String getEndUserMessage(Properties properties) {
		if (endUserMessage == null) {
			String value = properties.getProperty(id);
			if (value != null && !value.equalsIgnoreCase("")) {
				endUserMessage = value;
			} else {
				endUserMessage = "Errore inatteso: " + id + " - ${message}";
			}
		}
		while (containsPlaceholders(endUserMessage)) {
			endUserMessage = replaceFirstPlaceholder(endUserMessage);
		}
		return endUserMessage;
	}
	
	public boolean containsPlaceholders(String source) {
		return (source != null && source.indexOf("${") >= 0);
	}

	public String replaceFirstPlaceholder(String source) {
		int start = source.indexOf("${");
		int end = source.indexOf("}");
		String key = source.substring(start + 2, end);
		String val = getValueByKey(key);
		String before = source.substring(0, start);
		String after = source.substring(end + 1);
		source = before + val + after;
		
		return source;
	}

}

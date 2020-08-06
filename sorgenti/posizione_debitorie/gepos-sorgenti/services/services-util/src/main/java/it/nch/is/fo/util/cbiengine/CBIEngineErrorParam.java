package it.nch.is.fo.util.cbiengine;

import java.util.StringTokenizer;

public class CBIEngineErrorParam {
	public String rowMessage;
	
	
	private String key;
	private String value;
	
	public String getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}

	public CBIEngineErrorParam() {
		super();
	}

	public CBIEngineErrorParam(String rowMessage) {
		this.rowMessage = rowMessage;
		try {
			StringTokenizer st = new StringTokenizer(this.rowMessage, "=");
			key = st.nextToken();
			value = st.nextToken();
		} catch (Exception e) {
			//key = e.getClass().getName();
			value = "";
		}
	}
	
	public String toString() {
		return (" - param: " + key + " = " + value + " (" + rowMessage + ")");
	}
	
}

package it.tasgroup.idp.util;

import java.util.HashMap;

/**
 * <p>This class extends a {@link HashMap<String, String>} with the ability to 
 * serialize and deserialize its content in a readable string.</p> 
 * 
 * @author tasgroup
 *
 */

public class DataBag extends HashMap<String, String> {
	private static final long serialVersionUID = -4821139414691414534L;
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder("{");
		for (String key : keySet()) {
			if (out.length() > 1) out.append(", ");
			out.append(key).append("=").append(get(key));
		}
		out.append("}");
		return out.toString();
	}
	
	public static DataBag fromString (String serializedExtraData) {
		if (serializedExtraData == null) return null;
		DataBag dataBag = new DataBag();
		String searchString = serializedExtraData.substring(1, serializedExtraData.length()-1);
		String[] keysValues = searchString.split(", ");
		for (String keyValue : keysValues) {
			int pos = keyValue.indexOf("=");
			String key = keyValue.substring(0, pos);
			String value = keyValue.substring(pos+1);
			dataBag.put(key, value);
		}
		return dataBag;
	}
}

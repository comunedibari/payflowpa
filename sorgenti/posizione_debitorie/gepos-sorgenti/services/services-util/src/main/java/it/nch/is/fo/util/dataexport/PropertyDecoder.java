package it.nch.is.fo.util.dataexport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

public class PropertyDecoder {

	public static Collection getValuesFromBundle(String propertiesName, String keyValueName, String keyDescName) {
		return getValuesFromBundle(propertiesName, keyValueName, keyDescName, ",");
	}

	public static Collection getValuesFromBundle(String propertiesName, String keyValueName, String keyDescName, String separator) {
		Collection coll = new ArrayList();
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(propertiesName);
			String valueList = bundle.getString(keyValueName);
			String descList = bundle.getString(keyDescName);
			if (valueList != null && descList != null) {
				String[] valueArray = valueList.split(separator);
				String[] descArray = descList.split(separator);
				for (int i = 0; i < descArray.length; i++) {
					CodeDescriptionPair codeDescriptionPair = new CodeDescriptionPair();
					codeDescriptionPair.description = descArray[i];
					codeDescriptionPair.code = valueArray[i];
					coll.add(codeDescriptionPair);
				}
			}

		} catch (Exception exc) {

		}
		return coll;
	}

	public static HashMap getValuesFromBundleAsMap(String propertiesName, String keyValueName, String keyDescName) {
		return getValuesFromBundleAsMap(propertiesName, keyValueName, keyDescName, ",");
	}

	public static HashMap getValuesFromBundleAsMap(String propertiesName, String keyValueName, String keyDescName, String separator) {
		Collection coll = getValuesFromBundle(propertiesName, keyValueName, keyDescName, separator);
		HashMap map = new HashMap();
		for (Iterator it = coll.iterator(); it.hasNext(); ) {
			CodeDescriptionPair codeDescriptionPair = (CodeDescriptionPair)it.next();
			map.put(codeDescriptionPair.code, codeDescriptionPair.description);
		}
		return map;
	}
}

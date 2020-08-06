/**
 * 
 */
package it.tasgroup.iris.dto;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author pazzik
 *
 */
public class BillDTOInspector {
	
private static final ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-cbill.properties");
	
private static final String formattaNote = cpl.getProperty("cbill.moreInfo.formatta.note");
	
public static Map<String,String> parseNoteStrutturate(IPendenzaDTO pend){
	
		Map<String,String> tempValueMap = new LinkedHashMap<String, String>();
		
		String note = pend.getNote();
			
		if ("true".equalsIgnoreCase(formattaNote) && !StringUtils.isEmpty(note)){
				
				String[] pairs = note.split("[;]");
				
				for (String pair : pairs){
					
					String[] keyvalue = pair.split("[=]");
					
					if (keyvalue.length == 2)
						
						tempValueMap.put(keyvalue[0], keyvalue[1]);	
					
					else {
						
						tempValueMap.clear();
						
						tempValueMap.put("Note", note);
						
						break;
					}
				}					
		} 
		
		return tempValueMap;
		
	}
}
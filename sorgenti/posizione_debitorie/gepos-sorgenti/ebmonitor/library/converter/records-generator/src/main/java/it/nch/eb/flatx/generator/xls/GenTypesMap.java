/**
 * 30/nov/2009
 */
package it.nch.eb.flatx.generator.xls;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gdefacci
 */
public class GenTypesMap {
	
	Map/*<String, GenTypeModel>*/ map = new HashMap();
	
	public GenTypeModel get(String nm) {
		return (GenTypeModel) map.get(nm);
	}
	
	public void put(String nm, GenTypeModel typ) {
		map.put(nm, typ);
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

}

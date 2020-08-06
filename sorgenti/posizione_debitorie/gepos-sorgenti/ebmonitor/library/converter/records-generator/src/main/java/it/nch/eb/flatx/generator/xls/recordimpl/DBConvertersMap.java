/**
 * 
 */
package it.nch.eb.flatx.generator.xls.recordimpl;


import java.util.HashMap;
import java.util.Map;

/**
 * @author gdefacci
 */
public class DBConvertersMap {
	
	private Map/*<String, Xml2SqlCmdConvertersModel>*/ typesMap = new HashMap();
	
	public AliasedGenTypeModel get(String typeName) {
		if (typeName==null) throw new NullPointerException("typeName(" + typeName + ")");
		String tn = typeName.trim();
		if (tn.length()<1) throw new NullPointerException("typeName");
		AliasedGenTypeModel res = (AliasedGenTypeModel) typesMap.get(tn);
		return res;
	}
	
	public void put(String name, String cnvName, String toObjCnvc) {
		put(name, new AliasedGenTypeModel(name, cnvName, toObjCnvc));
	}
	
	public void put(String name, String dbName, String cnvName, String toObjCnvc) {
		put(name, new AliasedGenTypeModel(dbName, cnvName, toObjCnvc));
	}

	public void put(String name, AliasedGenTypeModel entry) {
		typesMap.put(name, entry);
	}
	
}

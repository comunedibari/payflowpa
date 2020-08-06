/**
 * Created on 05/ott/07
 */
package it.nch.eb.common.utils.map;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author gdefacci
 */
public class DataTypesUtils {

	public final static Set	TRUE_VALUES	= new TreeSet(Arrays.asList(new String[] { 
			"Y", "YES", "OK", "ON", "S", "SI", "TRUE", "T", "X" 
	}));
	
	public final static Set	FALSE_VALUES	= new TreeSet(Arrays.asList(new String[] { 
			"N", "NO", "KO", "OFF", "FALSE", "F" 
	}));

	public static boolean isTrueValue(String res) {
		return (TRUE_VALUES.contains(res.toUpperCase()));
	}
	
	public static Boolean getBooleanValue(String string) {
		if (TRUE_VALUES.contains(string.toUpperCase())) return Boolean.TRUE;
		if (FALSE_VALUES.contains(string.toUpperCase())) return Boolean.FALSE;
		return null;
	}
	
	public static String getString(Map[] maps, String key) {
		for (int i = 0; i < maps.length; i++) {
			Map map = maps[i];
			String res = getString(map, key);
			if (res!=null) return res;
		}
		return null;
	}
	
	public static Integer getInteger(Map[] maps, String key) {
		for (int i = 0; i < maps.length; i++) {
			Map map = maps[i];
			Integer res = getInteger(map, key);
			if (res!=null) return res;
		}
		return null;
	}
	
	public static Boolean getBoolean(Map[] maps, String key) {
		for (int i = 0; i < maps.length; i++) {
			Map map = maps[i];
			Boolean res = getBoolean(map, key);
			if (res!=null) return res;
		}
		return null;
	}

	public static String getString(Map map, String key) {
		Object res = map.get(key);
		if (res==null) return null;
		checkType(res, String.class);
		return (String) res;
	}
	
	public static Integer getInteger(Map map, String key) {
		Object res = map.get(key);
		if (res==null) return null;
		if (res instanceof String) {
			try {
				return Integer.valueOf((String)res);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw e;
			}
		}
		checkType(res, Integer.class);
		return (Integer) res;
	}
	
	public static Boolean getBoolean(Map map, String key) {
		Object res = map.get(key);
		if (res==null) return null;
		if (res instanceof String) {
			try {
				return  Boolean.valueOf( isTrueValue((String)res) );
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw e;
			}
		}
		checkType(res, Boolean.class);		// always raise an exception
		return (Boolean) res;
	}

	public static void checkType(Object res, Class klass) {
		if (!klass.isAssignableFrom(res.getClass())) 
			throw new IllegalStateException("expeceting a " + klass.getName() + 
					" but got[" + res + "]class[" + res.getClass() + "]" );
	}
	
	public static byte[] getByteArray(Map map, String key) {
		Object res = map.get(key);
		checkType(res, byte[].class);
		return (byte[]) res;
	}

	public static Clob getClob(Map map, String key) {
		Object obj = map.get(key);
		checkType(obj, Clob.class);
		return (Clob) obj;
	}
	
	public static Blob getBlob(Map map, String key) {
		Object obj = map.get(key);
		checkType(obj, Blob.class);
		return (Blob) obj;
	}
	
	public static String getOrElse(Map map, Object key, String elseReturn) {
		Object res = map.get(key);
		if (res == null) return elseReturn;
		else return (String) res;
	}
	
	public static Integer getOrElse(Map map, Object key, Integer elseReturn) {
		Object res = map.get(key);
		if (res == null) return elseReturn;
		else return (Integer) res;
	}
	
	public static int getOrElse(Map map, Object key, int elseReturn) {
		return getOrElse(map, key, new Integer(elseReturn)).intValue();
	}
	
	public static Boolean getOrElse(Map map, Object key, Boolean elseReturn) {
		Object res = map.get(key);
		if (res == null) return elseReturn;
		else return (Boolean) res;
	}
	
	public static boolean getOrElse(Map map, Object key, boolean elseReturn) {
		return getOrElse(map, key, Boolean.valueOf(elseReturn)).booleanValue();
	}
	
	public static Long getOrElse(Map map, Object key, Long elseReturn) {
		Object res = map.get(key);
		if (res == null) return elseReturn;
		else return (Long) res;
	}
	
	public static long getOrElse(Map map, Object key, long elseReturn) {
		return getOrElse(map, key, new Long(elseReturn)).longValue();
	}

	public static Number getOrElse(Map map, Object key, Number elseReturn) {
		Object res = map.get(key);
		if (res == null) return elseReturn;
		else return (Number) res;
	}
	
	public static BigDecimal getOrElse(Map map, Object key, BigDecimal elseReturn) {
		Object res = map.get(key);
		if (res == null) return elseReturn;
		else return (BigDecimal) res;
	}
	
	public static byte[] getOrElse(Map map, Object key, byte[] elseReturn) {
		Object res = map.get(key);
		if (res == null) return elseReturn;
		else return (byte[]) res;
	}
}

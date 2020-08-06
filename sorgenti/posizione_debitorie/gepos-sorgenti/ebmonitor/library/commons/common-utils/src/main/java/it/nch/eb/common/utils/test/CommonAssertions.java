/**
 * Created on 12/giu/07
 */
package it.nch.eb.common.utils.test;

import it.nch.eb.common.utils.FullConnectionProvider;
import it.nch.eb.common.utils.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import junit.framework.AssertionFailedError;
import junit.framework.ComparisonFailure;

/**
 * TODO split in map assertions and db assertions
 * @author gdefacci
 */
public class CommonAssertions {

	private FullConnectionProvider connectionProvider;

	public CommonAssertions(FullConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}

	public void assertExistRecord(String table, String fieldName,
			String fieldValue) {
		Connection connection = connectionProvider.getConnection();
		assertExistRecord(table, fieldName, fieldValue, connection);
		connectionProvider.closeConnection(connection);
	}

	public void assertExistRecords(String table, String fieldName,
			String fieldValue, int numerOfRecords) {
		Connection connection = connectionProvider.getConnection();
		assertExistRecords(table, fieldName, fieldValue, connection,
				numerOfRecords);
		connectionProvider.closeConnection(connection);
	}

	public void assertExistRecords(String table, String fieldName,
			String fieldValue, Connection connection, int numerOfRecords) {
		ResultSet rs = null;
		try {
			Statement stm = connection.createStatement();
			rs = stm.executeQuery("select * from " + table + " where "
					+ fieldName + " = '" + fieldValue + "'");
			if (rs != null) {
				int count = 0;
				while (rs.next()) {
					count++;
				}
				if (count != numerOfRecords)
					failExistRecords(table, fieldName, fieldValue, count,
							numerOfRecords);
			} else {
				if (numerOfRecords > 0)
					failExistRecords(table, fieldName, fieldValue, 0,
							numerOfRecords);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void assertExistRecord(String table, String fieldName,
			String fieldValue, Connection connection) {
		ResultSet rs = null;
		try {
			Statement stm = connection.createStatement();
			rs = stm.executeQuery("select * from " + table + " where "
					+ fieldName + " = '" + fieldValue + "'");
			if (rs != null) {
				if (!rs.next())
					failExistRecord(table, fieldName, fieldValue, false);
			} else {
				failExistRecord(table, fieldName, fieldValue, false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void assertMapContains(Map map, String key,
			String expecetedStringValue) {
		assertMapContains(map, key, (Object)expecetedStringValue);
	}

	public static void assertMapContains(Map map, String key, Object expecetedValue) {
		Object value = map.get(key);
		if (value == null) {
			if (expecetedValue == null) {
				cantFindTheKey(map, key);
			}
		} else {
			if (expecetedValue == null) {
				cantFindTheValue(map, key, value);
			} else if (!value.equals(expecetedValue)) {
				expectingADifferentValue(map, key, expecetedValue, value);
			}
		}
	}

	/**
	 * @param map
	 * @param key
	 * @param expecetedValue
	 * @param realValue
	 * @throws ComparisonFailure
	 */
	protected static void expectingADifferentValue(Map map, String key, Object expecetedValue, Object realValue)
			throws ComparisonFailure {
		String msg = "expeceting the map contains value[" + expecetedValue 
			+ "]for key[" + key 
			+ "]but was[" + realValue + "] map " + StringUtils.toString(map);
		
		throw new ComparisonFailure(
				msg, expecetedValue.toString(), realValue
						.toString());
	}

	/**
	 * @param map
	 * @param key
	 * @param value
	 * @throws ComparisonFailure
	 */
	protected static void cantFindTheValue(Map map, String key, Object value) throws ComparisonFailure {
		throw new AssertionFailedError(
				"the map " + StringUtils.toString(map) + " doesnt contain the expeceted value for key "
						+ key + " that should value[" + value + "]");
	}

	/**
	 * @param map
	 * @param key
	 * @param expecetedValue
	 * @throws ComparisonFailure
	 */
	protected static void cantFindTheKey(Map map, String key) throws ComparisonFailure {
		throw new AssertionFailedError("the map " + StringUtils.toString(map) 
				+ " does not contains the key " + key);
	}

	/**
	 * for every key <code>k</code> of <code>map</code> <br/>
	 * if <br/>
	 * <i>v = map.get(k)</i> <br/>
	 * and <br/>
	 * <i>v1 = submap.get(k)</i>  <br/>
	 * then we have: <br/>
	 * - or <i>v1 == null</i> <br/>
	 * - or <i>v equals v1</i> <br/>
	 * - or a new <code>AssertionFailedError</code> is thrown <br/>
	 * @param expectationsMap
	 * @return
	 */
	public static void assertMapOptionallyContains(Map map, Map submap) {
		if (map == null || submap == null) throw new NullPointerException();
		if ((map != null && submap != null) || (!map.isEmpty() && !submap.isEmpty())) {
			
			for (Iterator iterator = submap.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if (map.containsKey(key)) {
					Object mapvalue = String.valueOf(map.get(key));
					if (mapvalue==null) cantFindTheValue(map, key, mapvalue);
					else {
						Object submapvalue = submap.get(key);
						if (!mapvalue.equals(submapvalue)) {
							expectingADifferentValue(map, key, submapvalue, mapvalue);
						}
					}
				} else {
					cantFindTheKey(submap, key);
					throw new AssertionFailedError("the map doesnt contain the key :" + key);
				}
			}
		}
	}

	private void failExistRecord(String table, String fieldName,
			String fieldValue, boolean exist) {
		throw new AssertionFailedError("record for table " + table + " with "
				+ fieldName + "==" + fieldValue
				+ (exist == false ? " does not exist" : " exist"));
	}

	private void failExistRecords(String table, String fieldName,
			String fieldValue, int existant, int expeceted) {
		throw new AssertionFailedError("record for table " + table + " with "
				+ fieldName + "==" + fieldValue + "[" + existant
				+ "]records found  [" + expeceted + "]expeceted");
	}

	public void assertNotExistRecord(String table, String fieldName,
			String fieldValue) {
		Connection connection = connectionProvider.getConnection();
		assertNotExistRecord(table, fieldName, fieldValue, connection);
		connectionProvider.closeConnection(connection);
	}

	public void assertNotExistRecord(String table, String fieldName,
			String fieldValue, Connection connection) {
		ResultSet rs = null;
		try {
			Statement stm = connection.createStatement();
			rs = stm.executeQuery("select * from " + table + " where "
					+ fieldName + " = '" + fieldValue + "'");
			if (rs != null) {
				if (rs.next())
					failExistRecord(table, fieldName, fieldValue, true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * all entries in <code>subMap</code> should match the ones inside <code>map</code>
	 * @param map
	 * @param subMap
	 */
	public static void assertMapContainsAll(Map map, Map subMap) {
		if (map == null || subMap == null) throw new NullPointerException();
		if ((map != null && subMap != null) || (!map.isEmpty() && !subMap.isEmpty())) {
			Set remainingKeys = shallowCloneSet( subMap.keySet() );
			
			for (Iterator iterator = subMap.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if (map.containsKey(key)) {
					Object mapvalue = String.valueOf(map.get(key));
					if (mapvalue==null) cantFindTheValue(map, key, mapvalue);
					else {
						Object submapvalue = subMap.get(key);
						if (!mapvalue.equals(submapvalue)) {
							expectingADifferentValue(map, key, submapvalue, mapvalue);
						}
					}
				} else {
					cantFindTheKey(subMap, key);
					throw new AssertionFailedError("the map doesnt contain the key :" + key);
				}
				
				remainingKeys.remove(key);
			}
			
			if (!remainingKeys.isEmpty()) {
				mapDoesNotIncludeKeys(map, remainingKeys);
			}
		}	
	}

	/**
	 * @param keySet
	 * @return
	 */
	private static Set shallowCloneSet(Set keySet) {
		Set res = new TreeSet();
		for (Iterator it = keySet.iterator(); it.hasNext();) {
			res.add(it.next());
		}
		return res;
	}

	private static void mapDoesNotIncludeKeys(Map map, Set remainingKeys) {
		StringBuffer sb = new StringBuffer("the map " + StringUtils.toString(map) + " does not include following keys \n");
		for (Iterator it = remainingKeys.iterator(); it.hasNext();) sb.append("key[" +  it.next() + "], ");
		throw new AssertionFailedError(sb.toString());
	}
	
	public static void assertMapsMatch(Map map, Map subMap) {
		assertMapContainsAll(subMap, map);
	}

}

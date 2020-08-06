/**
 * Created on 18/apr/08
 */
package it.nch.test.maps;

import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.common.utils.map.DataTypesUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class DataTypeUtilsTest extends TestCase {
	
	Map map = new HashMap();
	{
		map.put("int1" , new Integer(12));
		map.put("int2" , new Integer(22));
		map.put("st1" , "pippo");
		map.put("bool" , Boolean.TRUE);
		map.put("long" , new Long(99));
		map.put("number", new BigDecimal(22.23));
		
	}
	
	public void testGetOrElse() throws Exception {
		assertEquals(new Integer(12), DataTypesUtils.getOrElse(map, "int1", new Integer(66)));
		assertEquals(12, DataTypesUtils.getOrElse(map, "int1", 66));
		assertEquals(66, DataTypesUtils.getOrElse(map, "int doesnt exist", 66));
		assertEquals("halo", DataTypesUtils.getOrElse(map, "st1 not exist", "halo"));
		assertEquals("pippo", DataTypesUtils.getOrElse(map, "st1", "halo"));
		assertEquals(Boolean.TRUE, DataTypesUtils.getOrElse(map, "bool", Boolean.FALSE));
		assertEquals(true, DataTypesUtils.getOrElse(map, "bool", false));
		assertEquals(new Long(99), DataTypesUtils.getOrElse(map, "long", new Long(55)));
		assertEquals(new BigDecimal(22.23), DataTypesUtils.getOrElse(map, "number", new BigDecimal(55.88)));
	}
	
	public void testRemoveTrailing() throws Exception {
		String str = "0002344534534500000";
		assertEquals("000234453453450", StringUtils.removeTrailing(str, "00"));
	}
	
	public void testRemoveTrailing1() throws Exception {
		String str = "00000000000";
		assertEquals("0", StringUtils.removeTrailing(str, "00"));
	}
	
	public void testRemoveTrailing2() throws Exception {
		String str = "00000000000";
		assertEquals("", StringUtils.removeTrailing(str, "0"));
	}
	
	public void testRemoveLeading() throws Exception {
		String str = "0002344534534500000";
		assertEquals("02344534534500000", StringUtils.removeLeading(str, "00"));
	}
	
	public void testRemoveLeading1() throws Exception {
		String str = "00000000000";
		assertEquals("0", StringUtils.removeLeading(str, "00"));
	}
	
	public void testRemoveLeading2() throws Exception {
		String str = "00000000000";
		assertEquals("", StringUtils.removeLeading(str, "0"));
	}

}

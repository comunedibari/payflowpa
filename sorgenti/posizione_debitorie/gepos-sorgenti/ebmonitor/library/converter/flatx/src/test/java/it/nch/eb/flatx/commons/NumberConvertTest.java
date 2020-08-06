/**
 * Created on 13/giu/08
 */
package it.nch.eb.flatx.commons;

import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.NumberConverterInsertDecimalSeparator;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class NumberConvertTest extends TestCase {
	
	public void testNumberConverterInsertDecimalSeparator() throws Exception {
		Converter rev = new NumberConverterInsertDecimalSeparator(5, "0");
		String res = rev.encode("0000412000");
		System.out.println(res);
		assertEquals("4.12", res);
	}
	
	public void testNumberConverterInsertDecimalSeparator1() throws Exception {
		Converter rev = new NumberConverterInsertDecimalSeparator(5, "0");
		String res = rev.encode("00000000000000040500000");
		System.out.println(res);
		assertEquals("405", res);
	}
	
	public void testNumberConverterInsertDecimalSeparator2() throws Exception {
		Converter rev = new NumberConverterInsertDecimalSeparator(7, "0");
		String res = rev.encode("03300");
		System.out.println(res);
		assertEquals(".00033", res);
	}
	
	public void testNumberConverterInsertDecimalSeparator11_10() throws Exception {
		Converter rev = new NumberConverterInsertDecimalSeparator(10, "0");
		String res = rev.encode("10010000000");
		System.out.println(res);
		assertEquals("1.001", res);
	}
	
	public void testNumberConverterInsertDecimalSeparator3() throws Exception {
		Converter rev = new NumberConverterInsertDecimalSeparator(5, "0");
		String res = rev.encode("");
		System.out.println(res);
		assertEquals("0", res);
	}

}

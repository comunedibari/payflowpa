/**
 * 11/mag/2009
 */
package it.nch.eb.flatx.flatmodel;

import java.lang.reflect.Method;

import junit.framework.TestCase;

/**
 * @author gdefacci
 */
public class ArrayMethodsTests extends TestCase{
	
	static class ArrIncluder {
		private byte[] bytes;
		private char[] chars;
		private int[] ints;
		private String[] strings;
		private String pippo;
		public byte[] getBytes() {
			return bytes;
		}
		public void setBytes(byte[] bytes) {
			this.bytes = bytes;
		}
		public char[] getChars() {
			return chars;
		}
		public void setChars(char[] chars) {
			this.chars = chars;
		}
		public int[] getInts() {
			return ints;
		}
		public void setInts(int[] ints) {
			this.ints = ints;
		}
		public String[] getStrings() {
			return strings;
		}
		public void setStrings(String[] strings) {
			this.strings = strings;
		}
		public String getPippo() {
			return pippo;
		}
		public void setPippo(String pippo) {
			this.pippo = pippo;
		}
		
	}
	
	public void testReklectiojn() throws Exception {
		ArrIncluder b1 = new ArrIncluder();
		Method mthd = b1.getClass().getMethod("setBytes", new Class[] { new byte[0].getClass() });
		mthd.invoke(b1, new Object[] { new byte[] { 12, 3, 5} });
		
		assertEquals(3, b1.getBytes().length);
		assertEquals(12, b1.getBytes()[0]);
		assertEquals(3, b1.getBytes()[1]);
		assertEquals(5, b1.getBytes()[2]);
	}

}

/**
 * Created on 06/dic/07
 */
package it.nch.eb.flatx.commons;

import it.nch.eb.common.utils.Alignments;
import it.nch.eb.common.utils.RegexpUtils;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class RegexpUtilsTest extends TestCase {

	static void show(String[] parts) {
		for (int i = 0; i < parts.length; i++) {
			System.out.println(parts[i]);
		}
	}
	
	public void testRemoveFillerRight() {
		String filled = "000024443";
		assertEquals("24443", RegexpUtils.removeFiller(filled, "0", Alignments.RIGHT));
		
		String filled1 = "     24443";
		assertEquals("24443", RegexpUtils.removeFiller(filled1, " ", Alignments.RIGHT));
	}
	
	public void testRemoveFillerRight1() {
		String filled = "=|=|=|24443";
		assertEquals("24443", RegexpUtils.removeFiller(filled, "=|", Alignments.RIGHT));
		
	}
	
	public void testRemoveFillerLeft() {
		String filled = "244430000";
		assertEquals("24443", RegexpUtils.removeFiller(filled, "0", Alignments.LEFT));
		
		String filled1 = "24443     ";
		assertEquals("24443", RegexpUtils.removeFiller(filled1, " ", Alignments.LEFT));
	}
	
	public void testRemoveFillerLeft1() {
		String filled = "24443=|=|=|";
		assertEquals("24443", RegexpUtils.removeFiller(filled, "=|", Alignments.LEFT));
		
	}

	public void testSplit() {
		String str = "slayer, the.haunted., other.MotherFuckers, ,,. ";
		String parts1[] = RegexpUtils.split(str, ".");
		show(parts1);
		
		assertEquals(4, parts1.length);
		assertEquals("slayer, the", parts1[0]);
		assertEquals("haunted", parts1[1]);
		assertEquals(", other", parts1[2]);
		assertEquals("MotherFuckers, ,,", parts1[3]);
		
	}
}

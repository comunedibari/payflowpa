/**
 * Created on 29/ago/2008
 */
package it.nch.streaming.test;

import junit.framework.TestCase;
import it.nch.eb.flatx.flatmodel.xpath.AttributeXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;


/**
 * @author gdefacci
 */
public class XPathParserTest extends TestCase {
	
	XPathsParser parser = XPathsParser.instance;
	
	public void test1() throws Exception {
		XPathPosition pos = parser.parseXPathPosition("data/item");
		assertEquals(2, pos.getDepth());
		assertEquals("item", pos.getName());
		assertNull(pos.getPrefix());
		assertNotNull(pos.getParent());
		assertEquals("data", pos.getParent().getName());
		assertNull(pos.getParent().getPrefix());
	}
	
	public void test2() throws Exception {
		XPathPosition pos = parser.parseXPathPosition("prfx:data/d:item");
		assertEquals(2, pos.getDepth());
		assertEquals("item", pos.getName());
		assertEquals("d", pos.getPrefix());
		assertNotNull(pos.getParent());
		assertEquals("data", pos.getParent().getName());
		assertEquals("prfx", pos.getParent().getPrefix());
	}

	public void test3() throws Exception {
		XPathPosition pos = parser.parseXPathPosition("one:more/prfx:data/item");
		assertEquals(3, pos.getDepth());
		assertEquals("item", pos.getName());
		assertNull( pos.getPrefix());
		assertNotNull(pos.getParent());
		assertEquals("data", pos.getParent().getName());
		assertEquals("prfx", pos.getParent().getPrefix());
	}
	
	public void test4() throws Exception {
		AttributeXPathPosition pos = parser.parseXPathAttribute("one:more/prfx:data/@item");
		assertEquals(3, pos.getDepth());
		assertEquals("item", pos.getName());
		assertNull( pos.getPrefix());
		assertNotNull(pos.getParent());
		assertEquals("data", pos.getParent().getName());
		assertEquals("prfx", pos.getParent().getPrefix());
	}
	
	public void test1idx() throws Exception {
		XPathPosition pos = parser.parseXPathPosition("data[2]/item");
		assertEquals(2, pos.getDepth());
		assertEquals("item", pos.getName());
		assertNull(pos.getPrefix());
		assertNotNull(pos.getParent());
		assertEquals("data", pos.getParent().getName());
		assertEquals(2, pos.getParent().getIndex());
		assertNull(pos.getParent().getPrefix());
	}
	
	public void test2idx() throws Exception {
		XPathPosition pos = parser.parseXPathPosition("prfx:data[3]/d:item[2]");
		assertEquals(2, pos.getDepth());
		assertEquals("item", pos.getName());
		assertEquals(2, pos.getIndex());
		assertEquals("d", pos.getPrefix());
		assertNotNull(pos.getParent());
		assertEquals("data", pos.getParent().getName());
		assertEquals(3, pos.getParent().getIndex());
		assertEquals("prfx", pos.getParent().getPrefix());
	}

	public void test3idx() throws Exception {
		XPathPosition pos = parser.parseXPathPosition("one:more[5]/prfx:data/item[2]");
		assertEquals(3, pos.getDepth());
		assertEquals("item", pos.getName());
		assertEquals(2, pos.getIndex());
		assertNull( pos.getPrefix());
		assertNotNull(pos.getParent());
		assertEquals("data", pos.getParent().getName());
		assertEquals("more", pos.getParent().getParent().getName());
		assertEquals(5, pos.getParent().getParent().getIndex());
		assertEquals("prfx", pos.getParent().getPrefix());
	}
	
	public void test4idx() throws Exception {
		AttributeXPathPosition pos = parser.parseXPathAttribute("one:more/prfx:data[3]/@item");
		assertEquals(3, pos.getDepth());
		assertEquals("item", pos.getName());
		assertNull( pos.getPrefix());
		assertNotNull(pos.getParent());
		assertEquals("data", pos.getParent().getName());
		assertEquals(3, pos.getParent().getIndex());
		assertEquals("prfx", pos.getParent().getPrefix());
	}
}

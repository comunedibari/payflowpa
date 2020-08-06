/**
 * Created on 03/set/2008
 */
package it.nch.streaming.test;

import it.nch.eb.flatx.flatmodel.xpath.AttributeXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class XPathsBindingsTest extends TestCase {

	XPathsParser parser = XPathsParser.instance;
	
	public void testNest() throws Exception {
		XPathPosition pos = parser.parseXPathPosition("data/item");
		XPathPosition posBy2 = parser.parseXPathPosition("data/item[2]");
		XPathPosition pos1 = pos.element("p1");
		AttributeXPathPosition pos2 = pos.attribute("att2");
		XPathPosition pos3 = pos1.element("p3");
		XPathPosition pos4 = pos1.element("p4");
		XPathPosition pos5 = pos.element("p5");
		XPathPosition pos6 = pos.element("p6");
		XPathsMapBindings bindings = new XPathsMapBindings();
		bindings.enterScope();
		bindings.put(pos, "a");
		bindings.put(posBy2, "a1");
		bindings.put(pos1, "b");
		bindings.put(pos2, "c");
		
		assertEquals(4, bindings.size());
		
		bindings.enterScope();
		bindings.put(pos3, "d");
		bindings.put(pos4, "e");
		bindings.put(pos5, "f");
		bindings.put(pos6, "g");
		
		assertEquals(8, bindings.size());
		
		assertTrue(bindings.get(pos).equals("a"));
		assertTrue(bindings.get(posBy2).equals("a1"));
		assertTrue(bindings.get(pos1).equals("b"));
		assertTrue(bindings.get(pos2).equals("c"));
		
		assertTrue(bindings.get(pos3).equals("d"));
		assertTrue(bindings.get(pos4).equals("e"));
		assertTrue(bindings.get(pos5).equals("f"));
		assertTrue(bindings.get(pos6).equals("g"));
		
		bindings.exitScope();
		
		assertTrue(bindings.get(pos).equals("a"));
		assertTrue(bindings.get(pos1).equals("b"));
		assertTrue(bindings.get(pos2).equals("c"));
		
		assertTrue(bindings.get(pos3) == null);
		assertTrue(bindings.get(pos4) == null);
		assertTrue(bindings.get(pos5) == null);
		assertTrue(bindings.get(pos6) == null);
		
		bindings.exitScope();
		
		assertTrue(bindings.get(pos) == null);
		assertTrue(bindings.get(pos1) == null);
		assertTrue(bindings.get(pos2) == null);
		
		bindings.enterScope();
		bindings.put(pos, "a");
		bindings.put(pos1, "b");
		bindings.put(pos2, "c");

		assertEquals(3, bindings.size());
		assertTrue(bindings.get(pos).equals("a"));
		assertTrue(bindings.get(pos1).equals("b"));
		assertTrue(bindings.get(pos2).equals("c"));
		bindings.exitScope();
	}
	
	public void testDefine1() throws Exception {
		XPathPosition pos = parser.parseXPathPosition("data/item");
		XPathPosition posBy2 = parser.parseXPathPosition("data/item[2]");
		XPathPosition pos1 = pos.element("p1");
		AttributeXPathPosition pos2 = pos.attribute("att2");
		XPathPosition pos3 = pos1.element("p3");
		XPathPosition pos4 = pos1.element("p4");
		XPathPosition pos5 = pos.element("p5");
		XPathPosition pos6 = pos.element("p6");
		XPathsMapBindings bindings = new XPathsMapBindings();
		bindings.enterScope();
		bindings.put(pos, "a");
		bindings.put(posBy2, "a1");
		bindings.put(pos1, "b");
		bindings.put(pos2, "c");
		
		bindings.define(pos6);
		
		assertEquals(4, bindings.size());
		
		bindings.enterScope();
		bindings.put(pos3, "d");
		bindings.put(pos4, "e");
		bindings.put(pos5, "f");
		bindings.put(pos6, "g");
		
		assertEquals(8, bindings.size());
		
		assertTrue(bindings.get(pos).equals("a"));
		assertTrue(bindings.get(posBy2).equals("a1"));
		assertTrue(bindings.get(pos1).equals("b"));
		assertTrue(bindings.get(pos2).equals("c"));
		
		assertTrue(bindings.get(pos3).equals("d"));
		assertTrue(bindings.get(pos4).equals("e"));
		assertTrue(bindings.get(pos5).equals("f"));
		assertTrue(bindings.get(pos6).equals("g"));
		
		bindings.exitScope();
		
		assertTrue(bindings.get(pos).equals("a"));
		assertTrue(bindings.get(pos1).equals("b"));
		assertTrue(bindings.get(pos2).equals("c"));
		
		assertTrue(bindings.get(pos3) == null);
		assertTrue(bindings.get(pos4) == null);
		assertTrue(bindings.get(pos5) == null);
		assertTrue(bindings.get(pos6).equals("g"));
		
		bindings.exitScope();
		
		assertTrue(bindings.get(pos) == null);
		assertTrue(bindings.get(pos1) == null);
		assertTrue(bindings.get(pos2) == null);
		assertTrue(bindings.get(pos6) == null);
		
		bindings.enterScope();
		bindings.put(pos, "a");
		bindings.put(pos1, "b");
		bindings.put(pos2, "c");

		assertEquals(3, bindings.size());
		assertTrue(bindings.get(pos).equals("a"));
		assertTrue(bindings.get(pos1).equals("b"));
		assertTrue(bindings.get(pos2).equals("c"));
		
		assertTrue(bindings.get(pos6) == null); 
		
		bindings.exitScope();
		
		assertTrue(bindings.getParent().isRoot());
	}
	
	public void testDefine2() throws Exception {
		XPathPosition pos = parser.parseXPathPosition("data/item");
		XPathPosition posBy2 = parser.parseXPathPosition("data/item[2]");
		XPathPosition pos1 = pos.element("p1");
		AttributeXPathPosition pos2 = pos.attribute("att2");
		XPathPosition pos3 = pos1.element("p3");
		XPathPosition pos4 = pos1.element("p4");
		XPathPosition pos5 = pos.element("p5");
		XPathPosition pos6 = pos.element("p6");
		XPathsMapBindings bindings = new XPathsMapBindings();
		bindings.define(pos6);
		bindings.enterScope();
		bindings.put(pos, "a");
		bindings.put(posBy2, "a1");
		bindings.put(pos1, "b");
		bindings.put(pos2, "c");
		
		assertEquals(4, bindings.size());
		
		bindings.enterScope();
		bindings.put(pos3, "d");
		bindings.put(pos4, "e");
		bindings.put(pos5, "f");
		bindings.put(pos6, "g");
		
		assertEquals(8, bindings.size());
		
		assertTrue(bindings.get(pos).equals("a"));
		assertTrue(bindings.get(posBy2).equals("a1"));
		assertTrue(bindings.get(pos1).equals("b"));
		assertTrue(bindings.get(pos2).equals("c"));
		
		assertTrue(bindings.get(pos3).equals("d"));
		assertTrue(bindings.get(pos4).equals("e"));
		assertTrue(bindings.get(pos5).equals("f"));
		assertTrue(bindings.get(pos6).equals("g"));
		
		bindings.exitScope();
		
		assertTrue(bindings.get(pos).equals("a"));
		assertTrue(bindings.get(pos1).equals("b"));
		assertTrue(bindings.get(pos2).equals("c"));
		
		assertTrue(bindings.get(pos3) == null);
		assertTrue(bindings.get(pos4) == null);
		assertTrue(bindings.get(pos5) == null);
		assertTrue(bindings.get(pos6).equals("g"));
		
		bindings.exitScope();
		
		assertTrue(bindings.get(pos) == null);
		assertTrue(bindings.get(pos1) == null);
		assertTrue(bindings.get(pos2) == null);
		assertTrue(bindings.get(pos6).equals("g"));
		
		bindings.enterScope();
		bindings.put(pos, "a");
		bindings.put(pos1, "b");
		bindings.put(pos2, "c");

		assertEquals(4, bindings.size());
		assertTrue(bindings.get(pos).equals("a"));
		assertTrue(bindings.get(pos1).equals("b"));
		assertTrue(bindings.get(pos2).equals("c"));
		
		assertTrue(bindings.get(pos6).equals("g"));
		
		bindings.exitScope();
		
		assertTrue(bindings.get(pos6).equals("g"));
		
		assertTrue(bindings.getParent().isRoot());
	}


}

/*
 * Copyright (C) 2004, 2007 Riccardo Solmi.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the GNU Lesser General Public License
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 */
package it.nch.fwk.xml.tests;

import it.nch.eb.common.utils.bindings.BindingManagerFactory;
import it.nch.eb.common.utils.bindings.IBindingManager;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;

/**
 * @author Riccardo Solmi
 */
public class BindingManagerTest extends TestCase {
	private IBindingManager bm;
	
	static class StringFactory {
		static String s[] = { "Allegati","SequenzaFasi","DescrizioneFase","Regole","RegolaPopolamento","Nome","Descrizione","Fase","FaseValidazione","Fasi" };
		String create() {
			int idx = new Random().nextInt(s.length); 
			return new String(s[idx]);
		}
		public String create(String string) {
			return create() + string;
		}
	}
	
	static class IntegerFactory {
		Integer create(String str) {
			return create();
		}
		Integer create() {
			int idx = new Random().nextInt(100); 
			return new Integer(idx);
		}
	}
	
	IntegerFactory intFactory;
	StringFactory stringFactory;

	protected void setUp() throws Exception {
		super.setUp();
		
		bm = BindingManagerFactory.instance.createBindingManager();
		intFactory = new IntegerFactory();
		stringFactory = new StringFactory();
	}

	public void testScopeProtocol() {
		bm.enterScope();
		bm.exitScope();

		bm.enterScope();
		bm.enterScope();
		bm.exitScope();
		bm.exitScope();		
	}

	public void testScopeProtocolError1() {
		try {
			bm.exitScope();
			fail();
		} catch (Exception e) {
		}
	}
	public void testScopeProtocolError2() {
		try {
			bm.enterScope();
			bm.enterScope();
			bm.exitScope();
			bm.exitScope();
			bm.exitScope();
			fail();
		} catch (Exception e) {
		}
	}

	public void testBindProtocolError() {
		try {
			bm.set("undefName", intFactory.create("aName"));
			fail();
		} catch (Exception e) {
		}

		try {
			bm.set("undefName", "a value");
			fail();
		} catch (Exception e) {
		}
	}

	public void testBindEntities() {
		Integer e1 = intFactory.create("aName");
		Integer e2 = intFactory.create("aName");

		bm.define("id1", e1);
		bm.define("id2", e2);
		assertEquals(e1, bm.get("id1"));
		assertEquals(e2, bm.get("id2"));

		bm.set("id1", e2);
		assertEquals(e2, bm.get("id1"));
		assertEquals(e2, bm.get("id2"));
		
		assertNull(bm.get("unregistered"));
	}

	public void testBindInScope() {
		Integer e1 = intFactory.create("aName");
		Integer e2 = intFactory.create("aName");
		Object e3 = stringFactory.create("data");
		Object e4 = stringFactory.create("aName");

		bm.define("id1", e1);
		bm.define("id2", e2);
		bm.enterScope();
		bm.define("id2", e3);
		bm.define("id3", e4);
		assertEquals(e1, bm.get("id1"));
		assertEquals(e3, bm.get("id2"));
		assertEquals(e4, bm.get("id3"));
		bm.exitScope();
		assertEquals(e1, bm.get("id1"));
		assertEquals(e2, bm.get("id2"));
		assertNull(bm.get("id3"));
	}
	public void testBindInStaticScope() {
		Integer e1 = intFactory.create("aName");
		Integer e2 = intFactory.create("aName");
		String e3 = stringFactory.create("data");
		String e4 = stringFactory.create("aName");

		IBindingManager sbm = BindingManagerFactory.instance.createBindingManager();
		sbm.define("id2", e3);
		sbm.define("id3", e4);
		sbm.enterScope();
		sbm.define("id4", e3);

		bm.define("id1", e1);
		bm.define("id2", e2);
//		bm.enterScope(sbm);
//		assertNull(bm.wGet("id1"));
//		assertEquals(e3, bm.wGet("id2"));
//		assertEquals(e4, bm.wGet("id3"));
//		assertEquals(e3, bm.wGet("id4"));
//		bm.exitScope();
//		assertEquals(e1, bm.wGet("id1"));
//		assertEquals(e2, bm.wGet("id2"));
//		assertNull(bm.wGet("id3"));
//		assertNull(bm.wGet("id4"));
	}

	public void testDefUse() {
		Integer e1 = intFactory.create("aName1");
		Integer e2 = intFactory.create("aName2");
		Integer e3 = intFactory.create("aName3");
		Integer e4 = intFactory.create("aName4");

		bm.define("id1", e1);
		bm.define("id2", e2);

		bm.define("id1", e2);
		assertEquals(e2, bm.get("id1"));
		bm.set("id1", e1);
		assertEquals(e1, bm.get("id1"));

		bm.enterScope();
		bm.define("id1", e3);

		bm.set("id1", e4);
		assertEquals(e4, bm.get("id1"));
		bm.set("id2", e4);
		assertEquals(e4, bm.get("id2"));

		bm.exitScope();
		assertEquals(e1, bm.get("id1"));
		assertEquals(e4, bm.get("id2"));
	}

	public void testIsSetUnset() {
		assertFalse(bm.isSet("a"));
		bm.define("a", new Integer(10));
		assertTrue(bm.isSet("a"));
		bm.unset("a");
		assertFalse(bm.isSet("a"));
	}

	public void testIsSetUnsetUndefined() {
		bm.unset("a");
		assertFalse(bm.isSet("a"));
	}

	public void testSetUnsetInScope() {
		bm.define("a", new Integer(10));
		bm.define("c", new Integer(140));
		bm.enterScope();
		bm.define("b", new Integer(140));
		assertTrue(bm.isSet("a"));
		assertTrue(bm.isSet("c"));
		assertTrue(bm.isSet("b"));
		bm.define("a", new Integer(20));
		bm.unset("a");
		assertTrue(bm.isSet("a"));
		bm.unset("c");
		assertFalse(bm.isSet("c"));
		bm.enterScope();
		assertTrue(bm.isSet("a"));
		assertFalse(bm.isSet("c"));
		
//		assertFalse(bm.isSet("b"));
		assertTrue(bm.isSet("b"));
	}
	
	public void testNames() {
		IBindingManager bm1 = BindingManagerFactory.instance.createBindingManager();
		Set/*<String>*/ localNames, names;
		
		bm1.define("a", 1);
		bm1.define("b", 2);
		bm1.define("c", 3);
		
		localNames = bm1.localNames();
		names = bm1.names();
		assertEquals(localNames, names);
		assertEquals(3, localNames.size());

		bm1.enterScope();
		bm1.define("d", 4);
		bm1.define("e", 5);

		localNames = bm1.localNames();
		names = bm1.names();
		assertEquals(2, localNames.size());
		assertEquals(5, names.size());
		assertTrue(names.containsAll(Arrays.asList(new String[] {
				"a", "b", "c", "d", "e"})));

		IBindingManager bm2 = BindingManagerFactory.instance.createBindingManager();
		
		bm2.define("f", 6);
		bm2.define("g", 7);
		bm2.define("h", 8);
		bm2.define("i", 9);

//		bm2.enterScope(bm1, true);
		bm2.enterScope(bm1);

		localNames = bm2.localNames();
		names = bm2.names();
		assertEquals(bm1.localNames().size(), localNames.size());
		assertTrue(localNames.containsAll(bm1.localNames()));
		assertEquals(4+bm1.names().size(), names.size());

		bm2.exitScope();

		localNames = bm2.localNames();
		names = bm2.names();
		assertEquals(localNames, names);
		assertEquals(4, localNames.size());

//		bm2.enterScope(bm1, false);
		bm2.enterScope(bm1);

		localNames = bm2.localNames();
		names = bm2.names();
		assertEquals(bm1.localNames().size(), localNames.size());
		assertTrue(localNames.containsAll(bm1.localNames()));
//		assertEquals(bm1.names().size(), names.size());
		assertTrue(names.containsAll(bm1.names()));
	}
}

/**
 * Created on 28/ago/07
 */
package it.nch.fwk.xml.tests;

import it.nch.fwk.checks.IElementCursor;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * @author gdefacci
 */
public class ChildrenIteratorTest extends TestCase  {
	
	public void testIteration() throws Exception {
		String classpathResourceName = "xml/simple.xml";
		IElementCursor elem = ResourcesUtilTestsHelper.parse(classpathResourceName);
		
		Iterator it/*<IElementCursor>*/ = elem.xpathIterator("aElement");

		String[] outcome = new String[] {"10", "20", "30", };
		
		assertTrue(it.hasNext());
		
		int idx = 0;
		while (it.hasNext()) {
			IElementCursor child = (IElementCursor) it.next();
			String strNumber = child.childStringValue("number");
			System.out.println(strNumber);
			assertEquals(outcome[idx], strNumber);
			idx++;
		}
	}

	

}

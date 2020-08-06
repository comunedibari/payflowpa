/**
 * Created on 18/dic/07
 */
package it.nch.fwk.xml.tests;

import java.math.BigDecimal;

import it.nch.fwk.checks.IElementCursor;
import it.nch.fwk.test.Testable;
import it.nch.fwk.test.ThrowsAssertor;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class NullElementCursorTest extends TestCase {

	final static String theXml = "<root> <child1 iid='123'>first</child1> <childn>427</childn> </root>";
	
	public void testNullElementCursor1() throws Exception {
		final IElementCursor elem = ResourcesUtilTestsHelper.parseContent(theXml);
		
		assertEquals(427, elem.childIntValue("/root/childn").intValue());
		assertEquals(null, elem.childIntValue("/root/child2"));		// does not exist
		
		ThrowsAssertor.assertThrow(NumberFormatException.class, new Testable() {

			public void test() {
				Integer i = elem.childIntValue("/root/child1");
			}
			
		});
		
		assertEquals(123, elem.child("/root/child1").attributeIntValue("iid").intValue() );
		
		BigDecimal bd =  BigDecimal.valueOf(427);
		assertEquals(bd, elem.childBigDecimalValue("/root/childn"));
		assertNull(elem.childBigDecimalValue("/root/childn/thisExistNot"));
		assertNull(elem.childStringValue("/root/childn/thisExistNot"));
		
	}
	
}

/**
 * 
 */
package it.nch.eb.flatx.converters;

import it.nch.eb.flatx.flatmodel.converters.Conversions;
import junit.framework.TestCase;

/**
 * @author gdefacci
 *
 */
public class NumericConversionsTest extends TestCase {
	
	public void test1() throws Exception {
		Conversions conversions = new Conversions();
		String res = conversions.LONG.encode("   12  ----");
		System.out.println(res);
	}

}

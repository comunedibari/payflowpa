/**
 * Created on 19/feb/2009
 */
package it.nch.eb.common.utils.test;

import it.nch.eb.common.utils.StringUtils;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class ConcatPkgsTest extends TestCase {
	
	public void testConcatPkg() throws Exception {
		String[] pkgs = {"fir", "sec.", ".thrd.", "forth."};
		String res = StringUtils.concatPackages(pkgs);
		assertEquals("fir.sec.thrd.forth", res);
	}

}

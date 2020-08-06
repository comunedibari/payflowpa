/**
 * Created on 23/ott/08
 */
package it.nch.test.maps;

import it.nch.eb.common.utils.StringUtils;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class StringUtilsTest extends TestCase {
	
	public void testToConstName() throws Exception {
		String cmlCas = "vedBuensEnde";
		String expcted = "VED_BUENS_ENDE";
		assertEquals(expcted, StringUtils.toConstName(cmlCas));
	}
	
	public void testToConstName1() throws Exception {
		String cmlCas = "VEDBuensEnde";
		String expcted = "VED_BUENS_ENDE";
		assertEquals(expcted, StringUtils.toConstName(cmlCas));
	}

}

/**
 * 21/ott/2009
 */
package it.nch.xml2sql.validations;

import it.nch.eb.xsqlcmd.utils.ModelValidationsUtils;
import junit.framework.TestCase;

/**
 * @author gdefacci
 */
public class CFTest extends TestCase {
	
	String[] validOnes = new String[] {
			"BCCNDR68M08A944I",
			"SCCMCH63D59D548D",
			"LCCPRB65E45F257J",
	};
	
	public void testCF1() throws Exception {
		for (int i = 0; i < validOnes.length; i++) {
			System.out.println( ModelValidationsUtils.checkCodiceFiscale(validOnes[i]) );
		}
		
		System.out.println( ModelValidationsUtils.checkCodiceFiscale("SCCMCH63D59D548D") );
	}

}

/**
 * 17/dic/2009
 */
package it.nch.eb.common.converter.pmtreq.dbtr.models.xml;

import java.util.regex.Pattern;

import it.nch.eb.common.converter.pmtreq.dbtr.models.Record01Model;

/**
 * @author gdefacci
 */
public class XmlRecord01Model extends Record01Model {

	private static Pattern zeroFillRg = Pattern.compile("^[0]*$");
	
	private static boolean isZeroFilled(String str) {
		Pattern pattern = zeroFillRg;
		return pattern.matcher(str).matches();
	}
	
	public boolean getShowNbOfTxsPerSts() {
		return !isZeroFilled(this.getDtldNbOfTxs());
	}

}

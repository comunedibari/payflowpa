/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import it.nch.eb.common.utils.StringUtils;
import junit.framework.TestCase;

/**
 * @author gdefacci
 *
 */
public class CharTest extends TestCase {
	
	String javaName(String str) {
		String r1 = str.replaceAll("\\&", "_");
		String[] parts = r1.trim().toLowerCase().split("_");
		StringBuffer sb = new StringBuffer();
		sb.append(parts[0]);
		int idx = 1;
		while (idx < parts.length) {
			sb.append(StringUtils.capitalized(parts[idx]));
			idx++;
		}
		return sb.toString();
	}
	
	public void testch1() throws Exception {
		String str = "TIPO&OPERATORE";
		
		String dbNm = getDbName(str);
		String jNm = javaName(str);
		
		System.out.println(dbNm);
		System.out.println(jNm);
	}

	/**
	 * @param str
	 * @return
	 */
	private String getDbName(String str) {
		return str.replaceAll("\\&", "");
	}

}

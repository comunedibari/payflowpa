/**
 * Created on 20/mar/08
 */
package it.nch.flatfile.xls.generate;

import it.nch.eb.flatx.generator.xls.XlsUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class RegexpTest extends TestCase {
	
	public static void test2() {
		String literal = "\'fuck you\'";
		Matcher matcher = XlsUtil.singleQuatedPattern.matcher(literal);
		assertTrue( matcher.matches() ); 
		matcher = XlsUtil.doubleQuatedPattern.matcher("\"asshole \"");
		assertTrue( matcher.matches() );
	}
	
	public static void test1() {
		String xpath = "/CPSR:CBICdtrPmtStatusReport/CPSR:GrpHdr/CPSR:InitgPty/CPSR:Id/CPSR:OrgId/CPSR:TaxIdNb";
		String xpath1 = "/CBICdtrPmtStatusReport/";
		String xpath2 = "/CPSR:CBICdtrPmtStatusReport/CPSR:GrpHdr/CPSR:IdE2E";
		String xpath3 = "/CPSR:CBICdtrPmtStatusReport/CPSR:TxInfAndSts/CPSR:OrgnlTxRef/CPSR:Amt/@Ccy";
		String xpath4 = "/CPSR:CBICdtrPmtStatusReport/CPSR:TxInfAndSts/CPSR:OrgnlTxRef/CPSR:Amt[18]";
		String xpath5 = "/CPSR:CBICdtrPmtStatusReport/CPSR:TxInfAndSts/CPSR:OrgnlTxRef/CPSR:SttlmInf/CPSR:InstgRmbrsmntAgt/CPSR:FinInstnId/CPSR:NmAndAdr/CPSR:PstlAdr/CPSR:AdrLine[1]";
		String val = "CPSR";
		Pattern xpathPattern = XlsUtil.xpathPattern;
		Pattern patter = xpathPattern;
		Matcher matcher = patter.matcher(xpath);
		assertTrue( matcher.matches() );
		matcher = patter.matcher(val);
		assertFalse( matcher.matches() );
		matcher = patter.matcher(xpath1);
		assertTrue( matcher.matches());
		matcher = patter.matcher(xpath2);
		assertTrue( matcher.matches());
		matcher = patter.matcher(xpath3);
		assertTrue( matcher.matches());
		matcher = patter.matcher(xpath4);
		assertTrue( matcher.matches());
		matcher = patter.matcher(xpath5);
		assertTrue( matcher.matches());

	}
	
	public void testXpaths() throws Exception {
		String xp = "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/ÏBODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:DbtrAcct/PMRQ:Id/PMRQ:IBAN";
		Pattern patter = XlsUtil.xpathPattern;
		Matcher matcher = patter.matcher(xp);
		assertFalse( matcher.matches() ); // contains an 'Ï' 
		
	}
	
	static boolean doesMatch(Pattern pattern, String value) {
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	
	public static void testMatchLiteralSymbols() {
		String literal = "\'fuck you\'";
		String literal1 = "\'+\'";
		String literal2 = "\'+..dfsd \"773ew8 \'";
		Pattern pattern = Pattern.compile("[\'].*[\']");
		assertTrue(doesMatch(pattern, literal)); 
		assertTrue(doesMatch(pattern, literal1)); 
		assertTrue(doesMatch(pattern, literal2)); 
	}

}

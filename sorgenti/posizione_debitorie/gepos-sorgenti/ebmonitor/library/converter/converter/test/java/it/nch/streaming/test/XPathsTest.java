/**
 * Created on 02/set/2008
 */
package it.nch.streaming.test;

import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class XPathsTest extends TestCase {
	
	XPathsParser parser = XPathsParser.instance;

	XPathPosition pos1 = parser.parseXPathPosition("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBISgnInf");		
	XPathPosition pos2 = parser.parseXPathPosition("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest");

	
	public void test1() throws Exception {
		assertFalse(pos1.equals(pos2));
		assertFalse(pos1.getUnindexed().equals(pos2));
		assertFalse(pos1.getUnindexed().equals(pos2.getUnindexed()));
	}
	
	public void testDotAndCo() throws Exception {
		BaseXPathPosition dot = XPathsParser.instance.parseXPath(".");
		BaseXPathPosition p1 = pos1.concat(dot);
		System.out.println(p1);
		assertEquals(pos1, p1);
	}

}

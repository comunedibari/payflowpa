/**
 * Created on 11/mar/2009
 */
package it.nch.eb.flatx.xpath;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathUtils;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;
import it.nch.fwk.core.NamespacesInfos;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class XPath1Test extends TestCase {
	
	NamespacesInfos	n1	= new NamespacesInfos(new String[][] { 
			{ "MSG", "urn:CBI:xsd:CBIPaymentRequestMsg.00.03.00" },
			{ "BODY", "urn:CBI:xsd:CBIBdyPaymentRequest.00.03.00" }, 
			{ "HE2E", "urn:CBI:xsd:CBIHdrSrv.001.07" },
			{ "HTRT", "urn:CBI:xsd:CBIHdrTrt.001.07" }, 
			{ "PMRQ", "urn:CBI:xsd:CBIPaymentRequest.00.03.00" },
			{ "SGNT", "urn:CBI:xsd:CBISgnInf.001.04" }, 
	});
	
	NamespacesInfos	n2	= new NamespacesInfos(new String[][] { 
			{ "", "urn:CBI:xsd:CBIPaymentRequestMsg.00.03.00" },
			{ "B", "urn:CBI:xsd:CBIBdyPaymentRequest.00.03.00" }, 
			{ "H", "urn:CBI:xsd:CBIHdrSrv.001.07" },
			{ "HT", "urn:CBI:xsd:CBIHdrTrt.001.07" }, 
			{ "P", "urn:CBI:xsd:CBIPaymentRequest.00.03.00" },
			{ "SGNT", "urn:CBI:xsd:CBISgnInf.001.04" }, 
	});
	
	public void test1() throws Exception {
		String p1 = "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf/PMRQ:RmtInf/PMRQ:Ustrd";
		String p2 = "/CBIPaymentRequestMsg/CBIBdyPaymentRequest/B:CBIEnvelPaymentRequest/B:CBIPaymentRequest/P:PmtInf/P:CdtTrfTxInf/P:RmtInf/P:Ustrd";
		
		XPathPosition xp1 = XPathsParser.instance.parseXPathPosition(p1);
		XPathPosition xp2 = XPathsParser.instance.parseXPathPosition(p2);
		
		assertTrue(XPathUtils.sharedInstance.match(xp1, n1, xp2, n2));
	}
	
	public void testContainsSegment() throws Exception {
		String p1 = "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf/PMRQ:RmtInf/PMRQ:Ustrd";
		String p2 = "/CBIPaymentRequestMsg/CBIBdyPaymentRequest/B:CBIEnvelPaymentRequest/B:CBIPaymentRequest/P:PmtInf/P:CdtTrfTxInf/P:RmtInf/P:Ustrd";
		
		XPathPosition xp1 = XPathsParser.instance.parseXPathPosition(p1);
		XPathPosition xp2 = XPathsParser.instance.parseXPathPosition(p2);
		
		assertTrue( XPathUtils.sharedInstance.containSegment(xp1, "BODY:CBIEnvelPaymentRequest") );
		assertFalse( XPathUtils.sharedInstance.containSegment(xp1, "BOYD:CBIEnvelPaymentRequest") );
		
		assertTrue( XPathUtils.sharedInstance.containSegment(xp2, "P:CdtTrfTxInf") );
		assertFalse( XPathUtils.sharedInstance.containSegment(xp2, "BOYD:CBIEnvelPaymentRequest") );
	}

}

package it.nch.eb.flatx.xpath;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathUtils;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;
import it.nch.fwk.test.AssertThrows;
import it.nch.fwk.test.Testable;
import junit.framework.TestCase;

/**
 * @author gdefacci
 */
public class RelativeXPathTest extends TestCase {
	
//	NamespacesInfos	n1	= new NamespacesInfos(new String[][] { 
//			{ "", "urn:CBI:xsd:CBIPaymentRequestMsg.00.03.00" },
//			{ "BODY", "urn:CBI:xsd:CBIBdyPaymentRequest.00.03.00" }, 
//			{ "HE2E", "urn:CBI:xsd:CBIHdrSrv.001.07" },
//			{ "HTRT", "urn:CBI:xsd:CBIHdrTrt.001.07" }, 
//			{ "PMRQ", "urn:CBI:xsd:CBIPaymentRequest.00.03.00" },
//			{ "SGNT", "urn:CBI:xsd:CBISgnInf.001.04" }
//	});
	
	public void testRel1() throws Exception {
		System.out.println("testRel1");
		String p1 = "pippo/BODY:pluto/paperino";
		String p2 = "pippo/minni/id";
		String p3a = "pippo/BODY:pluto/paperino/quo";
		String p3b = "pippo/BODY:pluto/paperino/qua";
		XPathPosition xp1 = XPathsParser.instance.parseXPathPosition(p1);
		XPathPosition xp2 = XPathsParser.instance.parseXPathPosition(p2);
		XPathPosition xp3a = XPathsParser.instance.parseXPathPosition(p3a);
		XPathPosition xp3b = XPathsParser.instance.parseXPathPosition(p3b);

		XPathUtils utils = XPathUtils.sharedInstance;
		
		System.out.println(utils.relative(xp1, xp2));
		System.out.println(utils.relative(xp1, xp3a));
		System.out.println(utils.relative(xp1, xp3b));
		
		String ep2 = "../../minni/id";
		String ep3a = "quo";
		String ep3b = "qua";
		XPathPosition exp2 = XPathsParser.instance.parseXPathPosition(ep2);
		XPathPosition exp3a = XPathsParser.instance.parseXPathPosition(ep3a);
		XPathPosition exp3b = XPathsParser.instance.parseXPathPosition(ep3b);
		
		assertEquals(exp2, utils.relative(xp1, xp2));
		assertEquals(exp3a, utils.relative(xp1, xp3a));
		assertEquals(exp3b, utils.relative(xp1, xp3b));
		
		
	}
	
	public void testRel2() throws Exception {
		System.out.println("testRel2");
		String ps1 = "/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/Insert/InfoPagamento/DettaglioPagamento/Allegato";
		String ps2 = "/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/Insert/InfoPagamento/DettaglioPagamento/DettaglioImporto/Voce";
		
		XPathPosition p1 = XPathsParser.instance.parseXPathPosition(ps1);
		XPathPosition p2 = XPathsParser.instance.parseXPathPosition(ps2);
		XPathPosition p3 = XPathsParser.instance.parseXPathPosition("../../Allegato");
		
		System.out.println(XPathUtils.sharedInstance.relative(p1, p2));
		System.out.println(XPathUtils.sharedInstance.relative(p2, p1));
		System.out.println(XPathUtils.sharedInstance.relative(p2, p3));
		
		
	}
	
	public void testStarWith() throws Exception {
		System.out.println("testStartsWith");
		String ps1 = "/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/Insert/InfoPagamento/DettaglioPagamento/Allegato";
		String ps2 = "/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/Insert/InfoPagamento/DettaglioPagamento/Allegato[4]";
		String ps3 = "/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/Insert/InfoPagamento[3]/DettaglioPagamento/Allegato";
		
		XPathPosition p1 = XPathsParser.instance.parseXPathPosition(ps1);
		XPathPosition p2 = XPathsParser.instance.parseXPathPosition(ps2);
		XPathPosition p3 = XPathsParser.instance.parseXPathPosition(ps3);

		System.out.println(p1);
		System.out.println(p2.startsWith(p1));
		System.out.println(p3.startsWith(p1));
		
	}
	
	public void testSplitXPath() throws Exception {
		System.out.println("testSplitPath");
		final String ps1 = "/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/Insert/InfoPagamento/DettaglioPagamento/Allegato";
		
		String[] ps1Parts1 = XPathUtils.sharedInstance.splitOnSegment(ps1, "Pendenza");
		assertEquals(2, ps1Parts1.length);
		assertEquals("/IdpAllineamentoPendenzeEnte/IdpBody", ps1Parts1[0]);
		assertEquals("/Insert/InfoPagamento/DettaglioPagamento/Allegato", ps1Parts1[1]);
		
		String[] ps1Parts2 = XPathUtils.sharedInstance.splitOnSegment(ps1, "IdpAllineamentoPendenzeEnte");
		assertEquals(2, ps1Parts2.length);
		assertNull(ps1Parts2[0]);
		assertEquals("/IdpBody/Pendenza/Insert/InfoPagamento/DettaglioPagamento/Allegato", ps1Parts2[1]);
		
		String[] ps1Parts3 = XPathUtils.sharedInstance.splitOnSegment(ps1, "Allegato");
		assertEquals(2, ps1Parts3.length);
		assertEquals("/IdpAllineamentoPendenzeEnte/IdpBody/Pendenza/Insert/InfoPagamento/DettaglioPagamento", ps1Parts3[0]);
		assertNull(ps1Parts3[1]);
		
		AssertThrows.shouldFail(RuntimeException.class, new Testable() {

			public void test() throws Throwable {
				XPathUtils.sharedInstance.splitOnSegment(ps1, "DontExist");
			}
			
		});
		
		
	}

}

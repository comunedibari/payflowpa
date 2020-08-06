/**
 * Created on 30/mar/2009
 */
package it.nch.streaming.test;

import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.WXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathStartsWith;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class XPathMapViewTest extends TestCase {
	
	XPathsMapBindings xpathMap = new XPathsMapBindings();
	XPathsParser xpParser = XPathsParser.instance;
	
	void put(String xpath, String value) {
		xpathMap.put(xpParser.parseXPath(xpath), value);
	}
	
		
	void init(WXPathMapScope map) {
		put( "/MSG:CBIPaymentRequestMsg/@xs:schemaLocation", "urn:CBI:xsd:CBIPaymentRequestMsg.00.03.05 CBIPaymentRequestMsg.00.03.05.xsd" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:CongrInfo", ""  );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:DiagInfo", "" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Receiver", "" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Sender", "" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:SrvInfo", "" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrTrt/HTRT:IdCBIRcvrf", "0000129Z" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrTrt/HTRT:IdCBISndrf", "0000649Q" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrTrt/HTRT:IdMsgTrt", "0000085Y0000124N0000649Q118323064001183230650000649Q118323066" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrTrt/HTRT:RtrnAddrl", "88002NCB13PR" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrTrt/HTRT:SrvNm", "DISP-PAG-SEPA" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrTrt/HTRT:XMLCrtDt", "2008-04-24T09:01:14" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:CongrInfo/HE2E:SrvBdyNb", "1" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:DiagInfo/HE2E:ChkDt", "2008-04-24T09:01:14" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:DiagInfo/HE2E:ChkSbj", "0000649Q" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:DiagInfo/HE2E:DiagVers", "1.0" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:DiagInfo/HE2E:UsrBnk", "0000085Y" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Receiver/HE2E:CBIRefrRecv", "0000655J" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Receiver/HE2E:IdCBIRecv", "0000129Z" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Receiver/HE2E:RecvTyp", "GPA" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Sender/HE2E:CBIRefrSend", "0000649Q" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Sender/HE2E:IdCBISend", "0000085Y" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Sender/HE2E:SendTyp", "STD" );
		
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Sender[2]/HE2E:CBIRefrSend", "7700649Q" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Sender[2]/HE2E:IdCBISend", "7700085Y" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Sender[2]/HE2E:SendTyp", "S77" );
		
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:SrvInfo/HE2E:IdE2EMsg", "0000085Y0000124N0000649Q11832306400118323065" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:SrvInfo/HE2E:SrvNm", "DISP-PAG-SEPA" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:SrvInfo/HE2E:XMLCrtDt", "2008-04-24T09:01:14" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr", "" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:CreDtTm", "2008-04-24T09:00:39.337+02:00" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:CtrlSum", "3" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:Grpg", "GRPD" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:InitgPty", "" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:MsgId", "9195191924042008090039337" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:NbOfTxs", "1" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:InitgPty/PMRQ:Id", "" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:InitgPty/PMRQ:Nm", "ISEL" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:InitgPty/PMRQ:Id/PMRQ:OrgId", "" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:InitgPty/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId", "" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:InitgPty/PMRQ:Id/PMRQ:OrgId/PMRQ:TaxIdNb", "GRNRNN65E30C794Q" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:InitgPty/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id", "0328237Q" );
		put( "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:InitgPty/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Issr", "ACBI" );
	}

	BaseXPathPosition p1 	= xpParser.parseXPath( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:DiagInfo/HE2E:DiagVers" );
	BaseXPathPosition p2 	= xpParser.parseXPath( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:DiagInfo/HE2E:UsrBnk" );
	BaseXPathPosition p3 	= xpParser.parseXPath( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Receiver/HE2E:CBIRefrRecv" );
	BaseXPathPosition p4 	= xpParser.parseXPath( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Receiver/HE2E:IdCBIRecv" );
	BaseXPathPosition p5 	= xpParser.parseXPath( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Receiver/HE2E:RecvTyp" );
	BaseXPathPosition p6 	= xpParser.parseXPath( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Sender/HE2E:CBIRefrSend" );
	BaseXPathPosition p7 	= xpParser.parseXPath( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Sender/HE2E:IdCBISend" );
	BaseXPathPosition p8 	= xpParser.parseXPath( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Sender/HE2E:SendTyp" );
	
	BaseXPathPosition p6a	= xpParser.parseXPath( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Sender[2]/HE2E:CBIRefrSend" );
	BaseXPathPosition p7a	= xpParser.parseXPath( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Sender[2]/HE2E:IdCBISend" );
	BaseXPathPosition p8a	= xpParser.parseXPath( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Sender[2]/HE2E:SendTyp" );
	
	BaseXPathPosition p9 	= xpParser.parseXPath( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:SrvInfo/HE2E:IdE2EMsg" );
	BaseXPathPosition p10 = xpParser.parseXPath( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:SrvInfo/HE2E:SrvNm" );
	BaseXPathPosition p11 = xpParser.parseXPath( "/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:SrvInfo/HE2E:XMLCrtDt" );
	BaseXPathPosition p12 = xpParser.parseXPath( "/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr" );

	public void test1() throws Exception {
		init(xpathMap);
		IXPathMapScope view = xpathMap.view(new XPathStartsWith(xpParser.parseXPathPosition("/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Receiver")));
		assertEquals(4, view.size());
		assertNull(  view.get(p1) );
		assertNull(  view.get(p2) );
		assertEquals("0000655J",  view.get(p3) );
		assertEquals("0000129Z",  view.get(p4) );
		assertEquals("GPA",  view.get(p5) );
		assertNull(view.get(p6));
		assertNull(view.get(p7));
		assertNull(view.get(p8));
		assertNull(view.get(p9));
		assertNull(view.get(p10));
		assertNull(view.get(p11));
		assertNull(view.get(p12));
	}
	
	public void test2() throws Exception {
		init(xpathMap);
		IXPathMapScope view = xpathMap.view(new XPathStartsWith(xpParser.parseXPathPosition("/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Sender")));
		assertEquals(4, view.size());
		assertNull(  view.get(p1) );
		assertNull(  view.get(p2) );
		assertNull(view.get(p3) );
		assertNull(view.get(p4) );
		assertNull(view.get(p5) );
		
		assertEquals("0000649Q", view.get(p6));
		assertEquals("0000085Y", view.get(p7));
		assertEquals("STD", view.get(p8));
		
		assertNull(view.get(p6a));
		assertNull(view.get(p7a));
		assertNull(view.get(p8a));
		
		assertNull(view.get(p9));
		assertNull(view.get(p10));
		assertNull(view.get(p11));
		assertNull(view.get(p12));
	}
	
	public void test3() throws Exception {
		init(xpathMap);
		IXPathMapScope view = xpathMap.view(new XPathStartsWith(xpParser.parseXPathPosition("/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv/HE2E:Sender[2]")));
		assertEquals(3, view.size());
		assertNull(  view.get(p1) );
		assertNull(  view.get(p2) );
		assertNull(view.get(p3) );
		assertNull(view.get(p4) );
		assertNull(view.get(p5) );
		
		assertNull(view.get(p6));
		assertNull(view.get(p7));
		assertNull(view.get(p8));
		
		assertEquals("7700649Q", view.get(p6a));
		assertEquals("7700085Y", view.get(p7a));
		assertEquals("S77", view.get(p8a));
		
		assertNull(view.get(p9));
		assertNull(view.get(p10));
		assertNull(view.get(p11));
		assertNull(view.get(p12));
	}
}

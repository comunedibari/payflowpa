/**
 * 
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;

/**
 * @author Admin
 *
 */
public class IdPDocumentXPaths {
	
	public static final String PENDENZA_XPATH_STRING = "/IdpAllineamentoPendenze/IdpBody/Pendenza";
	public static final String PENDENZA_ENTE_XPATH_STRING = "/IdpAllineamentoPendenze/IdpBody/Pendenza/IdPendenza";
	
	public static final XPathPosition e2eMsgId		= XPathsParser.instance.parseXPathPosition( 
			"/IdpAllineamentoPendenze/h:IdpHeader/h:E2E/h:E2EMsgId" );
	public static final XPathPosition e2eSrvcNm        = XPathsParser.instance.parseXPathPosition(
			"/IdpAllineamentoPendenze/h:IdpHeader/h:E2E/h:E2ESrvcNm");
	public static final XPathPosition xmlCrtDt         = XPathsParser.instance.parseXPathPosition(
			"/IdpAllineamentoPendenze/h:IdpHeader/h:E2E/h:XMLCrtDt");
	public static final XPathPosition senderE2ESndrId = XPathsParser.instance.parseXPathPosition(
			"/IdpAllineamentoPendenze/h:IdpHeader/h:E2E/h:Sender/h:E2ESndrId");
	public static final XPathPosition senderE2ESndrSys = XPathsParser.instance.parseXPathPosition(
			"/IdpAllineamentoPendenze/h:IdpHeader/h:E2E/h:Sender/h:E2ESndrSys");
	public static final BaseXPathPosition coVersione = XPathsParser.instance.parseXPath(
		"/IdpAllineamentoPendenze/@Versione");
	
	public static final XPathPosition senderTRTSndrId = XPathsParser.instance.parseXPathPosition(
			"/IdpAllineamentoPendenze/h:IdpHeader/h:TRT/h:Sender/h:SenderId");
	public static final XPathPosition senderTRTSndrSys = XPathsParser.instance.parseXPathPosition(
			"/IdpAllineamentoPendenze/h:IdpHeader/h:TRT/h:Sender/h:SenderSys");

	public static final XPathPosition pendenza = XPathsParser.instance.parseXPathPosition(
			PENDENZA_XPATH_STRING);
	public static final XPathPosition pendenzaEnte = XPathsParser.instance.parseXPathPosition(
			PENDENZA_ENTE_XPATH_STRING);	
	public static final XPathPosition idpHeader = XPathsParser.instance.parseXPathPosition(
			"/IdpAllineamentoPendenze/h:IdpHeader");
	public static final XPathPosition idpHeaderE2E = XPathsParser.instance.parseXPathPosition(
			"/IdpAllineamentoPendenze/h:IdpHeader/h:E2E");
	public static final BaseXPathPosition pendenzaTipoOperazione = XPathsParser.instance.parseXPath(
			"/IdpAllineamentoPendenze/IdpBody/Pendenza/@TipoOperazione");

	public static final XPathPosition idpOtf = XPathsParser.instance.parseXPathPosition(
			"/IdpAllineamentoPendenze/IdpOTF");	
	public static final BaseXPathPosition urlBack = XPathsParser.instance.parseXPath(
			"/IdpAllineamentoPendenze/IdpOTF/URL_BACK");
	public static final BaseXPathPosition urlCancel = XPathsParser.instance.parseXPath(
			"/IdpAllineamentoPendenze/h:IdpOTF/URL_CANCEL");	
	public static final BaseXPathPosition offlineMethods = XPathsParser.instance.parseXPath(
			"/IdpAllineamentoPendenze/h:IdpOTF/OFFLINE_PAYMENT_METHODS");		
	
}

package it.nch.eb.xsqlcmd.dbtrpos.record;

//import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.flatmodel.objectconverters.ObjectConverters;
import it.nch.eb.flatx.xmlrecord.XmlBaseConverters;

/**
 * generated 
 */
public class HeaderRecord extends it.nch.eb.flatx.flatmodel.XmlRecord  
	implements ObjectConverters, XmlBaseConverters, BaseConverters 
 , it.nch.eb.xsqlcmd.dbtrpos.record.PendenzeConverters, it.nch.eb.flatx.flatmodel.conversioninfo.ConstXPathToObjectConversionInfos   {

	private static final long	serialVersionUID	= -666L;
		
	public HeaderRecord () {
		this("/IdpAllineamentoPendenze/h:IdpHeader/h:E2E");
	}
	
	public HeaderRecord (String xpath) {
		super(xpath);
	}
	
	public final IXPathToObjectConversionInfo serviceName		 = builder.create("h:E2ESrvcNm")	;
	public final IXPathToObjectConversionInfo senderE2EMsgId     = builder.create("h:E2EMsgId")     ;
	public final IXPathToObjectConversionInfo senderXMLCrtDt     = builder.create(dateAsLongString,toSqlTimestamp, "h:XMLCrtDt")     ;
	public final IXPathToObjectConversionInfo senderId           = builder.create("h:Sender/h:E2ESndrId")      ;
	public final IXPathToObjectConversionInfo senderSys          = builder.create("h:Sender/h:E2ESndrSys")     ;
	public final IXPathToObjectConversionInfo receiverId         = builder.create("h:Receiver/h:E2ERcvrId")  ;
	public final IXPathToObjectConversionInfo receiverSys        = builder.create("h:Receiver/h:E2ERcvrSys") ;
	public final IXPathToObjectConversionInfo receiverE2EMsgId   = builder.create("h:Receiver/h:E2EMsgId")   ;
	public final IXPathToObjectConversionInfo receiverXMLCrtDt   = builder.create(dateAsLongString,toSqlTimestamp, "h:Reference/h:XMLCrtDt")   ;
	  
	
}
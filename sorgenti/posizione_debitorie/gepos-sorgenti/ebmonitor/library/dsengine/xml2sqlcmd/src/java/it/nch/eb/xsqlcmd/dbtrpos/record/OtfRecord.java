package it.nch.eb.xsqlcmd.dbtrpos.record;

//import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.flatmodel.objectconverters.ObjectConverters;
import it.nch.eb.flatx.xmlrecord.XmlBaseConverters;

/**
 * generated 
 */
public class OtfRecord extends it.nch.eb.flatx.flatmodel.XmlRecord  
	implements ObjectConverters, XmlBaseConverters, BaseConverters 
 , it.nch.eb.xsqlcmd.dbtrpos.record.PendenzeConverters, it.nch.eb.flatx.flatmodel.conversioninfo.ConstXPathToObjectConversionInfos   {

	private static final long	serialVersionUID	= -666L;
		
	public OtfRecord () {
		this("/IdpAllineamentoPendenze/IdpOTF");
	}
	
	public OtfRecord (String xpath) {
		super(xpath);
	}
	
	public final IXPathToObjectConversionInfo UrlBack		 = builder.create("URL_BACK")	;
	public final IXPathToObjectConversionInfo UrlCancel     = builder.create("URL_CANCEL");
	public final IXPathToObjectConversionInfo offlineMethod     = builder.create("OFFLINE_PAYMENT_METHODS");	  
	
}
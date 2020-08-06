package it.nch.eb.xsqlcmd.dbtrpos.gen.record;

//import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.flatmodel.objectconverters.ObjectConverters;
import it.nch.eb.flatx.xmlrecord.XmlBaseConverters;

/**
 * generated 
 */
public class FlussiRecord extends it.nch.eb.xsqlcmd.record.ChildRelativeXmlRecord  
	implements ObjectConverters, XmlBaseConverters, BaseConverters 
 , it.nch.eb.xsqlcmd.dbtrpos.record.PendenzeConverters, it.nch.eb.flatx.flatmodel.conversioninfo.ConstXPathToObjectConversionInfos   {

	private static final long	serialVersionUID	= -666L;
		
	public FlussiRecord () {
		this("/IdpAllineamentoPendenze/IdpBody/Pendenza/Destinatari/Destinatario", "Insert");
	}
	
	public FlussiRecord (String xpath) {
		this("/IdpAllineamentoPendenze/IdpBody/Pendenza/Destinatari/Destinatario", xpath);
	}
	
	public FlussiRecord (String base, String chldXPath) {
		super(base, chldXPath);
	}

	  public final IXPathToObjectConversionInfo dataCreazione = builder.create( dateYYY_MM_DD_AsLongString,toSqlDate,"../../../../h:IdpHeader/h:E2E/h:XMLCrtDt" );
	    public final IXPathToObjectConversionInfo oraCreazione = builder.create( dateAsLongString,toSqlTimestamp,"../../../../h:IdpHeader/h:E2E/h:XMLCrtDt" );
	    public final IXPathToObjectConversionInfo utenteCreatore = builder.create( "Id" );
	    public final IXPathToObjectConversionInfo totImportiPos = builder.create( concat("/../..", getChildPath(), "/ImportoTotale") );
	    public final IXPathToObjectConversionInfo divisa = builder.create( concat("/../..", getChildPath(), "/Divisa") );
	    public final IXPathToObjectConversionInfo tipoServizio = builder.create( "../../../../h:IdpHeader/h:E2E/h:SrvcNm" );
	    public final IXPathToObjectConversionInfo prodotto = builder.create( new S("PDB") );
	    public final IXPathToObjectConversionInfo msgid = builder.create( "../../../../h:IdpHeader/h:E2E/h:E2EMsgId" );
	    public final IXPathToObjectConversionInfo idMittente = builder.create( "../../../../h:IdpHeader/h:E2E/h:Sender/h:E2ESndrId" );
	    public final IXPathToObjectConversionInfo idSystem = builder.create( "../../../../h:IdpHeader/h:E2E/h:Sender/h:E2ESndrSys" );
	    public final IXPathToObjectConversionInfo idMittenteTrt = builder.create( "../../../../h:IdpHeader/h:TRT/h:Sender/h:SenderId" );
	    public final IXPathToObjectConversionInfo idSystemTrt = builder.create( "../../../../h:IdpHeader/h:TRT/h:Sender/h:SenderSys" );
	    public final IXPathToObjectConversionInfo coVersione = builder.create( "../../../../@Versione" );
	  
	
}
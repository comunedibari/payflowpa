package it.nch.eb.xsqlcmd.dbtrpos.gen.record;

//import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.flatmodel.objectconverters.ObjectConverters;
import it.nch.eb.flatx.xmlrecord.XmlBaseConverters;

/**
 * generated 
 */
public class PendenzeRecord extends it.nch.eb.xsqlcmd.record.ChildRelativeXmlRecord  
	implements ObjectConverters, XmlBaseConverters, BaseConverters 
 , it.nch.eb.xsqlcmd.dbtrpos.record.PendenzeConverters, it.nch.eb.flatx.flatmodel.conversioninfo.ConstXPathToObjectConversionInfos   {

	private static final long	serialVersionUID	= -666L;
		
	public PendenzeRecord () {
		this("/IdpAllineamentoPendenze/IdpBody/Pendenza", "Insert");
	}
	
	public PendenzeRecord (String xpath) {
		this("/IdpAllineamentoPendenze/IdpBody/Pendenza", xpath);
	}
	
	public PendenzeRecord (String base, String chldXPath) {
		super(base, chldXPath);
	}

  	public final IXPathToObjectConversionInfo cdTrbEnte = builder.create( "@TipoPendenza", true );
    public final IXPathToObjectConversionInfo idEnte = builder.create( "../../h:IdpHeader/h:E2E/h:Sender/h:E2ESndrId" );
    public final IXPathToObjectConversionInfo idSystem = builder.create( "../../h:IdpHeader/h:E2E/h:Sender/h:E2ESndrSys" );
    
    public final IXPathToObjectConversionInfo idMittenteTrt = builder.create( "../../h:IdpHeader/h:TRT/h:Sender/h:SenderId" );
    public final IXPathToObjectConversionInfo idSystemTrt = builder.create( "../../h:IdpHeader/h:TRT/h:Sender/h:SenderSys" );   
    
    public final IXPathToObjectConversionInfo idMittente = builder.create( "Mittente/Id" );
    public final IXPathToObjectConversionInfo deMittente = builder.create( "Mittente/Descrizione" );
    public final IXPathToObjectConversionInfo idPendenzaEnte = builder.create( "IdPendenza" );
    
    public final IXPathToObjectConversionInfo cartellaDiPagamento = builder.create(stringToBooleanInteger, "CartellaDiPagamento" );
//    public final IXPathToObjectConversionInfo cartellaDiPagamento = builder.create( concat(getChildPath(), "/CartellaDiPagamento") );
    
    public final IXPathToObjectConversionInfo note = builder.create( "Note" );
    public final IXPathToObjectConversionInfo deCausale = builder.create( concat(getChildPath(), "/DescrizioneCausale") );
    public final IXPathToObjectConversionInfo coRiscossore = builder.create( concat(getChildPath(), "/Riscossore/Id") );
    public final IXPathToObjectConversionInfo riferimento = builder.create( concat(getChildPath(), "/Riscossore/Riferimento") );
    public final IXPathToObjectConversionInfo tsCreazioneEnte = builder.create( dateAsLongString,toSqlTimestamp,concat(getChildPath(), "/DataCreazione") );
    public final IXPathToObjectConversionInfo tsEmissioneEnte = builder.create( dateAsLongString,toSqlTimestamp,concat(getChildPath(), "/DataEmissione") );
    public final IXPathToObjectConversionInfo tsPrescrizione = builder.create( dateAsLongString,toSqlTimestamp,concat(getChildPath(), "/DataPrescrizione") );
    public final IXPathToObjectConversionInfo annoRiferimento = builder.create( toYear,concat(getChildPath(), "/AnnoRiferimento") );
    public final IXPathToObjectConversionInfo tsModificaEnte = builder.create( dateAsLongString,toSqlTimestamp,concat(getChildPath(), "/DataModificaEnte") );
    public final IXPathToObjectConversionInfo stPendenza = builder.create( statoPendenzaConverter,concat(getChildPath(), "/Stato") );
    public final IXPathToObjectConversionInfo imTotale = builder.create( toBigDecimal,concat(getChildPath(), "/ImportoTotale") );
    public final IXPathToObjectConversionInfo dvRiferimento = builder.create( concat(getChildPath(), "/Divisa") );
    public final IXPathToObjectConversionInfo flModPagamento = builder.create( modalitaPagamentoConverter,concat(getChildPath(), "/InfoPagamento/@TipoPagamento") );
    public final IXPathToObjectConversionInfo prVersione = builder.create( new I(1) );
	  
	
}
package it.nch.eb.xsqlcmd.dbtrpos.gen.record;

//import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.flatmodel.objectconverters.ObjectConverters;
import it.nch.eb.flatx.xmlrecord.XmlBaseConverters;

/**
 * generated 
 */
public class AllegatoRecord extends it.nch.eb.flatx.flatmodel.XmlRecord  
	implements ObjectConverters, XmlBaseConverters, BaseConverters 
 , it.nch.eb.xsqlcmd.dbtrpos.record.PendenzeConverters, it.nch.eb.flatx.flatmodel.conversioninfo.ConstXPathToObjectConversionInfos   {

	private static final long	serialVersionUID	= -666L;
		
	public AllegatoRecord () {
		this("/IdpAllineamentoPendenze/IdpBody/Pendenza/Insert/Allegato");
	}
	
	public AllegatoRecord (String xpath) {
		super(xpath);
	}
	

	  public final IXPathToObjectConversionInfo tiAllegato = builder.create( tipoAllegatoConverter,"@Tipo" );
	    public final IXPathToObjectConversionInfo titolo = builder.create( "i:Titolo" );
	    public final IXPathToObjectConversionInfo tiCodificaBody = builder.create( "i:Codifica" );
	    public final IXPathToObjectConversionInfo datiBody = builder.create( toBytes,"i:Contenuto" );
	    public final IXPathToObjectConversionInfo idAntifalsific = builder.create( "i:IdAntifalsificazione" );
	  
	
}
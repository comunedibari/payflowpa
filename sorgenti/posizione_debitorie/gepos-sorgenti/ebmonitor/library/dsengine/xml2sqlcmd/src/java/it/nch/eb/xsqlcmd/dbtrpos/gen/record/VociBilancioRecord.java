package it.nch.eb.xsqlcmd.dbtrpos.gen.record;

//import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.flatmodel.objectconverters.ObjectConverters;
import it.nch.eb.flatx.xmlrecord.XmlBaseConverters;

/**
 * generated 
 */
public class VociBilancioRecord extends it.nch.eb.flatx.flatmodel.XmlRecord  
	implements ObjectConverters, XmlBaseConverters, BaseConverters 
 , it.nch.eb.xsqlcmd.dbtrpos.record.PendenzeConverters, it.nch.eb.flatx.flatmodel.conversioninfo.ConstXPathToObjectConversionInfos   {

	private static final long	serialVersionUID	= -666L;
		
	public VociBilancioRecord () {
		this("/IdpAllineamentoPendenze/IdpBody/Pendenza/Insert/InfoPagamento/DettaglioPagamento/DettaglioImporto/Voce");
	}
	
	public VociBilancioRecord (String xpath) {
		super(xpath);
	}
	

	  public final IXPathToObjectConversionInfo tiVoce = builder.create( "@Tipo" );
	    public final IXPathToObjectConversionInfo coVoce = builder.create( "Codice" );
	    public final IXPathToObjectConversionInfo deVoce = builder.create( "Descrizione" );
	    public final IXPathToObjectConversionInfo imVoce = builder.create( toBigDecimal,"Importo" );
	    public final IXPathToObjectConversionInfo coCapbilancio = builder.create( "CapitoloBilancio" );
	    public final IXPathToObjectConversionInfo coAccertamento = builder.create( "Accertamento" );
	  
	
}
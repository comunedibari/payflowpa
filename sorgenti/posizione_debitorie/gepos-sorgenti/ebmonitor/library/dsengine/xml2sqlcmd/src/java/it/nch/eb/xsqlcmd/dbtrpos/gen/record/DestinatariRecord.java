package it.nch.eb.xsqlcmd.dbtrpos.gen.record;

//import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.flatmodel.objectconverters.ObjectConverters;
import it.nch.eb.flatx.xmlrecord.XmlBaseConverters;

/**
 * generated 
 */
public class DestinatariRecord extends it.nch.eb.xsqlcmd.record.ChildRelativeXmlRecord  
	implements ObjectConverters, XmlBaseConverters, BaseConverters 
 , it.nch.eb.xsqlcmd.dbtrpos.record.PendenzeConverters, it.nch.eb.flatx.flatmodel.conversioninfo.ConstXPathToObjectConversionInfos   {

	private static final long	serialVersionUID	= -666L;
		
	public DestinatariRecord () {
		this("/IdpAllineamentoPendenze/IdpBody/Pendenza/Destinatari/Destinatario", "Insert");
	}
	
	public DestinatariRecord (String xpath) {
		this("/IdpAllineamentoPendenze/IdpBody/Pendenza/Destinatari/Destinatario", xpath);
	}
	
	public DestinatariRecord (String base, String chldXPath) {
		super(base, chldXPath);
	}

	    public final IXPathToObjectConversionInfo tiDestinatario = builder.create( tipoDestinatarioConverter,"@Tipo" );
	    public final IXPathToObjectConversionInfo coDestinatario = builder.create( "Id" );
	    public final IXPathToObjectConversionInfo deDestinatario = builder.create( "Descrizione" );
	    public final IXPathToObjectConversionInfo stRiga = builder.create( new S("V") );
	    public final IXPathToObjectConversionInfo prVersione = builder.create( new I(1) );

	    public final IXPathToObjectConversionInfo tipoSoggettoDestinatario = builder.create( "DatiDestinatario/TipoSoggetto");
	    public final IXPathToObjectConversionInfo anagraficaDestinatario = builder.create( "DatiDestinatario/Anagrafica");  
	    public final IXPathToObjectConversionInfo emailDestinatario = builder.create( "DatiDestinatario/EMail" );
	    public final IXPathToObjectConversionInfo indirizzoDestinatario = builder.create( "DatiDestinatario/Indirizzo" );
	    public final IXPathToObjectConversionInfo numCivicoDestinatario = builder.create( "DatiDestinatario/NumeroCivico" );
	    public final IXPathToObjectConversionInfo capDestinatario = builder.create( "DatiDestinatario/Cap" );
	    public final IXPathToObjectConversionInfo localitaDestinatario = builder.create( "DatiDestinatario/Localita" );
	    public final IXPathToObjectConversionInfo provinciaDestinatario = builder.create( "DatiDestinatario/Provincia" );
	    public final IXPathToObjectConversionInfo nazioneDestinatario = builder.create( "DatiDestinatario/Nazione" );

	    //Luogo e data di nascita del destinatario per il momento non sono gestiti
	    
	  
	
}
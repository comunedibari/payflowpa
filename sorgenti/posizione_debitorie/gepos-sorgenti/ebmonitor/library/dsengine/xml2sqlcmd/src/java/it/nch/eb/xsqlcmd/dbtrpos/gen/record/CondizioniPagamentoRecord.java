package it.nch.eb.xsqlcmd.dbtrpos.gen.record;

//import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.flatmodel.objectconverters.ObjectConverters;
import it.nch.eb.flatx.xmlrecord.XmlBaseConverters;

/**
 * generated 
 */
public class CondizioniPagamentoRecord extends it.nch.eb.flatx.flatmodel.XmlRecord  
	implements ObjectConverters, XmlBaseConverters, BaseConverters 
 , it.nch.eb.xsqlcmd.dbtrpos.record.PendenzeConverters, it.nch.eb.flatx.flatmodel.conversioninfo.ConstXPathToObjectConversionInfos   {

	private static final long	serialVersionUID	= -666L;
		
	public CondizioniPagamentoRecord () {
		this("/IdpAllineamentoPendenze/IdpBody/Pendenza/Insert/InfoPagamento/DettaglioPagamento/");
	}
	
	public CondizioniPagamentoRecord (String xpath) {
		super(xpath);
	}
	

  	public final IXPathToObjectConversionInfo tiPagamento = builder.create( modalitaPagamentoConverter,"../@TipoPagamento" );
    public final IXPathToObjectConversionInfo idPagamento = builder.create( "IdPagamento" );
    public final IXPathToObjectConversionInfo tiCip = builder.create( "CIP/@Tipo" );
    public final IXPathToObjectConversionInfo coCip = builder.create( "CIP/i:Codice" );
    public final IXPathToObjectConversionInfo dtScadenza = builder.create( dateYYY_MM_DD_AsLongString,toSqlDate,"DataScadenza" );
    public final IXPathToObjectConversionInfo dtInizioValidita = builder.create( dateYYY_MM_DD_AsLongString,toSqlDate,"DataInizioValidita" );
    public final IXPathToObjectConversionInfo dtFineValidita = builder.create( dateYYY_MM_DD_AsLongString,toSqlDate,"DataFineValidita" );
    public final IXPathToObjectConversionInfo imTotale = builder.create( toBigDecimal,"Importo" );
    public final IXPathToObjectConversionInfo stPagamento = builder.create( statoPagamentoConverter,"Stato" );
    
    	public final IXPathToObjectConversionInfo dtPagamento = builder.create( dateYYY_MM_DD_AsLongString,toSqlDate,"DettaglioTransazione/DataPagamento" );
    	public final IXPathToObjectConversionInfo deCanalepag = builder.create( "DettaglioTransazione/CanalePagamento" );
	    public final IXPathToObjectConversionInfo imPagamento = builder.create( toBigDecimal,"DettaglioTransazione/ImportoPagamento" );
	    public final IXPathToObjectConversionInfo deNotePagamento = builder.create( "DettaglioTransazione/NotePagamento" );
	    public final IXPathToObjectConversionInfo mezzoPagamento = builder.create( "DettaglioTransazione/MezzoPagamento" );
	    
	    
    public final IXPathToObjectConversionInfo stRiga = builder.create( "DettaglioTransazione/MezzoPagamento" );
    
    public final IXPathToObjectConversionInfo prVersione = builder.create( toInt,"DettaglioTransazione/IdPagamentoIdp" );
    public final IXPathToObjectConversionInfo opInserimento = builder.create( "DettaglioTransazione/IdTransazione" );
    
    public final IXPathToObjectConversionInfo causalePagamento = builder.create( "CausalePagamento" );
    public final IXPathToObjectConversionInfo codiceIBAN = builder.create( "AccreditoPagamento/i:CodiceIBAN" );
    public final IXPathToObjectConversionInfo beneficiario = builder.create( "AccreditoPagamento/i:Beneficiario" );
	public final IXPathToObjectConversionInfo tiOperazioneUpdate = builder.create( "TipoOperazione" );
}
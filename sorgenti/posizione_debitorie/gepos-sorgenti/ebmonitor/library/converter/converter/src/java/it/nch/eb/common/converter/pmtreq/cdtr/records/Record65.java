/**
 * Created on 15/gen/08
 */
package it.nch.eb.common.converter.pmtreq.cdtr.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.conversioninfo.GetElementText;
import it.nch.eb.flatx.flatmodel.conversioninfo.GetTextValue;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;


/**
 * @author gdefacci
 */
public class Record65 extends ConversionRecord implements BaseConverters, ConversionsConsts{

	private static final long serialVersionUID = 1L;
	private static final GetElementText	GET_CCY_ATTRIBUTE	= new GetElementText("@Ccy");
	private static final GetElementText	GET_ELEMENT_TEXT	= new GetElementText(".");

	public Record65() {
		super("/CBICdtrPmtStatusReportMsg/CBIBdyCdtrPmtStatusReport/BODY:CBIEnvelCdtrPmtStatusReport/BODY:CBICdtrPmtStatusReport/CPSR:TxInfAndSts/CPSR:OrgnlTxRef/CPSR:RmtInf/CPSR:Strd/CPSR:RfrdDocAmt","65");
	}
	
	public final Converter	fiiller				= createFixedValue(" ");
	public final Converter	tipoRec				= createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter	recCount			= createGetBindingValue(numb7, REC_COUNT, true);
	
	public final Converter  tipoImporto = create( fill3, or()
		.or("CPSR:DuePyblAmt", new GetTextValue("TOT")) 
		.or("CPSR:DscntApldAmt", new GetTextValue("SCT")) 
		.or("CPSR:RmtdAmt", new GetTextValue("PAG")) 
		.or("CPSR:CdtNoteAmt", new GetTextValue("NOT")) 
		.or("CPSR:TaxAmt", new GetTextValue("TAX")) 
	);
	
	/**
	 * FIXME
	 * GET_ELEMENT_TEXT deve diventare
	 * ReturnXPathElementToStringFunction getElementText(String path) = new ReturnXPathElementToStringFunction( conactPaths(path, "@Ccy"))
	 */
	public final Converter  importo = create( fd_number23_5, or()
		.or("CPSR:DuePyblAmt", GET_ELEMENT_TEXT) 
		.or("CPSR:DscntApldAmt", GET_ELEMENT_TEXT) 
		.or("CPSR:RmtdAmt", GET_ELEMENT_TEXT) 
		.or("CPSR:CdtNoteAmt", GET_ELEMENT_TEXT) 
		.or("CPSR:TaxAmt", GET_ELEMENT_TEXT) 
	);
	
	/**
	 * FIXME
	 * GET_ELEMENT_TEXT deve diventare
	 * ReturnXPathElementToStringFunction getElementText(String path) = new ReturnXPathElementToStringFunction( conactPaths(path, "@Ccy"))
	 */
	public final Converter  divisa = create( fill3, or()
		.or("CPSR:DuePyblAmt", GET_CCY_ATTRIBUTE) 
		.or("CPSR:DscntApldAmt", GET_CCY_ATTRIBUTE) 
		.or("CPSR:RmtdAmt", GET_CCY_ATTRIBUTE) 
		.or("CPSR:CdtNoteAmt", GET_CCY_ATTRIBUTE) 
		.or("CPSR:TaxAmt", GET_CCY_ATTRIBUTE) 
	);

	
}

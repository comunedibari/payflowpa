package it.nch.eb.common.converter.pmtreq.likepc.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.SubstringExtractorConverter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class Record16 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6264328015367997718L;
	public Record16() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf", "16");
	}
	
	public final Converter filler = createFixedValue(" ");
	public final Converter tipoRecord = createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter instrId = createXPath(numb7, "PMRQ:PmtId/PMRQ:InstrId", true);
	
	public final Converter iban = createXPath(new SubstringExtractorConverter(0,2), "../PMRQ:DbtrAcct/PMRQ:Id/PMRQ:IBAN", true);
	public final Converter filler1 = createXPath(new SubstringExtractorConverter(2,4), "../PMRQ:DbtrAcct/PMRQ:Id/PMRQ:IBAN", true);	
	public final Converter iban1 = createXPath(new SubstringExtractorConverter(4,5), "../PMRQ:DbtrAcct/PMRQ:Id/PMRQ:IBAN", true);
	public final Converter filler2 = createXPath(new SubstringExtractorConverter(5,10), "../PMRQ:DbtrAcct/PMRQ:Id/PMRQ:IBAN", true);	
	public final Converter iban2 = createXPath(new SubstringExtractorConverter(10,15), "../PMRQ:DbtrAcct/PMRQ:Id/PMRQ:IBAN", true);
	public final Converter iban3 = createXPath(new SubstringExtractorConverter(15,27), "../PMRQ:DbtrAcct/PMRQ:Id/PMRQ:IBAN", true);
	public final Converter blank = createGetBindingValue(fill7, BLANK);
	public final Converter endToEndId = createXPath(fill35, "PMRQ:PmtId/PMRQ:EndToEndId", true);
	public final Converter blank1 = createGetBindingValue(fill41, BLANK);
	
}
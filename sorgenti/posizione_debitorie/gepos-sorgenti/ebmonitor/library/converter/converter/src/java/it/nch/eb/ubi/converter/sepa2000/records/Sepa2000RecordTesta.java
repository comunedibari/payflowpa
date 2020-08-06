/**
 * Created on 09/gen/08
 */
package it.nch.eb.ubi.converter.sepa2000.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.common.conversioninfo.DecodeCUCSiaTimestampIDGenerator;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.SubstringExtractorConverter;


/**
 * @author gdefacci
 */
public class Sepa2000RecordTesta extends ConversionRecord {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5235709886932109003L;
	public Sepa2000RecordTesta() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest", "XM");
	}

	public final Converter filler = createFixedValue(fill1, " ");
	public final Converter tipoRecord = createGetBindingValue(fill2, "tipoRecord");
	public final Converter sia = createCUCDecoder(fill5, "BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:InitgPty/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id");
	
	public final Converter abiRicevente = createXPath(new SubstringExtractorConverter(5,10, fill5), "BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:DbtrAcct/PMRQ:Id/PMRQ:IBAN");
	public final Converter creDtTm = createXPath(xsDateTo_ddMMyy, "BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:CreDtTm");
	public final Converter uid = create(numb20, new DecodeCUCSiaTimestampIDGenerator("BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:InitgPty/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id"));
	public final Converter blank1 = createFixedValue(fill6, " ");
	
	public final Converter msgId = createXPath(fill59, "BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:MsgId");
	public final Converter blank2 = createFixedValue(fill1896, " ");
	
	
	
	
}
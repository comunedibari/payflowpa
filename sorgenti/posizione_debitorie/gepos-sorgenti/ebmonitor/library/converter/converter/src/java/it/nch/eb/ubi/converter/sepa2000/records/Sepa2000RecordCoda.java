/**
 * Created on 10/gen/08
 */
package it.nch.eb.ubi.converter.sepa2000.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.SubstringExtractorConverter;


/**
 * @author gdefacci
 */
public class Sepa2000RecordCoda extends ConversionRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5304279147140776886L;
	public Sepa2000RecordCoda() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest", "EF");
	}
	
	public final Converter filler = createFixedValue(" ");
	public final Converter tipoRecord = createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter sia = createCUCDecoder(fill5, "BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:InitgPty/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id");
	public final Converter ricevente = createXPath(new SubstringExtractorConverter(5,10), "BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:DbtrAcct/PMRQ:Id/PMRQ:IBAN");
	public final Converter dataFlusso = createXPath(xsDateTo_ddMMyy, "BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:CreDtTm");
	public final Converter blank1 = createFixedValue(fill26, " ");
	public final Converter numeroDisposizioni = createXPath(fill5, "BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:NbOfTxs");
	public final Converter blank2 = createFixedValue(fill15, " ");
	public final Converter totaleImporti  = createXPath(fd_number20_2, "BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:CtrlSum");
	public final Converter blank3 = createFixedValue(fill1915, " ");
	

}

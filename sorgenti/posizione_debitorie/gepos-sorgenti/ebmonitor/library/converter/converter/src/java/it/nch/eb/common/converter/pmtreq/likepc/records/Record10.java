package it.nch.eb.common.converter.pmtreq.likepc.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.SubstringExtractorConverter;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathsMapBindinsManagerStringFunction;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;

/**
 * generated 
 * customized by @author gdefacci
 */
public class Record10 extends ConversionRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3641749449640214625L;
	public Record10() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf", "10");
	}
	
	public final Converter filler = createFixedValue(" ");
	public final Converter tipoRecord = createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter instrId = createXPath(numb7, "PMRQ:PmtId/PMRQ:InstrId", true);
	public final Converter blank = createGetBindingValue(fill6, BLANK);
	public final Converter reqdExctnDt = createXPath(dateTo_ddMMyy, "../../PMRQ:PmtInf/PMRQ:ReqdExctnDt", true);
	public final Converter instrForDbtrAgt = createXPath(dateTo_ddMMyy, "PMRQ:PmtId/PMRQ:InstrForDbtrAgt", true);
	public final Converter prtry = createXPath(fill5, "PMRQ:Purp/PMRQ:Prtry", true);
	public final Converter instdAmt = createXPath(fd_number13_2, "PMRQ:Amt/PMRQ:InstdAmt", true);
		public final Converter filler1 = createFixedValue(fill1, "+");	
	public final Converter prtry1 = createXPath(fill5, 
			"../../PMRQ:PmtInf/PMRQ:DbtrAgt/PMRQ:FinInstnId/PMRQ:ClrSysMmbId/PMRQ:Prtry", true);
	
	public final Converter iban = createXPath(new SubstringExtractorConverter(10,15), 
			"../../PMRQ:PmtInf/PMRQ:DbtrAcct/PMRQ:Id/PMRQ:IBAN", true);
	public final Converter iban1 = createXPath(new SubstringExtractorConverter(15,27), 
			"../../PMRQ:PmtInf/PMRQ:DbtrAcct/PMRQ:Id/PMRQ:IBAN", true);
	
	public final Converter or = create( numb5, or()
			.or("PMRQ:CdtrAcct/PMRQ:Id/PMRQ:IBAN", new SubstringExtractorConverter(6,10) )
			.or("PMRQ:CdtrAcct/PMRQ:Id/PMRQ:BBAN", new SubstringExtractorConverter(2,6) )
			);
	
	public final Converter or1 = create( numb5, or()
			.or("PMRQ:CdtrAcct/PMRQ:Id/PMRQ:IBAN", new SubstringExtractorConverter(10,15) )
			.or("PMRQ:CdtrAcct/PMRQ:Id/PMRQ:BBAN", new SubstringExtractorConverter(6,11) )
			);
	
	public final Converter or2 = create( fill12, or()
			.or("PMRQ:CdtrAcct/PMRQ:Id/PMRQ:IBAN", new SubstringExtractorConverter(15,27) )
			.or("PMRQ:CdtrAcct/PMRQ:Id/PMRQ:BBAN", new SubstringExtractorConverter(11,23) )
			);
	
	public final Converter id =  createCUCDecoder(fill5, "../../PMRQ:PmtInf/PMRQ:Dbtr/PMRQ:Id/PMRQ:OrgId/PMRQ:Prtry/PMRQ:Id", true);
	public final Converter blank1 = createGetBindingValue(fill1, BLANK);
	public final Converter blank2 = createGetBindingValue(fill16, BLANK);
	public final Converter pmtMtd = create(fill1, new XPathsMapBindinsManagerStringFunction() {

		public String getValue(XPathPosition pos, XPathsMapBindings elemsMap, IBindingManager bindingManager) {
			String value = getSuffixValueOrElse("../../PMRQ:PmtInf/PMRQ:PmtMtd", "", pos, elemsMap);
			String chqTp = getSuffixValueOrElse("../PMRQ:ChqInstr/PMRQ:ChqTp", "", pos, elemsMap);
			String prtry = getSuffixValueOrElse("../PMRQ:PmtTpInf/PMRQ:SvcLvl/PMRQ:Prtry", "", pos, elemsMap);
			if ("TRF".equals(value) || "TRA".equals(value) ) return "1";
			if ("CHK".equals(value) || "CCCH".equals(chqTp) ) return "4";
			if ("CHK".equals(value) || "BCHQ".equals(chqTp) ) {
				if (prtry.startsWith("NT")) return "3";
				return "2";
			}
			throw new IllegalStateException();
		}
		
	});
	public final Converter blank3 = createGetBindingValue(fill5, BLANK);
		public final Converter filler2 = createFixedValue(fill1, "E");	

}
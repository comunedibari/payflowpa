/**
 * Created on 14/gen/08
 */
package it.nch.eb.common.converter.pmtreq.records;

import it.nch.eb.common.converter.ConversionRecordWithSia;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.conversioninfo.ExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathConversionInfo;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * @author gdefacci
 */
public class Record00 extends ConversionRecordWithSia implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 4996267093836227306L;

	public Record00() {
//		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf", "00");
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf", "00");
	}


	public final ExpressionConversionInfo	fiiller				= createFixedValue(" ");
	public final ExpressionConversionInfo	tipoRec				= createGetBindingValue(fill2, TIPO_RECORD);
	public final ExpressionConversionInfo	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);

	public final ExpressionConversionInfo	pmtMtd				= createXPath(fill3, "PMRQ:PmtMtd");
	public final ExpressionConversionInfo	InstrPrty			= createXPath(fill4, "PMRQ:PmtTpInf/PMRQ:InstrPrty", true);
	public final ExpressionConversionInfo	cd					= createXPath(fill4, "PMRQ:PmtTpInf/PMRQ:SvcLvl/PMRQ:Cd", true);
	public final ExpressionConversionInfo	reqdExctnDt			= createXPath(fill10, "PMRQ:ReqdExctnDt");
	public final ExpressionConversionInfo	nm					= createXPath(fill70, "PMRQ:Dbtr/PMRQ:Nm");
	public final ExpressionConversionInfo	taxIdNb				= createXPath(fill35, "PMRQ:Dbtr/PMRQ:Id/PMRQ:OrgId/PMRQ:TaxIdNb", true);
	public final ExpressionConversionInfo	id					= createXPath(fill35, "PMRQ:Dbtr/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id", true);
	public final ExpressionConversionInfo	issr				= createXPath(fill35, "PMRQ:Dbtr/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Issr", true);
	public final Converter	sia					= initSia();
	
	public final ExpressionConversionInfo	iban				= initIbanConversion();

	
	public final ExpressionConversionInfo	bic					= createXPath(fill11, "PMRQ:DbtrAgt/PMRQ:FinInstnId/PMRQ:BIC", true);
	public final ExpressionConversionInfo	prtry				= initIdIstConversion();

	public final ExpressionConversionInfo	pstlAdrsNm			= createXPath(fill70, "PMRQ:UltmtDbtr/PMRQ:Nm", true);
	public final ExpressionConversionInfo	pstlAdrsAdrTp		= createXPath(fill4, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:AdrTp", true);
	public final ExpressionConversionInfo	pstlAdrsAdrLine1	= createXPath(fill70, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:AdrLine[1]", true);
	public final ExpressionConversionInfo	pstlAdrsAdrLine2	= createXPath(fill70, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:AdrLine[2]", true);
	public final ExpressionConversionInfo	pstlAdrsStrtNm		= createXPath(fill70, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:StrtNm", true);
	public final ExpressionConversionInfo	pstlAdrsBldgNb		= createXPath(fill16, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:BldgNb", true);
	public final ExpressionConversionInfo	pstlAdrsPstCd		= createXPath(fill16, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:PstCd", true);
	public final ExpressionConversionInfo	pstlAdrsTwnNm		= createXPath(fill35, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:TwnNm", true);
	public final ExpressionConversionInfo	pstlAdrsCtrySubDvsn	= createXPath(fill35, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:CtrySubDvsn", true);
	public final ExpressionConversionInfo	pstlAdrsCtry		= createXPath(fill2, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:Ctry", true);

	public final ExpressionConversionInfo 	chrgbr				= createXPath(fill4, "PMRQ:ChrgBr",true); 
	public final ExpressionConversionInfo 	chrgsAcctIban		= createXPath(fill35, "PMRQ:ChrgsAcct/PMRQ:Id/PMRQ:IBAN", true);
	
//	public final ExpressionConversionInfoExp	lineTerminator		= createLineTerminator();

	protected Converter initSia() {
		return createGetBindingValue(fill5, SIA, true);
	}

	protected XPathConversionInfo initIbanConversion() {
		return createXPath(fill35, "PMRQ:DbtrAcct/PMRQ:Id/PMRQ:IBAN");
	}
	
	protected ExpressionConversionInfo initIdIstConversion() {
		return createXPath(fill35,"PMRQ:DbtrAgt/PMRQ:FinInstnId/PMRQ:ClrSysMmbId/PMRQ:Prtry");
	}
}

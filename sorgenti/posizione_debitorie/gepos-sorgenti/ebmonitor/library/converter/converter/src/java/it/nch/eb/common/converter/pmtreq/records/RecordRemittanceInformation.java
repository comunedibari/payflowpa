/**
 * Created on 15/gen/08
 */
package it.nch.eb.common.converter.pmtreq.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.flatmodel.conversioninfo.ExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;


/**
 * @author gdefacci
 */
public class RecordRemittanceInformation extends ConversionRecord implements BaseConverters, ConversionsConsts{

	private static final long	serialVersionUID	= 7627290155205822443L;
	
	public RecordRemittanceInformation(String xpath, String tipoRecord) {
		super(xpath, tipoRecord);
	}
	
	public final ExpressionConversionInfo	fiiller				= createFixedValue(" ");
	public final ExpressionConversionInfo	tipoRec				= createGetBindingValue(fill2, TIPO_RECORD);
	public final ExpressionConversionInfo	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);
	
	public final ExpressionConversionInfo	pstlAdrsNm			= createXPath(fill70, "PMRQ:Nm", true);
	public final ExpressionConversionInfo	pstlAdrsAdrTp		= createXPath(fill4, "PMRQ:PstlAdr/PMRQ:AdrTp", true);
	public final ExpressionConversionInfo	pstlAdrsAdrLine		= createXPath(fill140, "PMRQ:PstlAdr/PMRQ:AdrLine", true);
	public final ExpressionConversionInfo	pstlAdrsStrtNm		= createXPath(fill70, "PMRQ:PstlAdr/PMRQ:StrtNm", true);
	public final ExpressionConversionInfo	pstlAdrsBldgNb		= createXPath(fill16, "PMRQ:PstlAdr/PMRQ:BldgNb", true);
	public final ExpressionConversionInfo	pstlAdrsPstCd		= createXPath(fill16, "PMRQ:PstlAdr/PMRQ:PstCd", true);
	public final ExpressionConversionInfo	pstlAdrsTwnNm		= createXPath(fill35, "PMRQ:PstlAdr/PMRQ:TwnNm", true);
	public final ExpressionConversionInfo	pstlAdrsCtrySubDvsn	= createXPath(fill35, "PMRQ:PstlAdr/PMRQ:CtrySubDvsn", true);
	public final ExpressionConversionInfo	pstlAdrsCtry		= createXPath(fill2, "PMRQ:PstlAdr/PMRQ:Ctry", true);
	
	public final ExpressionConversionInfo	bei					= createXPath(fill11, "PMRQ:Id/PMRQ:OrgId/PMRQ:BEI", true);
	public final ExpressionConversionInfo	taxIdNb				= createXPath(fill35, "PMRQ:Id/PMRQ:OrgId/PMRQ:TaxIdNb", true);
	public final ExpressionConversionInfo	prtryId_Id			= createXPath(fill35, "PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id", true);
	public final ExpressionConversionInfo	prtryId_Issr		= createXPath(fill35, "PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Issr", true);
	public final ExpressionConversionInfo	ctryOfRes			= createXPath(fill2, "PMRQ:CtryOfRes",true);
	
}

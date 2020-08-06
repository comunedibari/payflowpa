/**
 * Created on 15/gen/08
 */
package it.nch.eb.common.converter.pmtreq.records;




/**
 * @author gdefacci
 */
public class Record70 extends RecordRemittanceInformation {

	private static final long	serialVersionUID	= -2286678724770051677L;

	public Record70() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf/PMRQ:RmtInf/PMRQ:Strd/PMRQ:Invcr", "70");
	}
	
}

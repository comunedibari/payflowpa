/**
 * Created on 15/gen/08
 */
package it.nch.eb.common.converter.pmtreq.records;




/**
 * @author gdefacci
 */
public class Record80 extends RecordRemittanceInformation {

	private static final long	serialVersionUID	= -291881776861218339L;

	public Record80() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf/PMRQ:RmtInf/PMRQ:Strd/PMRQ:Invcee", "80");
	}
	
}

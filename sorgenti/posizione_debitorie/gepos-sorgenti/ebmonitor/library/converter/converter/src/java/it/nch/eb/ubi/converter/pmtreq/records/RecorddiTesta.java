/**
 * Created on 14/gen/08
 */
package it.nch.eb.ubi.converter.pmtreq.records;

import it.nch.eb.common.converter.common.records.BaseRecordTesta;
import it.nch.eb.flatx.bean.types.Converter;


/**
 * 				
 * @author gdefacci
 */
public class RecorddiTesta extends BaseRecordTesta {

	/**
	 * 
	 */
	private static final long serialVersionUID = 485343579401404705L;
	public RecorddiTesta() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIHdrSrv", "SH");
	}
	
	public final Converter rtrnAddrl 	= createXPath(fill12, "../MSG:CBIHdrTrt/HTRT:RtrnAddrl", true);
	public final Converter sendTyp 		= createXPath(fill35, "HE2E:Sender/HE2E:SendTyp", true);
	public final Converter cBIRefrSend 	= createXPath(fill8, "HE2E:Sender/HE2E:CBIRefrSend", true);
	public final Converter recvTyp 		= createXPath(fill35, "HE2E:Receiver/HE2E:RecvTyp", true);
	public final Converter cBIRefrRecv 	= createXPath(fill8, "HE2E:Receiver/HE2E:CBIRefrRecv", true);
	

}

package it.nch.eb.common.converter.pmtreq.cdtr.records;

import it.nch.eb.common.converter.common.records.BaseRecordTesta;
import it.nch.eb.flatx.bean.types.Converter;

public class RecorddiTesta extends BaseRecordTesta {

	private static final long serialVersionUID = 1L;
	
	public RecorddiTesta() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIHdrTrt", "SH");
	}
	
	public final Converter rtrnAddrl 	= createXPath(fill12, "HTRT:RtrnAddrl", true);
	public final Converter sendTyp 		= createXPath(fill35, "../MSG:CBIHdrSrv/HE2E:Sender/HE2E:SendTyp", true);
	public final Converter cBIRefrSend 	= createXPath(fill8, "../MSG:CBIHdrSrv/HE2E:Sender/HE2E:CBIRefrSend", true);
	public final Converter recvTyp 		= createXPath(fill35, "../MSG:CBIHdrSrv/HE2E:Receiver/HE2E:RecvTyp", true);
	public final Converter cBIRefrRecv 	= createXPath(fill8, "../MSG:CBIHdrSrv/HE2E:Receiver/HE2E:CBIRefrRecv", true);
}
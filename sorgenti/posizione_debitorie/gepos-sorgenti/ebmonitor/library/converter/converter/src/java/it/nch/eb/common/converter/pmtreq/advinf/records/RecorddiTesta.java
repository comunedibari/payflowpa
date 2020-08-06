package it.nch.eb.common.converter.pmtreq.advinf.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class RecorddiTesta extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 4710907597073006743L;
	public RecorddiTesta() {
		super("/MSG:CBIAdvInstrMsg/MSG:CBIHdrSrv", "SH");
	}

	public final Converter filler = createFixedValue(fill1, " ");	
	public final Converter tipoRecord = createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter srvNm = createXPath(fill30, "../MSG:CBIHdrTrt/HTRT:SrvNm");
	public final Converter idCBISndrf = createXPath(fill8, "../MSG:CBIHdrTrt/HTRT:IdCBISndrf", true);
	public final Converter idCBIRcvrf = createXPath(fill8, "../MSG:CBIHdrTrt/HTRT:IdCBIRcvrf", true);
	public final Converter idMsgTrt = createXPath(fill61, "../MSG:CBIHdrTrt/HTRT:IdMsgTrt", true);
	public final Converter xMLCrtDt = createXPath(fill19, "../MSG:CBIHdrTrt/HTRT:XMLCrtDt", true);
	public final Converter idE2EMsg = createXPath(fill44, "HE2E:SrvInfo/HE2E:IdE2EMsg", true);
	public final Converter xMLCrtDt1 = createXPath(fill19, "HE2E:SrvInfo/HE2E:XMLCrtDt", true);
	public final Converter idCBISend = createXPath(fill8, "HE2E:Sender/HE2E:IdCBISend", true);
	public final Converter idCBIRecv = createXPath(fill8, "HE2E:Receiver/HE2E:IdCBIRecv", true);
	public final Converter usrBnk = createXPath(fill8, "HE2E:DiagInfo/HE2E:UsrBnk", true);
	public final Converter diagVers = createXPath(fill35, "HE2E:DiagInfo/HE2E:DiagVers", true);
	public final Converter chkSbj = createXPath(fill8, "HE2E:DiagInfo/HE2E:ChkSbj", true);
	public final Converter chkDt = createXPath(dateTime19ToXml, "HE2E:DiagInfo/HE2E:ChkDt",true);
	public final Converter srvBdyNb = createXPath(fill4, "HE2E:CongrInfo/HE2E:SrvBdyNb");
	public final Converter version = createGetBindingValue(fill35, VERSION, true);
	public final Converter totalRowsNumber = createGetBindingValue(numb7, TOTAL_ROW_NUMBER, true);
	public final Converter rtrnAddrl = createXPath(fill12, "../MSG:CBIHdrTrt/HTRT:RtrnAddrl", true);
	public final Converter sendTyp = createXPath(fill35, "HE2E:Sender/HE2E:SendTyp", true);
	
	public final Converter cBIRefrSend = createXPath(fill8, "HE2E:Sender/HE2E:CBIRefrSend", true);
	public final Converter recvTyp = createXPath(fill35, "HE2E:Receiver/HE2E:RecvTyp", true);
	public final Converter cBIRefrRecv = createXPath(fill8, "HE2E:Receiver/HE2E:CBIRefrRecv", true);
	
}
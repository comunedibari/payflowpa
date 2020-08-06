/**
 * 15/gen/2010
 */
package it.nch.eb.common.converter.pmtreq.dbtr.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.common.records.BaseRecordTesta;
import it.nch.eb.flatx.bean.types.Converter;

/**
 * @author gdefacci
 */
public class EsitiRecordTesta extends ConversionRecord {
	
	public EsitiRecordTesta() {
		super("/CBIDbtrPmtStatusReportMsg/CBIHdrSrv/", "SH");
	}
	
	public EsitiRecordTesta(String xpath, String tipoRecord) {
		super(xpath, tipoRecord);
	}
	
	private static final long	serialVersionUID	= 8540876451567957459L;
	
	public final Converter filler = createFixedValue(" ");
	public final Converter tipoRecord = createGetBindingValue(fill2, "tipoRecord");
	
	public final Converter srvNm 			= createXPath(fill30, "../CBIHdrTrt/HTRT:SrvNm");
	public final Converter idCBISndrf 		= createXPath(fill8, "../CBIHdrTrt/HTRT:IdCBISndrf");
	public final Converter idCBIRcvrf 		= createXPath(fill8, "../CBIHdrTrt/HTRT:IdCBIRcvrf");
	public final Converter idMsgTrt 		= createXPath(fill61, "../CBIHdrTrt/HTRT:IdMsgTrt");
	public final Converter xmlCrtDt 		= createXPath(dateTime19ToXml, "../CBIHdrTrt/HTRT:XMLCrtDt");
	
	public final Converter idE2EMsg 	= 	createXPath(fill44, "HE2E:SrvInfo/HE2E:IdE2EMsg");                 
	public final Converter srvInfoXmlCrtDt 	
										=  createXPath(dateTime19ToXml, "HE2E:SrvInfo/HE2E:XMLCrtDt");                
	public final Converter idCBISend	=  createXPath(fill8, "HE2E:Sender/HE2E:IdCBISend");                  
	public final Converter idCBIRecv 	=  createXPath(fill8, "HE2E:Receiver/HE2E:IdCBIRecv");                
	public final Converter usrBnk 		=  createXPath(fill8, "HE2E:DiagInfo/HE2E:UsrBnk",true);                  
			                                                                                                    
	public final Converter diagVers 	=  createXPath(fill35, "HE2E:DiagInfo/HE2E:DiagVers",true);               
			                                                                                                    
	public final Converter chkSbj 		=  createXPath(fill8, "HE2E:DiagInfo/HE2E:ChkSbj",true);                  
	public final Converter chkDt 		=  createXPath(dateTime19ToXml, "HE2E:DiagInfo/HE2E:ChkDt",true);                    
	public final Converter srvBdyNb 	=  createXPath(fill4, "HE2E:CongrInfo/HE2E:SrvBdyNb");
	
	public final Converter version			= createGetBindingValue(fill35, "version", true);
	public final Converter totalRowsNumber	= createGetBindingValue(numb7, TOTAL_ROW_NUMBER, true);
	
	public final Converter rtrnAddrl 	= createXPath(fill12, "../CBIHdrTrt/HTRT:RtrnAddrl", true);
	public final Converter sendTyp 		= createXPath(fill35, "HE2E:Sender/HE2E:SendTyp", true);
	public final Converter cBIRefrSend 	= createXPath(fill8, "HE2E:Sender/HE2E:CBIRefrSend", true);
	public final Converter recvTyp 		= createXPath(fill35, "HE2E:Receiver/HE2E:RecvTyp", true);
	public final Converter cBIRefrRecv 	= createXPath(fill8, "HE2E:Receiver/HE2E:CBIRefrRecv", true);

}

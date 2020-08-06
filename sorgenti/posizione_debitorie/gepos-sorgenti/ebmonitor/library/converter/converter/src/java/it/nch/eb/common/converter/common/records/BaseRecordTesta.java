/**
 * Created on 14/gen/08
 */
package it.nch.eb.common.converter.common.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.ubi.converter.pmtreq.records.MapDecoder;


/**
 * 				
 * @author gdefacci
 */
public class BaseRecordTesta extends ConversionRecord {

	private static final long	serialVersionUID	= 8540876451567957459L;
	public BaseRecordTesta(String path, String tipoRec) {
		super(path, tipoRec);
	}
	
	public final Converter filler = createFixedValue(" ");
	public final Converter tipoRecord = createGetBindingValue(fill2, "tipoRecord");
	
	public final Converter srvNm 			= createXPath(fill30, "../MSG:CBIHdrTrt/HTRT:SrvNm");
	public final Converter idCBISndrf 		= createXPath(fill8, "../MSG:CBIHdrTrt/HTRT:IdCBISndrf");
	public final Converter idCBIRcvrf 		= createXPath(fill8, "../MSG:CBIHdrTrt/HTRT:IdCBIRcvrf");
	public final Converter idMsgTrt 		= createXPath(fill61, "../MSG:CBIHdrTrt/HTRT:IdMsgTrt");
	public final Converter xmlCrtDt 		= createXPath(dateTime19ToXml, "../MSG:CBIHdrTrt/HTRT:XMLCrtDt");
	
	public final Converter idE2EMsg 	= 	createXPath(fill44, "HE2E:SrvInfo/HE2E:IdE2EMsg");                 
	public final Converter srvInfoXmlCrtDt 	
										=  createXPath(dateTime19ToXml, "HE2E:SrvInfo/HE2E:XMLCrtDt");                
	public final Converter idCBISend	=  createXPath(fill8, "HE2E:Sender/HE2E:IdCBISend");                  
	public final Converter idCBIRecv 	=  createIdCBIRecvConversion();                 
	public final Converter usrBnk 		=  createXPath(fill8, "HE2E:DiagInfo/HE2E:UsrBnk",true);                  
			                                                                                                    
	public final Converter diagVers 	=  createXPath(fill35, "HE2E:DiagInfo/HE2E:DiagVers",true);               
			                                                                                                    
	public final Converter chkSbj 		=  createXPath(fill8, "HE2E:DiagInfo/HE2E:ChkSbj",true);                  
	public final Converter chkDt 		=  createXPath(dateTime19ToXml, "HE2E:DiagInfo/HE2E:ChkDt",true);                    
	public final Converter srvBdyNb 	=  createXPath(fill4, "HE2E:CongrInfo/HE2E:SrvBdyNb");
	
	public final Converter version			= createGetBindingValue(fill35, "version", true);
	public final Converter totalRowsNumber	= createGetBindingValue(numb7, TOTAL_ROW_NUMBER, true);
	
	protected Converter createIdCBIRecvConversion() {
		return new MapDecoder(getPos(),ConversionsConsts.CUC_CUC_LOGIC_DEST_MAP,fill8, "HE2E:Receiver/HE2E:IdCBIRecv",false);
	}

	
}

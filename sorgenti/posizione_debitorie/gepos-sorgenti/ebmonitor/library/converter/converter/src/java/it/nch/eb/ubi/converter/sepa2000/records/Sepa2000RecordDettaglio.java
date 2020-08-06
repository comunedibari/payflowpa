/**
 * Created on 09/gen/08
 */
package it.nch.eb.ubi.converter.sepa2000.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.SubstringExtractorConverter;


/**
 * @author gdefacci
 */
public class Sepa2000RecordDettaglio extends ConversionRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1881793455347659816L;
	public Sepa2000RecordDettaglio() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf", null);
	}

	public final Converter fillerblank = createFixedValue(" ");
	public final Converter filler_10 	= createFixedValue(fill2, "10");
	public final Converter abiBeneficiario = createXPath(new SubstringExtractorConverter(5,10, fill5 ), "PMRQ:CdtrAcct/PMRQ:Id/PMRQ:IBAN");
	public final Converter cabBeneficiario = createXPath(new SubstringExtractorConverter(10,15, fill5), "PMRQ:CdtrAcct/PMRQ:Id/PMRQ:IBAN");
	public final Converter ccBeneficiario = createXPath(new SubstringExtractorConverter(15,27, rfill12), "PMRQ:CdtrAcct/PMRQ:Id/PMRQ:IBAN");
	
	public final Converter valoreFissoCC = createFixedValue(fill3, "C/C");
	public final Converter valoreFissoURI = createFixedValue(fill5, "/URI/");
	
	public final Converter endToEndId		  = createXPath(fill45, "PMRQ:PmtId/PMRQ:EndToEndId" );
	public final Converter cdtrPstlAdrPstCd   = createXPath(fill10, "PMRQ:Cdtr/PMRQ:PstlAdr/PMRQ:PstCd", true );
	public final Converter cdtrName           = createXPath(fill80, "PMRQ:Cdtr/PMRQ:Nm"                 );
	public final Converter cdtrPstlAdrAdrTp   = createXPath(fill5, "PMRQ:Cdtr/PMRQ:PstlAdr/PMRQ:AdrTp", true );

	public final Converter cdtrPstlAdrAdrLineStrtNm = create(fill75, or(new String[] {
		"PMRQ:Cdtr/PMRQ:PstlAdr/PMRQ:AdrLine", 
		"PMRQ:Cdtr/PMRQ:PstlAdr/PMRQ:StrtNm"
	}));
	
	public final Converter cdtrPstlAdrTwnNm                        = createXPath(fill40 , "PMRQ:Cdtr/PMRQ:PstlAdr/PMRQ:TwnNm", true );
	public final Converter blank1                                  = createFixedValue(fill8, " ");
	public final Converter blank2                                  = createFixedValue(fill8  , " ");
	public final Converter amt                                     = createXPath(fd_number17_2 , "PMRQ:Amt/PMRQ:InstdAmt" );
	public final Converter fisso0000                               = createFixedValue(fill4, "0000" );
//	public final Converter prtry                                   = createXPath(fill2  , "PMRQ:Purp/PMRQ:Prtry", true );
	public final Converter prtry                                   = create(fill2  , or(new String[] { "PMRQ:Purp/PMRQ:Cd", "PMRQ:Purp/PMRQ:Prtry" }));
	public final Converter rmtInfUstrdOrRmtInfStrd                 = create(fill140, or(new String[] { 
			"PMRQ:RmtInf/PMRQ:Ustrd", 
			"PMRQ:RmtInf/PMRQ:Strd" } ));
	
	public final Converter prtryIdOrOthrId                         = 
		create(fill70 , 
			or().or("PMRQ:Cdtr/PMRQ:Id/PMRQ:OrgId/PMRQ:BEI")
				.or("PMRQ:Cdtr/PMRQ:Id/PMRQ:OrgId/PMRQ:TaxIdNb")
				.or("PMRQ:Cdtr/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id" , 
					concatXPathExpression(new String[] {
							"PMRQ:Cdtr/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id", 
							"PMRQ:Cdtr/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Issr" }, 
							new Converter[] { fill35, fill35 } )					
				)
				.or("PMRQ:Cdtr/PMRQ:Id/PMRQ:PrvtId/PMRQ:TaxIdNb")
				.or("PMRQ:Cdtr/PMRQ:Id/PMRQ:PrvtId/PMRQ:OthrId/PMRQ:Id",
					concatXPathExpression(new String[] { 
							"PMRQ:Cdtr/PMRQ:Id/PMRQ:PrvtId/PMRQ:OthrId/PMRQ:Id", 
							"PMRQ:Cdtr/PMRQ:Id/PMRQ:PrvtId/PMRQ:OthrId/PMRQ:IdTpr" },
							new Converter[] { fill35 ,  fill35 } )) );


	
	public final Converter ultmtCdtrName                           = createXPath(fill70 , "PMRQ:UltmtCdtr/PMRQ:Nm", true );
	
	public final Converter ultmtCdtrIdentification                 = create(fill70 , 
			or(false).or("PMRQ:UltmtCdtr/PMRQ:Id/PMRQ:OrgId/PMRQ:BEI" )
				.or("PMRQ:UltmtCdtr/PMRQ:Id/PMRQ:OrgId/PMRQ:TaxIdNb" ) 
				.or("PMRQ:UltmtCdtr/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id", 
					concatXPathExpression(new String[] {
							"PMRQ:UltmtCdtr/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id", 
							"PMRQ:UltmtCdtr/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Issr" }, 
							new Converter[] { fill35, fill35 } )	
				)
				.or("PMRQ:UltmtCdtr/PMRQ:Id/PMRQ:PrvtId/PMRQ:TaxIdNb" )
				.or("PMRQ:UltmtCdtr/PMRQ:Id/PMRQ:PrvtId/PMRQ:OthrId/PMRQ:Id", 
					concatXPathExpression(new String[] {
							"PMRQ:UltmtCdtr/PMRQ:Id/PMRQ:PrvtId/PMRQ:OthrId/PMRQ:Id", 
							"PMRQ:UltmtCdtr/PMRQ:Id/PMRQ:PrvtId/PMRQ:OthrId/PMRQ:IdTp" }, 
							new Converter[] { fill35, fill35 } )	
				));	
	
	
	
	public final Converter cdtrPstlAdrBldgNb                       = createXPath(fill16 , "PMRQ:Cdtr/PMRQ:PstlAdr/PMRQ:BldgNb", true );
	public final Converter cdtrPstlAdrCtrySubDvsn                  = createXPath(fill35 , "PMRQ:Cdtr/PMRQ:PstlAdr/PMRQ:CtrySubDvsn", true );
	public final Converter blank3                                  = createFixedValue(fill29 , " ");
	
	public final Converter ultmtDbtrDbtr                           = create(fill80, or(new String[] {
			"../PMRQ:Dbtr/PMRQ:Nm",
			"../PMRQ:UltmtDbtr/PMRQ:Nm", 
			"../PMRQ:CdtTrfTxInf/PMRQ:UltmtDbtr/PMRQ:Nm" } ));
	
	public final Converter reqdExctnDt                             = createXPath(date8  , "../PMRQ:ReqdExctnDt" );
	public final Converter dbtrAcctAbi                             = createXPath(new SubstringExtractorConverter(5,10, fill5)  , "../PMRQ:DbtrAcct/PMRQ:Id/PMRQ:IBAN" );
	public final Converter dbtrAcctCAB                             = createXPath(new SubstringExtractorConverter(10,15, fill5)  , "../PMRQ:DbtrAcct/PMRQ:Id/PMRQ:IBAN" );
	public final Converter dbtrAcctCC                              = createXPath(new SubstringExtractorConverter(15,27, fill12), "../PMRQ:DbtrAcct/PMRQ:Id/PMRQ:IBAN" );
	
	public final Converter fissoN                                  = createFixedValue(fill1  , "N" );
	public final Converter amtDivisa                               = createXPath(fill3  , "PMRQ:Amt/PMRQ:InstdAmt/@Ccy" );
	
	public final Converter blank3a                                 = createFixedValue(fill20 , " ");
	
	public final Converter taxIdNb                                 = createXPath(fill15 , "../PMRQ:Dbtr/PMRQ:Id/PMRQ:OrgId/PMRQ:TaxIdNb", true );
	public final Converter instrPrty                               = createXPath(fill1  , "../PMRQ:PmtTpInf/PMRQ:InstrPrty",true );
	public final Converter dbtrAgtBIC                              = createXPath(fill13 , "../PMRQ:DbtrAgt/PMRQ:FinInstnId/PMRQ:BIC", true );
	public final Converter cdtrAgtBIC                              = createXPath(fill12 , "PMRQ:CdtrAgt/PMRQ:FinInstnId/PMRQ:BIC", true);
	
	public final Converter blank4                                  = createFixedValue(fill26 , " ");
	public final Converter blank5                                  = createFixedValue(fill1  , " ");
	public final Converter blank6                                  = createFixedValue(fill1  , " ");
	public final Converter blank7                                  = createFixedValue(fill9  , " ");
	public final Converter blank8                                  = createFixedValue(fill19 , " ");
	public final Converter cdtrAcctCIN                             = createXPath(new SubstringExtractorConverter(4,5, fill1)  , "PMRQ:CdtrAcct/PMRQ:Id/PMRQ:IBAN" );
	public final Converter cdtrAcctIBANPaese                       = createXPath(new SubstringExtractorConverter(0,2, fill2)  , "PMRQ:CdtrAcct/PMRQ:Id/PMRQ:IBAN" );
	public final Converter cdtrAcctIBANCcheckDigit                 = createXPath(new SubstringExtractorConverter(2,4, fill2)  , "PMRQ:CdtrAcct/PMRQ:Id/PMRQ:IBAN" );
	public final Converter siaMittente                             = createCUCDecoder(fill5  , "../../PMRQ:GrpHdr/PMRQ:InitgPty/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id");
	public final Converter siaOrdinante                            = createCUCDecoder(fill5  , "../PMRQ:Dbtr/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id" , true);
	public final Converter blank9                                  = createFixedValue(fill1  , " ");
	public final Converter blank10                                 = createFixedValue(fill1  , " ");
	public final Converter blank11                                 = createFixedValue(fill5  , " ");
	public final Converter blank12                                 = createFixedValue(fill5  , " ");
	public final Converter blank13                                 = createFixedValue(fill1  , " ");
	public final Converter identificativoEsito                     = createXPath(fill35 , "PMRQ:PmtId/PMRQ:InstrId" );
	public final Converter blank14                                 = createFixedValue(fill11 , " ");
	public final Converter blank16                                 = createFixedValue(fill1  , " ");
	public final Converter ultmtDbtrDistinta1                      = createXPath(fill4  , "../PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:AdrTp", true );
	public final Converter ultmtDbtrDistinta2                      = createXPath(fill70 , "../PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:AdrLine", true );
	public final Converter ultmtDbtrDistinta3                      = createXPath(fill70 , "../PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:StrtNm", true );
	public final Converter ultmtDbtrDistinta4                      = createXPath(fill16 , "../PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:BldgNb", true );
	public final Converter ultmtDbtrDistinta5                      = createXPath(fill16 , "../PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:PstCd", true );
	public final Converter ultmtDbtrDistinta6                      = createXPath(fill35 , "../PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:TwnNm" , true );
	public final Converter ultmtDbtrDistinta7                      = createXPath(fill35 , "../PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:CtrySubDvsn", true );
	public final Converter ultmtDbtrDistinta8                      = createXPath(fill2  , "../PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:Ctry", true );
	public final Converter blank17                                 = createXPath(fill4  , "../PMRQ:ChrgBr" );
	public final Converter blank18                                 = createXPath(fill30 , "../PMRQ:ChrgsAcct/PMRQ:Id/PMRQ:IBAN", true );
	public final Converter blank19                                 = createXPath(fill35 , "PMRQ:PmtTpInf/PMRQ:LclInstrm/PMRQ:Prtry", true );
	public final Converter blank20                                 = createXPath(fill4  , "PMRQ:PmtTpInf/PMRQ:CtgyPurp", true );
	public final Converter ultmtDbtrTransazione1                   = createXPath(fill4  , "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:AdrTp", true );
	public final Converter ultmtDbtrTransazione2                   = createXPath(fill70 , "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:AdrLine", true );
	public final Converter ultmtDbtrTransazione3                   = createXPath(fill70 , "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:StrtNm", true );
	public final Converter ultmtDbtrTransazione4                   = createXPath(fill16 , "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:BldgNb", true );
	public final Converter ultmtDbtrTransazione5                   = createXPath(fill16 , "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:PstCd",true );
	public final Converter ultmtDbtrTransazione6                   = createXPath(fill35 , "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:TwnNm",true );
	public final Converter ultmtDbtrTransazione7                   = createXPath(fill35 , "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:CtrySubDvsn",true );
	public final Converter ultmtDbtrTransazione8                   = createXPath(fill2  , "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:Ctry",true );
	public final Converter blank21                                 = createXPath(fill5  , "PMRQ:SrvInf",true );
	public final Converter ultmtCdtr1                              = createXPath(fill4  , "PMRQ:UltmtCdtr/PMRQ:PstlAdr/PMRQ:AdrTp", true);
	public final Converter ultmtCdtr2                              = createXPath(fill70 , "PMRQ:UltmtCdtr/PMRQ:PstlAdr/PMRQ:AdrLine", true);
	public final Converter ultmtCdtr3                              = createXPath(fill70 , "PMRQ:UltmtCdtr/PMRQ:PstlAdr/PMRQ:StrtNm", true);
	public final Converter ultmtCdtr4                              = createXPath(fill16 , "PMRQ:UltmtCdtr/PMRQ:PstlAdr/PMRQ:BldgNb", true);
	public final Converter ultmtCdtr5                              = createXPath(fill16 , "PMRQ:UltmtCdtr/PMRQ:PstlAdr/PMRQ:PstCd", true);
	public final Converter ultmtCdtr6                              = createXPath(fill35 , "PMRQ:UltmtCdtr/PMRQ:PstlAdr/PMRQ:TwnNm", true );
	public final Converter ultmtCdtr7                              = createXPath(fill35 , "PMRQ:UltmtCdtr/PMRQ:PstlAdr/PMRQ:CtrySubDvsn", true );
	public final Converter ultmtCdtr8                              = createXPath(fill2  , "PMRQ:UltmtCdtr/PMRQ:PstlAdr/PMRQ:Ctry", true );
	public final Converter destCdtrRsp1                            = createXPath(fill70 , "PMRQ:DestCdtrRsp/PMRQ:Nm", true );
	public final Converter destCdtrRsp2                            = createXPath(fill35 , "PMRQ:DestCdtrRsp/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id", true );
	public final Converter blank22                            	   = createFixedValue(fill10 , " " );
                                             

}

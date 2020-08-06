
package it.nch.eb.ubi.converter.sepa2000.models;

import it.nch.eb.common.utils.StringUtils;


public class Sepa2000RecordDettaglioModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String fillerblank;
		private String filler_10;
		private String abiBeneficiario;
		private String cabBeneficiario;
		private String ccBeneficiario;
		private String valoreFissoCC;
		private String valoreFissoURI;
		private String endToEndId;
		private String cdtrPstlAdrPstCd;
		private String cdtrName;
		private String cdtrPstlAdrAdrTp;
		private String cdtrPstlAdrAdrLineStrtNm;
		private String cdtrPstlAdrTwnNm;
		private String blank1;
		private String blank2;
		private String amt;
		private String fisso0000;
		private String prtry;
		private String rmtInfUstrdOrRmtInfStrd;
		private String prtryIdOrOthrId;
		private String ultmtCdtrName;
		private String ultmtCdtrIdentification;
		private String cdtrPstlAdrBldgNb;
		private String cdtrPstlAdrCtrySubDvsn;
		private String blank3;
		private String ultmtDbtrDbtr;
		private String reqdExctnDt;
		private String dbtrAcctAbi;
		private String dbtrAcctCAB;
		private String dbtrAcctCC;
		private String fissoN;
		private String amtDivisa;
		private String blank3a;
		private String taxIdNb;
		private String instrPrty;
		private String dbtrAgtBIC;
		private String cdtrAgtBIC;
		private String blank4;
		private String blank5;
		private String blank6;
		private String blank7;
		private String blank8;
		private String cdtrAcctCIN;
		private String cdtrAcctIBANPaese;
		private String cdtrAcctIBANCcheckDigit;
		private String siaMittente;
		private String siaOrdinante;
		private String blank9;
		private String blank10;
		private String blank11;
		private String blank12;
		private String blank13;
		private String identificativoEsito;
		private String blank14;
		private String blank16;
		private String ultmtDbtrDistinta1;
		private String ultmtDbtrDistinta2;
		private String ultmtDbtrDistinta3;
		private String ultmtDbtrDistinta4;
		private String ultmtDbtrDistinta5;
		private String ultmtDbtrDistinta6;
		private String ultmtDbtrDistinta7;
		private String ultmtDbtrDistinta8;
		private String blank17;
		private String blank18;
		private String blank19;
		private String blank20;
		private String ultmtDbtrTransazione1;
		private String ultmtDbtrTransazione2;
		private String ultmtDbtrTransazione3;
		private String ultmtDbtrTransazione4;
		private String ultmtDbtrTransazione5;
		private String ultmtDbtrTransazione6;
		private String ultmtDbtrTransazione7;
		private String ultmtDbtrTransazione8;
		private String blank21;
		private String ultmtCdtr1;
		private String ultmtCdtr2;
		private String ultmtCdtr3;
		private String ultmtCdtr4;
		private String ultmtCdtr5;
		private String ultmtCdtr6;
		private String ultmtCdtr7;
		private String ultmtCdtr8;
		private String destCdtrRsp1;
		private String destCdtrRsp2;
		private String blank22;
	
	private int	lineNumber;
	
		
			public String getFillerblank() {
				return fillerblank;
			}
		
			public void setFillerblank(String fillerblank) {
				this.fillerblank = fillerblank;
			}
			public String getFiller_10() {
				return filler_10;
			}
		
			public void setFiller_10(String filler_10) {
				this.filler_10 = filler_10;
			}
			public String getAbiBeneficiario() {
				return abiBeneficiario;
			}
		
			public void setAbiBeneficiario(String abiBeneficiario) {
				this.abiBeneficiario = abiBeneficiario;
			}
			public String getCabBeneficiario() {
				return cabBeneficiario;
			}
		
			public void setCabBeneficiario(String cabBeneficiario) {
				this.cabBeneficiario = cabBeneficiario;
			}
			public String getCcBeneficiario() {
				return ccBeneficiario;
			}
		
			public void setCcBeneficiario(String ccBeneficiario) {
				this.ccBeneficiario = ccBeneficiario;
			}
			public String getValoreFissoCC() {
				return valoreFissoCC;
			}
		
			public void setValoreFissoCC(String valoreFissoCC) {
				this.valoreFissoCC = valoreFissoCC;
			}
			public String getValoreFissoURI() {
				return valoreFissoURI;
			}
		
			public void setValoreFissoURI(String valoreFissoURI) {
				this.valoreFissoURI = valoreFissoURI;
			}
			public String getEndToEndId() {
				return endToEndId;
			}
		
			public void setEndToEndId(String endToEndId) {
				this.endToEndId = endToEndId;
			}
			public String getCdtrPstlAdrPstCd() {
				return cdtrPstlAdrPstCd;
			}
		
			public void setCdtrPstlAdrPstCd(String cdtrPstlAdrPstCd) {
				this.cdtrPstlAdrPstCd = cdtrPstlAdrPstCd;
			}
			public String getCdtrName() {
				return cdtrName;
			}
		
			public void setCdtrName(String cdtrName) {
				this.cdtrName = cdtrName;
			}
			public String getCdtrPstlAdrAdrTp() {
				return cdtrPstlAdrAdrTp;
			}
		
			public void setCdtrPstlAdrAdrTp(String cdtrPstlAdrAdrTp) {
				this.cdtrPstlAdrAdrTp = cdtrPstlAdrAdrTp;
			}
			public String getCdtrPstlAdrAdrLineStrtNm() {
				return cdtrPstlAdrAdrLineStrtNm;
			}
		
			public void setCdtrPstlAdrAdrLineStrtNm(String cdtrPstlAdrAdrLineStrtNm) {
				this.cdtrPstlAdrAdrLineStrtNm = cdtrPstlAdrAdrLineStrtNm;
			}
			public String getCdtrPstlAdrTwnNm() {
				return cdtrPstlAdrTwnNm;
			}
		
			public void setCdtrPstlAdrTwnNm(String cdtrPstlAdrTwnNm) {
				this.cdtrPstlAdrTwnNm = cdtrPstlAdrTwnNm;
			}
			public String getBlank1() {
				return blank1;
			}
		
			public void setBlank1(String blank1) {
				this.blank1 = blank1;
			}
			public String getBlank2() {
				return blank2;
			}
		
			public void setBlank2(String blank2) {
				this.blank2 = blank2;
			}
			public String getAmt() {
				return amt;
			}
		
			public void setAmt(String amt) {
				this.amt = amt;
			}
			public String getFisso0000() {
				return fisso0000;
			}
		
			public void setFisso0000(String fisso0000) {
				this.fisso0000 = fisso0000;
			}
			public String getPrtry() {
				return prtry;
			}
		
			public void setPrtry(String prtry) {
				this.prtry = prtry;
			}
			public String getRmtInfUstrdOrRmtInfStrd() {
				return rmtInfUstrdOrRmtInfStrd;
			}
		
			public void setRmtInfUstrdOrRmtInfStrd(String rmtInfUstrdOrRmtInfStrd) {
				this.rmtInfUstrdOrRmtInfStrd = rmtInfUstrdOrRmtInfStrd;
			}
			public String getPrtryIdOrOthrId() {
				return prtryIdOrOthrId;
			}
		
			public void setPrtryIdOrOthrId(String prtryIdOrOthrId) {
				this.prtryIdOrOthrId = prtryIdOrOthrId;
			}
			public String getUltmtCdtrName() {
				return ultmtCdtrName;
			}
		
			public void setUltmtCdtrName(String ultmtCdtrName) {
				this.ultmtCdtrName = ultmtCdtrName;
			}
			public String getUltmtCdtrIdentification() {
				return ultmtCdtrIdentification;
			}
		
			public void setUltmtCdtrIdentification(String ultmtCdtrIdentification) {
				this.ultmtCdtrIdentification = ultmtCdtrIdentification;
			}
			public String getCdtrPstlAdrBldgNb() {
				return cdtrPstlAdrBldgNb;
			}
		
			public void setCdtrPstlAdrBldgNb(String cdtrPstlAdrBldgNb) {
				this.cdtrPstlAdrBldgNb = cdtrPstlAdrBldgNb;
			}
			public String getCdtrPstlAdrCtrySubDvsn() {
				return cdtrPstlAdrCtrySubDvsn;
			}
		
			public void setCdtrPstlAdrCtrySubDvsn(String cdtrPstlAdrCtrySubDvsn) {
				this.cdtrPstlAdrCtrySubDvsn = cdtrPstlAdrCtrySubDvsn;
			}
			public String getBlank3() {
				return blank3;
			}
		
			public void setBlank3(String blank3) {
				this.blank3 = blank3;
			}
			public String getUltmtDbtrDbtr() {
				return ultmtDbtrDbtr;
			}
		
			public void setUltmtDbtrDbtr(String ultmtDbtrDbtr) {
				this.ultmtDbtrDbtr = ultmtDbtrDbtr;
			}
			public String getReqdExctnDt() {
				return reqdExctnDt;
			}
		
			public void setReqdExctnDt(String reqdExctnDt) {
				this.reqdExctnDt = reqdExctnDt;
			}
			public String getDbtrAcctAbi() {
				return dbtrAcctAbi;
			}
		
			public void setDbtrAcctAbi(String dbtrAcctAbi) {
				this.dbtrAcctAbi = dbtrAcctAbi;
			}
			public String getDbtrAcctCAB() {
				return dbtrAcctCAB;
			}
		
			public void setDbtrAcctCAB(String dbtrAcctCAB) {
				this.dbtrAcctCAB = dbtrAcctCAB;
			}
			public String getDbtrAcctCC() {
				return dbtrAcctCC;
			}
		
			public void setDbtrAcctCC(String dbtrAcctCC) {
				this.dbtrAcctCC = dbtrAcctCC;
			}
			public String getFissoN() {
				return fissoN;
			}
		
			public void setFissoN(String fissoN) {
				this.fissoN = fissoN;
			}
			public String getAmtDivisa() {
				return amtDivisa;
			}
		
			public void setAmtDivisa(String amtDivisa) {
				this.amtDivisa = amtDivisa;
			}
			public String getBlank3a() {
				return blank3a;
			}
		
			public void setBlank3a(String blank3a) {
				this.blank3a = blank3a;
			}
			public String getTaxIdNb() {
				return taxIdNb;
			}
		
			public void setTaxIdNb(String taxIdNb) {
				this.taxIdNb = taxIdNb;
			}
			public String getInstrPrty() {
				return instrPrty;
			}
		
			public void setInstrPrty(String instrPrty) {
				this.instrPrty = instrPrty;
			}
			public String getDbtrAgtBIC() {
				return dbtrAgtBIC;
			}
		
			public void setDbtrAgtBIC(String dbtrAgtBIC) {
				this.dbtrAgtBIC = dbtrAgtBIC;
			}
			public String getCdtrAgtBIC() {
				return cdtrAgtBIC;
			}
		
			public void setCdtrAgtBIC(String cdtrAgtBIC) {
				this.cdtrAgtBIC = cdtrAgtBIC;
			}
			public String getBlank4() {
				return blank4;
			}
		
			public void setBlank4(String blank4) {
				this.blank4 = blank4;
			}
			public String getBlank5() {
				return blank5;
			}
		
			public void setBlank5(String blank5) {
				this.blank5 = blank5;
			}
			public String getBlank6() {
				return blank6;
			}
		
			public void setBlank6(String blank6) {
				this.blank6 = blank6;
			}
			public String getBlank7() {
				return blank7;
			}
		
			public void setBlank7(String blank7) {
				this.blank7 = blank7;
			}
			public String getBlank8() {
				return blank8;
			}
		
			public void setBlank8(String blank8) {
				this.blank8 = blank8;
			}
			public String getCdtrAcctCIN() {
				return cdtrAcctCIN;
			}
		
			public void setCdtrAcctCIN(String cdtrAcctCIN) {
				this.cdtrAcctCIN = cdtrAcctCIN;
			}
			public String getCdtrAcctIBANPaese() {
				return cdtrAcctIBANPaese;
			}
		
			public void setCdtrAcctIBANPaese(String cdtrAcctIBANPaese) {
				this.cdtrAcctIBANPaese = cdtrAcctIBANPaese;
			}
			public String getCdtrAcctIBANCcheckDigit() {
				return cdtrAcctIBANCcheckDigit;
			}
		
			public void setCdtrAcctIBANCcheckDigit(String cdtrAcctIBANCcheckDigit) {
				this.cdtrAcctIBANCcheckDigit = cdtrAcctIBANCcheckDigit;
			}
			public String getSiaMittente() {
				return siaMittente;
			}
		
			public void setSiaMittente(String siaMittente) {
				this.siaMittente = siaMittente;
			}
			public String getSiaOrdinante() {
				return siaOrdinante;
			}
		
			public void setSiaOrdinante(String siaOrdinante) {
				this.siaOrdinante = siaOrdinante;
			}
			public String getBlank9() {
				return blank9;
			}
		
			public void setBlank9(String blank9) {
				this.blank9 = blank9;
			}
			public String getBlank10() {
				return blank10;
			}
		
			public void setBlank10(String blank10) {
				this.blank10 = blank10;
			}
			public String getBlank11() {
				return blank11;
			}
		
			public void setBlank11(String blank11) {
				this.blank11 = blank11;
			}
			public String getBlank12() {
				return blank12;
			}
		
			public void setBlank12(String blank12) {
				this.blank12 = blank12;
			}
			public String getBlank13() {
				return blank13;
			}
		
			public void setBlank13(String blank13) {
				this.blank13 = blank13;
			}
			public String getIdentificativoEsito() {
				return identificativoEsito;
			}
		
			public void setIdentificativoEsito(String identificativoEsito) {
				this.identificativoEsito = identificativoEsito;
			}
			public String getBlank14() {
				return blank14;
			}
		
			public void setBlank14(String blank14) {
				this.blank14 = blank14;
			}
			public String getBlank16() {
				return blank16;
			}
		
			public void setBlank16(String blank16) {
				this.blank16 = blank16;
			}
			public String getUltmtDbtrDistinta1() {
				return ultmtDbtrDistinta1;
			}
		
			public void setUltmtDbtrDistinta1(String ultmtDbtrDistinta1) {
				this.ultmtDbtrDistinta1 = ultmtDbtrDistinta1;
			}
			public String getUltmtDbtrDistinta2() {
				return ultmtDbtrDistinta2;
			}
		
			public void setUltmtDbtrDistinta2(String ultmtDbtrDistinta2) {
				this.ultmtDbtrDistinta2 = ultmtDbtrDistinta2;
			}
			public String getUltmtDbtrDistinta3() {
				return ultmtDbtrDistinta3;
			}
		
			public void setUltmtDbtrDistinta3(String ultmtDbtrDistinta3) {
				this.ultmtDbtrDistinta3 = ultmtDbtrDistinta3;
			}
			public String getUltmtDbtrDistinta4() {
				return ultmtDbtrDistinta4;
			}
		
			public void setUltmtDbtrDistinta4(String ultmtDbtrDistinta4) {
				this.ultmtDbtrDistinta4 = ultmtDbtrDistinta4;
			}
			public String getUltmtDbtrDistinta5() {
				return ultmtDbtrDistinta5;
			}
		
			public void setUltmtDbtrDistinta5(String ultmtDbtrDistinta5) {
				this.ultmtDbtrDistinta5 = ultmtDbtrDistinta5;
			}
			public String getUltmtDbtrDistinta6() {
				return ultmtDbtrDistinta6;
			}
		
			public void setUltmtDbtrDistinta6(String ultmtDbtrDistinta6) {
				this.ultmtDbtrDistinta6 = ultmtDbtrDistinta6;
			}
			public String getUltmtDbtrDistinta7() {
				return ultmtDbtrDistinta7;
			}
		
			public void setUltmtDbtrDistinta7(String ultmtDbtrDistinta7) {
				this.ultmtDbtrDistinta7 = ultmtDbtrDistinta7;
			}
			public String getUltmtDbtrDistinta8() {
				return ultmtDbtrDistinta8;
			}
		
			public void setUltmtDbtrDistinta8(String ultmtDbtrDistinta8) {
				this.ultmtDbtrDistinta8 = ultmtDbtrDistinta8;
			}
			public String getBlank17() {
				return blank17;
			}
		
			public void setBlank17(String blank17) {
				this.blank17 = blank17;
			}
			public String getBlank18() {
				return blank18;
			}
		
			public void setBlank18(String blank18) {
				this.blank18 = blank18;
			}
			public String getBlank19() {
				return blank19;
			}
		
			public void setBlank19(String blank19) {
				this.blank19 = blank19;
			}
			public String getBlank20() {
				return blank20;
			}
		
			public void setBlank20(String blank20) {
				this.blank20 = blank20;
			}
			public String getUltmtDbtrTransazione1() {
				return ultmtDbtrTransazione1;
			}
		
			public void setUltmtDbtrTransazione1(String ultmtDbtrTransazione1) {
				this.ultmtDbtrTransazione1 = ultmtDbtrTransazione1;
			}
			public String getUltmtDbtrTransazione2() {
				return ultmtDbtrTransazione2;
			}
		
			public void setUltmtDbtrTransazione2(String ultmtDbtrTransazione2) {
				this.ultmtDbtrTransazione2 = ultmtDbtrTransazione2;
			}
			public String getUltmtDbtrTransazione3() {
				return ultmtDbtrTransazione3;
			}
		
			public void setUltmtDbtrTransazione3(String ultmtDbtrTransazione3) {
				this.ultmtDbtrTransazione3 = ultmtDbtrTransazione3;
			}
			public String getUltmtDbtrTransazione4() {
				return ultmtDbtrTransazione4;
			}
		
			public void setUltmtDbtrTransazione4(String ultmtDbtrTransazione4) {
				this.ultmtDbtrTransazione4 = ultmtDbtrTransazione4;
			}
			public String getUltmtDbtrTransazione5() {
				return ultmtDbtrTransazione5;
			}
		
			public void setUltmtDbtrTransazione5(String ultmtDbtrTransazione5) {
				this.ultmtDbtrTransazione5 = ultmtDbtrTransazione5;
			}
			public String getUltmtDbtrTransazione6() {
				return ultmtDbtrTransazione6;
			}
		
			public void setUltmtDbtrTransazione6(String ultmtDbtrTransazione6) {
				this.ultmtDbtrTransazione6 = ultmtDbtrTransazione6;
			}
			public String getUltmtDbtrTransazione7() {
				return ultmtDbtrTransazione7;
			}
		
			public void setUltmtDbtrTransazione7(String ultmtDbtrTransazione7) {
				this.ultmtDbtrTransazione7 = ultmtDbtrTransazione7;
			}
			public String getUltmtDbtrTransazione8() {
				return ultmtDbtrTransazione8;
			}
		
			public void setUltmtDbtrTransazione8(String ultmtDbtrTransazione8) {
				this.ultmtDbtrTransazione8 = ultmtDbtrTransazione8;
			}
			public String getBlank21() {
				return blank21;
			}
		
			public void setBlank21(String blank21) {
				this.blank21 = blank21;
			}
			public String getUltmtCdtr1() {
				return ultmtCdtr1;
			}
		
			public void setUltmtCdtr1(String ultmtCdtr1) {
				this.ultmtCdtr1 = ultmtCdtr1;
			}
			public String getUltmtCdtr2() {
				return ultmtCdtr2;
			}
		
			public void setUltmtCdtr2(String ultmtCdtr2) {
				this.ultmtCdtr2 = ultmtCdtr2;
			}
			public String getUltmtCdtr3() {
				return ultmtCdtr3;
			}
		
			public void setUltmtCdtr3(String ultmtCdtr3) {
				this.ultmtCdtr3 = ultmtCdtr3;
			}
			public String getUltmtCdtr4() {
				return ultmtCdtr4;
			}
		
			public void setUltmtCdtr4(String ultmtCdtr4) {
				this.ultmtCdtr4 = ultmtCdtr4;
			}
			public String getUltmtCdtr5() {
				return ultmtCdtr5;
			}
		
			public void setUltmtCdtr5(String ultmtCdtr5) {
				this.ultmtCdtr5 = ultmtCdtr5;
			}
			public String getUltmtCdtr6() {
				return ultmtCdtr6;
			}
		
			public void setUltmtCdtr6(String ultmtCdtr6) {
				this.ultmtCdtr6 = ultmtCdtr6;
			}
			public String getUltmtCdtr7() {
				return ultmtCdtr7;
			}
		
			public void setUltmtCdtr7(String ultmtCdtr7) {
				this.ultmtCdtr7 = ultmtCdtr7;
			}
			public String getUltmtCdtr8() {
				return ultmtCdtr8;
			}
		
			public void setUltmtCdtr8(String ultmtCdtr8) {
				this.ultmtCdtr8 = ultmtCdtr8;
			}
			public String getDestCdtrRsp1() {
				return destCdtrRsp1;
			}
		
			public void setDestCdtrRsp1(String destCdtrRsp1) {
				this.destCdtrRsp1 = destCdtrRsp1;
			}
			public String getDestCdtrRsp2() {
				return destCdtrRsp2;
			}
		
			public void setDestCdtrRsp2(String destCdtrRsp2) {
				this.destCdtrRsp2 = destCdtrRsp2;
			}
			public String getBlank22() {
				return blank22;
			}
		
			public void setBlank22(String blank22) {
				this.blank22 = blank22;
			}
	
	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public String toString() {
		return StringUtils.getSimpleName(this.getClass()) + "\n" + StringUtils.toString(this);
	}
	
}
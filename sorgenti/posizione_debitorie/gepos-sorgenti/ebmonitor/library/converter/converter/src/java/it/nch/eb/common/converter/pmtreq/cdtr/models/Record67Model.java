
package it.nch.eb.common.converter.pmtreq.cdtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record67Model  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {

		private String filler;
		private String tipo_record;
		private String rec_count;
		private String alnRegnNb;
		private String cstmrNb;
		private String drvrsLicNb;
		private String birthDt;
		private String prvcOfBirth;
		private String cityOfBirth;
		private String ctryOfBirth;
		private String idntyCardNb;
		private String mplyrIdNb;
		private String id;
		private String idTp;
		private String psptNb;
		private String sclSctyNb;
		private String taxIdNb;
	
	private int	lineNumber;
	

			public String getFiller() {
				return filler;
			}

			public void setFiller(String filler) {
				this.filler = filler;
			}
			public String getTipo_record() {
				return tipo_record;
			}

			public void setTipo_record(String tipo_record) {
				this.tipo_record = tipo_record;
			}
			public String getRec_count() {
				return rec_count;
			}

			public void setRec_count(String rec_count) {
				this.rec_count = rec_count;
			}
			public String getAlnRegnNb() {
				return alnRegnNb;
			}

			public void setAlnRegnNb(String alnRegnNb) {
				this.alnRegnNb = alnRegnNb;
			}
			public String getCstmrNb() {
				return cstmrNb;
			}

			public void setCstmrNb(String cstmrNb) {
				this.cstmrNb = cstmrNb;
			}
			public String getDrvrsLicNb() {
				return drvrsLicNb;
			}

			public void setDrvrsLicNb(String drvrsLicNb) {
				this.drvrsLicNb = drvrsLicNb;
			}
			public String getBirthDt() {
				return birthDt;
			}

			public void setBirthDt(String birthDt) {
				this.birthDt = birthDt;
			}
			public String getPrvcOfBirth() {
				return prvcOfBirth;
			}

			public void setPrvcOfBirth(String prvcOfBirth) {
				this.prvcOfBirth = prvcOfBirth;
			}
			public String getCityOfBirth() {
				return cityOfBirth;
			}

			public void setCityOfBirth(String cityOfBirth) {
				this.cityOfBirth = cityOfBirth;
			}
			public String getCtryOfBirth() {
				return ctryOfBirth;
			}

			public void setCtryOfBirth(String ctryOfBirth) {
				this.ctryOfBirth = ctryOfBirth;
			}
			public String getIdntyCardNb() {
				return idntyCardNb;
			}

			public void setIdntyCardNb(String idntyCardNb) {
				this.idntyCardNb = idntyCardNb;
			}
			public String getMplyrIdNb() {
				return mplyrIdNb;
			}

			public void setMplyrIdNb(String mplyrIdNb) {
				this.mplyrIdNb = mplyrIdNb;
			}
			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}
			public String getIdTp() {
				return idTp;
			}

			public void setIdTp(String idTp) {
				this.idTp = idTp;
			}
			public String getPsptNb() {
				return psptNb;
			}

			public void setPsptNb(String psptNb) {
				this.psptNb = psptNb;
			}
			public String getSclSctyNb() {
				return sclSctyNb;
			}

			public void setSclSctyNb(String sclSctyNb) {
				this.sclSctyNb = sclSctyNb;
			}
			public String getTaxIdNb() {
				return taxIdNb;
			}

			public void setTaxIdNb(String taxIdNb) {
				this.taxIdNb = taxIdNb;
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
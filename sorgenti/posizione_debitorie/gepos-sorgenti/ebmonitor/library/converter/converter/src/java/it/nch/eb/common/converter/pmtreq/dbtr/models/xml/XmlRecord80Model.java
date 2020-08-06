package it.nch.eb.common.converter.pmtreq.dbtr.models.xml;

import it.nch.eb.common.converter.pmtreq.dbtr.models.Record80Model;

public class XmlRecord80Model extends Record80Model {
	
	public boolean isEmpty(String str) {
		return (str == null) || (str.trim().length() == 0) ;
	}

	public boolean getShowId() {
		return !isEmpty(this.getOrgnlTxRef_TaxIdNb()) || 
				!isEmpty(this.getOrgnlTxRef_Id()) || 
				!isEmpty(this.getOrgnlTxRef_Issr());
	}
	
	public boolean getShowPstlAdr() {
		return 
		!isEmpty(getAdrTp()) || 
		!isEmpty(getAdrLine1()) || 
		!isEmpty(getAdrLine2()) || 
		!isEmpty(getStrtNm()) || 
		!isEmpty(getBldgNb()) || 
		!isEmpty(getPstCd()) ||
		!isEmpty(getTwnNm()) || 
		!isEmpty(getCtry()) || 
		!isEmpty(getCtrySubDvsn());		
	}
	
	
	public boolean getShowPrtryId() {
		return !isEmpty(getOrgnlTxRef_Id()) || !isEmpty(getOrgnlTxRef_Issr());
	}
	
	public boolean getShowDbtr() {
		return !isEmpty(getOrgnlTxRef_Nm()) || getShowId();
	}
}
